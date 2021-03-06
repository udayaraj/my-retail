# productsapi  
This RESTful service provides product details along with product price.  
### Technology Stack:  

SpringBoot - RESTful service development  
Cassandra  - NoSQL database to store the product price details  
Gradle - Build Tool  
Docker - Run cassandra container.  

### Endpoint URL and Methods:  

GET - http://localhost:8080/myretail/v1/products/{id}  
PUT - http://localhost:8080/myretail/v1/products/{id}  
### Example:  
Request - GET URL - http://localhost:8080/myretail/v1/products/52328755   

Response:  

```sh
{     
	id: 52328755,  
	name: "LG 55\" Class 2160p 4K Ultra HD HDR Smart OLED TV - OLED55B7A",  
	current_price: 
	{  
		value: 999.99,  
		currency_code: "USD"   
	}   
}   
```   

Request:  PUT URL - http://localhost:8080/myretail/v1/products/52328755  

Request Body:  

```sh
{   
  "current_price": 1999.99,  
  "currency_code": "USD"  
}  
```  

Response:  


```sh
{  
	id: 52328755,  
	name: "LG 55\" Class 2160p 4K Ultra HD HDR Smart OLED TV - OLED55B7A",  
	current_price: {  
		value: 1999.99,  
		currency_code: "USD"  
	}  
}
```  

## Usage  

Clone the repo.    

Install docker.  

Reference to install docker -  https://docs.docker.com/docker-for-mac/install/  

Start the docker instance.  

Execute the below commands from command line based on the IDE. To make project IDE compatible.   
For eclipse   
  ```sh
  $ ./gradlew eclipse
  ```    
For intellij   
  ```sh
  $ ./gradlew idea
  ```    
Build JAR  
  ```sh
  $ ./gradlew clean build
  ```   

To bring up cassandra in docker container   
  ```sh
  $ cd docker
  ```   
  ```sh
  $ ./docker.sh
  ```   
  ```sh
  $ ./schema.sh
  ```   
    
docker.sh - To spin up cassandra docker image.  

schema.sh - To create required keyspace, Also inserts sample data into table.   

## Run the App   
Either of the options can be used  
  ```sh
  $ ./gradlew bootRun
  ```   
  ```sh
  $ java -jar productsapi-0.0.1-SNAPSHOT.jar
  ```   
  ```sh
  $ To start app in IDE (Run ProductsapiApplication.java class)
  ```    