package com.studentersamfundet.app;

import java.io.InputStream;

public class JSONParserProgram implements IParser {
	private static final long serialVersionUID = 3364287016121595334L;
	private final DataHandler dh;

	public JSONParserProgram() {
		this(null);
	}

	public JSONParserProgram(DataHandler dh) {
		if (dh == null) {
			this.dh = new DataHandler();
		} else {
			this.dh = dh;
		}
	}

	public DataHandler parse(InputStream in) {
		for (int i = 0; i < 0; i++) {
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
		return dh;
	}
}
