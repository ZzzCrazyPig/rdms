package com.rdms.base.action.model;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public abstract class BaseModel {
	
//	public abstract String[] getModelFields();
	@JSON(serialize=false)
	public Map<String, String> getFieldMap() throws DocumentException {
		Class<?> clazz = this.getClass();
		InputStream in = clazz.getResourceAsStream(clazz.getSimpleName() + ".fm.xml");
		SAXReader reader = new SAXReader();
		Document doc = reader.read(in);
		Element root = doc.getRootElement();
		Map<String, String> fieldMap = new HashMap<String, String>();
		for(Iterator<?> it = root.elementIterator(); it.hasNext();) {
			Element elem = (Element) it.next();
			String modelField = elem.attributeValue("model-field");
			String entityField = elem.attributeValue("entity-field");
			fieldMap.put(modelField, entityField);
		}
		return fieldMap;
	}
	
}
