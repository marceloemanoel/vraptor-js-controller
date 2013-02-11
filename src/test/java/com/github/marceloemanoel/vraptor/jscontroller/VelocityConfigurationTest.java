package com.github.marceloemanoel.vraptor.jscontroller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockServletContext;

import com.github.marceloemanoel.vraptor.jscontroller.generators.velocity.VelocityConfiguration;

public class VelocityConfigurationTest {
	
	private VelocityConfiguration configuration;
	
	private MockServletContext context;
	
	@Before public void setUp(){
		context = new MockServletContext();			
	}
	
	@Test public void
	whenConfigurationFound_shouldLoadParameters(){
		setUserConfiguredParameters();
		configuration = new VelocityConfiguration(context);
		assertEquals(defaultLoggerClass(), configuration.getLogger());
		assertEquals("class", configuration.getResourceLoader());
	}
	
	@Test public void
	whenNoUserConfigurationFound_thenShouldReturnDefaulParameters(){
		configuration = new VelocityConfiguration(context);
		assertEquals(defaultLoggerClass(), configuration.getLogger());
        assertEquals(defaultResourceLoader(), configuration.getResourceLoader());
	}

    private String defaultResourceLoader() {
        return "classpath";
    }

    private String defaultLoggerClass() {
        return "org.apache.velocity.runtime.log.NullLogSystem";
    }

	private void setUserConfiguredParameters() {
		context.setInitParameter("jscontroller.velocity.logger", defaultLoggerClass());
		context.setInitParameter("jscontroller.velocity.resource.loader", "class");
	}

}
