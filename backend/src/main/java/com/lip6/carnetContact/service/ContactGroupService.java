package com.lip6.carnetContact.service;

import com.lip6.carnetContact.model.ContactGroup;
import com.lip6.carnetContact.repository.ContactGroupRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactGroupService {

    @Autowired
    private ContactGroupRepositoryImpl contactGroupRepository;

    public ContactGroup createContactGroup(String nameGroup){
        return contactGroupRepository.createContactGroup(nameGroup);
    }

    public ContactGroup updateContactGroup(long id, String nameGroup){
        return contactGroupRepository.updateContactGroup(id,nameGroup);
    }
    public List<ContactGroup> findAllContactGroup(){
        return contactGroupRepository.findAllContactGroup();
    }

    public List<ContactGroup> findAllContactGroupByIdContact(long idContact){
        return contactGroupRepository.findAllContactGroupByIdContact(idContact);
    }
    public ContactGroup getOneContactGroup(long id){
        return contactGroupRepository.getOneContactGroup(id);
    }
    public ContactGroup addContact(long groupId, List<Long> contactIds){
        return contactGroupRepository.addContact(groupId,contactIds);
    }

    public ContactGroup removeContact(long groupId, long contactId){
        return contactGroupRepository.removeContact(groupId,contactId);
    }

    public void deleteContactGroup(long id){
        contactGroupRepository.deleteContact(id);
    }
}
