package edu.opinion.preprocess.Dom4jXML;

import edu.opinion.preprocess.bjtu.PretreatConfigBean;

public interface IPretreatConfigOpera {
	public Object getXMLElements();

	public boolean updateXMLElement(PretreatConfigBean pretreatConfigBean);

	public void bulidDocument();

	public boolean save();
}
