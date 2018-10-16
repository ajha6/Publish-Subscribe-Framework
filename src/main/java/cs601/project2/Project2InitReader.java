/**
 * 
 */
package cs601.project2;

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

import item.Reviews;

/**
 * @author anuragjha
 *
 */
public class Project2InitReader {

	private static Project2Init initProject2= null;
	
	/**
	 * jsonFileReader process Review file and then notifies DataStore 
	 * @param inputFile
	 */
	public static Project2Init project2InitjsonReader()	{

		JsonParser parser = new JsonParser();
		Path path = Paths.get("project2Init.json");	
		

		try(
				BufferedReader reader = Files.newBufferedReader(path, Charset.forName("ISO-8859-1"))
				)	{
			String line;
			System.out.println("Processing " + "project2Init.json" + " file.");

			while((line = reader.readLine()) != null)	{
				try {
					//parses each line into JsonObject
					JsonObject object =  parser.parse(line).getAsJsonObject();
					//creates AmazonReviews object from the Json Object 
					initProject2 = new Gson().fromJson(object, Project2Init.class);

				} catch(JsonSyntaxException jse)	{
					System.out.println("Skipping line ...");
				}
			}	

		}	catch(IOException ioe)	{
			System.out.println("Could not process init file");
			System.out.println("Exiting System");
			System.exit(0);
		}
		return initProject2;
	}
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Project2Init init = Project2InitReader.project2InitjsonReader();
		System.out.println("init: " + init);

	}

}
