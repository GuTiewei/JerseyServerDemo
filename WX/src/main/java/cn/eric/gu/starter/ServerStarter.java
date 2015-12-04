package cn.eric.gu.starter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.ws.rs.Path;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.eric.gu.exception.CommonExceptionMapper;

public class ServerStarter {

	private static Logger logger = LoggerFactory.getLogger(ServerStarter.class);
	private static ApplicationContext ctx = null;
	private static Thread thread=null;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		 logger.info("load applicationContext.xml");
		 ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		 HttpServer server = getServer();
		 server.start();
		 logger.info("server started");
		 thread=new Thread(new Runnable() {	
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					try {
						Thread.currentThread().sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}
				}
			}
		});
		 new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				thread.interrupt();
			}
		}).start();
		 thread.start();
		 System.out.println("finished");
	}

	public static HttpServer getServer() {
		final ResourceConfig rc = new ResourceConfig();
//		rc.register(JacksonFeature.class);
		for (Object object : ctx.getBeansWithAnnotation(Path.class).values()) {
            rc.register(object);
        }
		rc.registerInstances(new CommonExceptionMapper());
		return GrizzlyHttpServerFactory.createHttpServer(URI.create("http://0.0.0.0:8888/"),
                rc, false);
	}
}
