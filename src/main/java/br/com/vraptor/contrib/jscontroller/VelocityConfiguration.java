package br.com.vraptor.contrib.jscontroller;

import java.util.Map;

import javax.servlet.ServletContext;

import com.google.common.collect.Maps;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

@Component
@ApplicationScoped
public class VelocityConfiguration {
		
	private ServletContext context;
	private Map<String, String> parameters = Maps.newHashMap();
	
	public VelocityConfiguration(ServletContext context){
		this.context = context;
		this.loadParameters();
	}

	private void loadParameters() {
		parameters.put("logger", getLoggerParameter());
		parameters.put("resourceLoader", getResourceLoaderParameter());
	}

	private String getResourceLoaderParameter() {
		String resourceLoader = context.getInitParameter("jscontroller.velocity.resource.loader");
		if (resourceLoader == null || resourceLoader.equals("")){
			resourceLoader = DefaultParameters.RESOURCE_LOADER.getValue();
		}
		return resourceLoader;
	}

	private String getLoggerParameter() {
		String logger = context.getInitParameter("jscontroller.velocity.logger");
		if (logger == null || logger.equals("")){
			logger = DefaultParameters.LOGGER.getValue();
		}
		return logger;
	}
	
	public Map<String, String> getParameters(){
		return parameters;
	}

}
