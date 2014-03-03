package edu.opinion.preprocess.bjtu;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPathGenerator {

	static private String getID(Node node)
    {
        NamedNodeMap attributes = node.getAttributes();
        if (attributes == null)
            return null;
        Node id = attributes.getNamedItem("name");
        if (id == null)
            return null;
        return id.getNodeValue();
    }

    static private List getChildrenWithName(Node parent, String name)
    {
        NodeList children = parent.getChildNodes();
        List results = new ArrayList();
        for (int i = 0; i < children.getLength(); ++i)
        {
            Node child = children.item(i);
            if (child == null)
                continue;
            String nodeName = child.getNodeName();
            if (nodeName == null)
                continue;
            if (nodeName.equals(name))
            {
                results.add(child);
            }
        }
        return results;

    }

    //todo: move into XpathGenerator object
    static public String generateXPath(Node node)
    {
        Node parent = node.getParentNode();

        switch (node.getNodeType())
        {
            case Node.ATTRIBUTE_NODE :
                parent = ((Attr) node).getOwnerElement();
                return generateXPath(parent) + "/@" + node.getNodeName();

//            case Node.TEXT_NODE :
//                return "string(" + generateXPath(parent) + ")";

            case Node.ELEMENT_NODE :
            default :
                if (parent == null
                    || parent.getNodeType() == Node.DOCUMENT_NODE)
                {
                    return "/" + node.getNodeName();
                }

                if (getID(node) != null)
                {
                    return "//"
                        + node.getNodeName()
                        + "[@name='"
                        + getID(node)
                        + "']";
                }

                String path = generateXPath(parent) + '/' + node.getNodeName();
                List siblings = getChildrenWithName(parent, node.getNodeName());
                if (siblings.size() > 1)
                {
                    path += "[" + (siblings.indexOf(node) + 1) + "]";
                }
                return path;
        }
    }
}
