package com.multi.hector.testclient;

import java.util.Collection;

import me.prettyprint.cassandra.serializers.CompositeSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;

public class Example15_Composite {

	public static void main(String[] args) {
		Cluster cluster = HFactory.getOrCreateCluster("Test Cluster",
				"192.168.56.101:9160");
		Keyspace ksp = HFactory.createKeyspace("testdb", cluster);

		ColumnFamilyTemplate<String, Composite> compTemplate = new ThriftColumnFamilyTemplate<String, Composite>(
				ksp, "CountryStateCity", StringSerializer.get(),
				CompositeSerializer.get());

		ColumnFamilyResult<String, Composite> res = compTemplate
				.queryColumns("Korea");

		Collection<Composite> cols = res.getColumnNames();

		for (Composite c : cols) {
			String state = c.get(0, StringSerializer.get());
			String city = c.get(1, StringSerializer.get());

			System.out.println(state + "|" + city + " ==> " + res.getString(c));
		}

		System.out.println("----기본 조회완료!!");

		// -----범위 쿼리
		SliceQuery<String, Composite, String> sliceQuery = HFactory
				.createSliceQuery(ksp, StringSerializer.get(),
						CompositeSerializer.get(), StringSerializer.get());
		sliceQuery.setColumnFamily("CountryStateCity");
		sliceQuery.setKey("Korea");
		Composite startRange = new Composite("Seoul", "A");
		Composite endRange = new Composite("Seoul", "Z");

		sliceQuery.setRange(startRange, endRange, false, 3);
		QueryResult<ColumnSlice<Composite, String>> result = sliceQuery
				.execute();
		ColumnSlice<Composite, String> cs = result.get();
		for (HColumn<Composite, String> col : cs.getColumns()) {
			Composite c = col.getName();
			String state = c.get(0, StringSerializer.get());
			String city = c.get(1, StringSerializer.get());
			String desc = col.getValue();
			System.out.println(state + " : " + city + "==>" + desc);
		}
		System.out.println("----범위 쿼리 완료");
	}

}
