package com.saddahaq.media.activity_settings;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.saddahaq.media.R;
import com.saddahaq.media.database.UserDataStore;

public class ChangePswd extends Activity implements OnClickListener
{
	EditText cur_pswd,new_pswd,confirm_pswd;
	Button save_btn_pswd,cancel_btn_pswd;
	String my_id,present_pswd;
	ProgressDialog pDialog;
	UserDataStore uds;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_changepswd);
		initialize_controls();
		Intent it=getIntent();
		my_id=it.getExtras().getString("email");
		present_pswd=it.getExtras().getString("pswd");
		Log.e("present pswd", "present pswd :"+present_pswd);
		save_btn_pswd.setOnClickListener(this);
		cancel_btn_pswd.setOnClickListener(this);
	}
	public void initialize_controls()
	{
		cur_pswd		=		(EditText)findViewById(R.id.cur_pswd);
		new_pswd		=		(EditText)findViewById(R.id.new_pswd);
		confirm_pswd	=		(EditText)findViewById(R.id.confirm_pswd);
		cancel_btn_pswd	=		(Button)findViewById(R.id.cancel_btn_pswd);
		save_btn_pswd	=		(Button)findViewById(R.id.save_btn_pswd);
		uds				=		new UserDataStore(getApplicationContext());
		
	}
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.save_btn_pswd:
			if(cur_pswd.getText().toString().length()==0||new_pswd.getText().toString().length()==0||confirm_pswd.getText().toString().length()==0)
			{
				Toast.makeText(getApplicationContext(),"None of the fields can be empty!!",Toast.LENGTH_SHORT).show();
			}
			else if(!cur_pswd.getText().toString().equals(present_pswd))
			{
				Toast.makeText(getApplicationContext(),"Oops! Looks like your current password is incorrect!!",Toast.LENGTH_SHORT).show();
			}
			else if(!new_pswd.getText().toString().equals(confirm_pswd.getText().toString()))
			{
				Toast.makeText(getApplicationContext(),"confirm password does not match with the new password!!",Toast.LENGTH_SHORT).show();
			}
			else if(new_pswd.getText().toString().equals(present_pswd))
			{
				Toast.makeText(getApplicationContext(),"Current password and new password are same!!",Toast.LENGTH_SHORT).show();
			}
			else
			{
				new change_pswd().execute(my_id,present_pswd,new_pswd.getText().toString());
			}
			break;
		case R.id.cancel_btn_pswd:
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
	class change_pswd extends AsyncTask<String, String, String>
	{

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ChangePswd.this);
			pDialog.setMessage("Saving the changes...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		@Override
		protected String doInBackground(String... params) 
		{
			uds.open();
			uds.change_pswd(params[0], params[1], params[2]);
			return null;
		}
		protected void onPostExecute(String file_url) 
		{
			Handler h=new Handler();
			h.postDelayed(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getApplicationContext(), "Changes Saved!!", Toast.LENGTH_SHORT).show();
					pDialog.dismiss();
					uds.close();
					finish();
			        ChangePswd.this.overridePendingTransition(R.anim.slide_from, R.anim.slide_out_right);
				}
			}, 5000);
		}
	} 
}
