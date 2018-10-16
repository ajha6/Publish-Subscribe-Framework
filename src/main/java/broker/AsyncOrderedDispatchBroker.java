/**
 * 
 */
package broker;

import java.lang.management.ThreadInfo;
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
public class AsyncOrderedDispatchBroker<T> implements Broker<T>,Runnable { 

	private static AsyncOrderedDispatchBroker INSTANCE;

	private LinkedList<Subscriber> subscribied = new LinkedList<Subscriber>();

	private CircularBlockingQueue<T> dispatcher = new CircularBlockingQueue<T>(1000);

	private int recordCounter = 0;
	private boolean isReadComplete = false;
	private boolean isWriteComplete = false;

	ExecutorService threadPool;
	//constructor
	private AsyncOrderedDispatchBroker()	{
		this.initializeThreadPool();
	}

	public static synchronized AsyncOrderedDispatchBroker getInstance()	{
		if(INSTANCE == null)	{
			INSTANCE = new AsyncOrderedDispatchBroker<Reviews>();
		}
		return INSTANCE;
	}


	public synchronized void initializeThreadPool()	{
		this.threadPool = Executors.newFixedThreadPool(1);
		this.threadPool.execute(this);
		System.out.println("async ordered broker Initial thread count : " + Thread.activeCount());
	}

	/**
	 * @return the recordCounter
	 */
	public int getRecordCounter() {
		return recordCounter;
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
		processNewRecord(item);


	}

	/**
	 * processNewRecord method implements update of Review dispatcher for each new Record
	 * @param newRecord
	 */
	private synchronized void processNewRecord(T newRecord)	{
		//System.out.println(Thread.activeCount());
		this.dispatcher.put(newRecord);
		this.recordCounter += 1;

	}

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
		System.out.println("async ordered broker shutting down");



		if(this.threadPool.isTerminated())	{
			try {
				while(!this.threadPool.awaitTermination(2, TimeUnit.MINUTES))	{
					System.out.println("awaiting termination");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void run() {
		while(!this.isReadComplete)	{

			//T item1 = this.takeFromDispatcher();
			//	if(item1 == null)	{
			//		threadPool.shutdown();
			//	}
			//synchronized()	{
			//T item1 = this.dispatcher.take();

			//if(!this.dispatcher.isEmpty())	{
			
			while((!this.dispatcher.isEmpty())) 	{
				T item1 = this.dispatcher.take();
				for(Subscriber s : this.subscribied)	{
					s.onEvent(item1);
					//s.onEvent(newRecord);
				}
			} 
			//}
		}
		this.threadPool.shutdown();
		//while((!this.dispatcher.isEmpty())) 	{
		T item1;
		while((item1 = this.dispatcher.poll(200)) != null)	{
			//T item1 = this.dispatcher.poll(200);
			for(Subscriber s : this.subscribied)	{
				s.onEvent(item1);
			}
		}
		this.shutdown();
		this.isWriteComplete = true;
		//while(!this.threadPool.isTerminated())	{
		//	System.out.println("awaiting thread to complete its task");
		//	System.out.println("current thread: " + Thread.currentThread().getName());
		//}
		//this.shutdown();
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}



}
