package com.saddahaq.media.activity_settings;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.PluginState;
import android.widget.Button;
import android.widget.CheckBox;

import com.saddahaq.media.R;
import com.saddahaq.media.database.UserDataStore;

public class PersonalizeNews extends Activity implements OnClickListener
{
	Button politics,news,social,entertainment,sports,lifestyle,business,technology,health,science,education,legal;
	UserDataStore uds;
	String my_id;
	Dialog dialog;
	ArrayList<CheckBox> ls;
	Button btn;
	Display display ;
    int width;
    int height,count;
    Cursor c;
//    WebView wv;
	final boolean b[]={true,false,true,false,true};
	@SuppressLint("SetJavaScriptEnabled")
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_personalize_news);
		initialize_controls();
		politics.setOnClickListener(this);
		news.setOnClickListener(this);
		social.setOnClickListener(this);
		entertainment.setOnClickListener(this);
		sports.setOnClickListener(this);
		lifestyle.setOnClickListener(this);
		business.setOnClickListener(this);
		technology.setOnClickListener(this);
		health.setOnClickListener(this);
		science.setOnClickListener(this);
		education.setOnClickListener(this);
		legal.setOnClickListener(this);
//		String html = "";
//        html += "<html><body>";
//        html += "<iframe width=\"560\" height=\"315\" src=\"http://e.infogr.am/chart-88548503769\" frameborder=\"0\" allowfullscreen></iframe>";
//        html += "</body></html>";
//		wv.getSettings().setJavaScriptEnabled(true);
//		WebSettings settings = wv.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setAllowFileAccess(true);
////        if (Build.VERSION.SDK_INT > 7) {
////            settings.setPluginState(PluginState.ON);
////        } else {
////            ((Object) settings).setPluginsEnabled(true);
////        }
//		   wv.loadData(html, "text/html",null);
	}
	public void initialize_controls()
	{
		politics		=		(Button)findViewById(R.id.per_politics);
		news			=		(Button)findViewById(R.id.per_news);
		social			=		(Button)findViewById(R.id.per_social);
		entertainment	=		(Button)findViewById(R.id.per_entertainment);
		sports			=		(Button)findViewById(R.id.per_sports);
		lifestyle		=		(Button)findViewById(R.id.per_lifestyle);
		business		=		(Button)findViewById(R.id.per_business);
		technology		=		(Button)findViewById(R.id.per_technology);
		health			=		(Button)findViewById(R.id.per_health);
		science			=		(Button)findViewById(R.id.per_science);
		education		=		(Button)findViewById(R.id.per_education);
		legal			=		(Button)findViewById(R.id.per_legal);
		uds				=		new UserDataStore(getApplicationContext());
		my_id			=		 getIntent().getExtras().getString("email");
//		wv				=		(WebView)findViewById(R.id.webv);
		getApplicationContext();
		uds.open();
		count=uds.get_details_count(my_id, "politics");
		if(count!=0)
        {
        	politics.setText("Politics "+count);
        }
		count=uds.get_details_count(my_id, "news");
		if(count!=0)
        {
        	news.setText("News "+count);
        }
		count=uds.get_details_count(my_id, "social");
        if(count!=0)
        {
        	social.setText("Social-Civil Activity  "+count);
        }
		count=uds.get_details_count(my_id, "entertainment");
        if(count!=0)
        {
        	entertainment.setText("Entertainment  "+count);
        }
		count=uds.get_details_count(my_id, "sports");
		if(count!=0)
        {
        	sports.setText("Sports  "+count);
        }
		count=uds.get_details_count(my_id, "lifestyle");
		if(count!=0)
        {
        	lifestyle.setText("LifeStyle  "+count);
        }
		count=uds.get_details_count(my_id, "business");
		if(count!=0)
        {
        	business.setText("Business  "+count);
        }
		count=uds.get_details_count(my_id, "technology");
		if(count!=0)
        {
        	technology.setText("Technology  "+count);
        }
		count=uds.get_details_count(my_id, "health");
		if(count!=0)
        {
        	health.setText("Health  "+count);
        }
		count=uds.get_details_count(my_id, "science");
		if(count!=0)
        {
        	science.setText("Science  "+count);
        }
		uds.close();
	}
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.per_politics:
			count=0;
			dialog=new Dialog(PersonalizeNews.this);
			dialog.setTitle("Choose any!!");
            dialog.setContentView(R.layout.layout_politics);
            dialog.show();
            uds.open();
            c							=	uds.get_details(my_id,"politics");
            c.moveToFirst();
            ls							=	new ArrayList<CheckBox>();
            btn							=	(Button)dialog.findViewById(R.id.btn_politics);
            CheckBox 	parties			=	(CheckBox)dialog.findViewById(R.id.check_politics_parties);
            CheckBox 	politicians		=	(CheckBox)dialog.findViewById(R.id.check_politics_politicians);
            CheckBox 	policies		=	(CheckBox)dialog.findViewById(R.id.check_politics_policies);
            CheckBox 	national		=	(CheckBox)dialog.findViewById(R.id.check_politics_national);
            CheckBox 	international	=	(CheckBox)dialog.findViewById(R.id.check_politics_international);
            ls.add(parties);ls.add(politicians);ls.add(policies);ls.add(national);ls.add(international);
            count=0;
            for(int i=0;i<5;i++)
            {
            	if(c.getString(i+2).equals("true"))
            	{
            		ls.get(i).setChecked(true);
            		count++;
            	}
            	else
            	{
            		ls.get(i).setChecked(false);
            	}
            	
            }
            uds.close();
            Log.e("column",""+c.getString(1));
            btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) 
				{
					
					uds.open();
					uds.update_per_politics(my_id, ""+ls.get(0).isChecked(),""+ls.get(1).isChecked(),""+ls.get(2).isChecked(),""+ls.get(3).isChecked(),""+ls.get(4).isChecked());
					dialog.dismiss();
					uds.close();
					count=0;
					for(int i=0;i<5;i++)
		            {
		            	if(ls.get(i).isChecked())
		            	{
		            		count++;
		            	}
		            }
					if(count!=0)
			        {
			        	politics.setText("Politics "+count);
			        }
					else
					{
						politics.setText("Politics");
					}
				}
			});
			break;
		case R.id.per_news:
			count=0;
			dialog	=new Dialog(PersonalizeNews.this);
			dialog.setTitle("Choose any!!");
            dialog.setContentView(R.layout.layout_news);
            dialog.show();
            uds.open();
            c							=	uds.get_details(my_id, "news");
            c.moveToFirst();
            ls							=	new ArrayList<CheckBox>();
            btn							=	(Button)dialog.findViewById(R.id.btn_news);
            CheckBox 	weird			=	(CheckBox)dialog.findViewById(R.id.check_news_weird);
            CheckBox 	national1		=	(CheckBox)dialog.findViewById(R.id.check_news_natianal);
            CheckBox 	international1	=(CheckBox)dialog.findViewById(R.id.check_news_international);
            ls.add(weird);ls.add(national1);ls.add(international1);
            for(int i=0;i<3;i++)
            {
            	if(c.getString(i+2).equals("true"))
            	{
            		ls.get(i).setChecked(true);
            	}
            	else
            	{
            		ls.get(i).setChecked(false);
            	}
            	
            }
            uds.close();
            btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) 
				{
					uds.open();
					uds.update_per_news(my_id, ""+ls.get(0).isChecked(),""+ls.get(1).isChecked(),""+ls.get(2).isChecked());
					dialog.dismiss();
					uds.close();
					count=0;
					for(int i=0;i<3;i++)
		            {
		            	if(ls.get(i).isChecked())
		            	{
		            		count++;
		            	}
		            }
					if(count!=0)
			        {
			        	news.setText("News  "+count);
			        }
					else
					{
						news.setText("News");
					}
				}
			});
			break;
		case R.id.per_social:
			count=0;
			dialog	=new Dialog(PersonalizeNews.this);
			dialog.setTitle("Choose any!!");
            dialog.setContentView(R.layout.layout_social);
            dialog.show();
            uds.open();
            c							=	uds.get_details(my_id, "social");
            c.moveToFirst();
            ls							=	new ArrayList<CheckBox>();
            btn							=	(Button)dialog.findViewById(R.id.btn_social);
            CheckBox 	lo				=	(CheckBox)dialog.findViewById(R.id.check_social_local);
            CheckBox 	na				=	(CheckBox)dialog.findViewById(R.id.check_social_national);
            CheckBox 	in				=	(CheckBox)dialog.findViewById(R.id.check_social_international);
            ls.add(lo);ls.add(na);ls.add(in);
            for(int i=0;i<3;i++)
            {
            	if(c.getString(i+2).equals("true"))
            	{
            		ls.get(i).setChecked(true);
            	}
            	else
            	{
            		ls.get(i).setChecked(false);
            	}
            }
            uds.close();
            btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) 
				{
					uds.open();
					uds.update_per_social(my_id, ""+ls.get(0).isChecked(),""+ls.get(1).isChecked(),""+ls.get(2).isChecked());
					dialog.dismiss();
					uds.close();
					count=0;
					for(int i=0;i<3;i++)
		            {
		            	if(ls.get(i).isChecked())
		            	{
		            		count++;
		            	}
		            }
					if(count!=0)
			        {
			        	social.setText("Social-Civil Activity  "+count);
			        }
					else
					{
						social.setText("Social-Civil Activity");
					}
				}
			});
			break;
		case R.id.per_entertainment:
			count=0;
			dialog	=new Dialog(PersonalizeNews.this);
			dialog.setTitle("Choose any!!");
            dialog.setContentView(R.layout.layout_entertainment);
            dialog.show();
            uds.open();
            c								=	uds.get_details(my_id, "entertainment");
            c.moveToFirst();
            ls								=	new ArrayList<CheckBox>();
            btn								=	(Button)dialog.findViewById(R.id.btn_entertainment);
            CheckBox 	arts				=	(CheckBox)dialog.findViewById(R.id.check_enter_arts);
            CheckBox 	books				=	(CheckBox)dialog.findViewById(R.id.check_enter_books);
            CheckBox 	celebs				=	(CheckBox)dialog.findViewById(R.id.check_enter_celebrities);
            CheckBox 	movies				=	(CheckBox)dialog.findViewById(R.id.check_enter_movies);
            CheckBox 	tv					=	(CheckBox)dialog.findViewById(R.id.check_enter_tv);
            CheckBox 	theatre				=	(CheckBox)dialog.findViewById(R.id.check_enter_theatre);
            CheckBox 	bolly				=	(CheckBox)dialog.findViewById(R.id.check_enter_bollywood);
            CheckBox 	holly				=	(CheckBox)dialog.findViewById(R.id.check_enter_hollywood);
            CheckBox 	reg					=	(CheckBox)dialog.findViewById(R.id.check_enter_regional);
            ls.add(arts);ls.add(books);ls.add(celebs);ls.add(movies);ls.add(tv);ls.add(theatre);ls.add(bolly);ls.add(holly);ls.add(reg);
            for(int i=0;i<9;i++)
            {
            	if(c.getString(i+2).equals("true"))
            	{
            		ls.get(i).setChecked(true);
            	}
            	else
            	{
            		ls.get(i).setChecked(false);
            	}
            	
            }
            uds.close();
            btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) 
				{
					uds.open();
					uds.update_per_entertainment(my_id, ""+ls.get(0).isChecked(),""+ls.get(1).isChecked(),""+ls.get(2).isChecked(),""+ls.get(3).isChecked(),""+ls.get(4).isChecked(),""+ls.get(5).isChecked(),""+ls.get(6).isChecked(),""+ls.get(7).isChecked(),""+ls.get(8).isChecked());
					dialog.dismiss();
					uds.close();
					count=0;
					for(int i=0;i<9;i++)
		            {
		            	if(ls.get(i).isChecked())
		            	{
		            		count++;
		            	}
		            }
					if(count!=0)
			        {
			        	entertainment.setText("Entertainment  "+count);
			        }
					else
					{
						entertainment.setText("Entertainment");
					}
				}
			});
			break;
		case R.id.per_sports:
			count=0;
			dialog	=new Dialog(PersonalizeNews.this);
			dialog.setTitle("Choose any!!");
            dialog.setContentView(R.layout.layout_sports);
            dialog.show();
            uds.open();
            c								=	uds.get_details(my_id, "sports");
            c.moveToFirst();
            ls								=	new ArrayList<CheckBox>();
            btn								=	(Button)dialog.findViewById(R.id.btn_sports);
            CheckBox 	cricket				=	(CheckBox)dialog.findViewById(R.id.check_sports_cricket);
            CheckBox 	hockey				=	(CheckBox)dialog.findViewById(R.id.check_sports_hockey);
            CheckBox 	boxing				=	(CheckBox)dialog.findViewById(R.id.check_sports_boxing);
            CheckBox 	soccer				=	(CheckBox)dialog.findViewById(R.id.check_sports_soccer);
            CheckBox 	tennins					=	(CheckBox)dialog.findViewById(R.id.check_sports_tennis);
            CheckBox 	athe				=	(CheckBox)dialog.findViewById(R.id.check_sports_athletics);
            CheckBox 	badmin				=	(CheckBox)dialog.findViewById(R.id.check_sports_badminton);
            CheckBox 	olly				=	(CheckBox)dialog.findViewById(R.id.check_sports_olympics);
            CheckBox 	f1					=	(CheckBox)dialog.findViewById(R.id.check_sports_f1);
            CheckBox 	shoot				=	(CheckBox)dialog.findViewById(R.id.check_sports_shooting);
            CheckBox 	swim					=	(CheckBox)dialog.findViewById(R.id.check_sports_swimming);
            ls.add(cricket);ls.add(hockey);ls.add(boxing);ls.add(soccer);ls.add(tennins);ls.add(athe);
            ls.add(badmin);ls.add(olly);ls.add(f1);ls.add(shoot);ls.add(swim);
            for(int i=0;i<11;i++)
            {
            	if(c.getString(i+2).equals("true"))
            	{
            		ls.get(i).setChecked(true);
            	}
            	else
            	{
            		ls.get(i).setChecked(false);
            	}
            	
            }
            uds.close();
            btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) 
				{
					uds.open();
					uds.update_per_sports(my_id, ""+ls.get(0).isChecked(),""+ls.get(1).isChecked(),""+ls.get(2).isChecked(),""+ls.get(3).isChecked(),""+ls.get(4).isChecked(),""+ls.get(5).isChecked(),""+ls.get(6).isChecked(),""+ls.get(7).isChecked(),""+ls.get(8).isChecked(),""+ls.get(9).isChecked(),""+ls.get(10).isChecked());
					dialog.dismiss();
					uds.close();
					count=0;
					for(int i=0;i<11;i++)
		            {
		            	if(ls.get(i).isChecked())
		            	{
		            		count++;
		            	}
		            }
					if(count!=0)
			        {
			        	sports.setText("Sports  "+count);
			        }
					else
					{
						sports.setText("Sports");
					}
				}
			});
			break;
		case R.id.per_lifestyle:
			count=0;
			dialog	=new Dialog(PersonalizeNews.this);
			dialog.setTitle("Choose any!!");
            dialog.setContentView(R.layout.layout_lifestyle);
            dialog.show();
            uds.open();
            c								=	uds.get_details(my_id, "lifestyle");
            c.moveToFirst();
            ls								=	new ArrayList<CheckBox>();
            btn								=	(Button)dialog.findViewById(R.id.btn_lifestyle);
            CheckBox 	auto				=	(CheckBox)dialog.findViewById(R.id.check_ls_auto);
            CheckBox 	culte				=	(CheckBox)dialog.findViewById(R.id.check_ls_culte);
            CheckBox 	fandb				=	(CheckBox)dialog.findViewById(R.id.check_ls_fandb);
            CheckBox 	handg				=	(CheckBox)dialog.findViewById(R.id.check_ls_handg);
            CheckBox 	thea					=	(CheckBox)dialog.findViewById(R.id.check_ls_theatre);
            CheckBox 	travel				=	(CheckBox)dialog.findViewById(R.id.check_ls_travel);
            ls.add(auto);ls.add(culte);ls.add(fandb);ls.add(handg);ls.add(thea);ls.add(travel);
            for(int i=0;i<6;i++)
            {
            	if(c.getString(i+2).equals("true"))
            	{
            		count++;
            		ls.get(i).setChecked(true);
            	}
            	else
            	{
            		ls.get(i).setChecked(false);
            	}
            	
            }
            uds.close();
            btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) 
				{
					uds.open();
					uds.update_per_lifestyle(my_id, ""+ls.get(0).isChecked(),""+ls.get(1).isChecked(),""+ls.get(2).isChecked(),""+ls.get(3).isChecked(),""+ls.get(4).isChecked(),""+ls.get(5).isChecked());
					dialog.dismiss();
					uds.close();
					count=0;
					for(int i=0;i<6;i++)
		            {
		            	if(ls.get(i).isChecked())
		            	{
		            		count++;
		            	}
		            }
					if(count!=0)
			        {
			        	lifestyle.setText("LifeStyle  "+count);
			        }
					else
					{
						lifestyle.setText("LifeStyle");
					}
				}
			});
			break;
		case R.id.per_business:
			count=0;
			dialog	=new Dialog(PersonalizeNews.this);
			dialog.setTitle("Choose any!!");
            dialog.setContentView(R.layout.layout_business);
            dialog.show();
            uds.open();
            c								=	uds.get_details(my_id, "business");
            c.moveToFirst();
            ls								=	new ArrayList<CheckBox>();
            btn								=	(Button)dialog.findViewById(R.id.btn_business);
            CheckBox 	com					=	(CheckBox)dialog.findViewById(R.id.check_busi_company);
            CheckBox 	eco					=	(CheckBox)dialog.findViewById(R.id.check_busi_economy);
            CheckBox 	indu				=	(CheckBox)dialog.findViewById(R.id.check_busi_industry);
            CheckBox 	mark				=	(CheckBox)dialog.findViewById(R.id.check_busi_market);
            CheckBox 	peop				=	(CheckBox)dialog.findViewById(R.id.check_busi_people);
            ls.add(com);ls.add(eco);ls.add(indu);ls.add(mark);ls.add(peop);
            for(int i=0;i<5;i++)
            {
            	if(c.getString(i+2).equals("true"))
            	{
            		ls.get(i).setChecked(true);
            	}
            	else
            	{
            		ls.get(i).setChecked(false);
            	}
            	
            }
            uds.close();
            btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) 
				{
					uds.open();
					uds.update_per_business(my_id, ""+ls.get(0).isChecked(),""+ls.get(1).isChecked(),""+ls.get(2).isChecked(),""+ls.get(3).isChecked(),""+ls.get(4).isChecked());
					dialog.dismiss();
					uds.close();
					count=0;
					for(int i=0;i<5;i++)
		            {
		            	if(ls.get(i).isChecked())
		            	{
		            		count++;
		            	}
		            }
					if(count!=0)
			        {
			        	business.setText("Business  "+count);
			        }
					else
					{
						business.setText("Business");
					}
				}
				
			});
			break;
		case R.id.per_technology:
			count=0;
			dialog	=new Dialog(PersonalizeNews.this);
			dialog.setTitle("Choose any!!");
            dialog.setContentView(R.layout.layout_tech);
            dialog.show();
            ls								=	new ArrayList<CheckBox>();
            uds.open();
            c								=	uds.get_details(my_id, "technology");			
            c.moveToFirst();
            btn								=	(Button)dialog.findViewById(R.id.btn_tech);
            CheckBox 	internet			=	(CheckBox)dialog.findViewById(R.id.check_tech_internet);
            CheckBox 	computing			=	(CheckBox)dialog.findViewById(R.id.check_tech_computing);
            CheckBox 	pt					=	(CheckBox)dialog.findViewById(R.id.check_tech_pt);
            CheckBox 	et					=	(CheckBox)dialog.findViewById(R.id.check_tech_et);
            CheckBox 	vg					=	(CheckBox)dialog.findViewById(R.id.check_tech_vg);
            CheckBox 	ma					=	(CheckBox)dialog.findViewById(R.id.check_tech_mandap);
            CheckBox 	pc					=	(CheckBox)dialog.findViewById(R.id.check_tech_pclap);
            CheckBox 	wg					=	(CheckBox)dialog.findViewById(R.id.check_tech_wg);
            CheckBox 	camera				=	(CheckBox)dialog.findViewById(R.id.check_tech_cameras);
            ls.add(internet);ls.add(computing);ls.add(pt);ls.add(et);ls.add(vg);ls.add(ma);
            ls.add(pc);ls.add(wg);ls.add(camera);
            for(int i=0;i<9;i++)
            {
            	if(c.getString(i+2).equals("true"))
            	{
            		ls.get(i).setChecked(true);
            	}
            	else
            	{
            		ls.get(i).setChecked(false);
            	}
            	
            }
            uds.close();
            btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) 
				{
					uds.open();
					uds.update_per_tech(my_id, ""+ls.get(0).isChecked(),""+ls.get(1).isChecked(),""+ls.get(2).isChecked(),""+ls.get(3).isChecked(),""+ls.get(4).isChecked(),""+ls.get(5).isChecked(),""+ls.get(6).isChecked(),""+ls.get(7).isChecked(),""+ls.get(8).isChecked());
					dialog.dismiss();
					uds.close();
					count=0;
					for(int i=0;i<9;i++)
		            {
		            	if(ls.get(i).isChecked())
		            	{
		            		count++;
		            	}
		            }
					if(count!=0)
			        {
			        	technology.setText("Technology  "+count);
			        }
					else
					{
						technology.setText("Technology");
					}
				}
			});
			break;
		case R.id.per_health:
			count=0;
			dialog	=new Dialog(PersonalizeNews.this);
			dialog.setTitle("Choose any!!");
            dialog.setContentView(R.layout.layout_health);
            dialog.show();
            uds.open();
            c								=	uds.get_details(my_id, "health");
            c.moveToFirst();
            ls								=	new ArrayList<CheckBox>();
            btn								=	(Button)dialog.findViewById(R.id.btn_health);
            CheckBox 	nandf			=	(CheckBox)dialog.findViewById(R.id.check_health_nandf);
            CheckBox 	mandp			=	(CheckBox)dialog.findViewById(R.id.check_health_mandp);
            CheckBox 	dands					=	(CheckBox)dialog.findViewById(R.id.check_health_dands);
            ls.add(nandf);ls.add(mandp);ls.add(dands);
            for(int i=0;i<3;i++)
            {
            	if(c.getString(i+2).equals("true"))
            	{
            		ls.get(i).setChecked(true);
            	}
            	else
            	{
            		ls.get(i).setChecked(false);
            	}
            	
            }
            uds.close();
            btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) 
				{
					uds.open();
					uds.update_per_health(my_id, ""+ls.get(0).isChecked(),""+ls.get(1).isChecked(),""+ls.get(2).isChecked());
					dialog.dismiss();
					uds.close();
					count=0;
					for(int i=0;i<3;i++)
		            {
		            	if(ls.get(i).isChecked())
		            	{
		            		count++;
		            	}
		            }
					if(count!=0)
			        {
			        	health.setText("Health  "+count);
			        }
					else
					{
						health.setText("Health");
					}
				}
			});
			break;
		case R.id.per_science:
			count=0;
			dialog	=new Dialog(PersonalizeNews.this);
			dialog.setTitle("Choose any!!");
            dialog.setContentView(R.layout.layout_science);
            dialog.show();
            uds.open();
            c								=	uds.get_details(my_id, "science");
            c.moveToFirst();
            ls								=	new ArrayList<CheckBox>();
            btn								=	(Button)dialog.findViewById(R.id.btn_science);
            CheckBox 	space				=	(CheckBox)dialog.findViewById(R.id.check_science_space);
            CheckBox 	envi				=	(CheckBox)dialog.findViewById(R.id.check_science_environment);
            CheckBox 	geo					=	(CheckBox)dialog.findViewById(R.id.check_science_geography);
            ls.add(space);ls.add(envi);ls.add(geo);
            for(int i=0;i<3;i++)
            {
            	if(c.getString(i+2).equals("true"))
            	{
            		ls.get(i).setChecked(true);
            	}
            	else
            	{
            		ls.get(i).setChecked(false);
            	}
            	
            }
            uds.close();
            btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) 
				{
					uds.open();
					uds.update_per_science(my_id, ""+ls.get(0).isChecked(),""+ls.get(1).isChecked(),""+ls.get(2).isChecked());
					dialog.dismiss();
					uds.close();
					count=0;
					for(int i=0;i<3;i++)
		            {
		            	if(ls.get(i).isChecked())
		            	{
		            		count++;
		            	}
		            }
					if(count!=0)
			        {
			        	science.setText("Science  "+count);
			        }
					else
					{
						science.setText("Science");
					}
				}
			});
			break;
		case R.id.per_education:
			count=0;
			break;
		case R.id.per_legal:
			count=0;
			break;	
		}
	}
	public void onBackPressed()
	{
		super.onBackPressed();
		finish();
		this.overridePendingTransition(R.anim.slide_from, R.anim.slide_out_right);
	}
}