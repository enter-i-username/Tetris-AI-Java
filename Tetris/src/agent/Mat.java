package agent;

public
class Mat {
	public double[][] _mat;
	
	public
	Mat(double[][] mat) {
		this._mat = mat;
	}
	
	// ¾ØÕó³Ë·¨
	public
	Mat matmul(Mat mat) {
		Mat result = new Mat(new double[this._mat.length][mat._mat[0].length]);
		
		for (int i = 0; i < this._mat.length; ++i) {
			for (int j = 0; j < mat._mat[0].length; ++j) {
				for (int k = 0; k < this._mat[0].length; ++k) {
					result._mat[i][j] += this._mat[i][k] * mat._mat[k][j];
				}
			}
		}
		
		return result;
	}
	
	// ¾ØÕó¼Ó·¨
	public
	Mat add(Mat mat) {

		if (mat._mat.length == 1) {
			for (int i = 0; i < this._mat.length; ++i) {
				for (int j = 0; j < this._mat[0].length; ++j) {
					this._mat[i][j] += mat._mat[0][j];
				}
			}
		
		} else if (mat._mat[0].length == 1) {
			for (int i = 0; i < this._mat.length; ++i) {
				for (int j = 0; j < this._mat[0].length; ++j) {
					this._mat[i][j] += mat._mat[i][0];
				}
			}
			
		} else {
			for (int i = 0; i < this._mat.length; ++i) {
				for (int j = 0; j < this._mat[0].length; ++j) {
					this._mat[i][j] += mat._mat[i][j];
				}
			}
		}
		
		return this;
	}
	
	public
	void print() {
		for (int i = 0; i < this._mat.length; ++i) {
			for (int j = 0; j < this._mat[0].length; ++j) {
				System.out.printf("%f ", _mat[i][j]);
			}
			System.out.println();
		}
	}
}
