package com.alex.food.bean;

import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * 
 * @author ALEX
 *
 */
public class IntArraySerializer extends Vector<Integer> implements
		KvmSerializable {

	@Override
	public Object getProperty(int arg0) {
		return this.get(arg0);
	}

	@Override
	public int getPropertyCount() {
		return this.getPropertyCount();
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		
	}

}
