/**
 * 
 */
package subscriber;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import item.Reviews;

/**
 * @author anuragjha
 *
 */
public class Subscribers1<T> implements Subscriber<T> {

	private static int subsCount = 0;
	
	private int subscriberId;
	private String filter;
	
	private FileOutput outputFile;
	private BufferedWriter bw = null;
	private FileWriter fw;
	
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

	/**
	 * subscriber constructor
	 * @param filter
	 */
	public Subscribers1(String filter)	{
		subsCount += 1;
		this.subscriberId = subsCount;
		this.outputFile = new FileOutput("outFile"+subscriberId+".txt");
		this.filter = filter;
		this.openWriter();

	}
	
	/**
	 * openWriter methods opens writers to be used in writing to a file
	 */
	private void openWriter() {
		
		try {
			fw = new FileWriter(outputFile.getFile(),true);
			bw = new BufferedWriter(fw);
			//bw.write(mycontent); 
			////////bw.append(mycontent);    
			//System.out.println("File written Successfully");
			
		}
		catch (IOException ioe) {
			System.out.println(ioe.getMessage() );
			
		}
	}
	
	
	/**
	 * closeWriter method closes the resources that is open for writing 
	 */
	public void closeWriter()	{
		try{
			if(bw != null)	{
				bw.close();
			}
			//if(fw != null)	{
			//	fw.close();
			//}
		}catch(Exception e){
			System.out.println("Error in closing BufferedWriter");
		}
	}

	/**
	 * Called by the Broker when a new item
	 * has been published.
	 * @param item
	 */
	public synchronized void onEvent(T item)	{		
		toOutput(item);
		
	}

	
	/**
	 * toOutput method writes the record into output file of Subscriber
	 * @param item
	 */
	private synchronized void toOutput(T item)	{
		//System.out.println("current thread in subscriber: " + Thread.currentThread().getName());
		if(this.filter.matches("new") && ((Reviews)item).isNew())	{
			outputFile.addContent(item.toString(), bw);
			this.recordCount += 1;
		} else if(this.filter.matches("old") && !((Reviews)item).isNew())	{
			outputFile.addContent(item.toString(), bw);
			this.recordCount += 1;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	

	}



}
