package com.saddahaq.media.adapter;

import java.util.ArrayList;

import com.saddahaq.media.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class EventAdapter extends BaseAdapter
{
	Context con;
	ArrayList<String> month=new ArrayList<String>();
	ArrayList<String> date=new ArrayList<String>();
	ArrayList<String> name=new ArrayList<String>();
	ArrayList<String> desc=new ArrayList<String>();
	ArrayList<String> loca=new ArrayList<String>();
	public EventAdapter(Context con)
	{
		this.con		=		con;
		month.add("JAN");month.add("FEB");month.add("MAR");month.add("APR");month.add("MAY");month.add("JUNE");
		date.add("10");date.add("20");date.add("30");date.add("10");date.add("21");date.add("32");
		name.add("Abcdefgh");name.add("Ijklmnop");name.add("Qrstuvwx");name.add("YZsaknk");name.add("Venkatesh");name.add("Saddahaq");
		desc.add("Weekend Ventures Hyderabad");desc.add("Weekend Hack II Android");desc.add("Angle Hack Hyderabad");desc.add("How to Hack");desc.add("How to prevent Hacking");desc.add("FIFA Meeting... ");
		loca.add("Hyderabad");loca.add("Gachibowli");loca.add("Chennai");loca.add("Secundrabad");loca.add("Hyderabad");loca.add("Khammam");
	}
	@Override
	public int getCount() 
	{
		return month.size();
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
		convertView				=	LayoutInflater.from(con).inflate(R.layout.event_list, null);
		Button	 	da		=	(Button)convertView.findViewById(R.id.btn_pet_date);
		Button	 	mo		=	(Button)convertView.findViewById(R.id.btn_pet_month);
		TextView	na		=	(TextView)convertView.findViewById(R.id.txt_pet_name);
		TextView	de		=	(TextView)convertView.findViewById(R.id.txt_pet_desc);
		TextView	lo		=	(TextView)convertView.findViewById(R.id.txt_pet_loca);
		da.setText(date.get(position));
		mo.setText(month.get(position));
		na.setText(name.get(position));
		de.setText(desc.get(position));
		lo.setText(loca.get(position));
		return convertView;
	}

}
