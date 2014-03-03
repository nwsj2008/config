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
 * @author ���峬 ���泿 ����
 * 
 */

public class BbsParser {
	/**
	 * BBS ��������
	 * 
	 * @param dom
	 *            ��ҳDOM��
	 * @param bbsPattern
	 *            BBS����ģ��
	 * @return bbsData BBS��ȡ��Ϣ��װ
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
			// ��������
			// �ַ���exprTopic�����̳����Xpath���ʽ�������ڵ����ƣ��ڵ����ԣ��ڵ�����ֵ�ͽڵ�XPathֵ
			String exprTopic = bbsPattern.getTitle_XPath().trim();
			// �����ַ���title,������ű��⣬��ʼ��Ϊ��
			String title = null;
			// �ж��ַ���exprTopic�Ƿ�Ϊ��
			if (exprTopic != null && !exprTopic.equals("")) {
				// ��DOM���в�������exprTopic�Ľڵ㲢����nodesT
				NodeList nodesTopic = XPathAPI.selectNodeList(dom, exprTopic);

				// �����б�õ����еı���
				for (int i = 0; i < nodesTopic.getLength(); i++) {
					// �ж��Ƿ��нڵ�
					if (nodesTopic.item(i) != null) {
						// �жϽڵ������Ƿ�Ϊ��
						if (nodesTopic.item(i).getTextContent() != null) {
							// ���ڵ����ݴ���title
							title = nodesTopic.item(i).getTextContent().trim();
							logger.info("���⣺" + title);
							break;
						}
					}
				}
			}
			// ��title����bbsData
			bbsData.setTitle(title);

			// �������

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
							System.out.println("��飺" + edition);
							break;
						}
					}
				}
			}

			bbsData.setEdition(edition);

			// ������������
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

						System.out.println("����������" + reNum);
					}
				}
			}

			bbsData.setReNum(reNum);

			// ����¥������
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
						System.out.println("¥������:" + author);
					}
				}
			}

			bbsData.setAuthor(author);

			// ����¥����������
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
						System.out.println("¥���������ݣ�" + content);
					}
				}
			}

			bbsData.setContent(content);

			// ����¥������ʱ��
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
						 System.out.println("¥������ʱ�䣺" + time);
					} else {
						time = nodesTimeSend.item(bbsPattern.getHosttime())
								.getNextSibling().getTextContent().trim();
						System.out.println("¥������ʱ��:" + time);
					}
				}
			}

			bbsData.setTime(time);

			// ����������������
			String exprReAuthorName = bbsPattern.getReAuthor_XPath().trim();
			int k = 0;
			if (exprReAuthorName != null && !exprReAuthorName.equals("")) {
				NodeList nodesReAuthorName = XPathAPI.selectNodeList(dom,
						exprReAuthorName);
				// ����list���ͣ���Ż�����������
				List<String> reauthorLists = new ArrayList<String>();
				for (int i = bbsPattern.getOthername(); i < nodesReAuthorName
						.getLength(); i++) {
					if (nodesReAuthorName.item(i) != null) {
						if (nodesReAuthorName.item(i).getTextContent() != null) {
							reauthorLists.add(nodesReAuthorName.item(i)
									.getTextContent().trim()==null?" ":nodesReAuthorName.item(i)
											.getTextContent().trim());
							System.out.println("��������������" + k
									+ reauthorLists.get(k));
							k++;
						}
					}
				}
				// ��list����ת��Ϊstring���Ͳ�����bbsData
				bbsData.setReauthor(reauthorLists.toArray(new String[0]));
			}

			// ������������
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
					System.out.println("��������:" + l + recontentList.get(l));
					l++;
				}
			}
			bbsData.setRecontent(recontentList.toArray(new String[0]));

			// ��������ʱ��
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
							System.out.println("����ʱ�䣺" + m + retimeList.get(m));
							m++;
						} else {
							retimeList.add(nodesReTimeSend.item(i)
									.getNextSibling().getTextContent().trim());
							System.out.println("����ʱ�䣺" + m + retimeList.get(m));
							m++;
						}
					}
				}
				bbsData.setRetime(retimeList.toArray(new String[0]));
			}

			// ������������
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
//									.println("�������⣺" + j + retitleList.get(j));
							j++;
						}
					}
				}
				bbsData.setRetitle(retitleList.toArray(new String[0]));
			} else {
				for (int i = bbsPattern.getOthercontent(); i < nodesReContent
						.getLength(); i++) {
					retitleList.add(title);
					//System.out.println("�������⣺" + j + retitleList.get(j));
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
