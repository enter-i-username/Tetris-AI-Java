package core.map;

import core.data_structure.List;

public 
class Rows
extends List<Row> {
	
	private int _num_cols;
	
	public 
	Rows(int num_rows, int num_cols) {
		this._num_cols = num_cols;
		
		for (int i = 0; i < num_rows; ++i)
			this.append(new Row(num_cols));
	}
	
	public
	int num_rows() {
		return this.length();
	}
	
	public
	int num_cols() {
		return this._num_cols;
	}
	
	public
	Point point(int x, int y) {
		return this.elem(y).point(x);
	}
	
	public
	Row row(int y) {
		return this.elem(y);
	}
	
	public
	void prependRows(int num_rows) {
		for (int i = 0; i < num_rows; ++i) 
			this.prepend(new Row(this._num_cols));
	}
	
	public
	void removeRow(int row) {
		this.remove(row);
	}
}
