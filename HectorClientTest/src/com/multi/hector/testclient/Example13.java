package com.multi.hector.testclient;


import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hom.EntityManagerImpl;

import com.multi.hector.vo.Customer;
import com.multi.hector.vo.VIPCustomer;

public class Example13 {
	public static void main(String[] args) {
		
//CREATE COLUMN FAMILY customer
//WITH comparator = UTF8Type
//AND key_validation_class=UTF8Type
//AND default_validation_class = UTF8Type;

		
		Cluster cluster = HFactory.getOrCreateCluster("Test Cluster", "192.168.56.101:9160");
		Keyspace ksp = HFactory.createKeyspace("testdb", cluster);
		    
		EntityManagerImpl em = new EntityManagerImpl(ksp, "com.multi.hector.vo");
		Customer c1 = new Customer("mspark", "박문수", "010-245-8655", "C-22134");
		VIPCustomer v1 = new VIPCustomer("mrlee","이몽룡", "010-1212-4545", "C-24511", "V-1004");
		em.persist(c1);
		em.persist(v1);
		System.out.println("데이터 추가/수정 완료");
		
		//데이터 조회
		Customer c = (Customer)em.find(Customer.class, "mrlee");
		System.out.println(c.getUsername() + " : " + c.getCustomerNo());
	    

	}
}
