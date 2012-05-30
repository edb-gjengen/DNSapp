package com.studentersamfundet.app;

import java.io.Serializable;

import org.w3c.dom.NodeList;

public interface IParser extends Serializable {
	public DataHandler parse(NodeList nodes);
}
