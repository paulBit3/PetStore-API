package pet.store.model;

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
