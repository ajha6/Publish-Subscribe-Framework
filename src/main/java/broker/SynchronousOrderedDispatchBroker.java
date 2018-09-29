/**
 * 
 */
package broker;

import java.util.LinkedList;

import item.Reviews;
import subscriber.Subscriber;
import subscriber.Subscribers;

/**
 * @author anuragjha
 *
 */
public class SynchronousOrderedDispatchBroker<T> implements Broker<T>,Runnable { 
	
	//have to implement singleton and then change statement in Reviews class
	private static SynchronousOrderedDispatchBroker INSTANCE;
	private LinkedList<Subscriber> subscribied = new LinkedList<Subscriber>();
	
	//private boolean ;
	
	//constructor
	private SynchronousOrderedDispatchBroker()	{
	}
	
	public static synchronized SynchronousOrderedDispatchBroker getInstance()	{
		if(INSTANCE == null)	{
			INSTANCE = new SynchronousOrderedDispatchBroker<Reviews>();
		}
		return INSTANCE;
	}
	
	
	/**
	 * Called by a publisher to publish a new item. The 
	 * item will be delivered to all current subscribers.
	 * 
	 * @param item
	 */
	public synchronized void publish(T item)	{
		//System.out.println("record: " + item);
		//System.out.println("t: " + Thread.currentThread() + "\n");
		processNewRecord(item);
		
		
	}
	
	/**
	 * processNewRecord method implements update of Review DataStores for each new Record
	 * @param newRecord
	 */
	private void processNewRecord(T newRecord)	{
		//dispatcher.put(newRecord);
		
		for(Subscriber s : this.subscribied)	{
			s.onEvent(newRecord);
		}

	}
	
	/**
	 * Called once by each subscriber. Subscriber will be 
	 * registered and receive notification of all future
	 * published items.
	 * 
	 * @param subscriber
	 */
	public void subscribe(Subscriber<T> subscriber)	{
		this.subscribied.add(subscriber);
	}
	
	/**
	 * Indicates this broker should stop accepting new
	 * items to be published and shut down all threads.
	 * The method will block until all items that have been
	 * published have been delivered to all subscribers.
	 */
	public void shutdown()	{
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}



}
