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

import edu.opinion.preprocess.pattern.UrlPattern;
/**
 * 读取url模板（xml文件）
 * @author 廉捷，李舒晨，张彦超
 *
 */
public class UrlParserImp implements IUrlParser {
	
	private Document doc = null;
	private String filepath;
	
	public UrlParserImp(){
		this.bulidDocument();
	}
	
	/**
	 * 建立Dom结构
	 */
	public void bulidDocument(){
		SAXReader saxReader = new SAXReader();
		try{
			filepath = System.getProperty("user.dir")+"\\XMLPattern\\url_pattern.xml";
//			System.out.println("filepath:"+filepath);
			File file = new File(filepath);
			doc = saxReader.read(file);
		}catch (DocumentException e)
		{
			e.printStackTrace();
		}	
	}
	
	/**
	 * 获取xml元素
	 */
	public Vector getXMLElements(){
		Vector vector = new Vector();
		try{
			Element root = doc.getRootElement();
			List childs = root.elements("item");
			Iterator itr = childs.iterator();
			while (itr.hasNext()) {
				UrlPattern patt = new UrlPattern();
				Element child = (Element)itr.next();
				
				patt.setUrl(child.elementText("url"));
				patt.setRegex(child.elementText("regex"));
				patt.setModule(child.elementText("module"));
				patt.setName(child.attributeValue("name"));
				patt.setPrefix(child.elementText("prefix"));
				patt.setSufix(child.elementText("sufix"));
				patt.setIdName(child.elementText("idName"));
				patt.setSort(child.attributeValue("sort"));
				
				vector.addElement(patt);
			}		
		
			return vector;
		}catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}	
	}
	
	
	public boolean addElement(UrlPattern patt){
		try{
			Element item = doc.getRootElement().addElement("item");
			item.addAttribute("name", patt.getName());
			item.addAttribute("sort", patt.getSort());
			Element url = item.addElement("url");
			url.setText(patt.getUrl());
			Element regex = item.addElement("regex");
			regex.setText("regex");
			//Element module = item.addElement("module",patt.getModule());
			Element module = item.addElement("module");
			module.setText(patt.getModule());
			Element idName = item.addElement("idName");
			idName.setText(patt.getIdName());
			Element prefix = item.addElement("prefix");
			prefix.setText(patt.getPrefix());
			Element sufix = item.addElement("sufix");
			sufix.setText(patt.getSufix());
			return save();
		}catch(Exception ex)
		{
			System.out.println("添加URL-pattern新结点信息失败");
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean save(){
		try{
			OutputFormat outFmt =OutputFormat.createPrettyPrint();
		    outFmt.setEncoding("GBK");
		    outFmt.setIndent("    ");
		    XMLWriter xmlWriter = new XMLWriter(new FileWriter(new File(filepath)),outFmt);
		    xmlWriter.write(doc);
		    xmlWriter.close();
		    return true;
		}catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}

}
