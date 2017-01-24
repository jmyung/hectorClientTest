package com.multi.hector.testclient;

import me.prettyprint.cassandra.model.CqlQuery;
import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;

public class Example07 {
	public static void main(String[] args) {
		Cluster cluster = HFactory.getOrCreateCluster("Test Cluster",
				"192.168.56.101:9160");
		Keyspace ksp = HFactory.createKeyspace("testdb", cluster);
		StringSerializer ss = StringSerializer.get();
		CqlQuery<String,String,String> cqlQuery = 
				new CqlQuery<String,String,String>(ksp, ss, ss, ss);
		cqlQuery.setQuery("select * from users");
		QueryResult<CqlRows<String,String,String>> result = cqlQuery.execute();
		CqlRows<String,String,String> rows = result.get();
		
		for (Row<String,String,String> row : rows) {
			System.out.println("RowKey : " + row.getKey());
			ColumnSlice<String,String> cols = row.getColumnSlice();
			for (HColumn<String,String> c  : cols.getColumns()) {
				System.out.println(c.getName() + " : " + c.getValue());
			}
			System.out.println("------------------------");
		}
		
		
		//데이터 추가
		String query = "INSERT INTO users (key, birth_year,city , email, full_name, gender)"
					+ " VALUES ('steve', '1955', 'SunnyVale', 'steve@opensg.net', 'steve jobs', 'M')";
		cqlQuery.setQuery(query);
		cqlQuery.execute();
		System.out.println("데이터 추가 성공");
	}

}
