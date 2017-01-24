package com.multi.hector.testclient;

import java.util.Date;
import java.util.Random;

import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.cassandra.serializers.DynamicCompositeSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.FailoverPolicy;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.DynamicComposite;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;

import com.multi.cassandra.util.MyUtil;

public class Example10 {

	public static void main(String[] args) throws Exception {
		// 실행할 때마다 로그를 날짜별 RowKey에 컬럼으로 추가함
		/*
		 * 20130901 timeuuid:error timeuuid:warn timeuuid:info ... message1
		 * message2 message3 ... 20130902 timeuuid:error timeuuid:warn
		 * timeuuid:info ... message11 message12 message13 ...
		 */

		// 컬럼패밀리 생성
		// create column family logs
		// with
		// comparator='DynamicCompositeType(a=>AsciiType,b=>BytesType,i=>IntegerType,x=>LexicalUUIDType,l=>LongType,t=>TimeUUIDType,s=>UTF8Type,u=>UUIDType)'
		// and default_validation_class=UTF8Type and
		// key_validation_class=UTF8Type;

		// Cluster cluster = HFactory.getOrCreateCluster("MultiTest01",
		// "192.168.56.121:9160");

		CassandraHostConfigurator chc = new CassandraHostConfigurator(
				"192.168.56.101:9160,192.168.56.102:9160,192.168.56.103:9160,192.168.56.104:9160");
		chc.setRetryDownedHosts(true);
		Cluster cluster = HFactory.getOrCreateCluster("Test Cluster", chc);

		ConfigurableConsistencyLevel cLevel = new ConfigurableConsistencyLevel();
		cLevel.setDefaultReadConsistencyLevel(HConsistencyLevel.ONE);
		cLevel.setDefaultWriteConsistencyLevel(HConsistencyLevel.QUORUM);

		Keyspace ksp = HFactory.createKeyspace("testdb", cluster, cLevel,
				FailoverPolicy.ON_FAIL_TRY_ONE_NEXT_AVAILABLE);

		ColumnFamilyTemplate<String, DynamicComposite> compTemplate = new ThriftColumnFamilyTemplate<String, DynamicComposite>(
				ksp, "logs", StringSerializer.get(),
				DynamicCompositeSerializer.get());

		// 로그를 무작위로 추가
		for (int k = 0; k < 1000; k++) {
			Thread.sleep(4);
			String[] types = new String[] { "error", "warn", "info" };
			String[] messages = new String[] { "message1", "message2",
					"message3", "messag4" };
			// 난수 발생
			Random r = new Random();
			int i1 = r.nextInt(3);
			int i2 = r.nextInt(4);

			ColumnFamilyUpdater<String, DynamicComposite> updater = compTemplate
					.createUpdater(MyUtil.getYYYYMMDD());
			updater.setString(
					new DynamicComposite(TimeUUIDUtils.getTimeUUID((new Date())
							.getTime()), types[i1]), messages[i2]);

			try {
				compTemplate.update(updater);
				System.out.println("로그 데이터 추가" + k);
			} catch (HectorException e) {
				e.printStackTrace();
			}
		}
	}

}
