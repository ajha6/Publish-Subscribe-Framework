/**
 * 
 */
package item;

/**
 * @author anuragjha
 *
 */
public abstract class AmazonItem {

	protected int itemId;
	private static int itemCount = 0; //should i synchronize this variable ??? 

	/**
	 * constructor
	 */
	public AmazonItem()	{
		super();
		this.incrementCount();
	}

	/**
	 * 
	 */
	private synchronized void incrementCount()	{ //synchronized
		AmazonItem.itemCount += 1;
		this.itemId = itemCount;
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
