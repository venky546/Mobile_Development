package com.saddahaq.media.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.saddahaq.media.R;

public class GPTrail extends Activity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener, ResultCallback<LoadPeopleResult>
{
	  Button gplogin,gplogout,gpfriends,gpshare;
	  TextView gpuserinfo;
	  private static final int RC_SIGN_IN = 0;
	  private GoogleApiClient mGoogleApiClient;
	  private boolean mIntentInProgress;
	  private boolean mSignInClicked;
	  private ConnectionResult mConnectionResult;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.googleplus);
		gplogin		=		(Button)findViewById(R.id.gplogin);
		gplogout	=		(Button)findViewById(R.id.gplogout);
		gpfriends	=		(Button)findViewById(R.id.gpfriends);
		gpuserinfo	=		(TextView)findViewById(R.id.gpuserinfo);
		gpshare		=		(Button)findViewById(R.id.gpshare);
		gplogin.setOnClickListener(this);
		gplogout.setOnClickListener(this);
		gpfriends.setOnClickListener(this);
		gpshare.setOnClickListener(this);
		mGoogleApiClient=new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(Plus.API, null)
        .addScope(Plus.SCOPE_PLUS_LOGIN)
        .addScope(Plus.SCOPE_PLUS_PROFILE)
        .build();
		
	}
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.gplogin:
				if(!mGoogleApiClient.isConnecting()) 
				{
				    mSignInClicked = true;
//				    resolveSignInError();
				    mGoogleApiClient.connect();
				}
			break;
		case R.id.gpfriends:
			 Plus.PeopleApi.loadVisible(mGoogleApiClient, null)
		      .setResultCallback(this);
			break;
		case R.id.gplogout:
			if (mGoogleApiClient.isConnected()) 
			{
			      Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			      mGoogleApiClient.disconnect();
//			      mGoogleApiClient.connect();
			}
			gplogout.setVisibility(View.INVISIBLE);
			gpfriends.setVisibility(View.INVISIBLE);
			gpuserinfo.setVisibility(View.INVISIBLE);
			gpshare.setVisibility(View.INVISIBLE);
			break;
		case R.id.gpshare:
			Intent shareIntent = new PlusShare.Builder(this)
	          .setType("text/plain")
	          .setText("Check out this app for Saddahaq!!!")
	          .setContentUrl(Uri.parse("https://saddahaq.com"))
	          .addCallToAction("CONNECT",Uri.parse("https://saddahaq.com"), "/pages/create")
	          .getIntent();
	      startActivityForResult(shareIntent, 0);
		}
	}
	 protected void onStart() 
	 {
		    super.onStart();
     }
	protected void onStop() 
	{
		    super.onStop();
		    if (mGoogleApiClient.isConnected()) 
		    {
		      mGoogleApiClient.disconnect();
		    }
	}
	@Override
	public void onConnected(Bundle connectionHint) 
	{
		  mSignInClicked = false;
		  Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
		  gplogout.setVisibility(View.VISIBLE);
		  gpfriends.setVisibility(View.VISIBLE);
		  gpuserinfo.setVisibility(View.VISIBLE);
		  gpshare.setVisibility(View.VISIBLE);
		  getProfileInformation();
	}
	@Override
	public void onConnectionSuspended(int cause) 
	{
		 mGoogleApiClient.connect();
	}
	@Override
	public void onConnectionFailed(ConnectionResult result) 
	{
		 if (!mIntentInProgress && result.hasResolution()) 
		 {
			    try 
			    {
			      mIntentInProgress = true;
			      result.startResolutionForResult(this,RC_SIGN_IN);
			    } catch (SendIntentException e) 
			    {
			      mIntentInProgress = false;
			      mGoogleApiClient.connect();
			    }
			    mConnectionResult = result;
			    if (mSignInClicked) 
			    {
			      resolveSignInError();
			    }
		}
	}
	protected void onActivityResult(int requestCode, int responseCode, Intent intent) 
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
	}
	public void resolveSignInError() 
	{
	  if (mConnectionResult.hasResolution()) 
	  {
	    try 
	    {
	      mIntentInProgress = true;
	      mConnectionResult.startResolutionForResult(this,RC_SIGN_IN);
	    } catch (SendIntentException e) 
	    {
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
			String	personName = currentPerson.getDisplayName();
			String	personPhotoUrl = currentPerson.getImage().getUrl();
			String	personGooglePlusProfile = currentPerson.getUrl();
//			Plus.MomentsApi.load(mGoogleApiClient);
			String	gp_email = Plus.AccountApi.getAccountName(mGoogleApiClient);
			Log.e("Output :", "Name: " + personName + ", plusProfile: "
						+ personGooglePlusProfile + ", email: " + gp_email
						+ ", Image: " + personPhotoUrl);
			String a="Name :"+personName+"\nEmail :"+gp_email;
			gpuserinfo.setText(a);
//			Toast.makeText(getApplicationContext(),"GooglePlus::\nName : "+personName+"\n Email :"+gp_email, Toast.LENGTH_SHORT).show();
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
		 if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) 
		 {
			    PersonBuffer personBuffer = peopleData.getPersonBuffer();
			    try {
			      int count = personBuffer.getCount();
			      StringBuffer a=new StringBuffer();
			      a.append("Number of connections : "+count+"\n\n");
			      for (int i = 0; i < count; i++) 
			      {
//			        Log.d("Person", "Display name: " + personBuffer.get(i).getDisplayName());
			        a.append(""+personBuffer.get(i).getDisplayName()+"\n");
			      }
			      gpuserinfo.setText(a.toString());
			    } finally {
			      personBuffer.close();
			    }
	    } else {
			    Log.e("person", "Error requesting visible circles: " + peopleData.getStatus());
			  }
	}
}
