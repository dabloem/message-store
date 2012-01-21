/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mipagar.messagestore;

import java.util.Date;

/**
 *
 * @author Duncan Bloem <dabloem@gmail.com>
 */
public interface UserMessage {
    
    String getId();
    
    Date getCreated();

    Date getUpdated();
    
    Boolean getArchived();

    String getMessage();

    Boolean getRead();

    String getRecipient();

    String getSender();

    String getTitle();

    String getType();
    
}
