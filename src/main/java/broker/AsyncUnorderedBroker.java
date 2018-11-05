/**
 * 
 */
package broker;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import subscriber.Subscriber;

/**
 * @author anuragjha
 * AsyncUnorderedBroker class implements Asynchronous unordered Broker 
 * @param <T>
 */
public class AsyncUnorderedBroker<T> implements Broker<T>,Runnable { 
	
	private ConcurrentLinkedQueue<Subscriber<T>> subscriberList; 

	private ExecutorService helperPool;
	private int poolSize;

	private int recordCounter;

	//constructor
	public AsyncUnorderedBroker(int poolSize) {
		this.subscriberList = new ConcurrentLinkedQueue<Subscriber<T>>();
		this.recordCounter = 0;
		this.poolSize = poolSize;
	}

	/**
	 * @return the SubscriberList
	 */
	public ConcurrentLinkedQueue<Subscriber<T>> SubscriberList() {
	
		return subscriberList;
	}


	/**
	 * initializeHelperPool method creates a threadpool of helpers
	 * @param poolSize
	 */
	public void initializeHelperPool(int poolSize) {
		this.helperPool = Executors.newFixedThreadPool(poolSize);

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
		helperPool.execute(new AsyncUnOrderedBrokerHelper(item, this.subscriberList));
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
	public void subscribe(Subscriber<T> subscriber)	{
		this.subscriberList.add(subscriber);
	}


	/**
	 * Indicates this broker should stop accepting new
	 * items to be published and shut down all threads.
	 * The method will block until all items that have been
	 * published have been delivered to all subscribers.
	 */
	public void shutdown() {
		System.out.println("shutting down Async Unordered Broker");

		this.helperPool.shutdown();

		try {
			while(!this.helperPool.awaitTermination(10, TimeUnit.SECONDS))	{
				System.out.println("awaiting termination");
			}
		} catch (InterruptedException e) {
			System.out.println("Error in closing helper pool");
		}
	}


	@Override
	public void run() {

		this.initializeHelperPool(this.poolSize);

	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}



}
