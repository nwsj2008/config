package edu.opinion.preprocess.bjtu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ICTCLAS.I3S.AC.SegmentMain;


import edu.opinion.common.db.BasicDAO;
import edu.opinion.common.db.TbParserBbs;
import edu.opinion.common.db.TbReBbs;
import edu.opinion.preprocess.dbmanage.BbsSave;
import edu.opinion.preprocess.pattern.BbsData;

public class BbsDataTreat {

	private BbsData bbs = new BbsData();
	private String url;
	private String key;
	private int reNum2;

	private void bbsTopicDeal() {
		try {
			if (bbs.getTitle() != null) {

				TbParserBbs parserBbs = new TbParserBbs();
				parserBbs.setUrl(url);
				parserBbs.setIdkey(key);

				parserBbs.setTopic(this.getTopic(bbs.getTitle()));
				if (bbs.getAuthor() == null) {
					parserBbs.setAuthor("SYS-deliver");
				} else {
					parserBbs.setAuthor(bbs.getAuthor());
				}

				String conTopic = new String(bbs.getContent().getBytes(), "GBK");

				Date releaseTime = DataTypeConversion.timeTypeConversion(bbs
						.getTime() == null ? "" : bbs.getTime());
				// Date releaseTime = this.getTime(conTopic);
				if (releaseTime != null) {
					parserBbs.setReleaseTime(releaseTime);
				} else {
					parserBbs.setReleaseTime(new Date());
				}

					String content = this.getContent(conTopic);
					for (int i = 0; i < 31; i++) {
						StringBuilder sb= new StringBuilder();
						sb.append((char)i);
						content = content.replaceAll(sb.toString(), "");
					}
				parserBbs.setContent(content);

				if (bbs.getReNum() != null) {
					reNum2 = DataTypeConversion.reNumtypeConversion(bbs
							.getReNum());
					parserBbs.setReNum(reNum2);
				} else {
					parserBbs.setReNum(0);
				}
				parserBbs.setTag(bbs.getEdition());
				Vector<String> vector = new Vector<String>();
				vector.add(bbs.getTitle());
				vector.add(bbs.getAuthor());
				vector.add(String.valueOf(bbs.getTime()));
				// 哈希值
				parserBbs.setHashCode(this.getHashCode(vector));


				try {
					parserBbs.setSpTopic(SegmentMain
							.Split(parserBbs.getTopic()));
					parserBbs.setSpContent(SegmentMain.Split(parserBbs
							.getContent()));
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				parserBbs.setStorageTime(new Date());

				BbsSave.saveTopic(parserBbs);
				TbReBbs reBbs = new TbReBbs();

				if (bbs.getRecontent().length > 0) {

					for (int i = 0; i < bbs.getReauthor().length; i++) {
						// TbParserBbs bbsTopic =
						// BbsSave.getTopicBbsByIdkey(key);

						// TbReBbs reBbs = new TbReBbs();
						// reBbs.setTbParserBbs(bbsTopic);
						reBbs.setTbParserBbs(parserBbs);
						reBbs.setUrl(url);
						reBbs.setIdkey(key);
						reBbs.setReTitle(this.getTopic(bbs.getTitle()));
						if (bbs.getReauthor()[i] == null) {
							reBbs.setReAuthor("SYS-deliver");
						} else {
							reBbs.setReAuthor(bbs.getReauthor()[i]);

						}
						// String conRe = new
						// String(bbs.getRecontent()[i].getBytes(),
						// "GBK");
						String conRe = bbs.getRecontent()[i];
						if (bbs.getRetime() != null) {
							Date reTime = DataTypeConversion
									.timeTypeConversion(bbs.getRetime()[i]);
							reBbs.setReTime(reTime==null?new Date():reTime);
						} else {
							reBbs.setReTime(new Date());
						}

						reBbs.setTag(bbs.getEdition());
						reBbs.setReContent(this.getContent(conRe));
						reBbs.setSpTitle(parserBbs.getSpTopic());
						try {
							reBbs.setSpContent(SegmentMain.Split(reBbs
									.getReContent()));
						} catch (RuntimeException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						Vector<String> vectorRe = new Vector<String>();
						vectorRe.add(reBbs.getReTitle());
						vectorRe.add(reBbs.getReAuthor());
						vectorRe.add((reBbs.getReTime() == null ? new Date()
								: reBbs.getReTime()).toString());
						reBbs.setHashCode(this.getHashCode(vectorRe));
						reBbs.setStorageTime(new Date());
						BbsSave.saveRe(reBbs);
					}
				}
			}

			else {
				// 存回复页面
				TbReBbs trb = new TbReBbs();
				Date[] redatetime = new Date[bbs.getRetime().length];
				for (int i = 0; i < redatetime.length; i++) {
					redatetime[i] = DataTypeConversion.timeTypeConversion(bbs
							.getRetime()[i] == null ? "" : bbs.getRetime()[i]);
					Vector<String> vector = new Vector<String>();

					trb.setReAuthor(bbs.getReauthor()[i]);
					trb.setReContent(bbs.getRecontent()[i]);
					try {
						trb.setSpContent(SegmentMain.Split(trb.getReContent()));
						Thread.sleep(100);
					} catch (RuntimeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// 将回帖时间转换为日期型存入46楼 2009-07-19 20:56:13

					trb.setReTime(redatetime[i] == null ? new Date()
							: redatetime[i]);
					
					trb.setIdkey(key);
					trb.setUrl(url);
					trb.setStorageTime(new Date());

					String hql_for_tbParserBbs = "from TbParserBbs tpb where tpb.idkey='"
							+ key + "'";
					List tbParserBbsList = BasicDAO
							.queryByHql(hql_for_tbParserBbs);
					if (tbParserBbsList.size() > 0) {
						TbParserBbs tbParserBbs = (TbParserBbs) tbParserBbsList
								.get(0);
						trb.setTbParserBbs(tbParserBbs);
						trb.setReTitle(tbParserBbs.getTopic());
						trb.setSpTitle(tbParserBbs.getSpTopic());
						trb.setTag(tbParserBbs.getTag());
						vector.add(bbs.getRetitle()[i]);
						vector.add(bbs.getReauthor()[i]);
						vector.add(bbs.getRecontent()[i]);
						vector.add(bbs.getRetime()[i]);
						trb.setHashCode(this.getHashCode(vector));
						BbsSave.saveRe(trb);
					}

					// }
				}
			}

		} catch (Exception eBbsTopicDeal) {
			eBbsTopicDeal.printStackTrace();
		}
	}

	private void bbsReDeal() {
		try {
			TbParserBbs bbsTopic = BbsSave.getTopicBbsByIdkey(key);
			if (bbsTopic != null) {
				for (int i = 0; 1 < bbs.getReauthor().length; i++) {
					TbReBbs reBbs = new TbReBbs();
					reBbs.setTbParserBbs(bbsTopic);
					reBbs.setUrl(url);
					reBbs.setIdkey(key);
					reBbs.setReTitle(this.getTopic(bbs.getRetitle()[i]));

					Date reTime = this.getTime(bbs.getRecontent()[i]);
					if (reTime != null) {
						reBbs.setReTime(reTime);
					}
					reBbs.setTag(bbs.getEdition());
					reBbs.setReContent(this.getContent(bbs.getRecontent()[i]));
					Vector<String> vectorRe = new Vector<String>();
					vectorRe.add(reBbs.getReTitle());
					vectorRe.add(reBbs.getReAuthor());
					vectorRe.add(reBbs.getReTime().toString());
					reBbs.setHashCode(this.getHashCode(vectorRe));
					// BbsSave.saveRe(reBbs);
				}
			}
		} catch (Exception eBbsReDeal) {
			eBbsReDeal.printStackTrace();
		}
	}

	public void bbsTopic(BbsData bbsData, String u, String k) {
		try {
			bbs = bbsData;
			url = u;
			key = k;
			System.out.println("url-->" + url);
			this.bbsTopicDeal();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void bbsRe(BbsData bbsData, String u, String k) {
		try {
			bbs = bbsData;
			url = u;
			key = k;
			System.out.println("url-->" + url);
			this.bbsReDeal();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private int getHashCode(Vector needToCov) {
		String s_needToCov = "";
		for (Object obj : needToCov) {
			if (obj != null) {
				s_needToCov += obj.toString();
			}
		}
		int covHasCode = s_needToCov.hashCode();
		return covHasCode;
	}

	private String getTopic(String topic) {
		try {
			// topic = topic.substring(9);
			return topic;
		} catch (Exception eGetTopic) {
			eGetTopic.printStackTrace();
			return null;
		}
	}

	private Date getTime(String content) {
		try {
			Date date = new Date();
			String DatePattern1 = "[A-Z][a-z][a-z] [A-Z][a-z][a-z] [1-3][0-9] [0-2][0-9]:[0-5][0-9]:[0-5][0-9] [1-2][0-9][0-9][0-9]";
			Pattern pattern1 = Pattern.compile(DatePattern1);
			Matcher matcher1 = pattern1.matcher(content);
			if (matcher1.find()) {
				String timeString = matcher1.group();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"EEE MMM dd HH:mm:ss yyyy", Locale.US);
				// SimpleTimeZone aZone = new SimpleTimeZone(8,"GMT");
				// sdf.setTimeZone(aZone);
				date = sdf.parse(timeString);
			} else {
				String DatePattern2 = "[A-Z][a-z][a-z] [A-Z][a-z][a-z] \\?[1-9] [0-2][0-9]:[0-5][0-9]:[0-5][0-9] [1-2][0-9][0-9][0-9]";
				Pattern pattern2 = Pattern.compile(DatePattern2);
				Matcher matcher2 = pattern2.matcher(content);
				if (matcher2.find()) {
					String timeString = matcher2.group();
					SimpleDateFormat sdf = new SimpleDateFormat(
							"EEE MMM ?d HH:mm:ss yyyy", Locale.US);
					// SimpleTimeZone aZone = new SimpleTimeZone(8,"GMT");
					// sdf.setTimeZone(aZone);
					date = sdf.parse(timeString);
				}
			}
			return date;
		} catch (Exception eGetTime) {
			eGetTime.printStackTrace();
			return null;
		}
	}

	private String getContent(String content) {
		try {
			int index = content.indexOf("站内");
			if (index != -1) {
				content = content.substring(index + 6);
			}
			int end = content.indexOf("※");
			if (end - 1 > 0) {
				content = content.substring(0, end - 1);
			}
			if (content.indexOf(" ? ? --  ?") > 0) {
				content = content.substring(0, content.indexOf(" ? ? --  ?"));
			} else {
				if (content.indexOf(" ? --  ?") > 0) {
					content = content.substring(0, content.indexOf(" ? --  ?"));
				}
			}
			content = content.replaceAll("在 \\S+ \\(\\S+\\) 的大作中提到:", "");
			return content;
		} catch (Exception eGetContent) {
			eGetContent.printStackTrace();
			return null;
		}
	}

}
