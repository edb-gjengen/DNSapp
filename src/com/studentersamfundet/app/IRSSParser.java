package com.studentersamfundet.app;

import org.w3c.dom.NodeList;

public interface IRSSParser {
	public DataHandler parse(NodeList nodes);
}
