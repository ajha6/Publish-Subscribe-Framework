/**
 * 
 */
package cs601.project2;

import java.io.File;
import java.util.LinkedList;
import java.util.logging.Level;

import broker.AsyncOrderedBroker;
import broker.AsyncUnorderedBroker;
import broker.Broker;
import broker.SynchronousBroker;
import publisher.AmazonPublisher1;
import subscriber.Subscribers1;

/**
 * @author anuragjha
 * Broker Test app - Initial class to start 
 */
public class TestApp {

	private static Project2Init init;

	public TestApp() {
	}

	private void initializeLogger()	{
		Project2Logger.initialize("Broker Test App - " + init.getBrokerType(), init.getLoggerFile());
		Project2Logger.write(Level.INFO, ""+ init.toString(), 0);
	}

	private Subscribers1[] createSubscriberNew()	{
		Subscribers1[] subscribersNew = new Subscribers1[init.getSubscribersNew().length];
		for(int i = 1; i <= init.getSubscribersNew().length; i++)	{
			subscribersNew[i-1]  = new Subscribers1("new", init.getSubscribersNew()[i-1]);
		}
		return subscribersNew;
	}

	private Subscribers1[] createSubscriberOld()	{
		Subscribers1[] subscribersOld = new Subscribers1[init.getSubscribersOld().length];
		for(int i = 1; i <= init.getSubscribersOld().length; i++)	{
			subscribersOld[i-1] = new Subscribers1("old", init.getSubscribersOld()[i-1]);
		}
		return subscribersOld;
	}

	private Broker initializeBroker()	{
		Broker broker = null;
		switch(init.getBrokerType()) 	{		
		case "sync" : 
//			broker = SynchronousBroker.getInstance();
			broker = new SynchronousBroker();
			return broker;
		case "asyncOrdered" :
			int queueSize = init.getQueueSize();
//			broker = AsyncOrderedBroker.getInstance(queueSize);
			broker = new AsyncOrderedBroker(queueSize);
			Project2Logger.write(Level.INFO, "QueueSize: "+ init.getQueueSize() + "\n", 0);
			return broker;
		case "asyncUnordered" :
			int poolSize = init.getPoolSize();
//			broker = AsyncUnorderedBroker.getInstance(poolSize);
			broker = new AsyncUnorderedBroker(poolSize);
			Project2Logger.write(Level.INFO, "PoolSize: "+ init.getPoolSize() + "\n", 0);
			return broker;
		default :
			System.out.println("broker not found, exiting app.");
			return null;
		}
	}

	private void addSubscribers(Broker broker, Subscribers1[] subscribersNew, Subscribers1[] subscribersOld)	{
		for(Subscribers1 subs : subscribersNew) {
			broker.subscribe(subs);
		}
		for(Subscribers1 subs : subscribersOld) {
			broker.subscribe(subs);
		}
	}

	private LinkedList<Thread> createPublisherThread(Broker broker)	{
		LinkedList<Thread> publisherThreadList = new LinkedList<>();
		for(String file : init.getInputFiles())	{
			Thread publisherThread = new Thread(new AmazonPublisher1(file, broker));
			publisherThreadList.add(publisherThread);
		}
		return publisherThreadList;
	}

	private Thread createBrokerThread(Broker broker)	{
		Thread brokerThread = null;
		if(init.getBrokerType().matches("asyncOrdered"))	{
			//creating broker thread
			brokerThread = new Thread((AsyncOrderedBroker)broker);
		} else if(init.getBrokerType().matches("asyncUnordered"))	{
			//creating broker thread
			brokerThread = new Thread((AsyncUnorderedBroker)broker);
		}
		return brokerThread;
	}

	private void startPublisherThreads(LinkedList<Thread> publisherThreadList)	{
		for(Thread publisherThread: publisherThreadList) {
			publisherThread.start();
		}
	}

	private void joinPublisherThreads(LinkedList<Thread> publisherThreadList)	{
		for(Thread publisherThread: publisherThreadList) {
			try {
				publisherThread.join();

			} catch (InterruptedException ie) {
				System.out.println("Error in closing publisher threads");
			}
		}
		System.out.println("Publisher threads finished. ");
		Project2Logger.write(Level.INFO, "Publisher threads finished. ", 0);
	}


	private void closeBroker(Broker broker, Thread brokerThread)	{

		if(init.getBrokerType().matches("asyncOrdered") || init.getBrokerType().matches("asyncUnordered"))	{
			broker.shutdown();
			
			try {
					brokerThread.join();
			} catch (InterruptedException e) {
				System.out.println("Error in closing broker");
			}
		}
		System.out.println("Broker thread finished.");
		Project2Logger.write(Level.INFO, "Broker thread finished.", 0);
		Project2Logger.write(Level.INFO, "No of records read: " + broker.getRecordCounter(), 0);
	}


	private void closeSubscribers(Subscribers1[] subscribersNew, Subscribers1[] subscribersOld)	{
		for(Subscribers1 subs : subscribersNew) {
			subs.closeWriter();
			Project2Logger.write(Level.INFO, "Total records in New: " + subs.getRecordCount(), 0);
			System.out.println("Total records in New: " + subs.getRecordCount());

		}
		for(Subscribers1 subs : subscribersOld) {
			subs.closeWriter();
			Project2Logger.write(Level.INFO, "Total records in Old: " + subs.getRecordCount(), 0);
			System.out.println("Total records in Old: " + subs.getRecordCount());
		}

	}

	public void closeLogger()	{
		Project2Logger.close();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TestApp testApp = new TestApp();
		System.out.println("Project2");

		if(CmdLineArgsValidator.config.check(args))	{
			//reading configuration file content into Project2Init object
			init = Project2InitReader.project2InitjsonReader(args[0]);
		}
		else	{
			System.out.println("Unable to initialize, exiting system");
			System.exit(1);
		}

		//initializing logger 
		testApp.initializeLogger();

		long start = System.currentTimeMillis();

		//creating Subscribers
		Subscribers1[] subscribersNew = testApp.createSubscriberNew();
		Subscribers1[] subscribersOld = testApp.createSubscriberOld();

		//initializing Broker
		Broker broker;
		if((broker = testApp.initializeBroker()) == null)	{
			System.exit(1);
		}

		//adding subscriber to borker
		testApp.addSubscribers(broker, subscribersNew, subscribersOld);

		//creating AmazonPublisher1 thread for each input file
		LinkedList<Thread> publisherThreadList = testApp.createPublisherThread(broker);

		//starting publisher threads 
		testApp.startPublisherThreads(publisherThreadList);

		Thread brokerThread;
		if((brokerThread = testApp.createBrokerThread(broker)) != null)	{
			brokerThread.start();
		}



		//waiting for publisher threads to complete
		testApp.joinPublisherThreads(publisherThreadList);

		//closing broker thread
		testApp.closeBroker(broker, brokerThread);

		//closing subscriber resources
		testApp.closeSubscribers(subscribersNew, subscribersOld);

		// printing time taken and other details
		long end = System.currentTimeMillis(); //retrieve current time when finishing calculations
		System.out.println("Time taken: " + (end-start)/1000 + " seconds");
		Project2Logger.write(Level.INFO, "Time taken: " + (end-start)/1000 + " seconds", 0);

		//closing logger
		testApp.closeLogger();

	}


}