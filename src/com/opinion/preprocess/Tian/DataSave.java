package com.opinion.preprocess.Tian;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

import ICTCLAS.I3S.AC.SegmentMain;



import edu.opinion.common.db.BasicDAO;
import edu.opinion.common.db.TbParserBbs;
import edu.opinion.common.db.TbReBbs;


/**
 * @author Ų��˾��
 * ƥ������������Ϣ���������ݿ�
 */
public class DataSave {

	private TbParserBbs bbs;
	private TbReBbs rebbs;
	private Logger logger = Logger.getLogger("rootCategory");
	private List hList;

	public void save(List<String> regexList, StringBuilder WebData, String url) {
		/**
		 * ��ƥ�䵽�������ݡ����ߵȵ���Ϣ������ matcher1.group(1) �������� matcher1.group(2) ����ʱ��
		 * matcher1.group(3) �ظ��� matcher1.group(4) ��������
		 * 
		 * matcher0.group(1) �������ٰ�K matcher0.group(2) �������}
		 */
		bbs = new TbParserBbs();
		Matcher matcher1 = Pattern.compile(regexList.get(1)).matcher(WebData);
		if (matcher1.find()) {

			String Author = matcher1.group(1);
			Date ReleaseTime = DataTypeConversion.timeTypeConversion(TagClean
					.Clean(matcher1.group(2)));
			int Renum = Integer.parseInt(matcher1.group(3));
			String Content = TagClean.Clean(matcher1.group(4));
			bbs.setUrl(url);
			bbs.setAuthor(Author);
			bbs.setReleaseTime(ReleaseTime);
			bbs.setReNum(Renum);
			bbs.setIdkey("TianYa_Topic");
			bbs.setContent(Content);

			logger.info(Content);

			Matcher matcher0 = Pattern.compile(regexList.get(0)).matcher(
					WebData);
			if (matcher0.find()) {

				String Tag = matcher0.group(1);
				String Topic = TagClean.Clean(matcher0.group(2));
				List<String> hsList = new ArrayList<String>();
				hsList.add(Tag);
				hsList.add(Topic);
				int hash = GetHashCode.getHashCode(hsList);
				bbs.setHashCode(hash);
				bbs.setTag(Tag);
				bbs.setTopic(Topic);
				bbs.setSpTopic(SegmentMain.Split(bbs.getTopic()));
			
				bbs.setSpContent(SegmentMain.Split(bbs.getContent()));
				bbs.setStorageTime(new Date());
				try {
					StringBuilder sb = new StringBuilder();
					sb.append("From TbParserBbs where hashcode=").append(hash);
					hList = BasicDAO.queryByHql(sb.toString());
					if (hList.size() == 0) {
						//BasicDAO.save(bbs);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		/*
		 * �������Ϣ,ͨ�������еİ�����������ݿ��в���������ͨ������������� matcher4.group(1) ������K
		 * matcher4.group(2) �������}
		 * 
		 * matcher2.group(1) �������� matcher2.group(2) ����ʱ�� matcher2.group(3)); //
		 * ��������
		 */
		else {
			rebbs = new TbReBbs();
			Matcher matcher4 = Pattern.compile(regexList.get(0)).matcher(
					WebData);
			if (matcher4.find()) {

				String Tag = matcher4.group(1);
				String ReTitle = TagClean.Clean(matcher4.group(2));

				rebbs.setUrl(url);
				rebbs.setTag(Tag);
				rebbs.setReTitle(ReTitle);
			}
			Matcher matcher2 = Pattern.compile(regexList.get(2)).matcher(
					WebData);
			while (matcher2.find()) {

				String ReAuthor = matcher2.group(1);
				Date ReTime = DataTypeConversion.timeTypeConversion(matcher2
						.group(2).replaceAll("��", " "));
				String ReContent = TagClean.Clean(matcher2.group(3));
				logger.info(ReAuthor);
				rebbs.setReAuthor(ReAuthor);
				rebbs.setReTime(ReTime);
				List<String> hslist = new ArrayList<String>();
				hslist.add(ReAuthor);
				hslist.add(ReContent);
				int hashcode = GetHashCode.getHashCode(hslist);
				rebbs.setHashCode(hashcode);
				rebbs.setReContent(ReContent);
				rebbs.setIdkey("TianYa_RE");
				rebbs.setSpTitle(bbs.getSpTopic());
		
				rebbs.setSpContent(SegmentMain.Split(rebbs.getReContent()));
				rebbs.setStorageTime(new Date());

				StringBuilder ss = new StringBuilder();
				ss.append("From TbParserBbs where tag='").append(matcher4.group(1))
						.append("'and topic='").append(
								TagClean.Clean(matcher4.group(2))).append("'");
				String hql = ss.toString();

				/**
				 * ���ݿ��в���hashcode�������ظ�����BC
				 */
				try {
					hList = BasicDAO.queryByHql("From TbReBbs where hashcode='"
							+ hashcode + "'");
					List list = BasicDAO.queryByHql(hql);
					if (list.size() > 0 && hList.size() <= 0) {
						bbs = (TbParserBbs) list.get(0);
						rebbs.setTbParserBbs(bbs);
						//BasicDAO.save(rebbs);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
