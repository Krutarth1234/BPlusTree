package com.ads.bTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public abstract class bNode {
	public int order;
	public ArrayList<?> _items;
	public ArrayList<bNode> links;
	public indexNode parent;
	public bNode(int order, indexNode parent){
		this.parent = parent;
		this.order = order;
		this._items = new ArrayList<Object>(Collections.nCopies(order -1, (Object) null));
		this.links = new ArrayList<bNode>(Collections.nCopies(order, (bNode) null));
	}
	public bNode(int order,ArrayList<?> items, indexNode parent){
		this.parent = parent;
		this.order = order;
		this._items = items;
		_items.addAll(Collections.nCopies(order -1 - items.size(), null));
		this.links = new ArrayList<bNode>(Collections.nCopies(order, (bNode) null));
	}
	public bNode(int order,ArrayList<?> items, ArrayList<bNode> links, indexNode parent){
		this.parent = parent;
		this.order = order;
		this._items = items;
		_items.addAll(Collections.nCopies(order -1 - items.size(), null));
		this.links = links;
		links.addAll(Collections.nCopies(order - links.size(), null));
	}
	public int size() {
		// TODO Auto-generated method stub
		int size = 0;
		for(int i=0;i<this._items.size();i++) {
			if(this._items.get(i)== null) {
				break;
			}
			size++;
		}
		return size;
	}
	public void setParent(indexNode parent) {
		this.parent = parent;
	}
	public bNode search(float key) {
		// TODO Auto-generated method stub
		return null;
	}
	public bNode searchToInsert(float key) {
		// TODO Auto-generated method stub
		return null;
	}
}
