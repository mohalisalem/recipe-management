# recipe-management
this project is buid as a technical assignment with two models: Recipe and Ingredient

## Database configuration details
this application works with PostgreSQL, (tested with postgres14).
for database details : 
* database name: recipe
* database port: 5432
* username: postgres
* password: postgres


## application tests
To test this application, we can either run unit tests, or test REST API
- to run unit test, we can using "mvn test",
note: not all unit test have been developed,  I tried to add a single unit test method for each method in the service layer,
and for one or two methods I added a test method that checks null, and another that checks if it throws an exception.

- to test the rest API, we need to access to this [URL](http://localhost:8080/swagger-ui/#)
I included swagger in the project.
I also, created two recipes I copied them from this [site](https://realfood.tesco.com/recipes)
to test the main features.

