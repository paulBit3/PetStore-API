
🤔About the Project:
------
A real world RESTful API web App to manage a pet store.
The API allows users to create and manage a pet store and employees, and customers data through CRUD operations. 
It can be tested using any API testing tool such as Postman, Swagger of your choice. I tested using Advanced Rest API.

------

- Back-end techs stack: Java, Spring Boot, Spring Boot JPA, Hibernate, RESTful service, MySQL, and Tomcat server


------

- ```git clone: https://github.com/paulBit3/PetStore-API.git```



 Features
 -
 - User can create and retrieve and update store information
 - The API is also designed to list all of the pet stores, and the employees that work for each store and the customers that shop at each store.
-------


Models 
-

- Customer
  
```package pet.store.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/*
 * customer class
 * JPA and Hibernate create the customer table and this entity's relationship with pet stores

 */


@Entity
@Data
public class Customer {
	/*
	 * fields
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customer_id;
	
	private String first_name;
	private String last_name;
	private String email;
	
	//a list of pet store
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST)
	private Set<PetStore> petStores = new HashSet<>();
	
	/*
	 * end fields
	 */
	
}
```
- PetStore
- 
```package pet.store.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/*
 * pet store sclass
 * 
 * JPA and Hibernate create the customer table and this entity's relationship with pet stores

 */

@Entity
@Data
public class PetStore {
	/*
	 * fields
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pet_store_id;
	private String name;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String phone;

	
	//we adding one to many relationship for a list of employee
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Employee> employee = new HashSet<>();
	
	//we adding many to many relationship for a list of employee
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "pet_store_customer",
	joinColumns = @JoinColumn(name = "pet_store_id"),
	inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Set<Customer> customers = new HashSet<>();
	
	/*
	 * end fields
	 */
}
```

- Employee

```package pet.store.model;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/*
 * employee class
 * JPA and Hibernate create the customer table and this entity's relationship with pet stores

 */

@Entity
@Data
public class Employee {
	/*
	 * fields
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employee_id;
	
	private String first_name;
	private String last_name;
	private String phone;
	private String title;
	
	//we adding many to one relationship for s list of stores
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pet_store_id")
	private PetStore petStore;
	
	
	
	
	
	/*
	 * end fields
	 */
	
}
```


Controller
-

-- Create a pet store
```
	@PostMapping("api/store")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertStore(@RequestBody PetStoreData psData) {
		log.info("Creating a store {}", psData);
		return psService.savePetStore(psData);
	}
```

-- Update a pet store
```
	//modifing pet store Rest API
	@PutMapping("api/store/{pet_store_id}")
	public PetStoreData updateStore(@PathVariable Long pet_store_id, 
			@RequestBody PetStoreData psData) {
		
		//get a pet store to modify(update)
		psData.setPet_store_id(pet_store_id);
		
		log.info("Updating pet store {}", psData);
		return psService.savePetStore(psData);
	}
```


-- get all pet store
```
	//Get all of pet store Rest API
	@GetMapping("api/store")
	public List<PetStoreData> retriveAllStores(){
		
		log.info("Get all store");
		return psService.getAllStore();
	}
```

-- Create a customer
```
	//create pet store customer Rest API
	@PostMapping("api/store/{pet_store_id}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer insertCustomer(
			@PathVariable Long pet_store_id, @RequestBody PetStoreCustomer psCust) {
		
		log.info("Creating customer {} for store with Id={}", psCust, pet_store_id);
		return psService.saveCustomer(pet_store_id, psCust);
	}
```

-- Create an employee
```
//create pet store employee Rest API
	@PostMapping("api/store/{pet_store_id}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreEmployee insertEmployee(
			@PathVariable Long pet_store_id, @RequestBody PetStoreEmployee psEmpl) {
		
		log.info("Creating employee {} for store with Id={}", psEmpl, pet_store_id);
		return psService.saveEmployee(pet_store_id, psEmpl);
	}
```


Service
-

-- find of create a store
```
	//method to find or create a pet store
	private PetStore findOrCreateStore(Long pet_store_id) {
		// store entity object
		PetStore ps;
		
		//checking null pet store id object
		if(Objects.isNull(pet_store_id)) {
			//if so, then create one
			ps = new PetStore();
		} else {
			ps = findStoreById(pet_store_id);
		}
		return ps;
	}
```


-- Save a store data
```
//method to save pet store data
	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData psData) {
		//set a pet store id
		Long pet_store_id = psData.getPet_store_id();
		
		//find or create a pet store by passing the store id
		PetStore ps = findOrCreateStore(pet_store_id);
		
		//set store values to what passed in pet store Data in JSON
		setPetStoreFields(ps, psData);
		
		//save a new store
		PetStore pStore = psRepo.save(ps);
		
		return new PetStoreData(pStore);
	}
```

🤝# Contact
-----
Paul Brou  - 📫 Reach me at: paultechnologie@gmail.com

-----

Database ERD
-----
![Screenshot 2024-05-25 234541](https://github.com/paulBit3/PetStore-API/assets/43505777/ac01c140-d736-4817-afc8-82b4a28e1456)

-----

Some Demos
-----

--Create a store
![Screenshot 2024-06-03 050645](https://github.com/paulBit3/PetStore-API/assets/43505777/97cee7ea-d1d6-4a90-8c2a-373320c3d680)

--Get a store
![Screenshot 2024-06-03 051344](https://github.com/paulBit3/PetStore-API/assets/43505777/f22b67a5-17eb-4cd5-8d21-04c82b980c26)

--Update a store
![Screenshot 2024-06-03 053933](https://github.com/paulBit3/PetStore-API/assets/43505777/3d6df0a0-3b74-4267-aa51-01e0ebb3510c)




