package com.siata.smscheck;

import java.io.File;

import com.siata.smsmerge.ApplicationPrepare;

public class SmsCheck {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String db1 = "db/result.sqlite";
		File f1 = new File(db1);
		new ApplicationPrepare(f1);

	}

}
