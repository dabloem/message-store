/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mipagar.messagestore.aws;

import com.mipagar.messagestore.MessageStore;
import com.mipagar.messagestore.Recipient;
import com.mipagar.messagestore.UserMessage;
import com.spaceprogram.simplejpa.EntityManagerFactoryImpl;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 *
 * @author Duncan Bloem <dabloem@gmail.com>
 */
public class AmazonStore implements MessageStore {
    
    private static EntityManagerFactoryImpl factory = new EntityManagerFactoryImpl("PU", null, null, Collections.singleton("com.mipagar.messagestore.aws.SimpleDBUserMessage"));
    private EntityManager em = null;
    
    @Override
    public UserMessage get(String id){
        EntityManager em = factory.createEntityManager();
        return em.find(SimpleDBUserMessage.class, id);
    }
    
    @Override
    public List<UserMessage> fetch(Recipient recipient){
        if (em == null){
            em = factory.createEntityManager();
        }

        String name = recipient.getId().toString();
        List<UserMessage> resultList = em.createQuery("select o from SimpleDBUserMessage o where o.recipient = :rp").setParameter("rp", name).getResultList();
//        List<JPAUserMessage> resultList = em.createNativeQuery("select * from JPAUserMessage where recipient = :rp").setParameter("rp", name).getResultList();
//        List<JPAUserMessage> resultList = em.createNativeQuery("select * from JPAUserMessage").getResultList();
        em.close();
        return resultList;
    }
    
    @Override
    public UserMessage add(UserMessage message){
        EntityManager em = factory.createEntityManager();
        SimpleDBUserMessage userMessage = new SimpleDBUserMessage(message);
        em.persist(userMessage);
        em.close();
        return userMessage;
    }
    
    @Override
    public void remove(UserMessage message){
        EntityManager em = factory.createEntityManager();
        em.remove(message);
        em.close();
    }
    
    @Override
    public void update(UserMessage message){
        if (message instanceof SimpleDBUserMessage){
            EntityManager em = factory.createEntityManager();
            em.merge(message);
            em.close();
        } else {
            throw new PersistenceException("message not an SimpleDBUserMessage");
        }
    }
    
    @Override
    public void read(String messsageId){
        EntityManager em = factory.createEntityManager();
        SimpleDBUserMessage msg = new SimpleDBUserMessage(messsageId);
        msg.setRead(Boolean.TRUE);
        em.merge(msg);
        em.close();        
    }
    
    @Override
    public UserMessage archive(String messsageId){
        EntityManager em = factory.createEntityManager();
        SimpleDBUserMessage msg = new SimpleDBUserMessage(messsageId);
        msg.setArchived(Boolean.TRUE);
        
        SimpleDBUserMessage archived = em.merge(msg);
        em.close();    
        return archived;
    }

}
