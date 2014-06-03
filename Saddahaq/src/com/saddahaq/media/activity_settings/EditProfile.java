package com.saddahaq.media.activity_settings;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.saddahaq.media.R;
import com.saddahaq.media.database.UserDataStore;
import com.saddahaq.media.fragments.DatePickerFragment;

public class EditProfile extends Activity implements OnClickListener
{
	EditText fname,lname,location,dob,mobile;
	String email,fn,ln,loc,my_id;
	Bundle b;
	Button profile_cancle,profile_save;
	int pyear,pmonth,pday;
	UserDataStore uds;
	ProgressDialog pDialog;
	public void onCreate(Bundle SavedInstanceState)
	{
		super.onCreate(SavedInstanceState);
		setContentView(R.layout.profile_edit);
		Intent it=getIntent();
		my_id=it.getExtras().getString("email");
		initialize_controls();
		profile_cancle.setOnClickListener(this);
		profile_save.setOnClickListener(this);
		dob.setOnClickListener(this);
	}
	public void initialize_controls()
	{
		Log.e("function()","initialize_controls");
		fname			=		(EditText)findViewById(R.id.edit_fname);
		lname			=		(EditText)findViewById(R.id.edit_lname);
		location		=		(EditText)findViewById(R.id.edit_location);
		dob				=		(EditText)findViewById(R.id.edit_dob);
		mobile			=		(EditText)findViewById(R.id.edit_mobile);
		profile_save	=		(Button)findViewById(R.id.profile_save_btn);
		profile_cancle	=		(Button)findViewById(R.id.profile_cancel_btn);
		uds				=		new UserDataStore(getApplicationContext());
		dob.setInputType(InputType.TYPE_NULL);
		new FetchProfileAsyn().execute(my_id);
		
	}
	private void showDatePicker() {
		  DatePickerFragment date = new DatePickerFragment();
		  Calendar calender = Calendar.getInstance();
		  Bundle args = new Bundle();
		  pyear		=		calender.get(Calendar.YEAR);
		  pmonth		=		calender.get(Calendar.MONTH);
		  pday	=		calender.get(Calendar.DAY_OF_MONTH);
		  String da=dob.getText().toString();
		  if(da.length()==0)
		  {
			  args.putInt("year", calender.get(Calendar.YEAR));
			  args.putInt("month", calender.get(Calendar.MONTH));
			  args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
  
		  }
		  else//date is present in textarea
		  {
			  String ar[]=da.split("-");
			  args.putInt("year", Integer.parseInt(ar[2]));
			  args.putInt("month", Integer.parseInt(ar[1])-1);
			  args.putInt("day", Integer.parseInt(ar[0]));
		  }
		  date.setArguments(args);
		  date.setCallBack(ondate);
		  date.show(getFragmentManager(), "Date Picker");
		 }
		 OnDateSetListener ondate = new OnDateSetListener() 
		 {
		  @Override
		  public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) 
		  {
			  if(year>pyear)
			  {
				  Toast.makeText(getApplication().getApplicationContext(),"Dude are you from future ??", Toast.LENGTH_SHORT).show();
				  if(dob.getText().length()==0)
				  {
					  dob.setText("");  
				  }
				  
			  }
			  else if(monthOfYear>pmonth && year==pyear)
			  {
				  Toast.makeText(getApplication().getApplicationContext(),"Dude are you from future ??", Toast.LENGTH_SHORT).show();
				  if(dob.getText().length()==0)
				  {
					  dob.setText("");  
				  }
			  }
			  else
			  {
				  dob.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
					       + "-" + String.valueOf(year));  
			  }
			  
		  }
		 };
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
			case R.id.profile_save_btn:
				String fn=fname.getText().toString();
				String ln=lname.getText().toString();
				String birth=dob.getText().toString().length()==0?"Edit your DOB":dob.getText().toString();
				String loc=location.getText().toString().length()==0?"Edit your Location":location.getText().toString();
				String mob=mobile.getText().toString().length()==0?"Edit your Mobile Num":mobile.getText().toString();
				if(fn.length()==0||ln.length()==0)
				{
					Toast.makeText(getApplicationContext(),"first and last name fields can not be empty", Toast.LENGTH_SHORT).show();
				}
				else
				{
					new EditProfileAysn().execute(my_id,fn,ln,loc,birth,mob);
				}
				break;
			case R.id.profile_cancel_btn:
				finish();
		        this.overridePendingTransition(R.anim.slide_from, R.anim.slide_out_right);
				break;
			case R.id.edit_dob:
				showDatePicker();
				break;
		 }		
	}
	class FetchProfileAsyn extends AsyncTask<String, String, String>
	{
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditProfile.this);
			pDialog.setMessage("Fetching info...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		@Override
		protected String doInBackground(String... params) 
		{
			Log.e("msg", "do in background  :"+params[0]);
			uds.open();
			Cursor c=uds.getdetails_of_user(params[0]);
			c.moveToFirst();
			b=new Bundle();//b.putString
			b.putString("fname",c.getString(2));
			b.putString("lname",c.getString(3));
			b.putString("location",c.getString(7));
			b.putString("dob",c.getString(8));
			b.putString("mobile",c.getString(9));
			uds.close();
			return null;
		}
		protected void onPostExecute(String file_url) 
		{
			Handler h=new Handler();
			h.postDelayed(new Runnable() {
				@Override
				public void run() {
					pDialog.dismiss();
					fname.setText(b.getString("fname"));
					lname.setText(b.getString("lname"));
					if(!b.getString("location").equals("Edit your Location"))
					{
						location.setText(b.getString("location"));
					}
					if(!b.getString("dob").equals("Edit your DOB"))
					{
						dob.setText(b.getString("dob"));
					}
					if(!b.getString("mobile").equals("Edit your Mobile Num"))
					{
						mobile.setText(b.getString("mobile"));
					}
				}
			},1500);
		}
		
	}
	class EditProfileAysn extends AsyncTask<String, String, String>
	{
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditProfile.this);
			pDialog.setMessage("Saving the changes...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		@Override
		protected String doInBackground(String... params) 
		{
			Bundle b=new Bundle();
			b.putString("email",params[0]);
			b.putString("fname",params[1]);
			b.putString("lname",params[2]);
			b.putString("location",params[3]);
			b.putString("dob",params[4]);
			b.putString("mobile",params[5]);
			uds.open();
			uds.change_profile(b);
			uds.close();
			return null;
		}
		protected void onPostExecute(String file_url) 
		{
			Handler h=new Handler();
			h.postDelayed(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getApplicationContext(), "Changes Saved to profile!!", Toast.LENGTH_SHORT).show();
					pDialog.dismiss();
					finish();
			        EditProfile.this.overridePendingTransition(R.anim.slide_from, R.anim.slide_out_right);
				}
			}, 5000);
		}
	}
	public void onBackPressed()
	{
		super.onBackPressed();
		finish();
        this.overridePendingTransition(R.anim.slide_from, R.anim.slide_out_right);
	}
	}

