package com.saddahaq.media.activity_settings;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.saddahaq.media.R;
import com.saddahaq.media.adapter.EventAdapter;
import com.saddahaq.media.adapter.PetitionAdapter;

public class Apps extends Activity implements OnClickListener
{
	Button events,petetions;
	ListView lv;
	EventAdapter ea;
	PetitionAdapter pa;
	String my_id;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_apps);
		initialize_controls();
		Log.e("emial",""+my_id);
		lv.setAdapter(ea);
		events.setOnClickListener(this);
		petetions.setOnClickListener(this);
		events.setBackgroundColor(Color.parseColor("#E33331"));
//		ActionBar actionBar = getActionBar();
//	    actionBar.setDisplayHomeAsUpEnabled(true);
	}
	public void initialize_controls()
	{
		events		=	(Button)findViewById(R.id.btn_events);
		petetions	=	(Button)findViewById(R.id.btn_petetions);
		lv			=	(ListView)findViewById(R.id.apps_listview);
		ea			=	new EventAdapter(getApplicationContext());
		pa			=	new PetitionAdapter(getApplicationContext());
		Intent 	it	=	getIntent();
		my_id		=	it.getExtras().getString("email");
	}
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.btn_petetions:
		petetions.setBackgroundColor(Color.parseColor("#E33331"));
		events.setBackgroundColor(Color.parseColor("#E95045"));
//		pa	=	new PetitionAdapter(getApplicationContext());
		lv.setAdapter(pa);
		break;
	case R.id.btn_events:
		events.setBackgroundColor(Color.parseColor("#E33331"));
		petetions.setBackgroundColor(Color.parseColor("#E95045"));
//		ea	=	new EventAdapter(getApplicationContext());
		lv.setAdapter(ea);
		break;
		}
	}
	public void onBackPressed()
	{
		super.onBackPressed();
		finish();
		this.overridePendingTransition(R.anim.slide_from, R.anim.slide_out_right);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
}
