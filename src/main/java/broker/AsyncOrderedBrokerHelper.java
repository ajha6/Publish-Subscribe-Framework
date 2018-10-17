package broker;

import java.util.LinkedList;

import subscriber.Subscriber;

/**
 * @author anuragjha
 *
 * AsyncOrderedBrokerHelper class implements a helper for Asynchronous broker 
 */
public class AsyncOrderedBrokerHelper implements Runnable {

	
	public AsyncOrderedBrokerHelper()	{
	}
	
	/**
	 * run method takes the item from the Broker queue and distributes it to all subscribers
	 */
	public void run()	{
		
		//System.out.println("run of helper");
		Object item;
		LinkedList<Subscriber> subscribers = AsyncOrderedBroker.getInstance().getSubscriberList();
		while((item = AsyncOrderedBroker.getInstance().getDispatcher().poll(100)) != null) {
			
			for(Subscriber s : subscribers)	{
				
				s.onEvent(item);
			}
		}
		
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
