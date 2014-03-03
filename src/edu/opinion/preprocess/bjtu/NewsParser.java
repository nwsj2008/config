package edu.opinion.preprocess.bjtu;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import com.sun.org.apache.xpath.internal.XPathAPI;

import edu.opinion.preprocess.pattern.NewsData;
import edu.opinion.preprocess.pattern.NewsPattern;

/**
 * 
 * @author 张彦超 李舒晨 廉捷
 * 
 */

public class NewsParser {
	/**
	 * News解析方法
	 * 
	 * @param dom
	 *            网页DOM树
	 * @param newsPattern
	 *            News解析模板
	 * @return newsData News抽取信息封装
	 * @throws TransformerException
	 */
	public static NewsData newsParser(Document dom, NewsPattern newsPattern)
			throws TransformerException {
		NewsData newsData = new NewsData();
		try {
			// 解析新闻标题
			// 字符串exprTopic存放新闻标题节点名称，节点属性，节点属性值和节点XPath值
			String exprTopic = newsPattern.getTitle_XPath().trim();
			// 在DOM树中查找满足exprTopic的节点并存入nodesTopic
			NodeList nodesTopic = XPathAPI.selectNodeList(dom, exprTopic);
			// 定义字符串title,用来存放标题，初始化为空
			String title = null;
			// 判断是否有节点
			if (nodesTopic.item(0) != null) {
				// 将节点内容存入title
				title = nodesTopic.item(0).getTextContent().trim();
				System.out.println("新闻标题：" + title);
			}
			// 将title存入newsData
			newsData.setTitle(title);

			// 解析版块
			String exprEdition = newsPattern.getEdition_XPath().trim();
			NodeList nodesEdition = XPathAPI.selectNodeList(dom, exprEdition);
			String edition = null;
			if (nodesEdition.item(0) != null) {
				edition = nodesEdition.item(0).getTextContent().trim();
				System.out.println("版块：" + edition);
			}
			newsData.setEdition(edition);

			// 解析评论数量
			String exprNumberReversion = newsPattern.getReNum_XPath().trim();
			NodeList nodesNumberReversion = XPathAPI.selectNodeList(dom,
					exprNumberReversion);
			String reNum = null;
			if (nodesNumberReversion.item(0) != null
					&& !nodesNumberReversion.item(0).equals("")) {
				reNum = nodesNumberReversion.item(0).getTextContent().trim();
				System.out.println("评论数量：" + reNum);
			} else {
				reNum = "0";
				System.out.println("评论数量(页面没有)：" + reNum);
			}
			newsData.setReNum(reNum);

			// 解析新闻来源
			String exprAuthorName = newsPattern.getAuthor_XPath().trim();
			NodeList nodesAuthorName = XPathAPI.selectNodeList(dom,
					exprAuthorName);
			String author = null;
			if (nodesAuthorName.item(0) != null) {
				author = nodesAuthorName.item(0).getTextContent().trim();
				System.out.println("新闻来源:" + author);
			}
			newsData.setAuthor(author);

			// 解析新闻内容
			String exprContent = newsPattern.getContent_XPath().trim();
			NodeList nodesContent = XPathAPI.selectNodeList(dom, exprContent);
			String content = null;

			if (nodesContent.item(0) != null) {
				content = nodesContent.item(0).getTextContent().trim();
				if (nodesContent.item(1) != null) {
					content += nodesContent.item(1).getTextContent().trim();
				}
				System.out.println("新闻内容："+content);
			}
			System.out.println("新闻内容：" + content);
			newsData.setContent(content);

			// 解析新闻时间
			String exprTimeSend = newsPattern.getTime_XPath().trim();
			NodeList nodesTimeSend = XPathAPI.selectNodeList(dom, exprTimeSend);
			String time = null;
			if (nodesTimeSend.item(0) != null) {
				time = nodesTimeSend.item(0).getTextContent().trim();
				System.out.println("新闻时间：" + time);
			}
			newsData.setTime(time);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return newsData;

	}
}
