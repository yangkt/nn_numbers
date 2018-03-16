package numbers;

import java.util.ArrayList;

//speci
public class Network {
	
	NeuronGroup[] network; 
	//for numbers
	//at 0 is the group that has n neurons and 784 weights in each
	//at 1 is the group that has 10 neurons and n weights in each
	
	
	/**
	 * 
	 * @param a
	 * 		the number of groups of neurons
	 * @param sizes
	 * 		an array of the size of the groups
	 */
	public Network(int a, int[] sizes) {
		//TODO
	}
	
	public Network( boolean t, int n) {
		
		network = new NeuronGroup[2];
		
		//two neuron groups
		//first is length n
		//second is length 10
		network[0] = new NeuronGroup(n, 784);
		network[1] = new NeuronGroup(10, n);

	}
	
	/**
	 * Identify the actual digit of the given image
	 * @param pixels
	 * @return
	 */
	public int identify(Vector pixels) throws Exception{
		ArrayList<Double> output = getOutputVectors(pixels)[1];
		
		double[] softmax = new double[10];
		double denomenator = 0;
		
		for(int i=0; i<10; ++i) 
			denomenator += output.get(i);
		
		for(int i=0; i<10; ++i) 
			softmax[i] = output.get(i) / denomenator;
		
		int id=0;
		double largest = softmax[0];
		
		for(int i=0; i<10; ++i) {
			if(softmax[i] > largest) {
				largest = softmax[i];
				id = i;
			}
		}
		return id;		
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 * 	an array of Vectors
	 * 		in 0 - the vectors that result from the first neuron group
	 * 		in 1 - the vectors that result from the second neuron group (final vector)
	 * @throws Exception
	 */
	public Vector[] getOutputVectors(Vector input) throws Exception {
		
		NeuronGroup ng1 = network[0];
		NeuronGroup ng2 = network[1];
		
		//first vector goes in it's 784 in dimension
		Vector firstProduct = new Vector("output1");
		for (Neuron n : ng1) { 
			firstProduct.add( Neuron.sigmoid(Vector.dotProduct(n.getWeights(),input)));
		}
		
		//output vector is n long
		
		//second vector goes in with n
		Vector finalVector = new Vector("output2");
		for( Neuron n : ng2)
			finalVector.add(Neuron.sigmoid(Vector.dotProduct(n.getWeights(), firstProduct)));
		//output vector is 10 long
		
		return new Vector[] {firstProduct, finalVector};
	}
	

	/**
	 * 
	 * @param input
	 * @param tV
	 * @param adjR
	 * @throws Exception
	 */
	public void backwardsProp(Vector input, int tV, double adjR) throws Exception{
		
		Vector trueValue = Vector.generateRepresentation(tV);
		Vector[] vs = getOutputVectors(input);
		
		Vector result = vs[1]; //length 10
		Vector intermediate = vs[0]; //length n
		
		NeuronGroup n2 = network[1];  // 10 neurons and n weights in each 
		NeuronGroup n1 = network[0];  //n neurons and 784 weights in each
		
		int n = n1.size(); //the size that you chose for n
	
		
		//to remember weight adjustments for second layer
		Vector[] adj2 = new Vector[10];
		Vector[] adj1 = new Vector[n];
		
		
		//to remember the diff
		Vector[] dif2 = new Vector[10];
		
		for ( int i = 0 ; i < 10; i++) {
			
			//calculate the differentiated error
			double g = trueValue.get(i);
			double z = result.get(i);
			Vector adjs = new Vector("adjustments to weight2");
			Vector difs = new Vector("difs");
			
			for ( int j = 0; j < n; j++ ) {
				double y = intermediate.get(j);
				
				double de = (g-z)*z*(1-z)*y;
				difs.add(de);
				double adj = de*adjR;
				adjs.add(adj);
			}
			adj2[i] = adjs;
			dif2[i] = difs;
		}
		
		//n is how many neurons in the first layer 
		for( int k = 0; k < n; k++) {//to go through each neuron in the first layer
			//calculate de2			
		
			Vector adjs = new Vector ("adjustments to weight1");
			
			double y = intermediate.get(k); //the final product from that neuron
			
			for( int l = 0; l < 784; l++) {//to go through each weight in the neuron
				double x = input.get(l); //this is the input that corresponds with the weight
										 //being looked at 
				
				
				//now for summing variables that are all different
				double de1 = 0.0;
				for ( int m = 0; m < 10; m++) { //go through each neuron in the second group 
												// want to onerse the element at neuron m in second group and position k
									
					double w2 = n2.get(m).getWeights().get(k);
					//second group of neurons
					//get the mth neuron (from 1-10)
					//get the weight at position k (from 1-n)
					
					double de = dif2[m].get(k);
					//get the result from dif2 at m
						// this is the difs of the neuron at position m
					//get the value that results from the dif of the weight at k
					
					de1 += (de*w2*(1-y)*x);
				}
				double adj = de1 * adjR;
				adjs.add(adj);
			}
			adj1[k] = adjs;
		}
		
		//adjust weights
		n1.addAdjustments(adj1);
		n2.addAdjustments(adj2);		
	}
	
	
	
	class NeuronGroup extends ArrayList<Neuron>{
		
		/**
		 *  
		 * @param n
		 * 		how many neurons in the group
		 * @param w
		 * 		how many weights in each neuron
		 * @return
		 */
		public NeuronGroup(int n, int w) {
			for( int i = 0; i < n; i++)
				this.add(new Neuron("", w));
		}
		
		/**
		 * the length of the vector array is the same length as the array list of neurrons 
		 * @param adj
		 */
		public void addAdjustments(Vector[] adj) throws Exception{
			for( int i = 0; i < adj.length; i ++) {
				(this.get(i).getWeights()).add(adj[i]);
			}
		}
	}
		

}
