/**
 * 
 */
package broker;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import item.Reviews;
import subscriber.Subscriber;

/**
 * @author anuragjha
 *
 */
public class AsyncOrderedDispatchBroker<T> implements Broker<T>,Runnable { 

	//have to implement singleton and then change statement in Reviews class
	private static AsyncOrderedDispatchBroker INSTANCE;
	private CircularBlockingQueue<T> dispatcher; 
	private LinkedList<Subscriber> subscribied; 

	ExecutorService threadPool;

	//constructor
	private AsyncOrderedDispatchBroker()	{
		this.dispatcher = new CircularBlockingQueue<T>(500000);
		this.subscribied = new LinkedList<Subscriber>();
		//this.threadPool = Executors.newFixedThreadPool(8);
	}

	public static synchronized AsyncOrderedDispatchBroker getInstance()	{
		if(INSTANCE == null)	{
			INSTANCE = new AsyncOrderedDispatchBroker<Reviews>();
		}
		return INSTANCE;
	}

	
	
	/**
	 * @return the dispatcher
	 */
	public synchronized CircularBlockingQueue<T> getDispatcher() {
		return dispatcher;
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

		this.dispatcher.put(newRecord);

		//for(Subscriber s : this.subscribied)	{
		//	s.onEvent(newRecord);
		//}
		

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


	public void distribute()	{
		T newRecord = this.dispatcher.take();
		for(Subscriber s : this.subscribied)	{
			s.onEvent(newRecord);
		}
	}

	@Override
	public void run() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}



}
