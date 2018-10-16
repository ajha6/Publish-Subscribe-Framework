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
 *
 */
public class FileOutput {

	File file;
	///BufferedWriter bw = null;
	//FileWriter fw;

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}


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
	 * The method takes the filename and content as params and generates a file with the 
	 * specified content
	 * @param outFile
	 * @param mycontent
	 * @return true/false
	 */
	public synchronized void addContent(String mycontent, BufferedWriter bw)	{

		//////////////BufferedWriter bw = null;
		//FileWriter fw;

		try {


			/////////FileWriter fw = new FileWriter(this.file,true);
			/////////bw = new BufferedWriter(fw);
			//bw.write(mycontent); 
			bw.append(mycontent);    
			//System.out.println("File written Successfully");

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
