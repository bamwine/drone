## Drones

[[_TOC_]]

---

:scroll: **START**


### Introduction

There is a major new technology that is destined to be a disruptive force in the field of transportation: **the drone**. Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.

Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access.

---

### Task description

We have a fleet of **10 drones**. A drone is capable of carrying devices, other than cameras, and capable of delivering small loads. For our use case **the load is medications**.

A **Drone** has:
- serial number (100 characters max);
- model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
- weight limit (500gr max);
- battery capacity (percentage);
- state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).

Each **Medication** has: 
- name (allowed only letters, numbers, ‘-‘, ‘_’);
- weight;
- code (allowed only upper case letters, underscore and numbers);
- image (picture of the medication case).

Develop a service via REST API that allows clients to communicate with the drones (i.e. **dispatch controller**). The specific communicaiton with the drone is outside the scope of this task. 

The service should allow:
- registering a drone;
- loading a drone with medication items;
- checking loaded medication items for a given drone; 
- checking available drones for loading;
- check drone battery level for a given drone;

> Feel free to make assumptions for the design approach. 

---

### Requirements

While implementing your solution **please take care of the following requirements**: 

#### Functional requirements

- There is no need for UI;
- Prevent the drone from being loaded with more weight that it can carry;
- Prevent the drone from being in LOADING state if the battery level is **below 25%**;
- Introduce a periodic task to check drones battery levels and create history/audit event log for this.

---

#### Non-functional requirements

- Input/output data must be in JSON format;
- Your project must be buildable and runnable;
- Your project must have a README file with build/run/test instructions (use DB that can be run locally, e.g. in-memory, via container);
- Required data must be preloaded in the database.
- JUnit tests are optional but advisable (if you have time);
- Advice: Show us how you work through your commit history.

---


---
### How to Run the Project

#### Requirements

- JDK 11
- Java IDE (IntelliJ IDEA 2022.1)
- hsqldb Database was used in memory (Check the persitance.xml file)
- Firecamp or Postman(For testing ) 

### Steps by step for building and runing the project locally

- Clone the from the link git clone https://oauth:glpat-KxCBHW2ez2iw-xhJ3D2K@gitlab.com/musala-coding-tasks-solutions/moses-kipyegon.git
- Open the cloned project in IntelliJ IDEA
- Go to Build menu-> Build Project  to update all the maven dependencies

### Testing the API

Open Firecamp

**NB :** ContentType is application/json (Input/output data must be in JSON format)



- registering Medication **Registering  Medicines or Drugs**
**URL -->** http://localhost:8080/nnnnnn-1.0-SNAPSHOT/rest/medication
**The payload Input -->**
{
"code" :  "MAL547609",
"name" : "MALARIA TABS",
"weight" : 100.0,
"image" : "small.png"
}
**The response should be -->** 
[
    {
        "Result": "Success",
        "Message": "Medication Successfully Registered",
        "Code Number": "MAL547609",
        "name": "MALARIA TABS",
        "Weight": 100
    }
]

- checking available Medicines **checking available a Medicines or Drugs**
**URL -->** http://localhost:8080/nnnnnn-1.0-SNAPSHOT/rest/medication
**The payload Input -->** null none
**The response should be -->**
[
    {
        "code": "MAL547609",
        "id": 1,
        "image": "small.png",
        "name": "MALARIA TABS",
        "weight": 100
    },
    {
        "code": "HEA547609",
        "id": 3,
        "image": "big.png",
        "name": "HEADACHE",
        "weight": 10
    }
] 


- registering a drone**Registering a drone**
**URL -->** http://localhost:8080/nnnnnn-1.0-SNAPSHOT/rest/drone
**The payload Input -->**
{
"serial" : "1234",
"weightLimit" : 300.0,
"battery" : 0.98,
"state" :  "IDLE",
"model" : "Middleweight"
}  
**The response should be -->** 
[
    {
        "Result": "Success",
        "Message": "Drone Successfully Registered",
        "Serial Number": "1234",
        "WeightLimit": 300,
        "state": "IDLE"
    }
]

- checking available drones for loading **checking available drones for loading**
**URL -->** http://localhost:8080/nnnnnn-1.0-SNAPSHOT/rest/drone/available
**The payload Input -->**  
**The response should be -->**
[
    {
        "battery": 20,
        "id": 1,
        "model": "Middleweight",
        "serial": "123",
        "state": "IDLE",
        "weightLimit": 300
    },
    {
        "battery": 0.98,
        "id": 2,
        "model": "Middleweight",
        "serial": "1234",
        "state": "IDLE",
        "weightLimit": 300
    }
] 

- loading a drone with medication items **loading a drone with medication**
**URL -->**http://localhost:8080/nnnnnn-1.0-SNAPSHOT/rest/medication/loadmedicine
**The payload Input -->**
{
"code" :  "HEA547609",
"serial" : "123",
"source" : "kampala",
"destination":"jinja"
}
  
**The response should be -->**
[
    {
        "Result": "Success",
        "Message": "Drone Loaded Successfully !",
        "Drone serial": "123",
        "Medicine code": "HEA547609",
        "Medicine": "HEADACHE ",
        "source": "kampala",
        "destination": "jinja"
    }
] 
- checking loaded medication items for a given drone  **checking loaded medication for a given drone**
**URL -->**http://localhost:8080/nnnnnn-1.0-SNAPSHOT/rest/medication/dronemedicine/123
**The payload Input -->**  serial number --> 123
**The response should be -->**
[
    {
        "Result": "Success",
        "Message": "Drone Medicine Details !",
        "medicine name": "HEADACHE ",
        "drone serial ": "123",
        "Medicine code": "HEA547609"
    },
    {
        "Result": "Success",
        "Message": "Drone Medicine Details !",
        "medicine name": "HEADACHE ",
        "drone serial ": "123",
        "Medicine code": "HEA547609"
    }
] 

- check drone battery level for a given drone  **check drone battery level**
**URL -->**http://localhost:8080/nnnnnn-1.0-SNAPSHOT/rest/drone/batterylevel/1234
**The payload Input -->**  serial Number -->1234
**The response should be -->**
[
    {
        "Result": "Success",
        "Message": "Drone Battery Results",
        "Serial Number": "1234",
        "Batterylevel": 0.98
    }
] 



**My commit history** 
Since my git was already configured for Github so i uploaded the project to Github first then imported it in GitLab
so i created an empty repository on github
opened my CMD to start run Git commands
git clone https://github.com/bamwine/drone.git 
then cloned it  to my local computer
i ran these commands

git add .
git commit -m "intial changes"
git push -u origin main

authorise from browser

After uploading to github
opened my GitLab account created a new project by importing from Github









