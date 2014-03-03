package edu.opinion.preprocess.pattern;

/**
 * 封装了FileRecord类的JavaBean，对应FileRecord.xml文件
 * @author 李舒晨，廉捷，张彦超
 *
 */
public class FileRecord {
	
	private String path;
	private String backupPath;
	private String logPath;
	
	public String getLogPath() {
		return logPath;
	}
	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getBackupPath() {
		return backupPath;
	}
	public void setBackupPath(String backupPath) {
		this.backupPath = backupPath;
	}
	
	
	
	


	
}
