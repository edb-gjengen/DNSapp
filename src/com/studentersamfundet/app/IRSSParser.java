package com.studentersamfundet.app;

import java.io.Serializable;

import org.w3c.dom.NodeList;

public interface IRSSParser extends Serializable {
	public DataHandler parse(NodeList nodes);
}
