package com.saddahaq.media.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.saddahaq.media.R;
import com.saddahaq.media.activity_settings.DisplayCategories;

public class CategoriesFragment extends Fragment implements OnClickListener{
	
	public CategoriesFragment(){}
	String my;
	Button cate_science,cate_legal,cate_education,cate_health,cate_business,cate_lifestyle,cate_hi,cate_entertainment,cate_sports,cate_tech,cate_politics;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);
        initialize_controls(rootView);
//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        cate_science.setOnClickListener(this);
        cate_legal.setOnClickListener(this);
        cate_education.setOnClickListener(this);
        cate_health.setOnClickListener(this);
        cate_business.setOnClickListener(this);
        cate_lifestyle.setOnClickListener(this);
        cate_hi.setOnClickListener(this);
        cate_entertainment.setOnClickListener(this);
        cate_sports.setOnClickListener(this);
        cate_tech.setOnClickListener(this);
        cate_politics.setOnClickListener(this);
        
        return rootView;
    }
	public void initialize_controls(View rootView)
	{
		Bundle 		bundle			=	getArguments(); 
        my							=	bundle.getString("email");
        cate_science				=	(Button)rootView.findViewById(R.id.cate_science);
        cate_legal					=	(Button)rootView.findViewById(R.id.cate_legal);
        cate_education				=	(Button)rootView.findViewById(R.id.cate_education);
        cate_health					=	(Button)rootView.findViewById(R.id.cate_health);
        cate_business				=	(Button)rootView.findViewById(R.id.cate_business);
        cate_lifestyle				=	(Button)rootView.findViewById(R.id.cate_lifestyle);
        cate_hi						=	(Button)rootView.findViewById(R.id.cate_hi);
        cate_entertainment			=	(Button)rootView.findViewById(R.id.cate_entertainment);
        cate_sports					=	(Button)rootView.findViewById(R.id.cate_sports);
        cate_tech					=	(Button)rootView.findViewById(R.id.cate_tech);
        cate_politics				=	(Button)rootView.findViewById(R.id.cate_politics);
	}
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
			default:
				Intent it	=	new Intent(getActivity().getApplicationContext(),DisplayCategories.class);
				it.putExtras(getArguments());
				startActivity(it);
				getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				break;
		}
	}
}