package com.example.zhsh.domain;

import java.util.ArrayList;

/**
 * 新闻中心分类数据
 * gson对象封装原则
 * 遇到{}就是一个对象
 * 遇到[]就是一个ArrayList
 * 对象中所有属性名必须和服务器返回字段完全一致
 * @author Administrator
 *
 */
public class NewMenuData {
	public int retcode;
	public ArrayList<String> extend;
	public ArrayList<NewsData> data;
	//菜单详情页
	public class NewsData{
		public String id;
		public String title;
		public int type;
		public ArrayList<NewsTabData> children;
	}
	
	@Override
	public String toString() {
		return "NewMenuData [retcode=" + retcode + ", extend=" + extend
				+ ", data=" + data + "]";
	}

	//页签信息封装
	public class NewsTabData{
		public String id;
		public String title;
		public String url;
		public int type;
	}
}
