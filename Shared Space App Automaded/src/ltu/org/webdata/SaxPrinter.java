package ltu.org.webdata;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;



import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class SaxPrinter extends DefaultHandler{

	//List myEmpls;
	List myContacts;
	
	private String tempVal;
	private String requiredFriend;
	private StringBuffer sb = new StringBuffer();
	
	//to maintain context	
	private Contacts tempCon;
	
	
	public SaxPrinter()
	{		
		myContacts = new ArrayList();	
	}
	
	
	public void runExample(String frindID, String tag) 
	{
		
		this.requiredFriend=frindID;
		if (tag.compareTo("OK")==0)			
			parseDocument(requiredFriend);
		if (tag.compareTo("high")==0)			
			parseDocument2(requiredFriend);
		if (tag.compareTo("medium")==0)			
			parseDocument3(requiredFriend);
		if (tag.compareTo("low")==0)			
			parseDocument4(requiredFriend);
		//printData();
	}

	
	private void parseDocument(String friendID) 
	{
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try 
		{
			//String queryString = "http://130.240.233.35:8080/axis2/services/SocialGraph/getMailStrength?friendID="+ friendID;
			//String queryString = "http://130.240.233.35:8080/axis2/services/GroupRecommender/recommendGroup?context="+ friendID;
			String queryString = "http://130.240.233.35:8080/axis2/services/GroupRecommenderv3/recommendGroup?context="+ friendID;
			/* Replace blanks with HTML-Equivalent. */		
			URL url = new URL(queryString.replace(" ", "%20"));
			
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
			sp.parse(new InputSource(url.openStream()), this);			
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	 }
	
	private void parseDocument2(String friendID) 
	{
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try 
		{
			//String queryString = "http://130.240.233.35:8080/axis2/services/SocialGraph/getMailStrength?friendID="+ friendID;
			//String queryString = "http://130.240.233.35:8080/axis2/services/GroupRecommender/recommendGroup?context="+ friendID;
			String queryString = "http://130.240.233.35:8080/axis2/services/GroupRecommenderv6/recommendGroup?context="+ friendID;
			/* Replace blanks with HTML-Equivalent. */		
			URL url = new URL(queryString.replace(" ", "%20"));
			
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
			sp.parse(new InputSource(url.openStream()), this);			
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	 }
	
	private void parseDocument3(String friendID) 
	{
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try 
		{
			//String queryString = "http://130.240.233.35:8080/axis2/services/SocialGraph/getMailStrength?friendID="+ friendID;
			//String queryString = "http://130.240.233.35:8080/axis2/services/GroupRecommender/recommendGroup?context="+ friendID;
			String queryString = "http://130.240.233.35:8080/axis2/services/GroupRecommenderv4/recommendGroup?context="+ friendID;
			/* Replace blanks with HTML-Equivalent. */		
			URL url = new URL(queryString.replace(" ", "%20"));
			
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
			sp.parse(new InputSource(url.openStream()), this);			
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	 }
	
	private void parseDocument4(String friendID) 
	{
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try 
		{
			//String queryString = "http://130.240.233.35:8080/axis2/services/SocialGraph/getMailStrength?friendID="+ friendID;
			//String queryString = "http://130.240.233.35:8080/axis2/services/GroupRecommender/recommendGroup?context="+ friendID;
			String queryString = "http://130.240.233.35:8080/axis2/services/GroupRecommenderv5/recommendGroup?context="+ friendID;
			/* Replace blanks with HTML-Equivalent. */		
			URL url = new URL(queryString.replace(" ", "%20"));
			
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
			sp.parse(new InputSource(url.openStream()), this);			
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	 }
	

	/**
	 * Iterate through the list and print
	 * the contents
	 */
	
	public String printData()
	{
		Iterator it=myContacts.iterator();
		StringBuffer sb1 = new StringBuffer();
		try {						
			while(it.hasNext()) {
				//System.out.println(it.next().toString());
				sb1.append(it.next().toString());
			}	
		} catch (Exception e) {
			// TODO: handle exception
		}
		//System.out.println(sb1.toString());
		return sb.toString();
		
	}
	

	//Event Handlers
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException 
	{
		//reset
		tempVal = "";
		
		try {
			
			if(qName.equalsIgnoreCase("ns:getMailStrengthResponse")) 
			
			{
				//create a new instance of contacts
				tempCon = new Contacts();
			}
			else if (qName.equalsIgnoreCase("ns:return")) {
				
				tempCon.setName(tempVal);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException 
	{
		tempVal = new String(ch,start,length);
		sb.append(tempVal);		
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException 
	{

		if(qName.equalsIgnoreCase("ns:getMailStrengthResponse")) {
			myContacts.add(tempCon);
			
			
		}else if (qName.equalsIgnoreCase("ns:return")) {		
			tempCon.setName(tempVal);
		
		}			
	}
	
}