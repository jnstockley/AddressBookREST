# AddressBookREST

## Version 3.2
This update includes all the changes during the BETA as well as updated dependencies. 

## Version 3.0.1
This update includes updated dependencies and some minor bug fixes when getting a singular person! Please update.

## Version 3.0
I have just released Version 3.0! This version took longer to come then expexted. The main reason this took a while was becuase I had to convert the application to a springboot application rather then just maven. This version has added support for swagger documentation. To learn how to access read below!

## Version 3.0b1
I have released a pre-relase version of version 3.0 under the releases tab. This version has improvements and feature changes. To learn more visit the released tab!

## Version 2.6
Version 2.6 has been released! This update supports the new backend and AES encyrption and decryption! The next update I plan on optimizing the code since update has some unsuded code and was rushed. The REST API is still functional but needs improvement! Look for a version 2.7!

## Version 2.5
As some of you may know I have recently started to split parts of the program apart from eachother. I have recently released the new and improved backend of the program at https://github.com/jnstockley/AddressBook. I am currently startting to rewirte the CLI with the same improvements and also to include the new and improved backend. These same improvemnts will come to the REST interface shortly after the CLI. Stay tuned!

Verison 2.0 has been released!

This is the biggest update since I have release the first address book!

## Key Features
 - Parity with CLI
 - Switched to maven project
 - Allows to easily change server, database, username, and password
 - Stability and bug fixes
 
 ## How to Set Up your own server!
  If you want to use your own server then this program has that ability!
  To start you will need to setup a MySQL server and a database.
  Then create 3 tables one for address, occupation and person!
  I will release the scripts that I use to setup my server once I finish creating them!
  If you want this service to connect to your database on boot up go to IP:8080/AddressBookREST/connect/{server}/{database}/{username}/{password}
  *Password is not saved to a file only to connection within service
  **For now you will have to go to that URL everytime the service is restarted
  
 ## How to use my server!
  There are 2 ways to use this software on my server
    1. Use the war file on your own tomcat install and by default it will connect to my server
    2. Go to http://jackstockleyiowa.ddns.net/AddressBookUI This uses the RESTful service in a nice web UI
  
 ## How to access swagger doc!
  In order to access the swagger doc you have to first deploy the rest interface. Once deployed and running in a web browser go to http://[server ip]:8080/addressbookrest/swagger-ui.html
  
 ## Found a bug!
  Please report the bug under the issues tab with the bug label or tweet me on twitter @jackstockley_
 
 ## Have a question?
  You can leave questions under the issues tab with the question lable or tweet me on twitter @jackstockley_
  
 ## Commonly Asked Questions?
  - How do I run the .war file?
    - You need to have Java 8 installed and a tomcat 9 install. After thats dont place the .war file into the webapps folder in tomcat and start tomcat!
 
 - What operating systems are supported?
   - Pretty much any operating system that supports java version 8 and tomcat 9. That includes Mac, Windows, Linux and possibly Andriod!
   
- When will the AngularJS and CLI versions of this be updated?
  - All version are upadated and running! You can check them out on my profile home page!
  
- Will you continue to update this software with new features and bug fixes?
  - Yes of course, I do still have some features I plan on adding at some point. Right now I am planning on updating the backend on both the REST interface and CLI and converting the CLI into a maven project
  
- Do you have documention on the code?
  - Yes all the code I have written is public with comments as well as a java doc for the whole program. To view the javadoc you can go to https://jnstockley.github.io/AddressBookREST/
