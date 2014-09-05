package org.seasar.ip.mag.u_data;

import static org.junit.Assert.*;

import org.junit.Test;

public class IPAddressAssignmentManagerTest {
	IPAddressAssignmentManager ipaam = new IPAddressAssignmentManager("data");

	@Test
	public void testIPAddressAssignmentManager() {				
		assertEquals(0, ipaam.getSize());
	}

	@Test
	public void testGetSize() {
		assertEquals(0, ipaam.getSize());
	}

	@Test
	public void testIsRegisteredID() {
		assertFalse(ipaam.isRegisteredID("192.168.15.18"));
	}

	@Test
	public void testGetUserID() {
		assertEquals(0, ipaam.getUserID("192.168.15.18"));
	}

}
