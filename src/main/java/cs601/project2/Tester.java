/**
 * 
 */
package cs601.project2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import broker.AsyncOrderedDispatchBroker;
import item.Reviews;
import publisher.AmazonPublisher;
import subscriber.Subscribers;

/**
 * @author anuragjha
 * Initial class to start 
 */
public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("project2");
		long start = System.currentTimeMillis();


		ExecutorService threadPool = Executors.newFixedThreadPool(2);
		Subscribers s1 = new Subscribers("new");
		Subscribers s2 = new Subscribers("old"); 
		//Subscribers s3 = new Subscribers("new");
		//threadPool.execute(s1);
		//threadPool.execute(s2);

		//SynchronousOrderedDispatchBroker<Reviews> broker = 
		//		SynchronousOrderedDispatchBroker.getInstance();

		AsyncOrderedDispatchBroker<Reviews> broker = 
				AsyncOrderedDispatchBroker.getInstance();

		//broker.subscribe(s3);
		broker.subscribe(s1);
		broker.subscribe(s2);


		String[] inputFileArray = {"reviews_Apps_for_Android_5_copy.json",
		"reviews_Home_and_Kitchen_5_copy.json"};
		//String[] inputFileArray = Project2Init.getInputFiles();


		for(String file : inputFileArray)	{
			threadPool.execute(new AmazonPublisher(file, broker));
			//new AmazonPublisher(file, broker);
		}


		threadPool.shutdown();

		try {
			while(!threadPool.awaitTermination(2, TimeUnit.MINUTES))	{
				System.out.println("awaiting termination");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		long end = System.currentTimeMillis(); //retrieve current time when finishing calculations
		System.out.println("time: " + (end-start)/1000);
		System.out.println("no of records in subs1: " + s1.recordCount);
		System.out.println("no of records in subs2: " + s2.recordCount);


	}


}
