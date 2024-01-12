package com.lip6.carnetContact.service;

import com.lip6.carnetContact.model.Contact;
import com.lip6.carnetContact.model.PhoneNumber;
import com.lip6.carnetContact.repository.ContactRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class ContactService {

    @Autowired
    private ContactRepositoryImpl contactRepository;

    public Contact addContact(String firstname, String lastname, String email, String street, String city, String zip, String country, Set<PhoneNumber> phones){
        return contactRepository.addContact(firstname, lastname, email, street, city, zip, country, phones);
    }

    public List<Contact> getAllContacts(){
        return contactRepository.getAllContacts();
    }

    public Contact getOneContact(Long id){
        return contactRepository.getOneContact(id);
    }

    public void deleteContact(Long id){
        contactRepository.deleteContact(id);
    }

    public Contact updateContact(Long idContact, String firstname, String lastname, String email, String street, String city, String zip, String country, Set<PhoneNumber> phones){
        return contactRepository.updateContact(idContact, firstname, lastname, email, street, city, zip, country, phones);
    }

    public void deletePhoneNumber(Long idPhoneNumber){
        contactRepository.deletePhoneNumber(idPhoneNumber);
    }
}
