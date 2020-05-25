package by.epam.dobrov.dao.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import by.epam.dobrov.dao.impl.generator.HashGenerator;

public class HashGeneratorTest {

	@Test
	public void test_ShouldGeneratePasswordToMD5() {
		String password = "949204";
		String encryptedPassword = HashGenerator.generateSHA256(password);
		
		System.out.println(encryptedPassword);
		
		assertTrue(true);
	}

}
