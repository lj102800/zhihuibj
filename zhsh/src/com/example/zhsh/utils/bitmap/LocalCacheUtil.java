package com.example.zhsh.utils.bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.example.zhsh.utils.MD5Encoder;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
/**
 * 本地缓存工具类
 * @author liangjun
 *
 */
public class LocalCacheUtil {
	//图片缓存的文件夹
	public static final String DIRPATH=Environment.getExternalStorageDirectory().getAbsolutePath()+"/bitmap_cache66";
	public Bitmap getBitmapFromLocal(String url) {
		try {
			File file=new File(DIRPATH,MD5Encoder.encode(url));
			System.out.println(file.getAbsolutePath()+file.getName());
			if(file.exists()){
				Bitmap bitmap=BitmapFactory.decodeStream(new FileInputStream(file));
				return bitmap;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public void setBitmapToLocal(Bitmap bitmap,String url){
		File dirFile=new File(DIRPATH);
		//如果文件夹不存在或者不是文件夹
		if(!dirFile.exists()||!dirFile.isDirectory()){
			dirFile.mkdirs();
		}
		try {
			File file=new File(DIRPATH,MD5Encoder.encode(url));
			//将图片压缩保存在本地，参1：压缩格式；参2：压缩质量(0-100)；参3：输出流
			bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(file));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
