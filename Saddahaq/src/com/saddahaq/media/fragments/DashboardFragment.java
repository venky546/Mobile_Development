package com.saddahaq.media.fragments;
import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.saddahaq.media.R;
import com.saddahaq.media.adapter.DashBoardAdapter;
import com.saddahaq.media.database.UserDataStore;

public class DashboardFragment extends Fragment implements OnClickListener
{
	
	public DashboardFragment(){}
	UserDataStore uds;
	Cursor c,c1;
	String my;
	Button ly,lm,lw,comments,voteups,views;
	ListView ls;
	DashBoardAdapter dba;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initialize_controls(rootView);
        ly.setOnClickListener(this);
        lm.setOnClickListener(this);
        lw.setOnClickListener(this);
        comments.setOnClickListener(this);
        views.setOnClickListener(this);
        voteups.setOnClickListener(this);
        ls.setAdapter(dba);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return rootView;
    }
	public void initialize_controls(View rootView)
	{
		Bundle 		bundle			=	getArguments(); 
        my							=	bundle.getString("email");
        ly							=	(Button)rootView.findViewById(R.id.lastyear);
        lm							=	(Button)rootView.findViewById(R.id.lastmonth);
        lw							=	(Button)rootView.findViewById(R.id.lastweek);
        comments					=	(Button)rootView.findViewById(R.id.comments);
        views						=	(Button)rootView.findViewById(R.id.views);
        voteups						=	(Button)rootView.findViewById(R.id.voteups);
        ls							=	(ListView)rootView.findViewById(R.id.lv_dashboard);
        dba							=	new DashBoardAdapter(getActivity().getApplicationContext());
	}
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.lastyear:
			ly.setBackgroundColor(Color.parseColor("#E33331"));
			lm.setBackgroundColor(Color.parseColor("#E95045"));
			lw.setBackgroundColor(Color.parseColor("#E95045"));
			break;
		case R.id.lastmonth:
			ly.setBackgroundColor(Color.parseColor("#E95045"));
			lm.setBackgroundColor(Color.parseColor("#E33331"));
			lw.setBackgroundColor(Color.parseColor("#E95045"));
			break;
		case R.id.lastweek:
			ly.setBackgroundColor(Color.parseColor("#E95045"));
			lm.setBackgroundColor(Color.parseColor("#E95045"));
			lw.setBackgroundColor(Color.parseColor("#E33331"));
			break;
		case R.id.views:
			views.setBackgroundColor(Color.parseColor("#D8D8D8"));
			comments.setBackgroundColor(Color.parseColor("#EAEAEA"));
			voteups.setBackgroundColor(Color.parseColor("#EAEAEA"));
			break;
		case R.id.comments:
			views.setBackgroundColor(Color.parseColor("#EAEAEA"));
			comments.setBackgroundColor(Color.parseColor("#D8D8D8"));
			voteups.setBackgroundColor(Color.parseColor("#EAEAEA"));
			break;
		case R.id.voteups:
			views.setBackgroundColor(Color.parseColor("#EAEAEA"));
			comments.setBackgroundColor(Color.parseColor("#EAEAEA"));
			voteups.setBackgroundColor(Color.parseColor("#D8D8D8"));
			break;	
			
		}

	}
}