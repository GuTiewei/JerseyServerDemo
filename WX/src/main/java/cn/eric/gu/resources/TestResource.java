package cn.eric.gu.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.glassfish.grizzly.http.server.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

@Component
@Path("test")
public class TestResource {
	private Logger logger = LoggerFactory.getLogger(TestResource.class);
	
	@Path("/post")
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Document testPost(Document document, @Context Request request) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException {
		Element root = document.getDocumentElement();
		logger.info(root.getTagName());
		Element key1=(Element) root.getElementsByTagName("key1").item(0);
		logger.info(key1.getTagName()+"="+key1.getTextContent());
		Element key2=(Element) root.getElementsByTagName("key2").item(0);
		logger.info(key2.getTagName()+"="+key2.getTextContent());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();  
		Document doc = builder.parse(new FileInputStream("file/response.xml"));
		return doc;
	}
	@Path("/get")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response testGet() {
		return Response.status(200).entity("returnString").build();
	}
}
