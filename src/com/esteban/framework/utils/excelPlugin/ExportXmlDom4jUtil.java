package com.esteban.framework.utils.excelPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.esteban.framework.utils.WebUtils;

public class ExportXmlDom4jUtil {
	
	public static List<Map<String,String>> getExportConfigFromXML(String elementName) {
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		File inputXml = new File(WebUtils.getConfigValByName("excelExportConfig"));
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(inputXml);
			Element employees = document.getRootElement();
			for (Iterator i = employees.elementIterator(); i.hasNext();) {
				Element employee = (Element) i.next();
				if(employee.attribute("id").getValue().equals(elementName)){
					for (Iterator j = employee.elementIterator(); j.hasNext();) {
						Element node = (Element) j.next();
						Map<String, String> map=new HashMap<String, String>();
						map.put("name", node.attribute("name").getValue());
						if(node.attribute("isExportData")!=null){
							map.put("isExportData", node.attribute("isExportData").getValue());
						}
						if(node.attribute("titleText")!=null){
							map.put("titleText", node.attribute("titleText").getValue());
						}
						if(node.attribute("isNumber")!=null){
							map.put("isNumber", node.attribute("isNumber").getValue());
						}
						if(node.attribute("digit")!=null){
							map.put("digit", node.attribute("digit").getValue());
						}
						if(node.attribute("isDate")!=null){
							map.put("isDate", node.attribute("isDate").getValue());
						}
						if(node.attribute("formatStr")!=null){
							map.put("formatStr", node.attribute("formatStr").getValue());
						}
						if(node.attribute("isConvert")!=null){
							map.put("isConvert", node.attribute("isConvert").getValue());
						}
						if(node.attribute("convertStr")!=null){
							map.put("convertStr", node.attribute("convertStr").getValue());
						}
						if(node.attribute("convertDefault")!=null){
							map.put("convertDefault", node.attribute("convertDefault").getValue());
						}
						
						list.add(map);
					}
					break;
				}
			}
		} catch (DocumentException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
}