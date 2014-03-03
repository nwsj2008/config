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

import edu.opinion.preprocess.pattern.NewsPattern;

/**
 * 读取新闻模板（xml文件）
 * @author 李舒晨，廉捷，张彦超
 *
 */
public class NewsXmlParserImp implements INewsXmlParser {
	private Document doc = null;

	public NewsXmlParserImp() {
		this.bulidDocument();
	}
	/**
	 * 建立Dom结构
	 */
	public void bulidDocument() {
		SAXReader saxReader = new SAXReader();
		try {
			File file = new File("XMLpattern/news_pattern.xml");
			doc = saxReader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取xml元素
	 */
	public Vector getXMLElements() {
		Vector<NewsPattern> vector = new Vector<NewsPattern>();
		try {
			Element root = doc.getRootElement();
			List childs = root.elements("item");

			Iterator itr = childs.iterator();
			while (itr.hasNext()) {
				NewsPattern newsPatt = new NewsPattern();

				Element child = (Element) itr.next();

				newsPatt.setNews_name(child.attributeValue("name"));
				newsPatt.setNews_module(child.attributeValue("module"));

				Element describe = child.element("describe");
				newsPatt.setNews_url(describe.elementTextTrim("url"));

				Element title = child.element("title");
				newsPatt.setTitle_XPath(title.elementTextTrim("title_xpath"));
	

				Element edition = child.element("edition");
				newsPatt.setEdition_XPath(edition
						.elementTextTrim("edition_xpath"));
	

				Element author = child.element("author");
				newsPatt.setAuthor_XPath(author.elementTextTrim("author_xpath"));
		

				Element content = child.element("content");
				newsPatt.setContent_XPath(content
						.elementTextTrim("content_xpath"));
		

				Element time = child.element("time");
				newsPatt.setTime_XPath(time.elementTextTrim("time_xpath"));


				Element reNum = child.element("reNum");
				newsPatt.setReNum_XPath(reNum.elementTextTrim("reNum_xpath"));


				vector.addElement(newsPatt);

			}
			return vector;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean addXMLElement(NewsPattern newsPattern) {
		try {
			String bbsUrl = newsPattern.getNews_url();
			String bbsName = newsPattern.getNews_name();
			String bbsModule = newsPattern.getNews_module();
			
			String title_XPath = newsPattern.getTitle_XPath();
			String edition_XPath = newsPattern.getEdition_XPath();
			String author_XPath = newsPattern.getAuthor_XPath();
			String content_XPath = newsPattern.getContent_XPath();
			String time_XPath = newsPattern.getTime_XPath();
			String reNum_XPath = newsPattern.getReNum_XPath();

			Element root = doc.getRootElement();
			
			Element itemElm = root.addElement("item");
			itemElm.addAttribute("name", bbsName);
			itemElm.addAttribute("module", bbsModule);
			
			Element describeElm = itemElm.addElement("describe");
			
			Element bbsUrlElm = describeElm.addElement("url");
			bbsUrlElm.setText(bbsUrl);

			
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
					"PretreatXML/news_pattern.xml")), outFmt);
			xmlWriter.write(doc);
			xmlWriter.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
//	public static void main(String[] args) {
//		NewsXmlParserImp bpi = new NewsXmlParserImp();
//		Vector v = bpi.getXMLElements();
//		System.out.println(((NewsPattern) v.get(0)).getNews_name());
//		System.out.println(((NewsPattern) v.get(2)).getAuthor_attribution_value());
//		System.out.println(((NewsPattern) v.get(0)).getTitle_node());
//	}

}
