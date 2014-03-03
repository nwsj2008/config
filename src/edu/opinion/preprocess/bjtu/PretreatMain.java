package edu.opinion.preprocess.bjtu;

import java.util.Iterator;
import java.util.Vector;

import org.w3c.dom.Document;

import com.opinion.preprocess.Tian.ProcessTianYa;

import edu.opinion.preprocess.Dom4jXML.BbsXmlParserImp;
import edu.opinion.preprocess.Dom4jXML.FileParserImp;
import edu.opinion.preprocess.Dom4jXML.IBbsXmlParser;
import edu.opinion.preprocess.Dom4jXML.IFileParser;
import edu.opinion.preprocess.Dom4jXML.IUrlParser;
import edu.opinion.preprocess.Dom4jXML.NewsXmlParserImp;
import edu.opinion.preprocess.Dom4jXML.UrlParserImp;
import edu.opinion.preprocess.io.CrawlLog;
import edu.opinion.preprocess.io.FileGet;
import edu.opinion.preprocess.io.GetLog;
import edu.opinion.preprocess.pattern.BbsData;
import edu.opinion.preprocess.pattern.BbsPattern;
import edu.opinion.preprocess.pattern.FileRecord;
import edu.opinion.preprocess.pattern.NewsData;
import edu.opinion.preprocess.pattern.NewsPattern;

public class PretreatMain {

	/**
	 * 模块的主入口，完成各功能功能模块的串联，主要思路流程：读取爬虫生成的log文件，根据URL进行是否可以解析的判断，生成新的log日志
	 * 选择合适的解析模板进行页面信息解析，对解析内容进行处理： 1) 信息再次提取 2）分词 最后将解析出的信息存入数据库，以备后期的分析。
	 * 
	 */

	public static boolean isFinished_BBS = false;
	public static boolean isFinished_SEGMENT = false;

	public static void pretreatMainBBS() {

		try {

			// 读取BBS与REBBS与URL模板XML文件,2009-5-30
			IUrlParser urlParser = new UrlParserImp();
			Vector urlPatternVector = (Vector) urlParser.getXMLElements(); // url
			// 模板解析
			IBbsXmlParser bbsParser = new BbsXmlParserImp();
			Vector bbsPatternVector = (Vector) bbsParser.getXMLElements(); // bbs
			// 模板解析

			NewsXmlParserImp newsParser = new NewsXmlParserImp();
			Vector newsPatternVector = (Vector) newsParser.getXMLElements(); // 新闻模板解析

			IFileParser fileParser = new FileParserImp(); // 读取filerecord 配置文件
			FileRecord fr = fileParser.getFileRecord();
			if (fr == null) {
				System.out.print("配置文件信息读取失败");
				return;
			}

			// 生成新的日志文件，在FileRecord指定目录下
			FileGet f = new FileGet();
			boolean isDone = f.fileGenerator(fr.getPath(), urlPatternVector, fr
					.getLogPath());
			if (!isDone) {
				System.out.println("日志文件生成失败");
				return;
			} else {
				System.out.println("已生成日志文件");
			}

			Vector v_CrawlLog = GetLog.getCrawlLog(fr.getLogPath());

			int n = 0;
			BbsData bbsData;
			BbsDataTreat dataTreat = new BbsDataTreat();
			NewsData newsData;
			NewsDataSave newsDataSave = new NewsDataSave();

			for (Iterator it = v_CrawlLog.iterator(); it.hasNext();) {

				if (isFinished_BBS) {
					break;
				}
				CrawlLog crawlLog = (CrawlLog) it.next();
				String url = crawlLog.getUrl();
				String fileName = crawlLog.getFileName();
				String module = crawlLog.getModule();
				String key = crawlLog.getKey();
				String charset = crawlLog.getCharset();

				Document dom = GetLog.getSourceNode(fileName, true, charset);
				if (dom == null || !dom.hasChildNodes()) {
					System.out.println("dom generate error!!!!");
					return;
				}

				BbsPattern bbsPattern = PatternMatch.bbsPatternMatch(module,
						bbsPatternVector);
				if (bbsPattern != null) {
					bbsData = BbsParser.bbsParser(dom, bbsPattern, charset);
					System.out.println(bbsPattern.getBbs_module());
					if (module.equals(bbsPattern.getBbs_module())) {
						dataTreat.bbsTopic(bbsData, url, key);
					}

				}

				NewsPattern newsPattern = PatternMatch.newsPatternMatch(module,
						newsPatternVector);
				if (newsPattern != null) {
					newsData = NewsParser.newsParser(dom, newsPattern);
					System.out.println(newsPattern.getNews_module());
					if (module.equals(newsPattern.getNews_module())) {
						NewsDataSave.newsDataSave(newsData, url, key);
					}
				}

				n++;

			}

			// FileMove.moveFile(fr.getPath(), fr.getBackupPath());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			isFinished_BBS = true;
		}
	}

	/**
	 * 特思论坛预处理模块的主入口，完成各功能功能模块的串联，主要思路流程： 1) 从数据库中读取TESI论坛记录 2）对记录进行分词
	 * 3）将结果重新存入数据库，以备后期的分析。
	 * 
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			 PretreatMain.pretreatMainBBS();
			 ProcessTianYa pty = new ProcessTianYa();
			 pty.PreTreatBBS();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}