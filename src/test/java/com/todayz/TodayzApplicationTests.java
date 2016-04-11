package com.todayz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.todayz.TodayzApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TodayzApplication.class)
@WebAppConfiguration
public class TodayzApplicationTests {

	@Test
	public void contextLoads() {
	}
}
