package edu.opinion.segment;

import java.util.ArrayList;
import java.util.List;

import edu.opinion.common.db.TbParserBbs;
import edu.opinion.common.db.TbReBbs;

public class BJTUSegment {
	
	public static List segmentBbs(List list){
		List newList = new ArrayList();
		TbParserBbs bbs;
		SegmentMain seg = new SegmentMain();
		try{
			for (int i=0; i<list.size(); i++){
				bbs = (TbParserBbs)list.get(i);
				if (bbs.getTopic()!=null && bbs.getTopic().length()>0){
					bbs.setSpTopic(seg.segmentMain(bbs.getTopic()));
				}
				if (bbs.getContent()!=null && bbs.getContent().length()>0){
					bbs.setSpContent(seg.segmentMain(bbs.getContent()));
				}
				newList.add(bbs);
//				System.out.println("newList:"+newList.size());
			}
			return newList;
		}catch(Exception e){
			e.printStackTrace();
			return newList;
		}
	}
	
	public static List segmentRe(List list){
		List newList = new ArrayList();
		TbReBbs rebbs;
		SegmentMain seg = new SegmentMain();
		try{
			for (int i=0; i<list.size(); i++){
				rebbs = (TbReBbs)list.get(i);
				if (rebbs.getReTitle()!=null && rebbs.getReTitle().length()>0){
					rebbs.setSpTitle(seg.segmentMain(rebbs.getReTitle()));
				}
				if (rebbs.getReContent()!=null && rebbs.getReContent().length()>0){
					rebbs.setSpContent(seg.segmentMain(rebbs.getReContent()));
				}
				newList.add(rebbs);
			}
			return newList;
		}catch(Exception e){
			e.printStackTrace();
			return newList;
		}
	}
}
