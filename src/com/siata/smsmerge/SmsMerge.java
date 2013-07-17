package com.siata.smsmerge;

import java.io.File;

public class SmsMerge {

	/**
	 * @param args
	 */
	public void run(String... args) {

		// String db1 = "db/current.sqlite";
		// String db2 = "db/old.sqlite";

		String db1 = args[0];
		String db2 = args[1];

		File f1 = new File(db1);
		File f2 = new File(db2);

		new ApplicationPrepare(f1, f2);

		Operator o = new Operator();

		o.merge();

		DatabaseHolder.getInstance().closeApplication();

	}
}
