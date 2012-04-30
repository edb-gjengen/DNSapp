package com.studentersamfundet.app;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RSSParserNews implements IRSSParser {
	private static final long serialVersionUID = -2654591623794449527L;
	private final DataHandler dh;
	
	public RSSParserNews() {
		 this(null);
	}
	
	public RSSParserNews(DataHandler dh) {
		if (dh == null) {
			this.dh = new DataHandler();
		} else {
			this.dh = dh;
		}
	}

	public DataHandler parse(NodeList itemNodes) {
        String title = "";
        String description = "";
        String link = "";
        String content = "";
        String pubDate = "";
        
        for (int i = 0; i < itemNodes.getLength(); i++) { 
            Node itemNode = itemNodes.item(i); 
            
            if (itemNode.getNodeType() == Node.ELEMENT_NODE) {            
                // Convert the Node into an Element.
                Element itemElement = (Element) itemNode;
                
                // Get all elements under the <item> element.
                NodeList titleNodes = (itemElement).getElementsByTagName("title");
                NodeList descriptionNodes = (itemElement).getElementsByTagName("description");
                NodeList linkNodes = (itemElement).getElementsByTagName("link");
                NodeList textNodes = (itemElement).getElementsByTagName("content:encoded");
                NodeList dateNodes = (itemElement).getElementsByTagName("pubDate");
                
                // Convert a Node into an Element.
                Node titleElement = titleNodes.item(0);
                Node descriptionElement = descriptionNodes.item(0);
                Node linkElement = linkNodes.item(0);
                Node textElement = textNodes.item(0);
                Node dateElement = dateNodes.item(0);
                
                // Get all the child nodes.
                NodeList titleTextNodes = titleElement.getChildNodes();
                NodeList descriptionTextNodes = descriptionElement.getChildNodes();
                NodeList linkTextNodes = linkElement.getChildNodes();
                NodeList textTextNodes = textElement.getChildNodes();
                NodeList dateTextNodes = dateElement.getChildNodes();
                
                // Retrieve the text.
                title = titleTextNodes.item(0).getNodeValue(); // mandatory
                if (descriptionTextNodes.getLength() > 0) 	description = descriptionTextNodes.item(0).getNodeValue();
                if (linkTextNodes.getLength() > 0) 			link = linkTextNodes.item(0).getNodeValue();
                if (textTextNodes.getLength() > 0) 			content = textTextNodes.item(0).getNodeValue();
                if (dateTextNodes.getLength() > 0) 			pubDate = dateTextNodes.item(0).getNodeValue();
                
                dh.insertNews(title, description, content, link, pubDate);
            }
        }
        return dh;
	}
}
