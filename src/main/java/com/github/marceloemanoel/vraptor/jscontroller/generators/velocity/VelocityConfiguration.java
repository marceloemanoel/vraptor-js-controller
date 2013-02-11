package com.github.marceloemanoel.vraptor.jscontroller.generators.velocity;

import javax.servlet.ServletContext;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

@Component
@ApplicationScoped
public class VelocityConfiguration {
		
	private ServletContext context;
	
	public VelocityConfiguration(ServletContext context){
		this.context = context;
	}

	public String getResourceLoader() {
		String resourceLoader = context.getInitParameter("jscontroller.velocity.resource.loader");
		if (resourceLoader == null || resourceLoader.equals("")){
			resourceLoader = DefaultParameters.RESOURCE_LOADER.getValue();
		}
		return resourceLoader;
	}

	public String getLogger() {
		String logger = context.getInitParameter("jscontroller.velocity.logger");
		if (logger == null || logger.equals("")){
			logger = DefaultParameters.LOGGER.getValue();
		}
		return logger;
	}
}
