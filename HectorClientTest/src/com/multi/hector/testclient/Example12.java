package com.multi.hector.testclient;

import java.util.HashMap;

import me.prettyprint.cassandra.dao.SimpleCassandraDao;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;

public class Example12 {

	public static void main(String[] args) {

//		CREATE KEYSPACE testdb 
//		with placement_strategy = 'org.apache.cassandra.locator.SimpleStrategy'  
//		and strategy_options = { replication_factor:2 };

		
//		CREATE COLUMN FAMILY users
//		WITH comparator = UTF8Type
//		AND key_validation_class=UTF8Type
//		AND column_metadata = [
//			{column_name: full_name, validation_class: UTF8Type}
//			{column_name: email, validation_class: UTF8Type}
//			{column_name: city, validation_class: UTF8Type}
//			{column_name: gender, validation_class: UTF8Type}
//			{column_name: birth_year, validation_class: UTF8Type}
//		];

		
//		 ColumnFamilyUpdater<String,String> updater =
//		 template.createUpdater("mspark");
//		 updater.setString("full_name", "박문수");
//		 updater.setString("email", "mspark@opensg.net");
//		 updater.setString("birth_year", "1982");
//		 updater.setString("gender", "M");
//		 updater.setString("city", "Seoul");
		
		//----SimpleCassandraDao 객체 예제
		
		Cluster cluster = HFactory.getOrCreateCluster("Test Cluster",
				"192.168.56.101:9160,192.168.56.102:9160,192.168.56.103:9160,192.168.56.104:9160");
		Keyspace ksp = HFactory.createKeyspace("testdb", cluster);
		
		SimpleCassandraDao dao = new SimpleCassandraDao();
		dao.setKeyspace(ksp);
		dao.setColumnFamilyName("users");
		
		dao.insert("obama", "full_name", "barack obama");
		dao.insert("obama", "email", "bobama@opensg.net");
		dao.insert("obama", "birth_year", "1952");
		dao.insert("obama", "gender", "M");
		dao.insert("obama", "city", "Hawaii");
		
		System.out.println("데이터 추가");
		
		
		
	}

}
