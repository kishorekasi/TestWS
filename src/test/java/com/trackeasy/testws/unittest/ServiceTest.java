/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trackeasy.testws.unittest;

import com.google.gson.Gson;
import com.trackeasy.testws.model.Person;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kishoreu
 */
public class ServiceTest {
    
    //Server details
    //LOCAL Server
    private static final String LOCALHOST = "localhost";
    private static final String LOCALHOST_PORT = "8080";
    private static final String LOCALHOST_DEPLOY_INSTANCE = "TestWS";
    private static final String LOCAL_SERVER = LOCALHOST + ":" + LOCALHOST_PORT + "/" + LOCALHOST_DEPLOY_INSTANCE;
    
    //AWS Instance
    public static final String AWS_INSTANCE_NAME = "testws";
    public static final String AWS_DEPLOYMENT_INSTANCE_SUFFIX = "";
    public static final String AWS_TEST_INSTANCE_SUFFIX = "";
    
    //AWS Deployment Server
    private static final String AWS_DEPLOYMENT_INSTANCE_NAME = AWS_INSTANCE_NAME + AWS_DEPLOYMENT_INSTANCE_SUFFIX;
    private static final String AWS_DEPLOYMENT_INSTANCE = AWS_DEPLOYMENT_INSTANCE_NAME + ".elasticbeanstalk.com";
    private static final String AWS_DEPLOYMENT_SERVER = AWS_DEPLOYMENT_INSTANCE;
    
    //AWS Test Server
    private static final String AWS_TEST_INSTANCE_NAME = AWS_INSTANCE_NAME + AWS_TEST_INSTANCE_SUFFIX;;
    private static final String AWS_TEST_INSTANCE = AWS_TEST_INSTANCE_NAME + ".elasticbeanstalk.com";
    private static final String AWS_TEST_SERVER = AWS_TEST_INSTANCE;
    
    private static final String TEST_SERVER = AWS_TEST_SERVER;
    private static final String SERVER = TEST_SERVER; /* currently used server */
    private static final String BASE_URL = "http://" + SERVER + "/service/";
    
    
    //Logger
    private static final Logger LOG = LoggerFactory.getLogger(ServiceTest.class);
    private Collection collection;
    
    
    @BeforeClass
    public static void oneTimeSetUp() {
        LOG.debug("Staring one time setup");
    }
    
    @AfterClass
    public static void oneTimeTearDown() {
        LOG.debug("Tear down before exit");
    }
    
    @Before
    public void setUp() {
        collection = new ArrayList();
        LOG.debug("@Before - setUp");
    }
    
    @After
    public void tearDown() {
        collection.clear();
        LOG.debug("@After - tearDown");
    }
    
    @Ignore
    @Test
    public void testGetPerson() throws Exception {
        LOG.trace("Get XML test");
        
        int id = 1;
        URL getURL = new URL(BASE_URL + "getPersonByIdXML/" + id);
        HttpURLConnection conn = (HttpURLConnection) getURL.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/xml");
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        String line = reader.readLine();
        while (line != null) {
            LOG.trace(line);
            line = reader.readLine();
        }
        
        Assert.assertEquals(HttpURLConnection.HTTP_OK, conn.getResponseCode());
    }
    
    @Ignore
    @Test
    public void testPostPerson() throws Exception {
        LOG.trace("POST JSON test");
        
        int id = 1;
        URL getURL = new URL(BASE_URL + "updatePersonById/" + id);
        HttpURLConnection conn = (HttpURLConnection) getURL.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        
        Person p = new Person();
        p.setAge(10);
        p.setId(1);
        p.setFullName("Rachitha");
        
        Gson gson = new Gson();
        String reqBody = gson.toJson(p, Person.class);
        
        
        OutputStream os = conn.getOutputStream();
        os.write(reqBody.getBytes());
        os.flush();
        
        Assert.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, conn.getResponseCode());
    }
}
