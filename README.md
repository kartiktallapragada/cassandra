Cassandra + DataStax+ Java Console Application
=========

Project was created as a quick introduction to some of the basic cassandra functionality. This project will be expanded as we explore more functionality.

For this project, i have downloaded a single node DataStax Sandbox VM . http://www.datastax.com/download#dl-sandbox

Eclipse LUNA was used to create the console application, the pom.xml will contain details about the datastax java driver.

The link below must be used first to create the datamodel. The application is a simple facebook type application that stores users profile info, their posts and their relationships with friends. 
https://github.com/kartiktallapragada/cassandra/blob/master/simple-client/facebooklitecqlqueries/Facebooklite%20CQL%20Commands

The console application will assume that based on the users login, their loginID is gathered and is used to build all the information neccessary
1. Print the login users profile info
2. Print the login users posts
3. Print the login users friend and their posts

