/**
 * 
 */
package item;

import java.util.concurrent.locks.ReentrantLock;

/**
 * AmazonItem is base class for Reviews class
 * @author anuragjha
 */
public abstract class AmazonItem {

	protected int itemId;
	private transient volatile static int itemCount = 0;

	/**
	 * constructor
	 */
	public AmazonItem()	{
		super();
		this.incrementCount();
	}


	/**
	 * incrementCount method increments the recordCounter and assigns the value to itemId
	 */
	private synchronized void incrementCount()	{
		
		itemCount = itemCount + 1;
		this.itemId = itemCount;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
