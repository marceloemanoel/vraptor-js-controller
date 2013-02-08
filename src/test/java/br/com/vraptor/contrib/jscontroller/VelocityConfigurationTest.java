package br.com.vraptor.contrib.jscontroller;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockServletContext;

import com.google.common.collect.Maps;

public class VelocityConfigurationTest {
	
	private VelocityConfiguration configuration;
	
	private MockServletContext context;
	
	@Before public void setUp(){
		MockitoAnnotations.initMocks(this);
		context = new MockServletContext();			
	}
	
	@Test public void
	whenConfigurationFound_shouldLoadOnParameters(){
		setUserConfiguredParameters();
		configuration = new VelocityConfiguration(context);
		assertEquals(mapWithUserConfiguredParameters(), configuration.getParameters());
	}
	
	@Test public void
	whenNoUserConfigurationFound_thenShouldReturnDefaulParameters(){
		configuration = new VelocityConfiguration(context);
		assertEquals(mapDefaultParameters(), configuration.getParameters());
	}

	private Map<String, String> mapDefaultParameters() {
		Map<String, String> map = Maps.newHashMap();
		map.put("logger", "org.apache.velocity.runtime.log.NullLogSystem");
		map.put("resourceLoader", "classpath");
		return map;
	}

	private void setUserConfiguredParameters() {
		context.setInitParameter("jscontroller.velocity.logger", "org.apache.velocity.runtime.log.NullLogSystem");
		context.setInitParameter("jscontroller.velocity.resource.loader", "class");
	}

	private Map<String, String> mapWithUserConfiguredParameters() {
		Map<String, String> map = Maps.newHashMap();
		map.put("logger", "org.apache.velocity.runtime.log.NullLogSystem");
		map.put("resourceLoader", "class");
		return map;
	}

}
