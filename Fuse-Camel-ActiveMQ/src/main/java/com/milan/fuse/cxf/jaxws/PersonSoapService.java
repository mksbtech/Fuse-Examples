package com.milan.fuse.cxf.jaxws;

import com.milan.fuse.cxf.vo.PersonVO;
import java.util.List;
import javax.jws.WebParam;
import javax.jws.WebService;
/**
 * 
 * @author milan
 *
 */
@WebService(name="PersonSoapService")
public interface PersonSoapService
{
  public Boolean createPersonInBulk(@WebParam(name="person") List<PersonVO> paramList);
}
