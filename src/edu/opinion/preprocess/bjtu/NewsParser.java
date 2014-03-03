package edu.opinion.preprocess.bjtu;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import com.sun.org.apache.xpath.internal.XPathAPI;

import edu.opinion.preprocess.pattern.NewsData;
import edu.opinion.preprocess.pattern.NewsPattern;

/**
 * 
 * @author ���峬 ���泿 ����
 * 
 */

public class NewsParser {
	/**
	 * News��������
	 * 
	 * @param dom
	 *            ��ҳDOM��
	 * @param newsPattern
	 *            News����ģ��
	 * @return newsData News��ȡ��Ϣ��װ
	 * @throws TransformerException
	 */
	public static NewsData newsParser(Document dom, NewsPattern newsPattern)
			throws TransformerException {
		NewsData newsData = new NewsData();
		try {
			// �������ű���
			// �ַ���exprTopic������ű���ڵ����ƣ��ڵ����ԣ��ڵ�����ֵ�ͽڵ�XPathֵ
			String exprTopic = newsPattern.getTitle_XPath().trim();
			// ��DOM���в�������exprTopic�Ľڵ㲢����nodesTopic
			NodeList nodesTopic = XPathAPI.selectNodeList(dom, exprTopic);
			// �����ַ���title,������ű��⣬��ʼ��Ϊ��
			String title = null;
			// �ж��Ƿ��нڵ�
			if (nodesTopic.item(0) != null) {
				// ���ڵ����ݴ���title
				title = nodesTopic.item(0).getTextContent().trim();
				System.out.println("���ű��⣺" + title);
			}
			// ��title����newsData
			newsData.setTitle(title);

			// �������
			String exprEdition = newsPattern.getEdition_XPath().trim();
			NodeList nodesEdition = XPathAPI.selectNodeList(dom, exprEdition);
			String edition = null;
			if (nodesEdition.item(0) != null) {
				edition = nodesEdition.item(0).getTextContent().trim();
				System.out.println("��飺" + edition);
			}
			newsData.setEdition(edition);

			// ������������
			String exprNumberReversion = newsPattern.getReNum_XPath().trim();
			NodeList nodesNumberReversion = XPathAPI.selectNodeList(dom,
					exprNumberReversion);
			String reNum = null;
			if (nodesNumberReversion.item(0) != null
					&& !nodesNumberReversion.item(0).equals("")) {
				reNum = nodesNumberReversion.item(0).getTextContent().trim();
				System.out.println("����������" + reNum);
			} else {
				reNum = "0";
				System.out.println("��������(ҳ��û��)��" + reNum);
			}
			newsData.setReNum(reNum);

			// ����������Դ
			String exprAuthorName = newsPattern.getAuthor_XPath().trim();
			NodeList nodesAuthorName = XPathAPI.selectNodeList(dom,
					exprAuthorName);
			String author = null;
			if (nodesAuthorName.item(0) != null) {
				author = nodesAuthorName.item(0).getTextContent().trim();
				System.out.println("������Դ:" + author);
			}
			newsData.setAuthor(author);

			// ������������
			String exprContent = newsPattern.getContent_XPath().trim();
			NodeList nodesContent = XPathAPI.selectNodeList(dom, exprContent);
			String content = null;

			if (nodesContent.item(0) != null) {
				content = nodesContent.item(0).getTextContent().trim();
				if (nodesContent.item(1) != null) {
					content += nodesContent.item(1).getTextContent().trim();
				}
				System.out.println("�������ݣ�"+content);
			}
			System.out.println("�������ݣ�" + content);
			newsData.setContent(content);

			// ��������ʱ��
			String exprTimeSend = newsPattern.getTime_XPath().trim();
			NodeList nodesTimeSend = XPathAPI.selectNodeList(dom, exprTimeSend);
			String time = null;
			if (nodesTimeSend.item(0) != null) {
				time = nodesTimeSend.item(0).getTextContent().trim();
				System.out.println("����ʱ�䣺" + time);
			}
			newsData.setTime(time);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return newsData;

	}
}
