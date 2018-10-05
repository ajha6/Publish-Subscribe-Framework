/**
 * 
 */
package broker;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import item.Reviews;
import publisher.AmazonPublisher;
import subscriber.Subscriber;

/**
 * @author anuragjha
 *
 */
public class AsyncOrderedDispatchBroker<T> implements Broker<T>,Runnable { 

	private static AsyncOrderedDispatchBroker INSTANCE;

	private LinkedList<Subscriber> subscribied = new LinkedList<Subscriber>();

	private CircularBlockingQueue<T> dispatcher = new CircularBlockingQueue<T>(1);

	private int recordCounter = 0;

	ExecutorService threadPool;
	//constructor
	private AsyncOrderedDispatchBroker()	{
		threadPool = Executors.newFixedThreadPool(1);
		threadPool.execute(this);
	}

	public static synchronized AsyncOrderedDispatchBroker getInstance()	{
		if(INSTANCE == null)	{
			INSTANCE = new AsyncOrderedDispatchBroker<Reviews>();
		}
		return INSTANCE;
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
	public synchronized void publish(T item)	{
		//System.out.println("record: " + item);
		//System.out.println("t: " + Thread.currentThread() + "\n");
		processNewRecord(item);


	}

	/**
	 * processNewRecord method implements update of Review dispatcher for each new Record
	 * @param newRecord
	 */
	private synchronized void processNewRecord(T newRecord)	{

		this.dispatcher.put(newRecord);
		this.recordCounter += 1;
		//T item1 = this.dispatcher.take();
		//T item1 = this.dispatcher.take();
		//System.out.println("record: " + ((Reviews)item1).getItemId());
		//System.out.println("t: " + Thread.currentThread());
		//for(Subscriber s : this.subscribied)	{
		//	s.onEvent(item1);
		//s.onEvent(newRecord);
		//}

	}

	/**
	 * processNewRecord method implements update of Review DataStores for each new Record
	 * @param newRecord
	 */
	public synchronized T takeFromDispatcher()	{
		//return dispatcher.take();
		return dispatcher.take();

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
		threadPool.shutdown();
		
		try {
			while(!threadPool.awaitTermination(1, TimeUnit.MINUTES))	{
				System.out.println("awaiting termination");
			}
		} catch (InterruptedException e) {
			System.out.println("interrupted Exception");
			threadPool.shutdownNow();
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		while(true)	{

			//T item1 = this.takeFromDispatcher();
			//	if(item1 == null)	{
			//		threadPool.shutdown();
			//	}
			//synchronized()	{
			//T item1 = this.dispatcher.take();
			T item1 = this.dispatcher.poll();
			
				for(Subscriber s : this.subscribied)	{
					s.onEvent(item1);
					//s.onEvent(newRecord);
				}
			
			//}
		}
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}



}
