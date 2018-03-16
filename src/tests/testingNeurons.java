package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import image.ImageLoader;
import image.ImageLoader.DigitImage;
import numbers.Network;
import numbers.Vector;

class testingNeurons {

	String labelFile = "C:\\Users\\Katie\\Desktop\\network\\emnist-digits-train-labels-idx1-ubyte";
	String imageFile = "C:\\Users\\Katie\\Desktop\\network\\emnist-digits-train-images-idx3-ubyte";
	String testImage = "C:\\Users\\Katie\\Desktop\\network\\emnist-digits-test-images-idx3-ubyte";
	String testLabel = "C:\\Users\\Katie\\Desktop\\network\\emnist-digits-test-labels-idx1-ubyte";

	@Test
	public void train() throws Exception {
		Network numbers = new Network(true, 20);

		int cnt = 0;

		List<DigitImage> trainingData = loadDigitImages(labelFile, imageFile);
		for (int i = 0; i < 4; i++) {
			for (DigitImage di : trainingData) {
				int trueInt = di.label;
				Vector input = new Vector("input", di.pixels);
				numbers.backwardsProp(input, trueInt, .01);

				System.out.print("count: " + ++cnt);
				System.out.println(" actual: " + trueInt + "\tsystem: " + numbers.identify(input));
			}
		}

		int correct = 0;
		for (int t = 0; t < 100; ++t) {
			int trueInt = trainingData.get(t).label;
			Vector input = new Vector("input", trainingData.get(t).pixels);
			if( trueInt == numbers.identify(input))
				correct++;
			System.out.println("actual: " + trueInt + "\tsystem: " + numbers.identify(input));
		}
		System.out.println(correct);
		
		
		test(numbers);
	}
	
	@Test
	public void test(Network n) throws Exception{
		List<DigitImage> testData = loadDigitImages(testLabel, testImage);
		testAcc(n, testData);
	}

	@Test
	void testLoadDigitImages() {
		String labelFile = "C:\\Users\\Katie\\Desktop\\network\\emnist-digits-train-labels-idx1-ubyte";
		String imageFile = "C:\\Users\\Katie\\Desktop\\network\\emnist-digits-train-images-idx3-ubyte";

		for (DigitImage di : loadDigitImages(labelFile, imageFile)) {
			di.print();
		}
	}
	
	public void testAcc( Network numbers, List<DigitImage> di) throws Exception{
		double correct = 0.0;
		for (int t = 0; t < di.size(); ++t) {
			int trueInt = di.get(t).label;
			Vector input = new Vector("input", di.get(t).pixels);
			if( trueInt == numbers.identify(input))
				correct+=1;
			System.out.println("actual: " + trueInt + "\tsystem: " + numbers.identify(input));
		}
		System.out.println(correct/(double)di.size());
	}

	static public List<DigitImage> loadDigitImages(String labelFileName, String imageFileName) {

		ImageLoader loader = new ImageLoader(labelFileName, imageFileName);
		List<DigitImage> images = null;

		try {
			images = loader.loadDigitImages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (images != null)
			System.out.println("number of images = " + images.size());

		return images;
	}

}
