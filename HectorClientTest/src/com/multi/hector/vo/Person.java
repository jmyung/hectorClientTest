package com.multi.hector.vo;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Table;

import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.Id;


@Entity
@Table(name="customer")
@Inheritance
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
public abstract class Person {
	@Id
	private String id;
	@Column(name="name")
	private String username;
	@Column(name="tel")
	private String tel;
	
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Person(String id, String username, String tel) {
		super();
		this.id = id;
		this.username = username;
		this.tel = tel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
}
