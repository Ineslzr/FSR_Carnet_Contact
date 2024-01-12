package com.lip6.carnetContact.repository;

import com.lip6.carnetContact.configuration.JpaUtil;
import com.lip6.carnetContact.model.Address;
import com.lip6.carnetContact.model.Contact;
import com.lip6.carnetContact.model.PhoneNumber;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class ContactRepositoryImpl implements ContactRepository{

    @Override
    public Contact addContact(String firstname, String lastname, String email, String street, String city, String zip, String country, Set<PhoneNumber> phones) {

        Contact contact= null;
        try {
            EntityManager em= JpaUtil.getEmf().createEntityManager();

            EntityTransaction tx =  em.getTransaction();
            tx.begin();

            Address address = new Address(street, city, zip, country);
            contact = new Contact(firstname, lastname, email);
            contact.setAddresse(address);

            for(PhoneNumber phoneNumber : phones){
                phoneNumber.setContact(contact);
            }
            contact.setPhones(phones);
            em.persist(contact);

            tx.commit();

            em.close();

        }
        catch (Exception e) {
            e.printStackTrace();

        }
        return contact;
    }

    @Override
    public List<Contact> getAllContacts() {

        List<Contact> contacts = null;

        try {
            EntityManager em = JpaUtil.getEmf().createEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Contact> cq = cb.createQuery(Contact.class);
            Root<Contact> contactRoot = cq.from(Contact.class);

            contactRoot.fetch("addresse", JoinType.LEFT);
            SetJoin<Contact, PhoneNumber> phoneJoin = contactRoot.joinSet("phones", JoinType.LEFT);

            cq.select(contactRoot).distinct(true);

            TypedQuery<Contact> query = em.createQuery(cq);
            contacts = query.getResultList();

            em.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contacts;
    }

    @Override
    public Contact getOneContact(Long id) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        Contact contact = null;
        try {
            contact = em.find(Contact.class, id);
            if (contact != null) {

                contact.getAddresse();
                contact.getPhones().size();
                contact.getContactGroups().size();
            }
        } catch (NoResultException e) {
            System.out.println("Contact non trouvé pour l'ID : " + id);
        }

        em.close();
        return contact;
    }

    @Override
    public void deleteContact(Long id) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query deletePhoneNumbersQuery = em.createQuery("DELETE FROM PhoneNumber p WHERE p.contact.idContact = :contactId");
            deletePhoneNumbersQuery.setParameter("contactId", id);
            deletePhoneNumbersQuery.executeUpdate();

            Query deleteContactQuery = em.createQuery("DELETE FROM Contact c WHERE c.idContact = :contactId");
            deleteContactQuery.setParameter("contactId", id);
            int deletedCount = deleteContactQuery.executeUpdate();

            transaction.commit();

        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    @Override
    public Contact updateContact(Long idContact,String firstname, String lastname, String email, String street, String city, String zip, String country, Set<PhoneNumber> phones) {

        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Contact existingContact = null;
        try {
            tx.begin();
            existingContact = em.find(Contact.class, idContact);

            if(existingContact != null){
                Address address = new Address(street, city, zip, country);
                existingContact.setFirstName(firstname);
                existingContact.setLastName(lastname);
                existingContact.setEmail(email);

                existingContact.setAddresse(address);

                Set<PhoneNumber> existingPhones = existingContact.getPhones();
                Set<PhoneNumber> phonesToAdd = new HashSet<>();

                for (PhoneNumber newPhone : phones) {

                    for (PhoneNumber existingPhone : existingPhones) {
                        if (!(existingPhone.getPhoneKind().equals(newPhone.getPhoneKind()) &&
                                existingPhone.getPhoneNumber().equals(newPhone.getPhoneNumber()))) {
                            newPhone.setContact(existingContact);
                            phonesToAdd.add(newPhone);
                        }
                    }

                }

                existingContact.getPhones().addAll(phonesToAdd);
            }

            em.merge(existingContact);
            tx.commit();

        }catch (PersistenceException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }


        return existingContact;
    }

    @Override
    public void deletePhoneNumber(Long idPhoneNumber) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query deletePhoneNumbersQuery = em.createQuery("DELETE FROM PhoneNumber p WHERE p.idPhoneNumber = :phoneId");
            deletePhoneNumbersQuery.setParameter("phoneId", idPhoneNumber);
            deletePhoneNumbersQuery.executeUpdate();

            transaction.commit();

        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
