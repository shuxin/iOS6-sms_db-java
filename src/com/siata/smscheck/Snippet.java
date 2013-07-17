package com.siata.smscheck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.siata.sms.ios6.Attachment;
import com.siata.sms.ios6.Handle;
import com.siata.sms.ios6.Message;
import com.siata.sms.ios6.comparators.MessageDateComparator;
import com.siata.sms.ios6.jpa.HandleJpaController;
import com.siata.sms.ios6.jpa.MessageJpaController;
import com.siata.sms.ios6.jpa.exceptions.NonexistentEntityException;
import com.siata.smsmerge.DatabaseHolder;

public class Snippet {

	private HandleJpaController hHandleC;
	private MessageJpaController hMsgC;

	// removing empty handles
	// to-do - finish it :-)
	// easy to fix manually
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

	public void sortSms() {
		// TODO Auto-generated method stub

		hMsgC = new MessageJpaController(
				DatabaseHolder.getInstance().finalDatabase.getFactory());

		List<Message> msgs = hMsgC.findMessageEntities();
		List<MessageHolder> bakmsgs = new ArrayList<MessageHolder>();

		Collections.sort(msgs, new MessageDateComparator());
		int max = 0;

		for (Message message : msgs) {
			try {
				EntityManager em = DatabaseHolder.getInstance().finalDatabase
						.getFactory().createEntityManager();
				em.getTransaction().begin();

				bakmsgs.add(new MessageHolder(message));

				// bugged shit
				// hMsgC.destroy(message.getRowid());
				String delString = "DELETE FROM message WHERE ROWID = "
						+ message.getRowid() + "";
				Query q1 = em.createNativeQuery(delString);

				String delString2 = "DELETE FROM chat_message_join WHERE message_id = "
						+ message.getRowid() + "";
				Query q2 = em.createNativeQuery(delString2);

				// String delString3 =
				// "DELETE FROM message_attachment_join WHERE message_id = "
				// + message.getRowid() + "";
				// Query q3 = em.createNativeQuery(delString3);

				q1.executeUpdate();
				q2.executeUpdate();
				// q3.executeUpdate();
				em.getTransaction().commit();
				em.close();

				max++;
				if (max % 50 == 0) {
					System.out.print(".");
				}
				if (max % 500 == 0) {
					System.out.println("");
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(message);
				e.printStackTrace();
				break;
			}

		}

		System.out.println("");
		System.out.println("DELETED");
		System.out.println("");

		for (int i = 0; i < bakmsgs.size(); i++) {
			MessageHolder current = bakmsgs.get(i);

			try {
				Message createM = current.message;
				createM.setRowid(i);
				hMsgC.create(createM);

				EntityManager em = DatabaseHolder.getInstance().finalDatabase
						.getFactory().createEntityManager();
				em.getTransaction().begin();

				String delString = "INSERT INTO chat_message_join VALUES ("
						+ current.chatId + "," + i + ")";
				Query q1 = em.createNativeQuery(delString);

				if (current.oldAtt != null) {
					String delString3 = "UPDATE message_attachment_join SET message_id = "
							+ i
							+ " WHERE attachment_id = "
							+ current.oldAtt.getRowid() + "";
					Query q3 = em.createNativeQuery(delString3);
					q3.executeUpdate();
				}

				q1.executeUpdate();

				em.getTransaction().commit();
				em.close();

				if (i % 50 == 0) {
					System.out.print(".");
				}
				if (i % 500 == 0) {
					System.out.println("");
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
				break;
			}
		}

		System.out.println("");
		System.out.println("CREATED");
		System.out.println("");

		for (Message msg : hMsgC.findMessageEntities()) {
			if (bakmsgs.contains(msg)) {
				MessageHolder msgH = bakmsgs.get(bakmsgs.indexOf(msg));

				if (msgH.oldAtt != null) {
					msg.setAttachmentList(new ArrayList<Attachment>());
					msg.getAttachmentList().add(msgH.oldAtt);
					System.out.print(".");
					try {
						hMsgC.edit(msg);
					} catch (NonexistentEntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
	}
}
