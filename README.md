To build, execute:

`mvn clean install`

To run, execute:
`java -jar target/socketserver.jar`


It runs on port 9111 by default, which can be configured at runtime as a command line argument
example: `java -jar target/socketserver.jar <port number>`


Requests

1. Invalid Create job request:

`curl -X PUT http://localhost:9111/job -H 'content-type: application/json' -d '{ "s":"d" }'`

2. Valid Create Job Request
`curl -X PUT http://localhost:9111/job -H 'content-type: application/json' -d '{ "name":"dummy" }'`


3. Invalid Get 
`curl -X GET http://localhost:9111/job/1 -H 'content-type: application/json'`

4. Valid Get
`curl -X GET http://localhost:9111/job/0ad183bb-b1ad-48d2-a4ab-489ebc22acf4 -H 'content-type: application/json'`

5. Valid Update
 `curl -X POST http://localhost:9111/job/385c4964-36a9-4542-ac6e-b3e052a3f2d2 -H 'content-type: application/json' -d '{ "name": "dummy1" }'`

6. Invalid Update:
`curl -X POST http://localhost:9111/job/1 -H 'content-type: application/json' -d '{ "name": "dummy1" }' `

7. Invalid Update 2:
 `curl -X POST http://localhost:9111/job/385c4964-36a9-4542-ac6e-b3e052a3f2d2 -H 'content-type: application/json' -d '{ "x": "y" }'`
 
8. Delete valid
`curl -X DELETE http://localhost:9111/job/385c4964-36a9-4542-ac6e-b3e052a3f2d2 -H 'content-type: application/json'`

9.Delete Invalid:
`curl -X DELETE http://localhost:9111/job/1 -H 'content-type: application/json'`
