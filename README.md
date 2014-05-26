# Accessibility Support Database Services

This is the server side of the Accessibility Support Database offering CRUD REST Services to the front end.
Project page: http://www.w3.org/WAI/ACT/

## Key Technologies
+ JAVA
+ JPA
+ Hibernate
+ Mysql
+ RestEasy


## Build

### Prerequisites 
+ JKD 1.6+ (1.7+ recommended)
+ apache maven 2+


### Process 

+ mvn -Dmaven.test.skip=true install

## Deploy

### Prerequisites 
+ apache ant

### Process 

+ cp build.default.properties ../build.properties
+ edit build.properties
+ ant upload
+ ssh serverHost 
+ cd install.dir
+ ant deploy-all
+ java container restart
+ Apache proxy config if required 
+ Apache restart 

## REST API

See Javascript implemention of the REST API:
https://github.com/w3c/wai-axsdb-web/blob/master/js/API.js

In addition there is also a developer workbench for allowing developers to experiment with the data and the API functionality. 

AxsdbQL --> HQL like .. explain

### Data Types

```javascript
  session : {
      sessionName: null,
      sessionId: null, 
      testProfileId: "-1",
      testUnitIdList: [],
      testResultList: [],
      ratings: [],
      currentTestUnitId: "-1",
      userTestingProfiles: [],
      userId: null,
      userRoles: [],
      lastTestUnit: null,       
      pCounter: -10
  }
  
  filter : {
        page : "",
        userName : null,
        lastModified: "",
        criteriosLevel : "AAA",
        criterios : [],
        technologies : [],
        ats : [],
        uas : [],
        oss : []
    };
    
``` 

### TestingSession

Provides functionality for managing a user session like login, logout etc. The session is saved client side by combining sessionStorage and cookies but also server side. 

#### TestingSession#save

+ *Description*: Saves user session both locally and remotely 
+ *URL*: testingsession/commit 
+ *Method*: POST
+ *Request Data Type*: session
+ *Response*: HTTP.Status



### Testunit: 

Provides functionality for managing the test cases like saving new, editing etc.

### Requirement

Provides functionality for getting information about testing requirements including WCAG Principles Guidelines, Success Criteria, HTML techniques, CSS techniques etc.

### Profile

Provides functionality for managing the Testing Profiles

### Testresult 

Provides functionality for managing the Test Results

### Query

Provides a way of read only querying the database from the client using HQL

### Rating

Provides functionality for managing any rating (test case or test result)


## TODO

+ create test files folder
+ create log folder
+ config files
