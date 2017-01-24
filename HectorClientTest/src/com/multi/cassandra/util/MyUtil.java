package com.multi.cassandra.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MyUtil {
	
	static final long NUM_100NS_INTERVALS_SINCE_UUID_EPOCH = 0x01b21dd213814000L;

	public static long getTimeFromUUID(UUID uuid) {
		return (uuid.timestamp() - NUM_100NS_INTERVALS_SINCE_UUID_EPOCH) / 10000;
	}

	public static UUID createTimeUUID() {
		return java.util.UUID.fromString(new com.eaio.uuid.UUID().toString());
	}

	public static String getYYYYMMDD() {
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd"); 
		return df.format(d); 
	}
	
	public static String getYYYYMMDD(Date d) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd"); 
		return df.format(d); 
	}
	
	public static String getYYYYMMDDHHMISS() {
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S"); 
		return df.format(d); 
	}
	
	public static String getYYYYMMDDHHMISS(Date d) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S"); 
		return df.format(d); 
	}
	
	public static java.util.UUID getUUIDFromTimeUUID(com.eaio.uuid.UUID a)
	 {
	     return java.util.UUID.fromString(a.toString());
	 }
	 
	// byte로 되어있는 UUID를 다시 UUID형식으로 바꾸어 준다.
	// 이 함수는 cassandra에서 UUID type을 읽을 경우 (cassandra에서는 byte array로 읽히기 때문에) 원래 UUID type으로 바꾸어준다. 
	 public static java.util.UUID getUUIDFromTimeUUID(byte[] uuid)
	 {
	  long msb = 0;
	  long lsb = 0;
	  
	  assert uuid.length == 16;
	  
	  for(int i = 0 ; i < 8; i++)
	   msb = (msb << 8) | (uuid[i] & 0xff);
	  for (int i=8 ; i < 16; i++)
	   lsb = (lsb << 8) | (uuid[i] & 0xff);
	    
	  com.eaio.uuid.UUID u = new com.eaio.uuid.UUID(msb, lsb);
	  return java.util.UUID.fromString(u.toString());
	 }
	 
	//UUID를 byte array로 바꾸는데 사용한다.
	//UUID를 cassandra에 write할 때 사용한다. 
	 public static byte[] asByteArray(java.util.UUID uuid)
	 {
	  long msb = uuid.getMostSignificantBits();
	  long lsb = uuid.getLeastSignificantBits();
	  byte[] buffer = new byte[16];
	  
	  for(int i = 0 ;i < 8 ; i++)
	   buffer[i] = (byte) (msb >>> 8 * (7- i));
	  for (int i = 8 ; i < 16 ; i++)
	   buffer[i] = (byte) (lsb >>> 8 * (7 - i));
	  
	  return buffer;
	 }
}
