package com.siata.smsmerge;

import java.util.ArrayList;
import java.util.List;

import com.siata.sms.ios6.Attachment;
import com.siata.sms.ios6.Chat;
import com.siata.sms.ios6.ChatMessageJoin;
import com.siata.sms.ios6.Handle;
import com.siata.sms.ios6.Message;
import com.siata.sms.ios6.jpa.AttachmentJpaController;
import com.siata.sms.ios6.jpa.ChatJpaController;
import com.siata.sms.ios6.jpa.ChatMessageJoinJpaController;
import com.siata.sms.ios6.jpa.HandleJpaController;
import com.siata.sms.ios6.jpa.MessageJpaController;
import com.siata.sms.ios6.jpa.exceptions.PreexistingEntityException;
import com.siata.smscheck.ContactParser;

import ezvcard.VCard;
import ezvcard.types.EmailType;
import ezvcard.types.TelephoneType;

public class Operator {

	private Handle hostHandle;
	private HandleJpaController hHandleC;
	private HandleJpaController rHandleC;
	private MessageJpaController hMsgC;
	private MessageJpaController rMsgC;
	private ChatJpaController hChatC;
	private ChatJpaController rChatC;
	private AttachmentJpaController hAttachC;
	private AttachmentJpaController rAttachC;
	private ChatMessageJoinJpaController hChatMsgC;
	private ChatMessageJoinJpaController rChatMsgC;

	public Operator() {
	}

	// not used
	public void parse(ContactParser c) {

		for (VCard a : c.getAll()) {
			System.out.println("Parsuje: " + a.getFormattedName().getValue());

			if (a.getTelephoneNumbers() != null) {
				for (TelephoneType telephone : a.getTelephoneNumbers()) {
					System.out.println(telephone.getText().replaceAll(" ", ""));

				}
			}

			if (a.getEmails() != null) {
				for (EmailType email : a.getEmails()) {
					System.out.println(email.getValue());
				}
			}
		}
	}

	private void prepare() {
		hHandleC = new HandleJpaController(
				DatabaseHolder.getInstance().finalDatabase.getFactory());
		rHandleC = new HandleJpaController(
				DatabaseHolder.getInstance().remoteDatabase.getFactory());

		hMsgC = new MessageJpaController(
				DatabaseHolder.getInstance().finalDatabase.getFactory());
		rMsgC = new MessageJpaController(
				DatabaseHolder.getInstance().remoteDatabase.getFactory());

		hChatC = new ChatJpaController(
				DatabaseHolder.getInstance().finalDatabase.getFactory());
		rChatC = new ChatJpaController(
				DatabaseHolder.getInstance().remoteDatabase.getFactory());

		hAttachC = new AttachmentJpaController(
				DatabaseHolder.getInstance().finalDatabase.getFactory());
		rAttachC = new AttachmentJpaController(
				DatabaseHolder.getInstance().remoteDatabase.getFactory());

		hChatMsgC = new ChatMessageJoinJpaController(
				DatabaseHolder.getInstance().finalDatabase.getFactory());
		rChatMsgC = new ChatMessageJoinJpaController(
				DatabaseHolder.getInstance().remoteDatabase.getFactory());
	}

	public void merge() {
		// TODO Auto-generated method stub
		prepare();

		List<Handle> hostHandles = hHandleC.findHandleEntities();
		List<Handle> remHandles = rHandleC.findHandleEntities();

		System.out.println("Prepare handles");

		for (Handle handle : remHandles) {
			if (hostHandles.contains(handle)) {
				System.out.print(",");
				hostHandle = hostHandles.get(hostHandles.indexOf(handle));

			} else {
				System.out.print(".");
				try {
					hostHandle = (Handle) handle.clone();
					hostHandle.setRowid(null);
					hostHandle.setChatList(null);
					hHandleC.create(hostHandle);
				} catch (PreexistingEntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				mergeChat(handle);
			}

		}

		System.out.println("");
		System.out.println("Done handles");
		copyMessages();
	}

	private void copyMessages() {
		System.out.println("Prepare messages");

		List<Handle> hostHandles = hHandleC.findHandleEntities();
		List<Chat> hostChats = hChatC.findChatEntities();
		List<Message> rmsgs = rMsgC.findMessageEntities();
		int i = 0;
		for (Message rm : rmsgs) {
			if (i >= 11) {
				System.out.println("");
				i = 0;
			}
			System.out.print("-");
			try {
				Message hm = (Message) rm.clone();
				hm.setRowid(null);

				// handle
				Handle rhandle = rHandleC.findHandle(rm.getHandleId());
				Handle hhandle = hostHandles.get(hostHandles.indexOf(rhandle));
				hm.setHandleId(hhandle.getRowid());

				hm.setChatMessageJoin(null);
				hm.setAttachmentList(null);

				hMsgC.create(hm);

				// chat
				Chat rchat = rm.getChatMessageJoin().getChatId();
				Chat hchat = hostChats.get(hostChats.indexOf(rchat));

				ChatMessageJoin msgChat = new ChatMessageJoin();
				msgChat.setChatId(hchat);
				msgChat.setMessageId(hm.getRowid());
				msgChat.setMessage(hm);
				hChatMsgC.create(msgChat);

				for (Attachment a : rm.getAttachmentList()) {
					Attachment ha = (Attachment) a.clone();
					ha.setRowid(null);

					List<Message> ma = new ArrayList<Message>();
					ma.add(hm);
					ha.setMessageList(ma);
					hAttachC.create(ha);

					if (hm.getAttachmentList() == null) {
						hm.setAttachmentList(new ArrayList<Attachment>());
					}
					hm.getAttachmentList().add(ha);
					hMsgC.edit(hm);
				}

			} catch (Exception e) {
				System.out.println(rm.getGuid());
				e.printStackTrace();
			}
			i++;
		}

		System.out.println("");
		System.out.println("Done messages");
	}

	private void mergeChat(Handle handle) {

		Chat rchat = handle.getChatList().get(0);

		try {
			Chat hchat = (Chat) rchat.clone();
			hchat.setRowid(null);
			hchat.setChatMessageJoinList(null);

			// hchat.setHandleList(null);
			ArrayList<Handle> hl = new ArrayList<Handle>();
			hl.add(hostHandle);
			hchat.setHandleList(hl);

			hChatC.create(hchat);

			ArrayList<Chat> cl = new ArrayList<Chat>();
			cl.add(hchat);
			hostHandle.setChatList(cl);
			hHandleC.edit(hostHandle);

		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PreexistingEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
