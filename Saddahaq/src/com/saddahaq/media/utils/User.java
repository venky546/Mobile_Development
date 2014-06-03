package com.saddahaq.media.utils;

import android.util.Log;

public class User 
{
	private String name,email,password,location,fname,lname,dob,mobile,uname;
	private byte[] profile_pic;
	public User(){}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getMobile() {
		return mobile;
	}
	public byte[] getProfile_pic() {
		return profile_pic;
	}
	public void setProfile_pic(byte[] profile_pic) {
		this.profile_pic = profile_pic;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public User(String fname,String lname,String email,String password,String uname,byte[] pic)
	{
		this.name=fname+" "+lname;
		this.email=email;
		this.password=password;
		this.fname=fname;
		this.lname=lname;
		this.uname=uname;
		this.location="Edit your Location";
		this.dob="Edit your DOB";
		this.mobile="Edit your Mobile Num";
		this.profile_pic=pic;
	}

}
