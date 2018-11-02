# AddressBookREST
Setup:
1. Create mySQL server
2. Install tomcat on server and add the AddressBookREST.war file to tomcat
3. Change the ip`s, yes both, to you internal and or external ip
4. Change user and pass to your mySQL user username and password
5. In encryption create two diffrent random strings have to be 8 characters longs

How to use:
1. Get Methods
  a. Open a browser ie. chrome
  b. Go to the URL of you server:8200/AddressBookREST/"table"
  c. The program will return JSON to you
  d. For get by id add the id to the end of the url
2. All Methods
  a. Install a program called postman
  b. Type in server:8200/AddressBookREST/"table"
  c. Select method (GET,PUT,DELETE)
  d. For put go to body
  e. Type in JSON manually
  f. For get by id or delete add the id number at the end of the url
Any other questions tweet me on twitter @jackstockley_ and Ill add it her
