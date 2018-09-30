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
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * @author anuragjha
 *
 */
public class Project2Init {


	private static String[] inputFiles;


	public Project2Init()	{
		
	}

	
	/**
	 * @return the inputFiles
	 */
	public static String[] getInputFiles() {
		Project2Init.project2InitjsonReader();
		return inputFiles;
	}




	/**
	 * jsonFileReader process Review file and then notifies DataStore 
	 * @param inputFile
	 */
	public static void project2InitjsonReader()	{

		JsonParser parser = new JsonParser();
		Path path = Paths.get("project2Init.json");	


		try(
				BufferedReader reader = Files.newBufferedReader(path, Charset.forName("ISO-8859-1"))
				)	{
			String line;
			System.out.println("Processing " + "project2Init.json" + " file.");

			while((line = reader.readLine()) != null)	{
				try {

					JsonObject object =  parser.parse(line).getAsJsonObject();
					System.out.println("here");
					Project2Init.inputFiles = new Gson().fromJson(object, String[].class);




				} catch(JsonSyntaxException jse)	{
					System.out.println("Skipping line ...");
				}
			}	

		}	catch(IOException ioe)	{
			System.out.println("Could not process init file");
			System.out.println("Exiting System");
			System.exit(0);
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
