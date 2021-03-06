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
import java.util.logging.Level;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import broker.Broker;
import cs601.project2.Project2Logger;
import item.Reviews;

/**
 * AmazonPublisher1 implements Publisher functionality
 * @author anuragjha
 */
public class AmazonPublisher1 implements Runnable {

	private final String inputFile;
	private final Broker broker;
	
	/**
	 * Publisher constructor
	 * @param inputFile
	 * @param broker
	 */
	public AmazonPublisher1(String inputFile, Broker broker) {
		this.inputFile = inputFile;
		this.broker = broker;
	}


	/**
	 * jsonFileReader process Review file and then publish method of Broker 
	 * @param inputFile
	 */
	private synchronized void jsonFileReader(String inputFile) {

		JsonParser parser = new JsonParser();
		Path path = Paths.get(inputFile);	


		try(
				BufferedReader reader = Files.newBufferedReader(path, Charset.forName("ISO-8859-1"))
				) {
			
			String line;
			//System.out.println("Processing " + inputFile + " file.");
			Project2Logger.write(Level.INFO, "Processing " + inputFile + " file.", 0);
			while((line = reader.readLine()) != null) {
				try {
					//parses each line into JsonObject
					JsonObject object =  parser.parse(line).getAsJsonObject();
					//creates AmazonReviews object from the Json Object 
					Reviews newReview = new Gson().fromJson(object, Reviews.class);
					//calling publish method of Broker
					broker.publish(newReview);
					
				} catch(JsonSyntaxException jse) {
					System.out.println("Skipping line ...");
				}
			}	
		}	catch(IOException ioe) {
			System.out.println("Could not process Review file");
			System.out.println("Exiting System");
			System.exit(0);
		}

	}

	
	public void run() {
		this.jsonFileReader(this.inputFile);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
