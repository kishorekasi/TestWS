/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trackeasy.testws.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author kishoreu
 */

@XmlRootElement(name = "person")
@XmlType(propOrder={"id", "fullName", "age"})
public class Person implements java.io.Serializable {
    
    private int id;
    private String fullName;
    private int age;

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @XmlElement
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
   
    @Override
    public String toString() {
        return "Id: " + id + ", age: " + age + ", fullName: " + fullName;
    }
    
}


/*
public class Person implements java.io.Serializable {
    private Integer id;
    private Integer age;
    private String fullName;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    @Override
    public String toString() {
        return "Id: " + id + ", age: " + age + ", fullName: " + fullName;
    }
    
}
*/