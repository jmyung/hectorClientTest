package com.multi.hector.vo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import me.prettyprint.hom.annotations.Column;

@Entity
@DiscriminatorValue("normal")
public class Customer extends Person {
	@Column(name="custno")
	private String customerNo;

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(String id, String username, String tel, String customerNo) {
		super(id, username, tel);
		this.customerNo = customerNo;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
	
}
