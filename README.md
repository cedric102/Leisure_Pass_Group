# Assignment for Leisure Pass Group.

## Instruction :
 - Extract a list of products from the Persistence layer and list it in the view.
 - Implement a sorting algorithm by Category and then by Name.
 - Implement a sorting algorithm to sort individually by Id , Name , and Category
 - The data is persistent, so it should be retrievable even after the application reboots.

## Architecture :
 - The application follows the following schematic :
   1) View -> 2) Controller -> 3) Service -> 4) Repository -> 5) DAO -> 6) Database.

 - The data flows up and down between the View and the Database. The description of each element is presented below :
   1) View is the Front-End using Thymeleaf
   2) Controller using Spring Boot. It calls the Service Layer for the Business logic. ( The Business Logic is an abstraction to the controller )
   3) Service Layer : Focuses on performing the Models calls and preparing the data for the controller and updating the database.
   4) Repository: Used to perform the Database calls through the DAO using the JPA.
   5) DAO: It is the interface for the Persistence layer. Through this interface the status of the application can be saved.
   6) Database : The table is called 'entity', but it represents the list of products.

## Endpoints :
 - The application can be managed with the buttons at the top-left of the GUI ( Pop , View , Clear ). Each call their respective endpoint
 - On the next line , there are other links dedicated to sorting ( Sort Mix , id , name , Category )
 - localhost:8017/pop : it generates the desired data from the xlsx file and saves it in the persistence layer. The result is returned to the View as a table.
 - localhost:8017/sort : it sorts the database by name and then by category. The result is returned to the View as a table.
 - localhost:8017/view : it extracts the data from the persistence layer and returns the result to the View as a table.
 - localhost:8017/clear : it clears the data from the persistence layer and returns the result to the View as an empty table.
 - localhost:8017/sort_single/{sort} : it sorts the data from the persistence layer individually by Id , Name , and Category and returns the result to the View as an empty table.

 ## Note :
 - The Category_Id is mapped to its appropriate String.