/**
 * 
 */
package edu.vt.jowilcox.cs3114.p4.gis;

import java.io.Serializable;
import java.nio.Buffer;
import java.util.ArrayList;


/**
 * Object pool.
 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
 */
public class GISBufferPool<T extends Buffer> implements Serializable {
	/** Generated serial version id */
  private static final long serialVersionUID = -4340171158534043845L;
  
  static final int DEFAULT_SIZE = 20;

  /** Pool stack */
	private ArrayList<T> pool;
  /** Pool Stack */
  
  /**
   * Constructor.
   */
  public GISBufferPool(){
  	this(DEFAULT_SIZE);
  }
  
  /**
   * Constructor.
   * @param size
   */
  public GISBufferPool(int size) {
  	this.pool = new ArrayList<>(size);
  }
  
  public synchronized void get () { }
  
  public synchronized void put(T data) { }
  
}