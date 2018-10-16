/**
 * 
 */
package publisher;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import broker.Broker;
import item.Reviews;

/**
 * @author anuragjha
 *
 */
public class AmazonPublisher1 implements Runnable {

	//private ReviewsJsonHandler recordReader;
	private final String inputFile;
	private final Broker broker;
	

	public AmazonPublisher1(String inputFile, Broker broker)	{
		//this.jsonFileReader(inputFile);
		this.inputFile = inputFile;
		this.broker = broker;
		//this.isReadComplete = false;

		//this.jsonFileReader(this.inputFile);
	}


	/**
	 * jsonFileReader process Review file and then notifies DataStore 
	 * @param inputFile
	 */
	private synchronized void jsonFileReader(String inputFile)	{

		JsonParser parser = new JsonParser();
		Path path = Paths.get(inputFile);	


		try(
				BufferedReader reader = Files.newBufferedReader(path, Charset.forName("ISO-8859-1"))
				)	{
			String line;
			System.out.println("Processing " + inputFile + " file.");

			while((line = reader.readLine()) != null)	{
				try {
					//parses each line into JsonObject
					JsonObject object =  parser.parse(line).getAsJsonObject();
					//creates AmazonReviews object from the Json Object 
					Reviews newReview = new Gson().fromJson(object, Reviews.class);
					//new Review record notifies the data Store to process it
					//+++ instead of this -->   newReview.notifyBroker();
					
					//broker.publish(newReview);
					this.produce(newReview);
					//System.out.println("review: " + newReview);
					//broker.publish(newReview);

				} catch(JsonSyntaxException jse)	{
					System.out.println("Skipping line ...");
				}
			}	
		}	catch(IOException ioe)	{
			System.out.println("Could not process Review file");
			System.out.println("Exiting System");
			System.exit(0);
		}

	}


	private synchronized void produce(Reviews newReview)	{
		broker.publish(newReview);
	}

	public void run()	{
		//while(true)	{
		this.jsonFileReader(this.inputFile);
		//}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
