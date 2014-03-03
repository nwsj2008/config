package com.opinion.preprocess.Tian;

import java.io.File;
import java.util.List;




public class PreThread implements Runnable {
	/*
	 * author:hsr 
	 */
	private boolean isFinish = false;
	private File file;
	public PreThread(File file) {
		this.file = file;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void refresh(String path) {
		File f = new File(path);
		File[] listFiles = f.listFiles();
		String filename = "";
		String url = "";
		String charset = "";
		for (File file2 : listFiles) {
			if (file2.isDirectory()) {
				refresh(file2.getAbsolutePath());
			} else {
				if (file2.getName().matches("log.txt")) {
					String logPath = file2.getAbsolutePath();
					List<CrawlLog> crawList = UrlParser.urlMatch(logPath);
					Regex regex = new Regex();
					List<String> regexList = regex.GetRegex();// 将正则表达式放入链表中

					for (CrawlLog crawlLog : crawList) {
						filename = crawlLog.getFileName();
						url = crawlLog.getUrl();
						charset = crawlLog.getCharset().substring(8)
								.toUpperCase();

						StringBuilder WebData = new StringBuilder(FileContent
								.getContent(filename, charset));// 得到一个爬虫文件的源码

						DataSave dataSave = new DataSave();
						dataSave.save(regexList, WebData, url);
					}
				}
			}
		}
		isFinish = true;
	}

	public void run() {
		System.out.println(Thread.currentThread().getName());
		
		refresh(file.getAbsolutePath());
	}
}
