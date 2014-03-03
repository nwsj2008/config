package edu.opinion.preprocess.bjtu;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xpath.internal.XPathAPI;

import edu.opinion.preprocess.pattern.BbsData;
import edu.opinion.preprocess.pattern.BbsPattern;

/**
 * 
 * @author 张彦超 李舒晨 廉捷
 * 
 */

public class BbsParser {
	/**
	 * BBS 解析方法
	 * 
	 * @param dom
	 *            网页DOM树
	 * @param bbsPattern
	 *            BBS解析模板
	 * @return bbsData BBS抽取信息封装
	 * @throws TransformerException
	 */

	private static Logger logger = Logger.getRootLogger();

	public static BbsData bbsParser(Document dom, BbsPattern bbsPattern,
			String charset) throws TransformerException {
		String blankString = "";
		try {
			blankString = new String(new byte[] { -62, -96 }, charset);

		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BbsData bbsData = new BbsData();
		try {
			// 解析标题
			// 字符串exprTopic存放论坛标题Xpath表达式，包括节点名称，节点属性，节点属性值和节点XPath值
			String exprTopic = bbsPattern.getTitle_XPath().trim();
			// 定义字符串title,用来存放标题，初始化为空
			String title = null;
			// 判断字符串exprTopic是否为空
			if (exprTopic != null && !exprTopic.equals("")) {
				// 在DOM树中查找满足exprTopic的节点并存入nodesT
				NodeList nodesTopic = XPathAPI.selectNodeList(dom, exprTopic);

				// 遍历列表得到所有的标题
				for (int i = 0; i < nodesTopic.getLength(); i++) {
					// 判断是否有节点
					if (nodesTopic.item(i) != null) {
						// 判断节点内容是否为空
						if (nodesTopic.item(i).getTextContent() != null) {
							// 将节点内容存入title
							title = nodesTopic.item(i).getTextContent().trim();
							logger.info("标题：" + title);
							break;
						}
					}
				}
			}
			// 将title存入bbsData
			bbsData.setTitle(title);

			// 解析版块

			String exprEdition = bbsPattern.getEdition_XPath().trim();
			String edition = null;
			if (exprEdition != null && !exprEdition.equals("")) {
				NodeList nodesEdition = XPathAPI.selectNodeList(dom,
						exprEdition);
				for (int i = 0; i < nodesEdition.getLength(); i++) {
					if (nodesEdition.item(i) != null) {
						if (nodesEdition.item(i).getTextContent() != null) {
							edition = nodesEdition.item(i).getTextContent()
									.trim();
							System.out.println("版块：" + edition);
							break;
						}
					}
				}
			}

			bbsData.setEdition(edition);

			// 解析回帖数量
			String exprNumberReversion = bbsPattern.getReNum_XPath().trim();
			String reNum = null;
			// String mm = "";
			if (exprNumberReversion != null && !exprNumberReversion.equals("")) {
				NodeList nodesNumberReversion = XPathAPI.selectNodeList(dom,
						exprNumberReversion);
				if (nodesNumberReversion.item(0) != null) {
					if (nodesNumberReversion.item(0).getTextContent() != null) {
						reNum = nodesNumberReversion.item(0).getTextContent()
								.trim();

						System.out.println("回帖数量：" + reNum);
					}
				}
			}

			bbsData.setReNum(reNum);

			// 解析楼主姓名
			String exprAuthorName = bbsPattern.getAuthor_XPath().trim();
			String author = null;
			if (exprAuthorName != null && !exprAuthorName.equals("")) {
				NodeList nodesAuthorName = XPathAPI.selectNodeList(dom,
						exprAuthorName);
				if (nodesAuthorName.item(bbsPattern.getHostname()) != null) {
					if (nodesAuthorName.item(bbsPattern.getHostname())
							.getTextContent() != null) {
						author = nodesAuthorName.item(bbsPattern.getHostname())
								.getTextContent().trim();
						System.out.println("楼主姓名:" + author);
					}
				}
			}

			bbsData.setAuthor(author);

			// 解析楼主发帖内容
			String exprContent = bbsPattern.getContent_XPath().trim();
			String content = null;
			if (exprContent != null && !exprContent.equals("")) {
				NodeList nodesContent = XPathAPI.selectNodeList(dom,
						exprContent);
				if (nodesContent.item(bbsPattern.getHostcontent()) != null) {
					if (nodesContent.item(bbsPattern.getHostcontent())
							.getTextContent() != null) {
						content = nodesContent
								.item(bbsPattern.getHostcontent())
								.getTextContent().replaceAll("<[\\s\\S]*>", "")
								.replace(blankString, "").replace("\n", "")
								.trim();
						System.out.println("楼主发帖内容：" + content);
					}
				}
			}

			bbsData.setContent(content);

			// 解析楼主发帖时间
			String exprTimeSend = bbsPattern.getTime_XPath().trim();
			String time = null;
			if (exprTimeSend != null && !exprTimeSend.equals("")) {
				NodeList nodesTimeSend = XPathAPI.selectNodeList(dom,
						exprTimeSend);
				if (nodesTimeSend.item(bbsPattern.getHosttime()) != null) {
					if (nodesTimeSend.item(bbsPattern.getHosttime())
							.getTextContent() != null
							&& !nodesTimeSend.item(bbsPattern.getHosttime())
									.getTextContent().equals("")) {
						time = nodesTimeSend.item(bbsPattern.getHosttime())
								.getTextContent().trim();
						 System.out.println("楼主发帖时间：" + time);
					} else {
						time = nodesTimeSend.item(bbsPattern.getHosttime())
								.getNextSibling().getTextContent().trim();
						System.out.println("楼主发帖时间:" + time);
					}
				}
			}

			bbsData.setTime(time);

			// 解析回帖作者姓名
			String exprReAuthorName = bbsPattern.getReAuthor_XPath().trim();
			int k = 0;
			if (exprReAuthorName != null && !exprReAuthorName.equals("")) {
				NodeList nodesReAuthorName = XPathAPI.selectNodeList(dom,
						exprReAuthorName);
				// 定义list类型，存放回帖作者姓名
				List<String> reauthorLists = new ArrayList<String>();
				for (int i = bbsPattern.getOthername(); i < nodesReAuthorName
						.getLength(); i++) {
					if (nodesReAuthorName.item(i) != null) {
						if (nodesReAuthorName.item(i).getTextContent() != null) {
							reauthorLists.add(nodesReAuthorName.item(i)
									.getTextContent().trim()==null?" ":nodesReAuthorName.item(i)
											.getTextContent().trim());
							System.out.println("回帖作者姓名：" + k
									+ reauthorLists.get(k));
							k++;
						}
					}
				}
				// 将list类型转换为string类型并存入bbsData
				bbsData.setReauthor(reauthorLists.toArray(new String[0]));
			}

			// 解析回帖内容
			String exprReContent = bbsPattern.getReContent_XPath().trim();
			int l = 0;
			NodeList nodesReContent = XPathAPI.selectNodeList(dom,
					exprReContent);
			List<String> recontentList = new ArrayList<String>();
			for (int i = bbsPattern.getOthercontent(); i < nodesReContent
					.getLength(); i++) {
				if (nodesReContent.item(i) != null) {
					recontentList.add(nodesReContent.item(i).getTextContent()
							.trim());
					System.out.println("回帖内容:" + l + recontentList.get(l));
					l++;
				}
			}
			bbsData.setRecontent(recontentList.toArray(new String[0]));

			// 解析回帖时间
			String exprReTimeSend = bbsPattern.getReTime_XPath().trim();
			int m = 0;
			if (exprReTimeSend != null && !exprReTimeSend.equals("")) {
				NodeList nodesReTimeSend = XPathAPI.selectNodeList(dom,
						exprReTimeSend);
				List<String> retimeList = new ArrayList<String>();
				for (int i = bbsPattern.getOthertime(); i < nodesReTimeSend
						.getLength(); i++) {
					if (nodesReTimeSend.item(i) != null) {
						if (nodesReTimeSend.item(i).getTextContent() != null
								&& !nodesReTimeSend.item(i).getTextContent()
										.equals("")) {
							retimeList.add(nodesReTimeSend.item(i)
									.getTextContent().trim());
							System.out.println("回帖时间：" + m + retimeList.get(m));
							m++;
						} else {
							retimeList.add(nodesReTimeSend.item(i)
									.getNextSibling().getTextContent().trim());
							System.out.println("回帖时间：" + m + retimeList.get(m));
							m++;
						}
					}
				}
				bbsData.setRetime(retimeList.toArray(new String[0]));
			}

			// 解析回帖标题
			String exprReTopic = bbsPattern.getReTitle_XPath().trim();
			int j = 0;
			List<String> retitleList = new ArrayList<String>();
			if (exprReTopic != null && !exprReTopic.equals("")) {
				NodeList nodesReTopic = XPathAPI.selectNodeList(dom,
						exprReTopic);
				for (int i = bbsPattern.getOthercontent(); i < nodesReContent
						.getLength(); i++) {
					if (nodesReTopic.item(0) != null) {
						if (nodesReTopic.item(0).getTextContent() != null) {
							retitleList.add(nodesReTopic.item(0)
									.getTextContent().trim());
//							System.out
//									.println("回帖标题：" + j + retitleList.get(j));
							j++;
						}
					}
				}
				bbsData.setRetitle(retitleList.toArray(new String[0]));
			} else {
				for (int i = bbsPattern.getOthercontent(); i < nodesReContent
						.getLength(); i++) {
					retitleList.add(title);
					//System.out.println("回帖标题：" + j + retitleList.get(j));
					j++;
				}
				bbsData.setRetitle(retitleList.toArray(new String[0]));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bbsData;
	}

}
