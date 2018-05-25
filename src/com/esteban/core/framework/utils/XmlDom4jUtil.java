package com.esteban.core.framework.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

/**
 * *
 *
 */
public class XmlDom4jUtil {

	/**
	 * 示锟斤拷xml
	 <?xml version="1.0" encoding="UTF-8"?>
	 <catalog>
	 <!--An XML Catalog-->
	 <?target instruction?>
	 <journal title="XML Zone"
	 publisher="IBM developerWorks">
	 <article level="Intermediate" date="December-2001">
	 <title>Java configuration with XML Schema</title>
	 <author>
	 <firstname>Marcello</firstname>
	 <lastname>Vitaletti</lastname>
	 </author>
	 </article>
	 </journal>
	 </catalog>
	 */
	public static void generateDocument() {
		Document document = DocumentHelper.createDocument();
		Element catalogElement = document.addElement("catalog");
		catalogElement.addComment("An XML catalog");
		catalogElement.addProcessingInstruction("target","text");
		Element journalElement =  catalogElement.addElement("journal");
		journalElement.addAttribute("title", "XML Zone");
		journalElement.addAttribute("publisher", "IBM developerWorks");
		Element articleElement=journalElement.addElement("article");
		articleElement.addAttribute("level", "Intermediate");
		articleElement.addAttribute("date", "December-2001");
		Element titleElement=articleElement.addElement("title");
		titleElement.setText("Java configuration with XML Schema");
		Element authorElement=articleElement.addElement("author");
		Element  firstNameElement=authorElement.addElement("firstname");
		firstNameElement.setText("Marcello");
		Element lastNameElement=authorElement.addElement("lastname");
		lastNameElement.setText("Vitaletti");
		document.addDocType("catalog", null,"file://c:/Dtds/catalog.dtd");

		try{
			XMLWriter output = new XMLWriter(new FileWriter( new File("g:/catalog/catalog.xml") ));
			output.write( document );
			output.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 锟斤拷取xml锟斤拷锟斤拷
	 */
	public static void parserXmlByFile(String fileName) {
		File inputXml = new File(fileName);
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(inputXml);
			Element employees = document.getRootElement();
			for (Iterator i = employees.elementIterator(); i.hasNext();) {
				Element employee = (Element) i.next();
				for (Iterator j = employee.elementIterator(); j.hasNext();) {
					Element node = (Element) j.next();
					System.out.println(node.getName() + ":" + node.getText());
				}
			}
		} catch (DocumentException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("dom4j parserXml");
	}

	public static HashMap<String,String> parserXmlByString(String xmlcnt) {
		HashMap<String,String> map=new HashMap<String,String>();
		SAXReader saxReader = new SAXReader();
		try {
			InputStream in=new ByteArrayInputStream(xmlcnt.getBytes("UTF-8"));
			Document document = saxReader.read(in);
			Element employees = document.getRootElement();
			for (Iterator i = employees.elementIterator(); i.hasNext();) {
				Element employee = (Element) i.next();
				for (Iterator j = employee.elementIterator(); j.hasNext();) {
					Element node = (Element) j.next();
					map.put(employee.getName()+"."+node.getName(), node.getText());
				}
				map.put(employee.getName(), employee.getText());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			map.put("ErrCode", "1001");
			map.put("ErrMsg", "XmlDom4jUtil.DocumentException");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			map.put("ErrCode", "1001");
			map.put("ErrMsg", "XmlDom4jUtil.UnsupportedEncodingException");
		}
		return map;
	}

	public static void main(String[] args) {
		XmlDom4jUtil.parserXmlByString("<?xml version=\"1.0\" encoding=\"utf-8\" ?><RequestXml><AppCode>JFSC</AppCode><ResponseDate>20131101023124</ResponseDate><Sign></Sign><ErrCode>0001</ErrCode><ErrMsg>锟斤拷锟斤拷XML锟斤拷锟斤拷斐</ErrMsg></RequestXml>");
	}
}