package edu.opinion.preprocess.bjtu;

import java.util.Iterator;
import java.util.Vector;

import edu.opinion.preprocess.Dom4jXML.BbsXmlParserImp;
import edu.opinion.preprocess.Dom4jXML.NewsXmlParserImp;
import edu.opinion.preprocess.pattern.*;

/**
 * 
 * PatternMatch�����ģ���ƥ��
 * 
 * @author ���泿�����ݣ����峬
 * 
 */
public class PatternMatch {
	
	/**
	 * BBSģ��ƥ��
	 * @param urlPattern	����BBSurl����������urlPattern,�����װurl������������Ϣ
	 * @return	bbsPattern	���õ�ģ�����
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
	 * ����ģ��ƥ��
	 * @param urlPattern	��������url����������urlPattern,�����װurl������������Ϣ
	 * @return	newsPattern	���õ�ģ�����
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
