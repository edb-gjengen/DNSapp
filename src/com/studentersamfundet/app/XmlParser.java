package com.studentersamfundet.app;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParser {
		public static DataHandler dh;
		
		public XmlParser() {
			 dh = new DataHandler();
		}
	
		DataHandler parse(NodeList itemNodes) {
			String id = "";
            String title = "";
            String description = "";
            String date = "";
            String location = "";
            String category = "";
            // String pictureUrl = ""; Necessary?
            
            for (int i = 0; i < itemNodes.getLength(); i++) { 
                Node itemNode = itemNodes.item(i); 
                
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {            
                    // Convert the Node into an Element.
                    Element itemElement = (Element) itemNode;
                    
                    // Get all elements under the <item> element.
                    NodeList idNodes = (itemElement).getElementsByTagName("id");
                    NodeList titleNodes = (itemElement).getElementsByTagName("title");
                    NodeList descriptionNodes = (itemElement).getElementsByTagName("description");
                    NodeList dateNodes = (itemElement).getElementsByTagName("date");
                    NodeList locationNodes = (itemElement).getElementsByTagName("location");
                    NodeList categoryNodes = (itemElement).getElementsByTagName("category");
                    
                    // Convert a Node into an Element.
                    Element idElement = (Element) idNodes.item(0);
                    Element titleElement = (Element) titleNodes.item(0);
                    Element descriptionElement = (Element) descriptionNodes.item(0);
                    Element dateElement = (Element) dateNodes.item(0);
                    Element locationElement = (Element) locationNodes.item(0);
                    Element categoryElement = (Element) categoryNodes.item(0);
                    
                    // Get all the child nodes.
                    NodeList idTextNodes = ((Node) idElement).getChildNodes();
                    NodeList titleTextNodes = ((Node) titleElement).getChildNodes();
                    NodeList descriptionTextNodes = ((Node) descriptionElement).getChildNodes();
                    NodeList dateTextNodes = ((Node) dateElement).getChildNodes();
                    NodeList locationTextNodes = ((Node) locationElement).getChildNodes();
                    NodeList categoryTextNodes = ((Node) categoryElement).getChildNodes();
                    
                    // Retrieve the text.
                    id = ((Node) idTextNodes.item(0)).getNodeValue();
                    title = ((Node) titleTextNodes.item(0)).getNodeValue();
                    description = ((Node) descriptionTextNodes.item(0)).getNodeValue();
                    date = ((Node) dateTextNodes.item(0)).getNodeValue();
                    location = ((Node) locationTextNodes.item(0)).getNodeValue();
                    category = ((Node) categoryTextNodes.item(0)).getNodeValue();
                    dh.insert(id, title, description, date, location, category);
                }
            }
            return dh;
		}
}
