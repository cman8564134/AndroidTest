package com.example.user.androidtest.Modal;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by User on 3/3/2018.
 */
public class PersonTest {

    private Person person;

    @Before
    public void setup()
    {
        person=new Person();
    }

    @Test
    public void personIsNotNull() {
        assertNotNull(person);
    }

    @Test
    public void factoryConstrutor()throws Exception
    {
        assertNull(person.getFirstName());
        assertNull(person.getLastName());
        assertNull(person.getPhoneNo());
    }

    @Test
    public void personConstructor()throws Exception{

        person=new Person("asd","fgh",UserType.Private,"0123456789");
        assertNotNull(person);
        assertEquals("asd",person.getFirstName());
        assertEquals("fgh",person.getLastName());
        assertEquals(UserType.Private,person.getType());
        assertEquals("0123456789",person.getPhoneNo());
    }

    @Test
    public void setFirstName() throws Exception {
        //for both getter and setter
        person.setFirstName("newName");
        assertEquals("newName",person.getFirstName());
    }



    @Test
    public void setLastName() throws Exception {
        //for both getter and setter
        person.setLastName("newLastName");
        assertEquals("newLastName",person.getLastName());
    }



    @Test
    public void setType() throws Exception {
        //for both getter and setter
        person.setType(UserType.Dealer);
        assertEquals(UserType.Dealer,person.getType());
    }



    @Test
    public void setPhoneNo() throws Exception {
        //for both getter and setter
        person.setPhoneNo("0124532516");
        assertEquals("0124532516",person.getPhoneNo());
    }





}