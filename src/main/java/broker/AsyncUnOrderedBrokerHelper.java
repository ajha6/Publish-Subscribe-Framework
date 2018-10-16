package broker;

import java.util.LinkedList;

import subscriber.Subscriber;

public class AsyncUnOrderedBrokerHelper<T> implements Runnable {

	

	//public synchronized void onEvent(T item)	{
	//	LinkedList<Subscriber> subscribers = AsyncUnorderedDispatchBroker1.getInstance().getSubscribied();
	//	for(Subscriber s : subscribers)	{
	//
	//			s.onEvent(item);
	//		}
	//	}
	T newItem;
	LinkedList<Subscriber> subscribers;
	
	public AsyncUnOrderedBrokerHelper(T item, LinkedList<Subscriber> subscribers )	{
		this.newItem = item;
		this.subscribers = subscribers;
	}
	
	public void run()	{
		
		for(Subscriber s : this.subscribers)	{

			s.onEvent(this.newItem);
		}
		
	}
	
	
/**
	public void run()	{

		//subscriber list
		//System.out.println("run of helper");
		Object item; //= AsyncUnorderedDispatchBroker1.getInstance().getNewItem();
		//while(true)	{
			while((item = AsyncUnorderedDispatchBroker1.getInstance().getDispatcher().poll(1000)) != null) {
			//if((item = AsyncUnorderedDispatchBroker1.getInstance().getNewItem()) != null)	{
				
				LinkedList<Subscriber> subscribers = AsyncUnorderedDispatchBroker1.getInstance().getSubscribied();
				for(Subscriber s : subscribers)	{

					s.onEvent(item);
				}
				//AsyncUnorderedDispatchBroker1.getInstance().setNewItem(null);
				
			}
		//}

		//AsyncUnorderedDispatchBroker1.getInstance().shutdown();



	} 
	**/


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
