package com.example.zhsh.domain;

import java.util.ArrayList;

public class PhotoBean {
	public int retcode;
	public PhotoData data;
	public class PhotoData{
		public ArrayList<PhotoNewsData> news; 
	}
	public class PhotoNewsData{
		public String id;
		public String listimage;
		public String pubdate;
		public String title;
		public String url; 
	}
}
