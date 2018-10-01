/**
 * 
 */
package subscriber;

import item.Reviews;

/**
 * @author anuragjha
 *
 */
public class Subscribers1<T> implements Subscriber<T>,Runnable {

	private static int count = 0;
	private FileOutput outputFile;
	private int subscriberId;
	private String filter;

	public int recordCount = 0;
	//private boolean firstItemReceived = false;
	//private boolean isOnEvent;
	private T item;
	private volatile boolean isEvent = false;

	//	public Subscribers1()	{
	//		Subscribers1.count += 1;
	//		this.subscriberId = count;
	//		this.outputFile = new FileOutput("outFile"+subscriberId+".txt");

	//	}

	public Subscribers1(String filter)	{
		Subscribers1.count += 1;
		this.subscriberId = count;
		this.outputFile = new FileOutput("outFile"+subscriberId+".txt");
		this.filter = filter;

	}

	public synchronized void onEvent(T item)	{
		this.item = item;
		this.isEvent = true;
		toOutput(this.item); /// move this to run()


	}

	private synchronized void toOutput(T item)	{
		//System.out.println("in toOutput: "+ item.toString());

		if(this.filter.matches("new") && ((Reviews)item).isNew())	{

			outputFile.addContent(item.toString());
			this.recordCount += 1;
		} else if(this.filter.matches("old") && !((Reviews)item).isNew())	{

			outputFile.addContent(item.toString());
			this.recordCount += 1;
		}
	}


	@Override
	public void run() {
		//after 1st object received, in a true while - start polling --- so after the value - 
		// NUll is received break out of the loop

	//	while(true)	{
	//		synchronized(this)	{
	//			if(this.isEvent)	{
	//				toOutput(this.item);
	//				this.isEvent = false;
	//				System.out.println("in blablabla: "+ this.item);
	//			}
	//		}
//
	//	}



	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Subscribers1 s = new Subscribers1();

	}



}
