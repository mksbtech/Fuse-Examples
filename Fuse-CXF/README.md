#Description 
<p>This is an example JAXWS,JAXRS and JPA osgi application built using gradle and deployed on Jboss Fuse Fabric 6.1 with database as Mysql 5.5.41</p>
<p>Steps to run the Fuse-CXF example on Jboss Fuse fabric
<br/>1.Create a mysql database fuse_db
<br/>2.Create a table Person in above database using below command
<br/>   CREATE TABLE `Person` (
  `ID` int(11) NOT NULL,
  `FIRST_NAME` varchar(45) DEFAULT NULL,
  `LAST_NAME` varchar(45) DEFAULT NULL,
  `EMAIL_ADDRESS` varchar(45) DEFAULT NULL,
  `AGE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
)
<br/>3.Checkout projects Fuse-Cxf and Fuse-Datasource from GIT.
<br/>  Fuse-Cxf contains code related to JaxWS and JaxRS webservices while Fuse-Datasource contains code related to datasource exposed as osgi service.
  Both projects use Blueprint as DI framework.
<br/>4.Run gradle clean build on root folders of both projects
<br/>5.Below two jar files will be created under build/libs directory of each project respectively
	<br/>Fuse-Cxf-1.0.0.jar
	<br/>Fuse-Datasource-1.0.jar
<br/>6.Start Jboss Fuse
<br/>7.Create admin user using following command.It will prompt for username and password.
<br/>	create-admin-user
<br/>8.Create fabric using following command
<br/>	fabric:create --zookeeper-password admin --wait-for-provisioning
<br/>9.Create a child container using following command,this will create a childContainer whose parent is root.
<br/>	container-create-child root childContainer
<br/>10.Create a profile myDSProfile for datasource bundle using following commands
<br/>	profile-create myDSProfile
<br/>	profile-edit --bundles mvn:commons-dbcp/commons-dbcp/1.4 --bundles mvn:mysql/mysql-connector-java/5.1.26 --bundles mvn:org.apache.geronimo.specs/geronimo-jta_1.1_spec/1.1.1 --bundles mvn:commons-pool/commons-pool/1.5.2 --bundles file:<Path to Fuse-Datasource-1.0.0.jar> myDSProfile
<br/>11.Create a profile myCxfProfile for Webservices bundle using following commands
	<br/>profile-create --parents feature-cxf myCxfProfile
	<br/>profile-edit --features jndi --features transaction --features jpa --features fabric-cxf --bundles mvn:commons-dbcp/commons-dbcp/1.4 --bundles file:<Path to Fuse-Cxf-1.0.0.jar> --bundles mvn:com.fasterxml.jackson.jaxrs/jackson-jaxrs-json-provider/2.0.1 --bundles mvn:commons-pool/commons-pool/1.5.2 --bundles wrap:mvn:net.sourceforge.serp/serp/1.13.1 --bundles mvn:org.apache.openjpa/openjpa/2.3.0 myCxfProfile
<br/>12.Start the childContainer using following command if not started already
<br/>	container-start childContainer
<br/>13.Add the new profiles to the container using following commands
<br/>	container-add-profile childContainer myDSProfile
<br/>	container-add-profile childContainer myCxfProfile
<br/>14.Check the container status using container-list command.It should be success once all dependencies have been downloaded.This may take sometime based on your internet speed.
<br/>15.Connect to childContainer using container-connect childContainer command.It will ask for admin username and password.Please use the same as admin user
<br/>16.Once connected to childContainer use following command to check the status of the two bundles
<br/>	osgi:list|grep Sample
<br/>	This will display the above two bundles.Check the status should be active and Create.Sample output is below
<br/>	[ 930] [Active     ] [Created     ] [       ] [   60] A Sample Jboss Fuse datasource bundle built using Gradle (1.0.0)
<br/>	[ 935] [Active     ] [Created     ] [       ] [   60] A Sample Jboss Fuse Cxf application build using Gradle (1.0.0)
<br/>17.Check the webservice endpoints with following command
<br/>	cxf:list-endpoints
<br/>	You should see something similar to below output in Fuse console
<br/>	Name                      State      Address                                                      BusID                                   
<br/>	[PersonRestServiceImpl  ] [Started ] [/services/rest                                            ] [com.milan.fuse.cxf-cxf1898868527      ]
<br/>	[PersonSoapServicePort  ] [Started ] [/PersonSoapService                                        ] [com.milan.fuse.cxf-cxf1898868527      ]
<br/>The above output confirms that the two bundles have been deployed successfully.

<br/>
#Testing
<br/>
#JAXWS
<br/>1.Download and install SOAP UI
<br/>2.Create a SOAP  project with wsdl location as 
<br/>http://localhost:8182/cxf/PersonSoapService?wsdl
<br/>3.Click OK
<br/>4.This will create a definition PersnSoapServiceServiceSoapBinding with a sample request as similar to below:
<br/><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:jax="http://jaxws.cxf.fuse.milan.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <jax:createPersonInBulk>
         <!--Zero or more repetitions:-->
         <person>
            <!--Optional:-->
            <age></age>
            <!--Optional:-->
            <email></email>
            <!--Optional:-->
            <firstName></firstName>
            <!--Optional:-->
            <lastName></lastName>
         </person>
         
      </jax:createPersonInBulk>
   </soapenv:Body>
</soapenv:Envelope> 
<br/>5.Fill in the data in the field and execute.You can add multiple person tag with different data in the fields.
<br/>6.Check in the database.Based on input corresponding records should be created in DB

<br/>
#JAXRS
<br/>1.Open browser and type following url
<br/>http://localhost:8182/cxf/services/rest/personRestService/getAllPersons
<br/>This will return the person records in Person table in DB in XML
<br/>2.Similarly you can use Postman/RestConsole plugins in chrome to verify json response and also the other two resful services which are 
<br/>http://localhost:8182/cxf/services/rest/personRestService/createPerson
<br/>http://localhost:8182/cxf/services/rest/personRestService/getPersonByEmail/{email}
<br/>where {email} is pathparam.
</p>
	
