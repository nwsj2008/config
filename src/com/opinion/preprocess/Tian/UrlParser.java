package com.opinion.preprocess.Tian;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * 获得爬虫日志文件中的文件名、url、编码方式，将与天涯论坛网址匹配后
 * 的这些属性以对象的方式存入数组中。
 * 
 */
public class UrlParser {
	public static List<CrawlLog> urlMatch(String logPath){
		String regex = "http://www.tianya.cn/\\w+/\\w+/(\\w+)?/\\d+/\\d+\\.shtml";
		CrawlLog crawlLog ;
		List<CrawlLog> crawList =  new ArrayList<CrawlLog>();
		 File logFile = new File(logPath);
		 String parent = logFile.getParent()+"\\";
		 try {
			BufferedReader bufferedReader  = new BufferedReader(new FileReader(logFile));
			String line = "";
			String dd = new String (new byte[]{0});
			while ((line=bufferedReader.readLine())!=null) {
				if(line.startsWith(dd))
					break;
				String [] filed = line.split(";");
				Matcher matcher = Pattern.compile(regex).matcher(filed[1]);
				if (matcher.matches()) {
					crawlLog = new CrawlLog();
					crawlLog.setFileName(parent+filed[0]);
					crawlLog.setUrl(filed[1]);
					crawlLog.setCharset(filed[filed.length-1].trim());
					crawList.add(crawlLog);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return crawList;
		
	}
	public static void main(String[] args) {
		System.out.println(urlMatch("D://sww/log.txt").get(0).getFileName());
	}
	
}
