package edu.opinion.preprocess.bjtu;

import java.util.Iterator;
import java.util.Vector;

import edu.opinion.preprocess.Dom4jXML.BbsXmlParserImp;
import edu.opinion.preprocess.Dom4jXML.NewsXmlParserImp;
import edu.opinion.preprocess.pattern.*;

/**
 * 
 * PatternMatch类完成模板的匹配
 * 
 * @author 李舒晨，廉捷，张彦超
 * 
 */
public class PatternMatch {
	
	/**
	 * BBS模板匹配
	 * @param urlPattern	经过BBSurl解析产生的urlPattern,里面封装url解析的特征信息
	 * @return	bbsPattern	采用的模板对象
	 */
	public static BbsPattern bbsPatternMatch(String module, Vector vector) {
		try{
			BbsPattern bbsPattern = null;
			outfor: for (Iterator it = vector.iterator(); it.hasNext();) {
				bbsPattern = (BbsPattern)it.next();
				if (bbsPattern.getBbs_module().equals(module)) {
					break outfor;
				}
				bbsPattern = null;
			}
			return bbsPattern;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * 新闻模板匹配
	 * @param urlPattern	经过新闻url解析产生的urlPattern,里面封装url解析的特征信息
	 * @return	newsPattern	采用的模板对象
	 */
	
	public static NewsPattern newsPatternMatch(String module, Vector vector) {
		try{
			NewsPattern newsPattern = null;
			outfor: for (Iterator it = vector.iterator(); it.hasNext();) {
				newsPattern = (NewsPattern)it.next();
				if (newsPattern.getNews_module().equals(module)) {
					break outfor;
				}
				newsPattern = null;
			}
			return newsPattern;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	

}
