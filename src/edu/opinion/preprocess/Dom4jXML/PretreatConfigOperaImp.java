package edu.opinion.preprocess.Dom4jXML;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import edu.opinion.preprocess.bjtu.PretreatConfigBean;

public class PretreatConfigOperaImp implements IPretreatConfigOpera {
	private Document doc = null;

	/**
	 * PretreatConfigOperaImp类构造方法
	 * 
	 */
	public PretreatConfigOperaImp() {
		this.bulidDocument();
	}

	/**
	 * 建立Dom结构
	 */
	public void bulidDocument() {
		SAXReader saxReader = new SAXReader();
		try {
			File file = new File("PretreatXML/pretreat_config.xml");
			doc = saxReader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取XML元素
	 */
	public Object getXMLElements() {

		try {
			Element root = doc.getRootElement();

			PretreatConfigBean preConfBean = new PretreatConfigBean();

			Element pretreat_isAutoElm = root.element("pretreat_isAuto");

			int isAuto = pretreat_isAutoElm.getTextTrim().equals("") ? 0
					: Integer.valueOf(pretreat_isAutoElm.getTextTrim());
			preConfBean.setIsAuto(isAuto);

			Element pretreat_intervalElm = root.element("pretreat_interval");
			long interval = pretreat_intervalElm.getTextTrim().equals("") ? 0
					: Long.valueOf(pretreat_intervalElm.getTextTrim());
			preConfBean.setInterval(interval);

			Element pretreat_startTimeElm = root.element("pretreat_startTime");
			String startTime = pretreat_startTimeElm.getTextTrim().equals("") ? ""
					: pretreat_startTimeElm.getTextTrim();
			preConfBean.setStartTime(startTime);

			return preConfBean;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 更新xml节点信息
	 * 
	 * @param readLastLine
	 *            目前日志读取中所读的最后一行的标记
	 * @return 是否成功更新节点数据
	 */
	public boolean updateXMLElement(PretreatConfigBean pretreatConfigBean) {
		try {
			int isAuto = pretreatConfigBean.getIsAuto() == null ? 0
					: pretreatConfigBean.getIsAuto();
			long interval = pretreatConfigBean.getInterval() == null ? 0
					: pretreatConfigBean.getInterval();
			String startTime = pretreatConfigBean.getStartTime() == null ? ""
					: pretreatConfigBean.getStartTime();

			Element root = doc.getRootElement();

			Element pretreat_isAutoElm = root.element("pretreat_isAuto");

			Element pretreat_intervalElm = root.element("pretreat_interval");

			Element pretreat_startTimeElm = root.element("pretreat_startTime");

			pretreat_isAutoElm.setText(String.valueOf(isAuto));
			pretreat_intervalElm.setText(String.valueOf(interval));
			pretreat_startTimeElm.setText(startTime);

			return save();

		} catch (Exception ex) {
			System.out.println("更新结点信息失败");
			ex.printStackTrace();
			return false;
		}
	}

	public boolean save() {
		try {
			OutputFormat outFmt = OutputFormat.createPrettyPrint();
			outFmt.setEncoding("GBK");
			outFmt.setIndent("    ");
			XMLWriter xmlWriter = new XMLWriter(new FileWriter(new File(
					"PretreatXML/pretreat_config.xml")), outFmt);
			xmlWriter.write(doc);
			xmlWriter.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
