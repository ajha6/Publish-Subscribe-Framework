/**
 * 
 */
package broker;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import item.Reviews;
import subscriber.Subscriber;

/**
 * @author anuragjha
 *
 */
public class AsyncUnorderedDispatchBroker1<T> implements Broker<T>,Runnable { 

	private static AsyncUnorderedDispatchBroker1 INSTANCE;

	private LinkedList<Subscriber> subscribied = new LinkedList<Subscriber>();

	private CircularBlockingQueue<T> dispatcher = new CircularBlockingQueue<T>(100);
	/////////T newItem;

	ExecutorService helperPool;

	private int recordCounter = 0;
	private boolean isReadComplete = false;
	private boolean isWriteComplete = false;
	private int maxQueueSize = 0;

	/**
	 * @return the dispatcher
	 */
	public CircularBlockingQueue<T> getDispatcher() {
		return dispatcher;
	}

	/**
	 * @return the newItem
	 */
	//public synchronized T getNewItem() {
	//	return newItem;
	//}

	/**
	 * @param newItem the newItem to set
	 */
	//public synchronized void setNewItem(T newItem) {
	//	this.newItem = newItem;
	//}

	/**
	 * @return the subscribied
	 */
	public LinkedList<Subscriber> getSubscribied() {
		return subscribied;
	}





	//private AsyncOrderedBrokerHelper helper = new AsyncOrderedBrokerHelper();

	//constructor
	private AsyncUnorderedDispatchBroker1()	{
		//helperPool = Executors.newFixedThreadPool(5);
	}

	public static synchronized AsyncUnorderedDispatchBroker1 getInstance()	{
		if(INSTANCE == null)	{
			INSTANCE = new AsyncUnorderedDispatchBroker1<Reviews>();
		}
		return INSTANCE;
	}

	public synchronized void initializeHelperPool(int poolSize)	{
		//System.out.println("async unordered broker Initial thread count : " + Thread.activeCount());
		this.helperPool = Executors.newFixedThreadPool(poolSize);
		//for(int i = 1; i <= poolSize; i++)	{
		//	helperPool.execute(new AsyncUnOrderedBrokerHelper());
		//}
		System.out.println("async unordered broker Initial thread count : " + Thread.activeCount());

	}


	/**
	 * @return the recordCounter
	 */
	public int getRecordCounter() {
		return recordCounter;
	}

	public int getMaxQueueSize()	{
		return this.maxQueueSize;
	}


	/**
	 * @param isReadComplete the isReadComplete to set
	 */
	public void setReadComplete(boolean isReadComplete) {
		this.isReadComplete = isReadComplete;
	}


	/**
	 * @return the isWriteComplete
	 */
	public boolean isWriteComplete() {
		return isWriteComplete;
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
		//processNewRecord(item);

		//System.out.println("async unordered broker current thread : " + Thread.currentThread());
		//////this.dispatcher.put(item);
		//this.newItem = item;
		this.recordCounter += 1;
		helperPool.execute(new AsyncUnOrderedBrokerHelper(item, this.subscribied));

		// threadpool.execute(helperThread);



	}
	/**
	public synchronized void publish(T item)	{
		//System.out.println("record: " + item);
		//System.out.println("t: " + Thread.currentThread() + "\n");
		//processNewRecord(item);

		//System.out.println("async unordered broker current thread : " + Thread.currentThread());
		this.dispatcher.put(item);
		//this.newItem = item;
		this.recordCounter += 1;
		helperPool.execute(new AsyncUnOrderedBrokerHelper());

		// threadpool.execute(helperThread);



	}
**/

	/**
	 * 
	 * @param
	 */
	public int threadCount()	{
		return Thread.activeCount();

	}


	/**
	 * Called once by each subscriber. Subscriber will be 
	 * registered and receive notification of all future
	 * published items.
	 * 
	 * @param subscriber
	 */
	public synchronized void subscribe(Subscriber<T> subscriber)	{
		this.subscribied.add(subscriber);
	}


	/**
	 * Indicates this broker should stop accepting new
	 * items to be published and shut down all threads.
	 * The method will block until all items that have been
	 * published have been delivered to all subscribers.
	 */
	public synchronized void shutdown()	{
		System.out.println("shutting down");
		System.out.println(Thread.activeCount());

		this.helperPool.shutdown();

		//if(this.helperPool.isTerminated())	{
			try {
				while(!this.helperPool.awaitTermination(2, TimeUnit.MINUTES))	{
					System.out.println("awaiting termination");
				}
			} catch (InterruptedException e) {
				System.out.println("Error in closing helper pool");
			}
		//}
		this.isWriteComplete = true;
	}


	@Override
	public void run() {
		//System.out.println("in async unordered run method");

		this.initializeHelperPool(500);

		//try {
		//	this.wait();
		//}catch(InterruptedException ie)	{
		//	ie.printStackTrace();
		//}



	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}



}
