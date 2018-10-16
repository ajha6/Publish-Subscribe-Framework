/**
 * 
 */
package cs601.project2;

import java.util.LinkedList;
import java.util.logging.Level;

import broker.AsyncUnorderedBroker;
import item.Reviews;
import publisher.AmazonPublisher1;
import subscriber.Subscribers1;

/**
 * @author anuragjha
 * Initial class to start 
 */
public class TestAppAsyncUnordered {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Project2 - Asynchronous Unordered Broker Test App");
		//reading configuration file content into Project2Init object
		Project2Init init = Project2InitReader.project2InitjsonReader();

		//initializing logger 
		Project2Logger.initialize("Asynchronous Unordered Broker Test App", init.getLoggerFile());

		long start = System.currentTimeMillis();

		//String[] inputFileArray = {"reviews_Apps_for_Android_5.json",
		//"reviews_Home_and_Kitchen_5.json"};

		Subscribers1 s1 = new Subscribers1("new");
		Subscribers1 s2 = new Subscribers1("old");

		AsyncUnorderedBroker<Reviews> broker = AsyncUnorderedBroker.getInstance();

		//registering subscribers
		broker.subscribe(s1);
		broker.subscribe(s2);

		//creating AmazonPublisher1 thread for each input file
		LinkedList<Thread> publisherThreadList = new LinkedList<>();
		for(String file : init.getInputFiles())	{
			Thread publisherThread = new Thread(new AmazonPublisher1(file, broker));
			publisherThreadList.add(publisherThread);
		}
		
		//creating broker thread
		Thread brokerThread = new Thread(broker);
		
		//starting broker thread
		brokerThread.start();
		
		//starting publisher threads 
		for(Thread publisherThread: publisherThreadList) {
			publisherThread.start();
		}
		
		
		
		//waiting for publisher threads to complete
		for(Thread publisherThread: publisherThreadList) {
			try {
				
				publisherThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Publisher threads finished.");
		Project2Logger.write(Level.INFO, "Publisher threads finished.", 0);
		
		//shutting down broker
		broker.shutdown();
				
		try {
			
			brokerThread.join();
		} catch (InterruptedException ie) {
			System.out.println("Error in closing broker");
		}
		System.out.println("Broker thread finished.");
		Project2Logger.write(Level.INFO, "Broker thread finished.", 0);
		
		//closing subscriber resources
		s1.closeWriter();
		s2.closeWriter();

		long end = System.currentTimeMillis(); //retrieve current time when finishing calculations
		System.out.println("Time taken: " + (end-start)/1000 + " seconds");
		Project2Logger.write(Level.INFO, "Time taken: " + (end-start)/1000 + " seconds", 0);
		//System.out.println("No of records read: " + broker.getRecordCounter());
		Project2Logger.write(Level.INFO, "No of records read: " + broker.getRecordCounter(), 0);
		//System.out.println("Total records in subs1: " + s1.recordCount);
		Project2Logger.write(Level.INFO, "Total records in subs1: " + s1.recordCount, 0);
		//System.out.println("Total records in subs2: " + s2.recordCount);
		Project2Logger.write(Level.INFO, "Total records in subs2: " + s2.recordCount, 0);
		
		//closing logger
		Project2Logger.close();


	}


}