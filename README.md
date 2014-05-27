# Accessibility Support Database Services

This is the server side of the Accessibility Support Database offering CRUD REST Services to the front end.
Project page: http://www.w3.org/WAI/ACT/

## REST API

See Javascript implemention of the REST API:
https://github.com/w3c/wai-axsdb-web/blob/master/js/API.js

In addition there is also a developer workbench for allowing developers to experiment with the data and the API functionality. 

AxsdbQL --> HQL like .. explain


### TestingSession

Provides functionality for managing a user session like login, logout etc. The session is saved client side by combining sessionStorage and cookies but also server side. 

#### TestingSession#save

Add a session to the server side session pool. If there is no session id an id is being generated

+ *URL*: testingsession/commit 
+ *Method*: POST
+ *Request Data Type*: TestingSession
+ *Response HTTP Status*: OK=200 / NOT_MODIFIED: 304
+ *Response Entity*: TestingSession

#### TestingSession#getSession

Gets a session object by session id.

+ *URL*: testingsession/browse/{sessionid}
+ *Method*: GET
+ *Request Data Type*: Null
+ *Response HTTP Status*: OK=200 / NOT_FOUND: 404
+ *Response Entity*: TestingSession

#### TestingSession#login

Authentication / Authorization of the session

+ *URL*: testingsession/login
+ *Method*: POST
+ *Request Data Type*: LoginData: {userId:"", pass: "", sessionId=""}
+ *Response HTTP Status*: OK=200 / FORBIDDEN: 403
+ *Response Entity*: TestingSession (with userid and roles)

#### TestingSession#logout

Session log out

+ *URL*: testingsession/logout/{sessionId}
+ *Method*: POST
+ *Request Data Type*: Null
+ *Response HTTP Status*: OK=200
+ *Response Entity*: Null
 
#### TestingSession#persist

Given a session, this persists the content (test results, [TODO: search filters]) in the database. 

+ *URL*: testingsession/commit/persist/{sessionId} 
+ *Method*: POST
+ *Request Data Type*: Null
+ *Response HTTP Status*: OK=200 / NOT_MODIFIED: 304
+ *Response Entity*: TestingSession

### Profile

Provides functionality for managing Testing Profiles

#### Profile#insertTestingProfile

Inserts in database a testing profile for the specific user

+ *URL*: profile/{userId}/{sessionId}
+ *Method*: POST
+ *Request Data Type*: UserTestingProfile 
+ *Response HTTP Status*:  CREATED=201 / NOT_MODIFIED=304 / UNAUTHORIZED: 401
+ *Response Entity*: profileId

#### Profile#updateTestingProfile

Updates in database a testing profile for the specific user

+ *URL*: profile/put/{sessionId}
+ *Method*: POST
+ *Request Data Type*: UserTestingProfile 
+ *Response HTTP Status*:  CREATED=201 / NOT_MODIFIED=304 / UNAUTHORIZED: 401
+ *Response Entity*: profileId

#### Profile#deleteTestingProfile

Deletes in database a testing profile for the specific user

+ *URL*: profile/{pid}/{sessionId}
+ *Method*: DELETE
+ *Request Data Type*: Null 
+ *Response HTTP Status*:   CREATED=200 / NOT_FOUND=404/ NOT_MODIFIED=304 / UNAUTHORIZED= 401
+ *Response Entity*: profileId
 

#### Profile#getProfilesByUserId

Get Profiles By UserId

+ *URL*: profile/{userId}/{sessionId}
+ *Method*: POST
+ *Request Data Type*: 
+ *Response HTTP Status*:  OK=200 / UNAUTHORIZED: 401+ 
+ *Response Entity* :List of UserTestingProfile 

#### Profile#getAssistiveTechnologies

Retrieve unique AssistiveTechnologies in existing testing profiles

+ *URL*: browse/ATs/{term}
+ *Method*: GET
+ *Request Data Type*:  
+ *Response HTTP Status*:  OK=200
+ *Response Entity* : List of AssistiveTechnology 

#### Profile#getPlatforms

Retrieve unique Platforms (OSs) in existing testing profiles

+ *URL*: browse/platforms/{term}
+ *Method*: GET
+ *Request Data Type*:  
+ *Response HTTP Status*:  OK=200
+ *Response Entity* : List of Platform 

#### Profile#getUserAgents

Retrieve unique User Agents (browsers) in existing testing profiles

+ *URL*: browse/UAs/{term}
+ *Method*: GET
+ *Request Data Type*:  
+ *Response HTTP Status*:  OK=200
+ *Response Entity* : List of UserAgent 

#### Profile#getPlugins

Retrieve unique Plugins in existing testing profiles

+ *URL*: browse/Plugins/{term}
+ *Method*: GET
+ *Request Data Type*:  
+ *Response HTTP Status*:  OK=200
+ *Response Entity* : List of Plugin 

#### Profile#getProfileById

Retrieve testing profile by id

+ *URL*:"browse/{profileId}
+ *Method*: GET
+ *Request Data Type*:  
+ *Response HTTP Status*:  OK=200
+ *Response Entity* : TestingProfile or Null



### Test

Provides functionality for managing the test cases like saving new, editing etc.

### Requirement

Provides functionality for getting information about testing requirements including WCAG Principles Guidelines, Success Criteria, HTML techniques, CSS techniques etc.


### Testresult 

Provides functionality for managing the Test Results

### Query

Provides a way of read only querying the database from the client using HQL

### Rating

Provides functionality for managing any rating (test case or test result)

### Data Types

```javascript
  TestingSession : {
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



## TODO

+ create test files folder
+ create log folder
+ config files
