/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mipagar.messagestore.aws;

import com.mipagar.messagestore.Recipient;
import com.mipagar.messagestore.UserMessage;
import com.mipagar.messagestore.aws.SimpleDBUserMessage;
import com.mipagar.messagestore.aws.AmazonStore;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Duncan Bloem <dabloem@gmail.com>
 */
public class AmazonStoreTest {
    
    public AmazonStoreTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of fetch method, of class AmazonStore.
     */
    @Test
    public void testFetch() {
        System.out.println("fetch");
        Recipient recipient = new Recipient();
        recipient.setId(123456L);
        
        AmazonStore instance = new AmazonStore();
        List<UserMessage> result = instance.fetch(recipient);
        result.size();
        for (UserMessage message : result) {
            System.out.println(message.toString());
        }
        
        UserMessage get = instance.get("134c6b66-7d79-4881-b895-48706485cb20");
        
        result = instance.fetch(recipient);
        assertEquals(3, result.size());
    }
    
    @Test
    @Ignore
    public void testGet(){
        AmazonStore instance = new AmazonStore();
        UserMessage message = instance.get("7ee1ee02-a5a5-4629-9cc2-de2ed4245bf8");
        assertNotNull(message);
        Logger.getLogger(AmazonStoreTest.class.getName()).log(Level.INFO, "{0}", message);
    }

    @Test
    @Ignore
    public void testUpdate(){
        AmazonStore instance = new AmazonStore();
        UserMessage message = instance.get("b6b253b0-44d5-4a52-b20d-435471d831c0");
        
        SimpleDBUserMessage updatedMessage = new SimpleDBUserMessage(message.getId(), "8533", message.getMessage());
        Date expected = message.getUpdated();
        
        instance.update(updatedMessage);
        
        Logger.getLogger(AmazonStoreTest.class.getName()).log(Level.INFO, "{0}", updatedMessage);
        assertNotSame(expected, updatedMessage.getUpdated());
    }
    
    @Test
    public void testAdd(){
        SimpleDBUserMessage message = new SimpleDBUserMessage("123456", "already read");
        message.setRead(Boolean.TRUE);
        AmazonStore instance = new AmazonStore();
        UserMessage add = instance.add(message);
        assertNotNull(add.getId());
        
        Logger.getLogger(AmazonStoreTest.class.getName()).log(Level.INFO, "{0}", add);
    }

    @Test(expected=PersistenceException.class)
    @Ignore
    public void testAddEmpty(){
        SimpleDBUserMessage message = new SimpleDBUserMessage("", "no recipient");
        AmazonStore instance = new AmazonStore();
        instance.add(message);

        SimpleDBUserMessage noMessage = new SimpleDBUserMessage("12345", "");
        instance.add(noMessage);
        
        Logger.getLogger(AmazonStoreTest.class.getName()).log(Level.INFO, "{0}", noMessage);
    }
    
    @Test
    public void testRemove(){
        AmazonStore instance = new AmazonStore();
        UserMessage message = instance.get("8a015d08-d328-41cf-a369-a83c6a8ed362");
        System.out.println(message);
        instance.remove(message);
        Logger.getLogger(AmazonStoreTest.class.getName()).log(Level.INFO, "{0}", message);        
    }
    
    @Test
    @Ignore
    public void testArchive(){
        AmazonStore instance = new AmazonStore();
        SimpleDBUserMessage message = new SimpleDBUserMessage("to be archived");
        instance.add(message);
        assertFalse(message.getArchived());
        
        UserMessage archived = instance.archive(message.getId());
        assertTrue(archived.getArchived());
        System.out.println(archived);
    }
    
}
