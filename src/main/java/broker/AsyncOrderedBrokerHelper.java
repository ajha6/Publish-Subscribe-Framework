package broker;

import java.util.concurrent.ConcurrentLinkedQueue;

import subscriber.Subscriber;

/**
 * @author anuragjha
 *
 * AsyncOrderedBrokerHelper class implements a helper for Asynchronous broker 
 */
public class AsyncOrderedBrokerHelper implements Runnable {

	private AsyncOrderedBroker broker;


	public AsyncOrderedBrokerHelper(AsyncOrderedBroker broker) {
		this.broker = broker;
	}

	/**
	 * run method takes the item from the Broker queue and distributes it to all subscribers
	 */
	//	public void run() {
	//
	//		//System.out.println("run of helper");
	//		Object item;
	//		ConcurrentLinkedQueue<Subscriber> subscribers = broker.getSubscriberList();
	//		while((item = broker.getDispatcher().poll(20)) != null) {
	//			for(Subscriber s : subscribers)	{
	//				s.onEvent(item);
	//			}
	//		}
	//
	//	}


	public void run() {
		Object item = null;
		while(((item = broker.getDispatcher().poll(20)) != null) || broker.isShoulRun()) {
			
			ConcurrentLinkedQueue<Subscriber> subscribers = broker.getSubscriberList();
			//if(item != null) {	
				for(Subscriber s : subscribers)	{
					s.onEvent(item);
				}
			//}
		}

	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
