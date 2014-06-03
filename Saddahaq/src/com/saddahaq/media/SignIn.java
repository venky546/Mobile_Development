package com.saddahaq.media;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.Builder;
import com.facebook.Session.OpenRequest;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphUser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.saddahaq.media.database.UserDataStore;
import com.saddahaq.media.utils.ConnectionDetector;

@SuppressWarnings("deprecation")
public class SignIn extends Activity implements ResultCallback<People.LoadPeopleResult>,ConnectionCallbacks, OnConnectionFailedListener ,OnClickListener
{
	  LinearLayout llcontainer;
	  EditText email,pswd;
	  Button login,cancel,fb,gp;
	  List<String> permitions;
	  private Pattern pattern;
	  private Matcher matcher;
	  private ConnectionDetector cd;
	  private Boolean isInternetPresent = false;
	  private String personName,personPhotoUrl,personGooglePlusProfile,nickname,Location,gp_email;
	  private Session.StatusCallback statusCallback = new SessionStatusCallback();
	  private static final int RC_SIGN_IN = 0;
	  private GoogleApiClient mGoogleApiClient;
	  private boolean mSignInClicked;
	  private ConnectionResult mConnectionResult;
	  private boolean mIntentInProgress;
	  private UserDataStore uds;
	  Bundle b=new Bundle();
      static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin_layout);
		cd=new ConnectionDetector(getApplicationContext());
		initialize_session_apiclient(savedInstanceState); 
		initializecontrols();
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) 
			{
				String em=email.getText().toString();
				String ps=pswd.getText().toString();
				uds.open();
				if(em.length()==0||ps.length()==0)
				{
					Toast.makeText(getApplicationContext(), "Enter all the above", Toast.LENGTH_SHORT).show();
				}
				else
				{
					if(validate(em))
					{
						if(uds.CheckValidUser(em, ps))
						{
							Toast.makeText(getApplicationContext(), "Login Sucess!!", Toast.LENGTH_SHORT).show();
							Intent it=new Intent(getApplicationContext(),SlidingMenu.class);
							it.putExtra("id",em);
							Bundle b=new Bundle();
							Cursor c=uds.getdetails_of_user(em);
							c.moveToFirst();
							b.putString("name",c.getString(1));
							b.putString("fname",c.getString(2));
							b.putString("lname",c.getString(3));
							b.putString("email", c.getString(5));
							b.putString("pswd",c.getString(6));
							b.putString("location",c.getString(7));
							b.putString("dob",c.getString(8));
							b.putString("mobile",c.getString(9));
							it.putExtras(b);
							startActivity(it);
							finish();
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Wrong Credentials submitted!!", Toast.LENGTH_SHORT).show();
						}
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Wrong email format", Toast.LENGTH_SHORT).show();
					}
					
				}
				uds.close();
			}
		});
		fb.setOnClickListener(this);
		gp.setOnClickListener(this);
	}
	public void initialize_session_apiclient(Bundle savedInstanceState)
	{
		permitions = new ArrayList<String>();
		permitions.add("user_friends");
		permitions.add("email");
		//Initialize the session and restore the session for facebook
				Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
				Session session = Session.getActiveSession();
				if (session == null) {
					if (savedInstanceState != null) {
						session = Session.restoreSession(SignIn.this, null, null,
								savedInstanceState);
					}
					if (session == null) {
						session = new Session(SignIn.this);
					}
					Session.setActiveSession(session);
					if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
						session.openForRead(new Session.OpenRequest(SignIn.this)
								.setCallback(null));
					}
				} else {
					Session.openActiveSession(SignIn.this, false, null);
				}
		//googleapiclient initialization..
		 mGoogleApiClient = new GoogleApiClient.Builder(this)
	        .addConnectionCallbacks(this)
	        .addOnConnectionFailedListener(this)
	        .addApi(Plus.API, null)
	        .addScope(Plus.SCOPE_PLUS_LOGIN)
	        .addScope(Plus.SCOPE_PLUS_PROFILE)
	        .build();
	}
	public void initializecontrols()
	{
		llcontainer		=	(LinearLayout)findViewById(R.id.llcontainer);
		email			=	(EditText)findViewById(R.id.emial);
		pswd			=	(EditText)findViewById(R.id.pswd);
		login			=	(Button)findViewById(R.id.Login);
		cancel			=	(Button)findViewById(R.id.cancel);
		pattern 		=	 Pattern.compile(EMAIL_PATTERN);
		fb				=	(Button)findViewById(R.id.fblogin);
		gp				=	(Button)findViewById(R.id.gplogin);
		uds				=	new UserDataStore(getApplicationContext());
		email.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		pswd.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		login.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		cancel.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	}
	public boolean validate(final String hex) 
	{
		matcher = pattern.matcher(hex);
		return matcher.matches();
	}
	public void onConnectionFailed(ConnectionResult result) 
	{
		  if (!mIntentInProgress) 
		  {
		    // Store the ConnectionResult so that we can use it later when the user clicks
		    // 'sign-in'.
		    mConnectionResult = result;

			    if (mSignInClicked) 
			    {
			      // The user has already clicked 'sign-in' so we attempt to resolve all
			      // errors until the user is signed in, or they cancel.
			      resolveSignInError();
			    }
		  }
	}
	@Override
	public void onClick(View v) 
	{
		isInternetPresent=cd.isConnectingToInternet();
		if(v.getId()==R.id.gplogin && !mGoogleApiClient.isConnecting())
		{
			     if(isInternetPresent)
			     {
			    	 mSignInClicked = true;
		    		 mGoogleApiClient.connect(); 
			     }
			     else
			     {
			    	 display_nointernet_dialog();
			     }
		}
		else if(v.getId()==R.id.fblogin)
		{
			if(isInternetPresent)
		     {
			      Session session = Session.getActiveSession();
					if (session != null) 
					{
						if (!session.isOpened() && !session.isClosed()) 
						{
							OpenRequest op = new Session.OpenRequest(
									SignIn.this);
							op.setLoginBehavior(SessionLoginBehavior.SSO_WITH_FALLBACK);
							op.setCallback(statusCallback);
							op.setPermissions(permitions);
							session = new Builder(SignIn.this).build();
							Session.setActiveSession(session);
//							session.openForPublish(op);
							session.openForRead(op);
						} else 
						{
							Session.openActiveSession(SignIn.this, true,
									statusCallback);
						}
				      }
		   }
			else
		     {
				display_nointernet_dialog(); 
		     }
		 }
	     	
		
	}
	protected void onStart() {
		super.onStart();
//		mGoogleApiClient.connect();
	}

	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}
	@Override
	public void onConnected(Bundle arg0) {
		mSignInClicked = true;
		Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
		getProfileInformation();
		Log.e("msg","getprofileinfo completed....");
		 Plus.PeopleApi.loadVisible(mGoogleApiClient, null)
	      .setResultCallback(this);
//		mGoogleApiClient.disconnect();
	}
	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
	}
	@Override
	protected void onActivityResult(int requestCode, int responseCode,Intent intent) 
	{
		if (requestCode == RC_SIGN_IN) 
		{
			if (responseCode != RESULT_OK) 
			{
				mSignInClicked = false;
			}
			mIntentInProgress = false;
			if (!mGoogleApiClient.isConnecting()) 
			{
				mGoogleApiClient.connect();
			}
		}
		else if (responseCode == RESULT_OK && requestCode == Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE)
		{
			Session.getActiveSession().onActivityResult(this, requestCode,responseCode, intent);
		}
	}
	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
			} catch (SendIntentException e) {
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	
      }
	private void getProfileInformation() 
	{
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) 
			{
				Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
				personName = currentPerson.getDisplayName();
				personPhotoUrl = currentPerson.getImage().getUrl();
				personGooglePlusProfile = currentPerson.getUrl();
				nickname = currentPerson.getNickname();
				Location=currentPerson.getCurrentLocation();
				gp_email = Plus.AccountApi.getAccountName(mGoogleApiClient);
				Log.e("Output :", "Name: " + personName + ", plusProfile: "
						+ personGooglePlusProfile + ", email: " + email
						+ ", Image: " + personPhotoUrl);
				Toast.makeText(getApplicationContext(),"GooglePlus::\nName : "+personName+"\n Email :"+gp_email, Toast.LENGTH_SHORT).show();
				if(personName!=null)
				{
					Intent it=new Intent(getApplicationContext(),SlidingMenu.class);
//					startActivity(it);
				}
			} else 
			{
				Toast.makeText(getApplicationContext(),
						"Person information is null", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onResult(LoadPeopleResult peopleData) 
	{
		Log.e("function","onResult");
		 if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
			    PersonBuffer personBuffer = peopleData.getPersonBuffer();
			    try {
			      int count = personBuffer.getCount();
			      for (int i = 0; i < count; i++) {
			        Log.d("Person", "Display name: " + personBuffer.get(i).getDisplayName());
			        Log.d("Person", "Id: " + personBuffer.get(i).getId());
			      }
			    } finally {
			      personBuffer.close();
			    }
			  } else {
			    Log.e("person", "Error requesting visible circles: " + peopleData.getStatus());
			  }
	}
	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			if (session.isOpened()) 
			{
				Request.newMeRequest(Session.getActiveSession(), new GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) 
					{
							Log.e("User Info", ""+user );
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
							}
						Log.e("permissions",Session.getActiveSession().getPermissions().toString());
					}
				}).executeAsync();
			}
		}

		
	}
	public void getFriends()
	{
		Session s=Session.getActiveSession();
		if(s==null)
		{
			Log.e("session","Session is NULL");
		}
		else
		{
			Log.e("session","Session is NOTNULL");
		}
	}
	public void display_nointernet_dialog()
	{
		final Dialog dialog = new Dialog(SignIn.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.no_internet_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        RelativeLayout rl=(RelativeLayout)dialog.findViewById(R.id.rel_nointernet);
        rl.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button signin=(Button)dialog.findViewById(R.id.btn_no_internet);
        signin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
	}
}