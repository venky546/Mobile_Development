package com.saddahaq.media.activity_settings;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.saddahaq.media.R;
import com.saddahaq.media.adapter.ArticleAdapter;
public class DisplayCategories extends Activity implements OnScrollListener
{
	ListView lv;
	ArticleAdapter aad;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_categories);
		lv				=		(ListView)findViewById(R.id.lv);
		aad				=		new ArticleAdapter(getApplicationContext());
		lv.setAdapter(aad);
		lv.setOnScrollListener(this);
		View footer = ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer, null, false);
		lv.addFooterView(footer);
	}
	public void onBackPressed()
	{
		super.onBackPressed();
		finish();
		this.overridePendingTransition(R.anim.slide_from, R.anim.slide_out_right);
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) 
	{
		
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) 
	{
		
	}
}
