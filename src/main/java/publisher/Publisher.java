/**
 * 
 */
package publisher;

/**
 * @author anuragjha
 *
 */
public interface Publisher<T> {
	
	/**
	 * Called by the Publisher when a new item
	 * has to be published.
	 * @param item
	 */
	public void createEvent(T item);

}
