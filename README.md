# AddressBookREST

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
