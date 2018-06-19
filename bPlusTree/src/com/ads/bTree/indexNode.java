package com.ads.bTree;

import java.util.ArrayList;

public class indexNode extends bNode{
	
	public ArrayList<Float> items;
	
	public indexNode(int order, ArrayList<Float> items, indexNode parent) {
		super(order, items, parent);
		this.items =  (ArrayList<Float>)super._items;
	}
	
	public indexNode(int order, ArrayList<Float> items, ArrayList<bNode> links, indexNode parent) {
		super(order, items, links, parent);
		this.items =  (ArrayList<Float>)super._items;
	}

	
	public bNode searchToInsert(float key) {
		if (key<=items.get(0)) {
			return links.get(0);
		}
		float curItem = items.get(0);
		float nextItem;
		if(items.get(1) ==null) {
			return links.get(1);
		}else {
			nextItem = items.get(1);
		}
		for(int i=0; i<this.items.size()-1;i++) {
			//TODO check if this works.
			if(key>curItem && key <=nextItem) {
				return links.get(i+1);
			}else {
				curItem = nextItem;
				if(i+2>=order-1) {
					return links.get(i+2);
				}
				else if(items.get(i+2) ==null) {
					return links.get(i+2);
				}else {
					nextItem = items.get(i+2);
				}
			}
		}
		return links.get(links.size()-1);
	}
	
	// TODO maintain links order and doubly linked list as well
	public void insert(indexNode mergeTree, int index) {
		
		ArrayList<Float> oldItems= this.items;
		ArrayList<bNode> oldLinks= this.links;
		int oldItemsSize = oldItems.size();
		for(int i=index; i<oldItemsSize;i++) {
			if(oldItems.get(i)!=null) {
				if(oldItems.get(i)<mergeTree.items.get(0)) {
					continue;
				}else {
					// TODO check
					oldItems.add(i,mergeTree.items.get(0));
					oldItems.remove(oldItems.size()-1);
					oldLinks.add(i+1, mergeTree.links.get(1));
					mergeTree.links.get(1).setParent(this);
					oldLinks.remove(oldLinks.size()-1);
					return;
				}
			}
			else {
				oldItems.set(i,mergeTree.items.get(0));
				oldLinks.set(i+1, mergeTree.links.get(1));
				mergeTree.links.get(1).setParent(this);
				return;
			}
		}
	}

	public void set(indexNode node) {
		this.items.removeAll(_items);
		this.items.addAll(node.items);
		this._items =  (ArrayList<Float>)items;
		this.links.removeAll(links);
		this.links.addAll(node.links);
	}
}
