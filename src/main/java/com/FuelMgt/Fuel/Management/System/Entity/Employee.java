package com.FuelMgt.Fuel.Management.System.Entity;

import java.util.List;
import jakarta.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "employees")
public class Employee {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    public Employee() {
    }

    @NotBlank   //Bean validation
    @Column(nullable = false,unique = true)
    private String employeeId;
    
    
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String department;

    @NotBlank
    private String position;

    @NotBlank
    private String phone;

    @Email
    private String email;

    private boolean active = true;
    
    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<Vehicle> vehicles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Employee(Long id, @NotBlank String employeeId, @NotBlank String firstName, @NotBlank String lastName,
			@NotBlank String department, @NotBlank String position, @NotBlank String phone, @Email String email,
			boolean active) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
		this.position = position;
		this.phone = phone;
		this.email = email;
		this.active = active;
	}

    // getters and setters
    
    

}
