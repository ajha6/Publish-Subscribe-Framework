/**
 * 
 */
package cs601.project2;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author anuragjha
 * https://docs.oracle.com/javase/8/docs/technotes/guides/logging/overview.html
 */
public class Project2Logger {

	private static Logger logger; 
	private static FileHandler logHandler;
	

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}


	public static void initialize(String logName, String logFile)	{

		logger = Logger.getLogger(logName);
		
		try	{
			logHandler = new FileHandler(logFile);	
		} catch(IOException ioe)	{
			System.out.println("Error while acquiring the file handler for the logger");
		}
		
		logger.addHandler(logHandler);

		logger.log(Level.INFO, "Logger Name: " + logName + "\tLogFile" + logFile, 0);
		

	}
	
	public static void close()	{
		logHandler.close();
		
	}
	
	
	


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
