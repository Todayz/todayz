package com.todayz;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;


//현재 사용하지 않음.
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TodayzApplication.class);
	}
/*
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO Auto-generated method stub
		// super.onStartup(servletContext);
		// Logger initialization is deferred in case a ordered
		// LogServletContextInitializer is being used
		this.logger = LogFactory.getLog(getClass());
		WebApplicationContext rootAppContext = createRootApplicationContext(servletContext);
		if (rootAppContext != null) {
			servletContext.addListener(new ApplicationListener());
		} else {
			this.logger.debug("No ContextLoaderListener registered, as " + "createRootApplicationContext() did not "
					+ "return an application context");
		}
	}*/
}
