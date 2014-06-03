package com.saddahaq.media.activity_settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.saddahaq.media.R;
import com.saddahaq.media.database.UserDataStore;

public class EmailNotifications extends Activity implements OnClickListener
{
	CheckBox check_approve,check_comment,check_respond,check_mention,check_vote,check_follow,check_rsvp,check_news,check_interest;
	Spinner spinner_approve,spinner_comment,spinner_respond,spinner_mention,spinner_vote,spinner_follow,spinner_rsvp,spinner_news,spinner_interest;
	Button save,cancle;
	List<CheckBox> cblist;
	List<Spinner> splist;
	List<String> l1,l2;
	ArrayAdapter<String> d1,d2;
	String my_id;
	UserDataStore uds;
	ProgressDialog pDialog;
	Cursor c,c1;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_change_notifications);
		initialize_controls();
		save.setOnClickListener(this);
		cancle.setOnClickListener(this);
		uds.open();
		c					=		uds.get_email_notifications(my_id);
		c.moveToFirst();
		String  status		=		c.getString(1).toString();
		Log.e("Status", status);
//		if(status.equals("first"))
//		{
//			d1					=		new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,l1);//getResources().getStringArray(R.array.notification_array1)
//			d2					=		new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,l2);//getResources().getStringArray(R.array.notification_array2)
//			d1.setDropDownViewResource(R.layout.spinnerbg);
//			d2.setDropDownViewResource(R.layout.spinnerbg);
//			for(int i=0;i<9;i++)
//			{
//				if(i<=3)
//				{
//					splist.get(i).setAdapter(d1);
//				}
//				else
//				{
//					splist.get(i).setAdapter(d2);
//				}
//			}
//		}
//		else if(status.equals("updated"))
//		{
//			
			for(int i=0;i<9;i++)
			{
				if(c.getString(i+12).equals("true"))
				{
					cblist.get(i).setChecked(true);
				}
				else
				{
					cblist.get(i).setChecked(false);
				}
				if(i<=3)
				{
					List<String> l=new ArrayList<String>();
					l.add(c.getString(i+3));
					for(int j=0;j<3;j++)
					{
						if(!l.contains(l1.get(j)))
						{
							l.add(l1.get(j));
						}
					}
					Log.e("List :"+i, ""+l.toString());
					d1					=		new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,l);
					d1.setDropDownViewResource(R.layout.spinnerbg);
					splist.get(i).setAdapter(d1);
				}
				else
				{
					List<String> l=new ArrayList<String>();
					l.add(c.getString(i+3));
					for(int j=0;j<2;j++)
					{
						if(!l.contains(l2.get(j)))
						{
							l.add(l2.get(j));
						}
					}
					Log.e("List :"+i, ""+l.toString());
					d2					=		new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,l);
					d2.setDropDownViewResource(R.layout.spinnerbg);
					splist.get(i).setAdapter(d2);
				}
				
			}
			
//		}

				
	}
	public void initialize_controls()
	{
		l1					=		new ArrayList<String>();
		l2					=		new ArrayList<String>();
		my_id				=		 getIntent().getExtras().getString("email");
		uds					=		 new UserDataStore(getApplicationContext());
		spinner_approve		=		(Spinner)findViewById(R.id.spinner_approve);
		spinner_comment		=		(Spinner)findViewById(R.id.spinner_comment);
	    spinner_respond		=		(Spinner)findViewById(R.id.spinner_respond);
	    spinner_mention		=		(Spinner)findViewById(R.id.spinner_mention);
	    spinner_vote		=		(Spinner)findViewById(R.id.spinner_vote);
	    spinner_follow		=		(Spinner)findViewById(R.id.spinner_follow);
	    spinner_rsvp		=		(Spinner)findViewById(R.id.spinner_rsvp);
	    spinner_news		=		(Spinner)findViewById(R.id.spinner_news);
	    spinner_interest    =		(Spinner)findViewById(R.id.spinner_interest);
		check_approve		=		(CheckBox)findViewById(R.id.check_approve);
		check_comment		=		(CheckBox)findViewById(R.id.check_comment);
		check_respond		=		(CheckBox)findViewById(R.id.check_respond);
		check_mention		=		(CheckBox)findViewById(R.id.check_mention);
		check_vote			=		(CheckBox)findViewById(R.id.check_vote);
		check_follow		=		(CheckBox)findViewById(R.id.check_follow);
		check_rsvp			=		(CheckBox)findViewById(R.id.check_rsvp);
		check_news			=		(CheckBox)findViewById(R.id.check_news);
		check_interest		=		(CheckBox)findViewById(R.id.check_interest);
		save				=		(Button)findViewById(R.id.notifications_save);
		cancle				=		(Button)findViewById(R.id.notifications_cancel);
		cblist				=		new ArrayList<CheckBox>();
		splist				=		new ArrayList<Spinner>();
		splist				=		Arrays.asList(new Spinner[]{spinner_approve,spinner_comment,spinner_respond,spinner_mention,spinner_vote,spinner_follow,spinner_rsvp,spinner_news,spinner_interest});
		cblist				=		Arrays.asList(new CheckBox[]{check_approve,check_comment,check_respond,check_mention,check_vote,check_follow,check_rsvp,check_news,check_interest});
		l1.add("As it comes");l1.add("Daily");l1.add("Weekly");
		l2.add("Daily");l2.add("Weekly");
	
	
	}
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.notifications_save:
			uds.update_notifications("updated", my_id, splist.get(0).getSelectedItem().toString(), splist.get(1).getSelectedItem().toString(), splist.get(2).getSelectedItem().toString(), splist.get(3).getSelectedItem().toString(), splist.get(4).getSelectedItem().toString(),  splist.get(5).getSelectedItem().toString(), splist.get(6).getSelectedItem().toString(), splist.get(7).getSelectedItem().toString(), splist.get(8).getSelectedItem().toString(),""+cblist.get(0).isChecked(),""+cblist.get(1).isChecked(),""+cblist.get(2).isChecked(),""+cblist.get(3).isChecked(),""+cblist.get(4).isChecked(),""+cblist.get(5).isChecked(),""+cblist.get(6).isChecked(),""+cblist.get(7).isChecked(),""+cblist.get(8).isChecked());
			finish();
	        this.overridePendingTransition(R.anim.slide_from, R.anim.slide_out_right);
			break;
		case R.id.notifications_cancel:
			finish();
	        this.overridePendingTransition(R.anim.slide_from, R.anim.slide_out_right);
	        break;
		}
	}
	public void onBackPressed()
	{
		super.onBackPressed();
		finish();
        this.overridePendingTransition(R.anim.slide_from, R.anim.slide_out_right);
	}
	class Fetch_Notification_info extends AsyncTask<String, String, String>
	{
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EmailNotifications.this);
			pDialog.setMessage("Fetching Notifications info...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}	
		@Override
		protected String doInBackground(String... params) 
		{
						return null;
		}

		protected void onPostExecute(String file_url) 
		{
//			Handler h=new Handler();
//			h.postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					pDialog.dismiss();
//				}
//			},2000);
		}

	}
}