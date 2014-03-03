package com.opinion.preprocess.Tian;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import edu.opinion.preprocess.bjtu.PretreatMain;

/**
 * @author 挪威司机 主程序的入口
 *
 */
public class ProcessTianYa {

	private PreThreadFactory PHF = PreThreadFactory.getInstance();
	private String filePath;
	public void PreTreatBBS() throws InterruptedException {

		List<File> fileList = new ArrayList<File>();
		
		Document document = null;
		SAXReader saReader = new SAXReader();
		try {
			document = saReader.read(new File("conf/FileRecord.xml"));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Element> eList = document.selectNodes("//opinion:para");
		
		for (Element element : eList) {
			List<Element> names = element.selectNodes("opinion:para_name");
			List<Element> values = element.selectNodes("opinion:para_value");
			String name = names.get(0).getText();
			if ("Path".equals(name)) {
				 filePath = values.get(0).getText();
			}
		}
		File file = new File(filePath);
		File[] crawlFiles = file.listFiles();
		for (File file2 : crawlFiles) {
			fileList.add(file2);
		}
		
		while (fileList.size() > 0) {
			File file1;
			synchronized (fileList) {
				file1 = fileList.get(0);
				fileList.remove(0);
			}
			PreThread preThread = PHF.getThread(file1);
			
			if (preThread== null) {
				preThread = PHF.getThread(file1);
			}
			Thread.sleep(500);
			Thread thread = new Thread(preThread);
			thread.start();					//启动多线程			
		}
	}
	
	public static void main(String[] args) throws InterruptedException {

		ProcessTianYa PM = new ProcessTianYa();
		PM.PreTreatBBS();
//	PretreatMain.pretreatMainBBS();
	}
}
