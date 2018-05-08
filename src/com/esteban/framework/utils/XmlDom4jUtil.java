package com.esteban.framework.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * *
 *
 * @author hongliang.dinghl * Dom4j 锟斤拷锟絏ML锟侥碉拷锟斤拷锟斤拷锟絏ML锟侥碉拷
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
		// 使锟斤拷 DocumentHelper 锟洁创锟斤拷一锟斤拷锟侥碉拷实锟斤拷 DocumentHelper 锟斤拷锟斤拷锟?XML 锟侥碉拷锟节碉拷锟?dom4jAPI锟斤拷锟斤拷锟斤拷
		Document document = DocumentHelper.createDocument();
		// 使锟斤拷 addElement() 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷元锟斤拷 catalog 锟斤拷 addElement() 锟斤拷锟斤拷锟斤拷 XML 锟侥碉拷锟斤拷锟斤拷锟斤拷元锟斤拷
		Element catalogElement = document.addElement("catalog");
		// 锟斤拷 catalog 元锟斤拷锟斤拷使锟斤拷 addComment() 锟斤拷锟斤拷锟斤拷锟阶拷汀锟紸n XML catalog锟斤拷锟斤拷
		catalogElement.addComment("An XML catalog");
		// 锟斤拷 catalog 元锟斤拷锟斤拷使锟斤拷 addProcessingInstruction() 锟斤拷锟斤拷锟斤拷锟斤拷一锟斤拷锟斤拷锟斤拷指锟斤拷
		catalogElement.addProcessingInstruction("target","text");
		// 锟斤拷 catalog 元锟斤拷锟斤拷使锟斤拷 addElement() 锟斤拷锟斤拷锟斤拷锟斤拷 journal 元锟斤拷
		Element journalElement =  catalogElement.addElement("journal");
		// 使锟斤拷 addAttribute() 锟斤拷锟斤拷锟斤拷 journal 元锟斤拷锟斤拷锟?title锟斤拷 publisher 锟斤拷锟斤拷
		journalElement.addAttribute("title", "XML Zone");
		journalElement.addAttribute("publisher", "IBM developerWorks");
		// 锟斤拷 article 元锟斤拷锟斤拷锟斤拷锟?journal 元锟斤拷
		Element articleElement=journalElement.addElement("article");
		// 为 article 元锟斤拷锟斤拷锟斤拷 level 锟斤拷 date 锟斤拷锟斤拷
		articleElement.addAttribute("level", "Intermediate");
		articleElement.addAttribute("date", "December-2001");
		// 锟斤拷 article 元锟斤拷锟斤拷锟斤拷锟斤拷 title 元锟斤拷
		Element titleElement=articleElement.addElement("title");
		// 使锟斤拷 setText() 锟斤拷锟斤拷锟斤拷锟斤拷 article 元锟截碉拷锟侥憋拷
		titleElement.setText("Java configuration with XML Schema");
		// 锟斤拷 article 元锟斤拷锟斤拷锟斤拷锟斤拷 author 元锟斤拷
		Element authorElement=articleElement.addElement("author");
		// 锟斤拷 author 元锟斤拷锟斤拷锟斤拷锟斤拷 firstname 元锟截诧拷锟斤拷锟矫革拷元锟截碉拷锟侥憋拷
		Element  firstNameElement=authorElement.addElement("firstname");
		firstNameElement.setText("Marcello");
		// 锟斤拷 author 元锟斤拷锟斤拷锟斤拷锟斤拷 lastname 元锟截诧拷锟斤拷锟矫革拷元锟截碉拷锟侥憋拷
		Element lastNameElement=authorElement.addElement("lastname");
		lastNameElement.setText("Vitaletti");
		// 锟斤拷锟斤拷使锟斤拷 addDocType() 锟斤拷锟斤拷锟斤拷锟斤拷牡锟斤拷锟斤拷锟剿碉拷锟?
		// 锟斤拷锟斤拷锟斤拷锟?XML 锟侥碉拷锟斤拷锟斤拷锟斤拷锟侥碉拷锟斤拷锟斤拷说锟斤拷
		// <!DOCTYPE catalog SYSTEM "file://c:/Dtds/catalog.dtd">
		document.addDocType("catalog", null,"file://c:/Dtds/catalog.dtd");

		// 锟斤拷锟?XML 锟侥碉拷 catalog.xml 锟侥筹拷锟斤拷XmlDom4J.java锟斤拷
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
		// 使锟斤拷 SAXReader 锟斤拷锟斤拷 XML 锟侥碉拷
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
		// 使锟斤拷 SAXReader 锟斤拷锟斤拷 XML 锟侥碉拷
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
		// 锟斤拷锟絰ml
//		XmlDom4jDemo.generateDocument();
		// 锟斤拷取xml
		XmlDom4jUtil.parserXmlByString("<?xml version=\"1.0\" encoding=\"utf-8\" ?><RequestXml><AppCode>JFSC</AppCode><ResponseDate>20131101023124</ResponseDate><Sign></Sign><ErrCode>0001</ErrCode><ErrMsg>锟斤拷锟斤拷XML锟斤拷锟斤拷斐?/ErrMsg></RequestXml>");
	}
}