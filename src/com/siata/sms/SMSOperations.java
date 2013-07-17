package com.siata.sms;

import java.io.File;

import com.siata.smscheck.SmsCheck;
import com.siata.smsmerge.SmsMerge;

public class SMSOperations {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// files[0].toPath().getParent()+"/result.sqlite"
		if (args.length == 2) {
			System.out
					.println("Merging files...");
			
			new SmsMerge().run(args);
			
			System.out.println("Merged... sorting...");
			File f = new File(args[0]);
			new SmsCheck().run(f.toPath().getParent()+"/result.sqlite");
			
			
			System.out.println("Sorted.");
			System.out.println("Your file is results.sqlite.");
			System.out.println("Same dir as first file.");
		}
	}

}
