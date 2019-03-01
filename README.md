# AddressBookREST
Extremely Overdue Update!!!!! I lot has happened since I last posted an update! I had my MySQL server up for a bit but currently, it is down due to a corrupted kernel on my server! I am trying right now to recover the data on the server to save reinstall time! Wish me luck. I am also going to be working on rewriting this program to add new features to it once I have finished the CLI version! Thanks!

Setup:
 1. Create MySQL server
 2. Install tomcat on the server and add the AddressBookREST.war file to tomcat
 3. Change the IP's, yes both, to your internal and or external IP
 4. Change user and pass to your MySQL user username and password
 5. In encryption create, two different random strings have to be 8 characters longs

How to use:

Get Methods:
   1. Open a browser ie. chrome
   2. Go to the URL of your server:8200/AddressBookREST/"table"
   3. The program will return JSON to you
   4. For GET by id add the id to the end of the URL

All Methods:
   1. Install a program called Postman
   2. Type in server:8200/AddressBookREST/"table"
   3. Select method (GET,PUT,DELETE)
   4. For PUT go to body
   5. Type in JSON manually
   6. For GET by id or delete add the id number at the end of the URL

Any other questions tweet me on twitter @jackstockley_ and Ill add it here
