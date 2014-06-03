package com.saddahaq.media.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteHelper extends SQLiteOpenHelper
{
	public static final String column_name="name",column_fname="fname",column_lname="lname",column_email="email",column_password="password",column_location="location",column_dob="dob",
			   column_mobile="mobile",column_uname="uname",column_flag="flag",column_path="path",column_notifi_nid="nid",column_notifi_status="status",column_notifi_email="email",column_notifi_approve="approve",
					   column_pic="pic",column_notifi_comment="comment",column_notifi_respond="respond",column_notifi_mention="mention",column_notifi_vote="vote",column_notifi_follow="follow",column_notifi_rsvp="rsvp",column_notifi_news="news",column_notifi_interest="interest",
			   column_check_nid="cid",column_check_status="statuscheck",column_check_email="emailcheck",column_check_approve="approvecheck",column_check_comment="commentcheck",column_check_respond="respondcheck",column_check_mention="mentioncheck",column_check_vote="votecheck",column_check_follow="followcheck",column_check_rsvp="rsvpcheck",column_check_news="newscheck",
			   column_check_interest="interestcheck",table_user="users",table_image="image",table_notification="notifications",table_check="check",table_per_politics="politics",table_per_news="news",table_per_social="social",table_per_entertainment="entertainment",table_per_sports="sports",table_per_lifestyle="lifestyle"
			   ,table_per_business="business",table_per_technology="technology",table_per_health="health",table_per_science="science";
	public static final String CREATE_TABLE="create table users(uid INTEGER PRIMARY KEY AUTOINCREMENT,name text not null,fname text not null,lname text not null,uname text not null,email VARCHAR(25) not null,password not null,location text not null,dob text not null,mobile text not null,pic BLOB);";
	public static final String CREATE_TABLE_NOTIFICATIONS="create table notifications(nid INTEGER PRIMARY KEY AUTOINCREMENT,status text not null,email text not null,approve text not null,comment text not null,respond text not null,mention text not null,vote text not null,follow text not null,rsvp text not null,news text not null,interest text not null,approvecheck text not null,commentcheck text not null,respondcheck text not null,mentioncheck text not null,votecheck text not null,followcheck text not null,rsvpcheck text not null,newscheck text not null,interestcheck text not null);";
    public static final String Create_Table_per_politics="create table politics (polid INTEGER PRIMARY KEY AUTOINCREMENT,email text not null,parties text not null,politicians text not null,policies text not null,national text not null,international text not null);";
    public static final String Create_Table_per_news="create table news(newid INTEGER PRIMARY KEY AUTOINCREMENT,email text not null,weird text not null,national text not null,international text not null);";
    public static final String Create_Table_per_social="create table social (solid INTEGER PRIMARY KEY AUTOINCREMENT,email text not null,local text not null,national text not null,international text not null);";
    public static final String Create_Table_per_entertainment="create table entertainment(eid INTEGER PRIMARY KEY AUTOINCREMENT,email text not null,arts text not null,books text not null,celebrities text not null,movies text not null,tv text not null,theatre text not null,bollywood text not null,hollywood text not null,regional text not null);";
    public static final String Create_Table_per_sports="create table sports(sid INTEGER PRIMARY KEY AUTOINCREMENT,email text not null,cricket text not null,hockey text not null,boxing text not null,soccer text not null,tennis text not null,athletics text not null,badminton text not null,olympics text not null,f1 text not null,shooting text not null,swimming text not null);";
    public static final String Create_Table_per_lifestyle="create table lifestyle(lid INTEGER PRIMARY KEY AUTOINCREMENT,email text not null,automotive text not null,culture text not null,fandb text not null,handg text not null,theatre text not null,travel text not null);";
    public static final String Create_Table_per_business="create table business(bid INTEGER PRIMARY KEY AUTOINCREMENT,email text not null,companies text not null,economy text not null,industry text not null,markets text not null,people text not null);";
    public static final String Create_Table_per_technology="create table technology(tid INTEGER PRIMARY KEY AUTOINCREMENT,email text not null,internet text not null,computing text not null,pt text not null,et text not null,vg text not null,manda text not null,pcandlap text not null,wg text not null,cameras text not null);";
    public static final String Create_Table_per_health="create table health(hid INTEGER PRIMARY KEY AUTOINCREMENT,email text not null,nf text not null,mp text not null,ds text not null);";
    public static final String Create_Table_per_science="create table science(said INTEGER PRIMARY KEY AUTOINCREMENT,email text not null,space text not null,envi text not null,geo text not null);";
    public MySqliteHelper(Context context) 
	{
		super(context, "saddahaqtest.db", null, 1);
	}
	@Override
	public void onCreate(SQLiteDatabase database) 
	{
		database.execSQL(CREATE_TABLE);
		database.execSQL(CREATE_TABLE_NOTIFICATIONS);
		database.execSQL(Create_Table_per_politics);
		database.execSQL(Create_Table_per_news);
		database.execSQL(Create_Table_per_social);
		database.execSQL(Create_Table_per_entertainment);
		database.execSQL(Create_Table_per_sports);
		database.execSQL(Create_Table_per_lifestyle);
		database.execSQL(Create_Table_per_business);
		database.execSQL(Create_Table_per_technology);
		database.execSQL(Create_Table_per_health);
		database.execSQL(Create_Table_per_science);
	}
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) 
	{
		database.execSQL("DROP TABLE IF EXISTS users");
	    onCreate(database);
	}

}
