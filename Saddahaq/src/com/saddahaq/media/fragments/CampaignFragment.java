package com.saddahaq.media.fragments;
import java.util.Calendar;
import com.saddahaq.media.R;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class CampaignFragment extends Fragment {
	
	public CampaignFragment(){}
	private TextView tvDisplayDate;
	private Button btnChangeDate;
	static final int DATE_DIALOG_ID = 999;
	DatePickerDialog dpd;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
	{
        View rootView = inflater.inflate(R.layout.fragment_campaign, container, false);
        tvDisplayDate	=	(TextView)rootView.findViewById(R.id.tvDate);
		btnChangeDate	=	(Button)rootView.findViewById(R.id.btnChangeDate);
		btnChangeDate.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				showDatePicker();
			}
		});
        return rootView;
    }
	 private void showDatePicker() {
		  DatePickerFragment date = new DatePickerFragment();
		  Calendar calender = Calendar.getInstance();
		  Bundle args = new Bundle();
		  args.putInt("year", calender.get(Calendar.YEAR));
		  args.putInt("month", calender.get(Calendar.MONTH));
		  args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
		  date.setArguments(args);
		  date.setCallBack(ondate);
		  date.show(getFragmentManager(), "Date Picker");
		 }
		 OnDateSetListener ondate = new OnDateSetListener() {
		  @Override
		  public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
		   tvDisplayDate.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
			       + "-" + String.valueOf(year));
		  }
		 };
}