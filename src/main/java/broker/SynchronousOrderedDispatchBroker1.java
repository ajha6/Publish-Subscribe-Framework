/**
 * 
 */
package broker;

import java.util.LinkedList;
import java.util.concurrent.SynchronousQueue;

import item.Reviews;
import subscriber.Subscriber;

/**
 * @author anuragjha
 *
 */
public class SynchronousOrderedDispatchBroker1<T> implements Broker<T>,Runnable { 

	private static SynchronousOrderedDispatchBroker1 INSTANCE;

	private LinkedList<Subscriber> subscribied = new LinkedList<Subscriber>();

	private CircularBlockingQueue<T> dispatcher = new CircularBlockingQueue<T>(1);

	//constructor
	private SynchronousOrderedDispatchBroker1()	{
	}

	public static synchronized SynchronousOrderedDispatchBroker1 getInstance()	{
		if(INSTANCE == null)	{
			INSTANCE = new SynchronousOrderedDispatchBroker1<Reviews>();
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
	private synchronized void processNewRecord(T newRecord)	{

		this.dispatcher.put(newRecord);
		//System.out.println("putting in dispatcher: "+ newRecord.toString());
		///////////////////////////////this.dispatcher.take();

		//	T item = this.dispatcher.take();
		//	System.out.println("in processNewRecord");
		//	for(Subscriber s : this.subscribied)	{
		//		System.out.println("in processNewRecord");
		//		s.onEvent(item);
		//	}

	}

	public synchronized T takeFromDispatcher()	{
		return this.dispatcher.take();

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
	public void shutdown()	{

	}

	@Override
	public void run() {
		while(true)	{
			T item = this.dispatcher.take();
			for(Subscriber s : this.subscribied)	{
				s.onEvent(item);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}



}
