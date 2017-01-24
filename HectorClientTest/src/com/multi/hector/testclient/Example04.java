package com.multi.hector.testclient;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;

public class Example04 {
	public static void main(String[] args) {
		Cluster cluster = HFactory.getOrCreateCluster("Test Cluster",
				"192.168.56.101:9160");
		
		Keyspace ksp = HFactory.createKeyspace("testdb", cluster);
		
		ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<>(
				ksp, "users", StringSerializer.get(), StringSerializer.get());
		
		try {
			ColumnFamilyResult<String, String> result = template
					.queryColumns("hswon");
			System.out.println("Full name : " + result.getString("full_name"));
			System.out.println("Email : " + result.getString("email"));
			System.out.println("city : " + result.getString("city"));
		} catch (HectorException e) {
			e.printStackTrace();
		}
	}
}
