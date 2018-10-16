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
public class AsyncUnorderedDispatchBroker<T> implements Broker<T>,Runnable { 

	private static AsyncUnorderedDispatchBroker INSTANCE;

	private LinkedList<Subscriber> subscribied = new LinkedList<Subscriber>();

	private CircularBlockingQueue<T> dispatcher = new CircularBlockingQueue<T>(5000);

	private int recordCounter = 0;
	private boolean isReadComplete = false;
	private boolean isWriteComplete = false;
	private int maxQueueSize = 0;

	ExecutorService threadPool;
	//constructor
	private AsyncUnorderedDispatchBroker()	{
		this.initializeThreadPool();
	}

	public static synchronized AsyncUnorderedDispatchBroker getInstance()	{
		if(INSTANCE == null)	{
			INSTANCE = new AsyncUnorderedDispatchBroker<Reviews>();
		}
		return INSTANCE;
	}

	public synchronized void initializeThreadPool()	{
		threadPool = Executors.newFixedThreadPool(20);
		//return threadPool;
		threadPool.execute(this);
		//System.out.println("async unordered broker Initial thread count : " + Thread.activeCount());
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
		processNewRecord(item);


	}

	/**
	 * processNewRecord method implements update of Review dispatcher for each new Record
	 * @param newRecord
	 */
	private synchronized void processNewRecord(T newRecord)	{

		//System.out.println("async unordered broker current thread : " + Thread.currentThread());
		
		int newSize = this.dispatcher.getSize();
		if(this.maxQueueSize < newSize)	{
			this.maxQueueSize = newSize;
		}
		this.recordCounter += 1;
		this.dispatcher.put(newRecord);
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


		
		if(this.threadPool.isTerminated())	{
			try {
				while(!this.threadPool.awaitTermination(2, TimeUnit.MINUTES))	{
					System.out.println("awaiting termination");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.isWriteComplete = true;
	}


	@Override
	public void run() {
		System.out.println("in async unordered run method");
		while(!this.isReadComplete)	{

			//T item1 = this.takeFromDispatcher();
			//	if(item1 == null)	{
			//		threadPool.shutdown();
			//	}
			//synchronized()	{
			//T item1 = this.dispatcher.take();

			//if(!this.dispatcher.isEmpty())	{
			while((!this.dispatcher.isEmpty())) 	{
				T item1 = this.dispatcher.poll(200);
				for(Subscriber s : this.subscribied)	{
					s.onEvent(item1);
					//s.onEvent(newRecord);
				}
			} 
			//}
		}
		this.threadPool.shutdown();
		while((!this.dispatcher.isEmpty())) 	{
			T item1 = this.dispatcher.poll(200);
			for(Subscriber s : this.subscribied)	{
				s.onEvent(item1);
			}
		}
		this.shutdown();
		
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
