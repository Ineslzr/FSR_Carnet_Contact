package com.lip6.carnetContact.controller;

import com.lip6.carnetContact.model.Contact;
import com.lip6.carnetContact.model.ContactRequest;
import com.lip6.carnetContact.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/contacts")
@Transactional
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping(value = "/addContact", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE )
    public ResponseEntity<Contact> addContact(@RequestBody ContactRequest request) {
        Contact contact = contactService.addContact(request.getFirstName(), request.getLastName(), request.getEmail(), request.getStreet(), request.getCity(), request.getZip(), request.getCountry(), request.getPhones());
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping("/contact/{id}")
    public Contact getContactById(@PathVariable Long id) {
        return contactService.getOneContact(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/phoneNumber/{id}")
    public ResponseEntity<String> deletePhoneNumber(@PathVariable Long id) {
        contactService.deletePhoneNumber(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id,@RequestBody ContactRequest request){
        Contact contact = contactService.updateContact(id,request.getFirstName(), request.getLastName(), request.getEmail(), request.getStreet(), request.getCity(), request.getZip(), request.getCountry(), request.getPhones());

        return new ResponseEntity<>(contact, HttpStatus.OK);
    }
}
