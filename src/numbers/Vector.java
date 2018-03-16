package numbers;

import java.util.ArrayList;
import java.util.Collection;

/**
 * class that represents vectors
 * Supports multiplication 
 *
 */
public class Vector extends ArrayList<Double> {
	
//	private ArrayList<Double> vector;
	private String id;
	
	public Vector(String id, Collection<Double> values) {
		super(values);
		this.id = id;
	}
	
	public Vector(String id) {
		this.id = id;
	}
	
	public Vector(String id, double[] d) {
		this.id = id;
		
		for( int i = 0; i< d.length; i++)
			this.add(d[i]);
	}
	
	public static Vector generateDefault(int size, String id ) {
		ArrayList<Double> list = new ArrayList<Double>();
		for( int i = 0; i < size; i ++) {
			list.add(Math.random()*2-1);
		}
		return new Vector(id, list);
	}
	
	/**
	 * Method that computes the dot product between two vectors
	 * @param a & b:
	 * 		vectors to be multiplied
	 * @return
	 * 		product of multiplication
	 * @throws Exception
	 * 		when the dimensions of the vectors do not match
	 */
	public static Double dotProduct( Vector a , Vector b ) throws Exception{
		Double sum = (double) 0;
		
		if (a.size() != b.size())
			throw new Exception("vectors do not match in size");
		else 
			for (int i = 0; i < a.size(); i++) 
				sum += a.get(i) * b.get(i);
		return sum;
	}
	
	/**
	 * Adds the values at the same index to each other
	 */
	public void add(Vector a) throws Exception{
		if (this.size() != a.size())
			throw new Exception("vectors do not match in size");
		else {
			for (int i = 0; i < a.size(); i++) {
				this.set(i, this.get(i) + a.get(i));
			}
		}
	}
	
	
	/**
	 * Makes a vector of length 10 with 1 at specified location (not index)
	 */
	public static Vector generateRepresentation(int n) {
		Vector rep = new Vector("vector represenation of " + n);
		for (int i = 0; i < 10; i++) {
			rep.add(i == (n) ? 1.0 : 0.0);
		}
		return rep;
	}

}
