package com.siata.smscheck;

import java.io.File;

import com.siata.smsmerge.ApplicationPrepare;
import com.siata.smsmerge.DatabaseHolder;

public class SmsCheck {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("SMSCHECK");
		String db1 = "db/fixed.sqlite";
		File f1 = new File(db1);
		new ApplicationPrepare(f1);

		Snippet s = new Snippet();
		s.sortSms();

		DatabaseHolder.getInstance().finalDatabase.getFactory().close();
	}

}
