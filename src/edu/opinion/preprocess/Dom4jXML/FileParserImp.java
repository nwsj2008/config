package edu.opinion.preprocess.Dom4jXML;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import edu.opinion.preprocess.pattern.FileRecord;

;

public class FileParserImp implements IFileParser {

	private Document doc = null;

	public FileRecord getFileRecord() {
		SAXReader saxReader = new SAXReader();
		FileRecord fr = new FileRecord();
		try {
			String filepath = System.getProperty("user.dir")
					+ "\\conf\\FileRecord.xml";
			File file = new File(filepath);
			doc = saxReader.read(file);
			Element root = doc.getRootElement();
			List childs = root.elements("para");
			Element child1 = (Element) childs.get(0);
			fr.setPath(child1.elementText("para_value"));
			Element child2 = (Element) childs.get(1);
			fr.setBackupPath(child2.elementText("para_value"));
			Element child3 = (Element) childs.get(2);
			fr.setLogPath(child3.elementText("para_value"));
		
			return fr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
