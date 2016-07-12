package com.oncore.middleware;

import com.amazonaws.util.StringInputStream;
import com.oncore.middleware.fileCreator.table.TableCreator;
import com.oncore.middleware.jms.message.consumer.TableCreatorMessageListener;
import com.oncore.middleware.model.Entity;
import com.sun.org.apache.xerces.internal.jaxp.SAXParserImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sun.net.www.http.HttpClient;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;


@ComponentScan
public class MiddleWareRunner {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ApplicationContext context
                = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
    }
}
