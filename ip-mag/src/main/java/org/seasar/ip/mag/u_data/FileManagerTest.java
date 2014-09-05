package org.seasar.ip.mag.u_data;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class FileManagerTest {
	FileManager fm = new FileManager(
			"data" + File.separator +
			"ipdat");

	@Test
	public void testCreateLockFile() {
		fm.createLockFile();
		File lock = new File(
				"data" + File.separator +
				"lock"
				);
		
		boolean result = lock.exists();
		fm.deleteLockFile();
		
		assertTrue(result);
	}

	@Test
	public void testDeleteLockFile() {
		fm.createLockFile();
		File lock = new File(
				"data" + File.separator +
				"lock"
				);
		
		boolean result = lock.exists();
		fm.deleteLockFile();
		
		assertTrue(result);
	}

}
