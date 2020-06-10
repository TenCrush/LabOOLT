package arraylist.visualization;

import java.util.ArrayList;



public class ArrayListModel {
	public ArrayList<Integer> array = new ArrayList<Integer>();
	private int size;
	
	public ArrayListModel() {
	
	}
	public ArrayListModel(int size) {
		this.size = size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getSize() {
		return array.size();
	}
	public void append(int i) {
		if(array.size()<size) {
			array.add(i);
		}
		else {
			System.out.println("No more space");
		}
	}
	public void remove(int i) {
		if(array.size()>0 || i<array.size()) {
			array.remove(i);
		}
		else {
			System.out.println("Nothing in arraylist");
		}
	}
	public void insert(int i, int index) {
		if(array.size()<size) {
			array.add(index, i);
		}
		else {
			System.out.println("No more space");
		}
	}
	public void replace(int index, int i) {
		if(array.size()>0 && array.size()<=size) {
			array.set(index, i);
		}
		else {
			System.out.println("Cant replace");
		}
	}
	public void print(){
		for(int i = 0; i < array.size(); i++) {   
		    System.out.format("%d " ,array.get(i));
		}  
		System.out.println();
	}
	public void clear(){
		array.clear();
		}
	
	public int getLast() {
		if (array.size()>0) {
			return array.get(array.size()-1);
		}
		else return 0;
	}
	public boolean checkFull() {
		if (array.size() == size) {
			return true;
		}
		else return false;
	}
	public void pop() {
		if (array.size()>0) {
			array.remove(array.size()-1);
		}
// dm the cung loi :) ngu vl
	}
}

