package com.siata.smscheck;

import java.util.List;

import com.siata.sms.ios6.Handle;
import com.siata.sms.ios6.Message;
import com.siata.sms.ios6.jpa.HandleJpaController;
import com.siata.sms.ios6.jpa.MessageJpaController;
import com.siata.smsmerge.DatabaseHolder;

public class Snippet {

	private HandleJpaController hHandleC;
	private MessageJpaController hMsgC;

	public void doit() {

		hHandleC = new HandleJpaController(
				DatabaseHolder.getInstance().finalDatabase.getFactory());

		hMsgC = new MessageJpaController(
				DatabaseHolder.getInstance().finalDatabase.getFactory());

		List<Message> msgs = hMsgC.findMessageEntities();

		for (Handle h : hHandleC.findHandleEntities()) {

			boolean empty = true;
			for (Message message : msgs) {
				if (message.getHandleId().equals(h.getRowid())) {
					empty = false;
				}
			}

		}

	}
}
