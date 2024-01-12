package com.lip6.carnetContact.controller;

import com.lip6.carnetContact.model.ContactGroup;
import com.lip6.carnetContact.model.ContactGroupRequest;
import com.lip6.carnetContact.service.ContactGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/contactGroup")
public class ContactGroupController {

    @Autowired
    private ContactGroupService contactGroupService;

    @PostMapping("/create")
    public ResponseEntity<ContactGroup> createContactGroupe(@RequestBody ContactGroupRequest request){
        ContactGroup group = contactGroupService.createContactGroup(request.getGroupName());

        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ContactGroup> updateContactGroup(@PathVariable Long id,@RequestBody ContactGroupRequest request){
        ContactGroup group = contactGroupService.updateContactGroup(id,request.getGroupName());

        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<ContactGroup> findAllContactGroupe(){
        return contactGroupService.findAllContactGroup();
    }

    @GetMapping("/all/{idContact}")
    public List<ContactGroup> findAllContactGroupeByIdContact(@PathVariable Long idContact){
        return contactGroupService.findAllContactGroupByIdContact(idContact);
    }

    @GetMapping("/group/{id}")
    public ContactGroup getOneContactGroup(@PathVariable Long id) {
        return contactGroupService.getOneContactGroup(id);
    }

    @PutMapping("/addContact/{groupId}")
    public ResponseEntity<ContactGroup> addContact(@PathVariable Long groupId,@RequestBody List<Long> contactIds){
        ContactGroup group = contactGroupService.addContact(groupId,contactIds);

        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PutMapping("/removeContact/{groupId}/{contactId}")
    public ResponseEntity<ContactGroup> removeContact(@PathVariable Long groupId,@PathVariable Long contactId){
        ContactGroup group = contactGroupService.removeContact(groupId,contactId);

        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteContactGroup(@PathVariable Long id){
        contactGroupService.deleteContactGroup(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
