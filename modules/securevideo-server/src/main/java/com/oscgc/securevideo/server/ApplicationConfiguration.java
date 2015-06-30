package com.oscgc.securevideo.server;

import com.oscgc.securevideo.server.security.suburl.DefaultNamespaceRedirectFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.Filter;

/**
 * Created by cengruilin on 15/5/7.
 */

@Configuration
@ImportResource({ "classpath:/applicationContext-main.xml" })
@ComponentScan(value = "com.oscgc.securevideo", excludeFilters = { @ComponentScan.Filter(value = Controller.class) })
@EnableAsync
public class ApplicationConfiguration {

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		resolver.setMaxUploadSize(104857600);
		resolver.setMaxInMemorySize(4096);
		return resolver;
	}

}
