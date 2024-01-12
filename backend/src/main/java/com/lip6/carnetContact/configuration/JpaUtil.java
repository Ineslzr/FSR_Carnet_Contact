package com.lip6.carnetContact.configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

    private static EntityManagerFactory emf = null;

    public static EntityManagerFactory getEmf() {
        if(emf == null) {
            emf = Persistence.createEntityManagerFactory("projetJPA");
        }
        return emf;
    }

    public static void close(){
        if( emf != null){
            emf.close();
            emf = null;
        }
    }
}
