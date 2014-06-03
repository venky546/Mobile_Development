package com.saddahaq.media.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.saddahaq.media.R;

public class PetitionAdapter extends BaseAdapter
{
	ArrayList<String> titles=new ArrayList<String>();
	ArrayList<String> votes=new ArrayList<String>();
	Context con;
	public PetitionAdapter(Context con) 
	{
		this.con=con;
		titles.add("Shri Pranab Mukherjee:\nWithdraw Rustication of Mohan Dharavath,\nSatish Nainala and Subhash Kumar");
		titles.add("Ask Narendra Modi, Arvind\nKejriwal and Rahul Gandhi to agree\nto a national debate before the\nelections.");
		titles.add("Make Sure Our Armed Forces Personnel Are Able to Vote");
		titles.add("Shri Pranab Mukherjee:\nWithdraw Rustication of Mohan Dharavath,\nSatish Nainala and Subhash Kumar");
		titles.add("Ask Narendra Modi, Arvind\nKejriwal and Rahul Gandhi to agree\nto a national debate before the\nelections.");
		titles.add("Make Sure Our Armed Forces Personnel Are Able to Vote");
		votes.add("1000");
		votes.add("2000");
		votes.add("3000");
		votes.add("1000");
		votes.add("2000");
		votes.add("3000");
	}
	@Override
	public int getCount() {
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
		convertView				=	LayoutInflater.from(con).inflate(R.layout.petetion_list, null);
		TextView 	title		=	(TextView)convertView.findViewById(R.id.petetion_title);
		Button	 	vote		=	(Button)convertView.findViewById(R.id.petetion_votes);
		title.setText(titles.get(position).toString());
		vote.setText(votes.get(position).toString());
		return convertView;
	}
}
