package com.smartride.msa.config.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.kryptoblocks.msa.config.ConfigApplication;

// TODO: Auto-generated Javadoc
/**
 * The Class ContextTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ConfigApplication.class)
public class ContextTest {

	/**
	 * Context loads.
	 */
	@Test
	public void contextLoads() {
	}
	

}
