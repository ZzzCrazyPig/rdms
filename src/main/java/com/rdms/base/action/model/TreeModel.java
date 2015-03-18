package com.rdms.base.action.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TreeModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String text;
	private String href = "null";
	
	private List<TreeModel> children;
	
	public TreeModel() {}
	
	public JSONObject toJSONObject() {
		TreeModel model = this;
		return this.toJSONObject(model);
	}
	
	private JSONObject toJSONObject(TreeModel model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", model.getId());
		jsonObj.put("text", model.getText());
		jsonObj.put("href", model.getHref());
		if(model.getChildren() == null || model.getChildren().size() == 0) {
			return jsonObj;
		}
		JSONArray jsonChildren = new JSONArray();
		for(TreeModel child : model.getChildren()) {
			JSONObject jsonChild = this.toJSONObject(child);
			jsonChildren.add(jsonChild);
		}
		jsonObj.put("children", jsonChildren);
		return jsonObj;
	}
	
	public void addChild(TreeModel child) {
		if(this.children == null) {
			this.children = new ArrayList<TreeModel>();
		}
		this.children.add(child);
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

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public List<TreeModel> getChildren() {
		return children;
	}

	public void setChildren(List<TreeModel> children) {
		this.children = children;
	}
	

}
