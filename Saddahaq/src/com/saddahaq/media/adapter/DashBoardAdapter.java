package com.saddahaq.media.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.saddahaq.media.R;

public class DashBoardAdapter extends BaseAdapter
{
	Context con;
	ArrayList<String> titles=new ArrayList<String>();
	ArrayList<String> views=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();
	ArrayList<String> voteups=new ArrayList<String>();
	public DashBoardAdapter(Context con) 
	{
		this.con	=	con;
		titles.add("Half of Modi cabinet are \ngraduates.NDA govt scores over \nUPA govt");
		titles.add("Despite all efforts, Team Modi \nstill older than the outgoing \nTeam Manmohan");
		titles.add("Who will be hostess at 7, \nRCR functionsonce Modi moves \nto PM residence?");
		titles.add("Half of Modi cabinet are \ngraduates.NDA govt scores over \nUPA govt");
		titles.add("Despite all efforts, Team Modi \nstill older than the outgoing \nTeam Manmohan");
		titles.add("Who will be hostess at 7, \nRCR functionsonce Modi moves \nto PM residence?");
		views.add("100");views.add("200");views.add("300");views.add("400");views.add("500");views.add("600");
		comments.add("120");comments.add("560");comments.add("460");comments.add("640");comments.add("555");comments.add("111");
		voteups.add("205");voteups.add("300");voteups.add("300");voteups.add("100");voteups.add("500");voteups.add("600");
	}
	@Override
	public int getCount() 
	{
		return titles.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		convertView				=	LayoutInflater.from(con).inflate(R.layout.dash_article_item, null);
		TextView 		ti		=	(TextView)convertView.findViewById(R.id.dash_arti_title);
		TextView 		vo		=	(TextView)convertView.findViewById(R.id.dash_arti_voteups);
		TextView 		co		=	(TextView)convertView.findViewById(R.id.dash_arti_comments);
		TextView 		vi		=	(TextView)convertView.findViewById(R.id.dash_arti_views);
		ti.setText(titles.get(position));
		vo.setText(voteups.get(position)+"votes");
		co.setText(comments.get(position)+"comments");
		vi.setText(views.get(position)+"views");
		return convertView;
	}

}
