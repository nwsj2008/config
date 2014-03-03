package edu.opinion.preprocess.bjtu;

import java.util.Date;
import java.util.Vector;
import java.util.List;

import ICTCLAS.I3S.AC.SegmentMain;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import edu.opinion.common.db.TbParserNews;
import edu.opinion.preprocess.io.CrawlLog;
import edu.opinion.preprocess.pattern.NewsData;
import edu.opinion.preprocess.pattern.UrlPattern;

/**
 * 
 * @author ���峬 ���泿 ����
 *
 */

public class NewsDataSave {
	/**
	 * �������ݴ洢
	 * @param newsData NEWS��ȡ��Ϣ��װ
	 * @param crawlLog
	 * @param urlPattern URL����ģ��
	 * @return
	 * @throws Exception
	 */
  
	public static boolean newsDataSave(NewsData newsData, String url,
			String idKey) throws Exception {
		boolean ifSaveSuccess = false;
		String title = newsData.getTitle();
		String edition = newsData.getEdition()==null?"":newsData.getEdition();
		String author = newsData.getAuthor();
		String content = newsData.getContent();
		String time = newsData.getTime()==null?"":newsData.getTime();
		String reNum = newsData.getReNum();
		Date datetime = null;
		int intrenum;
//		String url = crawlLog.getUrl();
//		String idKey = urlPattern.getIdKey();

		Vector vector = new Vector();
		vector.addElement(title);
		vector.addElement(url);
		vector.addElement(time);
		// ��ϣֵ
		int newsHashCode = DataTypeConversion.getHashCode(vector);
		//�������й�ϣֵ
		String hql = "from TbParserNews news where news.hashCode = "
				+ newsHashCode;
		List rowList = TbParserNewsDeal.queryTbParserNewsByHql(hql);
		DataTypeConversion newsDataTypeConversion = new DataTypeConversion();
		try {
			// �������ݿ⣬����治����ͬ�Ĺ�ϣֵ���
			if (rowList.size() == 0) {
				TbParserNews tpb = new TbParserNews();
				tpb.setTitle(title);
				tpb.setTag(edition);
				tpb.setContent(content);
				tpb.setAuthor(author);
				// ��ʱ��ת��Ϊ�����ʹ���
				datetime = newsDataTypeConversion.timeTypeConversion(time);
				tpb.setReleaseTime(datetime==null?new Date():datetime);
				// ��������ת��Ϊ�����ʹ���
				
				
				intrenum = newsDataTypeConversion.reNumtypeConversion(reNum);
				tpb.setReNum(intrenum);
				tpb.setUrl(url);
				tpb.setIdkey(idKey);
				tpb.setHashCode(newsHashCode);
				tpb.setSpTitle(SegmentMain.Split(title==null?"":title));
				tpb.setSpContent(SegmentMain.Split(content));
				tpb.setStorageTime(new Date());
				TbParserNewsDeal.addTbParserNews(tpb);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return ifSaveSuccess;
	}
}
