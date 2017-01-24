package com.multi.hector.testclient;

import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.model.AllOneConsistencyLevelPolicy;
import me.prettyprint.cassandra.model.BasicColumnDefinition;
import me.prettyprint.cassandra.model.BasicKeyspaceDefinition;
import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.FailoverPolicy;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.ColumnDefinition;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.factory.HFactory;

public class Example01 {
	public static void main(String[] args) {
		Cluster cluster = HFactory.getOrCreateCluster("Test Cluster",
				"192.168.56.101:9160");

		// 키스페이스 삭제
		//cluster.dropKeyspace("testdb", true);

		// 키스페이스 생성
		BasicKeyspaceDefinition ksTestdb = new BasicKeyspaceDefinition();
		ksTestdb.setName("testdb");
		ksTestdb.setStrategyClass(ThriftKsDef.DEF_STRATEGY_CLASS);
		ksTestdb.setStrategyOption("replication_factor", "1");
		cluster.addKeyspace(ksTestdb, true);
		System.out.println("Keyspace 생성 완료!");
	
		//----단순 keyspace!!
		Keyspace ksp = HFactory.createKeyspace("testdb", cluster);
		 
		//----Consistency 옵션,  FailOver 옵션 부여한 Keyspace!!
//		AllOneConsistencyLevelPolicy cLevel = new AllOneConsistencyLevelPolicy();
//		ConfigurableConsistencyLevel cLevel = new ConfigurableConsistencyLevel();
//		cLevel.setDefaultReadConsistencyLevel(HConsistencyLevel.ONE);
//		cLevel.setDefaultWriteConsistencyLevel(HConsistencyLevel.QUORUM);
//		
//		Keyspace ksp = HFactory.createKeyspace("testdb", cluster, cLevel,
//				FailoverPolicy.ON_FAIL_TRY_ONE_NEXT_AVAILABLE);

		// 컬럼패밀리 생성 코드
		List<ColumnDefinition> colMetaData = new ArrayList<ColumnDefinition>();

		BasicColumnDefinition col1 = new BasicColumnDefinition();
		col1.setName(StringSerializer.get().toByteBuffer("full_name"));
		col1.setValidationClass(ComparatorType.UTF8TYPE.getClassName());

		BasicColumnDefinition col2 = new BasicColumnDefinition();
		col2.setName(StringSerializer.get().toByteBuffer("email"));
		col2.setValidationClass(ComparatorType.UTF8TYPE.getClassName());

		BasicColumnDefinition col3 = new BasicColumnDefinition();
		col3.setName(StringSerializer.get().toByteBuffer("city"));
		col3.setValidationClass(ComparatorType.UTF8TYPE.getClassName());

		BasicColumnDefinition col4 = new BasicColumnDefinition();
		col4.setName(StringSerializer.get().toByteBuffer("gender"));
		col4.setValidationClass(ComparatorType.UTF8TYPE.getClassName());

		BasicColumnDefinition col5 = new BasicColumnDefinition();
		col5.setName(StringSerializer.get().toByteBuffer("birth_year"));
		col5.setValidationClass(ComparatorType.UTF8TYPE.getClassName());

		colMetaData.add(col1);
		colMetaData.add(col2);
		colMetaData.add(col3);
		colMetaData.add(col4);
		colMetaData.add(col5);

		ColumnFamilyDefinition cfUsers = HFactory.createColumnFamilyDefinition(
				"testdb", "users", ComparatorType.UTF8TYPE, colMetaData);
		cfUsers.setKeyValidationClass("UTF8Type");
		
		ColumnFamilyDefinition cfCategories = HFactory
				.createColumnFamilyDefinition("testdb", "test",
						ComparatorType.UTF8TYPE);
		cfCategories.setKeyValidationClass("LongType");
		
		cluster.addColumnFamily(cfUsers, true);
		cluster.addColumnFamily(cfCategories, true);

		System.out.println("CF  생성 완료!!");
	}
}
