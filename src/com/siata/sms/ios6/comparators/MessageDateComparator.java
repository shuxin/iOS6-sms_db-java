package com.siata.sms.ios6.comparators;

import java.util.Comparator;

import com.siata.sms.ios6.Message;

public class MessageDateComparator implements Comparator<Message> {

	@Override
	public int compare(Message arg0, Message arg1) {
		return arg0.getDate().compareTo(arg1.getDate());
	}

	
}
