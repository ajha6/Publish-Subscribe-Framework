/**
 * 
 */
package item;

import java.util.Arrays;

/**
 * Reviews class stores the data read from the input Review files
 * @author anuragjha
 *
 */
public class Reviews extends AmazonItem {

	private String reviewerID;
	private String asin;
	private String reviewerName;
	private int[] helpful;
	private String reviewText;
	private int overall;
	private String summary;
	private long unixReviewTime;
	private String reviewTime;

	//variable to understand if the review is new or old
	private boolean isNew;


	/**
	 * Constructor - empty constructor for Gson
	 */
	public Reviews()	{

	}


	/**
	 * isNew method returns if the Review is new or old 
	 * @return the isNew (true / false)
	 */
	public synchronized boolean isNew() {
		if(!this.isNew)	{
			this.setIsNew();
		}
		return isNew;
	}


	
	/**
	 * setIsNew method checks and sets the isNew state of Review
	 */
	private void setIsNew() {
		if (this.unixReviewTime >= 1362268800)	{
			this.isNew = true;
		}
		else	{
			this.isNew = false;
		}
	}


	/**
	 * @return itemId
	 */
	public synchronized int getItemId() {
		return itemId;
	}

	
	/** 
	 * toString method of Object class
	 * @override
	 */
	public synchronized String toString()	{
		return "\n ItemId: "+ this.itemId
				+ "\n\treviewerID: " + this.reviewerID
				+ "\n\tasin: " + this.asin
				+ "\n\treviewerName: " + this.reviewerName
				+ "\n\thelpful: " + Arrays.toString(this.helpful)
				+ "\n\treviewText: " + this.reviewText
				+ "\n\toverall: " + this.overall
				+ "\n\tsummary: " + this.summary
				+ "\n\tunixReviewTime: " + this.unixReviewTime
				+ "\n\treviewTime: " + this.reviewTime + "\n";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub



	}

}
