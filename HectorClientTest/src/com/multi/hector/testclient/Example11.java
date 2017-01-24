package com.multi.hector.testclient;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import me.prettyprint.cassandra.serializers.DynamicCompositeSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.TimeUUIDSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.DynamicComposite;
import me.prettyprint.hector.api.factory.HFactory;

import com.multi.cassandra.util.MyUtil;

public class Example11 {

	public static void main(String[] args) {
		// Example10 예제에 이어서 실행할 파일
		Cluster cluster = HFactory.getOrCreateCluster("Test Cluster",
				"192.168.56.101:9160");
		Keyspace ksp = HFactory.createKeyspace("testdb", cluster);
		
		ColumnFamilyTemplate<String, DynamicComposite> compTemplate = new ThriftColumnFamilyTemplate<String, DynamicComposite>(
				ksp, "logs", StringSerializer.get(),
				new DynamicCompositeSerializer());
		
		//특정 날짜의 로그 보기
		ColumnFamilyResult<String, DynamicComposite> res = 
				compTemplate.queryColumns("20131013");
		Collection<DynamicComposite> cols =  res.getColumnNames();
		for (DynamicComposite dc : cols) {
			UUID uuid = UUID.fromString(dc.get(0, TimeUUIDSerializer.get()).toString());
			Date d = new Date(MyUtil.getTimeFromUUID(uuid));
			String type = dc.get(1, StringSerializer.get());
			System.out.println(MyUtil.getYYYYMMDDHHMISS(d) + "|" + type + " ==> " + res.getString(dc));
		}
		
		System.out.println("조회완료!!");
	}

}
