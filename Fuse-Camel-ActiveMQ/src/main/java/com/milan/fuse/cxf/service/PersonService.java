package com.milan.fuse.cxf.service;

import com.milan.fuse.cxf.entity.Person;
import com.milan.fuse.cxf.exception.ServiceException;
import com.milan.fuse.cxf.vo.PersonVO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PersonService
{
  private EntityManager entityManager;
  
  public List<PersonVO> getAllPersons()
    throws ServiceException
  {
    PersonVO personVO = null;
    List<PersonVO> personVOList = new ArrayList(5);
    List<Person> personList = this.entityManager.createQuery("select p from Person p").getResultList();
    for (Person p : personList)
    {
      personVO = new PersonVO();
      personVO.setAge(p.getAge());
      personVO.setEmail(p.getEmail());
      personVO.setFirstName(p.getFirstName());
      personVO.setLastName(p.getLastName());
      personVOList.add(personVO);
    }
    return personVOList;
  }
  
  public boolean createPerson(PersonVO person)
    throws ServiceException
  {
    Person p = new Person();
    boolean result = false;
    p.setEmail(person.getEmail());
    p.setAge(person.getAge());
    p.setFirstName(person.getFirstName());
    p.setLastName(person.getLastName());
    try
    {
      this.entityManager.persist(p);
      this.entityManager.flush();
      
      result = true;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return result;
  }
  
  public PersonVO getPersonByEmail(String email)
    throws ServiceException
  {
    PersonVO personVO = null;
    
    Person p = (Person)this.entityManager.createQuery("select p from Person p where p.email='" + email + "'").getResultList().get(0);
    personVO = new PersonVO();
    personVO.setAge(p.getAge());
    personVO.setEmail(p.getEmail());
    personVO.setFirstName(p.getFirstName());
    personVO.setLastName(p.getLastName());
    return personVO;
  }
  
  public EntityManager getEntityManager()
  {
    return this.entityManager;
  }
  
  public void setEntityManager(EntityManager entityManager)
  {
    this.entityManager = entityManager;
  }
}
