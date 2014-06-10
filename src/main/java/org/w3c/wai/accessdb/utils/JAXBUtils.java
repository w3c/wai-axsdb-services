package org.w3c.wai.accessdb.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JAXBUtils {
	public static String objectToJSONString(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		String json = null;
		try {
			json = mapper.defaultPrettyPrintingWriter().writeValueAsString(o);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public static Object JSONString2object(String content, Object o) {
		Class<? extends Object> valueType = o.getClass();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		try {
			o = mapper.readValue(content, valueType);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	public static String XmlObjectToString(Object o) {
		OutputStream baos = new ByteArrayOutputStream();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(o.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(o, baos);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baos.toString();

	}

	public static Object stringToObject(String s, Class theclass) throws JAXBException {
		Object o = null;
		ByteArrayInputStream input = new ByteArrayInputStream(s.getBytes());
		JAXBContext jaxbContext = JAXBContext.newInstance(theclass);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		o = theclass.cast(jaxbUnmarshaller.unmarshal(input));
		return o;
	}

	public static Object fileToObject(File file, Class theclass) throws JAXBException {
		Object o = null;
			JAXBContext jaxbContext = JAXBContext.newInstance(theclass);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			o = theclass.cast(jaxbUnmarshaller.unmarshal(file));

		return o;
	}

	public static File objectToXmlFile(String path, Object o)
			throws IOException, JAXBException {
		File file = InOutUtils.createFile(path);
		JAXBContext jaxbContext = JAXBContext.newInstance(o.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(o, file);

		return file;

	}

}
