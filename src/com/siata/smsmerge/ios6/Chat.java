/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siata.smsmerge.ios6;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author m.siatkowski
 */
@Entity
@Table(name = "chat", catalog = "", schema = "")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "Chat.findAll", query = "SELECT c FROM Chat c"),
		@NamedQuery(name = "Chat.findByRowid", query = "SELECT c FROM Chat c WHERE c.rowid = :rowid"),
		@NamedQuery(name = "Chat.findByGuid", query = "SELECT c FROM Chat c WHERE c.guid = :guid"),
		@NamedQuery(name = "Chat.findByStyle", query = "SELECT c FROM Chat c WHERE c.style = :style"),
		@NamedQuery(name = "Chat.findByState", query = "SELECT c FROM Chat c WHERE c.state = :state"),
		@NamedQuery(name = "Chat.findByAccountId", query = "SELECT c FROM Chat c WHERE c.accountId = :accountId"),
		@NamedQuery(name = "Chat.findByProperties", query = "SELECT c FROM Chat c WHERE c.properties = :properties"),
		@NamedQuery(name = "Chat.findByChatIdentifier", query = "SELECT c FROM Chat c WHERE c.chatIdentifier = :chatIdentifier"),
		@NamedQuery(name = "Chat.findByServiceName", query = "SELECT c FROM Chat c WHERE c.serviceName = :serviceName"),
		@NamedQuery(name = "Chat.findByRoomName", query = "SELECT c FROM Chat c WHERE c.roomName = :roomName"),
		@NamedQuery(name = "Chat.findByAccountLogin", query = "SELECT c FROM Chat c WHERE c.accountLogin = :accountLogin"),
		@NamedQuery(name = "Chat.findByIsArchived", query = "SELECT c FROM Chat c WHERE c.isArchived = :isArchived"),
		@NamedQuery(name = "Chat.findByLastAddressedHandle", query = "SELECT c FROM Chat c WHERE c.lastAddressedHandle = :lastAddressedHandle") })
public class Chat implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "chatSeq")
	@TableGenerator(name = "chatSeq", table = "sqlite_sequence", pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "chat", allocationSize = 1, initialValue = 1)
	@Column(name = "ROWID")
	private Integer rowid;
	@Basic(optional = false)
	@Column(name = "guid")
	private String guid;
	@Column(name = "style")
	private Integer style;
	@Column(name = "state")
	private Integer state;
	@Column(name = "account_id")
	private String accountId;
	@Column(name = "properties")
	private String properties;
	@Column(name = "chat_identifier")
	private String chatIdentifier;
	@Column(name = "service_name")
	private String serviceName;
	@Column(name = "room_name")
	private String roomName;
	@Column(name = "account_login")
	private String accountLogin;
	@Column(name = "is_archived")
	private Integer isArchived;
	@Column(name = "last_addressed_handle")
	private String lastAddressedHandle;
	@ManyToMany(mappedBy = "chatList")
	private List<Handle> handleList;
	@OneToMany(mappedBy = "chatId")
	private List<ChatMessageJoin> chatMessageJoinList;

	public Chat() {
	}

	public Chat(Integer rowid) {
		this.rowid = rowid;
	}

	public Chat(Integer rowid, String guid) {
		this.rowid = rowid;
		this.guid = guid;
	}

	public Integer getRowid() {
		return rowid;
	}

	public void setRowid(Integer rowid) {
		this.rowid = rowid;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Integer getStyle() {
		return style;
	}

	public void setStyle(Integer style) {
		this.style = style;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getChatIdentifier() {
		return chatIdentifier;
	}

	public void setChatIdentifier(String chatIdentifier) {
		this.chatIdentifier = chatIdentifier;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getAccountLogin() {
		return accountLogin;
	}

	public void setAccountLogin(String accountLogin) {
		this.accountLogin = accountLogin;
	}

	public Integer getIsArchived() {
		return isArchived;
	}

	public void setIsArchived(Integer isArchived) {
		this.isArchived = isArchived;
	}

	public String getLastAddressedHandle() {
		return lastAddressedHandle;
	}

	public void setLastAddressedHandle(String lastAddressedHandle) {
		this.lastAddressedHandle = lastAddressedHandle;
	}

	@XmlTransient
	public List<Handle> getHandleList() {
		return handleList;
	}

	public void setHandleList(List<Handle> handleList) {
		this.handleList = handleList;
	}

	@XmlTransient
	public List<ChatMessageJoin> getChatMessageJoinList() {
		return chatMessageJoinList;
	}

	public void setChatMessageJoinList(List<ChatMessageJoin> chatMessageJoinList) {
		this.chatMessageJoinList = chatMessageJoinList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (rowid != null ? rowid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Chat)) {
			return false;
		}
		Chat other = (Chat) object;
		// if ((this.rowid == null && other.rowid != null)
		// || (this.rowid != null && !this.rowid.equals(other.rowid))) {
		// return false;
		// }
		if ((this.guid == null && other.guid != null)
				|| (this.guid != null && !this.guid.equals(other.guid))) {
			return false;
		}
		if ((this.chatIdentifier == null && other.chatIdentifier != null)
				|| (this.chatIdentifier != null && !this.chatIdentifier.equals(other.chatIdentifier))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.siata.smsmerge.ios6.Chat[ rowid=" + rowid + " ]";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
