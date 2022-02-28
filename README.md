# selenium-practice
To run the tests, open the CMD and type: 
```
mvn clean test
```

To run the Postman test via Newman, type:

```
newman run .\src\test\resources\First.postman_collection.json  
```

To save the Newman report, type:

```
newman run .\src\test\resources\First.postman_collection.json -r htmlextra --reporter-htmlextra-expo
rt .\src\test\resources
```
It'll be saved in the resources folder