/**
 * 
 */
package subscriber;

import broker.AsyncOrderedDispatchBroker;
import item.Reviews;

/**
 * @author anuragjha
 *
 */
public class Subscribers1<T> implements Subscriber<T> {

	private static int count = 0;
	private FileOutput outputFile;
	private int subscriberId;
	private String filter;

	public int recordCount = 0;
	//private boolean firstItemReceived = false;
	//private boolean isOnEvent;
	//private T item;
	
//	public Subscribers1()	{
//		count += 1;
//		subscriberId = count;
//		outputFile = new FileOutput("outFile"+subscriberId+".txt");
//
//	}

	public Subscribers1(String filter)	{
		count += 1;
		subscriberId = count;
		outputFile = new FileOutput("outFile"+subscriberId+".txt");
		this.filter = filter;

	}

	public synchronized void onEvent(T item)	{
		//System.out.println("onEvent in Subscriber" + this.subscriberId);
		//System.out.println("t: "+ Thread.activeCount() );
		/// creating new object every time !!!!!!
		
		toOutput(item);
		
	}

	private void toOutput(T item)	{
		if(this.filter.matches("new") && ((Reviews)item).isNew())	{

			outputFile.addContent(item.toString());
			this.recordCount += 1;
		} else if(this.filter.matches("old") && !((Reviews)item).isNew())	{

			outputFile.addContent(item.toString());
			this.recordCount += 1;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Subscribers s = new Subscribers();

	}



}
