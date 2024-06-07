package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.model.Customer;
import pet.store.model.Employee;
import pet.store.model.PetStore;



/* Pet Store  data class
 * This class is a Data Transfer Object (DTO). It is used to load data from JSON
 * sent in the pet store requests and to convert to JSON in the replies.
 */

@Data
@NoArgsConstructor
public class PetStoreData {
	
	/*
	 * fields
	 */
	
	private Long pet_store_id;
	private String name;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String phone;
	
	
	//list of customer
	private Set<PetStoreCustomer> customers = new HashSet<>();
	
	
	//list of employees
	private Set<PetStoreEmployee> employees = new HashSet<>();
	
	
	/*
	 * end fields
	 */
	
	
	//pet park constructor
	public PetStoreData(PetStore ps) {
		pet_store_id = ps.getPet_store_id();
		name = ps.getName();
		address = ps.getAddress();
		city = ps.getCity();
		state = ps.getState();
		zip = ps.getZip();
		phone = ps.getPhone();

		
		//loop to create a list of customers in pet store
		for(Customer c : ps.getCustomers()) {
			customers.add(new PetStoreCustomer(c));
		}
		
		//loop to create a list of employees in pet store
		for(Employee empl : ps.getEmployee()) {
			employees.add(new PetStoreEmployee(empl));
		}
	}
	

	
	/*
	 * no need to create the PetStoreCustomer class seperately
	 * ...so we created an inner class here
	 */
	
	@Data
	@NoArgsConstructor
	public static class PetStoreCustomer{
		private Long customer_id;
		private String first_name;
		private String last_name;
		private String email;
		
		//constructor
		public PetStoreCustomer(Customer c) {
			customer_id = c.getCustomer_id();
			first_name = c.getFirst_name();
			last_name = c.getLast_name();
			email = c.getEmail();
		}
	}
	
	/*
	 * no need to create the PetStoreEmployee class seperately
	 * ...so we created an inner class here
	 */
	
	@Data
	@NoArgsConstructor
	public static class PetStoreEmployee{
		private Long employee_id;
		private String first_name;
		private String last_name;
		private String title;
		private String phone;
		
		//constructor
		public PetStoreEmployee(Employee empl) {
			employee_id = empl.getEmployee_id();
			first_name = empl.getFirst_name();
			last_name = empl.getLast_name();
			title = empl.getTitle();
			phone = empl.getPhone();
		}
	}
	
	
	
}
