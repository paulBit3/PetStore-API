package pet.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.service.PetStoreService;

/**
 * This class intercepts HTTP requests and formulates appropriate responses

*/


@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
	
	//injects the pet store service in this fields after calling the service class
	@Autowired
	private PetStoreService psService;
	
	
	
	/*
	 * implementing pet store
	 */
	
	
	/* Rest API...
	 * we map the POST request to the method, and send the request to /api/pet_store
	 * POST request will call savePetStore mthod service to create and insert a store
	 * in the Database
	 */
	@PostMapping("api/store")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertStore(@RequestBody PetStoreData psData) {
		log.info("Creating a store {}", psData);
		return psService.savePetStore(psData);
	}
	
	
	//modifing pet store Rest API
	@PutMapping("api/store/{pet_store_id}")
	public PetStoreData updateStore(@PathVariable Long pet_store_id, 
			@RequestBody PetStoreData psData) {
		
		//get a pet store to modify(update)
		psData.setPet_store_id(pet_store_id);
		
		log.info("Updating pet store {}", psData);
		return psService.savePetStore(psData);
	}
	
	
	//Get all of pet store Rest API
	@GetMapping("api/store")
	public List<PetStoreData> retriveAllStores(){
		
		log.info("Get all store");
		return psService.getAllStore();
	} 
	
	
	//get store by Id Rest API
	@GetMapping("api/store/{pet_store_id}")
	public PetStoreData getStoreById(@PathVariable Long pet_store_id) {
		
		log.info("Get store by Id = {}", pet_store_id);
		return psService.getStoreById(pet_store_id);
	}
	
	
	//delete a store by store Id Rest API
	@DeleteMapping("api/store/{pet_store_id}")
	public Map<String, String> deleteStore(
			@PathVariable Long pet_store_id){
		
		log.info("Deleting store with Id = {}", pet_store_id);
		
		//deleting
		psService.deleteStore(pet_store_id);
		return Map.of("message","Store with Id " + pet_store_id + "successfully deleted!");
		
	}
	
	/*
	 * end implementing pet store
	 */

	
	
	/*
	 * implementing pet store customer and employee
	 */
	
	//create pet store customer Rest API
	@PostMapping("api/store/{pet_store_id}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer insertCustomer(
			@PathVariable Long pet_store_id, @RequestBody PetStoreCustomer psCust) {
		
		log.info("Creating customer {} for store with Id={}", psCust, pet_store_id);
		return psService.saveCustomer(pet_store_id, psCust);
	}
	
	
	//create pet store employee Rest API
	@PostMapping("api/store/{pet_store_id}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreEmployee insertEmployee(
			@PathVariable Long pet_store_id, @RequestBody PetStoreEmployee psEmpl) {
		
		log.info("Creating employee {} for store with Id={}", psEmpl, pet_store_id);
		return psService.saveEmployee(pet_store_id, psEmpl);
	}
	
	
	
	/*
	 * end implementing pet stores customer and employees
	 */
	
	
}
