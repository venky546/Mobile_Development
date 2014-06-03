package com.saddahaq.media.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saddahaq.media.R;
import com.saddahaq.media.database.UserDataStore;

public class NotificationFragment extends Fragment 
{
	public UserDataStore uds;
	public NotificationFragment(){}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        Log.e("msg", "oncreateview");
        Bundle bundle=getArguments(); 
        String my=bundle.getString("email");
        Log.e("EMail from activity->notifi", ""+my);
        uds	=	new UserDataStore(getActivity().getApplicationContext());
        uds.open();
        Cursor c=uds.getdetails_of_user(my);
        c.moveToFirst();
        Log.e("MSG","cursor count :: "+c.getCount());
        Log.e("MSG", "User Details :: "+c.getString(1)+"\nemail "+c.getString(5));
        uds.close();
        return rootView;
    }
}