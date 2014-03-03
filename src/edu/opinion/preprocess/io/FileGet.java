package edu.opinion.preprocess.io;

import java.io.*;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import edu.opinion.preprocess.Dom4jXML.IUrlParser;
import edu.opinion.preprocess.Dom4jXML.UrlParserImp;
import edu.opinion.preprocess.bjtu.UrlParser;
import edu.opinion.preprocess.pattern.UrlPattern;



public class FileGet {
	
	private BufferedWriter bw;
	private Vector urlPatternVector;

	/**
	 * 读取Log方法
	 * 
	 * @param path
	 *            日志所在路径
	 * 
	 * @return 封装了CrawlLog类的Vector
	 */

	private void getCrawlLog(String path) throws Exception, Error {
		
		String logFile = path+"log.txt";
		File input = new File(logFile);
		FileReader fr = new FileReader(input);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		String dd=  new String (new byte[]{0});
		while ((line = br.readLine()) != null) {
		
			String[] info = line.split(";");
            String charset = info[info.length-1].replaceAll("charset=", "").toUpperCase().trim();
            if (charset.equalsIgnoreCase("unknown")) {
				String line1 = "";
            	String filename = path +info[0];
            	BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filename)));
            	while ((line1=bufferedReader.readLine())!=null) {
					Pattern pattern  = Pattern.compile("(?<=charset=)\\w+(-)?\\w+");
					Matcher matcher = pattern.matcher(line1);
					if (matcher.find()) {
						charset = matcher.group();
						break;
					}
				}
			}
         /*   else if (charset.equalsIgnoreCase("gb2312")) {
				charset="GBK";
			}*/
            
            
			UrlParser urlP = new UrlParser();
			UrlPattern urlPattern = urlP.getPattern(info[1],urlPatternVector);

			if (urlPattern != null){
				String str = path+info[0]+";"+info[1]+";"+urlPattern.getModule()+";"+urlPattern.getIdKey()+";"+charset+"\r\n";
				bw.write(str);
			}
		}
			
			fr.close();
			br.close();

	}



	public static void main(String[] args) {
		try {
			FileGet f = new FileGet();
			IUrlParser urlParser = new UrlParserImp();
			Vector vector = (Vector)urlParser.getXMLElements();
			boolean isDone = f.fileGenerator("E:\\crawlcontent",vector,"E:\\backup.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 此方法用来遍历爬虫抓取文件，生成新的log日志
	 * 新的log日志将用来对文件进行处理
	 */
	public boolean fileGenerator(String path, Vector urlPatt, String logPath){
		try{
			this.setPattern(urlPatt);
			File file=new File(logPath);  
			bw = new BufferedWriter(new FileWriter(file));
			this.refreshFileList(path);
			bw.flush();
			return true;
		}catch(Exception e){
			return false;
		}finally{
			try{
				if (bw!=null){
					bw.close();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
	}
	
	private void refreshFileList(String strPath) { 
		try{
			File dir = new File(strPath); 
	        File[] files = dir.listFiles();       
	        if (files == null) 
	            return; 
	        for (int i = 0; i < files.length; i++) { 
	            if (files[i].isDirectory()) { 
	                refreshFileList(files[i].getAbsolutePath()); 
	            } else { 
	            	String  fiString = files[i].getParent();
	            	String fileRoot = files[i].getAbsolutePath().substring(0, (files[i].getAbsolutePath().lastIndexOf("\\"))+1);
	                this.getCrawlLog(fileRoot);
//	                System.out.println(fileRoot);
	                break;
	            } 
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
    }
	
	private void setPattern(Vector patt){
		this.urlPatternVector = patt;

	}


}
