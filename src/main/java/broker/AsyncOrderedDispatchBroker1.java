package broker;

import java.util.LinkedList;

import item.Reviews;
import subscriber.Subscriber;

public class AsyncOrderedDispatchBroker1<T> implements Broker<T>,Runnable {

	private static AsyncOrderedDispatchBroker1 INSTANCE;
	
	public LinkedList<Subscriber> subscriberList = new LinkedList<Subscriber>();
	private CircularBlockingQueue<T> dispatcher = new CircularBlockingQueue<T>(1000);
	
	private AsyncOrderedBrokerHelper helper = new AsyncOrderedBrokerHelper();
	Thread helperThread;
	
	private int recordCounter = 0;
	
	//constructor
	private AsyncOrderedDispatchBroker1()	{
	}
	
	public static synchronized AsyncOrderedDispatchBroker1 getInstance()	{
		if(INSTANCE == null)	{
			INSTANCE = new AsyncOrderedDispatchBroker1<Reviews>();
		}
		return INSTANCE;
	}
	
	
	/**
	 * @return the subscribied
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
	public synchronized void publish(T item)	{
		this.dispatcher.put(item);
		this.recordCounter += 1;
	}
	
	
	/**
	 * Called once by each subscriber. Subscriber will be 
	 * registered and receive notification of all future
	 * published items.
	 * 
	 * @param subscriber
	 */
	public synchronized void subscribe(Subscriber<T> subscriber)	{
		this.subscriberList.add(subscriber);
	}
	
	
	/**
	 * Indicates this broker should stop accepting new
	 * items to be published and shut down all threads.
	 * The method will block until all items that have been
	 * published have been delivered to all subscribers.
	 */
	public synchronized void shutdown()	{
		try {
			this.helperThread.join();
		} catch(InterruptedException ie)	{
			System.out.println("helper join incomplete");
		}
	}
	
	
	public void run()	{
		
		this.helperThread = new Thread(new AsyncOrderedBrokerHelper());
		this.helperThread.start();
		

		
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Subscriber s = (Subscriber) AsyncOrderedDispatchBroker1.getInstance().getSubscriberList();
			
		
	}

}
