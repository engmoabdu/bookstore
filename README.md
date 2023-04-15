# Bookstore
This is a simple online bookstore that allows users to search and add/remove books to their shopping cart, and perform checkout operations which will return the total payable amount after applying discounts. The application is secured, ensuring that users can only see their own shopping cart.

## Technology Stack
This application has been developed using the following technologies:
- Java 17
- Docker
- Docker Compose
- Spring Boot 3
- JOCO
- JUnit 5
- Swagger 3
- Spring Security

## GIVEN
### Book Attributes
Each book has the following attributes:
- Name
- Description
- Author 
- Type/Classification 
- Price 
- ISBN

### Discounts
The promotion/discounts are variant according to book type/classification. For example, fiction books may have a 10% discount, while comic books have 0% discount.

## Getting Started
- To get started, clone the repository and navigate to the project root directory:
```bash
        git clone https://github.com/engMoAbdu/bookstore.git
        cd bookstore
```
**Note:** _To run the application, we recommend using Docker Compose. Docker Compose is a tool for defining and running multi-container Docker applications. It allows you to run the entire application stack with a single command, simplifying the deployment process._

- To start the application using Docker Compose, navigate to the project directory and run the following command:
```bash
        docker-compose up --build --scale app= n
```
This command will build the Docker images for each service and start the containers. The --scale flag specifies that we want to run n 0f instances of the app service.

We use Nginx as a load balancer to distribute incoming requests among the app instances. The app instances are connected to a MySQL database.

- To stop the application, press CTRL+C in the terminal window where you started Docker Compose.
```bash
        docker-compose down -v
```
This will stop and remove the containers, networks, and volumes created by Docker Compose.
- Once you run the compose file, you can test the code by accessing the API documentation.

## API Documentation
The application includes Swagger 3 OpenAPI for API documentation. You can access the documentation in your web browser by navigating to and do all tests: ![SwaggerURL]http://localhost:8090/abdu/swagger-ui/index.html#/

## API Endpoints
The following endpoints are available in this application:

### Auth Controller
This controller is responsible for user authentication and user management.

#### Admin User Registration

```bash
          POST /v1/auth/register-admin
```
#### User Registration [Customer]
```bash
          POST /v1/auth/register-user
```

#### Get All Users [Admin Only]
```bash
          GET /v1/auth/users
```

#### Delete User [Admin Only]
```bash
          DELETE /v1/auth/users/{userId}
```

### Book Controller

#### Add new Book [Admin Only]
```bash
          POST /v1/books
```

#### Get Book By ID [Login User]
```bash
          GET /v1/books
```
#### Add new List of Books [Admin Only]
```bash
          POST /v1/books/list
```

#### Get All Books using filtration [Login User]
```bash
          GET /v1/books/search
```
**Note:** To test this API from Swagger 3 you should aware that the text field sort click on [Add string item] button then add on this field [name of field] then comma [type of sorting] for example [id,desc]

### CartItem Controller [Login User]

#### Add book to the cart [Login User]
```bash
          POST /v1/cart-item
```

#### remove from cart [Login User]
```bash
          DELETE /v1/cart-item/{bookId}
```

#### update quantity of book inside the cart [Login User]
```bash
          PUT /v1/cart-item/{cartItemId}/{bookId}
```

### Checkout Controller [Login User]

#### checkout API [Login User]
```bash
          POST /v1/checkout
```

### ShoppingCart Controller [Login User]

#### Create a new shopping cart [Login User]
```bash
          GET /v1/carts
```

#### get shopping cart for the current user API [Login User]
```bash
          GET /v1/carts
```

