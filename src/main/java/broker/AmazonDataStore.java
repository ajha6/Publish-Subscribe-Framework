package broker;

import java.util.HashMap;

import item.Reviews;


/**
 * @author anuragjha
 *	AmazonDataStore class contains all the Data Structure to hold Record details and Word details
 *  and contain methods to update those data structure, also a AmazonDataStoreSearcher Object
 */
public enum AmazonDataStore {

	ONE;


	// key - recId, value - AmazonObject
	private HashMap<Integer, Reviews> reviewDataStore = new HashMap<Integer, Reviews>();



	/**
	 * @return the reviewDataStore
	 */
	public HashMap<Integer, Reviews> getReviewDataStore() {
		return reviewDataStore;
	}


	/**
	 * newRecord method is called via notifyDataStore method of Amazon Reviews
	 * This method process the new record to 2 Review DataStores
	 * @param newRecord
	 */
	public void newRecord(Reviews newRecord)	{
		processNewRecord(newRecord);
	}



	/**
	 * processNewRecord method implements update of Review DataStores for each new Record
	 * @param newRecord
	 */
	private void processNewRecord(Reviews newRecord)	{
		// update 2 data store for Reviews
		//updating reviewDataStore
		reviewDataStore.put(newRecord.getItemId(), newRecord);

	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
