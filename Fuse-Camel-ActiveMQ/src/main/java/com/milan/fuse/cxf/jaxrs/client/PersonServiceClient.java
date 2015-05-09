package com.milan.fuse.cxf.jaxrs.client;

import java.util.List;
import java.util.logging.Level;

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
public interface PersonServiceClient {
	@GET
	  @Path("/getAllPersons")
	  @Produces({"application/xml", "application/json"})
	  public List<PersonVO> getAllPersons();
//	  {
//	    return null;
//	  }
	  
	  @PUT
	  @Path("/createPerson")
	  @Consumes({"application/xml", "application/json"})
	  @Produces({"application/xml", "application/json"})
	  public String createPerson(PersonVO person);
//	  {
//	   
//	   return null;
//	  }
	  
	  @GET
	  @Path("/getPersonByEmail/{email}")
	  @Consumes({"application/xml", "application/json"})
	  @Produces({"application/xml", "application/json"})
	  public PersonVO getPersonByEmail(@PathParam("email") String email);
//	  {
//	    return null;
//	  }

}
