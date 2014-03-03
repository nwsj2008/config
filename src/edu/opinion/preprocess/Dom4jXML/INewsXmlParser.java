package edu.opinion.preprocess.Dom4jXML;

import java.util.Vector;

import edu.opinion.preprocess.pattern.NewsPattern;


public interface INewsXmlParser {
	
	public Vector getXMLElements();
	public void bulidDocument();
	public boolean addXMLElement(NewsPattern newsPattern);

}
