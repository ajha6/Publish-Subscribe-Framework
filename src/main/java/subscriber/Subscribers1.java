/**
 * 
 */
package subscriber;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import item.Reviews;

/**
 * @author anuragjha
 *
 */
public class Subscribers1<T> implements Subscriber<T> {

	private int recordCount;
	
	private String filter;	
	private FileOutput outputFile;
	private BufferedWriter bw;
	private FileWriter fw;
	//Gson gson;
		

	/**
	 * subscriber constructor
	 * @param filter
	 */
	public Subscribers1(String filter, String file)	{
		this.outputFile = new FileOutput(file);
		this.filter = filter;
		this.recordCount = 0;
		this.bw = null;
		//this.gson = new Gson();
		//opens a File to write the results
		this.openWriter();

	}
	
	/**
	 * @return the recordCount
	 */
	public int getRecordCount() {
		return recordCount;
	}
	
	/**
	 * openWriter methods opens writers to be used for writing to a file
	 */
	private void openWriter() {
		
		try {
			fw = new FileWriter(outputFile.getFile(),true);
			bw = new BufferedWriter(fw);
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
	public void onEvent(T item)	{		
		toOutput(item);
		
	}

	
	/**
	 * toOutput method writes the record into output file of Subscriber
	 * @param item
	 */
	private void toOutput(T item)	{
		//System.out.println("current thread in subscriber: " + Thread.currentThread().getName());
		if(this.filter.matches("new") && ((Reviews)item).isNew())	{
			outputFile.addContent(new Gson().toJson(item)+"\n", bw);
			synchronized(this)	{
				
				this.recordCount += 1;
			}
			
		} else if(this.filter.matches("old") && !((Reviews)item).isNew())	{
			outputFile.addContent(new Gson().toJson(item)+"\n", bw);
			synchronized(this)	{
				
				this.recordCount += 1;
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	

	}



}
