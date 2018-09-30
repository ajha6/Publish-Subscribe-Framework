/**
 * 
 */
package item;

import java.util.Arrays;

/**
 * @author anuragjha
 *
 */
public class Reviews extends AmazonItem {

	/*
	{"reviewerID": "A1N4O8VOJZTDVB", 
		"asin": "B004A9SDD8", 
		"reviewerName": "Annette Yancey", 
		"helpful": [1, 1], 
		"reviewText": "Loves the song, so he really couldn't wait to play this. A little less interesting for him so he doesn't play long, but he is almost 3 and likes to play the older games, but really cute for a younger child.", 
		"overall": 3.0, 
		"summary": "Really cute", 
		"unixReviewTime": 1383350400, 
		"reviewTime": "11 2, 2013"
			}
	 */
	private String reviewerID;
	private String asin;
	private String reviewerName;
	private int[] helpful;
	private String reviewText;
	private int overall;
	private String summary;
	private long unixReviewTime;
	private String reviewTime;

	private boolean isNew;


	/**
	 * Constructor - empty constructor for Gson
	 */
	public Reviews()	{

	}


	/**
	 * @return the isNew
	 */
	public synchronized boolean isNew() {
		if(!this.isNew)	{
			this.setIsNew();
		}
		return isNew;
	}


	/**
	 * @param isNew the isNew to set
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
	 * @return the itemId //inherited from AmazonItem Class
	 */
	public synchronized int getItemId() {
		return itemId;
	}

	/**
	 * sends the new AmazonObject record to DataStore
	 */
	//public synchronized void notifyBroker()	{
		//notify DataStore
		//AmazonDataStore.ONE.newRecord(this);
	//	System.out.println("record: " + this.getItemId());
	//	System.out.println("t: " + Thread.currentThread() + "\n");
		
	//}
	
	
	public String toString()	{
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
