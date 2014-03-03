package edu.opinion.preprocess.dbmanage;

import java.util.List;

import edu.opinion.common.db.TbParserBbs;
import edu.opinion.common.db.TbReBbs;
import edu.opinion.common.db.BasicDAO;

public class BbsSave {
	
	public static boolean saveTopic(TbParserBbs bbs){
		String hql = "SELECT COUNT(*) FROM TbParserBbs AS tb WHERE tb.hashCode="+bbs.getHashCode();
		try{
			int num = BasicDAO.getItemAccount(hql);
			if (num==0){
				BasicDAO.save(bbs);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
			
		}
	}
	
	public static boolean saveRe(TbReBbs bbsRe){
		String hql = "SELECT COUNT(*) FROM TbReBbs AS tb WHERE tb.hashCode="+bbsRe.getHashCode();
		try{
			int num = BasicDAO.getItemAccount(hql);
			if (num==0){
				BasicDAO.save(bbsRe);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public static TbParserBbs getTopicBbsByIdkey(String key){
		String hql = "FROM TbParserBbs AS tb WHERE tb.idkey='"+key+"'";
		try{
			List list = BasicDAO.queryByHql(hql);
				if (!list.isEmpty()){
					return ((TbParserBbs)list.get(0));
				}else{
					return null;
				}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
