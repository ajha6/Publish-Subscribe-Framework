/**
 * 
 */
package cs601.project2;

import java.util.LinkedList;

import broker.AsyncOrderedDispatchBroker;
import item.Reviews;
import publisher.AmazonPublisher1;
import subscriber.Subscribers1;

/**
 * @author anuragjha
 * Initial class to start 
 */
public class AppTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("project2");
		long start = System.currentTimeMillis();


		String[] inputFileArray = {"reviews_Apps_for_Android_5_copy.json",
		"reviews_Home_and_Kitchen_5_copy.json"};

		
		Subscribers1 s1 = new Subscribers1("new");
		
		Subscribers1 s2 = new Subscribers1("old");
		
		Subscribers1 s3 = new Subscribers1("new");
		

		
		//SynchronousOrderedDispatchBroker2<Reviews> broker = 
		//		SynchronousOrderedDispatchBroker2.getInstance();
		
		AsyncOrderedDispatchBroker<Reviews> broker = AsyncOrderedDispatchBroker.getInstance();
		
		//AsyncUnorderedDispatchBroker<Reviews> broker = AsyncUnorderedDispatchBroker.getInstance();
		
		
		broker.subscribe(s1);
		broker.subscribe(s2);
		broker.subscribe(s3);
		
		LinkedList<Thread> threadList = new LinkedList<>();
		for(String file : inputFileArray)	{
			//AmazonPublisher1 publisher = new AmazonPublisher1(file, broker);
			Thread t = new Thread(new AmazonPublisher1(file, broker));
			threadList.add(t);
			
		}
		
		for(Thread t: threadList) {
			t.start();
		}
		System.out.println("Initial thread count : " + Thread.activeCount());
		for(Thread t: threadList) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		broker.shutdown();
		
		
		long end = System.currentTimeMillis(); //retrieve current time when finishing calculations
		System.out.println("time: " + (end-start)/1000);
		System.out.println("no of records into dispatcher: " + broker.getRecordCounter());
		System.out.println("no of records in subs1: " + s1.recordCount);
		System.out.println("no of records in subs2: " + s2.recordCount);
		System.out.println("no of records in subs3: " + s3.recordCount);
		


	}


}