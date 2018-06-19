package com.ads.bTree;
import java.util.ArrayList;

import com.ads.doublyLinkedList.node;
//TODO red black tree for storing
public class dataNode extends bNode {
	// Hideing parent variable 
	public ArrayList<tuple<Float, String>> items;
	public node<dataNode> capsuleNode;
	
	public dataNode(int order, indexNode parent) {
		super(order, parent);
		this.items =  (ArrayList<tuple<Float, String>>)super._items;
		this.capsuleNode = new node<dataNode>(this,null,null);
		}
	
	public dataNode(int order, node<dataNode> capNode, indexNode parent) {
		super(order, parent);
		this.items =  (ArrayList<tuple<Float, String>>)super._items;
		this.capsuleNode = capNode;
		}
	
	public dataNode(int order, ArrayList<tuple<Float, String>> items, node<dataNode> capNode, indexNode parent) {
		// TODO
		super(order, items, parent);
		this.capsuleNode = capNode;
		this.items =  (ArrayList<tuple<Float, String>>)super._items;
	}
	
	public dataNode(int order, ArrayList<tuple<Float, String>> items, indexNode parent) {
		super(order, items, parent);
		this.capsuleNode = new node<dataNode>(this,null,null);
		this.items =  (ArrayList<tuple<Float, String>>)super._items;
	}

	public node<dataNode> getCapNode() {
		return this.capsuleNode;
	}
	
	public void setCapNode(node<dataNode> capNode) {
		this.capsuleNode = capNode;
		return;
	}
	
	public tuple<Float, String> getKey(float key) {
		for(int i=0; i<items.size();i++) {
			if((items.get(i)).getX()==key) {
				return items.get(i);
			}
		}
		return null;
	}
	
	public tuple<Float, String> getKeyGreaterOrEqual(float key) {
		for(int i=0; i<items.size();i++) {
			if((items.get(i)).getX()>=key) {
				return items.get(i);
			}
		}
		return null;
	}
	
	public tuple<Boolean, ArrayList<tuple<Float, String>>> searchRange(float startKey, float endKey) {
		ArrayList<tuple<Float, String>> result = new ArrayList<tuple<Float, String>>();
		boolean toContinue = true;
		// assumes that items are sorted
		tuple<Float, String> curItem = (tuple<Float, String>) items.get(0);
		
		for(int i=0;i<this.size();i++) {
			curItem = (tuple<Float, String>) items.get(i);
			if (curItem !=null) {
				if(curItem.getX()>=startKey && curItem.getX()<=endKey) {
					result.add(curItem);
				}else if(curItem.getX()>endKey){
					toContinue = false;
					break;
				}
			}else {
				break;
			}
		}
			
		return new tuple<Boolean, ArrayList<tuple<Float, String>>>(toContinue, result);
	}
	public void set(dataNode node) {
		this._items = new ArrayList<tuple<Float, String>>(node.items);
		this.items =  (ArrayList<tuple<Float, String>>)super._items;
		this.links = new ArrayList<bNode>(node.links);
		this.capsuleNode = node.capsuleNode;
	}
	public void insert(tuple<Float, String> item) {
		float key = item.getX();
		for(int i=0;i<this.items.size();i++) {
			if(this.items.get(i)!=null) {
				if(this.items.get(i).getX()<=key) {
					continue;
				}else {
					this.items.add(i, item);
					this.items.remove(this.items.size()-1);
					break;
				}
			}else {
				this.items.set(i, item);
				break;
			}
		}
	}

	
}
