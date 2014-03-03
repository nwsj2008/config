package edu.opinion.preprocess.Dom4jXML;

import java.util.Vector;

import edu.opinion.preprocess.pattern.BbsPattern;

public interface IBbsXmlParser {
	
	public Vector getXMLElements();
	public void bulidDocument();
	public boolean addXMLElement(BbsPattern bbsPattern);
	public boolean updateXMLElement(BbsPattern bbsPattern);

}
