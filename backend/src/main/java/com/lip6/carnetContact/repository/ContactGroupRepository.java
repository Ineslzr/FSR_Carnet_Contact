package com.lip6.carnetContact.repository;

import com.lip6.carnetContact.model.ContactGroup;

import java.util.List;

public interface ContactGroupRepository {

    public ContactGroup createContactGroup(String nameGroup);

    public ContactGroup updateContactGroup(long id, String nameGroup);

    public List<ContactGroup> findAllContactGroup();

    public List<ContactGroup> findAllContactGroupByIdContact(long idContact);

    public ContactGroup getOneContactGroup(long id);

    public ContactGroup addContact(long groupId, List<Long> contactIds);

    public ContactGroup removeContact(long groupId,long contactId);

    public void deleteContact(long id);
}
