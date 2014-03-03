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
	 * ģ�������ڣ���ɸ����ܹ���ģ��Ĵ�������Ҫ˼·���̣���ȡ�������ɵ�log�ļ�������URL�����Ƿ���Խ������жϣ������µ�log��־
	 * ѡ����ʵĽ���ģ�����ҳ����Ϣ�������Խ������ݽ��д��� 1) ��Ϣ�ٴ���ȡ 2���ִ� ��󽫽���������Ϣ�������ݿ⣬�Ա����ڵķ�����
	 * 
	 */

	public static boolean isFinished_BBS = false;
	public static boolean isFinished_SEGMENT = false;

	public static void pretreatMainBBS() {

		try {

			// ��ȡBBS��REBBS��URLģ��XML�ļ�,2009-5-30
			IUrlParser urlParser = new UrlParserImp();
			Vector urlPatternVector = (Vector) urlParser.getXMLElements(); // url
			// ģ�����
			IBbsXmlParser bbsParser = new BbsXmlParserImp();
			Vector bbsPatternVector = (Vector) bbsParser.getXMLElements(); // bbs
			// ģ�����

			NewsXmlParserImp newsParser = new NewsXmlParserImp();
			Vector newsPatternVector = (Vector) newsParser.getXMLElements(); // ����ģ�����

			IFileParser fileParser = new FileParserImp(); // ��ȡfilerecord �����ļ�
			FileRecord fr = fileParser.getFileRecord();
			if (fr == null) {
				System.out.print("�����ļ���Ϣ��ȡʧ��");
				return;
			}

			// �����µ���־�ļ�����FileRecordָ��Ŀ¼��
			FileGet f = new FileGet();
			boolean isDone = f.fileGenerator(fr.getPath(), urlPatternVector, fr
					.getLogPath());
			if (!isDone) {
				System.out.println("��־�ļ�����ʧ��");
				return;
			} else {
				System.out.println("��������־�ļ�");
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
	 * ��˼��̳Ԥ����ģ�������ڣ���ɸ����ܹ���ģ��Ĵ�������Ҫ˼·���̣� 1) �����ݿ��ж�ȡTESI��̳��¼ 2���Լ�¼���зִ�
	 * 3����������´������ݿ⣬�Ա����ڵķ�����
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