package com.siata.smscheck;

import java.util.List;

import com.siata.sms.ios6.Attachment;
import com.siata.sms.ios6.Message;

public class MessageHolder {

	Message message;
	Integer oldId;
	Integer chatId;
	Attachment oldAtt;

	public MessageHolder(Message message) {
		super();

		try {
			this.oldId = message.getRowid();
			this.chatId = message.getChatMessageJoin().getChatId().getRowid();
			this.message = (Message) message.clone();

			if (message.getAttachmentList() != null && (!message.getAttachmentList().isEmpty()))
			{
				this.oldAtt = (Attachment) message.getAttachmentList().get(0).clone();
			}
			
			this.message.setRowid(null);
			this.message.setAttachmentList(null);
			this.message.setChatMessageJoin(null);
			
		
		} catch (CloneNotSupportedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		MessageHolder o = (MessageHolder) obj;
		return o.message.equals(this.message);
	}
	
	

}
