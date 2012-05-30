package com.studentersamfundet.app;

import java.io.InputStream;
import java.io.Serializable;

public interface IParser extends Serializable {
	public DataHandler parse(InputStream in);
}