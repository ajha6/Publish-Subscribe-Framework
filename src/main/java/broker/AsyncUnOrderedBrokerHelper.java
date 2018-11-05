package broker;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import subscriber.Subscriber;

public class AsyncUnOrderedBrokerHelper<T> implements Runnable {

	private T newItem;
	private ConcurrentLinkedQueue<Subscriber<T>> subscribers;
	
	public AsyncUnOrderedBrokerHelper(T item, ConcurrentLinkedQueue<Subscriber<T>> subscribers ) {
		this.newItem = item;
		this.subscribers = subscribers;
	}
	
	public void run() {
		
		for(Subscriber s : this.subscribers) {

			s.onEvent(this.newItem);
		}
		
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
