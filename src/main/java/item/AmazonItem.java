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
	private volatile static int itemCount = 0; 
	//private ReentrantLock lock = new ReentrantLock();

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
	private synchronized void incrementCount()	{ //synchronized
		//lock.lock();
		AmazonItem.itemCount = AmazonItem.itemCount + 1;
		this.itemId = AmazonItem.itemCount;
		//System.out.println("itemId: " + this.itemId);
		//lock.unlock();
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
