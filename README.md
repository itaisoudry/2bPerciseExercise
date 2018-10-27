# 2bPrecise - Development exercise

##### Technoligical specs
  - ##### Server
    - Spring boot
    - Hibernate (H2 In-Memory database) - SQL Server (data is loaded from data.sql file in the resources folder)
 - ##### Client
    - HTML
    - CSS
    - Javascript
    - Bootstrap
    - JQuery

    I used SQL database because of the relational structure of the application. using SQL will allow adding new components and functionallity much easier (such as manipulating data, adding new relations between the objects etc...)
    
    In order to run the app, download exercise.jar and run it (java -jar exercise.jar), open the browser and go to http://localhost:8080/employees.html (Make sure you have Java 8 or higher installed)
    
    __Note__: The client files are located inside the server, main/resources/static

### API Documentation
   -  Click [here](https://docs.google.com/document/d/1pc3HKtUV99ZVPwkH3LqFIQPDljX4AEjgM4OEq2s_KQU/edit?usp=sharing)
   
### Future use cases
1. __Create,Update and Delete objects__ - those methods are already implemented in the server since they are a must in my opinion (update and delete are not being used, but create is used for Reports and Tasks), so all we need to do, is create the proper UI for handling those methods. (By adding a Manager for example, we will need to choose Employees that he manages).
2. __Editing Reports and Tasks__ - Editing text and dates, and possibly add additional fields such as Labels or Report to manager when task is completed
3. __Login__ - Enable user / admin login (using username-password / sso): this will allow us to set permissions / data visibility for specific users in the organization and also for security purposes - OAuth ( we can use Spring security for the token handling and validation)
4. __Dividing the Organization into Groups or Teams__ - We can do this easily by adding another table to our DB that will represent the relations between users / managers and groups. Also, by adding another controller to handle those requests from the admin.
5. __Adding profile picture in the Employee profile__ - right now, there is only one image and the data is static, but we can store different static profile pictures and let the user choose one of them, or upload his own ( this will require storing the images in the db, and adding a profile ID to every employee)
6. __Different types of Managers__ - If we want to create different types of Managers, we can extend our model in order to do so. We will probably need to create different objects for every type of Manager ( if we will need custom fields for every type of manager), but this will be done easily, because all Managers will extend from the Employee class and will be extended according to the additional data.
    


### Thanks,
####    Itai
