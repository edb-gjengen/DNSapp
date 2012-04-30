package com.studentersamfundet.app;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RSSParserProgram implements IRSSParser {
	private static final long serialVersionUID = 3364287016121595334L;
	private final DataHandler dh;
	
	public RSSParserProgram() {
		 this(null);
	}
	
	public RSSParserProgram(DataHandler dh) {
		if (dh == null) {
			this.dh = new DataHandler();
		} else {
			this.dh = dh;
		}
	}

	public DataHandler parse(NodeList itemNodes) {
        for (int i = 0; i < itemNodes.getLength(); i++) {
        	String id = "";
            String title = "";
            String description = "";
            String date = "";
            String location = "";
            String text = "";
            String category = "";
            String image = "";
            String priceReg = "";
            String priceMem = "";
            String ticketUrl = "";
            
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
                NodeList textNodes = (itemElement).getElementsByTagName("content");
                NodeList categoryNodes = (itemElement).getElementsByTagName("category");
                NodeList imageNodes = (itemElement).getElementsByTagName("image");
                NodeList priceRegNodes = (itemElement).getElementsByTagName("price-regular");
                NodeList priceMemNodes = (itemElement).getElementsByTagName("price-member");
                NodeList ticketUrlNodes = (itemElement).getElementsByTagName("ticket-url");
                
                // Convert a Node into an Element.
                Node idElement = idNodes.item(0);
                Node titleElement = titleNodes.item(0);
                Node descriptionElement = descriptionNodes.item(0);
                Node dateElement = dateNodes.item(0);
                Node locationElement = locationNodes.item(0);
                Node textElement = textNodes.item(0);
                Node categoryElement = categoryNodes.item(0);
                Node imageElement = imageNodes.item(0);
                Node priceRegElement = priceRegNodes.item(0);
                Node priceMemElement = priceMemNodes.item(0);
                Node ticketUrlElement = ticketUrlNodes.item(0);
                
                // Get all the child nodes.
                NodeList idTextNodes = idElement.getChildNodes();
                NodeList titleTextNodes = titleElement.getChildNodes();
                NodeList descriptionTextNodes = descriptionElement.getChildNodes();
                NodeList dateTextNodes = dateElement.getChildNodes();
                NodeList locationTextNodes = locationElement.getChildNodes();
                NodeList textTextNodes = textElement.getChildNodes();
                NodeList categoryTextNodes = categoryElement.getChildNodes();
                NodeList imageTextNodes = imageElement.getChildNodes();
                NodeList priceRegTextNodes = priceRegElement.getChildNodes();
                NodeList priceMemTextNodes = priceMemElement.getChildNodes();
                NodeList ticketUrlTextNodes = ticketUrlElement.getChildNodes();
                
                // Retrieve the text.
                id = idTextNodes.item(0).getNodeValue(); // mandatory
                title = titleTextNodes.item(0).getNodeValue(); // mandatory
                if (descriptionTextNodes.getLength() > 0) 	description = descriptionTextNodes.item(0).getNodeValue();
                if (dateTextNodes.getLength() > 0) 			date = dateTextNodes.item(0).getNodeValue();
                if (locationTextNodes.getLength() > 0) 		location = locationTextNodes.item(0).getNodeValue();
                if (textTextNodes.getLength() > 0) 			text = textTextNodes.item(0).getNodeValue();
                if (categoryTextNodes.getLength() > 0) 		category = categoryTextNodes.item(0).getNodeValue();
                if (imageTextNodes.getLength() > 0)			image = imageTextNodes.item(0).getNodeValue();
                if (priceRegTextNodes.getLength() > 0)		priceReg = priceRegTextNodes.item(0).getNodeValue();
                if (priceMemTextNodes.getLength() > 0)			priceMem = priceMemTextNodes.item(0).getNodeValue();
                if (ticketUrlTextNodes.getLength() > 0)			ticketUrl = ticketUrlTextNodes.item(0).getNodeValue();
                
                // This is as ugly as it gets. Refactor?
                dh.insertEvent(id, 
                		title, 
                		description, 
                		date, 
                		location, 
                		text, 
                		category, 
                		image,
                		priceReg,
                		priceMem,
                		ticketUrl);
            }
        }
        return dh;
	}
}
