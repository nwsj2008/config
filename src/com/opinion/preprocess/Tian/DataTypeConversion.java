package com.opinion.preprocess.Tian;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据类型转换
 * 
 * @author 张彦超 李舒晨 廉捷
 * 
 */

public class DataTypeConversion {
	/**
	 * 
	 * @param time 时间
	 * @return
	 */
	public static Date timeTypeConversion(String time) {
		// 用正则表达式抽取时间
		String DatePattern = "[0-9][0-9][0-9][0-9]-([0-1])?[0-9]-([0-3])?[0-9] [0-2][0-9]:[0-5][0-9]:[0-5][0-9]";
		String DatePattern2 = "[0-9][0-9][0-9][0-9]-([0-1])?[0-9]-([0-3])?[0-9] [0-2][0-9]:[0-5][0-9]";
		String DatePattern3 = "[0-9][0-9][0-9][0-9]年[0-1][0-9]月[0-3][0-9]日[0-2][0-9]:[0-5][0-9]";
		Pattern p = Pattern.compile(DatePattern);
		Pattern p2 = Pattern.compile(DatePattern2);
		Pattern p3 = Pattern.compile(DatePattern3);
		//匹配
		Matcher m = p.matcher(time);
		Matcher m2 = p2.matcher(time);
		Matcher m3 = p3.matcher(time);
		//是否匹配
		boolean c = m.find();
		boolean c2 = m2.find();
		boolean c3 = m3.find();
		if (c2) {
			if (c) {
				// 将yyyy-MM-dd HH:mm:ss时间转换为日期类型
				SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					String sString = m.group();
					Date dat = d.parse(sString);
					return dat;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				// 将yyyy-MM-dd HH:mm时间转换为日期类型
				SimpleDateFormat d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					String sString = m2.group();
					Date dat2 = d2.parse(sString);
					return dat2;
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}
		}else {
			if(c3){
				// 将yyyy年MM月dd日HH:mm时间转换为日期类型
				SimpleDateFormat d3 = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
				try {
					String sString = m3.group();
					Date dat3 = d3.parse(sString);
					return dat3;
				} catch (ParseException e) {
					e.printStackTrace();
				}			
			}
		}
		return null;
	}
	/**
	 *  抽取回复数量
	 * @param reNum 回复数
	 * @return
	 */

	public static int reNumtypeConversion(String reNum) {
		// 抽取回复数
		String NumPattern = "[0-9]+";
		//匹配
		Pattern p = Pattern.compile(NumPattern);
		Matcher m = p.matcher(reNum);
		String num = "";
		while (m.find()) {
			num = m.group();
		}
		if (!num.equals("")) {
			//将回复数转换为整型
			return Integer.parseInt(num);
		}
		return 0;

	}

	/**
	 * 获取所需的HashCode
	 * @param needToCov	装有需生成HashCode对象的Vector
	 * @return
	 */
	public static int getHashCode(Vector needToCov){
		String s_needToCov = "";
		for(Object obj : needToCov){
			if(obj !=null){
				s_needToCov += obj.toString();
			}
		}
		System.out.println(s_needToCov);
		int covHasCode = s_needToCov.hashCode();
		return covHasCode;
	}
	
	
	
}
