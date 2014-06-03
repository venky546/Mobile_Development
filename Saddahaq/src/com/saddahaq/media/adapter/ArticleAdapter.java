package com.saddahaq.media.adapter;

import java.util.ArrayList;

import com.saddahaq.media.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArticleAdapter extends BaseAdapter
{
	Context con;
	ArrayList<String> titles = new ArrayList<String>();
	ArrayList<String> authors = new ArrayList<String>();
	ArrayList<String> days = new ArrayList<String>();
	public ArticleAdapter(Context con)
	{
		this.con=con;
		titles.add("Half of Modi cabinet are graduates -- \nNDA govt scores over UPA govt");
		titles.add("Despite all efforts, Team Modi still \nolder than the outgoing Team Manmohan");
		titles.add("Who will be hostess at 7, RCR functions\nonce Modi moves to PM residence?");
		titles.add("Half of Modi cabinet are graduates -- \nNDA govt scores over UPA govt");
		titles.add("Despite all efforts, Team Modi still \nolder than the outgoing Team Manmohan");
		titles.add("Who will be hostess at 7, RCR functions\nonce Modi moves to PM residence?");
		authors.add("Piyush Rai");
		authors.add("Subhabrata Dasgupta");
		authors.add("Akshaya Nath");
		authors.add("Piyush Rai");
		authors.add("Subhabrata Dasgupta");
		authors.add("Akshaya Nath");
		days.add("1 day ago");
		days.add("2 days ago");
		days.add("3 days ago");
		days.add("1 day ago");
		days.add("2 days ago");
		days.add("3 days ago");
	}
	@Override
	public int getCount() 
	{
		return titles.size();
	}
	@Override
	public Object getItem(int arg0) 
	{
		return arg0;
	}
	@Override
	public long getItemId(int arg0) 
	{
		return arg0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		convertView				=	LayoutInflater.from(con).inflate(R.layout.article_list, null);
		TextView 	title		=	(TextView)convertView.findViewById(R.id.article_title);
		TextView 	author		=	(TextView)convertView.findViewById(R.id.article_author);
		TextView 	time		=	(TextView)convertView.findViewById(R.id.article_time);
		title.setText(titles.get(position).toString());
		author.setText(authors.get(position).toString());
		time.setText(days.get(position).toString());
		return convertView;
	}
}
