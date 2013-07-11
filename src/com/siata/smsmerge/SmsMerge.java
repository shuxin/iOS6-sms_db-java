package com.siata.smsmerge;

import java.io.File;

public class SmsMerge {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String db1 = "db/current.sqlite";
		String db2 = "db/old.sqlite";

		File f1 = new File(db1);
		File f2 = new File(db2);

		new ApplicationPrepare(f1, f2);
		
		// String card = "db/contacts.vcf";
		// File vcard = new File(card);
		//
		// ContactParser c = new ContactParser(vcard);
		//
		Operator o = new Operator();
		//o.parse(c);
		
		o.merge();
		
		
		DatabaseHolder.getInstance().closeApplication();

	}
}
