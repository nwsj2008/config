package com.opinion.preprocess.Tian;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/*
 * author:胡少荣 得到模板中匹配BBS节点信息的正则表达式
 */

public class Regex  {

	public List<String> GetRegex() {

		List<String> list = new ArrayList<String>();
		BufferedReader bufferedReader = null;
		try {

			bufferedReader = new BufferedReader(new FileReader(new File(
					"conf/bbsNode")));
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				String[] mm = line.split("@");
				String regex = mm[1];
				list.add(regex);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

}
