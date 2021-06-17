package core.map;

public class Row {

	private Point[] _points;
	private boolean _is_checked;
	
	public
	Row(int cols) {
		this._points = new Point[cols];
		for (int i = 0; i < cols; ++i)
			this._points[i] = new Point();
		
		this._is_checked = false;
	}
	
	public 
	int num_cols() {
		return this._points.length;
	}
	
	public
	Point point(int col) {
		return this._points[col];
	}
	
	public 
	boolean isChecked() {
		return this._is_checked;
	}
	
	public 
	void clearChecked() {
		this._is_checked = false;
	}
	
	public 
	boolean checkRemovable() {
		for (int i = 0; i < this._points.length; ++i) 
			if (this._points[i].state != Point.State.STATIC) {
				this._is_checked = true;
				return false;
			}
				
		this._is_checked = true;
		return true;
	}
}
