package com.rdms.base.action.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.rdms.util.JsonDateValueProcessor;

public class ComboTreeModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String text;
	private String state = "closed";
	private List<ComboTreeModel> children;
	
	public ComboTreeModel() {}
	
	public void addChild(ComboTreeModel child) {
		if(this.children == null) {
			this.children = new ArrayList<ComboTreeModel>();
		}
		this.children.add(child);
	}
	
	public JSONObject toJSONObject() {
		ComboTreeModel model = this;
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONObject jsonObj = JSONObject.fromObject(model, jsonConfig);
		jsonObj = this.processNoChildren(jsonObj);
		return jsonObj;
	}
	
	private JSONObject processNoChildren(JSONObject jsonObj) {
		JSONArray children = (JSONArray) jsonObj.get("children");
		if(children == null || children.size() == 0) {
			jsonObj.remove("children");
			jsonObj.remove("state");
			return jsonObj;
		}
		for(int i = 0, n = children.size(); i < n; i++) {
			JSONObject child = children.getJSONObject(i);
			this.processNoChildren(child);
		}
		return jsonObj;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<ComboTreeModel> getChildren() {
		return children;
	}
	public void setChildren(List<ComboTreeModel> children) {
		this.children = children;
	}
	
}
