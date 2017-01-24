package com.multi.hector.testclient;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import me.prettyprint.cassandra.connection.RoundRobinBalancingPolicy;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;

public class Example02 {

	public static void main(String[] args) {
		// 로드밸런싱 설정: DynamicLoadbalancingPolicy, RoundRobin~
		RoundRobinBalancingPolicy lbp = new RoundRobinBalancingPolicy();
		// 카산드라 호스트 설정
		CassandraHostConfigurator chc = new CassandraHostConfigurator(
				"192.168.56.101:9160,192.168.56.102:9160,192.168.56.103:9160,192.168.56.104:9160");
		chc.setLoadBalancingPolicy(lbp);
		
		//HostRetryService
		chc.setRetryDownedHosts(true);
		chc.setRetryDownedHostsQueueSize(10);
		chc.setRetryDownedHostsDelayInSeconds(30);
		
		// Enable the service
		chc.setAutoDiscoverHosts(true);
		// 60 seconds == 1 minute
		chc.setAutoDiscoveryDelayInSeconds(60);

		Cluster cluster = HFactory.getOrCreateCluster("Test Cluster", chc);

		Set<CassandraHost> hostSet = cluster.getKnownPoolHosts(true);
		for (CassandraHost ch : hostSet) {
			System.out.println(ch.getHost() + ", " + ch.getPort());
		}
		
		System.out.println("성공");
	}

}
