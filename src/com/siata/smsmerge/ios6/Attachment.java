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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author m.siatkowski
 */
@Entity
@Table(name = "attachment", catalog = "", schema = "")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "Attachment.findAll", query = "SELECT a FROM Attachment a"),
		@NamedQuery(name = "Attachment.findByRowid", query = "SELECT a FROM Attachment a WHERE a.rowid = :rowid"),
		@NamedQuery(name = "Attachment.findByGuid", query = "SELECT a FROM Attachment a WHERE a.guid = :guid"),
		@NamedQuery(name = "Attachment.findByCreatedDate", query = "SELECT a FROM Attachment a WHERE a.createdDate = :createdDate"),
		@NamedQuery(name = "Attachment.findByStartDate", query = "SELECT a FROM Attachment a WHERE a.startDate = :startDate"),
		@NamedQuery(name = "Attachment.findByFilename", query = "SELECT a FROM Attachment a WHERE a.filename = :filename"),
		@NamedQuery(name = "Attachment.findByUti", query = "SELECT a FROM Attachment a WHERE a.uti = :uti"),
		@NamedQuery(name = "Attachment.findByMimeType", query = "SELECT a FROM Attachment a WHERE a.mimeType = :mimeType"),
		@NamedQuery(name = "Attachment.findByTransferState", query = "SELECT a FROM Attachment a WHERE a.transferState = :transferState"),
		@NamedQuery(name = "Attachment.findByIsOutgoing", query = "SELECT a FROM Attachment a WHERE a.isOutgoing = :isOutgoing") })
public class Attachment implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "attachSeq")
	@TableGenerator(name = "attachSeq", table = "sqlite_sequence", pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "attachment", allocationSize = 1, initialValue = 1)
	@Column(name = "ROWID")
	private Integer rowid;
	@Basic(optional = false)
	@Column(name = "guid")
	private String guid;
	@Column(name = "created_date")
	private Integer createdDate;
	@Column(name = "start_date")
	private Integer startDate;
	@Column(name = "filename")
	private String filename;
	@Column(name = "uti")
	private String uti;
	@Column(name = "mime_type")
	private String mimeType;
	@Column(name = "transfer_state")
	private Integer transferState;
	@Column(name = "is_outgoing")
	private Integer isOutgoing;
	@JoinTable(name = "message_attachment_join", joinColumns = { @JoinColumn(name = "attachment_id", referencedColumnName = "ROWID") }, inverseJoinColumns = { @JoinColumn(name = "message_id", referencedColumnName = "ROWID") })
	@ManyToMany
	private List<Message> messageList;

	public Attachment() {
	}

	public Attachment(Integer rowid) {
		this.rowid = rowid;
	}

	public Attachment(Integer rowid, String guid) {
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

	public Integer getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Integer createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getStartDate() {
		return startDate;
	}

	public void setStartDate(Integer startDate) {
		this.startDate = startDate;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getUti() {
		return uti;
	}

	public void setUti(String uti) {
		this.uti = uti;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public Integer getTransferState() {
		return transferState;
	}

	public void setTransferState(Integer transferState) {
		this.transferState = transferState;
	}

	public Integer getIsOutgoing() {
		return isOutgoing;
	}

	public void setIsOutgoing(Integer isOutgoing) {
		this.isOutgoing = isOutgoing;
	}

	@XmlTransient
	public List<Message> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
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
		if (!(object instanceof Attachment)) {
			return false;
		}
		Attachment other = (Attachment) object;
		if ((this.guid == null && other.guid != null)
				|| (this.guid != null && !this.guid.equals(other.guid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.siata.smsmerge.ios6.Attachment[ rowid=" + rowid + " ]";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
