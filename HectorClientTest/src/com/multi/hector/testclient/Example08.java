package com.multi.hector.testclient;

import java.nio.ByteBuffer;
import java.util.Arrays;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.SuperCfResult;
import me.prettyprint.cassandra.service.template.SuperCfTemplate;
import me.prettyprint.cassandra.service.template.SuperCfUpdater;
import me.prettyprint.cassandra.service.template.ThriftSuperCfTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.factory.HFactory;

public class Example08 {

	public static void main(String[] args) throws Exception {
		Cluster cluster = HFactory.getOrCreateCluster("Test Cluster",
				"192.168.56.101:9160");
		Keyspace ksp = HFactory.createKeyspace("testdb", cluster);

		// ---슈퍼컬럼패밀리 생성:4장 cassandra-cli, special column 참조
// Create column family users2 with comparator='UTF8Type'
// and column_type='Super' and subcomparator='UTF8Type';

		// ColumnFamilyDefinition cfUsers2 =
		// HFactory.createColumnFamilyDefinition("testdb", "users2",
		// ComparatorType.UTF8TYPE);
		// cfUsers2.setKeyValidationClass("UTF8Type");
		// cfUsers2.setColumnType(ColumnType.SUPER);
		// cfUsers2.setSubComparatorType(ComparatorType.UTF8TYPE);
		//
		// cluster.addColumnFamily(cfUsers2, true);
		// System.out.println("Super Column Family 생성 완료");

		// ---데이터 추가/수정/조회
		// Template 객체 작성
		StringSerializer ss = StringSerializer.get();
		SuperCfTemplate<String, String, String> template = new ThriftSuperCfTemplate<String, String, String>(
				ksp, "users2", ss, ss, ss);

		// 데이터 추가
		SuperCfUpdater<String, String, String> updater = template
				.createUpdater("mrlee");
		SuperCfUpdater<String, String, String> uName = updater
				.addSuperColumn("name");
		uName.setString("first", "몽룡");
		uName.setString("last", "이");
		SuperCfUpdater<String, String, String> uAddress = updater
				.addSuperColumn("address");
		uAddress.setString("dong", "역삼동");
		uAddress.setString("bunji", "718-5");
		uAddress.setString("gu", "강남구");

		try {
			template.update(updater);
		} catch (Exception e) {
			e.printStackTrace();
		}

		updater = template.createUpdater("chsung");
		uName = updater.addSuperColumn("name");
		uName.setString("first", "춘향");
		uName.setString("last", "성");
		uAddress = updater.addSuperColumn("address");
		uAddress.setString("dong", "암사동");
		uAddress.setString("bunji", "125-11");
		uAddress.setString("gu", "강동구");

		try {
			template.update(updater);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("2건의 데이터 추가/수정 완료!!");

		// 데이터 조회
		// SuperCfResult<String,String,String> result =
		// template.querySuperColumns("chsung");
		SuperCfResult<String, String, String> result = template
				.querySuperColumns(Arrays.asList("mrlee", "chsung"),
						Arrays.asList("name", "address"));

		while (result != null) {
			System.out.println("key : " + result.getKey());
			for (String superColName : result.getSuperColumns()) {
				HSuperColumn<String, String, ByteBuffer> superCol = result
						.getSuperColumn(superColName);
				System.out.println("super : " + superCol.getName());
				for (HColumn<String, ByteBuffer> col : superCol.getColumns()) {
					ByteBuffer bytebuf = col.getValue();
					byte[] bytearr = new byte[bytebuf.remaining()];
					bytebuf.get(bytearr);
					String val = new String(bytearr, "UTF-8");
					System.out.println("  - " + col.getName() + " : " + val);
				}
			}

			if (result.hasNext())
				result = result.next();
			else
				result = null;
		}
	}

}
