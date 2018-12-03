# AddressBookREST
ATTENTION!!!!! 
Today around 2:30 CDT the mySQL server will be taken offline for several hours! I am going to be consolidating my servers. I am hoping to have the mySQL server back up around 8:00 PM CDT at the latest! All data on the server will be deleted!

Setup:
1. Create mySQL server
2. Install tomcat on server and add the AddressBookREST.war file to tomcat
3. Change the ip`s, yes both, to you internal and or external ip
4. Change user and pass to your mySQL user username and password
5. In encryption create two diffrent random strings have to be 8 characters longs

How to use:

Get Methods:
  1. Open a browser ie. chrome
  2. Go to the URL of you server:8200/AddressBookREST/"table"
  3. The program will return JSON to you
  4. For get by id add the id to the end of the url

All Methods:
  1. Install a program called postman
  2. Type in server:8200/AddressBookREST/"table"
  3. Select method (GET,PUT,DELETE)
  4. For put go to body
  5. Type in JSON manually
  6. For get by id or delete add the id number at the end of the url

Any other questions tweet me on twitter @jackstockley_ and Ill add it here
