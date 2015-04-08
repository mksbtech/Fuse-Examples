package com.milan.fuse.cxf.jaxrs;

import com.milan.fuse.cxf.exception.ServiceException;
import com.milan.fuse.cxf.service.PersonService;
import com.milan.fuse.cxf.vo.PersonVO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/personRestService")
public class PersonRestServiceImpl
{
  private static Logger LOG = Logger.getLogger(PersonRestServiceImpl.class.getName());
  private PersonService personService;
  
  @GET
  @Path("/getAllPersons")
  @Produces({"application/xml", "application/json"})
  public List<PersonVO> getAllPersons()
  {
    List<PersonVO> personList = null;
    try
    {
      personList = this.personService.getAllPersons();
    }
    catch (ServiceException se)
    {
      LOG.log(Level.SEVERE, se.getMessage(), se);
    }
    return personList;
  }
  
  @PUT
  @Path("/createPerson")
  @Consumes({"application/xml", "application/json"})
  @Produces({"application/xml", "application/json"})
  public String createPerson(PersonVO person)
  {
    boolean result = false;
    try
    {
      result = this.personService.createPerson(person);
    }
    catch (ServiceException se)
    {
      LOG.log(Level.SEVERE, se.getMessage(), se);
      result = false;
    }
    if (result) {
      return "Success";
    }
    return "Failure";
  }
  
  @GET
  @Path("/getPersonByEmail/{email}")
  @Consumes({"application/xml", "application/json"})
  @Produces({"application/xml", "application/json"})
  public PersonVO getPersonByEmail(@PathParam("email") String email)
  {
    PersonVO person = null;
    try
    {
      person = this.personService.getPersonByEmail(email);
    }
    catch (ServiceException se)
    {
      LOG.log(Level.SEVERE, se.getMessage(), se);
    }
    return person;
  }
  
  public void setPersonService(PersonService personService)
  {
    this.personService = personService;
  }
}
