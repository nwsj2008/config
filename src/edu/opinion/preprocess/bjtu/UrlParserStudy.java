package edu.opinion.preprocess.bjtu;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlParserStudy {

	private String regex = "";
	private String suffix = "";
	private String prefix = "http://";
	private List<String> mainList;
	private String webSite;
	boolean isTrue = true;
	
	/**
	 * 判别该页面为静态或者动态页面
	 */
	private String getSuffix(String url){
		int size = mainList.size();
		String regexSuffix = "[a-zA-Z]+";
		if (url.lastIndexOf(".")>0) {
			String subUrl = url.substring(url.lastIndexOf(".")+1, url.length());
			Pattern pattern = Pattern.compile(regexSuffix);
	        Matcher matcher = pattern.matcher(subUrl);
	        if ((Boolean)matcher.matches()){
	        	suffix = "."+subUrl;
	        	try{
	        		for (int i=0; i<size; i++){
	        			String tempUrl = mainList.get(i).toString();
		        		String temp = tempUrl.substring(0, tempUrl.lastIndexOf("."));
		        		mainList.remove(i);
		        		mainList.add(i, temp);
		        	}
	        	}catch(Exception e){
	        		e.printStackTrace();
	        		isTrue = false;
	        		return "****";
	        	}
	        	
	        	return url.substring(0, url.lastIndexOf("."));
	        }
	        else return url;
		} 
		else return url;
	}
	
	private void setList(List urlList){
		this.mainList = urlList;
	}
	
	/**
	 * 该方法生成URL模版的主进程
	 * 在执行该方法前需正则验证用户输入的所有URL均为有效URL地址
	 */
	public boolean urlMain(String iUrl1, String iUrl2, String iUrl3){
		List<String> urlList = new ArrayList<String>();
		urlList.add(iUrl1);
		urlList.add(iUrl2);
		urlList.add(iUrl3);
		this.setList(urlList);
//		boolean isFromSameUrl = this.isFromSameWebsite();
//		if (!isFromSameUrl)
//			return false;
		this.basicDeal();
		if (!isTrue)
			return false;
		String url = urlList.get(0).toString();
		url = this.getPrefix(url);	
		if (!isTrue)
			return false;
		url = this.getSuffix(url);
		if (!isTrue)
			return false;
		if( suffix.length()>1 ){ //如果是静态页面,即以'.html'等形式结尾,则执行下面的操作
			while (mainList.get(0).toString().indexOf("/")>0){
				this.getContent();
				if (!isTrue){
					break;
				}
			}
			this.dealEnd(mainList);
	        regex = regex + suffix;
	        this.replaceRegex();
		}else{ //如果是动态页面,即以'.jsp?id=****'等形式结尾,则执行下面的操作
			this.dealDynamicHeadPart();
			if (!isTrue)
				return false;
			boolean numOfEqual = this.isEqualsNum("=", mainList);
			boolean numOfAnd = this.isEqualsNum("&", mainList);
			boolean numOfChr = this.isEqualsNum("#", mainList);
			if (numOfEqual && numOfAnd && numOfChr){
				this.dealDynamicLastPart();
				if (!isTrue)
					return false;
				this.replaceRegex();
			}else{
				return false;
			}
		}
		return isTrue;
		
	}
	
	/**
	 * 1)判断某URL表达式中两个'/'之间的内容是数字,字母,还是混合字符
	 * 2)判断所给字符串的匹配规则
	 */
	private int getType(String content){
		String regex1 = "\\d+";
		String regex2 = "[a-zA-Z]+";
		String regex3 = "[a-zA-Z0-9]+";
		Pattern pattern = Pattern.compile(regex1);
		Matcher matcher = pattern.matcher(content);
		if ((Boolean)matcher.matches())
			return 1;
		else{
			pattern = Pattern.compile(regex2);
			matcher = pattern.matcher(content);
			if ((Boolean)matcher.matches())
				return 2;
			else{
				pattern = Pattern.compile(regex3);
				matcher = pattern.matcher(content);
				if ((Boolean)matcher.matches())
					return 3;
				else
					return 4;
		     }
		
	     }
    }
	
	/**
	 * 得到URL地址的HTTP://前缀,形如:"http://www.sohu.com/"
	 */
	private String getPrefix(String url){
		int size = mainList.size();
		List<String> listUrl = new ArrayList<String>();
		try{
			for (int i=0; i<size; i++){
				String urlTemp = mainList.get(i).toString();
				String url1 = urlTemp.substring(urlTemp.indexOf("//")+2,urlTemp.indexOf("/", 8));
				listUrl.add(url1);
				String temp1 = urlTemp.substring(urlTemp.indexOf("//")+2, urlTemp.length());
				String temp = urlTemp.substring(8+temp1.indexOf("/"),urlTemp.length());
				mainList.remove(i);
				mainList.add(i, temp);
			}	
			regex = regex + prefix + this.dealUrl(listUrl);
			webSite = regex;
		}catch (Exception e){
			e.printStackTrace();
			isTrue = false;
    		return "****";		
		}		
		return url.substring(7, url.length());
	}
	
	/**
	 * 遍历URL中所有'/'之间的内容,如果各输入URL中两个'/'之间内容全部相同,则被定义为全文匹配模式
	 * 若不同,则定义为正则匹配模式,具体正则表达式通过调用getType(String content)方法实现
	 */
	private void getContent(){
		int size = mainList.size();
		List list = new ArrayList();
		boolean flag = true;
		try{
			for (int i=0; i<size; i++){
				String url = mainList.get(i).toString();
				String temp = url.substring(0, url.indexOf("/")); 
				list.add(temp);
				String tempUrl = url.substring(url.indexOf("/")+1,url.length());
				mainList.remove(i);
				mainList.add(i, tempUrl);
			}
			for (int i=1; i<size; i++){
				if(!list.get(i-1).toString().equals(list.get(i).toString())){
					flag = false;			
				}
			}
			if (flag){
				regex = regex + "/" + list.get(0).toString();
			}
			else{
				switch(this.getType(list.get(0).toString())){
				case 1:
					regex = regex + "/\\d+";
					break;
				case 2:
					regex = regex + "/\\[a-zA-Z]+";
					break;
				case 3:
					regex = regex + "/\\[a-zA-Z0-9]+";
					break;
				case 4:
					regex = regex + "/\\w+";
					break;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			isTrue = false;
		}
	}
	
	/**
	 * 处理静态页面的页面名称匹配,形如:
	 * http://www.aaa.com/bbb/ccc/ddd.html则处理最后一个'/'后,即ddd处的匹配规则
	 */
	private void dealEnd(List list){
		int size = list.size();
		boolean flag = true;
		for (int i=1; i<size; i++){
			if(!list.get(i-1).toString().equals(list.get(i).toString()))
				flag = false;
		}
		if (flag){
			regex = regex + "/" + list.get(0).toString();
		}
		else{
			switch(this.getType(list.get(0).toString())){
			case 1:
				regex = regex + "/\\d+";
				break;
			case 2:
				regex = regex + "/\\[a-zA-Z]+";
				break;
			case 3:
				regex = regex + "/\\[a-zA-Z0-9]+";
				break;
			case 4:
				regex = regex + "/\\w+";
				break;
			}
		}	
	}
	
	/**
	 * 提供外部方法调用此类生成的页面匹配正则表达式:regex
	 */
	public String getRegex(){
		return this.regex;
	}
	
	/**
	 * 将正则表达式中"."替换为"\\."
	 * 将正则表达式中"?"替换为"\\?"
	 * 其中'.'与'?'在正则表达中需要转义
	 * 而'\'在JAVA中要转义为:"\\"
	 */
	private void replaceRegex(){
		regex = regex.replace(".", "\\.");
		regex = regex.replace("?", "\\?");
	}
	
	/**
	 * 判断输入各URL中，‘/‘符号出现次数是否相同
	 * 如果相同则被认为是可以尝试生成模版的URL队列
	 * 若不同，则返回false,需要用户重新输入形式相同的URL队列
	 */
	private void basicDeal(){
		String url1 = mainList.get(0).toString();
		int count1 = 0;
		int m1 = url1.indexOf("/");
		while (m1!=-1){
			m1 = url1.indexOf("/", m1+1);
			count1++;
		}
		for (int i=1; i<mainList.size(); i++){
			String url = mainList.get(i).toString();
			int count = 0;
			int m = url.indexOf("/");
			while (m!=-1){
				m = url.indexOf("/", m+1);
				count++;
			}
			if (count != count1){
				isTrue = false;
				break;
			}
		}
	}
	
	/**
	 * 此方法主要用来处理URL域名的匹配问题
	 * 例如需要建立对:news.sohu.com与travel.sohu.com
	 * 的匹配规则
	 * 需要注意的是定义例如:computer.it.sohu.com与sports.sohu.com这样长途不同URL域名的匹配规则
	 */
	private String dealUrl(List list){
		int size = list.size();
		String urlNameRegex = "";
		boolean isSame = this.isEqualsNum(".", list);
		if (isSame){	
			boolean isFirstSame = true;
			while(list.get(0).toString().indexOf(".")!=-1){
				boolean isLastSame = true;
				for(int i=1; i<size; i++){
					String url1 = list.get(i-1).toString();
					String end1 = url1.substring(url1.lastIndexOf(".")+1,url1.length());
					String url2 = list.get(i).toString();
					String end2 = url2.substring(url2.lastIndexOf(".")+1,url2.length());
					if (!end1.equals(end2)){
						isLastSame = false;
						break;
					}
				}
				if(isLastSame){
					urlNameRegex = "."+list.get(0).toString().substring(list.get(0).toString().lastIndexOf(".")+1,list.get(0).toString().length()) + urlNameRegex;
				}
				else{			
					urlNameRegex = ".\\[a-zA-Z0-9]+" + urlNameRegex;
				}
				for (int i=0; i<size; i++){
					String url = list.get(i).toString();
					String subUrl = url.substring(0,url.lastIndexOf("."));
					list.remove(i);
					list.add(i, subUrl);
				}
			}
			for (int i=1; i<size; i++){
				String url1 = list.get(i-1).toString();
				String url2 = list.get(i).toString();
				if (!url1.equals(url2)){
					isFirstSame = false;
					break;
				}
			}
			if (isFirstSame)
				urlNameRegex = list.get(0).toString() + urlNameRegex;
			else{				
				urlNameRegex = "\\[a-zA-Z0-9]+" + urlNameRegex;
			}
		}else{
			while(list.get(0).toString().indexOf(".")!=-1){
				boolean isLastSame = true;
				for(int i=1; i<size; i++){
					String url1 = list.get(i-1).toString();
					String end1 = url1.substring(url1.lastIndexOf(".")+1,url1.length());
					String url2 = list.get(i).toString();
					String end2 = url2.substring(url2.lastIndexOf(".")+1,url2.length());
					if (!end1.equals(end2)){
						isLastSame = false;
						break;
					}
				}
				if(isLastSame){
					urlNameRegex = "."+list.get(0).toString().substring(list.get(0).toString().lastIndexOf(".")+1,list.get(0).toString().length()) + urlNameRegex;
				}
				else{			
					urlNameRegex = ".\\[a-zA-Z0-9]+" + urlNameRegex;
				}
				for (int i=0; i<size; i++){
					String url = list.get(i).toString();
					String subUrl = url.substring(0,url.lastIndexOf("."));
					list.remove(i);
					list.add(i, subUrl);
				}
			}
			String firtSub = urlNameRegex.substring(1,urlNameRegex.length());
			urlNameRegex = "(\\[a-zA-Z0-9]+.)+"+ firtSub;		
		}
		return urlNameRegex;
	}
	
	/**
	 * 判断所给定List中各字符串中某字符出现次数是否相同
	 */
	private boolean isEqualsNum(String chr,List list){
		String url1 = list.get(0).toString();
		int count1 = 0;
		int m1 = url1.indexOf(chr);
		boolean isSame = true;
		while (m1!=-1){
			m1 = url1.indexOf(chr, m1+1);
			count1++;
		}
		for (int i=1; i<list.size(); i++){
			String url = list.get(i).toString();
			int count = 0;
			int m = url.indexOf(chr);
			while (m!=-1){
				m = url.indexOf(chr, m+1);
				count++;
			}
			if (count != count1){
				isSame = false;
				break;
			}
		}
		return isSame;
	}
	
	/**
	 * 获得某list中,所给定字符'chr'出现次数最少的list元素
	 * 例如list.get(0)中字符串"abc"出现2次,
	 * list.get(1)中字符串"abc"出现1次,
	 * list.get(2)中字符串"abc"出现4次,
	 * 则返回int型1
	 */
	private int theSmallNumIndex(String chr,List list){
		String url1 = list.get(0).toString();
		int countSmall = 0;
		int index = 0;
		int m1 = url1.indexOf(chr);
		while (m1!=-1){
			m1 = url1.indexOf(chr, m1+1);
			countSmall++;
		}
		for (int i=1; i<mainList.size(); i++){
			String url = mainList.get(i).toString();
			int count = 0;
			int m = url.indexOf(chr);
			while (m!=-1){
				m = url.indexOf(chr, m+1);
				count++;
			}
			if (count < countSmall){
				index = i;
				countSmall = count;
			}
		}
		return index;
	}
	
	/**
	 * 对动态页面中，url地址信息唯一'?'之前的内容进行匹配
	 * 通常在动态页面中，'?'之前的部分都是相同的，因此本程序将'?'之前的部分进行字符完全匹配处理
	 * 即：http://www.thegreatwall.com.cn/phpbbs/index.php?id=100380&forumid=1
	 * 在这个URL地址中，regex将对http://www.thegreatwall.com.cn/phpbbs/index.php?将对字符进行完全匹配
	 */
	private void dealDynamicHeadPart(){
		int size = mainList.size();
		try{
			String url = mainList.get(0).toString();
			url = url.substring(0, url.indexOf("?")+1);
			for (int i=0; i<size; i++){
				String temp = mainList.get(i).toString().substring(mainList.get(i).toString().indexOf("?")+1, mainList.get(i).toString().length());
				mainList.remove(i);
				mainList.add(i, temp);
			}
			regex = regex + "/" + url;
		}catch(Exception e){
			e.printStackTrace();
			isTrue = false;
		}
	}
	
	/**
	 * 对动态页面中，url地址信息唯一'?'之后的内容进行匹配
	 * 设计思路为通过"&"符号将URL地址中'?'之后的部分区分开
	 * 即：http://www.thegreatwall.com.cn/phpbbs/index.php?id=100380&forumid=1
	 * 在这个URL地址中，'?'之后的部分被分割成：id=100380与forumid=1，从而对这两部分进行分别匹配
	 * 需要注意的是，有些URL地址结尾部分包含"#"，如：http://zhidao.baidu.com/q?ct=24&cm=16&tn=ikusercenter#ask
	 * 因此在判断时，应该首先将URL按照包含"#"与不包含"#"字分别处理
	 */
	private void dealDynamicLastPart(){
		int size = mainList.size();
		try{
			boolean isChr = this.isExist("#", mainList.get(0).toString());
			if (isChr){
				int m = mainList.get(0).toString().indexOf("&");
				while(m!=-1){
					String url = mainList.get(0).toString();
					String temp = url.substring(0, url.indexOf("&"));
					String firstPart = temp.substring(0, temp.indexOf("=")+1);
					String lastPart = temp.substring(temp.indexOf("=")+1,temp.length());
					String regexTemp = "";
					switch(this.getType(lastPart)){
					case 1:
						regexTemp = regexTemp + "/\\d+";
						break;
					case 2:
						regexTemp = regexTemp + "/\\[a-zA-Z]+";
						break;
					case 3:
						regexTemp = regexTemp + "/\\[a-zA-Z0-9]+";
						break;
					case 4:
						regexTemp = regexTemp + "/\\w+";
						break;
					}
					regex = regex + firstPart + regexTemp + "&";
					for (int i=0; i<size; i++){
						String urlTemp = url.substring(url.indexOf("&")+1, url.length());
						mainList.remove(i);
						mainList.add(i, urlTemp);
					}
					m = mainList.get(0).toString().indexOf("&");
				}
				String url = mainList.get(0).toString();
				String firstPart = url.substring(0, url.indexOf("=")+1);
				String lastPart = url.substring(url.indexOf("=")+1,url.indexOf("#"));
				String end = url.substring(url.indexOf("#"), url.length());
				String regexTemp = "";
				switch(this.getType(lastPart)){
				case 1:
					regexTemp = regexTemp + "/\\d+";
					break;
				case 2:
					regexTemp = regexTemp + "/\\[a-zA-Z]+";
					break;
				case 3:
					regexTemp = regexTemp + "/\\[a-zA-Z0-9]+";
					break;
				case 4:
					regexTemp = regexTemp + "/\\w+";
					break;
				}
				regex = regex + firstPart + regexTemp + end;
			}
			else {
				int m = mainList.get(0).toString().indexOf("&");
				while(m!=-1){
					String url = mainList.get(0).toString();
					String temp = url.substring(0, url.indexOf("&"));
					String firstPart = temp.substring(0, temp.indexOf("=")+1);
					String lastPart = temp.substring(temp.indexOf("=")+1,temp.length());
					String regexTemp = "";
					switch(this.getType(lastPart)){
					case 1:
						regexTemp = regexTemp + "/\\d+";
						break;
					case 2:
						regexTemp = regexTemp + "/\\[a-zA-Z]+";
						break;
					case 3:
						regexTemp = regexTemp + "/\\[a-zA-Z0-9]+";
						break;
					case 4:
						regexTemp = regexTemp + "/\\w+";
						break;
					}
					regex = regex + firstPart + regexTemp + "&";
					for (int i=0; i<size; i++){
						String urlTemp = url.substring(url.indexOf("&")+1, url.length());
						mainList.remove(i);
						mainList.add(i, urlTemp);
					}
					m = mainList.get(0).toString().indexOf("&");
				}
				String url = mainList.get(0).toString();
				String firstPart = url.substring(0, url.indexOf("=")+1);
				String lastPart = url.substring(url.indexOf("=")+1,url.length());
				String regexTemp = "";
				switch(this.getType(lastPart)){
				case 1:
					regexTemp = regexTemp + "/\\d+";
					break;
				case 2:
					regexTemp = regexTemp + "/\\[a-zA-Z]+";
					break;
				case 3:
					regexTemp = regexTemp + "/\\[a-zA-Z0-9]+";
					break;
				case 4:
					regexTemp = regexTemp + "/\\w+";
					break;
				}
				regex = regex + firstPart + regexTemp;
			}
		}catch(Exception e){
			e.printStackTrace();
			isTrue = false;
		}
	}
	
	/**
	 * 此方法用来判断所给字符串中是否包含某特定字符
	 */
	private boolean isExist(String chr,String content){
		if(content.indexOf(chr)==-1)
			return false;
		else
			return true;
	}
	
	/**
	 * 在URL匹配方法中，对于长度相同，形式相同，却来自不同站点的URL地址，可能对配备规则的建立产生完全错误的影响
	 * 比如下列两个URL地址中：
	 * url1:http://news.sohu.com/news/20080614/46348532.html
	 * url2:http://news.sina.com/news/20080614/32538532.html
	 * 因此，本方法判断list中各url是否来源于同一站点
	 * 方法是将".com"前的站点名称进行比对，若相同，则认定是同一站点
	 */
	private boolean isFromSameWebsite(){
		boolean isSame = true;
		int size = mainList.size();
		for (int i=1; i<size; i++){
			String url1 = mainList.get(i-1).toString().substring(0, mainList.get(i-1).toString().indexOf(".com"));
			String url2 = mainList.get(i).toString().substring(0, mainList.get(i).toString().indexOf(".com"));
			String temp1 = url1.substring(url1.lastIndexOf(".")+1, url1.length());
			String temp2 = url2.substring(url2.lastIndexOf(".")+1, url2.length());
			if (!temp1.equals(temp2))
				isSame = false;
		}
		return isSame;
	}
	
	/**
	 * 此方法用来获得模版的站点域名
	 */
	public String getWebSite(){
		return this.webSite;
	}
	
	
}
