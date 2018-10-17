/**
 * 
 */
package broker;

import java.util.LinkedList;

import item.Reviews;
import subscriber.Subscriber;
import subscriber.Subscribers1;

/**
 * @author anuragjha
 *
 */
public class SynchronousBroker<T> implements Broker<T> { 

	private static SynchronousBroker INSTANCE;

	private LinkedList<Subscriber> subscribied;

	//private CircularBlockingQueue<T> dispatcher = new CircularBlockingQueue<T>(1);

	private int recordCounter;
	//private boolean isReadComplete = false;

	//constructor
	private SynchronousBroker()	{
		this.subscribied = new LinkedList<Subscriber>();
		this.recordCounter = 0;
	}

	public static synchronized SynchronousBroker getInstance()	{
		if(INSTANCE == null)	{
			INSTANCE = new SynchronousBroker<Reviews>();
		}
		return INSTANCE;
	}

	/**
	 * @param isReadComplete the isReadComplete to set
	 */
	//public void setReadComplete(boolean isReadComplete) {
	//	this.isReadComplete = isReadComplete;
	//}

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

		for(Subscriber s : this.subscribied)	{
			s.onEvent(item);
		}
		synchronized(this)	{
			this.recordCounter += 1;
		}


	}

	/**
	 * processNewRecord method implements update of Review DataStores for each new Record
	 * @param newRecord
	 */
	//	private synchronized void processNewRecord(T newRecord)	{
	//
	//		//this.dispatcher.put(newRecord);
	//		
	//		//T item = this.dispatcher.take();
	//		this.recordCounter += 1;
	//		for(Subscriber s : this.subscribied)	{
	//			s.onEvent(newRecord);
	//			//System.out.println("here");
	//		}
	//		
	//	}

	//public synchronized T takeFromDispatcher()	{
	//	return this.dispatcher.take();

	//}


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
	public void shutdown()	{

	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}
