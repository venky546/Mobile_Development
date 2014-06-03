package com.saddahaq.media.utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.Builder;
import com.facebook.Session.OpenRequest;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.saddahaq.media.R;
import com.saddahaq.media.MainActivity;
public class FbTrail extends Activity
{
	String TAG="FbTrail";
	private UiLifecycleHelper uiHelper;
	Button authButton,getfriends,logout;
	TextView userInfoTextView;
	private String requestId;
	private boolean gameLaunchedFromDeepLinking = false;
	private boolean gameOverMessageDisplaying = false;
	private GraphUser currentFBUser;
	public void onCreate(Bundle SavedInstanceState)
	{
		super.onCreate(SavedInstanceState);
		setContentView(R.layout.facebook);
		uiHelper = new UiLifecycleHelper(this, callback);
	    uiHelper.onCreate(SavedInstanceState);
	    logout=(Button)findViewById(R.id.logout);
	    userInfoTextView=(TextView)findViewById(R.id.userInfoTextView);
	    Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		Session session = Session.getActiveSession();
		if (session == null) 
		{
			if (SavedInstanceState != null) 
			{
				session = Session.restoreSession(FbTrail.this, null, null,
						SavedInstanceState);
			}
			if (session == null) 
			{
				session = new Session(FbTrail.this);
			}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) 
			{
				session.openForRead(new Session.OpenRequest(FbTrail.this)
						.setCallback(null));
			}
		}
		else 
		{
			Session.openActiveSession(FbTrail.this, false, null);
		}
		logout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					Session.getActiveSession().closeAndClearTokenInformation();
					logout.setVisibility(View.INVISIBLE);
					getfriends.setVisibility(View.GONE);
			}
		});
		authButton = (Button) findViewById(R.id.authButton);
		getfriends = (Button) findViewById(R.id.friends);
		getfriends.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				getfriends();
				sendRequestDialog();
			}
		});
	    authButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				Session session = Session.getActiveSession();
				if (session != null) 
				{
					if (!session.isOpened() && !session.isClosed()) 
					{
						OpenRequest op = new Session.OpenRequest(FbTrail.this);
						op.setLoginBehavior(SessionLoginBehavior.SSO_WITH_FALLBACK);
						op.setPermissions(Arrays.asList("email","user_birthday"));//Arrays.asList("publish_stream","user_location", "user_birthday", "user_likes","basic_info","public_profile","email")
						op.setCallback(callback);
						session = new Builder(FbTrail.this).build();
						Session.setActiveSession(session);
//						session.openForPublish(op);
						session.openForRead(op);
					} else 
					{
						Session.openActiveSession(FbTrail.this, true,callback);
					}
			     }
			}
		});
	}
	public GraphUser getCurrentFBUser()
	{
		return currentFBUser;
	}
	private void onSessionStateChange(Session session, SessionState state, Exception exception) 
	{
		if (state.isOpened()) 
		{
			userInfoTextView.setVisibility(View.VISIBLE);
	        getfriends.setVisibility(View.VISIBLE);
				Request.newMeRequest(Session.getActiveSession(), new GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) 
					{
							Log.e("User Info", ""+user );
							currentFBUser=user;
							if(user!=null)
							{
											String name=user.getName();
											String bday=user.getBirthday();
											String email=user.getProperty("email").toString();
											if(email!=null)
											{
												Toast.makeText(getApplicationContext(), "Email : "+email, Toast.LENGTH_SHORT).show();
											}
								  Toast.makeText(getApplicationContext(), "Facebook::\nName :"+name+"\nBday :"+bday, Toast.LENGTH_SHORT).show();
								  userInfoTextView.setText(buildUserInfoDisplay(user));
							}
						Log.e("permissions",Session.getActiveSession().getPermissions().toString());
					}
				}).executeAsync();
				logout.setVisibility(View.VISIBLE);
	    } else if (state.isClosed()) 
	    {
	        userInfoTextView.setVisibility(View.INVISIBLE);
	    }
		if (state.isOpened() && requestId != null)
		{
	        Toast.makeText(getApplicationContext(), "Incoming request",
	                Toast.LENGTH_SHORT).show();
	        requestId = null;
	    }
	}
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	private String buildUserInfoDisplay(GraphUser user) {
	    StringBuilder userInfo = new StringBuilder("");

	    userInfo.append(String.format("Name: %s\n\n", 
	        user.getName()));
	    userInfo.append(String.format("Birthday: %s\n\n", 
	        user.getBirthday()));
	    userInfo.append(String.format("Locale: %s\n\n", 
	        user.getProperty("locale")));
	    return userInfo.toString();
	}
	private void sendRequestDialog() {
	    Bundle params = new Bundle();
	    params.putString("message", "Learn how to make your Android apps social");
	    params.putString("data",
	            "{\"badge_of_awesomeness\":\"1\"," +
	            "\"social_karma\":\"5\"}");
	    params.putString("redirect_uri", "https://play.google.com/store/apps/details?id=com.saddahaqmedia.powerplay");
	    WebDialog requestsDialog = (
	        new WebDialog.RequestsDialogBuilder(FbTrail.this,
	            Session.getActiveSession(),
	            params))
	            .setOnCompleteListener(new OnCompleteListener() {
	                @Override
	                public void onComplete(Bundle values,
	                    FacebookException error) {
	                    if (error != null) {
	                        if (error instanceof FacebookOperationCanceledException) {
	                            Toast.makeText(getApplicationContext(), 
	                                "Request cancelled", 
	                                Toast.LENGTH_SHORT).show();
	                        } else {
	                            Toast.makeText(getApplicationContext(), 
	                                "Network Error", 
	                                Toast.LENGTH_SHORT).show();
	                        }
	                    } else {
	                        final String requestId = values.getString("request");
	                        if (requestId != null) {
	                            Toast.makeText(getApplicationContext(), 
	                                "Request sent",  
	                                Toast.LENGTH_SHORT).show();
	                        } else {
	                            Toast.makeText(getApplicationContext(), 
	                                "Request cancelled", 
	                                Toast.LENGTH_SHORT).show();
	                        }
	                    }   
	                }

	            })
	            .build();
	    requestsDialog.show();
	}
	public void getfriends()
	{
		Bundle bundle = new Bundle();
		bundle.putString("fields", "id,name");
		bundle.putString("limit", "5000");
		final ProgressDialog dialog = new ProgressDialog(FbTrail.this);
		dialog.setMessage("please wait.");
		dialog.show();
		Request request = new Request(Session.getActiveSession(),"/me/friends", bundle, HttpMethod.GET,new Request.Callback() 
		{
					@Override
					public void onCompleted(Response response) 
					{
						try 
						{
							GraphObject graphObject = response.getGraphObject();
							if (graphObject == null)
								graphObject = response.getGraphObject();
							if (graphObject != null) 
							{
								JSONObject jsonObject = graphObject.getInnerJSONObject();

								if (jsonObject.has("id")|| jsonObject.toString().contains("id")) 
								{
									JSONArray array = jsonObject.getJSONArray("data");
									for (int i = 0; i < array.length(); i++) 
									{
										JSONObject user = array.getJSONObject(i);
										String id=user.getString("id");
										String name = user.getString("name");
										String image = "http://graph.facebook.com/"+ user.getString("id")+ "/picture?type=normal";
										if(name.equals("Sree Harsha"))
										{
											Log.e("User :", "Name : "+name+" id : "+id);
										}
										
									}
									dialog.dismiss();

								}
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				});
		request.executeAsync();
	}
	
	public void onResume() {
	    super.onResume();
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }
	    if (getCurrentFBUser() != null && !gameLaunchedFromDeepLinking) {
			// As long as the user is logged in and the game hasn't been launched yet
			// from deep linking, see if it has been deep linked and launch the game appropriately
			Uri target = getIntent().getData();
			if (target != null) {
				Intent i = new Intent(FbTrail.this, MainActivity.class);
				
			    // Target is the deep-link Uri, so skip loading this home screen and load the game
				// directly with the sending user's picture to smash
				String graphRequestIDsForSendingUser = target.getQueryParameter("request_ids");
				String feedPostIDForSendingUser = target.getQueryParameter("challenge_brag");
				
				if (graphRequestIDsForSendingUser != null) {
					// Deep linked through a Request and use the latest request (request_id) if multiple requests have been sent
					String [] graphRequestIDsForSendingUsers = graphRequestIDsForSendingUser.split(",");
					String graphRequestIDForSendingUser = graphRequestIDsForSendingUsers[graphRequestIDsForSendingUsers.length-1];
					Bundle bundle = new Bundle();
					bundle.putString("request_id", graphRequestIDForSendingUser);
					i.putExtras(bundle);
					gameLaunchedFromDeepLinking = true;
					startActivityForResult(i, 0);
					
					// Delete the Request now it has been consumed and processed
					Request deleteFBRequestRequest = new Request(Session.getActiveSession(),
							graphRequestIDForSendingUser + "_" +getCurrentFBUser().getId(),
							new Bundle(),
		                    HttpMethod.DELETE,
		                    new Request.Callback() {

								@Override
								public void onCompleted(Response response) {
									FacebookRequestError error = response.getError();
									if (error != null) {
										Log.e(TAG, "Deleting consumed Request failed: " + error.getErrorMessage());
									} else {
										Log.i(TAG, "Consumed Request deleted");
									}
								}
							});
					Request.executeBatchAsync(deleteFBRequestRequest);
				} else if (feedPostIDForSendingUser != null) {
					// Deep linked through a feed post, so start the game smashing the user specified by the id attached to the
					// challenge_brag parameter
					Bundle bundle = new Bundle();
					bundle.putString("user_id", feedPostIDForSendingUser);
					i.putExtras(bundle);
					gameLaunchedFromDeepLinking = true;
					startActivityForResult(i, 0);
				}
			} else {
			    // Launched with no deep-link Uri, so just continue as normal and load the home screen
			}
		}
		
		if (!gameLaunchedFromDeepLinking && gameOverMessageDisplaying) {
			// The game hasn't just been launched from deep linking and the game over message should still be displaying, so ...
			
			// Complete the game over logic
//			completeGameOver();
		}
	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
}
