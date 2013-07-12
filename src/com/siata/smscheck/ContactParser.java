package com.siata.smscheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import ezvcard.Ezvcard;
import ezvcard.VCard;

// not used
public class ContactParser {

	private List<VCard> all;

	public ContactParser(File vcard) {
		try {
			all = Ezvcard.parse(vcard).all();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<VCard> getAll() {
		return all;
	}

}
