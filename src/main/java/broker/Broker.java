/**
 * 
 */
package broker;

import subscriber.Subscriber;

/**
 * @author anuragjha
 *
 */
public interface Broker<T> {
	
	
	//public boolean isShoulRun();
	//public void setShoulRun(boolean shoulRun);
	
	
	/**
	 * Called by a publisher to publish a new item. The 
	 * item will be delivered to all current subscribers.
	 * 
	 * @param item
	 */
	public void publish(T item);
	
	/**
	 * Called once by each subscriber. Subscriber will be 
	 * registered and receive notification of all future
	 * published items.
	 * 
	 * @param subscriber
	 */
	public void subscribe(Subscriber<T> subscriber);
	
	
	/**
	 * Indicates this broker should stop accepting new
	 * items to be published and shut down all threads.
	 * The method will block until all items that have been
	 * published have been delivered to all subscribers.
	 */
	public void shutdown();
	
	/**
	 * method to return the number of records read by Broker
	 * @return
	 */
	public int getRecordCounter();
}
