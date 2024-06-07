package pet.store.model;

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
