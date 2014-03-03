package edu.opinion.preprocess.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import edu.opinion.preprocess.io.CrawlLog;

public class GetLog {
	
	/**
	 * 读取Log方法
	 * 
	 * @param path
	 *            日志所在路径
	 * 
	 * @return 封装了CrawlLog类的Vector
	 */

	public static Vector getCrawlLog(String path) throws Exception, Error {
		Vector<CrawlLog> v_crawlLog = new Vector<CrawlLog>();
		try{
			
			File input = new File(path);
			FileReader fr = new FileReader(input);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] info = line.split(";");
				CrawlLog cl = new CrawlLog();
				cl.setFileName(info[0]);
				cl.setUrl(info[1]);
				cl.setModule(info[2]);
				cl.setKey(info[3]);
				cl.setCharset(info[4]);
				v_crawlLog.addElement(cl);
			}
			fr.close();
			br.close();
			return v_crawlLog;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 
	 * @param path
	 *            要解析的HTML文件存放地址
	 * @param fromfile
	 *            HTML文件是否来源于文件
	 * @return 解析生成的网页DOM树
	 * @throws Exception
	 * @throws Error
	 */
	public static Document getSourceNode(String path, boolean fromfile,String charset)
			throws Exception, Error {
		DOMParser parser = new DOMParser();
		parser.setFeature("http://xml.org/sax/features/namespaces", false);
		Document dom = null;

		if (path != null && !path.trim().equals("")) {
			String tmp = path;
			// System.out.println(path);
			if (fromfile) {
				
				FileInputStream input = new FileInputStream(path);
				InputStreamReader fr = new InputStreamReader(input,charset);
				InputSource is = new InputSource(fr);
				parser.parse(is);
				fr.close();
			} else {

				URL url = new URL(tmp);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				InputStream inputs = con.getInputStream();
				InputStreamReader isr = new InputStreamReader(inputs, charset);
				InputSource source = new InputSource(isr);
				parser.parse(source);
			}
			dom = parser.getDocument();
			if (dom != null)
				dom.normalize();
			return dom;
		} else {
			return null;
		}

	}


}
