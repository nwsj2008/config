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
	 * �б��ҳ��Ϊ��̬���߶�̬ҳ��
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
	 * �÷�������URLģ���������
	 * ��ִ�и÷���ǰ��������֤�û����������URL��Ϊ��ЧURL��ַ
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
		if( suffix.length()>1 ){ //����Ǿ�̬ҳ��,����'.html'����ʽ��β,��ִ������Ĳ���
			while (mainList.get(0).toString().indexOf("/")>0){
				this.getContent();
				if (!isTrue){
					break;
				}
			}
			this.dealEnd(mainList);
	        regex = regex + suffix;
	        this.replaceRegex();
		}else{ //����Ƕ�̬ҳ��,����'.jsp?id=****'����ʽ��β,��ִ������Ĳ���
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
	 * 1)�ж�ĳURL���ʽ������'/'֮�������������,��ĸ,���ǻ���ַ�
	 * 2)�ж������ַ�����ƥ�����
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
	 * �õ�URL��ַ��HTTP://ǰ׺,����:"http://www.sohu.com/"
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
	 * ����URL������'/'֮�������,���������URL������'/'֮������ȫ����ͬ,�򱻶���Ϊȫ��ƥ��ģʽ
	 * ����ͬ,����Ϊ����ƥ��ģʽ,����������ʽͨ������getType(String content)����ʵ��
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
	 * ����̬ҳ���ҳ������ƥ��,����:
	 * http://www.aaa.com/bbb/ccc/ddd.html�������һ��'/'��,��ddd����ƥ�����
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
	 * �ṩ�ⲿ�������ô������ɵ�ҳ��ƥ��������ʽ:regex
	 */
	public String getRegex(){
		return this.regex;
	}
	
	/**
	 * ��������ʽ��"."�滻Ϊ"\\."
	 * ��������ʽ��"?"�滻Ϊ"\\?"
	 * ����'.'��'?'������������Ҫת��
	 * ��'\'��JAVA��Ҫת��Ϊ:"\\"
	 */
	private void replaceRegex(){
		regex = regex.replace(".", "\\.");
		regex = regex.replace("?", "\\?");
	}
	
	/**
	 * �ж������URL�У���/�����ų��ִ����Ƿ���ͬ
	 * �����ͬ����Ϊ�ǿ��Գ�������ģ���URL����
	 * ����ͬ���򷵻�false,��Ҫ�û�����������ʽ��ͬ��URL����
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
	 * �˷�����Ҫ��������URL������ƥ������
	 * ������Ҫ������:news.sohu.com��travel.sohu.com
	 * ��ƥ�����
	 * ��Ҫע����Ƕ�������:computer.it.sohu.com��sports.sohu.com������;��ͬURL������ƥ�����
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
	 * �ж�������List�и��ַ�����ĳ�ַ����ִ����Ƿ���ͬ
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
	 * ���ĳlist��,�������ַ�'chr'���ִ������ٵ�listԪ��
	 * ����list.get(0)���ַ���"abc"����2��,
	 * list.get(1)���ַ���"abc"����1��,
	 * list.get(2)���ַ���"abc"����4��,
	 * �򷵻�int��1
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
	 * �Զ�̬ҳ���У�url��ַ��ϢΨһ'?'֮ǰ�����ݽ���ƥ��
	 * ͨ���ڶ�̬ҳ���У�'?'֮ǰ�Ĳ��ֶ�����ͬ�ģ���˱�����'?'֮ǰ�Ĳ��ֽ����ַ���ȫƥ�䴦��
	 * ����http://www.thegreatwall.com.cn/phpbbs/index.php?id=100380&forumid=1
	 * �����URL��ַ�У�regex����http://www.thegreatwall.com.cn/phpbbs/index.php?�����ַ�������ȫƥ��
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
	 * �Զ�̬ҳ���У�url��ַ��ϢΨһ'?'֮������ݽ���ƥ��
	 * ���˼·Ϊͨ��"&"���Ž�URL��ַ��'?'֮��Ĳ������ֿ�
	 * ����http://www.thegreatwall.com.cn/phpbbs/index.php?id=100380&forumid=1
	 * �����URL��ַ�У�'?'֮��Ĳ��ֱ��ָ�ɣ�id=100380��forumid=1���Ӷ����������ֽ��зֱ�ƥ��
	 * ��Ҫע����ǣ���ЩURL��ַ��β���ְ���"#"���磺http://zhidao.baidu.com/q?ct=24&cm=16&tn=ikusercenter#ask
	 * ������ж�ʱ��Ӧ�����Ƚ�URL���հ���"#"�벻����"#"�ֱַ���
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
	 * �˷��������ж������ַ������Ƿ����ĳ�ض��ַ�
	 */
	private boolean isExist(String chr,String content){
		if(content.indexOf(chr)==-1)
			return false;
		else
			return true;
	}
	
	/**
	 * ��URLƥ�䷽���У����ڳ�����ͬ����ʽ��ͬ��ȴ���Բ�ͬվ���URL��ַ�����ܶ��䱸����Ľ���������ȫ�����Ӱ��
	 * ������������URL��ַ�У�
	 * url1:http://news.sohu.com/news/20080614/46348532.html
	 * url2:http://news.sina.com/news/20080614/32538532.html
	 * ��ˣ��������ж�list�и�url�Ƿ���Դ��ͬһվ��
	 * �����ǽ�".com"ǰ��վ�����ƽ��бȶԣ�����ͬ�����϶���ͬһվ��
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
	 * �˷����������ģ���վ������
	 */
	public String getWebSite(){
		return this.webSite;
	}
	
	
}
