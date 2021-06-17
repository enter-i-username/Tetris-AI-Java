package core.modules.brick;

import core.brick.BrickInMap;

public 
class BrickModule {

	private BrickInMap _brick;
	private BrickInMap _next_brick;
	private int _no;
	private int _x_in_map;
	private int _y_in_map;
	private boolean _is_first = true;
	
	
	public
	BrickModule(int no, int x_in_map, int y_in_map) {
		this._no = no;
		this._x_in_map = x_in_map;
		this._y_in_map = y_in_map;
	}
	
	public
	BrickInMap brick() {
		return this._brick;
	}
	
	public 
	BrickInMap nextBrick() {
		return this._next_brick;
	}
	
	public
	void genBrick() {
		if (this._is_first) {
			this._brick = BrickInMap.generateRandomType(this._no, this._x_in_map, this._y_in_map);;
			this._is_first = false;
		} else {
			this._brick = this._next_brick;
		}
		
		this._next_brick = BrickInMap.generateRandomType(this._no, this._x_in_map, this._y_in_map);
	}
	
}
