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
@Table(name = "handle", catalog = "", schema = "")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "Handle.findAll", query = "SELECT h FROM Handle h"),
		@NamedQuery(name = "Handle.findByRowid", query = "SELECT h FROM Handle h WHERE h.rowid = :rowid"),
		@NamedQuery(name = "Handle.findById", query = "SELECT h FROM Handle h WHERE h.id = :id"),
		@NamedQuery(name = "Handle.findByCountry", query = "SELECT h FROM Handle h WHERE h.country = :country"),
		@NamedQuery(name = "Handle.findByService", query = "SELECT h FROM Handle h WHERE h.service = :service"),
		@NamedQuery(name = "Handle.findByUncanonicalizedId", query = "SELECT h FROM Handle h WHERE h.uncanonicalizedId = :uncanonicalizedId") })
public class Handle implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "handleSeq")
	@TableGenerator(name = "handleSeq", table = "sqlite_sequence", pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "handle", allocationSize = 1, initialValue = 1)
	@Column(name = "ROWID")
	private Integer rowid;
	@Basic(optional = false)
	@Column(name = "id")
	private String id;
	@Column(name = "country")
	private String country;
	@Basic(optional = false)
	@Column(name = "service")
	private String service;
	@Column(name = "uncanonicalized_id")
	private String uncanonicalizedId;
	@JoinTable(name = "chat_handle_join", joinColumns = { @JoinColumn(name = "handle_id", referencedColumnName = "ROWID") }, inverseJoinColumns = { @JoinColumn(name = "chat_id", referencedColumnName = "ROWID") })
	@ManyToMany
	private List<Chat> chatList;

	public Handle() {
	}

	public Handle(Integer rowid) {
		this.rowid = rowid;
	}

	public Handle(Integer rowid, String id, String service) {
		this.rowid = rowid;
		this.id = id;
		this.service = service;
	}

	public Integer getRowid() {
		return rowid;
	}

	public void setRowid(Integer rowid) {
		this.rowid = rowid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getUncanonicalizedId() {
		return uncanonicalizedId;
	}

	public void setUncanonicalizedId(String uncanonicalizedId) {
		this.uncanonicalizedId = uncanonicalizedId;
	}

	@XmlTransient
	public List<Chat> getChatList() {
		return chatList;
	}

	public void setChatList(List<Chat> chatList) {
		this.chatList = chatList;
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
		if (!(object instanceof Handle)) {
			return false;
		}
		Handle other = (Handle) object;
		// if ((this.rowid == null && other.rowid != null) || (this.rowid !=
		// null && !this.rowid.equals(other.rowid))) {
		// return false;
		// }
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		if ((this.service == null && other.service != null)
				|| (this.service != null && !this.service.equals(other.service))) {
			return false;
		}
		// if ((this.country == null && other.country != null)
		// || (this.country != null && !this.country.equals(other.country))) {
		// return false;
		// }
		return true;
	}

	@Override
	public String toString() {
		return "com.siata.smsmerge.ios6.Handle[ rowid=" + rowid + " ]";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
