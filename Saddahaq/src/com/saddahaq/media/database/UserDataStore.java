package com.saddahaq.media.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.saddahaq.media.utils.User;
public class UserDataStore 
{
	private SQLiteDatabase database;
	private MySqliteHelper dbHelper;
	private String TAG="UserDataStore";
	private String columns[]={"uid",MySqliteHelper.column_name,MySqliteHelper.column_fname,MySqliteHelper.column_lname,MySqliteHelper.column_uname,MySqliteHelper.column_email,MySqliteHelper.column_password,MySqliteHelper.column_location,MySqliteHelper.column_dob,MySqliteHelper.column_mobile};
	private String columns_image[]={"sid",MySqliteHelper.column_email,MySqliteHelper.column_flag,MySqliteHelper.column_path};
	private String columns_notifications[]={MySqliteHelper.column_notifi_nid,MySqliteHelper.column_notifi_email,MySqliteHelper.column_notifi_approve,MySqliteHelper.column_notifi_comment,MySqliteHelper.column_notifi_respond,MySqliteHelper.column_notifi_mention,MySqliteHelper.column_notifi_vote,MySqliteHelper.column_notifi_follow,MySqliteHelper.column_notifi_rsvp,MySqliteHelper.column_notifi_news,MySqliteHelper.column_notifi_interest};
	public UserDataStore(Context context)
	{
		dbHelper	=	new MySqliteHelper(context);
	}
	public void open()
	{
		database	=	dbHelper.getWritableDatabase();
		 Log.e(TAG,"database opened");
	}
	public void close()
	{
		dbHelper.close();
		 Log.e(TAG,"database closed");
	}
	public void create_user(User u)
	{
	try 
  	  {
			  ContentValues cv=new ContentValues();
			  cv.put(MySqliteHelper.column_name, 		u.getName());
			  cv.put(MySqliteHelper.column_fname, 		u.getFname());	
			  cv.put(MySqliteHelper.column_lname, 		u.getLname());
			  cv.put(MySqliteHelper.column_uname, 		u.getUname());
			  cv.put(MySqliteHelper.column_email, 		u.getEmail());
			  cv.put(MySqliteHelper.column_password,	u.getPassword());
			  cv.put(MySqliteHelper.column_location, 	u.getLocation());
			  cv.put(MySqliteHelper.column_dob, 		u.getDob());
			  cv.put(MySqliteHelper.column_mobile, 		u.getMobile());
			  cv.put(MySqliteHelper.column_pic, 		u.getProfile_pic());
			  database.insert(MySqliteHelper.table_user, null, cv);
			  Log.e(TAG,"User added!!");
			  Log.e(TAG, "Name : "+u.getName()+"\nFname : "+u.getFname()+"\nLname :"+u.getLname()+"\nUname : "+u.getUname()+"\nEmail :"+u.getEmail()+"\nPassword :"+u.getPassword()+"\nLocation :"+u.getLocation()+"\nDob : "+u.getDob()+"\nMobile : "+u.getMobile());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void create_email_notifications(String status,String email,String approve,String comment,String respond,String mention,String vote,String follow,String rsvp,String news,String interest,String approve_check,String comment_check,String respond_check,String mention_check,String vote_check,String follow_check,String rsvp_check,String news_check,String interest_check)
	{
		  try 
	  	  {
			  ContentValues cv=new ContentValues();
			  cv.put(MySqliteHelper.column_notifi_status, 		status);
			  cv.put(MySqliteHelper.column_notifi_email, 		email);
			  cv.put(MySqliteHelper.column_notifi_approve, 		approve);	
			  cv.put(MySqliteHelper.column_notifi_comment, 		comment);
			  cv.put(MySqliteHelper.column_notifi_respond, 		respond);
			  cv.put(MySqliteHelper.column_notifi_mention, 		mention);
			  cv.put(MySqliteHelper.column_notifi_vote, 		vote);
			  cv.put(MySqliteHelper.column_notifi_follow, 		follow);
			  cv.put(MySqliteHelper.column_notifi_rsvp, 		rsvp);
			  cv.put(MySqliteHelper.column_notifi_news, 		news);
			  cv.put(MySqliteHelper.column_notifi_interest, 	interest);
			  cv.put(MySqliteHelper.column_check_approve, 		approve_check);	
			  cv.put(MySqliteHelper.column_check_comment, 		comment_check);
			  cv.put(MySqliteHelper.column_check_respond, 		respond_check);
			  cv.put(MySqliteHelper.column_check_mention, 		mention_check);
			  cv.put(MySqliteHelper.column_check_vote, 		vote_check);
			  cv.put(MySqliteHelper.column_check_follow, 		follow_check);
			  cv.put(MySqliteHelper.column_check_rsvp, 		rsvp_check);
			  cv.put(MySqliteHelper.column_check_news, 		news_check);
			  cv.put(MySqliteHelper.column_check_interest, 	interest_check);
			  database.insert(MySqliteHelper.table_notification, null, cv);
			  Log.e(TAG,"Notifications added!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public void create_check(String status,String email,String approve,String comment,String respond,String mention,String vote,String follow,String rsvp,String news,String interest)
	{
		  try 
	  	  {
			  ContentValues cv=new ContentValues();
			  cv.put(MySqliteHelper.column_check_status, 		status);
			  cv.put(MySqliteHelper.column_check_email, 		email);
			  cv.put(MySqliteHelper.column_check_approve, 		approve);	
			  cv.put(MySqliteHelper.column_check_comment, 		comment);
			  cv.put(MySqliteHelper.column_check_respond, 		respond);
			  cv.put(MySqliteHelper.column_check_mention, 		mention);
			  cv.put(MySqliteHelper.column_check_vote, 			vote);
			  cv.put(MySqliteHelper.column_check_follow, 		follow);
			  cv.put(MySqliteHelper.column_check_rsvp, 			rsvp);
			  cv.put(MySqliteHelper.column_check_news, 			news);
			  cv.put(MySqliteHelper.column_check_interest, 		interest);
			  database.insert(MySqliteHelper.table_check, null, cv);
			  Log.e(TAG,"Notifications added!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	//set of operations for personalize_politics
	public void create_per_politics(String email,String parties,String politicians,String policies,String national,String international)
	{
		  try 
	  	  {
			  ContentValues cv=new ContentValues();
			  cv.put("email", 			email);
			  cv.put("parties", 		parties);	
			  cv.put("politicians", 	politicians);
			  cv.put("policies", 		policies);
			  cv.put("national", 		national);
			  cv.put("international", 	international);
			  database.insert(MySqliteHelper.table_per_politics, null, cv);
			  Log.e(TAG,"politics added!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public int update_per_politics(String email,String parties,String politicians,String policies,String national,String international)
	{
		
		  ContentValues cv=new ContentValues();
		  cv.put("parties", 		parties);	
		  cv.put("politicians", 	politicians);
		  cv.put("policies", 		policies);
		  cv.put("national", 		national);
		  cv.put("international", 	international);
		  return 	database.update(MySqliteHelper.table_per_politics, cv,"email = ? ", new String[]{email});
	}
	public Cursor get_per_politics(String email)
	{
		Cursor cur=database.rawQuery("select * from politics where email=?", new String[]{email});
		return cur;
	}
	public Cursor get_details(String email,String table)
	{
		Log.e("function","get_details");
		Cursor cur=database.rawQuery("select * from "+table+" where email=?",new String[]{email});
		return cur;
	}
	public int get_details_count(String email,String table)
	{
		int count=0,col_count;
		Cursor cur=database.rawQuery("select * from "+table+" where email=?",new String[]{email});
		cur.moveToFirst();
		col_count=cur.getColumnCount();
		for(int i=0;i<col_count;i++)
		{
			if(cur.getString(i).equals("true"))
			{
				count++;
			}
		}
		return count;
	}
	//set of operations for personalize news
	public void create_per_news(String email,String weird,String national,String international)
	{
		  try 
	  	  {
			  ContentValues cv=new ContentValues();
			  cv.put("email", 			email);
			  cv.put("weird", 			weird);	
			  cv.put("national", 		national);
			  cv.put("international", 	international);
			  database.insert(MySqliteHelper.table_per_news, null, cv);
			  Log.e(TAG,"News added!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public int update_per_news(String email,String weird,String national,String international)
	{
		
		  ContentValues cv=new ContentValues();
		  cv.put("weird", 			weird);	
		  cv.put("national", 		national);
		  cv.put("international", 	international);
		  return 	database.update(MySqliteHelper.table_per_news, cv,"email = ? ", new String[]{email});
	}
	//set of operations for social
	public void create_per_social(String email,String local,String national,String international)
	{
		  try 
	  	  {
			  ContentValues cv=new ContentValues();
			  cv.put("email", 			email);
			  cv.put("local", 			local);	
			  cv.put("national", 		national);
			  cv.put("international", 	international);
			  database.insert(MySqliteHelper.table_per_social, null, cv);
			  Log.e(TAG,"social added!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public int update_per_social(String email,String local,String national,String international)
	{
		
		  ContentValues cv=new ContentValues();
		  cv.put("local", 			local);	
		  cv.put("national", 		national);
		  cv.put("international", 	international);
		  return 	database.update(MySqliteHelper.table_per_social, cv,"email = ? ", new String[]{email});
	}
	
	//set of operations for entertainment
	public void create_per_entertainment(String email,String arts,String books,String celebrities,String movies,String tv,String theatre,String bollywood,String hollywood,String regional)
	{
		  try 
	  	  {
			  ContentValues cv=new ContentValues();
			  cv.put("email", 			email);
			  cv.put("arts", 			arts);
			  cv.put("books", 			books);
			  cv.put("celebrities", 	celebrities);
			  cv.put("movies", 			movies);
			  cv.put("tv", 				tv);
			  cv.put("theatre", 		theatre);
			  cv.put("bollywood", 		bollywood);
			  cv.put("hollywood", 		hollywood);
			  cv.put("regional", 		regional);
			  database.insert(MySqliteHelper.table_per_entertainment, null, cv);
			  Log.e(TAG,"social added!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public int update_per_entertainment(String email,String arts,String books,String celebrities,String movies,String tv,String theatre,String bollywood,String hollywood,String regional)
	{
		
		  ContentValues cv=new ContentValues();
		  cv.put("arts", 			arts);
		  cv.put("books", 			books);
		  cv.put("celebrities", 	celebrities);
		  cv.put("movies", 			movies);
		  cv.put("tv", 				tv);
		  cv.put("theatre", 		theatre);
		  cv.put("bollywood", 		bollywood);
		  cv.put("hollywood", 		hollywood);
		  cv.put("regional", 		regional);
		  return 	database.update(MySqliteHelper.table_per_entertainment, cv,"email = ? ", new String[]{email});
	}
	
	//set of operations for sports
	public void create_per_sports(String email,String cricket,String hockey,String boxing,String soccer,String tennis,String athletics,String badminton,String olympics,String f1,String shooting,String swimming)
	{
		  try 
	  	  {
//			  cricket text not null,hockey text not null,boxing text not null,soccer text not null,tennis text not null,athletics text not null,badminton text not null,olympics text not null,f1 text not null,shooting text not null,swimming text not null
			  ContentValues cv=new ContentValues();
			  cv.put("email", 				email);
			  cv.put("cricket", 			cricket);
			  cv.put("hockey", 				hockey);
			  cv.put("boxing", 				boxing);
			  cv.put("soccer", 				soccer);
			  cv.put("tennis", 				tennis);
			  cv.put("athletics", 			athletics);
			  cv.put("badminton", 			badminton);
			  cv.put("olympics", 			olympics);
			  cv.put("f1", 					f1);
			  cv.put("shooting", 			shooting);
			  cv.put("swimming", 			swimming);
			  database.insert(MySqliteHelper.table_per_sports, null, cv);
			  Log.e(TAG,"social added!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public int update_per_sports(String email,String cricket,String hockey,String boxing,String soccer,String tennis,String athletics,String badminton,String olympics,String f1,String shooting,String swimming)
	{
		
		  ContentValues cv=new ContentValues();
		  cv.put("cricket", 			cricket);
		  cv.put("hockey", 				hockey);
		  cv.put("boxing", 				boxing);
		  cv.put("soccer", 				soccer);
		  cv.put("tennis", 				tennis);
		  cv.put("athletics", 			athletics);
		  cv.put("badminton", 			badminton);
		  cv.put("olympics", 			olympics);
		  cv.put("f1", 					f1);
		  cv.put("shooting", 			shooting);
		  cv.put("swimming", 			swimming);
		  return 	database.update(MySqliteHelper.table_per_sports, cv,"email = ? ", new String[]{email});
	}
	
	//set of permissions for lifestyle
	public void create_per_lifestyle(String email,String automotive,String culture,String fandb,String handg,String theatre,String travel)
	{
		  try 
	  	  {
			  ContentValues cv=new ContentValues();
			  cv.put("email", 				email);
			  cv.put("automotive", 			automotive);
			  cv.put("culture", 			culture);
			  cv.put("fandb", 				fandb);
			  cv.put("handg", 				handg);
			  cv.put("theatre", 			theatre);
			  cv.put("travel", 				travel);
			  database.insert(MySqliteHelper.table_per_lifestyle, null, cv);
			  Log.e(TAG,"social added!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public int update_per_lifestyle(String email,String automotive,String culture,String fandb,String handg,String theatre,String travel)
	{
		
		  ContentValues cv=new ContentValues();
		  cv.put("automotive", 			automotive);
		  cv.put("culture", 			culture);
		  cv.put("fandb", 				fandb);
		  cv.put("handg", 				handg);
		  cv.put("theatre", 			theatre);
		  cv.put("travel", 				travel);
		  return 	database.update(MySqliteHelper.table_per_lifestyle, cv,"email = ? ", new String[]{email});
	}
	
	//set of operations for business
	public void create_per_business(String email,String companies,String economy,String industry,String markets,String people)
	{
		  try 
	  	  {
			  ContentValues cv=new ContentValues();
			  cv.put("email", 				email);
			  cv.put("companies", 			companies);
			  cv.put("economy", 			economy);
			  cv.put("industry", 			industry);
			  cv.put("markets", 			markets);
			  cv.put("people", 				people);
			  database.insert(MySqliteHelper.table_per_business, null, cv);
			  Log.e(TAG,"social added!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public int update_per_business(String email,String companies,String economy,String industry,String markets,String people)
	{
		
		  ContentValues cv=new ContentValues();
		  cv.put("companies", 			companies);
		  cv.put("economy", 			economy);
		  cv.put("industry", 			industry);
		  cv.put("markets", 			markets);
		  cv.put("people", 				people);
		  return 	database.update(MySqliteHelper.table_per_business, cv,"email = ? ", new String[]{email});
	}
	
	//set of  operations for Technology
	public void create_per_tech(String email,String internet,String computing,String pt,String et,String vg,String manda,String pcandlap,String wg,String cameras)
	{
		  try 
	  	  {
			  ContentValues cv=new ContentValues();
			  cv.put("email", 			email);
			  cv.put("internet", 		internet);
			  cv.put("computing", 		computing);
			  cv.put("pt", 				pt);
			  cv.put("et", 				et);
			  cv.put("vg", 				vg);
			  cv.put("manda", 			manda);
			  cv.put("pcandlap", 		pcandlap);
			  cv.put("wg", 				wg);
			  cv.put("cameras", 		cameras);
			  database.insert(MySqliteHelper.table_per_technology, null, cv);
			  Log.e(TAG,"Technology added!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public int update_per_tech(String email,String internet,String computing,String pt,String et,String vg,String manda,String pcandlap,String wg,String cameras)
	{
		
		  ContentValues cv=new ContentValues();
		  cv.put("internet", 			internet);
		  cv.put("computing", 			computing);
		  cv.put("pt", 					pt);
		  cv.put("et", 					et);
		  cv.put("vg", 					vg);
		  cv.put("manda", 				manda);
		  cv.put("pcandlap", 			pcandlap);
		  cv.put("wg", 					wg);
		  cv.put("cameras", 			cameras);
		  return 	database.update(MySqliteHelper.table_per_technology, cv,"email = ? ", new String[]{email});
	}
	
	// set of operations for Health
	public void create_per_health(String email,String nf,String mp,String ds)
	{
		  try 
	  	  {
			  ContentValues cv=new ContentValues();
			  cv.put("email", 			email);
			  cv.put("nf", 			nf);	
			  cv.put("mp", 			mp);
			  cv.put("ds", 			ds);
			  database.insert(MySqliteHelper.table_per_health, null, cv);
			  Log.e(TAG,"social added!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public int update_per_health(String email,String nf,String mp,String ds)
	{
		
		  ContentValues cv=new ContentValues();
		  cv.put("nf", 			nf);	
		  cv.put("mp", 			mp);
		  cv.put("ds", 			ds);
		  return 	database.update(MySqliteHelper.table_per_health, cv,"email = ? ", new String[]{email});
	}
	
	// set of operations for Science
	public void create_per_science(String email,String space,String envi,String geo)
	{
		  try 
	  	  {
			  ContentValues cv=new ContentValues();
			  cv.put("email", 			email);
			  cv.put("space", 			space);	
			  cv.put("envi", 			envi);
			  cv.put("geo", 			geo);
			  database.insert(MySqliteHelper.table_per_science, null, cv);
			  Log.e(TAG,"social added!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public int update_per_science(String email,String space,String envi,String geo)
	{
		
		  ContentValues cv=new ContentValues();
		  cv.put("space", 			space);	
		  cv.put("envi", 			envi);
		  cv.put("geo", 			geo);
		  return 	database.update(MySqliteHelper.table_per_science, cv,"email = ? ", new String[]{email});
	}
	
	public Cursor get_email_notifications(String email)
	{
		Cursor cur=database.rawQuery("select * from notifications where email=?", new String[]{email});
		return cur;
	}
	public Cursor get_check_details(String email)
	{
		Cursor cur=database.rawQuery("select * from check where email=?", new String[]{email});
		return cur;
	}
	public Cursor getall()
	{
		 Cursor cursor = database.query(MySqliteHelper.table_user,columns, null, null, null, null, null);
		 return cursor;
	}
	public  boolean CheckIsDataAlreadyInDBorNot(String TableName,String dbfield, String fieldValue) 
	{
		Cursor cursor = database.rawQuery("select * from "+TableName+" where "+dbfield+"=?",new String[]{""+fieldValue});
	    if(cursor.getCount()>0)//email(field value) exists
	    {
	    	return true;
	    }
	    return false;
	}
	public  boolean CheckValidUser(String email, String password) 
	{
		Cursor cursor = database.rawQuery("select * from users where email= ? and password =?" ,new String[]{email,password});
	    if(cursor.getCount()==1)//email(field value) exists
	    {
	    	return true;
	    }
	    return false;
	}
	public Cursor getdetails_of_user(String email)
	{
		Cursor cur=database.rawQuery("select * from users where email=?", new String[]{email});
		return cur;
	}
	public int  change_pswd(String email,String oldpswd,String newpswd)
	{
		ContentValues cv=new ContentValues();
		cv.put(MySqliteHelper.column_password,newpswd);
		return database.update(MySqliteHelper.table_user, cv, MySqliteHelper.column_email+" = ? ", new String[]{email});
	}
	public int  change_profile(Bundle b)
	{
		ContentValues cv=new ContentValues();
		cv.put(MySqliteHelper.column_name,b.getString("fname")+" "+b.getString("lname"));
		cv.put(MySqliteHelper.column_fname,b.getString("fname"));
		cv.put(MySqliteHelper.column_lname,b.getString("lname"));
		cv.put(MySqliteHelper.column_location,b.getString("location"));
		cv.put(MySqliteHelper.column_dob,b.getString("dob"));
		cv.put(MySqliteHelper.column_mobile,b.getString("mobile"));
		return 	database.update(MySqliteHelper.table_user, cv,MySqliteHelper.column_email+" = ? ", new String[]{b.getString("email")});
	}
	public void update_pic(byte[] pic,String email)
	{
		ContentValues cv=new ContentValues();
		cv.put(MySqliteHelper.column_pic,pic);
		database.update(MySqliteHelper.table_user, cv,MySqliteHelper.column_email+" = ? ", new String[]{email});
	}
	public int update_notifications(String status,String email,String approve,String comment,String respond,String mention,String vote,String follow,String rsvp,String news,String interest,String approve_check,String comment_check,String respond_check,String mention_check,String vote_check,String follow_check,String rsvp_check,String news_check,String interest_check)
	{
		
		  ContentValues cv=new ContentValues();
		  cv.put(MySqliteHelper.column_notifi_status, 		status);
		  cv.put(MySqliteHelper.column_notifi_approve, 		approve);	
		  cv.put(MySqliteHelper.column_notifi_comment, 		comment);
		  cv.put(MySqliteHelper.column_notifi_respond, 		respond);
		  cv.put(MySqliteHelper.column_notifi_mention, 		mention);
		  cv.put(MySqliteHelper.column_notifi_vote, 		vote);
		  cv.put(MySqliteHelper.column_notifi_follow, 		follow);
		  cv.put(MySqliteHelper.column_notifi_rsvp, 		rsvp);
		  cv.put(MySqliteHelper.column_notifi_news, 		news);
		  cv.put(MySqliteHelper.column_notifi_interest, 	interest);
		  cv.put(MySqliteHelper.column_check_approve, 		approve_check);	
		  cv.put(MySqliteHelper.column_check_comment, 		comment_check);
		  cv.put(MySqliteHelper.column_check_respond, 		respond_check);
		  cv.put(MySqliteHelper.column_check_mention, 		mention_check);
		  cv.put(MySqliteHelper.column_check_vote, 			vote_check);
		  cv.put(MySqliteHelper.column_check_follow, 		follow_check);
		  cv.put(MySqliteHelper.column_check_rsvp, 			rsvp_check);
		  cv.put(MySqliteHelper.column_check_news, 			news_check);
		  cv.put(MySqliteHelper.column_check_interest, 		interest_check);
		  return 	database.update(MySqliteHelper.table_notification, cv,MySqliteHelper.column_notifi_email+" = ? ", new String[]{email});
	}
}
