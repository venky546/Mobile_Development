package com.saddahaq.media.utils;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.saddahaq.media.R;

public class DatePic extends Activity
{
	private TextView tvDisplayDate;
	private Button btnChangeDate;
	private int year;
	private int month;
	private int day;
	static final int DATE_DIALOG_ID = 999;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.date);
		tvDisplayDate	=	(TextView)findViewById(R.id.tvDate);
		btnChangeDate	=	(Button)findViewById(R.id.btnChangeDate);
		btnChangeDate.setOnClickListener(new OnClickListener() 
		{
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) 
			{
				showDialog(DATE_DIALOG_ID);
			}
		});
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
	}
	@Override
	protected Dialog onCreateDialog(int id) 
	{
		switch (id) 
		{
		case DATE_DIALOG_ID:
			DatePickerDialog dpd=new DatePickerDialog(this,new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) 
			{
				if(selectedYear > year)
	            {
	                tvDisplayDate.setText("Are you from future ??");
	            }
				else if(selectedYear > month || selectedDay > day)
				{
					tvDisplayDate.setText("Are you from future ??");
				}
	            else
	            {
	                tvDisplayDate.setText(new StringBuilder().append(selectedMonth + 1)
	                        .append("-").append(selectedDay).append("-").append(selectedYear)
	                        .append(" "));
	            }
			}
		
		}, year, month, day);
//			dpd.setCancelable(false);
//			dpd.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
//                    new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                	Log.e("MAG","cancel clicked....");
//                	final Calendar c = Calendar.getInstance();
//            		 int year1 = c.get(Calendar.YEAR);
//            		 int month1 = c.get(Calendar.MONTH);
//            		 int day1 = c.get(Calendar.DAY_OF_MONTH);
//                	tvDisplayDate.setText("present :"+new StringBuilder().append(month1)
//	                        .append("-").append(day1).append("-").append(year1)
//	                        .append(" "));	
//                 dialog.dismiss();
//                }
//            });
		return dpd;
		}
		return null;
	}
}
