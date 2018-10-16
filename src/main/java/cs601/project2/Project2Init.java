/**
 * 
 */
package cs601.project2;

/**
 * @author anuragjha
 *
 */
public class Project2Init {


	private String[] inputFiles;
	private String loggerFile;

	public Project2Init()	{
		
	}


	/**
	 * @return the inputFiles
	 */
	public String[] getInputFiles() {
		return inputFiles;
	}


	/**
	 * @return the loggerFile
	 */
	public String getLoggerFile() {
		return loggerFile;
	}

	
	public String toString()	{
		return "inputFiles: " + this.inputFiles[1] +
				"\nloggerFile: " + this.loggerFile;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
