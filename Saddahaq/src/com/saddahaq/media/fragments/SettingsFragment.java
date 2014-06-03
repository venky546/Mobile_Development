package com.saddahaq.media.fragments;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.saddahaq.media.R;
import com.saddahaq.media.activity_settings.ChangePswd;
import com.saddahaq.media.activity_settings.EditProfile;
import com.saddahaq.media.activity_settings.EmailNotifications;
import com.saddahaq.media.activity_settings.PersonalizeNews;

public class SettingsFragment extends Fragment implements OnClickListener
{
	public SettingsFragment(){}
	View rootView;
	Intent it;
	Button change_pswd,change_profile,change_email_notifications,change_personalize_news;
	String my;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		rootView = inflater.inflate(R.layout.fragment_settings, container, false);
		change_pswd					=		(Button)rootView.findViewById(R.id.change_pswd);
		change_profile				=		(Button)rootView.findViewById(R.id.change_profile);
		change_email_notifications	=		(Button)rootView.findViewById(R.id.change_email_notifications);
		change_personalize_news		=		(Button)rootView.findViewById(R.id.change_personalize_news);
		Bundle bundle=getArguments(); 
        my=bundle.getString("email");
        Log.e("email from activity->Settings", ""+my);
		change_pswd.setOnClickListener(this);
		change_profile.setOnClickListener(this);
		change_email_notifications.setOnClickListener(this);
		change_personalize_news.setOnClickListener(this);
        return rootView;
    }
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.change_pswd:
			it=new Intent(getActivity(),ChangePswd.class);
			it.putExtras(getArguments());
			startActivity(it);
			getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			break;
		case R.id.change_profile:
			it=new Intent(getActivity(),EditProfile.class);
			it.putExtras(getArguments());
			startActivity(it);
			getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			break;
		case R.id.change_email_notifications:
			it=new Intent(getActivity(),EmailNotifications.class);
			it.putExtras(getArguments());
			startActivity(it);
			getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			break;
		case R.id.change_personalize_news:
			it=new Intent(getActivity(),PersonalizeNews.class);
			it.putExtras(getArguments());
			startActivity(it);
			getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			break;
		}
	}
}