package edu.opinion.preprocess.io;

import java.io.File;

import com.opinion.preprocess.Tian.ProcessTianYa;

public class FileMove {
	
	public static void moveFile(String oldPath, String newPath){
		try{
			
			File newFile = new File(newPath);
			if(!newFile.exists()){
				newFile.mkdirs();
			}
			File file = new File(oldPath);
			File fileList[] = file.listFiles();
			for (int i=0; i<fileList.length; i++){
					fileList[i].renameTo(new File(newPath + fileList[i].getName())); 	
				
			}
			System.out.println("Files Remove Successfully!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		try{
			FileMove.moveFile("E:\\crawlcontent\\", "E:\\backup\\");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
