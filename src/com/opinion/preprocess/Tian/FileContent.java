package com.opinion.preprocess.Tian;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
/*
 * author:胡少荣 获得源文件内容返回
 */
public class FileContent {

	public static String getContent(String path,String charset) {
		System.out.println(path);
		BufferedReader bufferedReader = null;
		StringBuilder sb = null;
	
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(path)), "GBK"));
			
				String line = "";
				sb  = new StringBuilder();
				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line);
				}
				
				
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return sb.toString();
	}

	
}
