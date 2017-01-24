package com.multi.hector.testclient;

import me.prettyprint.cassandra.serializers.CompositeSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.factory.HFactory;

public class Example14_Composite {
	public static void main(String[] args) {
		
//		create column family CountryStateCity
//		with comparator = 'CompositeType(UTF8Type,UTF8Type)'
//		and key_validation_class = 'UTF8Type'
//		and default_validation_class = 'UTF8Type';

		Cluster cluster = HFactory.getOrCreateCluster("Test Cluster", "192.168.56.101:9160");
		Keyspace ksp = HFactory.createKeyspace("testdb", cluster);
		
		ColumnFamilyTemplate<String, Composite> compTemplate = new ThriftColumnFamilyTemplate<String, Composite>(
				ksp, "CountryStateCity", StringSerializer.get(),
				CompositeSerializer.get());
		
		ColumnFamilyUpdater<String, Composite> updater = compTemplate.createUpdater("Korea");
		updater.setString(new Composite("Seoul", "Kangnam"), "�������Դϴ�");
		compTemplate.update(updater);
		updater.setString(new Composite("Kyungbuk", "Uljin"), "�����Դϴ�");
		compTemplate.update(updater);
		updater.setString(new Composite("Seoul", "Kangdong"), "�������Դϴ�");
		compTemplate.update(updater);		
		updater.setString(new Composite("Seoul", "Nowon"), "������Դϴ�");
		compTemplate.update(updater);
		
		ColumnFamilyUpdater<String, Composite> updater2 = compTemplate.createUpdater("USA");
		updater2.setString(new Composite("CA", "LosAngeles"), "�ν����������Դϴ�.");
		compTemplate.update(updater2);
		updater2.setString(new Composite("CA", "SanFransisco"), "�������ý����Դϴ�.");
		compTemplate.update(updater2);
		updater2.setString(new Composite("CA", "PaloAlto"), "�ȷξ����Դϴ�.");
		compTemplate.update(updater2);
		
		System.out.println("������ �߰�/���� �Ϸ�");
	}

}
