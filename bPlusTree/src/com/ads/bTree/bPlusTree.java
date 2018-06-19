package com.ads.bTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.ads.doublyLinkedList.doublyLinkedList;
import com.ads.doublyLinkedList.node;

// TODO make B tree key data type(cur hard coded to float) independent
// TODO replace array list with red black trees.

public class bPlusTree {
	int order;
	bNode root;
	doublyLinkedList<dataNode> leafList;
	public bPlusTree(int order){
		this.order = order;
		leafList = new doublyLinkedList<dataNode>();
		this.root = new dataNode(order, null);
		leafList.start = ((dataNode)this.root).capsuleNode;
		leafList.end = ((dataNode)this.root).capsuleNode;
		
	}

	public void printTree() {
		bNode currNode = this.root;
		Queue<bNode> nodeStack = new LinkedList<bNode>();
		nodeStack.add(currNode);
		while(!nodeStack.isEmpty()) {
			currNode = nodeStack.remove();
			int i=0;
			for(i=0;i<order-1;i++) {
				if(currNode==null) {
					break;
				}
				if(currNode._items.get(i)!= null) {
					System.out.print(currNode._items.get(i).toString()+"*");
				}
				nodeStack.add(currNode.links.get(i));
			}
			System.out.println();
			if (i >= order -1)
				nodeStack.add(currNode.links.get(i));
		}
		System.out.println();
		String toPrint = "";
		node<dataNode> thisNode = leafList.start;
		 while(thisNode.next!=null) {
			 if(thisNode.getValue()!=null) {
				 for(int i=0;i<thisNode.getValue().size();i++) {
					 toPrint += "("+thisNode.getValue().items.get(i).getX()+","+thisNode.getValue().items.get(i).getY()+")------->";
				 }
			 }else {
				 toPrint += "(null)------->";
			 }
			 thisNode=thisNode.next;
		 }
		 if(thisNode.getValue()!=null) {
			 for(int i=0;i<thisNode.getValue().size();i++) {
				 toPrint += "("+thisNode.getValue().items.get(i).getX()+","+thisNode.getValue().items.get(i).getY()+")------->";
			 }
		 }else {
			 toPrint += "(null)------->";
		 }
		 System.out.println(toPrint);
	}
	//TODO does not maintain doubly linked list.
	public void insert(tuple<Float, String> item) {
		//printTree();
		tuple<dataNode, ArrayList<indexNode>> searchResult = searchHelper(item.getX());
		dataNode amendNode = searchResult.getX();
		ArrayList<indexNode> parentStack = searchResult.getY();
		if(amendNode.size()<(this.order-1)){
			amendNode.insert(item);
			
		}else {
			ArrayList<tuple<Float, String>> oldItems= new ArrayList<tuple<Float, String>> (amendNode.items);
			int oldItemsSize = oldItems.size();
			for(int i=0; i<oldItemsSize;i++) {
				if(oldItems.get(i)!=null) {
					if(oldItems.get(i).getX()<=item.getX()) {
						continue;
					}else {
						oldItems.add(i,item);
						break;
					}
				}else {
					oldItems.add(i,item);
					break;
				}
			}
			
			if(!oldItems.contains(item)) {
				oldItems.add(oldItems.size(),item);
			}
			// TODO add capsule nodes
			dataNode nodeOne = new dataNode(order,
					new ArrayList<tuple<Float, String>>(oldItems.subList(0, (order/2))),
					amendNode.capsuleNode, amendNode.parent);
			amendNode.set(nodeOne);
			
			dataNode nodeTwo = new dataNode(order,
					new ArrayList<tuple<Float, String>>(oldItems.subList(order/2 , order)), null);
			
			doublyLinkedList<dataNode> leafListTwo = new doublyLinkedList<dataNode>();
			leafListTwo.start = nodeTwo.capsuleNode;
			leafListTwo.end = nodeTwo.capsuleNode;

			ArrayList<Float> indexItems = new ArrayList<Float>(Collections.nCopies(order -1, (Float) null));
			indexItems.set(0,(nodeTwo.items.get(0)).getX());
			ArrayList<bNode> indexLinks = new ArrayList<bNode>(Collections.nCopies(order, (bNode) null));
			indexLinks.set(1,nodeTwo);
			indexNode mergeTree = new indexNode(order, indexItems, indexLinks, null);
			nodeTwo.setParent(mergeTree);
			if (parentStack.size()==0) {
				mergeTree.links.set(0, amendNode);
				this.root=mergeTree;
				amendNode.setParent(mergeTree);
				correctLeafList(leafListTwo);
				return;
			}
			int index = parentStack.get(0).links.indexOf(amendNode);
			splitAndMerge(parentStack,mergeTree, leafListTwo, index);
		}
		return;
	}


	private void correctLeafList(doublyLinkedList<dataNode> listToMerge) {
		node<dataNode> leftSibling = getLeftSibling(listToMerge.start);
		if (leftSibling == null) {
			listToMerge.end.setNext(leafList.start);
			leafList.start.setPrev(listToMerge.end);
			leafList.start = listToMerge.start;
		}else if(leftSibling.next == null) {
			leafList.end.setNext(listToMerge.start);
			listToMerge.start.setPrev(leafList.end);
			leafList.end = listToMerge.end;
		}else {
			listToMerge.start.setPrev(leftSibling);
			listToMerge.end.setNext(leftSibling.getNext());
			leftSibling.getNext().setPrev(listToMerge.end);
			leftSibling.setNext(listToMerge.start);
		}
	}
	
	private node<dataNode> getLeftSibling(node<dataNode> node) {
	
		indexNode prevNode;
		indexNode currNode = node.getValue().parent;

		int index = currNode.links.indexOf(node.getValue());
		if(index!=0) {
			return ((dataNode)currNode.links.get(index-1)).capsuleNode;
		}else {
			prevNode = currNode;
			if (currNode.parent== null) {
				return null;
			}
			currNode = currNode.parent;
		}
		bNode goDownNode;
		while(currNode!=null) {
			index = currNode.links.indexOf(prevNode);
			if(index!=0) {
				goDownNode = currNode.links.get(index -1);
				while(goDownNode.links.get(goDownNode.size())!=null) {
					goDownNode = goDownNode.links.get(goDownNode.size());
				}
				return ((dataNode)goDownNode).capsuleNode;
			}else {
				prevNode = currNode;
				if (currNode.parent==null) {
					return null;
				}
				currNode = currNode.parent;
			}
		}
		return null;
	}

	private node<dataNode> getRightSibling(node<dataNode> node) {

		indexNode prevNode;
		indexNode currNode = node.getValue().parent;

		int index = currNode.links.indexOf(node.getValue());
		if(index!=currNode.size()) {
			return ((dataNode)currNode.links.get(index+1)).capsuleNode;
		}else {
			prevNode = currNode;
			if (currNode.parent==null) {
				return null;
			}
			currNode = currNode.parent;
		}
		bNode goDownNode;
		while(currNode!=null) {
			index = currNode.links.indexOf(prevNode);
			if(index!=currNode.size()) {
				goDownNode = currNode.links.get(index+1);
				while(goDownNode.links.get(0)!=null) {
					goDownNode = goDownNode.links.get(0);
				}
				return ((dataNode)goDownNode).capsuleNode;
			}else {
				prevNode = currNode;
				if (currNode.parent==null) {
					return null;
				}
				currNode = currNode.parent;
			}
		}
		return null;
	}
	private void splitAndMerge(ArrayList<indexNode> parentStack, indexNode mergeTree, doublyLinkedList<dataNode> treeLeafList, int index) {
			
		indexNode currNode = parentStack.remove(0);

		if(currNode.size()<(this.order-1)) {
			currNode.insert(mergeTree, index);
			correctLeafList(treeLeafList);
			return;
		}else {
			ArrayList<Float> oldItems= currNode.items;
			ArrayList<bNode> oldLinks= currNode.links;
			int oldItemsSize = oldItems.size();
			int i=0;
			for(i=0; i<oldItemsSize;i++) {
				if(oldItems.get(i)!=null) {
					if(oldItems.get(i)<mergeTree.items.get(0)) {
					continue;
					}else {
						// TODO check
						oldItems.add(i,mergeTree.items.get(0));
						oldLinks.add(i+1, mergeTree.links.get(1));
						mergeTree.links.get(1).setParent(currNode);
						break;
					}
				}else {
					oldItems.add(i,mergeTree.items.get(0));
					oldLinks.add(i+1, mergeTree.links.get(1));
					mergeTree.links.get(1).setParent(currNode);
					break;
				}
				
			}

			if(i == oldItemsSize) {
				oldItemsSize = oldItems.size();
				oldItems.add(oldItemsSize, mergeTree.items.get(0));
				oldLinks.add(oldItemsSize + 1, mergeTree.links.get(1));
				mergeTree.links.get(1).setParent(currNode);
			}
			correctLeafList(treeLeafList);
			
			ArrayList<Float> itemsOne = new ArrayList<Float>(Collections.nCopies(order -1 - (order/2), (Float) null));
			ArrayList<bNode> linksOne = new ArrayList<bNode>(Collections.nCopies(order - (order/2) -1, (bNode) null));
			itemsOne.addAll(0, oldItems.subList(0, (order/2)));
			linksOne.addAll(0,oldLinks.subList(0, (order/2)+1));
			indexNode nodeOne = new indexNode(order,itemsOne,linksOne, mergeTree.parent);
			
			ArrayList<Float> itemsTwo = new ArrayList<Float>(Collections.nCopies(order - (order/2) -1, (Float) null));
			ArrayList<bNode> linksTwo = new ArrayList<bNode>(Collections.nCopies(order - (order/2) -1, (bNode) null));
			itemsTwo.addAll(0, oldItems.subList(order/2 + 1 , order));
			linksTwo.addAll(0,oldLinks.subList(order/2 + 1 , order+1));
			indexNode nodeTwo = new indexNode(order,itemsTwo,linksTwo, null);
			for(int j=0; j<=nodeTwo.size();j++) {
				nodeTwo.links.get(j).setParent(nodeTwo);
			}
			
			
			
			
			ArrayList<Float> itemsMergeTree = new ArrayList<Float>(Collections.nCopies(order -1 - (order/2 -1), (Float) null));
			ArrayList<bNode> linksMergeTree = new ArrayList<bNode>(Collections.nCopies(order - (order/2), (bNode) null));
			itemsMergeTree.set(0,oldItems.get(order/2));
			linksMergeTree.set(1,nodeTwo);
			indexNode newMergeTree = new indexNode(order, itemsMergeTree, linksMergeTree, null);
			nodeTwo.setParent(newMergeTree);
			currNode.set(nodeOne);
			
			// find right sibling and correct linked list for tree one.
			bNode goDownNode = currNode;
			while(goDownNode.links.get(goDownNode.size())!=null) {
				goDownNode = goDownNode.links.get(goDownNode.size());
			}
			
			node<dataNode> rightSibling = getRightSibling(((dataNode)goDownNode).capsuleNode);
			if (rightSibling == null) {
				((dataNode)goDownNode).capsuleNode.setNext(null);
				leafList.end = ((dataNode)goDownNode).capsuleNode;
			}else
			{
				((dataNode)goDownNode).capsuleNode.setNext(rightSibling);
				rightSibling.setPrev(((dataNode)goDownNode).capsuleNode);
			}
			// correctly assign start and end node.
			doublyLinkedList<dataNode> newLeafList = new doublyLinkedList<dataNode>();
			// start 
			goDownNode = nodeTwo;
			
			while(goDownNode.links.get(0)!=null) {
				goDownNode = goDownNode.links.get(0);
			}
			
			((dataNode)goDownNode).capsuleNode.setPrev(null);
			newLeafList.start = ((dataNode)goDownNode).capsuleNode;
			
			//end
			goDownNode = nodeTwo;
			while(goDownNode.links.get(goDownNode.size())!=null) {
				goDownNode = goDownNode.links.get(goDownNode.size());
			}
			newLeafList.end = ((dataNode)goDownNode).capsuleNode;
			
			if (parentStack.size()==0) {
				// change
				newMergeTree.links.set(0, currNode);
				this.root=newMergeTree;
				currNode.setParent(newMergeTree);
				correctLeafList(newLeafList);
				return;
			}
			index = parentStack.get(0).links.indexOf(currNode);
			splitAndMerge(parentStack,newMergeTree, newLeafList,index);
		}
	}

	private tuple<dataNode, ArrayList<indexNode>> searchHelper(float key){
		bNode curNode = this.root;
		ArrayList<indexNode> parentStack = new ArrayList<indexNode>();
		while(true) {
			//TODO isTreeEmpty
			if (curNode != null) {
				Class<?> curClass = curNode.getClass();
				if(curClass == indexNode.class) {
					parentStack.add(0, (indexNode)curNode);
					curNode = curNode.searchToInsert(key);
				}
				else if(curClass == dataNode.class) {
					tuple<dataNode, ArrayList<indexNode>> result = 
							new tuple<dataNode,ArrayList<indexNode>>((dataNode)curNode,parentStack);
					return result;
				}
			}else {
				break;
			}
		}
		// search single key
		return null;
	}
	
	public  ArrayList<tuple<Float,String>> search(float key){
		ArrayList<tuple<Float,String>> result = searchRange(key,key);
		return result;
	}
	
	public ArrayList<tuple<Float,String>> searchRange(float startKey, float endKey){
		
		dataNode temp = searchHelper(startKey).getX();
		ArrayList<tuple<Float,String>> result = new ArrayList<tuple<Float,String>>();
		dataNode curNode = temp;
		while(true) {
			tuple<Boolean,ArrayList<tuple<Float,String>>> nodeResult = curNode.searchRange(startKey, endKey); 
			result.addAll(nodeResult.getY());
			if(nodeResult.getX()) {
				node<dataNode> nextNode = curNode.capsuleNode.getNext();
				if(nextNode!=null) {
					curNode = nextNode.getValue();
				}else {
					break;
				}
			}
			else {
				break;
			}
		}
	
	// search key range 
	return result;
	}
}
