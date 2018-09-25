/**
 * 
 */
package item;

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



	/**
	 * Constructor - empty constructor for Gson
	 */
	public Reviews()	{

	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Thread t1 = new Thread()	{
			public void run()	{
				for(int i = 0; i < 30000; i++)	{
					System.out.println("ItemId: "+ new Reviews().itemId);
				}
			}
		};
		
		Thread t2 = new Thread()	{
			public void run()	{
				for(int i = 0; i < 30000; i++)	{
					System.out.println("ItemId: "+ new Reviews().itemId);
				}
			}
		};


		t1.start();
		t2.start();
		
		try	{
			t1.join();
			t2.join();
		}
		catch(InterruptedException ie)	{
			System.out.println(ie.getStackTrace());
		}
		
		System.out.println("Complete");

	}

}
