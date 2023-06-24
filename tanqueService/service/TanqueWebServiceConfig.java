/**
 * 
 */
package com.itq.tanqueService.service;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * @author herre
 *
 */
@EnableWs
@Configuration

public class TanqueWebServiceConfig extends WsConfigurerAdapter{
	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<>(servlet, "/ws/*");
	}
	
	@Bean(name = "servicioTanque")
	public Wsdl11Definition defaultWsdl11Definition() {
		SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
		wsdl11Definition.setWsdl(new ClassPathResource("wsdl/servicioTanque.wsdl"));
		return wsdl11Definition;
	}
	
	@Bean (name = "tanque")
	public XsdSchema tanqueSchema() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/tanques.xsd"));
	}
	
	@Bean (name = "cliente")
	public XsdSchema clienteSchema() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/cliente.xsd"));
	}
	
	@Bean (name = "solicitud")
	public XsdSchema solicitudSchema() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/solicitudServicio.xsd"));
	}
}
