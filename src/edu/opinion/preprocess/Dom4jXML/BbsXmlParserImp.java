package edu.opinion.preprocess.Dom4jXML;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import edu.opinion.preprocess.pattern.BbsPattern;
/**
 * 读取BBS模板（xml文件）
 * @author 李舒晨，张彦超，廉捷
 *
 */
public class BbsXmlParserImp implements IBbsXmlParser {

	private Document doc = null;

	public BbsXmlParserImp() {
		this.bulidDocument();
	}
	/**
	 * 建立Dom结构
	 */
	public void bulidDocument() {
		SAXReader saxReader = new SAXReader();
		try {
			String filepath = System.getProperty("user.dir")+"\\XMLPattern\\bbs_pattern.xml";
			File file = new File(filepath);
			doc = saxReader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取xml元素
	 */
	public Vector getXMLElements() {
		Vector<BbsPattern> vector = new Vector<BbsPattern>();
		try {
			Element root = doc.getRootElement();
			List childs = root.elements("item");

			Iterator itr = childs.iterator();
			while (itr.hasNext()) {
				BbsPattern bbsPatt = new BbsPattern();
				Element child = (Element) itr.next();

				bbsPatt.setBbs_name(child.attributeValue("name"));
				bbsPatt.setBbs_module(child.attributeValue("module"));

				Element describe = child.element("describe");
				int hostname = ("").equals(describe.elementTextTrim("hostname")) ? 0
						: Integer.valueOf(describe.elementTextTrim("hostname"));
				bbsPatt.setHostname(hostname);
				int hostcontent = describe.elementTextTrim("hostcontent")
						.equals("") ? 0 : Integer.valueOf(describe
						.elementTextTrim("hostcontent"));
				bbsPatt.setHostcontent(hostcontent);
				int hosttime = describe.elementTextTrim("hosttime").equals("") ? 0
						: Integer.valueOf(describe.elementTextTrim("hosttime"));
				bbsPatt.setHosttime(hosttime);
				int othername = describe.elementTextTrim("othername")
						.equals("") ? 0 : Integer.valueOf(describe
						.elementTextTrim("othername"));
				bbsPatt.setOthername(othername);
				int othercontent = describe.elementTextTrim("othercontent")
						.equals("") ? 0 : Integer.valueOf(describe
						.elementTextTrim("othercontent"));
				bbsPatt.setOthercontent(othercontent);
				int othertime = describe.elementTextTrim("othertime")
						.equals("") ? 0 : Integer.valueOf(describe
						.elementTextTrim("othertime"));
				bbsPatt.setOthertime(othertime);
				bbsPatt.setBbs_url(describe.elementTextTrim("url"));

				Element title = child.element("title");
				bbsPatt.setTitle_XPath(title.elementTextTrim("title_xpath"));

				Element edition = child.element("edition");
				bbsPatt.setEdition_XPath(edition
						.elementTextTrim("edition_xpath"));

				Element author = child.element("author");
				bbsPatt.setAuthor_XPath(author.elementTextTrim("author_xpath"));

				Element content = child.element("content");
				bbsPatt.setContent_XPath(content
						.elementTextTrim("content_xpath"));

				Element time = child.element("time");
				bbsPatt.setTime_XPath(time.elementTextTrim("time_xpath"));

				Element reNum = child.element("reNum");
				bbsPatt.setReNum_XPath(reNum.elementTextTrim("reNum_xpath"));

				Element retitle = child.element("retitle");
				bbsPatt.setReTitle_XPath(retitle
						.elementTextTrim("retitle_xpath"));

				Element reauthor = child.element("reauthor");
				bbsPatt.setReAuthor_XPath(reauthor
						.elementTextTrim("reauthor_xpath"));

				Element recontent = child.element("recontent");
				bbsPatt.setReContent_XPath(recontent
						.elementTextTrim("recontent_xpath"));

				Element retime = child.element("retime");
				bbsPatt.setReTime_XPath(retime.elementTextTrim("retime_xpath"));

				vector.addElement(bbsPatt);
			}
			return vector;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 更新xml节点信息
	 */

	public boolean updateXMLElement(BbsPattern bbsPattern) {
		try {
			String bbsUrl = bbsPattern.getBbs_url();
			String bbsName = bbsPattern.getBbs_name();
			String bbsModule = bbsPattern.getBbs_module();
			int hostName = bbsPattern.getHostname();
			int hostContent = bbsPattern.getHostcontent();
			int hostTime = bbsPattern.getHosttime();
			int otherName = bbsPattern.getOthername();
			int otherContent = bbsPattern.getOthercontent();
			int otherTime = bbsPattern.getOthertime();
			String title_XPath = bbsPattern.getTitle_XPath();
			String edition_XPath = bbsPattern.getEdition_XPath();
			String author_XPath = bbsPattern.getAuthor_XPath();
			String content_XPath = bbsPattern.getContent_XPath();
			String time_XPath = bbsPattern.getTime_XPath();
			String reNum_XPath = bbsPattern.getReNum_XPath();
			String reTitle_XPath = bbsPattern.getReTitle_XPath();
			String reAuthor_XPath = bbsPattern.getReAuthor_XPath();
			String reContent_XPath = bbsPattern.getReContent_XPath();
			String reTime_XPath = bbsPattern.getReTime_XPath();

			Element root = doc.getRootElement();
			
			Element bbsUrlElm = (Element) root.element("url");

			Element hostNameElm = (Element) root.element("hostname");

			Element hostContentElm = (Element) root.element("hostcontent");
			
			Element hostTimeElm = (Element) root.element("hosttime");
			
			Element otherNameElm = (Element) root.element("othername");
			
			Element otherContentElm = (Element) root.element("othercontent");
			
			Element otherTimeElm = (Element) root.element("othertime");

			Element title_XPathElm = (Element) root.element("title_xpath");

			Element edition_XPathElm = root.element("edition_xpath");

			Element author_XPathElm = root.element("author_xpath");

			Element content_XPathElm = root.element("content_xpath");

			Element time_XPathElm = (Element) root.element("time_xpath");

			Element reNum_XPathElm = (Element) root.element("reNum_xpath");

			Element reTitle_XPathElm = (Element) root.element("retitle_xpath");

			Element reAuthor_XPathElm = (Element) root
					.element("reauthor_xpath");

			Element reContent_XPathElm = (Element) root
					.element("recontent_xpath");

			Element reTime_XPathElm = (Element) root.element("retime_xpath");

			title_XPathElm.setText(title_XPath);
			edition_XPathElm.setText(edition_XPath);
			author_XPathElm.setText(author_XPath);
			content_XPathElm.setText(content_XPath);
			time_XPathElm.setText(time_XPath);
			reNum_XPathElm.setText(reNum_XPath);
			reTitle_XPathElm.setText(reTitle_XPath);
			reAuthor_XPathElm.setText(reAuthor_XPath);
			reContent_XPathElm.setText(reContent_XPath);
			reTime_XPathElm.setText(reTime_XPath);

			return save();

		} catch (Exception ex) {
			System.out.println("更新新结点信息未能成功");
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean addXMLElement(BbsPattern bbsPattern) {
		try {
			String bbsUrl = bbsPattern.getBbs_url();
			String bbsName = bbsPattern.getBbs_name();
			String bbsModule = bbsPattern.getBbs_module();
			int hostName = bbsPattern.getHostname()== null ? 0 :bbsPattern.getHostname();
			int hostContent = bbsPattern.getHostcontent() == null ? 0 :bbsPattern.getHostcontent();
			int hostTime = bbsPattern.getHosttime()== null ? 0 : bbsPattern.getHosttime();
			int otherName = bbsPattern.getOthername()== null ? 0 : bbsPattern.getOthername();
			int otherContent = bbsPattern.getOthercontent()== null ? 0 : bbsPattern.getOthercontent();
			int otherTime = bbsPattern.getOthertime()== null ? 0 : bbsPattern.getOthertime();
			String title_XPath = bbsPattern.getTitle_XPath();
			String edition_XPath = bbsPattern.getEdition_XPath();
			String author_XPath = bbsPattern.getAuthor_XPath();
			String content_XPath = bbsPattern.getContent_XPath();
			String time_XPath = bbsPattern.getTime_XPath();
			String reNum_XPath = bbsPattern.getReNum_XPath();
			String reTitle_XPath = bbsPattern.getReTitle_XPath();
			String reAuthor_XPath = bbsPattern.getReAuthor_XPath();
			String reContent_XPath = bbsPattern.getReContent_XPath();
			String reTime_XPath = bbsPattern.getReTime_XPath();

			Element root = doc.getRootElement();
			
			Element itemElm = root.addElement("item");
			itemElm.addAttribute("name", bbsName);
			itemElm.addAttribute("module", bbsModule);
			
			Element describeElm = itemElm.addElement("describe");
			
			Element bbsUrlElm = describeElm.addElement("url");
			bbsUrlElm.setText(bbsUrl);

			Element hostNameElm = describeElm.addElement("hostname");
			hostNameElm.setText(String.valueOf(hostName));

			Element hostContentElm = describeElm.addElement("hostcontent");
			hostContentElm.setText(String.valueOf(hostContent));
			
			Element hostTimeElm = describeElm.addElement("hosttime");
			hostTimeElm.setText(String.valueOf(hostTime));
			
			Element otherNameElm = describeElm.addElement("othername");
			otherNameElm.setText(String.valueOf(otherName));
			
			Element otherContentElm = describeElm.addElement("othercontent");
			otherContentElm.setText(String.valueOf(otherContent));
			
			Element otherTimeElm = describeElm.addElement("othertime");
			otherTimeElm.setText(String.valueOf(otherTime));
			
			Element titleElm = itemElm.addElement("title");
			Element title_XPathElm = titleElm.addElement("title_xpath");
			title_XPathElm.setText(title_XPath);
			
			Element editionElm = itemElm.addElement("edition");
			Element edition_XPathElm = editionElm.addElement("edition_xpath");
			edition_XPathElm.setText(edition_XPath);

			Element authorElm = itemElm.addElement("author");
			Element author_XPathElm = authorElm.addElement("author_xpath");
			author_XPathElm.setText(author_XPath);

			Element contentElm = itemElm.addElement("content");
			Element content_XPathElm = contentElm.addElement("content_xpath");
			content_XPathElm.setText(content_XPath);

			Element timeElm = itemElm.addElement("time");
			Element time_XPathElm = timeElm.addElement("time_xpath");
			time_XPathElm.setText(time_XPath);

			Element reNumElm = itemElm.addElement("reNum");
			Element reNum_XPathElm = reNumElm.addElement("reNum_xpath");
			reNum_XPathElm.setText(reNum_XPath);

			Element reTitleElm = itemElm.addElement("retitle");
			Element reTitle_XPathElm = reTitleElm.addElement("retitle_xpath");
			reTitle_XPathElm.setText(reTitle_XPath);

			Element reAuthorElm = itemElm.addElement("reauthor");
			Element reAuthor_XPathElm = reAuthorElm.addElement("reauthor_xpath");
			reAuthor_XPathElm.setText(reAuthor_XPath);

			Element reContentElm = itemElm.addElement("recontent");
			Element reContent_XPathElm = reContentElm.addElement("recontent_xpath");
			reContent_XPathElm.setText(reContent_XPath);

			Element reTimeElm = itemElm.addElement("retime");
			Element reTime_XPathElm = reTimeElm.addElement("retime_xpath");
			reTime_XPathElm.setText(reTime_XPath);

			return save();

		} catch (Exception ex) {
			System.out.println("添加新结点信息未能成功");
			ex.printStackTrace();
			return false;
		}
	}
	
	
	private boolean save() {
		// TODO Auto-generated method stub
		try {
			OutputFormat outFmt = OutputFormat.createPrettyPrint();
			outFmt.setEncoding("GBK");
			outFmt.setIndent("    ");
			XMLWriter xmlWriter = new XMLWriter(new FileWriter(new File(
					"XMLPattern/bbs_pattern.xml")), outFmt);
			xmlWriter.write(doc);
			xmlWriter.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	 public static void main(String[] args){
		 BbsXmlParserImp bpi = new BbsXmlParserImp();
		 Vector v = bpi.getXMLElements();
		 String conNode = ((BbsPattern)v.get(0)).getContent_XPath();
//		 String conAttribution = ((BbsPattern)v.get(0)).getContent_attribution_value();
		 System.out.println(conNode);
//		 System.out.println(conAttribution);
		 System.out.println(((BbsPattern)v.get(0)).getTitle_XPath());
		 System.out.println(((BbsPattern)v.get(0)).getBbs_name());
		 System.out.println(((BbsPattern)v.get(0)).getHostname());
		 System.out.println(((BbsPattern)v.get(0)).getBbs_url());
		 
		 bpi.addXMLElement((BbsPattern)v.get(0));
		 System.out.println("OK");
	 }
}
