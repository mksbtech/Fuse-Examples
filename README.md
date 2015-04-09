# Fuse-Examples
JAXWS,JAXRS,JPA,Blueprint example for Jboss Fuse fabric using Gradle as build tool.It consists of following two projects
1. Fuse-Datasource : This project uses blueprint file to expose  a Mysql database as OSGI service.
   Please read the project readme.md file to deploy and run this pproject on Fuse Fabric
2. Fuse-Cxf: This project exposes a JAXWS and 3 JAXRS webservices which uses JPA and OSGI to connect with the datasource 
   exposed by above project.Please read the project readme.md file to deploy and run this pproject on Fuse Fabric
