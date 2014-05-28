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
+ *Response HTTP Status*: OK=200 | NOT_MODIFIED: 304
+ *Response Entity*: TestingSession

#### TestingSession#getSession

Gets a session object by session id.

+ *URL*: testingsession/browse/{sessionid}
+ *Method*: GET
+ *Request Data Type*: Null
+ *Response HTTP Status*: OK=200 | NOT_FOUND: 404
+ *Response Entity*: TestingSession

#### TestingSession#login

Authentication / Authorization of the session

+ *URL*: testingsession/login
+ *Method*: POST
+ *Request Data Type*: LoginData: {userId:"", pass: "", sessionId=""}
+ *Response HTTP Status*: OK=200 | FORBIDDEN: 403
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
+ *Response HTTP Status*: OK=200 | NOT_MODIFIED: 304
+ *Response Entity*: TestingSession

### Profile

Provides functionality for managing Testing Profiles

#### Profile#insertUserProfile

Inserts in database a testing profile for the specific user

+ *URL*: profile/{userId}/{sessionId}
+ *Method*: POST
+ *Request Data Type*: UserTestingProfile 
+ *Response HTTP Status*:  CREATED=201 | NOT_MODIFIED=304 | UNAUTHORIZED: 401
+ *Response Entity*: profileId

#### Profile#updateUserProfile

Updates in database a testing profile for the specific user

+ *URL*: profile/put/{sessionId}
+ *Method*: POST
+ *Request Data Type*: UserTestingProfile 
+ *Response HTTP Status*:  CREATED=201 | NOT_MODIFIED=304 | UNAUTHORIZED: 401
+ *Response Entity*: profileId

#### Profile#deleteUserProfile

Deletes in database a testing profile for the specific user

+ *URL*: profile/{pid}/{sessionId}
+ *Method*: DELETE
+ *Request Data Type*: Null 
+ *Response HTTP Status*:   CREATED=200 | NOT_FOUND=404 | NOT_MODIFIED=304 | UNAUTHORIZED= 401
+ *Response Entity*: profileId
 

#### Profile#findByUserId

Get Profiles By UserId

+ *URL*: profile/{userId}/{sessionId}
+ *Method*: POST
+ *Request Data Type*: 
+ *Response HTTP Status*:  OK=200 | UNAUTHORIZED=401 
+ *Response Entity* :List of UserTestingProfile 

#### Profile#getAssistiveTechnologies

Retrieve unique AssistiveTechnologies in existing testing profiles by term

+ *URL*: profile/browse/ATs/{term}
+ *Method*: GET
+ *Request Data Type*:  
+ *Response HTTP Status*:  OK=200
+ *Response Entity* : List of AssistiveTechnology 

#### Profile#getPlatforms

Retrieve unique Platforms (OSs) in existing testing profiles by term

+ *URL*: profile/browse/platforms/{term}
+ *Method*: GET
+ *Request Data Type*:  
+ *Response HTTP Status*:  OK=200
+ *Response Entity* : List of Platform 

#### Profile#getUserAgents

Retrieve unique User Agents (browsers) in existing testing profiles by term

+ *URL*: profile/browse/UAs/{term}
+ *Method*: GET
+ *Request Data Type*:  
+ *Response HTTP Status*:  OK=200
+ *Response Entity* : List of UserAgent 

#### Profile#getPlugins

Retrieve unique Plugins in existing testing profiles by term

+ *URL*: profile/browse/Plugins/{term}
+ *Method*: GET
+ *Request Data Type*:  
+ *Response HTTP Status*:  OK=200
+ *Response Entity* : List of Plugin 

#### Profile#findByProfileId

Retrieve testing profile by id

+ *URL*:profile/browse/{profileId}
+ *Method*: GET
+ *Request Data Type*:  
+ *Response HTTP Status*:  OK=200
+ *Response Entity* : TestingProfile | Null


### Test

Provides functionality for managing the tests like saving new, editing etc.

#### Test#findAll

Finds all Tests

+ *URL*:test
+ *Method*: GET
+ *Request Data Type*:  
+ *Response HTTP Status*:  OK=200
+ *Response Entity* :  {entities: [Test]}

#### Test#findById

Finds an existing Test by testUnitId 

+ *URL*:test/{testUnitId}
+ *Method*: GET
+ *Request Data Type*:  
+ *Response HTTP Status*:  OK=200 | NOT_FOUND=404
+ *Response Entity* : Test || Null

#### Test#findByTechnique

Finds Tests by Technique Id

+ *URL*:test/byTechnique/{tid}
+ *Method*: GET
+ *Request Data Type*:  
+ *Response HTTP Status*:  OK=200
+ *Response Entity* :  {entities: [Test]}

#### Test#getTestsTreeData

Get TreeNodeData with Tests based on Filter

+ *URL*:test/tree
+ *Method*: GET
+ *Request Data Type*:  filter
+ *Response HTTP Status*:  OK=200
+ *Response Entity* :  treeNodeData

#### Test#getTestAsXml

Get Test as XML by testUnitId

+ *URL*:test/xml/{id}
+ *Method*: GET
+ *Request Data Type*:  
+ *Response HTTP Status*:  OK=200
+ *Response Entity* :  XML as text

#### Test#updateTestFromXml

Update Test from XML 

+ *URL*:test/xml/{sessionId}
+ *Method*: POST
+ *Request Data Type*: Test 
+ *Response HTTP Status*:  OK=200 |  NOT_MODIFIED=304 | NOT_ACCEPTABLE=406 | UNAUTHORIZED=401
+ *Response Entity* :  Test

#### Test#deleteTest

Deletes Test by id

+ *URL*:test/{sessionId}/{id}
+ *Method*: DELETE
+ *Request Data Type*:  
+ *Response HTTP Status*:  OK=200 |  NOT_MODIFIED=304 | NOT_ACCEPTABLE=406 | UNAUTHORIZED=401
+ *Response Entity* :  
 
#### Test#updateTest

Updates a test

+ *URL*:test/status/{sessionId}
+ *Method*: POST
+ *Request Data Type*: Test
+ *Response HTTP Status*:  OK=200 |  NOT_MODIFIED=304 | UNAUTHORIZED=401
+ *Response Entity* : Test

#### Test#deleteResourceFile

Deletes a resource file of a Test

+ *URL*:test/resource/{sessionId}/{testUnitId}/{fileId}
+ *Method*: DELETE
+ *Request Data Type*: 
+ *Response HTTP Status*:  OK=200 |  NOT_MODIFIED=304 | UNAUTHORIZED=401
+ *Response Entity* : 

#### Test#testPersist

Saves a test posted by a form. See config file (FORM_TESTUNIT_FORMFIELD_TESTUNITDESCRIPTION, FORM_TESTUNIT_FORMFIELD_CODE,FORM_TESTUNIT_FORMFIELD_TESTFILE). See more in [config section](#Config) 

+ *URL*:test/commit/{sessionId}
+ *Method*: POST
+ *Request Data Type*: HttpServletRequest (Form Post [TODO: explain])
+ *Response HTTP Status*:   CREATED=201 |  NOT_MODIFIED=304 | UNAUTHORIZED=401
+ *Response Entity* : testUnitId



### WCAG2

Provides functionality for getting information about testing requirements including WCAG Principles Guidelines, Success Criteria, HTML techniques, CSS techniques etc.


### Testresult 

Provides functionality for managing the Test Results

### Query

Provides a way of read only querying the database from the client using HQL

### Rating

Provides functionality for managing any rating (test case or test result)

### Data Types

```javascript
  testingSession : {
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
    
   treeNodeData : {
        label:"ROOT",
        children:[TreeData],
        value:null,
        selected:false,
        disabled:false,
        subselector:true,
        selectable:true,
        collapsed:true,
        type:"type of data",
        description:null,
        noOfChildren:0
   }

 test: {
    "testUnitId":"ARIA1_0000001",
    "title":"ertert",
    "description":"ertert",
    "status":"unconfirmed",
    "creator":"evlachog",
    "version":"0.1",
    "date":"2014-04-23 11:45:40",
    "technique":{
       "id":1,
       "nameId":"ARIA1",
       "specRef":"https://raw.github.com/w3c/wcag/Working-Branch-for-Fall-2014/wcag20/sources/techniques/aria/ARIA1.xml",
       "title":"Using the aria-describedby property to provide a descriptive label for user interface controls",
       "webTechnology":"WCAG20-ARIA-TECHS",
       "status":1,
       "lastModified":"2014-03-28T17:18:24.000+0000",
       "sha":"90d1c1c3fdf2a793a0c93cc5408c53b913ddd1a7"
    },
    "subject":{
       "testFile":{
          "id":2,
          "mediatype":null,
          "src":"ARIA1_0000001.html"
       },
       "resources":[]
    },
    "testProcedure":{
       "yesNoQuestion":"ertret",
       "expectedResult":true,
       "steps":[
          {
             "step":"ertert",
             "orderId":0,
             "id":1
          }
       ]
    },
    "comment":"ertre",
    "id":1,
    "language":"en"
 }


``` 

## Key Technologies
+ JAVA
+ JPA
+ Hibernate
+ Mysql
+ RestEasy


## Config

TODO

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
