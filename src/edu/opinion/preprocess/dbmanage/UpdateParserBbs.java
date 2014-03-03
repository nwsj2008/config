package edu.opinion.preprocess.dbmanage;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.opinion.common.db.HibernateSessionFactory;
import edu.opinion.common.db.TbParserBbs;

import java.sql.*;
import java.util.Iterator;
import java.util.List;

public class UpdateParserBbs {
	
	private Session session;
	
	private Connection PrepareConn(){
		session = HibernateSessionFactory.getSession();
		Connection conn=session.connection();
		return conn;
	}
	
	public void update(List<TbParserBbs> lst){
		Connection con=PrepareConn();
		PreparedStatement pstmt;

		try{
			pstmt = con.prepareStatement("update tb_parser_bbs set StorageTime=?, Sp_topic=?, Sp_content=? where ID=?"); 
			TbParserBbs parsebbs;
			String time = new java.util.Date().toLocaleString();
			
			Iterator it=lst.iterator();
			while(it.hasNext()){
				parsebbs=(TbParserBbs)it.next();
				pstmt.setString(1, time);
				pstmt.setString(2, parsebbs.getSpTopic());
				pstmt.setString(3, parsebbs.getSpContent());	
				pstmt.setString(4, parsebbs.getId().toString());
				pstmt.execute();
			}

			con.commit();
			
			if (pstmt!=null){
				pstmt.close();
			}
			if (con!=null){
				con.close();
			}
			session.close();
		}
		catch(Exception e){
			System.out.print(e.getMessage());
		}


	}
}
