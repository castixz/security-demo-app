# Demo Security application

## Technology used:
- Java (21)
- SpringBoot (3.3.3)
- Gradle
- GIT
- Docker (optional)
- Bruno (optional)

## About:

There is only one endpoint in the application `/api/v1/admin` which is secured by basic auth. There 3 available users for testing: 'admin' (with non-admin role), 'user1' (with admin role) and 'user2' (with non-admin role). The password to all of them is 'dummypassword'. 

## Setup:
You can run the application within a docker container by running `docker-compose up` in the root of the project. There is also folder "test-collection" that contains testing requests for Bruno HTTP client to ease testing.