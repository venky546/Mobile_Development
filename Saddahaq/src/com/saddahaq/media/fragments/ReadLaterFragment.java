package com.saddahaq.media.fragments;
import android.app.Fragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ListView;
import android.widget.TextView;
import com.saddahaq.media.R;
import com.saddahaq.media.adapter.ArticleAdapter;

public class ReadLaterFragment extends Fragment implements MultiChoiceModeListener
{
	TextView tv;
	ListView lv;
	ArticleAdapter aad;
	public ReadLaterFragment(){}
	String my;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
         View rootView = inflater.inflate(R.layout.fragment_readlater, container, false);
         initalize_controls(rootView);
         tv.setText("List of articles");
         lv.setAdapter(aad);
         lv.setMultiChoiceModeListener(this);
         lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
         
        return rootView;
    }
	public void initalize_controls(View rootView)
	{
		tv						=	(TextView)rootView.findViewById(R.id.rl_list_desc);
		lv						=	(ListView)rootView.findViewById(R.id.rdlater_list);
		aad						=	new ArticleAdapter(getActivity().getApplicationContext());
		Bundle 		bundle		=	getArguments(); 
        my						=	bundle.getString("email");
	}
	@Override
	public boolean onActionItemClicked(ActionMode arg0, MenuItem arg1) 
	{
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onCreateActionMode(ActionMode arg0, Menu arg1) 
	{
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onDestroyActionMode(ActionMode arg0) 
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) 
	{
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onItemCheckedStateChanged(ActionMode arg0, int arg1, long arg2,boolean arg3) 
	{
		// TODO Auto-generated method stub
		
	}
}