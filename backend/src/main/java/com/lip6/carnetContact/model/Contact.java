package com.lip6.carnetContact.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idContact;

    private String firstName;
    private String lastName;
    private String email;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="idAddress")
    @JsonManagedReference
    Address addresse;

    @OneToMany(cascade=CascadeType.ALL,mappedBy="contact", fetch = FetchType.EAGER)
    @JsonManagedReference
    Set<PhoneNumber> phones= new HashSet<PhoneNumber>();

    @ManyToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name="CTC_GRP",
            joinColumns=@JoinColumn(name="CTC_ID"),
            inverseJoinColumns=@JoinColumn(name="GRP_ID"))
    Set<ContactGroup> contactGroups= new HashSet<ContactGroup>();

    public Contact() {
    }

    public Contact(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public long getIdContact() {
        return idContact;
    }

    public void setIdContact(long idContact) {
        this.idContact = idContact;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddresse() {
        return addresse;
    }

    public void setAddresse(Address addresse) {
        this.addresse = addresse;
    }

    public Set<PhoneNumber> getPhones() {
        return phones;
    }

    public void setPhones(Set<PhoneNumber> phones) {
        this.phones = phones;
    }

    public Set<ContactGroup> getContactGroups() {
        return contactGroups;
    }


    public void setContactGroups(Set<ContactGroup> contactGroups) {
        this.contactGroups = contactGroups;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "idContact=" + idContact +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", addresse=" + addresse +
                ", phones=" + phones +
                '}';
    }
}
