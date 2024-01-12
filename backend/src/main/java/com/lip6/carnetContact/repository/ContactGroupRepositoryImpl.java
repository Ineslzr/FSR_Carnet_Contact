package com.lip6.carnetContact.repository;

import com.lip6.carnetContact.configuration.JpaUtil;
import com.lip6.carnetContact.model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class ContactGroupRepositoryImpl implements ContactGroupRepository  {


    @Override
    public ContactGroup createContactGroup(String nameGroup) {

        ContactGroup contactGroup = null;
        try {
            EntityManager em= JpaUtil.getEmf().createEntityManager();

            EntityTransaction tx =  em.getTransaction();
            tx.begin();

            contactGroup = new ContactGroup(nameGroup);

            em.persist(contactGroup);
            tx.commit();

            em.close();

        }catch (Exception e) {
            e.printStackTrace();

        }
        return contactGroup;
    }

    @Override
    public ContactGroup updateContactGroup(long id, String nameGroup) {


        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        ContactGroup existingContactGroup = null;

        try {
            tx.begin();
            existingContactGroup = em.find(ContactGroup.class, id);

            if(existingContactGroup != null){
                existingContactGroup.setNameGroup(nameGroup);
            }

            em.merge(existingContactGroup);
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
        return existingContactGroup;
    }

    @Override
    public List<ContactGroup> findAllContactGroup() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();

        String requete = "SELECT cg FROM ContactGroup cg";
        TypedQuery<ContactGroup> query = em.createQuery(requete, ContactGroup.class);

        List<ContactGroup> results = query.getResultList();

        for (ContactGroup contact : results) {
            System.out.println("Contact:" + contact);
        }

        em.close();
        return results;
    }

    @Override
    public List<ContactGroup> findAllContactGroupByIdContact(long idContact) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();

        String requete = "SELECT c " +
                "FROM ContactGroup c " +
                "JOIN c.contacts contact " +
                "WHERE contact.idContact = :idContact";

        TypedQuery<ContactGroup> query = em.createQuery(requete, ContactGroup.class);
        query.setParameter("idContact", idContact);

        List<ContactGroup> results = query.getResultList();

        em.close();
        return results;
    }

    @Override
    public ContactGroup getOneContactGroup(long id) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        ContactGroup group = null;

        try {
            String jpql = "SELECT g FROM ContactGroup g LEFT JOIN FETCH g.contacts WHERE g.idContactGroup = :id";
            TypedQuery<ContactGroup> query = em.createQuery(jpql, ContactGroup.class);
            query.setParameter("id", id);

            group = query.getSingleResult();

            if (group != null) {
                group.getContacts().size();
            }
        } catch (NoResultException e) {
            System.out.println("Groupe non trouvé pour l'ID : " + id);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return group;
    }

    @Override
    public ContactGroup addContact(long groupId, List<Long> contactIds) {

        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        ContactGroup group = null;

        try {
            tx.begin();

            group = em.find(ContactGroup.class, groupId);
            if (group != null) {

                List<Contact> contactsToAdd = em.createQuery("SELECT c FROM Contact c WHERE c.idContact IN :contactIds", Contact.class)
                        .setParameter("contactIds", contactIds)
                        .getResultList();

                for(Contact contact : contactsToAdd){
                    group.addContact(contact);
                }

                em.merge(group);

            }

            tx.commit();

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return group;
    }

    @Override
    public ContactGroup removeContact(long groupId, long contactId) {

        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        ContactGroup group = null;
        try {
            tx.begin();

            group = em.find(ContactGroup.class, groupId);
            if (group != null) {
                Contact contactToDelete = em.find(Contact.class, contactId);

                if(contactToDelete != null){
                    group.removeContact(contactToDelete);
                }

                em.merge(group);

            }

            tx.commit();

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return group;
    }

    @Override
    public void deleteContact(long id) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query deleteContactQuery = em.createQuery("DELETE FROM ContactGroup c WHERE c.idContactGroup = :id");
            deleteContactQuery.setParameter("id", id);
            int deletedCount = deleteContactQuery.executeUpdate();

            transaction.commit();

            System.out.println("Nombre de contacts supprimées : " + deletedCount);
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
