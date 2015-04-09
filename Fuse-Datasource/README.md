#Description <p>This is an example JAXWS,JAXRS and JPA osgi application built using gradle and deployed on Jboss Fuse Fabric 6.1 with database as Mysql 5.5.41 .This consists of two bundles Fuse-Datasource and Fuse-Cxf.</p>
<p>Steps to run the Fuse-CXF example on Jboss Fuse fabric</p>
<p>1.Create a mysql database fuse_db</p>
<p>2.Create a table Person in above database using below command
   <br/>CREATE TABLE `Person` (
  `ID` int(11) NOT NULL,
  `FIRST_NAME` varchar(45) DEFAULT NULL,
  `LAST_NAME` varchar(45) DEFAULT NULL,
  `EMAIL_ADDRESS` varchar(45) DEFAULT NULL,
  `AGE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
)</p>
<p>3.Checkout projects Fuse-Datasource from GIT.
  <br/>Fuse-Datasource contains code related to datasource exposed as osgi service.
  </p>
<p>4.Run gradle clean build on root folder of project</p>
<p>5.Following jar file will be created under build/libs directory of each project respectively
<br/>	Fuse-Datasource-1.0.0jar
</p>
<p>6.Start Jboss Fuse</p>
<p>7.Create admin user using following command.It will prompt for username and password.
	<br/>create-admin-user
</p>
<p>8.Create fabric using following command
	<br/>fabric:create --zookeeper-password admin --wait-for-provisioning</p>
<p>9.Create a child container using following command,this will create a childContainer whose parent is root.
<br/>container-create-child root childContainer</p>
<p>10.Create a profile myDSProfile for datasource bundle using following commands
	<br/>profile-create myDSProfile
	<br/>profile-edit --bundles mvn:commons-dbcp/commons-dbcp/1.4 --bundles mvn:mysql/mysql-connector-java/5.1.26 --bundles mvn:org.apache.geronimo.specs/geronimo-jta_1.1_spec/1.1.1 --bundles mvn:commons-pool/commons-pool/1.5.2 --bundles file:<Path to Fuse-Datasource-1.0.0.jar> myDSProfile</p>
<br/>11.Start the childContainer using following command if not started already
<br/>	container-start childContainer
<br/>12.Add the new profile to the container using following commands
<br/>	container-add-profile childContainer myDSProfile
<br/>13.Check the container status using container-list command.It should be success once all dependencies have been downloaded.This may take sometime based on your internet speed.
<br/>14.Connect to childContainer using container-connect childContainer command.It will ask for admin username and password.Please use the same as admin user
<br/>15.Once connected to childContainer use following command to check the status of the bundle
<br/>	osgi:list|grep Sample
<br/>	This will display the above two bundles.Check the status should be active and Create.Sample output is below
<br/>	[ 930] [Active     ] [Created     ] [       ] [   60] A Sample Jboss Fuse datasource bundle built using Gradle (1.0.0)
<br/>16.Check the bundle details with following command
<br/> osgi:ls 930
<br/>It will display the datasource details exposed as OSGI service
