package edu.opinion.preprocess.dbmanage;

import java.util.List;

import edu.opinion.common.db.BasicDAO;
import edu.opinion.common.db.HibernateSessionFactory;

import org.hibernate.Query;
import org.hibernate.Session;

public class ReadItem {
	
	public static int itemBbsNum(){
		try{
			String hql = "SELECT COUNT(*) FROM TbParserBbs AS bbs WHERE bbs.spTopic is null";
			int number = BasicDAO.getItemAccount(hql);
			System.out.println("number="+number);
			return number;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	public static int itemReNum(){
		try{
			String hql = "SELECT COUNT(*) FROM TbReBbs AS rebbs WHERE rebbs.spTitle is null";
			int number = BasicDAO.getItemAccount(hql);
			System.out.println("number="+number);
			return number;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	public static List getBbsItem(int curr, int num, int rounds){
		try{
			Session session = HibernateSessionFactory.getSession();
			String hql = "FROM TbParserBbs AS bbs WHERE bbs.spTopic is null ORDER BY bbs.id";
			if (curr != rounds){
			    Query q = session.createQuery(hql);
		    	q.setFirstResult(0);
		    	q.setMaxResults(100);
			    List results = q.list();
			    return results;			    
			}
			else{
			    Query q = session.createQuery(hql);
		    	q.setFirstResult(0);
		    	q.setMaxResults(num-(curr-1)*100);
			    List results = q.list();
			    return results;
			}	
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public static List getReItem(int curr, int num, int rounds){
		try{
			Session session = HibernateSessionFactory.getSession();
			String hql = "FROM TbReBbs AS rebbs WHERE rebbs.spTitle is null ORDER BY rebbs.id";
			if (curr != rounds){
			    Query q = session.createQuery(hql);
		    	q.setFirstResult(0);
		    	q.setMaxResults(100);
			    List results = q.list();
			    return results;			    
			}
			else{
			    Query q = session.createQuery(hql);
		    	q.setFirstResult(0);
		    	q.setMaxResults(num-(curr-1)*100);
			    List results = q.list();
			    return results;
			}	
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

}
