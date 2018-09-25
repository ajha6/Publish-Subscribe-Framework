/**
 * 
 */
package item;

/**
 * @author anuragjha
 *
 */
public abstract class AmazonItem implements Item {

	protected long itemId;
	private volatile static long itemCount = 0; //should i synchronize this variable ??? 

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
	private synchronized void incrementCount()	{
		AmazonItem.itemCount += 1;
		this.itemId = itemCount;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
