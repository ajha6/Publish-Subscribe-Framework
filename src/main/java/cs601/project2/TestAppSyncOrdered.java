/**
 * 
 */
package cs601.project2;

import java.util.LinkedList;

import broker.AsyncOrderedDispatchBroker1;
import item.Reviews;
import publisher.AmazonPublisher1;
import subscriber.Subscribers1;

/**
 * @author anuragjha
 * Initial class to start 
 */
public class TestAppSyncOrdered {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("project2");

		long start = System.currentTimeMillis();

		String[] inputFileArray = {"reviews_Apps_for_Android_5.json",
		"reviews_Home_and_Kitchen_5.json"};


		Subscribers1 s1 = new Subscribers1("new");
		Subscribers1 s2 = new Subscribers1("old");
		//Subscribers1 s3 = new Subscribers1("new");



		//SynchronousOrderedDispatchBroker2<Reviews> broker = 
		//		SynchronousOrderedDispatchBroker2.getInstance();

		AsyncOrderedDispatchBroker1<Reviews> broker = AsyncOrderedDispatchBroker1.getInstance();
		//AsyncOrderedBrokerHelper helper = new AsyncOrderedBrokerHelper();

		//AsyncUnorderedDispatchBroker1<Reviews> broker = AsyncUnorderedDispatchBroker1.getInstance();

		//broker.initializeThreadPool();
		//System.out.println("Initial broker thread count : " + broker.threadCount());
		broker.subscribe(s1);
		broker.subscribe(s2);
		//broker.subscribe(s3);

		LinkedList<Thread> threadList = new LinkedList<>();
		for(String file : inputFileArray)	{
			//AmazonPublisher1 publisher = new AmazonPublisher1(file, broker);
			Thread t = new Thread(new AmazonPublisher1(file, broker));
			threadList.add(t);

		}
		Thread brokerThread = new Thread(broker);
		
		brokerThread.start();
		
		
		for(Thread t: threadList) {
			t.start();
		}
		
		//helperThread1.start();
		//helperThread2.start();
		//System.out.println("helper thread started: "+ helperThread.getName());
		
		
		System.out.println(" thread count : " + Thread.activeCount());
		
		for(Thread t: threadList) {
			try {
				System.out.println("waiting for Publishers to finish: ");
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
			System.out.println("waiting for broker to finish");
			brokerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		

		//broker.setReadComplete(true);
		//broker.shutdown();
		//System.out.println("inter broker count : " + broker.threadCount());
		//while(!broker.isWriteComplete())	{

		//}
		//broker.shutdown();
		//while(!broker.isWriteComplete())	{
		//	try {
		//		Thread.sleep(200);
		//	} catch (InterruptedException e) {
		//		System.out.println("Write not complete");
		//	}
	//	}

		long end = System.currentTimeMillis(); //retrieve current time when finishing calculations
		System.out.println("time: " + (end-start)/1000);
		System.out.println("no of records into dispatcher: " + broker.getRecordCounter());
		System.out.println("no of records in subs1: " + s1.recordCount);
		System.out.println("no of records in subs2: " + s2.recordCount);
		//System.out.println("no of records in subs3: " + s3.recordCount);
		//System.out.println("MAx queue size was: " + broker.getMaxQueueSize());
		


	}


}