package com.ads.doublyLinkedList;

public class node <valueType> {
	public valueType value;
	public node<valueType> next;
	public node<valueType> prev;
	public node(valueType value, node<valueType> next, node<valueType> prev) {
		this.value = value;
	    this.next = next;
	    this.prev = prev;
	  }
	public valueType getValue() {
		return this.value;
	}
	public void setValue(valueType data) {
		this.value = data;
	}
	public node<valueType> getPrev(){
		return this.prev;
	}
	public void setNext(node<valueType> node) {
		this.next = node;
	}
	public node<valueType> getNext(){
		return this.next;
	}
	public void setPrev(node<valueType> node) {
		this.prev = node;
	}
	@Override
	public String toString() {
		return String.format("%s", value);
		}
}
