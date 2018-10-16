/**
 * 
 */
package item;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author anuragjha
 *
 */
public abstract class AmazonItem {

	protected int itemId;
	private volatile static int itemCount = 0; 
	private ReentrantLock lock = new ReentrantLock();

	/**
	 * constructor
	 */
	public AmazonItem()	{
		super();

			this.incrementCount();
	}

	
	//private synchronized int getItemCount()	{
		
	//	return itemCount;
		
	//}
	
	
	/**
	 * 
	 */
	private synchronized void incrementCount()	{ //synchronized
		lock.lock();
		AmazonItem.itemCount = AmazonItem.itemCount + 1;
		this.itemId = AmazonItem.itemCount;
		//System.out.println("itemId: " + this.itemId);
		lock.unlock();
	}

	/**
	 * sends the new AmazonObject record to DataStore
	 */
	//public abstract void notifyBroker();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
