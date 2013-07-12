/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siata.sms.ios6;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author m.siatkowski
 */
@Entity
@Table(name = "chat_message_join", catalog = "", schema = "")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "ChatMessageJoin.findAll", query = "SELECT c FROM ChatMessageJoin c"),
		@NamedQuery(name = "ChatMessageJoin.findByMessageId", query = "SELECT c FROM ChatMessageJoin c WHERE c.messageId = :messageId") })
public class ChatMessageJoin implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "message_id")
	private Integer messageId;
	@JoinColumn(name = "chat_id", referencedColumnName = "ROWID")
	@ManyToOne
	private Chat chatId;
	@JoinColumn(name = "message_id", referencedColumnName = "ROWID", insertable = false, updatable = false)
	@OneToOne
	private Message message;

	public ChatMessageJoin() {
	}

	public ChatMessageJoin(Integer messageId) {
		this.messageId = messageId;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public Chat getChatId() {
		return chatId;
	}

	public void setChatId(Chat chatId) {
		this.chatId = chatId;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (messageId != null ? messageId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof ChatMessageJoin)) {
			return false;
		}
		ChatMessageJoin other = (ChatMessageJoin) object;
		if ((this.messageId == null && other.messageId != null)
				|| (this.messageId != null && !this.messageId
						.equals(other.messageId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.siata.smsmerge.ios6.ChatMessageJoin[ messageId="
				+ messageId + " ]";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
