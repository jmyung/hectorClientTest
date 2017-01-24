package com.multi.hector.testclient;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.ColumnSliceIterator;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.SliceQuery;

public class Example05 {
	public static void main(String[] args) {
		Cluster cluster = HFactory.getOrCreateCluster("Test Cluster",
				"192.168.56.101:9160");
		
		Keyspace ksp = HFactory.createKeyspace("testdb", cluster);
		
		ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<>(
				ksp, "users", StringSerializer.get(), StringSerializer.get());
		
		SliceQuery<String,String,String> query = 
				HFactory.createSliceQuery(ksp, StringSerializer.get(), StringSerializer.get(), StringSerializer.get());
		query.setColumnFamily("users");
		query.setKey("mspark");
		ColumnSliceIterator<String, String, String> iterator = 
			    new ColumnSliceIterator<String, String, String>(query, null, "\uFFFF", false, 5);
		
		while (iterator.hasNext()) {
			HColumn<String,String> col = iterator.next();
			System.out.println(col.getName() + " : " + col.getValue());
		}
	}
}
