/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mipagar.messagestore.aws;

import com.mipagar.messagestore.UserMessage;
import com.mipagar.messagestore.validation.UserMessageEntityListener;
import com.spaceprogram.simplejpa.model.IdedTimestampedBase;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author Duncan Bloem <dabloem@gmail.com>
 */
@Entity
@EntityListeners(UserMessageEntityListener.class)
public class SimpleDBUserMessage extends IdedTimestampedBase implements UserMessage {
    
    private String type;
    private String sender;
    private String recipient;
    private String title;
    private String message;
    private Boolean read;
    private Boolean archived;;

    public SimpleDBUserMessage() {}
    
    public SimpleDBUserMessage(UserMessage message){
        this.id = message.getId();
        this.recipient = message.getRecipient();
        this.title = message.getTitle();
        this.message = message.getMessage();
        this.sender = message.getSender();
        this.type = message.getType();
        this.read = message.getRead();
        this.archived = message.getArchived();
    }

    public SimpleDBUserMessage(String messageId) {
        this.id = messageId;
    }

    public SimpleDBUserMessage(String recipient, String message) {
        this.recipient = recipient;
        this.message = message;
    }
    
    SimpleDBUserMessage(String messageId, String recipient, String message) {
        this.id = messageId;
        this.recipient = recipient;
        this.message = message;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Override
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    @Override
    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    @Override
    public String toString() {
        return "JPAUserMessage{" + "id=" + id + ", created=" + getCreated() + ", updated=" + getUpdated() + ", recipient=" + recipient + ", sender=" + sender + ", title=" + title + ", message=" + message + '}';
    }
    
}
