package org.w3c.wai.accessdb.utils;
import java.text.SimpleDateFormat;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.w3c.wai.accessdb.services.ConfigService;

//Customized {@code ContextResolver} implementation to pass ObjectMapper to use
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonConfigurator implements ContextResolver<ObjectMapper> {
 private ObjectMapper objectMapper;

 public JacksonConfigurator() throws Exception {
     this.objectMapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
     this.objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
     this.objectMapper.getDeserializationConfig().withDateFormat(new SimpleDateFormat(ConfigService.INSTANCE.getConfigParam("DATE_FORMAT")));
     
     final AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();

     this.objectMapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRAP_ROOT_VALUE, false);

     this.objectMapper.setDeserializationConfig(this.objectMapper.getDeserializationConfig().withAnnotationIntrospector(introspector));
     this.objectMapper.setSerializationConfig(this.objectMapper.getSerializationConfig().withAnnotationIntrospector(introspector));

     
 }
 public ObjectMapper getContext(Class<?> objectType) {
     return objectMapper;
 }
}
