# LDAP Proxy server

LDAP Proxy application, which forwards GET request with parameters to LDAP Server and returns the result, of course ;-)

Spring Boot application.

Runs as a standalone server. To start on local machine, compile:

```
mvn clean package
```

...and then run

```
java -jar ldapproxy.jar
```

To get JSON of LDAP informatin go to:

```
http://localhost:8080/ldapproxy/get?username=newton&password=password
```

More information: https://www.forumsys.com/tutorials/integration-how-to/ldap/online-ldap-test-server/

eof.