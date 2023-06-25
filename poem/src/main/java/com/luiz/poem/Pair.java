package com.luiz.poem;
/**
 * 
 * @author Luiz Felipe Tafner
 *
 */
import java.lang.String;
public class Pair {

	private String left;
	private Integer right;
	String temp;
	/**
	 * Class constructor
	 * @param left intial string value
	 * @param right intial int value
	 */
	public Pair(String left, Integer right) {
		assert left != null;
	    assert right != null;

	    this.left = left;
	    this.right = right;
	}
	/**
	 * Method to return string value of pair
	 * @return string
	 */
	public String getLeft() { return left; }
	/**
	 * Method to return int value of pair
	 * @return int
	 */
	public Integer getRight() { return right; }
	
	/**
	 * Method to set the string value of pair to some value s
	 * @param s The value to set
	 */
	public void setLeft(String s) { left = s; }
	/**
	 * Method to set int value of pair to some value i
	 * @param i The value to set
	 */
	public void setRight(Integer i) { right = i; }

	/*@Override
	public int hashCode() { return left.hashCode() ^ right.hashCode(); }
	*/
	
	/**
	 * {@inheritDoc}
	 * Compares whether this pair is equal to pair o
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Pair)) return false;
	    Pair pairo = (Pair) o;
	    return this.left.equals(pairo.getLeft()) &&
	           this.right.equals(pairo.getRight());
	}
	/**
	 * Compares the string value of a pair with a string input and returns true if they are equal
	 * @param s The string input value
	 * @return boolean
	 */
	public boolean leftEquals(String s) {
		//return this.left.equalsignorecase(s);
		temp = this.getLeft();
		  	if(temp.equalsIgnoreCase(s)) {
		  		return true;
		  	}
		  	else
		  		return false;
	  }
}
