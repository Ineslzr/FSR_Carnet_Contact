package com.lip6.carnetContact.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="ContactGroup")
public class ContactGroup {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idContactGroup;

    private String nameGroup;

    @JsonManagedReference
    @ManyToMany(cascade=CascadeType.PERSIST,mappedBy = "contactGroups",fetch = FetchType.EAGER )
    private Set<Contact> contacts = new HashSet<Contact>();


    public ContactGroup() {

    }
    public ContactGroup(String nameGroup) {
        this.nameGroup=nameGroup;
    }

    public long getIdContactGroup() {
        return idContactGroup;
    }

    public void setIdContactGroup(long idContactGroup) {
        this.idContactGroup = idContactGroup;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getNameGroup() {
        return nameGroup;
    }
    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        contact.getContactGroups().add(this);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
        contact.getContactGroups().remove(this);
    }
}
