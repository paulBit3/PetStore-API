package pet.store.service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;


import pet.store.dao.CustomerRepository;
import pet.store.dao.EmployeeRepository;
import pet.store.dao.PetStoreRepository;

import pet.store.model.Customer;
import pet.store.model.Employee;
import pet.store.model.PetStore;




/** Pet Store service 
 * This class sits between the controller (I/O layer) and the data (DAO) layer.
 * It is responsible for transforming the DTO (Data Transfer Object) data into
 * the JPA entities and vice versa
*/

@Service // Tells Spring that this class is a Managed Bean
public class PetStoreService {
	
	//Injects the PetStoreRepository bean into this field
	@Autowired
	private PetStoreRepository psRepo;
	
	
	//Injects the CustomerRepository bean into this field
	@Autowired
	private CustomerRepository cRepo;
	
	
	//Injects the EmployeeRepository bean into this field
	@Autowired
	private EmployeeRepository emplRepo;

	
	/*
	 * emplementing pet store
	 */
	
	//methode to save pet store data
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
	
	
	//method to set fields for pet store
	private void setPetStoreFields(PetStore ps, 
			PetStoreData psData) {
		// setting pet store fields values to pet store data fields
		ps.setPet_store_id(psData.getPet_store_id());
		ps.setName(psData.getName());
		ps.setAddress(psData.getAddress());
		ps.setCity(psData.getCity());
		ps.setState(psData.getState());
		ps.setZip(psData.getZip());
		ps.setPhone(psData.getPhone());
		
	}

	
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
	
	
	
	//method to find store by Id
	private PetStore findStoreById(Long pet_store_id) {
		
		return psRepo.findById(pet_store_id)
				.orElseThrow(() -> new NoSuchElementException(
						"Store with ID=" + pet_store_id + "was not found."));
	}
	
	/*
	 * end emplementing pet store
	 */
	
	
	
	
	/*
	 * emplementing employee
	 */
	
	
	//method to save employee data. we passing pet_store_id and employee object
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long pet_store_id, 
			PetStoreEmployee psEmpl) {
		
		//set a pet store
		PetStore ps = findStoreById(pet_store_id);
		
		//find or create employee by passing the store id and employee id
		Employee empl = findOrCreateEmployee(pet_store_id, psEmpl.getEmployee_id());
		
		//set employee values to what passed in employee Data in JSON
		setEmployeeFields(empl, psEmpl);
		
		//set employee pet store
		empl.setPetStore(ps);
		
		//add employee to pet store
		ps.getEmployee().add(empl);
		
		//save a new employee
		Employee employee = emplRepo.save(empl);
		
		return new PetStoreEmployee(employee);
		
	}
	
	
	
	//method to set fields for employee
	private void setEmployeeFields(Employee empl,
			PetStoreEmployee psEmpl) {
		// setting employee fields values to pet store employee data fields
		empl.setEmployee_id(psEmpl.getEmployee_id());
		empl.setFirst_name(psEmpl.getFirst_name());
		empl.setLast_name(psEmpl.getLast_name());
		empl.setPhone(psEmpl.getPhone());
		empl.setTitle(psEmpl.getTitle());

		
	}
	

	//method to find or create employee by passing the store id and employee id
	private Employee findOrCreateEmployee(Long pet_store_id, 
			Long employee_id) {
		
		//employee entity object
		Employee empl;
		
		//checking null employee id object
		if(Objects.isNull(employee_id)) {
			//then create a new one
			empl = new Employee();
		} else {
			//if id not null, we call get emplyee method and we pass both store and employee Id
			empl = getEmployeById(pet_store_id, employee_id);
		}
		return empl;
	}

	
	//method to find employee by Id
	private Employee getEmployeById(Long pet_store_id, Long employee_id) {
		
		Employee empl = emplRepo.findById(employee_id)
				.orElseThrow(() -> new NoSuchElementException(
						"Employee with ID=" + employee_id + " does not exist"));
		
		//if store id associated with employee match, return employee 
		if(empl.getPetStore().getPet_store_id() == pet_store_id) {
			return empl;
		} else {
			//if not equal to the store Id pass, throw Exception
			throw new IllegalArgumentException(
					"This store ID " + pet_store_id + 
					"Not associated with employee with ID " + employee_id);
		}
	}
	

	
	/*
	 * end emplementing employee
	 */
	
	
	
	
	/*
	 * emplementing customer
	 */
	
	//method to save customer data. we pass pet store Id and customer object
	@Transactional(readOnly = false)
	public PetStoreCustomer saveCustomer(Long pet_store_id, 
			PetStoreCustomer psCust) {
		
		//set a pet store
		PetStore ps = findStoreById(pet_store_id);
		
		//find or create customer by passing the store id and customer id
		Customer c = findOrCreateCustomerById(
				pet_store_id, psCust.getCustomer_id());
		
		
		//set customer values to what passed in cuatomer Data in JSON
		setCustomerFields(c, psCust);
		
		//add store to customer
		c.getPetStores().add(ps);
		
		//add customer to pet store
		ps.getCustomers().add(c);
		
		//save a new customer
		Customer cust = cRepo.save(c);
		
		return new PetStoreCustomer(cust);
	}

	
	
	//method to set fields for customer
	private void setCustomerFields(Customer c, 
			PetStoreCustomer psCust) {
		// setting customer fields values to pet store customer data fields
		c.setCustomer_id(psCust.getCustomer_id());
		c.setFirst_name(psCust.getFirst_name());
		c.setLast_name(psCust.getLast_name());
		c.setEmail(psCust.getEmail());
		
	}

	

	//method to find or create customer by passing the store id and customer id
	private Customer findOrCreateCustomerById(Long pet_store_id, 
			Long customer_id) {

		//customer entity object
		Customer c;
		
		//checking null customer id object
		if(Objects.isNull(customer_id)) {
			//then create a new customer
			c = new Customer();
		} else {
			//if id not null, we call get customer method and we pass both store and customer Id
			c = getCustomerById(pet_store_id, customer_id);
		}
		
		return c;
	}

	
	//method to find customer by Id
	private Customer getCustomerById(Long pet_store_id, Long customer_id) {
		
		Customer c = cRepo.findById(customer_id)
				.orElseThrow(() -> new NoSuchElementException(
						"Customer with ID" + customer_id + " does not exist."));
		
		/*
		 * if store id associated with customer match, return customer
		 */
		
		// a list of pet store
		Set<PetStore> petStores = c.getPetStores() ;
		
		//boolean to check store id
		boolean IsEqualStoreId = false;
		
		//ehance loop to check store Id
		for(PetStore pStore : petStores) {
			if(pStore.getPet_store_id() == pet_store_id) {
				IsEqualStoreId = true;
			}
		}
		//then if Id matches, we return customer
		if(IsEqualStoreId)
			return c;
		else
			throw new IllegalArgumentException(
					"This store ID " + pet_store_id + 
					"Not associated with employee with ID " + customer_id);
	
	}

	
	
	/*
	 * end emplementing customer
	 */
	
	
	
	//method to get all store data from pet store table
	public List<PetStoreData> getAllStore(){
		return psRepo.findAll()
				.stream()
				.map(PetStoreData::new)
				.toList();
//		//a list of store
//		List<PetStore> pStores = psRepo.findAll();
//		
//		//an empty list of store data
//		List<PetStoreData> psRes = new LinkedList<>();
//		
//		//enhance loop in pet store 
//		for(PetStore pStore : pStores) {
//			//create a new store data
//			PetStoreData psData = new PetStoreData(pStore);
//			
//			//get clean and empty employees
//			psData.getEmployees().clear();
//			psData.getCustomers().clear();
//			
//			//saving new store data
//			psRes.add(psData);
//		}
//		return psRes;
	}
	
	//method to get a pet store
	public PetStoreData getStoreById(Long pet_store_id) {
		//get a store object
		PetStore pStore = findStoreById(pet_store_id);
		
		//return a new store data
		return new PetStoreData(pStore);
	}
	
	
	//delete a store
	public void deleteStore(Long pet_store_id) {
		//get a store object
		PetStore pStore = findStoreById(pet_store_id);
		
		psRepo.delete(pStore);
	}
	

}
