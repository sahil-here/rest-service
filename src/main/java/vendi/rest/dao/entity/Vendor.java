package vendi.rest.dao.entity;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;

/**
 * Created by sahil.bansal on 21/07/16.
 */
@Entity
@Table(name = "Vendor", indexes = { @Index(columnList = "id", name = "id_index_on_Vendor") })

@NamedQueries({
        @NamedQuery(name = "idempotencyCheckForVendorCreation", query = "FROM Vendor WHERE name = :name and email = :email and contact = :contact"),
        @NamedQuery(name = "findVendorByEmail", query = "FROM Vendor WHERE email = :email")})
public class Vendor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique=true)
    private String email;

    @Column(name = "contact", nullable = false, unique=true)
    private String contact;

    @Column(name = "password", nullable = false)
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
