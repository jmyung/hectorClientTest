package com.multi.hector.testclient;

import me.prettyprint.cassandra.serializers.DynamicCompositeSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.DynamicComposite;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;

public class Example09 {
	public static void main(String[] args) {
		//----미리 Column Family 생성할 것
//create column family users3 
//with comparator ='DynamicCompositeType(a=>AsciiType,b=>BytesType,i=>IntegerType,
//x=>LexicalUUIDType,l=>LongType,t=>TimeUUIDType,s=>UTF8Type,u=>UUIDType)' 
//and default_validation_class=UTF8Type and key_validation_class=UTF8Type;
		Cluster cluster = HFactory.getOrCreateCluster("Test Cluster",
				"192.168.56.101:9160");
		Keyspace ksp = HFactory.createKeyspace("testdb", cluster);
				
		ColumnFamilyTemplate<String, DynamicComposite> compTemplate  =
		        new ThriftColumnFamilyTemplate<String, DynamicComposite>(ksp,
		        		"users3", StringSerializer.get(), new DynamicCompositeSerializer());
		
		ColumnFamilyUpdater<String, DynamicComposite> updater = compTemplate.createUpdater("obama1");
		updater.setString(new DynamicComposite("name", "first"), "Barack");
		updater.setString(new DynamicComposite("name", "last"), "Obama");
		updater.setString(new DynamicComposite("address", "bunji"), "718-5");
		updater.setString(new DynamicComposite("address", "dong"), "Yeoksam-dong");
		updater.setString(new DynamicComposite("address", "gu"), "Kangnam-gu");
		
		try {
		    compTemplate.update(updater); //Do Final Update
			System.out.println("데이터 추가/수정 완료");		    
		} catch (HectorException e) {
		    e.printStackTrace();
			System.out.println("데이터 추가 실패!!");

		}

		//데이터 추가, 수정 완료
	}

}
