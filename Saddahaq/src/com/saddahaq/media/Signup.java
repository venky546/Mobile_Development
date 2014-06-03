package com.saddahaq.media;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.saddahaq.media.utils.User;

public class Signup extends Activity implements ResultCallback<People.LoadPeopleResult>,ConnectionCallbacks, OnConnectionFailedListener ,OnClickListener
{
	LinearLayout llcontainer1;
	EditText firstname,lastname,username,emailid,password;
	Button signup,cancel,fb_signup,gp_signup;
	String fn,ln,un,pswd,em;
	CheckBox accept;
	private ConnectionDetector cd;
	private Pattern pattern;
	private Matcher matcher;
	List<String> permitions;
	private Boolean isInternetPresent = false;
	private String personName,personPhotoUrl,personGooglePlusProfile,nickname,Location,gp_email;
	private static final int RC_SIGN_IN = 0;
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	private GoogleApiClient mGoogleApiClient;
	private boolean mSignInClicked,mIntentInProgress;
	private ConnectionResult mConnectionResult;
	private UserDataStore uds;
	static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_layout);
		Log.e("msg","singup");
		uds		=	new UserDataStore(getApplicationContext());
		initializecontrols();
     	initialize_session_apiclient(savedInstanceState);
		fb_signup.setOnClickListener(this);
		gp_signup.setOnClickListener(this);
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		signup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) 
			{
				fn=firstname.getText().toString();
				ln=lastname.getText().toString();
				un=username.getText().toString();
				em=emailid.getText().toString();
				pswd=password.getText().toString();
				if(fn.length()==0||ln.length()==0||un.length()==0||pswd.length()==0)
				{
					Toast.makeText(Signup.this,"Fill all the details", Toast.LENGTH_SHORT).show();
				}
				else if(!accept.isChecked())
				{
					Toast.makeText(Signup.this,"Forgot to check the terms & conditions", Toast.LENGTH_SHORT).show();
				}
				else if(!validate(em))
				{
					Toast.makeText(Signup.this,"Wrong email format", Toast.LENGTH_SHORT).show();
				}
				else
				{
					uds.open();
					if(uds.CheckIsDataAlreadyInDBorNot("users", "email", em))
					{
						Toast.makeText(Signup.this,"Email Already exists", Toast.LENGTH_SHORT).show();
					}
					else
					{
						Toast.makeText(Signup.this,"Signup Sucess", Toast.LENGTH_SHORT).show();
						ByteArrayOutputStream boas=new ByteArrayOutputStream();
						Bitmap bm=BitmapFactory.decodeResource(getResources(), R.drawable.blank);
						bm.compress(Bitmap.CompressFormat.JPEG, 100, boas);
						byte a[]=boas.toByteArray();
						User u=new User(fn,ln,em,pswd,un,a);
						uds.create_user(u);
						uds.create_email_notifications("first",em, "Daily", "Daily", "Daily", "Daily", "Daily", "Daily", "Daily", "Daily", "Daily","false","false","false","false","false","false","false","false","false");
						uds.create_per_politics(em,"false", "false", "false", "false", "false");
						uds.create_per_news(em,"false", "false", "false");
						uds.create_per_social(em,"false", "false", "false");
						uds.create_per_entertainment(em, "false",  "false",  "false",  "false",  "false",  "false",  "false", "false",  "false");
						uds.create_per_sports(em, "false","false","false","false","false","false","false","false","false","false","false");
						uds.create_per_lifestyle(em, "false", "false", "false", "false", "false","false");
						uds.create_per_business(em, "false", "false", "false", "false", "false");
						uds.create_per_tech(em,  "false",  "false",  "false",  "false",  "false",  "false",  "false",  "false",  "false");
						uds.create_per_health(em,  "false",  "false",  "false");
						uds.create_per_science(em,  "false",  "false",  "false");
						Intent it=new Intent(getApplicationContext(),SlidingMenu.class);
						Bundle b=new Bundle();
						b.putString("name",fn+" "+ln);
						b.putString("fname",fn);
						b.putString("lname",ln);
						b.putString("email", em);
						b.putString("pswd",pswd);
						b.putString("location",u.getLocation());
						b.putString("dob",u.getDob());
						b.putString("mobile",u.getMobile());
						it.putExtras(b);
						startActivity(it);
						finish();
					}
					uds.close();
					
					
				}
			}
		});
	}
	private void initialize_session_apiclient(Bundle savedInstanceState) 
	{
		permitions = new ArrayList<String>();
		permitions.add("user_friends");
		permitions.add("email");
		//Initialize the session and restore the session for facebook
				Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
				Session session = Session.getActiveSession();
				if (session == null) {
					if (savedInstanceState != null) {
						session = Session.restoreSession(Signup.this, null, null,
								savedInstanceState);
					}
					if (session == null) {
						session = new Session(Signup.this);
					}
					Session.setActiveSession(session);
					if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
						session.openForRead(new Session.OpenRequest(Signup.this)
								.setCallback(null));
					}
				} else {
					Session.openActiveSession(Signup.this, false, null);
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
	@SuppressWarnings("deprecation")
	private void initializecontrols()
	{
		llcontainer1	=	(LinearLayout)findViewById(R.id.llcontainer);
		signup			=	(Button)findViewById(R.id.signup);
		cancel			=	(Button)findViewById(R.id.cancel_signup);
		firstname		=	(EditText)findViewById(R.id.firstname);
		lastname		=	(EditText)findViewById(R.id.lastname);
		username		=	(EditText)findViewById(R.id.username);
		emailid			=	(EditText)findViewById(R.id.emailid);
		password		=	(EditText)findViewById(R.id.password);
		pattern 		= 	Pattern.compile(EMAIL_PATTERN);
		accept			=	(CheckBox)findViewById(R.id.accept);
		fb_signup		=	(Button)findViewById(R.id.fb_signup);
		gp_signup		=	(Button)findViewById(R.id.gp_signup);
		cd				=	new ConnectionDetector(getApplicationContext());
		firstname.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		lastname.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		username.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		emailid.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		password.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		signup.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		cancel.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	}
	public boolean validate(final String hex) 
	{
		 
		matcher = pattern.matcher(hex);
		return matcher.matches();
 
	}
	private class SessionStatusCallback implements Session.StatusCallback 
	{

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
								  Intent it=new Intent(getApplicationContext(),SlidingMenu.class);
								  startActivity(it);
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
	@Override
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
	}
	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
	}
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
	@Override
	public void onClick(View v) 
	{
		isInternetPresent=cd.isConnectingToInternet();
		if(v.getId()==R.id.gp_signup && !mGoogleApiClient.isConnecting())
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
		else if(v.getId()==R.id.fb_signup)
		{
			if(isInternetPresent)
		     {
			      Session session = Session.getActiveSession();
					if (session != null) 
					{
						if (!session.isOpened() && !session.isClosed()) 
						{
							OpenRequest op = new Session.OpenRequest(
									Signup.this);
							op.setLoginBehavior(SessionLoginBehavior.SSO_WITH_FALLBACK);
							op.setCallback(statusCallback);
							op.setPermissions(permitions);
							session = new Builder(Signup.this).build();
							Session.setActiveSession(session);
							session.openForRead(op);
						} else 
						{
							Session.openActiveSession(Signup.this, true,
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
	private void resolveSignInError() 
	{
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
						+ personGooglePlusProfile + ", email: " + gp_email
						+ ", Image: " + personPhotoUrl);
				Toast.makeText(getApplicationContext(),"GooglePlus::\nName : "+personName+"\n Email :"+gp_email, Toast.LENGTH_SHORT).show();
				if(personName!=null)
				{
					Intent it=new Intent(getApplicationContext(),SlidingMenu.class);
					startActivity(it);
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
	public void onResult(LoadPeopleResult peopleData) 
	{
		Log.e("function","onResult");
		 if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
			    PersonBuffer personBuffer = peopleData.getPersonBuffer();
			    try {
			      int count = personBuffer.getCount();
			      for (int i = 0; i < count; i++) {
			        Log.d("Person", "Display name: " + personBuffer.get(i).getDisplayName());
			        Log.d("Person", "Email: " + personBuffer.get(i).getDisplayName());
			      }
			    } finally {
			      personBuffer.close();
			    }
			  } else {
			    Log.e("person", "Error requesting visible circles: " + peopleData.getStatus());
			  }
	}
	@SuppressWarnings("deprecation")
	public void display_nointernet_dialog()
	{
		final Dialog dialog = new Dialog(Signup.this);
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
