/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siata.sms.ios6;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author m.siatkowski
 */
@Entity
@Table(name = "message", catalog = "", schema = "")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
		@NamedQuery(name = "Message.findByRowid", query = "SELECT m FROM Message m WHERE m.rowid = :rowid"),
		@NamedQuery(name = "Message.findByGuid", query = "SELECT m FROM Message m WHERE m.guid = :guid"),
		@NamedQuery(name = "Message.findByText", query = "SELECT m FROM Message m WHERE m.text = :text"),
		@NamedQuery(name = "Message.findByReplace", query = "SELECT m FROM Message m WHERE m.replace = :replace"),
		@NamedQuery(name = "Message.findByServiceCenter", query = "SELECT m FROM Message m WHERE m.serviceCenter = :serviceCenter"),
		@NamedQuery(name = "Message.findByHandleId", query = "SELECT m FROM Message m WHERE m.handleId = :handleId"),
		@NamedQuery(name = "Message.findBySubject", query = "SELECT m FROM Message m WHERE m.subject = :subject"),
		@NamedQuery(name = "Message.findByCountry", query = "SELECT m FROM Message m WHERE m.country = :country"),
		@NamedQuery(name = "Message.findByAttributedBody", query = "SELECT m FROM Message m WHERE m.attributedBody = :attributedBody"),
		@NamedQuery(name = "Message.findByVersion", query = "SELECT m FROM Message m WHERE m.version = :version"),
		@NamedQuery(name = "Message.findByType", query = "SELECT m FROM Message m WHERE m.type = :type"),
		@NamedQuery(name = "Message.findByService", query = "SELECT m FROM Message m WHERE m.service = :service"),
		@NamedQuery(name = "Message.findByAccount", query = "SELECT m FROM Message m WHERE m.account = :account"),
		@NamedQuery(name = "Message.findByAccountGuid", query = "SELECT m FROM Message m WHERE m.accountGuid = :accountGuid"),
		@NamedQuery(name = "Message.findByError", query = "SELECT m FROM Message m WHERE m.error = :error"),
		@NamedQuery(name = "Message.findByDate", query = "SELECT m FROM Message m WHERE m.date = :date"),
		@NamedQuery(name = "Message.findByDateRead", query = "SELECT m FROM Message m WHERE m.dateRead = :dateRead"),
		@NamedQuery(name = "Message.findByDateDelivered", query = "SELECT m FROM Message m WHERE m.dateDelivered = :dateDelivered"),
		@NamedQuery(name = "Message.findByIsDelivered", query = "SELECT m FROM Message m WHERE m.isDelivered = :isDelivered"),
		@NamedQuery(name = "Message.findByIsFinished", query = "SELECT m FROM Message m WHERE m.isFinished = :isFinished"),
		@NamedQuery(name = "Message.findByIsEmote", query = "SELECT m FROM Message m WHERE m.isEmote = :isEmote"),
		@NamedQuery(name = "Message.findByIsFromMe", query = "SELECT m FROM Message m WHERE m.isFromMe = :isFromMe"),
		@NamedQuery(name = "Message.findByIsEmpty", query = "SELECT m FROM Message m WHERE m.isEmpty = :isEmpty"),
		@NamedQuery(name = "Message.findByIsDelayed", query = "SELECT m FROM Message m WHERE m.isDelayed = :isDelayed"),
		@NamedQuery(name = "Message.findByIsAutoReply", query = "SELECT m FROM Message m WHERE m.isAutoReply = :isAutoReply"),
		@NamedQuery(name = "Message.findByIsPrepared", query = "SELECT m FROM Message m WHERE m.isPrepared = :isPrepared"),
		@NamedQuery(name = "Message.findByIsRead", query = "SELECT m FROM Message m WHERE m.isRead = :isRead"),
		@NamedQuery(name = "Message.findByIsSystemMessage", query = "SELECT m FROM Message m WHERE m.isSystemMessage = :isSystemMessage"),
		@NamedQuery(name = "Message.findByIsSent", query = "SELECT m FROM Message m WHERE m.isSent = :isSent"),
		@NamedQuery(name = "Message.findByHasDdResults", query = "SELECT m FROM Message m WHERE m.hasDdResults = :hasDdResults"),
		@NamedQuery(name = "Message.findByIsServiceMessage", query = "SELECT m FROM Message m WHERE m.isServiceMessage = :isServiceMessage"),
		@NamedQuery(name = "Message.findByIsForward", query = "SELECT m FROM Message m WHERE m.isForward = :isForward"),
		@NamedQuery(name = "Message.findByWasDowngraded", query = "SELECT m FROM Message m WHERE m.wasDowngraded = :wasDowngraded"),
		@NamedQuery(name = "Message.findByIsArchive", query = "SELECT m FROM Message m WHERE m.isArchive = :isArchive"),
		@NamedQuery(name = "Message.findByCacheHasAttachments", query = "SELECT m FROM Message m WHERE m.cacheHasAttachments = :cacheHasAttachments"),
		@NamedQuery(name = "Message.findByCacheRoomnames", query = "SELECT m FROM Message m WHERE m.cacheRoomnames = :cacheRoomnames"),
		@NamedQuery(name = "Message.findByWasDataDetected", query = "SELECT m FROM Message m WHERE m.wasDataDetected = :wasDataDetected"),
		@NamedQuery(name = "Message.findByWasDeduplicated", query = "SELECT m FROM Message m WHERE m.wasDeduplicated = :wasDeduplicated") })
public class Message implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "messageSeq")
	@TableGenerator(name = "messageSeq", table = "sqlite_sequence", pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "message", allocationSize = 1, initialValue = 1)
	@Column(name = "ROWID")
	private Integer rowid;
	@Basic(optional = false)
	@Column(name = "guid")
	private String guid;
	@Column(name = "text")
	private String text;
	@Column(name = "replace")
	private Integer replace;
	@Column(name = "service_center")
	private String serviceCenter;
	@Column(name = "handle_id")
	private Integer handleId;
	@Column(name = "subject")
	private String subject;
	@Column(name = "country")
	private String country;
	@Column(name = "attributedBody")
	private String attributedBody;
	@Column(name = "version")
	private Integer version;
	@Column(name = "type")
	private Integer type;
	@Column(name = "service")
	private String service;
	@Column(name = "account")
	private String account;
	@Column(name = "account_guid")
	private String accountGuid;
	@Column(name = "error")
	private Integer error;
	@Column(name = "date")
	private Integer date;
	@Column(name = "date_read")
	private Integer dateRead;
	@Column(name = "date_delivered")
	private Integer dateDelivered;
	@Column(name = "is_delivered")
	private Integer isDelivered;
	@Column(name = "is_finished")
	private Integer isFinished;
	@Column(name = "is_emote")
	private Integer isEmote;
	@Column(name = "is_from_me")
	private Integer isFromMe;
	@Column(name = "is_empty")
	private Integer isEmpty;
	@Column(name = "is_delayed")
	private Integer isDelayed;
	@Column(name = "is_auto_reply")
	private Integer isAutoReply;
	@Column(name = "is_prepared")
	private Integer isPrepared;
	@Column(name = "is_read")
	private Integer isRead;
	@Column(name = "is_system_message")
	private Integer isSystemMessage;
	@Column(name = "is_sent")
	private Integer isSent;
	@Column(name = "has_dd_results")
	private Integer hasDdResults;
	@Column(name = "is_service_message")
	private Integer isServiceMessage;
	@Column(name = "is_forward")
	private Integer isForward;
	@Column(name = "was_downgraded")
	private Integer wasDowngraded;
	@Column(name = "is_archive")
	private Integer isArchive;
	@Column(name = "cache_has_attachments")
	private Integer cacheHasAttachments;
	@Column(name = "cache_roomnames")
	private String cacheRoomnames;
	@Column(name = "was_data_detected")
	private Integer wasDataDetected;
	@Column(name = "was_deduplicated")
	private Integer wasDeduplicated;
	@ManyToMany(mappedBy = "messageList")
	private List<Attachment> attachmentList;
	@OneToOne(mappedBy = "message")
	private ChatMessageJoin chatMessageJoin;

	public Message() {
	}

	public Message(Integer rowid) {
		this.rowid = rowid;
	}

	public Message(Integer rowid, String guid) {
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getReplace() {
		return replace;
	}

	public void setReplace(Integer replace) {
		this.replace = replace;
	}

	public String getServiceCenter() {
		return serviceCenter;
	}

	public void setServiceCenter(String serviceCenter) {
		this.serviceCenter = serviceCenter;
	}

	public Integer getHandleId() {
		return handleId;
	}

	public void setHandleId(Integer handleId) {
		this.handleId = handleId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAttributedBody() {
		return attributedBody;
	}

	public void setAttributedBody(String attributedBody) {
		this.attributedBody = attributedBody;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountGuid() {
		return accountGuid;
	}

	public void setAccountGuid(String accountGuid) {
		this.accountGuid = accountGuid;
	}

	public Integer getError() {
		return error;
	}

	public void setError(Integer error) {
		this.error = error;
	}

	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}

	public Integer getDateRead() {
		return dateRead;
	}

	public void setDateRead(Integer dateRead) {
		this.dateRead = dateRead;
	}

	public Integer getDateDelivered() {
		return dateDelivered;
	}

	public void setDateDelivered(Integer dateDelivered) {
		this.dateDelivered = dateDelivered;
	}

	public Integer getIsDelivered() {
		return isDelivered;
	}

	public void setIsDelivered(Integer isDelivered) {
		this.isDelivered = isDelivered;
	}

	public Integer getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(Integer isFinished) {
		this.isFinished = isFinished;
	}

	public Integer getIsEmote() {
		return isEmote;
	}

	public void setIsEmote(Integer isEmote) {
		this.isEmote = isEmote;
	}

	public Integer getIsFromMe() {
		return isFromMe;
	}

	public void setIsFromMe(Integer isFromMe) {
		this.isFromMe = isFromMe;
	}

	public Integer getIsEmpty() {
		return isEmpty;
	}

	public void setIsEmpty(Integer isEmpty) {
		this.isEmpty = isEmpty;
	}

	public Integer getIsDelayed() {
		return isDelayed;
	}

	public void setIsDelayed(Integer isDelayed) {
		this.isDelayed = isDelayed;
	}

	public Integer getIsAutoReply() {
		return isAutoReply;
	}

	public void setIsAutoReply(Integer isAutoReply) {
		this.isAutoReply = isAutoReply;
	}

	public Integer getIsPrepared() {
		return isPrepared;
	}

	public void setIsPrepared(Integer isPrepared) {
		this.isPrepared = isPrepared;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Integer getIsSystemMessage() {
		return isSystemMessage;
	}

	public void setIsSystemMessage(Integer isSystemMessage) {
		this.isSystemMessage = isSystemMessage;
	}

	public Integer getIsSent() {
		return isSent;
	}

	public void setIsSent(Integer isSent) {
		this.isSent = isSent;
	}

	public Integer getHasDdResults() {
		return hasDdResults;
	}

	public void setHasDdResults(Integer hasDdResults) {
		this.hasDdResults = hasDdResults;
	}

	public Integer getIsServiceMessage() {
		return isServiceMessage;
	}

	public void setIsServiceMessage(Integer isServiceMessage) {
		this.isServiceMessage = isServiceMessage;
	}

	public Integer getIsForward() {
		return isForward;
	}

	public void setIsForward(Integer isForward) {
		this.isForward = isForward;
	}

	public Integer getWasDowngraded() {
		return wasDowngraded;
	}

	public void setWasDowngraded(Integer wasDowngraded) {
		this.wasDowngraded = wasDowngraded;
	}

	public Integer getIsArchive() {
		return isArchive;
	}

	public void setIsArchive(Integer isArchive) {
		this.isArchive = isArchive;
	}

	public Integer getCacheHasAttachments() {
		return cacheHasAttachments;
	}

	public void setCacheHasAttachments(Integer cacheHasAttachments) {
		this.cacheHasAttachments = cacheHasAttachments;
	}

	public String getCacheRoomnames() {
		return cacheRoomnames;
	}

	public void setCacheRoomnames(String cacheRoomnames) {
		this.cacheRoomnames = cacheRoomnames;
	}

	public Integer getWasDataDetected() {
		return wasDataDetected;
	}

	public void setWasDataDetected(Integer wasDataDetected) {
		this.wasDataDetected = wasDataDetected;
	}

	public Integer getWasDeduplicated() {
		return wasDeduplicated;
	}

	public void setWasDeduplicated(Integer wasDeduplicated) {
		this.wasDeduplicated = wasDeduplicated;
	}

	@XmlTransient
	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public ChatMessageJoin getChatMessageJoin() {
		return chatMessageJoin;
	}

	public void setChatMessageJoin(ChatMessageJoin chatMessageJoin) {
		this.chatMessageJoin = chatMessageJoin;
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
		if (!(object instanceof Message)) {
			return false;
		}
		Message other = (Message) object;
		if ((this.guid == null && other.guid != null)
				|| (this.guid != null && !this.guid.equals(other.guid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.siata.smsmerge.ios6.Message[ rowid=" + rowid + " ]";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
