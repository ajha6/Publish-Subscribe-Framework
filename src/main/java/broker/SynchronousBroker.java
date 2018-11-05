/**
 * 
 */
package broker;

import java.util.LinkedList;

import subscriber.Subscriber;

/**
 * @author anuragjha
 *
 */
public class SynchronousBroker<T> implements Broker<T> { 

	private LinkedList<Subscriber<T>> subscribied;

	private int recordCounter;

	//constructor
	public SynchronousBroker() {
		this.subscribied = new LinkedList<Subscriber<T>>();
		this.recordCounter = 0;
	}

	/**
	 * @return the recordCounter
	 */
	public int getRecordCounter() {
		return recordCounter;
	}

	/**
	 * Called by a publisher to publish a new item. The 
	 * item will be delivered to all current subscribers.
	 * 
	 * @param item
	 */
	public void publish(T item)	{

		for(Subscriber<T> s : this.subscribied)	{
			s.onEvent(item);
		}
		synchronized(this)	{
			this.recordCounter += 1;
		}


	}


	/**
	 * Called once by each subscriber. Subscriber will be 
	 * registered and receive notification of all future
	 * published items.
	 * 
	 * @param subscriber
	 */
	public void subscribe(Subscriber<T> subs) {
		this.subscribied.add(subs);

	}


	/**
	 * Indicates this broker should stop accepting new
	 * items to be published and shut down all threads.
	 * The method will block until all items that have been
	 * published have been delivered to all subscribers.
	 */
	public void shutdown() {

	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}
