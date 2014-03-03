package edu.opinion.preprocess.bjtu;

import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.opinion.preprocess.pattern.UrlPattern;

/**
 * url����
 * @author ���ݣ����泿�����峬
 *
 */
public class UrlParser {
	
	UrlPattern urlPatt;
	
	public UrlPattern getPattern(String url, Vector vector){
		try{
			Iterator<UrlPattern> itr = vector.iterator(); 
	        UrlPattern patt = new UrlPattern();
	        while(itr.hasNext()){  
	        	patt = itr.next();
	            String regex = patt.getRegex();
	            Pattern pattern = Pattern.compile(regex);
	            Matcher matcher = pattern.matcher(url);
	            if (matcher.matches()){
	            	System.out.println("��ҳ"+url+"���Ա�����");
	            	urlPatt = new UrlPattern();
	            	urlPatt = patt;
	            	String idKey = getIdKey(url,patt.getIdName(),patt.getPrefix(),patt.getSufix());
	            	if (idKey!=null)
	            		urlPatt.setIdKey(idKey);       	
	            	break;
	            }              
	        
	        }
	        if (urlPatt!=null)
	        	return urlPatt;
	        else
	        	return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	public String getIdKey(String url, String idName, String prefix, String sufix){
		int digit = prefix.length();
		try{
			if (sufix!=null && sufix.length()>0){
				String subUrlTem = url.substring(url.lastIndexOf(prefix)+digit, url.length());
				String subUrl = subUrlTem.substring(0, subUrlTem.indexOf(sufix));
				return idName + subUrl;
			}else{
				String subUrl = url.substring(url.lastIndexOf(prefix)+digit, url.length());
				return idName + subUrl;
			}			
		}catch (StringIndexOutOfBoundsException e){
			e.printStackTrace();
			System.out.println("URLģ����ƥ���ֶγ���");
			return "xxxx";
		}
		
	}

}
