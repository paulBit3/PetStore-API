package pet.store.model;



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
