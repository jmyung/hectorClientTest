package com.multi.hector.testclient;

import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;

public class Example03 {
	public static void main(String[] args) {
		Cluster cluster = HFactory.getOrCreateCluster("Test Cluster",
				"192.168.56.101:9160");
		
		Keyspace ksp = HFactory.createKeyspace("testdb", cluster);
		
		ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(
				ksp, "users", StringSerializer.get(), StringSerializer.get());

		// 데이터 추가
//		ColumnFamilyUpdater<String, String> updater = template.createUpdater("hswon");
//		updater.setString("full_name", "원형섭");
//		updater.setString("email", "stepano@opensg.net");
//		updater.setString("birth_year", "1995");
//		updater.setString("gender", "M");
//		updater.setString("city", "Seoul");
		
//		 ColumnFamilyUpdater<String,String> updater =
//		 template.createUpdater("chsung");
//		 updater.setString("full_name", "chunhyang sung");
//		 updater.setString("email", "chsung@opensg.net");
//		 updater.setString("birth_year", "1992");
//		 updater.setString("gender", "F");
//		 updater.setString("city", "Namwon");
		
		 ColumnFamilyUpdater<String,String> updater =
		 template.createUpdater("mspark");
		 updater.setString("full_name", "박문수");
		 updater.setString("email", "mspark@opensg.net");
		 updater.setString("birth_year", "1982");
		 updater.setString("gender", "M");
		 updater.setString("city", "Seoul");
		
		try {
			template.update(updater);
		} catch (HectorException e) {
			e.printStackTrace();
		}

		// 샘플 컬럼패밀리
		// CREATE COLUMN FAMILY test
		// WITH comparator = UTF8Type
		// AND key_validation_class=IntegerType
		// AND default_validation_class = UTF8Type;

		ColumnFamilyTemplate<Long, String> template2 = new ThriftColumnFamilyTemplate<Long, String>(
				ksp, "test", LongSerializer.get(), StringSerializer.get());
		ColumnFamilyUpdater<Long, String> updater2 = template2.createUpdater(1001L);
		updater2.setString("a", "a1");
		updater2.setString("b", "b1");
		try {
			template2.update(updater2);
		} catch (HectorException e) {
			e.printStackTrace();
		}
		System.out.println("추가/수정 완료!!");

	}
}
