package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pet.store.model.Employee;




/*
 * This EmployeeRepository(Employee Dao) interface class, extends {@link JpaRepository}. 
 * Both JpaRepository and its parent interface {@link CrudRepository} provide convenient 
 * common methods used for CRUD operations on the contributor table.
 */

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
