package com.saddahaq.media;
import java.util.List;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.android.Facebook;
import com.facebook.model.GraphUser;
import com.saddahaq.media.R;
import com.saddahaq.media.utils.FbTrail;
public class MainActivity extends Activity {
	Button click;
	Facebook fb;
	List<String> permitions;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		click=(Button)findViewById(R.id.click);
				Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		click.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		click.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.login_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                Button signin=(Button)dialog.findViewById(R.id.signinbtn);
                signin.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent it=new Intent(MainActivity.this,SignIn.class);
						startActivity(it);
						finish();
					}
				});
                Button signup=(Button)dialog.findViewById(R.id.signupbtn);
                signup.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
					Intent it=new Intent(MainActivity.this,Signup.class);
					startActivity(it);
					finish();
					}
				});
//				startActivity(new Intent(getApplicationContext(),FbTrail.class));
			}
		});

	}
}
