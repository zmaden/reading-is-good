# ReadingIsGood
ReadingIsGood is an online books retail firm which operates only on the Internet. Developed with springboot and used postgres as database.

#### Firstly, you need to create admin with ```/user/createAdmin``` url and create customer with ```/customer/createCustomer``` url. After that you can access endpoints using Basic Token. All requests should have basic token parameter that contains saved email and password. Otherwise, you can't access the system.


There is 5 controller in application.
* Customer Controller
    * Persist New Customer,
    * Query All Orders Of The Customer
* Book Controller
    * Persist New Book,
    * Update Book's Stock
* Order Controller
    * Persist New Order,
    * Query Order By Id,
    * List Orders By Date Interval
* Statics Controller
    * Monthly Sales Statistics
* User Controller
    * Create Admin

System developed with Spring Security. There is two user type as admin and customer.
Admin can save a book, add book to stock, list orders and monitor monthly statistics. But cannot create order.
Customer can create order and get their own orders detail.

You can see request and response sample in swagger definition (http://localhost:8081/swagger-ui/index.html#/).
Also postman collection is added to repository.

**_NOTE:_**  Postman collection is created with basic token auth parameters. If you create admin and create customer with given credentials, you don't need to change other requests basic token parameters. If you want to create admin and customer different credentials, you have to add the credentials to basic token.

**_HINT:_**  You can test easily if you follow this steps;
- /user/createAdmin
- /customer/createCustomer
- /book/saveBook
- /book/addBookToStock
- /order/createOrder
- /order/getByOrderNumber
- /order/list
- /customer/getCustomerOrders
- /statistics/getMonthlyReport

### User
#### 1. createAdmin
Request:
  ````http request
  POST localhost:8081/user/createAdmin
  ````

  ```` json
    {
      "email" : "admin@getir.com",
      "password" : "1"
    }
  ````
Response:
  ```` json
  {
    "data": {
        "email": "admin@getir.com",
        "role": "ROLE_ADMIN"
    },
    "error": null,
    "time": "2023-02-08T14:12:17.816453900Z"
}
  ````

### Customer
#### 1. createCustomer
Request:
  ````http request
  POST localhost:8081/customer/createCustomer
  ````

  ```` json
    {
      "email" : "customer@getir.com",
      "password" : "1"
    }
  ````
Response:
  ```` json
  {
    "data": {
        "email": "customer@getir.com"
    },
    "error": null,
    "time": "2023-02-08T14:13:17.386547700Z"
}
  ````
#### 2. getCustomerOrders
Request:
  ````http request
  GET localhost:8081/customer/getCustomerOrders?page=0&pageSize=10
  ````
Auth:
```` json
  {
      "type": "basic",
      "basic": [
          {
              "key": "username",
              "value": "customer@getir.com",
              "type": "string"
          },
          {
              "key": "password",
              "value": "1",
              "type": "string"
          }
      ]
  }
  ````
Response:
  ```` json
  {
    "data": {
        "orderResponses": [
            {
                "orderNumber": 41225261,
                "totalAmount": 550.0,
                "orderLines": [
                    {
                        "code": "BOOK-A",
                        "quantity": 5,
                        "amount": 150.0
                    },
                    {
                        "code": "BOOK-B",
                        "quantity": 10,
                        "amount": 400.0
                    }
                ]
            }
        ],
        "page": 1,
        "pageSize": 10
    },
    "error": null,
    "time": "2023-02-08T13:55:35.625391900Z"
  }
  ````

### Book
#### 1. saveBook
Request:
  ````http request
  POST localhost:8081/book/saveBook
  ````
Auth:
```` json
  {
      "type": "basic",
      "basic": [
          {
              "key": "username",
              "value": "admin@getir.com",
              "type": "string"
          },
          {
              "key": "password",
              "value": "1",
              "type": "string"
          }
      ]
  }
  ````
  ```` json
    {
        "code": "BOOK-A",
        "price": 30.0
    }
  ````
Response:
  ```` json
  {
    "data": {
        "code": "BOOK-A",
        "price": 30.0
    },
    "error": null,
    "time": "2023-02-08T14:13:17.386547700Z"
  }
  ````
#### 2. addBookToStock
Request:
  ````http request
  POST localhost:8081/book/addBookToStock
  ````
Auth:
```` json
  {
      "type": "basic",
      "basic": [
          {
              "key": "username",
              "value": "admin@getir.com",
              "type": "string"
          },
          {
              "key": "password",
              "value": "1",
              "type": "string"
          }
      ]
  }
  ````
```` json
  {
    "code": "BOOK-A",
    "quantity": 20
  }
  ````
Response:
  ```` json
  {
    "data": {
        "code": "BOOK-A",
        "quantity": 20
    },
    "error": null,
    "time": "2023-02-08T13:55:35.625391900Z"
}
  ````

### Order
#### 1. createOrder
Request:
  ````http request
  POST localhost:8081/order/createOrder
  ````
Auth:
```` json
  {
      "type": "basic",
      "basic": [
          {
              "key": "username",
              "value": "customer@getir.com",
              "type": "string"
          },
          {
              "key": "password",
              "value": "1",
              "type": "string"
          }
      ]
  }
  ````
  ```` json
    {
        "orderLines": [
            {
                "code" : "BOOK-A",
                "quantity" : 5
            },
            {
                "code" : "BOOK-B",
                "quantity" : 10
            }
        ]
    }
  ````
Response:
  ```` json
  {
    "data": {
        "orderNumber": 41225261,
        "totalAmount": 550.0,
        "orderLines": [
            {
                "code": "BOOK-A",
                "quantity": 5,
                "amount": 150.0
            },
            {
                "code": "BOOK-B",
                "quantity": 10,
                "amount": 400.0
            }
        ]
    },
    "error": null,
    "time": "2023-02-08T14:13:17.386547700Z"
}
  ````
#### 2. getByOrderNumber
Request:
  ````http request
  GET localhost:8081/order/getByOrderNumber?orderNumber=41225261
  ````
Auth:
```` json
  {
      "type": "basic",
      "basic": [
          {
              "key": "username",
              "value": "admin@getir.com",
              "type": "string"
          },
          {
              "key": "password",
              "value": "1",
              "type": "string"
          }
      ]
  }
  ````
Response:
  ```` json
  {
    "data": {
        "orderNumber": 41225261,
        "totalAmount": 550.0,
        "orderLines": [
            {
                "code": "BOOK-A",
                "quantity": 5,
                "amount": 150.0
            },
            {
                "code": "BOOK-B",
                "quantity": 10,
                "amount": 400.0
            }
        ]
    },
    "error": null,
    "time": "2023-02-08T14:13:17.386547700Z"
  }
  ````
#### 3. listOrder
Request:
  ````http request
  POST localhost:8081/order/list
  ````
Auth:
```` json
  {
      "type": "basic",
      "basic": [
          {
              "key": "username",
              "value": "admin@getir.com",
              "type": "string"
          },
          {
              "key": "password",
              "value": "1",
              "type": "string"
          }
      ]
  }
  ````
  ```` json
    {
      "startDate" : "2023-01-01",
      "endDate" : "2023-01-30"
    }
  ````
Response:
  ```` json
  {
    "data": [
        {
            "orderNumber": 41225261,
            "totalAmount": 550.0,
            "orderLines": [
                {
                    "code": "BOOK-A",
                    "quantity": 5,
                    "amount": 150.0
                },
                {
                    "code": "BOOK-B",
                    "quantity": 10,
                    "amount": 400.0
                }
            ]
        }
    ],
    "error": null,
    "time": "2023-02-08T14:13:17.386547700Z"
  }
  ````
### Customer
#### 1. getMonthlyReport
Request:
  ````http request
  GET localhost:8081/statistics/getMonthlyReport?email=customer@getir.com
  ````
Auth:
```` json
  {
      "type": "basic",
      "basic": [
          {
              "key": "username",
              "value": "admin@getir.com",
              "type": "string"
          },
          {
              "key": "password",
              "value": "1",
              "type": "string"
          }
      ]
  }
  ````
Response:
  ```` json
  {
    "data": [
        {
            "monthName": "November",
            "orderCount": 2,
            "totalAmount": 550.0,
            "totalQuantity": 15
        }
    ],
    "error": null,
    "time": "2023-02-08T14:13:17.386547700Z"
  }
  ````

## Technologies
* Java 17
* SpringBoot 2.7.5
* Spring Data JPA
* Spring Security
* Postgres
* Maven
* Mockito
* JUnit
* Swagger

## Run Commands
You can run ```docker-compose up``` in root directory after clone repository from github.
