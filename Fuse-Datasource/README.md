Description :This is an example JAXWS,JAXRS and JPA osgi application built using gradle and deployed on Jboss Fuse Fabric 6.1 with database as Mysql 5.5.41 .This consists of two bundles Fuse-Datasource and Fuse-Cxf.
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
