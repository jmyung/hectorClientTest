package com.multi.hector.vo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import me.prettyprint.hom.annotations.Column;

@Entity
@DiscriminatorValue("vip")
public class VIPCustomer extends Customer {
	@Column(name="vipno")
	private String vipNo;
	
	public VIPCustomer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VIPCustomer(String id, String username, String tel, String customerNo) {
		super(id, username, tel, customerNo);
		// TODO Auto-generated constructor stub
	}

	public VIPCustomer(String id, String username, String tel, String customerNo, String vipNo) {
		super(id, username, tel, customerNo);
		this.vipNo = vipNo;
	}

	public String getVipNo() {
		return vipNo;
	}

	public void setVipNo(String vipNo) {
		this.vipNo = vipNo;
	}
	
	
}
