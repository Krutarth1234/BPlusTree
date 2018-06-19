package com.ads.bTree;

import java.lang.String;
import java.util.ArrayList;


public class tuple<xType, yType>{ 
	public final xType x;
	public final yType y;
	public tuple(xType x, yType y) {
		this.x = x;
	    this.y = y;
	  }
	public xType getX() {
		return this.x;
	}
	public yType getY() {
		return this.y;
	}
	@Override
	public String toString() {
		return String.format("(%s, %s)", x, y);
		}
	public tuple<Boolean, ArrayList<tuple<Float, String>>> searchRange() {
		// TODO Auto-generated method stub
		return null;
	}
}
