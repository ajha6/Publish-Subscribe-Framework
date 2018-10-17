/**
 * 
 */
package cs601.project2;

/**
 * @author anuragjha
 * Project2Init class holds the config information 
 *
 */
public class Project2Init {

	private String brokerType;
	private String loggerFile;
	private String[] inputFiles; //contains list of input files
	private String[] subscribersNew; //contains list of output files
	private String[] subscribersOld; //contains list of output files
	private int queueSize;
	private int poolSize;
	
	/**
	 * constructor
	 */
	public Project2Init()	{
		
	}
	
	
	/**
	 * @return the brokerType
	 */
	public String getBrokerType() {
		return brokerType;
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

	/**
	 * @return the subscribersNew
	 */
	public String[] getSubscribersNew() {
		return subscribersNew;
	}
	
	
	/**
	 * @return the subscribersOld
	 */
	public String[] getSubscribersOld() {
		return subscribersOld;
	}
	
	
	/**
	 * @return the queueSize
	 */
	public int getQueueSize() {
		return queueSize;
	}


	/**
	 * @return the poolSize
	 */
	public int getPoolSize() {
		return poolSize;
	}
	
	
	public String toString()	{
		StringBuilder sb = new StringBuilder();
		sb.append("brokerType: " + this.brokerType + "\n");
		for(String file : inputFiles)	{
			sb.append("inputFile: " + file + "\n");
		}
		sb.append("loggerFile: " + this.loggerFile + "\n");
		for(String file : this.subscribersNew)	{
			sb.append("New Subscribers: " + file + "\n");
		}
		for(String file : this.subscribersOld)	{
			sb.append("Old Subscribers: " + file);
		}
	
		return sb.toString();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
