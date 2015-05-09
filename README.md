# Fuse-Examples
<p>JAXWS,JAXRS,JPA,Blueprint example for Jboss Fuse fabric using Gradle as build tool.It consists of following two projects:</p>
<p>1. Fuse-Datasource : This project uses blueprint file to expose  a Mysql database as OSGI service.
   <br/>Please read the project readme.md file to deploy and run this project on Fuse Fabric</p>
<p>2. Fuse-Cxf: This project exposes a JAXWS and 3 JAXRS webservices which uses JPA and OSGI to connect with the datasource<br/>
   exposed by above project.Please read the project readme.md file to deploy and run this pproject on Fuse Fabric</p>
<p>3. Fuse-Camel-ActiveMQ: This project contains following two camel routes<br/>
(i)ProducerRoute:This route has a SOAP webservices as from endpoint.This servies takes list of person objects as input.The to endpoint of the route is ActiveMQ queue called myqueue.This route takes the payload from SOAP service and places in in myqueue as POJO.</br>
(i)ConsumerRoute:From endpoint is myqueue while To endpoint is a restful service createPerson method.This route fetches the list from queue and slip it into person objects and calls the restful method passing each person object as input.This result creation of person records in mysql DB exposed as OSGI service via Fuse-Datasource project.</br>
</p>
