package com.saddahaq.media.fragments;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Request.GraphUserCallback;
import com.facebook.model.GraphUser;
import com.saddahaq.media.R;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SignoutFragment extends Fragment {
	
	public SignoutFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_signout, container, false);
        Request.newMeRequest(Session.getActiveSession(), new GraphUserCallback() {
			@Override
			public void onCompleted(GraphUser user, Response response) 
			{
				if(user!=null)
				{
					JSONObject jb=user.getInnerJSONObject();
					  try {
								String name=jb.getString("name");
								Log.e("Notification->Name", name);
//								Toast.makeText(getActivity(), "Facebook->notification Fragment\n ::\nName :"+name, Toast.LENGTH_SHORT).show();
							} catch (JSONException e) {
								e.printStackTrace();
							}
				}	
			}
		}).executeAsync();
        return rootView;
    }
}