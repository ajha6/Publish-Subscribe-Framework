package broker;

import java.util.LinkedList;

import item.Reviews;
import subscriber.Subscriber;

/**
 * @author anuragjha
 * AsyncOrderedBroker class implements Asynchronous ordered broker
 * @param <T>
 */
public class AsyncOrderedBroker<T> implements Broker<T>,Runnable {

//	private static AsyncOrderedBroker INSTANCE;

	public LinkedList<Subscriber> subscriberList; 
	private CircularBlockingQueue<T> dispatcher; 

//	private AsyncOrderedBrokerHelper helper; 
	private Thread helperThread;

	private int recordCounter; 

	/**
	 * constructor - initializes the resources needed
	 */
	public AsyncOrderedBroker(int queueSize)	{
		this.subscriberList = new LinkedList<Subscriber>();
		this.dispatcher = new CircularBlockingQueue<T>(queueSize);
		this.recordCounter = 0;	
//		this.helper = new AsyncOrderedBrokerHelper();
	}


//	public static synchronized AsyncOrderedBroker getInstance()	{
//		return INSTANCE;
//	}


//	public static synchronized AsyncOrderedBroker getInstance(int queueSize)	{
//		if(INSTANCE == null)	{
//
//			INSTANCE = new AsyncOrderedBroker<Reviews>(queueSize);
//		}
//		return INSTANCE;
//	}

	/**
	 * @return the subscriberList
	 */
	public LinkedList<Subscriber> getSubscriberList() {
		return subscriberList;
	}


	/**
	 * @return the dispatcher
	 */
	public CircularBlockingQueue<T> getDispatcher() {
		return dispatcher;
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
		this.dispatcher.put(item);
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
	public void shutdown()	{
		System.out.println("shutting down Async Ordered Broker");
		try	{
			this.helperThread.join();
		}catch(InterruptedException ie)	{
			System.out.println("Error in closing helper thread");
		}
	}

	/**
	 * run method creates and starts new helper thread
	 */
	public void run()	{

		this.helperThread = new Thread(new AsyncOrderedBrokerHelper(this));
		this.helperThread.start();


	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Subscriber s = (Subscriber) AsyncOrderedDispatchBroker1.getInstance().getSubscriberList();


	}

}
