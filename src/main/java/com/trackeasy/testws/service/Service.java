/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trackeasy.testws.service;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.trackeasy.testws.model.Person;
import com.trackeasy.testws.platform.HibernateUtil;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kishoreu
 */
@Path("service")
public class Service {
    
    //Logger
    private static final Logger LOG = LoggerFactory.getLogger(Service.class);
    //Should simulate a 'database'
    private static Map<Integer,Person> persons = new HashMap<Integer,Person>();
    
    //Insert test data in simulated database
    static {
        /*for (int i = 0; i < 10; i++) {
            Person person = new Person();
            int id = i + 1;
            person.setId(id);
            person.setFullName(("My Wonderful Person " + id));
            person.setAge(new Random().nextInt(40) + 1);
            
            persons.put(id,person);
        }*/
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Person"); 
        List<Person> resultSet = query.list();
        
        Person p;
        for(int i = 0; i < resultSet.size(); i++) {
            persons.put(resultSet.get(i).getId(), resultSet.get(i));
        }
    }
    
    //Method which should return a single person object in XML format
    @GET
    @Path("/getPersonByIdXML/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Person getPersonByIdXML(@PathParam("id") int id) {
        return persons.get(id);
    }
    
    @POST
    @Path("/updatePersonById/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updatePersonById(@PathParam("id") int id, InputStream in) {
        LOG.trace("inside updatePersonById: " + id);
        Person p = (Person) parsePersonJSON(in, Person.class);
        LOG.trace(p.toString());
        persons.put(id, p);
        //return p;
    }
    
    //Method which should return a single person object in JSON format
    @GET
    @Path("/getPersonByIdJSON/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getPersonByIdJSON(@PathParam("id") int id) {
        return persons.get(id);
    }
    
    //Method which should return a list of all person objects in XML format
    @GET
    @Path("/getAllPersonsInXML")
    @Produces(MediaType.APPLICATION_XML)
    public List<Person> getAllPersonsInXML() {
        return new ArrayList<Person>(persons.values());
    }
    
    @GET
    @Path("/getPersonFullNameInXML/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public String getPersonFullNameInXML(@PathParam("id") int id) {
        return persons.get(id).getFullName();
    }
    
    @GET
    @Path("/getPersonFullNameInJSON/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPersonFullNameInJSON(@PathParam("id") int id) {
        return persons.get(id).getFullName();
    }
    
    protected Person parsePersonJSON (InputStream in, Class objType) {
        Person retObj;
                
        try {
            Gson gson = new Gson();
            retObj = new Person();
            retObj = (Person) gson.fromJson(new InputStreamReader(in), Person.class);
        } catch (Exception e) {
            LOG.debug(e.getMessage());
            throw new WebApplicationException (e, Response.Status.BAD_REQUEST);
            
        }
        
        return retObj;
    }

}
