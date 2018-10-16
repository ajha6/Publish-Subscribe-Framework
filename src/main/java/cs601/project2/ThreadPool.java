/**
 * 
 */
package cs601.project2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author anuragjha
 * creates threadpool to be used by the program
 */
public class ThreadPool {

	/**
	 * constructor
	 */
	public ThreadPool()	{
		this.createThreadPool();
	}


	public void createThreadPool()	{
		/* TBD: executor service */		
		ExecutorService threadPool = Executors.newFixedThreadPool(30);


		long start = System.currentTimeMillis(); //retrieve current time when starting calculations

//////--------////////$$$$$$$$$$$$$$    threadPool.execute();			

		/* TBD: shutdown executor service */
		threadPool.shutdown();
		try {
			threadPool.awaitTermination(2, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		long end = System.currentTimeMillis(); //retrieve current time when finishing calculations
		System.out.println("time: " + (end-start));
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ThreadPool();

	}

}
