/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mipagar.messagestore.aws;

import com.mipagar.messagestore.aws.SimpleDBUserMessage;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.mipagar.messagestore.AmazonMsgStore;
import com.mipagar.messagestore.NoUserMessage;
import com.mipagar.messagestore.Recipient;
import com.mipagar.messagestore.Sender;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.*;
import org.junit.rules.MethodRule;

/**
 *
 * @author Duncan Bloem <dabloem@gmail.com>
 */
@BenchmarkOptions(callgc = false, benchmarkRounds = 10, warmupRounds = 0)
public class AmazonMsgStoreTest {
    
    @Rule
    public BenchmarkRule benchmarkRule = new BenchmarkRule();
    
    public AmazonMsgStore amazonMsgStore = new AmazonMsgStore();
    
    public AmazonMsgStoreTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        amazonMsgStore.init();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of add method, of class AmazonMsgStore.
     */
    @Test
    @Ignore
    public void testAdd() {
        System.out.println("add");
        Sender s = new Sender();
        s.setId(1234L);
        s.setName("Duncan");
        
        Recipient r = new Recipient();
        r.setId(6789L);
        r.setName("Matthijs");
                
        NoUserMessage userMessage = new NoUserMessage(s, r, "Subject title", "This is a text");
        AmazonMsgStore instance = new AmazonMsgStore();
        instance.init();
        
        boolean expResult = true;
        boolean result = instance.add(userMessage);
        assertEquals(expResult, result);
    }

    @Test
    public void addJPAMessage(){
        System.out.println("add JPA Message");
        SimpleDBUserMessage message = new SimpleDBUserMessage();
        message.setMessage("My first JPA Message");
        message.setRecipient("12345");
        
        String id = amazonMsgStore.add(message);
        System.out.println(id);
    }
    
    /**
     * Test of remove method, of class AmazonMsgStore.
     */
    @Test
    @Ignore
    public void testRemove() {
        System.out.println("remove");
        NoUserMessage userMessage = new NoUserMessage();
        userMessage.setId("f48ae9f5-62dc-416d-a48c-dadfe95b0c55");
        
        AmazonMsgStore instance = new AmazonMsgStore();
        instance.init();
        
        boolean expResult = true;
        boolean result = instance.remove(userMessage);
        assertEquals(expResult, result);
    }

    /**
     * Test of fetch method, of class AmazonMsgStore.
     */
    @Test
    @Ignore
    public void testFetch() {
        System.out.println("fetch");
        Recipient recipient = new Recipient();
        recipient.setId(6789L);
//        AmazonMsgStore instance = new AmazonMsgStore();
//        instance.init();

        List expResult = null;

        List<NoUserMessage> result = amazonMsgStore.fetch(recipient);
        assertEquals(30, result.size());
    }

    /**
     * Test of read method, of class AmazonMsgStore.
     */
    @Test
    @Ignore
    public void testRead() {
        System.out.println("read");
        NoUserMessage userMessage = new NoUserMessage();
        userMessage.setId("fcbb4646-22ed-4c96-8a8f-2787921d4ecd");
        AmazonMsgStore instance = new AmazonMsgStore();
        instance.init();
        
        boolean expResult = true;
        boolean result = instance.read(userMessage);
        assertEquals(expResult, result);
    }
}
