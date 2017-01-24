package com.multi.hector.testclient;

import java.util.List;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;

public class Example06 {
	public static void main(String[] args) {
		Cluster cluster = HFactory.getOrCreateCluster("Test Cluster",
				"192.168.56.101:9160");

		Keyspace ksp = HFactory.createKeyspace("testdb", cluster);

		ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<>(
				ksp, "users", StringSerializer.get(), StringSerializer.get());

		//여러개의 Row로 Iteration하기
		MultigetSliceQuery<String,String,String> query = 
			HFactory.createMultigetSliceQuery(ksp, StringSerializer.get(), StringSerializer.get(), StringSerializer.get());
		query.setColumnFamily("users");
		query.setKeys("mspark", "chsung", "hswon");
		query.setRange("", "", false, 3);
		QueryResult<Rows<String,String,String>> result = query.execute();
		
		Rows<String,String,String> rows = result.get();
		
//		for (Row<String,String,String> row : rows) {
//			System.out.println("ROW KEY : " + row.getKey());
//			ColumnSlice<String,String> cols = row.getColumnSlice();
//			System.out.println("full_name : " + cols.getColumnByName("full_name").getValue());
//			System.out.println("email : " + cols.getColumnByName("email").getValue());
//			System.out.println("gender : " + cols.getColumnByName("gender").getValue());
//			System.out.println("=================================================");
//		}
		
		for (Row<String, String, String> row : rows) {
			System.out.println("ROW KEY : " + row.getKey());
			ColumnSlice<String, String> cols = row.getColumnSlice();
			List<HColumn<String,String>> list = cols.getColumns();
			System.out.println("-Size : " + list.size());
			for (HColumn<String,String> c : list) {
				System.out.println(c.getName() + " : " + c.getValue()); 
			}
			System.out.println("=========================");
		}	
		
	}
}
