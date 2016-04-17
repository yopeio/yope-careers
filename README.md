# Careers Networking API
Decentralised, blockchain-based careers networking API

## Main features:
* Persistence into the blockchain of candidates profiles, education, skills and job experiences.
* Fast search by non-sensitive data, powered by an embedded centralised search engine.
* Retrieval of full candidate’s information, given its address, from the contract stored into the blockchain.
* Pay-for-a-service and advertising-free search. HR and recruiters pay for reading candidates information, thanks to a bitcoin micropayment internal mechanism.
* Role-based data visualisation. Candidates can choose which information are visible to specific network users roles.
* Access-token-based authorization layer.

### UML Diagrams

- [Sequence Diagrams](https://github.com/yopeio/yope-careers/wiki/Sequence%20Diagrams)
- [Class Diagrams](https://github.com/yopeio/yope-careers/wiki/Class%20Diagrams)

### Public API
[User, Title, Verification API reference](https://github.com/yopeio/yope-careers/wiki/Public%20API)


### Prerequisities
- java 8
- maven 3.x
- a local/remote ethereum blockchain based on  https://github.com/ethereum/wiki/wiki/JSON-RPC reference.

### Dependencies
- [Yope Ethereum API](https://github.com/yopeio/ethereum-api)

### Configuration
in `yope-careers-rest\application.yml`

* Address of the  JSON RPC Ethereum Java client: `org.ethereum.address: http:/...`
* Ethereum account address: `org.ethereum.accountAddress: ...`

### How to build and run it
- Build locally [Yope Ethereum API](https://github.com/yopeio/ethereum-api)
- `cd yope-careers-rest`
- `mvn spring-boot:run`

### How to test it

#### Create a new Candidate

##### Request
```
curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{
    "type": "CANDIDATE",
    "username": "{USER}",
    "password": "issam",
    "dateOfBirth": 34560000000,
    "profile": {
        "title": "Herr",
        "firstName": "Massimiliano",
        "lastName": "Gerardi",
        "role": "Engineer",
        "description": "A Software Engineer with more that 10 years experience",
        "tags": ["engineer", "software", "UML", "java"]
    }
}' "http://localhost:8081/user"
```
##### Response
```
{
   "success":true,
   "status":201,
   "body":{
      "type":"CANDIDATE",
      "username":"{USER}",
      "profile":{
         "title":"Herr",
         "firstName":"Massimiliano",
         "lastName":"Gerardi",
         "tags":[
            "engineer",
            "software",
            "UML",
            "java"
         ],
         "description":"A Software Engineer with more that 10 years experience"
      },
      "hash":"0x068bb9d3bdeb859a6cda5318c02cefd9d14550cf",
      "dateOfBirth":34560000000
   }
}
```
#### Get all Users
##### Request
```
curl -X GET -H "Cache-Control: no-cache" "http://localhost:8081/eUsers"
```
##### Response
```
{
  "_embedded" : {
    "eUsers" : [ {
      "created" : 1460806331232,
      "modified" : 1460806342897,
      "username" : "massi",
      "password" : null,
      "profile" : {
        "title" : "Herr",
        "firstName" : "Massimiliano",
        "lastName" : "Gerardi",
        "tags" : [ "engineer", "software", "UML", "java" ],
        "role" : "Engineer",
        "description" : "A Software Engineer with more that 10 years experience"
      },
      "titles" : null,
      "status" : "ACTIVE",
      "type" : "CANDIDATE",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8081/eUsers/0xa36916a88a87f08af276b94113c8859671e4e60e"
        },
        "eUser" : {
          "href" : "http://localhost:8081/eUsers/0xa36916a88a87f08af276b94113c8859671e4e60e"
        }
      }
    },
    ...
  }
}    
```
#### Search by lastName
##### Request
```
curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{
    "candidate": {
        "type": "CANDIDATE",
        "profile": {
            "lastName": "Davassi"
        }
    },
    "page": 0,
    "size": 10
}' "http://localhost:8081/user/search"
```
##### Response
```
{
  "success": true,
  "status": 201,
  "body": {
    "elements": [
      {
        "created": 1460806395207,
        "modified": 1460806399671,
        "type": "CANDIDATE",
        "username": "gian",
        "profile": {
          "title": "Herr",
          "firstName": "Gianluigi",
          "lastName": "Davassi",
          "tags": [
            "developer",
            "leader",
            "java",
            "ethereum",
            "blockchain",
            "javascript"
          ],
          "description": "A lead developer with more that 1 years experience in blockchain and financial applications"
        },
        "status": "ACTIVE",
        "hash": "0x92539df78d5b38c2514c4f1ce81922b7919c7cc5"
      }
    ],
    "total": 1,
    "pages": 1,
    "from": 0,
    "last": true
  }
}
```

#### License
The ethereum-api is released under the MIT permissive free software license.
