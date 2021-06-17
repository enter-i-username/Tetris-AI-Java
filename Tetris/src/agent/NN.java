package agent;

public
class NN {
	
	
	public static void main(String[] args) {
		
		NN neural_network = new NN(
			new Layer(Weights.W1, Weights.b1, new NN.Layer.ReLU()),
			new Layer(Weights.W2, Weights.b2, new NN.Layer.ReLU()),
			new Layer(Weights.W3, Weights.b3, new NN.Layer.Linear())
		);
		
		Mat input = new Mat(new double[][] {
			{1, 2, 3, 4},
		});
		
		Mat output = neural_network.predict(input);
		
		output.print();
		
	}
	
	public static
	class Layer {
		public static abstract
		class Activation {
			abstract Mat f(Mat mat);
		}
		
		public static 
		class ReLU 
		extends Activation {
			Mat f(Mat mat) {
				for (int i = 0; i < mat._mat.length; ++i) {
					for (int j = 0; j < mat._mat[0].length; ++j) {
						mat._mat[i][j] = mat._mat[i][j] >= 0 ? mat._mat[i][j] : 0;
					}
				}
				
				return mat;
			}
		}
		
		public static 
		class Linear 
		extends Activation {
			Mat f(Mat mat) {
				return mat;
			}
		}
		
		public Mat W;
		public Mat b;
		public Activation act;
		
		Layer(Mat W, Mat b, Activation act) {
			this.W = W;
			this.b = b;
			this.act = act;
		}
	}
	
	private Layer[] _layers;

	public
	NN(Layer...layers) {
		this._layers = layers;
	}
	
	public
	Mat predict(Mat input) {
		
		for (int i = 0; i < this._layers.length; ++i) {
			Layer layer = this._layers[i];
			input = layer.act.f(input.matmul(layer.W).add(layer.b));
		}
		
		return input;
	}
}
