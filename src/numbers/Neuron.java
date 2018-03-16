package numbers;

import java.util.ArrayList;

/**
 * Class that represents a single neuron
 *
 */
public class Neuron {
	
	private String id;
	private Vector weights;
	
//constructors 
	
	public Neuron(String id, Vector weights) {
		this.id = id;
		this.weights = weights;
	}
	
	/**
	 * 
	 * @param id
	 * 		neuron id
	 * @param i
	 * 		how many weights in the neuron
	 */
	public Neuron(String id, int i) {
		this.id = id;
		weights = Vector.generateDefault(i, "weights");
	}
	
	
	
//set and get methods
	
	public String getId() {return id;}
	
	public Vector getWeights() {return weights;}
	
	public void setId(String id) {this.id = id;}
	
	public void setWeights(Vector weights) {this.weights = weights;}
	
	
	/**
	 * method that computes the result of the sigmoid function
	 */
	public static double sigmoid(double x) {
		return (1 / (1 + Math.pow(Math.E, (-1 * x))));
	}
	
	/**
	 * method that finds the resulting value of the neutron from a incoming signal vector
	 */
	public double output(Vector x) throws Exception {
		double dotProduct;

		dotProduct = Vector.dotProduct(x, getWeights());
		return sigmoid(dotProduct);
	}

	/**
	 * Method that adjusts the value at the index by a certain amount
	 **/
	public double adjustBy(int index, double change) {
		double original = weights.get(index);
		weights.set(index, original + change);
		return original;
	}

}

