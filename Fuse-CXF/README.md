Description :This is an example JAXWS,JAXRS and JPA osgi application built using gradle and deployed on Jboss Fuse Fabric 6.1 with database as Mysql 5.5.41 
Steps to run the Fuse-CXF example on Jboss Fuse fabric
1.Create a mysql database fuse_db
2.Create a table Person in above database using below command
   CREATE TABLE `Person` (
  `ID` int(11) NOT NULL,
  `FIRST_NAME` varchar(45) DEFAULT NULL,
  `LAST_NAME` varchar(45) DEFAULT NULL,
  `EMAIL_ADDRESS` varchar(45) DEFAULT NULL,
  `AGE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
)
3.Checkout projects Fuse-Cxf and Fuse-Datasource from GIT.
  Fuse-Cxf contains code related to JaxWS and JaxRS webservices while Fuse-Datasource contains code related to datasource exposed as osgi service.
  Both projects use Blueprint as DI framework.
4.Run gradle clean build on root folders of both projects
5.Below two jar files will be created under build/libs directory of each project respectively
	Fuse-Cxf-1.0.0.jar
	Fuse-Datasource-1.0.jar
6.Start Jboss Fuse
7.Create admin user using following command.It will prompt for username and password.
	create-admin-user
8.Create fabric using following command
	fabric:create --zookeeper-password admin --wait-for-provisioning
9.Create a child container using following command,this will create a childContainer whose parent is root.
	container-create-child root childContainer
10.Create a profile myDSProfile for datasource bundle using following commands
	profile-create myDSProfile
	profile-edit --bundles mvn:commons-dbcp/commons-dbcp/1.4 --bundles mvn:mysql/mysql-connector-java/5.1.26 --bundles mvn:org.apache.geronimo.specs/geronimo-jta_1.1_spec/1.1.1 --bundles mvn:commons-pool/commons-pool/1.5.2 --bundles file:<Path to Fuse-Datasource-1.0.0.jar> myDSProfile
11.Create a profile myCxfProfile for Webservices bundle using following commands
	profile-create --parents feature-cxf myCxfProfile
	profile-edit --features jndi --features transaction --features jpa --features fabric-cxf --bundles mvn:commons-dbcp/commons-dbcp/1.4 --bundles file:<Path to Fuse-Cxf-1.0.0.jar> --bundles mvn:com.fasterxml.jackson.jaxrs/jackson-jaxrs-json-provider/2.0.1 --bundles mvn:commons-pool/commons-pool/1.5.2 --bundles wrap:mvn:net.sourceforge.serp/serp/1.13.1 --bundles mvn:org.apache.openjpa/openjpa/2.3.0 myCxfProfile
12.Start the childContainer using following command if not started already
	container-start childContainer
13.Add the new profiles to the container using following commands
	container-add-profile childContainer myDSProfile
	container-add-profile childContainer myCxfProfile
14.Check the container status using container-list command.It should be success once all dependencies have been downloaded.This may take sometime based on your internet speed.
15.Connect to childContainer using container-connect childContainer command.It will ask for admin username and password.Please use the same as admin user
16.Once connected to childContainer use following command to check the status of the two bundles
	osgi:list|grep Sample
	This will display the above two bundles.Check the status should be active and Create.Sample output is below
	[ 930] [Active     ] [Created     ] [       ] [   60] A Sample Jboss Fuse datasource bundle built using Gradle (1.0.0)
	[ 935] [Active     ] [Created     ] [       ] [   60] A Sample Jboss Fuse Cxf application build using Gradle (1.0.0)
17.Check the webservice endpoints with following command
	cxf:list-endpoints
	You should see something similar to below output in Fuse console
	Name                      State      Address                                                      BusID                                   
	[PersonRestServiceImpl  ] [Started ] [/services/rest                                            ] [com.milan.fuse.cxf-cxf1898868527      ]
	[PersonSoapServicePort  ] [Started ] [/PersonSoapService                                        ] [com.milan.fuse.cxf-cxf1898868527      ]
The above output confirms that the two bundles have been deployed successfully.

Testing
JAXWS
1.Download and install SOAP UI
2.Create a SOAP  project with wsdl location as 
http://localhost:8182/cxf/PersonSoapService?wsdl
3.Click OK
4.This will create a definition PersnSoapServiceServiceSoapBinding with a sample request as similar to below:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:jax="http://jaxws.cxf.fuse.milan.com/">
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
5.Fill in the data in the field and execute.You can add multiple person tag with different data in the fields.
6.Check in the database.Based on input corresponding records should be created in DB

JAXRS
1.Open browser and type following url
http://localhost:8182/cxf/services/rest/personRestService/getAllPersons
This will return the person records in Person table in DB in XML
2.Similarly you can use Postman/RestConsole plugins in chrome to verify json response and also the other two resful services which are 
http://localhost:8182/cxf/services/rest/personRestService/createPerson
http://localhost:8182/cxf/services/rest/personRestService/getPersonByEmail/{email}

where {email} is pathparam.
	
	