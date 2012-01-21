/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mipagar.messagestore;

import com.mipagar.messagestore.aws.SimpleDBUserMessage;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.*;
import com.spaceprogram.simplejpa.EntityManagerFactoryImpl;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 *
 * @author Duncan Bloem <dabloem@gmail.com>
 */
public class AmazonMsgStore implements NoMessageStore {
    
    private AmazonSimpleDB amazonSimpleDB;
    
    private Map<Recipient, List<NoUserMessage>> userMessages = new HashMap();
    
    private static EntityManagerFactoryImpl factory;
    
    @PostConstruct
    public void init(){
        amazonSimpleDB = new AmazonSimpleDBClient(new BasicAWSCredentials("AKIAJY2G37GQNFYPOOFQ", "frxoni8S1BJc0PeE939mbicVnPN5GPDKL9MOlyLW"));
        
        factory = new EntityManagerFactoryImpl("PU", null, null, Collections.singleton("com.mipagar.messagestore.JPAUserMessage"));
//        EntityManager em = factory.createEntityManager();
//        JPAUserMessage find = em.find(JPAUserMessage.class, "8257ff91-2480-441a-805c-3b6406d4e3cf");
//        System.out.println(find.getMessage());
    }
    
    @Override
    public boolean add(NoUserMessage userMessage){
        String id = UUID.randomUUID().toString();
        ReplaceableAttribute recipientAttr = new ReplaceableAttribute("Recipient", userMessage.getRecipient().getId().toString(), false);
        ReplaceableAttribute senderAttr = new ReplaceableAttribute("Sender", userMessage.getSender().getId().toString(), false);
        ReplaceableAttribute messageAttr = new ReplaceableAttribute("Message", userMessage.getMessage(), true);
        ReplaceableAttribute titleAttr = new ReplaceableAttribute("Title", userMessage.getTitle(), true);

        PutAttributesRequest par = new PutAttributesRequest()
                .withDomainName("MsgStore")
                .withItemName(id)
                .withAttributes(recipientAttr, senderAttr, messageAttr, titleAttr);
        amazonSimpleDB.putAttributes(par);
        return true;
    }
    
    public String add(SimpleDBUserMessage message){
        EntityManager em = factory.createEntityManager();
        em.persist(message);
        em.close();
        return message.getId();
    }
    
    @Override
    public boolean remove(NoUserMessage userMessage){
        DeleteAttributesRequest dar = new DeleteAttributesRequest("MsgStore", userMessage.getId());
        amazonSimpleDB.deleteAttributes(dar);
        return true;
    }

    @Override
    public List<NoUserMessage> fetch(Recipient recipient){
        List<NoUserMessage> ums = userMessages.get(recipient);
        List<NoUserMessage> result = new ArrayList<NoUserMessage>();
        if (ums != null){
            result.addAll(ums);
            return result;
        } else {
            SelectResult select = amazonSimpleDB.select(new SelectRequest(String.format("select * from MsgStore where Recipient = '%d'", recipient.getId())));
            List<Item> items = select.getItems();
            for (Item item : items) {
                NoUserMessage um = new NoUserMessage(null, recipient, getSingleAttribute(item.getAttributes(), "Title") , getSingleAttribute(item.getAttributes(), "Message"));
                um.setId(item.getName());
                um.setRead(Boolean.valueOf(getSingleAttribute(item.getAttributes(), "Read")));
                result.add(um);
            }
            userMessages.put(recipient, result);
        }
        return result;
    }
    
    @Override
    public boolean read(NoUserMessage userMessage){
        PutAttributesRequest par = new PutAttributesRequest()
            .withDomainName("MsgStore")
            .withItemName(userMessage.getId())
            .withAttributes(new ReplaceableAttribute("read", Boolean.TRUE.toString(), Boolean.TRUE));
        amazonSimpleDB.putAttributes(par);
        return true;
    }

    private String getSingleAttribute(List<Attribute> attributes, String attributeName) {
        for (Attribute attribute : attributes) {
            if (attributeName.equalsIgnoreCase(attribute.getName())){
                return attribute.getValue();
            }
        }
        return null;
    }
}
