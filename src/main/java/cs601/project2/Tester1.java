/**
 * 
 */
package cs601.project2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import broker.SynchronousOrderedDispatchBroker1;
import item.Reviews;
import publisher.AmazonPublisher1;
import subscriber.Subscribers1;

/**
 * @author anuragjha
 * Initial class to start 
 */
public class Tester1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("project2");
		long start = System.currentTimeMillis();


		ExecutorService threadPool = Executors.newFixedThreadPool(2);
		Subscribers1 s1 = new Subscribers1("new");
		Subscribers1 s2 = new Subscribers1("old"); 
		//Subscribers s3 = new Subscribers("new");
		//threadPool.execute(s1);
		//threadPool.execute(s2);

		SynchronousOrderedDispatchBroker1<Reviews> broker = 
				SynchronousOrderedDispatchBroker1.getInstance();

		//AsyncOrderedDispatchBroker<Reviews> broker = 
		//		AsyncOrderedDispatchBroker.getInstance();

		//broker.subscribe(s3);
		broker.subscribe(s1);
		broker.subscribe(s2);


		String[] inputFileArray = {"reviews_Apps_for_Android_5_copy.json",
		"reviews_Home_and_Kitchen_5_copy.json"};
		//String[] inputFileArray = Project2Init.getInputFiles();


		for(String file : inputFileArray)	{
			threadPool.execute(new AmazonPublisher1(file, broker));
			//new AmazonPublisher(file, broker);
		}
		//threadPool.execute(s1);
		//threadPool.execute(s2);

		//threadPool.shutdown();

		//try {
		//	while(!threadPool.awaitTermination(2, TimeUnit.MINUTES))	{
		//		System.out.println("awaiting termination");
		//	}
		//} catch (InterruptedException e) {
		//	e.printStackTrace();
		//}

		long end = System.currentTimeMillis(); //retrieve current time when finishing calculations
		System.out.println("time: " + (end-start)/1000);
		System.out.println("no of records in subs1: " + s1.recordCount);
		System.out.println("no of records in subs2: " + s2.recordCount);


	}


}
