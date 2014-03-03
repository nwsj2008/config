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
 * @author 张彦超 李舒晨 廉捷
 *
 */

public class NewsDataSave {
	/**
	 * 新闻数据存储
	 * @param newsData NEWS抽取信息封装
	 * @param crawlLog
	 * @param urlPattern URL解析模板
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
		// 哈希值
		int newsHashCode = DataTypeConversion.getHashCode(vector);
		//遍历表中哈希值
		String hql = "from TbParserNews news where news.hashCode = "
				+ newsHashCode;
		List rowList = TbParserNewsDeal.queryTbParserNewsByHql(hql);
		DataTypeConversion newsDataTypeConversion = new DataTypeConversion();
		try {
			// 遍历数据库，如果存不在相同的哈希值则存
			if (rowList.size() == 0) {
				TbParserNews tpb = new TbParserNews();
				tpb.setTitle(title);
				tpb.setTag(edition);
				tpb.setContent(content);
				tpb.setAuthor(author);
				// 将时间转换为日期型存入
				datetime = newsDataTypeConversion.timeTypeConversion(time);
				tpb.setReleaseTime(datetime==null?new Date():datetime);
				// 将回帖数转换为整数型存入
				
				
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
