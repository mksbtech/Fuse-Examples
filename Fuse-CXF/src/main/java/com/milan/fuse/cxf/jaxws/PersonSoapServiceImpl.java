package com.milan.fuse.cxf.jaxws;

import java.util.List;

import javax.jws.WebService;

import com.milan.fuse.cxf.service.PersonService;
import com.milan.fuse.cxf.vo.PersonVO;

@WebService(endpointInterface="com.milan.fuse.cxf.jaxws.PersonSoapService")
public class PersonSoapServiceImpl
  implements PersonSoapService
{
	private PersonService personService;
  public Boolean createPersonInBulk(List<PersonVO> personList)
  {	  
    for (PersonVO p : personList) {
      personService.createPerson(p);
    }
    return new Boolean(true);
  }
public PersonService getPersonService() {
	return personService;
}
public void setPersonService(PersonService personService) {
	this.personService = personService;
}
}
