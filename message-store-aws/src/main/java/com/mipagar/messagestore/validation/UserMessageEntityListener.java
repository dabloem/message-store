/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mipagar.messagestore.validation;

import com.mipagar.messagestore.aws.SimpleDBUserMessage;
import javax.persistence.PersistenceException;
import javax.persistence.PrePersist;

/**
 *
 * @author Duncan Bloem <dabloem@gmail.com>
 */
public class UserMessageEntityListener {
    
    @PrePersist
    public void prePersist(Object object){
        SimpleDBUserMessage message = (SimpleDBUserMessage) object;
        if (message.getMessage().trim().isEmpty()){
            throw new PersistenceException("Message cannot be null");
        }
        if (message.getRecipient().trim().isEmpty()){
            throw new PersistenceException("Recipient cannot be null");
        }
    }
}
