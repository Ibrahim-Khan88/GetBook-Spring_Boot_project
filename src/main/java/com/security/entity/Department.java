package com.security.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Department {

	@Id
	@GeneratedValue
	int id;

	@NotBlank
	@Size(max = 50)
	String name;

	@ManyToMany(mappedBy = "department", fetch = FetchType.LAZY)
	private List<Book> book;

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + "]";
	}

}
