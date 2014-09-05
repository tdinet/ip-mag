package org.seasar.ip.mag.u_data;

import static org.junit.Assert.*;

import java.net.InetAddress;

import org.junit.Test;

public class IPAddressRangeTest {
	IPAddressRange ipar = new IPAddressRange("data");

	@Test
	public void testIPAddressRange() {		
		assertEquals("192.168.15.18", ipar.ipi.next());
	}

	@Test
	public void testGetHost() throws Exception {		
		assertEquals(
				InetAddress.getLocalHost().getHostAddress(),
				ipar.getHost());
	}

	@Test
	public void testReset() {
		ipar.reset();
	}

	@Test
	public void testIsConnection() {
		assertTrue(ipar.isConnection("192.168.15.22"));
	}

}
