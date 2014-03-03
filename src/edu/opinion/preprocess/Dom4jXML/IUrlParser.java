package edu.opinion.preprocess.Dom4jXML;

import java.util.Vector;

import edu.opinion.preprocess.pattern.UrlPattern;

public interface IUrlParser {
	
	public void bulidDocument();
	public Vector getXMLElements();
	public boolean addElement(UrlPattern patt);
	public boolean save();

}
