package vendi.rest.dao.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;

import vendi.entity.UserType;

@Entity
@Table(name = "FieldExecutive", indexes = { @Index(columnList = "id", name = "fe_id_index_on_fieldexecutive"),
		@Index(columnList = "username", name = "username_index_on_fieldexecutive"),
		@Index(columnList = "vendor_id", name = "vendor_id_index_on_fieldexecutive") })

@NamedQueries({
		@NamedQuery(name = "idempotencyCheckForFECreation", query = "FROM FieldExecutive WHERE username = :username and vendor_id = :vendor_id and created_at = :created_at"),
		@NamedQuery(name = "changePasswordFieldExecutive", query = "UPDATE FieldExecutive set password = :password WHERE username= :username"),
		@NamedQuery(name = "updateFieldExecutive", query = "UPDATE FieldExecutive set contact = :contact , name = :name , is_enabled = :is_enabled , updated_at =NOW() , updated_by = :updated_by WHERE username= :username"),
		@NamedQuery(name = "findFieldExecutiveByUsername", query = "FROM FieldExecutive WHERE username= :username"),
		@NamedQuery(name = "findFieldExecutiveById", query = "FROM FieldExecutive WHERE id= :id"),
		@NamedQuery(name = "findFieldExecutiveByIdAndVendor", query = "FROM FieldExecutive WHERE id= :id and vendor_id= :vendor_id"),
		@NamedQuery(name = "findAllFieldExecutiveByVendorId", query = "FROM FieldExecutive WHERE vendor_id= :vendor_id"),
		@NamedQuery(name = "findAllEnabledFieldExecutiveByVendorId", query = "FROM FieldExecutive WHERE vendor_id= :vendor_id and is_enabled = :is_enabled")})

public class FieldExecutive implements Serializable {

	private static final long serialVersionUID = 416193118681643719L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", nullable = false, unique=true)
	private String username;

	@JsonIgnore
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "contact", nullable = false)
	private String contact;

	@Column(name = "vendor_id", nullable = false)
	private String vendorId;

	@Column(name = "name")
	private String name;
	
	@Column(name = "is_enabled", nullable = false)
	private Boolean isEnabled;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@Column(name = "updated_by")
	@Enumerated(EnumType.STRING)
	private UserType updatedBy;
	
	@OneToMany( mappedBy="fieldExecutive", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
//	@JsonManagedReference
//	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Shipment> shipments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public UserType getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(UserType updatedBy) {
		this.updatedBy = updatedBy;
	}

	public List<Shipment> getShipments() {
		return shipments;
	}

	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	@Override
	public String toString() {
		return "FieldExecutive [fieldExecutiveId=" + id + ", username=" + username + ", password="
				+ password + ", contact=" + contact + ", vendorId=" + vendorId + ", name=" + name + ", isEnabled="
				+ isEnabled + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy
				+ "]";
	}

}
