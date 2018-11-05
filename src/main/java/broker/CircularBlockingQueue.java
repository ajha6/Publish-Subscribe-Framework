/**
 * 
 */
package broker;

import java.util.concurrent.TimeUnit;

/**
 * @author anuragjha
 *
 */
public class CircularBlockingQueue<T> {

	private T[] itemQueue;
	private int start;
	private int end;
	private int size;


	public CircularBlockingQueue(int initSize) {
		this.itemQueue = (T[]) new Object[initSize];
		this.start = 0;
		this.end = -1;  //circular queue
		this.size = 0;

	}


	public synchronized int getSize() {
		return this.size;
	}


	/**
	 * put method tries to put item in the queue, if the queue is full 
	 * then it tells the thread to wait
	 * @param item
	 */
	public synchronized void put(T item) {

		while(this.size == this.itemQueue.length) {	
			try	{
				this.wait();
			} catch(InterruptedException ie) {
				ie.printStackTrace();
			}
		}

		int next = (end+1)%(this.itemQueue.length); //circular queue
		this.itemQueue[next] = item;
		end = next;
		this.size += 1;

		if(this.size == 1) {
			this.notifyAll();
		}

	}


	/**
	 * take method tries to remove element from 
	 * @return
	 */
	public synchronized T take() {

		while(this.size == 0) {
			try	{
				this.wait();
			} catch (InterruptedException ie) {
				System.out.println("Cannot read from dispatcher");
			}
		}

		T item = this.itemQueue[start];
		this.start = (this.start + 1)%(this.itemQueue.length);
		this.size -= 1;

		if(this.size == (this.itemQueue.length - 1)) {
			this.notifyAll();
		}

		return item;
	}

	/*
	 * isEmpty method returns true/false for empty/non-empty queue
	 */
	public synchronized boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * Retrieves and removes the head of this queue, waiting up to the specified wait time 
	 * if necessary for an element to become available and returns null if the queue is still empty.
	 * @return
	 */
	public synchronized T poll(long time) {

		long startTime;
		long endTime;
		startTime = System.currentTimeMillis();
		while(this.size == 0) {
			try	{

				this.wait(time);
				if(this.size == 0) {
					endTime = System.currentTimeMillis();
					if((endTime - startTime) < time)	{
						continue;
					}else	{
						return null;
					}
				}
			} catch (InterruptedException ie)	{
				System.out.println("Cannot read from dispatcher");
			}
		}



		if(this.size == 0) {
			return null;
		}

		T item = this.itemQueue[start];
		this.start = (this.start + 1)%(this.itemQueue.length);
		this.size -= 1;

		if(this.size == (this.itemQueue.length - 1)) {
			this.notifyAll();
		}

		return item;

	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
