package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pet.store.model.PetStore;




/*
 * This PetStoreRepository(PetStore Dao) interface class, extends {@link JpaRepository}. 
 * Both JpaRepository and its parent interface {@link CrudRepository} provide convenient 
 * common methods used for CRUD operations on the contributor table.
 */

public interface PetStoreRepository extends JpaRepository<PetStore, Long> {
	
}
