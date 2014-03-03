package com.opinion.preprocess.Tian;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ��������ת��
 * 
 * @author ���峬 ���泿 ����
 * 
 */

public class DataTypeConversion {
	/**
	 * 
	 * @param time ʱ��
	 * @return
	 */
	public static Date timeTypeConversion(String time) {
		// ��������ʽ��ȡʱ��
		String DatePattern = "[0-9][0-9][0-9][0-9]-([0-1])?[0-9]-([0-3])?[0-9] [0-2][0-9]:[0-5][0-9]:[0-5][0-9]";
		String DatePattern2 = "[0-9][0-9][0-9][0-9]-([0-1])?[0-9]-([0-3])?[0-9] [0-2][0-9]:[0-5][0-9]";
		String DatePattern3 = "[0-9][0-9][0-9][0-9]��[0-1][0-9]��[0-3][0-9]��[0-2][0-9]:[0-5][0-9]";
		Pattern p = Pattern.compile(DatePattern);
		Pattern p2 = Pattern.compile(DatePattern2);
		Pattern p3 = Pattern.compile(DatePattern3);
		//ƥ��
		Matcher m = p.matcher(time);
		Matcher m2 = p2.matcher(time);
		Matcher m3 = p3.matcher(time);
		//�Ƿ�ƥ��
		boolean c = m.find();
		boolean c2 = m2.find();
		boolean c3 = m3.find();
		if (c2) {
			if (c) {
				// ��yyyy-MM-dd HH:mm:ssʱ��ת��Ϊ��������
				SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					String sString = m.group();
					Date dat = d.parse(sString);
					return dat;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				// ��yyyy-MM-dd HH:mmʱ��ת��Ϊ��������
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
				// ��yyyy��MM��dd��HH:mmʱ��ת��Ϊ��������
				SimpleDateFormat d3 = new SimpleDateFormat("yyyy��MM��dd��HH:mm");
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
	 *  ��ȡ�ظ�����
	 * @param reNum �ظ���
	 * @return
	 */

	public static int reNumtypeConversion(String reNum) {
		// ��ȡ�ظ���
		String NumPattern = "[0-9]+";
		//ƥ��
		Pattern p = Pattern.compile(NumPattern);
		Matcher m = p.matcher(reNum);
		String num = "";
		while (m.find()) {
			num = m.group();
		}
		if (!num.equals("")) {
			//���ظ���ת��Ϊ����
			return Integer.parseInt(num);
		}
		return 0;

	}

	/**
	 * ��ȡ�����HashCode
	 * @param needToCov	װ��������HashCode�����Vector
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
