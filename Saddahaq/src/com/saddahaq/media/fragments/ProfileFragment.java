package com.saddahaq.media.fragments;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saddahaq.media.R;
import com.saddahaq.media.activity_settings.Apps;
import com.saddahaq.media.adapter.ArticleAdapter;
import com.saddahaq.media.adapter.EventAdapter;
import com.saddahaq.media.adapter.PetitionAdapter;
import com.saddahaq.media.database.UserDataStore;

public class ProfileFragment extends Fragment implements OnLongClickListener,OnClickListener
{
	public ProfileFragment(){}
	ImageView profile_pic;
	TextView profile_name,profile_location,profile_desc;
	Button btn_following,btn_followers,btn_articles,btn_events,btn_petetions,btn_apps;
	UserDataStore uds;
	Cursor c,c1;
	String my;
	RelativeLayout rl;
	static String picturePath;
	int IMAGE_PICKER_SELECT=1;
	ListView lv;
	ArticleAdapter aad;
	PetitionAdapter pad;
	EventAdapter    ead;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
        View rootView 			= 	inflater.inflate(R.layout.fragment_profile, container, false);
        initialize_controls(rootView);
        Log.e("email from activity->profile", ""+my);
        setdata();
        profile_pic.setOnLongClickListener(this);
        btn_articles.setOnClickListener(this);
        btn_apps.setOnClickListener(this);
//        btn_events.setOnClickListener(this);
//        btn_petetions.setOnClickListener(this);
        btn_followers.setOnClickListener(this);
        btn_following.setOnClickListener(this);
        btn_articles.setBackgroundColor(Color.parseColor("#E33331"));
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return rootView;
    }
	public void initialize_controls(View rootView)
	{
        profile_pic				=	(ImageView)rootView.findViewById(R.id.profile_pic);
        profile_name			=	(TextView)rootView.findViewById(R.id.profile_name);
        profile_desc			=	(TextView)rootView.findViewById(R.id.profile_desc);
        btn_articles			=	(Button)rootView.findViewById(R.id.btn_articles);
        btn_following			=	(Button)rootView.findViewById(R.id.btn_followers);
        btn_followers			=	(Button)rootView.findViewById(R.id.btn_following);
        btn_apps				=	(Button)rootView.findViewById(R.id.btn_apps);
//        btn_events				=	(Button)rootView.findViewById(R.id.btn_events);
//        btn_petetions			=	(Button)rootView.findViewById(R.id.btn_petetions);
        lv						=	(ListView)rootView.findViewById(R.id.article_listview);
        uds						=	new UserDataStore(getActivity().getApplicationContext());
        Bundle 		bundle		=	getArguments(); 
        my						=	bundle.getString("email");
        rl						=	(RelativeLayout)rootView.findViewById(R.id.rl);
	}
	public void setdata()
	{
		uds.open();			
        c=uds.getdetails_of_user(my);
        c.moveToFirst();
        btn_followers.setText("25\nFollowers");
        btn_following.setText("125\nFollowing");
        profile_name.setText(c.getString(1).toString());
        byte[] outImage=c.getBlob(10);
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        profile_pic.setImageBitmap(theImage);
        aad	=	new ArticleAdapter(getActivity().getApplicationContext());
        lv.setAdapter(aad);
        uds.close();
	}
	@Override
	public boolean onLongClick(View v) 
	{
		
		switch(v.getId())
		{
		case R.id.profile_pic:
			Log.e("LOngClickListener", "Pic");
			select_image();
			break;
		}
		return false;
	}
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btn_articles:
			btn_articles.setBackgroundColor(Color.parseColor("#E33331"));
			btn_apps.setBackgroundColor(Color.parseColor("#E95045"));
			aad	=	new ArticleAdapter(getActivity().getApplicationContext());
	        lv.setAdapter(aad);
			break;
		case R.id.btn_apps:
			Intent it=new Intent(getActivity().getApplicationContext(),Apps.class);
			it.putExtras(getArguments());
			startActivity(it);
			getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			break;
		case R.id.btn_followers:
			btn_followers.setBackgroundColor(Color.parseColor("#E95045"));
			btn_following.setBackgroundColor(Color.parseColor("#E33331"));
			break;
		case R.id.btn_following:
			btn_following.setBackgroundColor(Color.parseColor("#E95045"));
			btn_followers.setBackgroundColor(Color.parseColor("#E33331"));
			break;	
		}
	}
	public void select_image()
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		alertDialogBuilder.setTitle("Edit Profile pic");
		alertDialogBuilder
			.setMessage("Choose any..")
			.setPositiveButton("Gallery",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) 
				{
					Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); 
					startActivityForResult(i, IMAGE_PICKER_SELECT);
				}
			  })
			.setNegativeButton("Camera",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
			        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			        startActivityForResult(intent, 4);
				}
			});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{ 
		if (requestCode == IMAGE_PICKER_SELECT && resultCode == Activity.RESULT_OK) 
		{
			Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(
                               selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
            profile_pic.setImageBitmap(yourSelectedImage);
            saveimage(yourSelectedImage);
		}
		else if (requestCode == 4 && resultCode== Activity.RESULT_OK && data != null)
		{
            Bundle extras = data.getExtras();
            Bitmap bitMap = (Bitmap) extras.get("data");
            profile_pic.setImageBitmap(bitMap);
            saveimage(bitMap);
		}    
	}
	public void saveimage(Bitmap bm)
	{
		uds.open();
		ByteArrayOutputStream boas=new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, boas);
		byte b[]=boas.toByteArray();
		uds.update_pic(b, my);
		uds.close();
	}
	
}