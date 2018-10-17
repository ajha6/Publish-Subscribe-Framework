/**
 * 
 */
package subscriber;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author anuragjha
 * FileOutput class handles creating and writing to a file
 */
public class FileOutput {

	File file;
	
	/**
	 * constructor - creates a new file
	 * @param outFile
	 */
	public FileOutput(String outFile)	{
		this.file = new File(outFile);

		try	{

			if (!this.file.exists()) {
				this.file.createNewFile();
			}
			else	{
				this.file.delete();
				this.file.createNewFile();
			}
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}

	}
	

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}


	/**
	 * The method takes content and pointer to a file as params and appends the
	 * specified content
	 * @param outFile
	 * @param mycontent
	 */
	public synchronized void addContent(String mycontent, BufferedWriter bw)	{

		try {
			bw.append(mycontent);    
		}
		catch (IOException ioe) {
			System.out.println(ioe.getMessage() );

		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
