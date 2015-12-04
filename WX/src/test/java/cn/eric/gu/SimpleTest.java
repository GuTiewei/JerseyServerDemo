package cn.eric.gu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class SimpleTest {

	Client client = ClientBuilder.newClient();
	WebTarget target = null;

	@Test
	public void testPost() throws ParserConfigurationException, FileNotFoundException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();  
		Document doc = builder.parse(new FileInputStream("file/request.xml"));
		target = client.target("http://127.0.0.1:8888/test/post");
		Response res = target.request(MediaType.APPLICATION_XML).post(Entity.entity(doc,MediaType.APPLICATION_XML));
		Document result = res.readEntity(Document.class);
		System.out.println(result.getDocumentElement().getTagName());
		System.out.println(result.getDocumentElement().getElementsByTagName("key1").item(0).getTextContent());
		System.out.println(result.getDocumentElement().getElementsByTagName("key2").item(0).getTextContent());
	}
	@Test
	public void testGet() {
		target = client.target("http://127.0.0.1:8888/test/get");
		String str = target.request(MediaType.TEXT_PLAIN).get(String.class);
		System.out.println(str);
	}
}
