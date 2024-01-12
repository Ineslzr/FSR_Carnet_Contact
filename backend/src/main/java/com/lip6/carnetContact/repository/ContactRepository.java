package com.lip6.carnetContact.repository;

import com.lip6.carnetContact.model.Contact;
import com.lip6.carnetContact.model.PhoneNumber;

import java.util.List;
import java.util.Set;

public interface ContactRepository {

    public Contact addContact(String firstname, String lastname, String email, String street, String city, String zip, String country, Set<PhoneNumber> phones);
    public List<Contact> getAllContacts();
    public Contact getOneContact(Long id);

    public void deleteContact(Long id);

    public Contact updateContact(Long idContact,String firstname, String lastname, String email, String street, String city, String zip, String country, Set<PhoneNumber> phones);

    public void deletePhoneNumber(Long idPhoneNumber);
}
