package core.map;

import core.brick.*;
import core.data_structure.List;
import core.display.Display;

public 
class Map 
extends Rows {
	
	public static 
	class MapBrick {
		public Map map;
		public BrickInMap brick;
		
		public 
		MapBrick(Map map, BrickInMap brick) {
			this.map = map;
			this.brick = brick;
		}
	}

	private List<int[]> _remove_list;
	private int[][] _coors;
	private int[][] _last_coors;
	public  int score;
	
	public
	Map(int num_rows, int num_cols) {
		super(num_rows, num_cols);
		this._remove_list = null;
	}
	
	// 深拷贝构造函数
	public
	Map(Map copy) {
		super(copy.num_rows(), copy.num_cols());
		for (int i = 0; i < this.num_cols(); ++i) {
			for (int j = 0; j < this.num_rows(); ++j) {
				this.point(i, j).state = copy.point(i, j).state;
			}
		}
		
		if (copy._remove_list != null) {
			this._remove_list = new List<int[]>();
			for (int i = 0; i < copy._remove_list.length(); ++i) 
				this._remove_list.append(copy._remove_list.elem(i));
		}
		
		if (copy._coors != null 
			&& copy._last_coors != null) {
			this._coors = new int[copy._coors.length][copy._coors[0].length];
			this._last_coors = new int[copy._last_coors.length][copy._last_coors[0].length];
			BrickInMap.int_array_copy(this._last_coors, copy._last_coors);
			BrickInMap.int_array_copy(this._coors, copy._coors);
		}
		
		this.score = copy.score;
		
	}
	
	public 
	void disp() {
		int[][] map = Display.copyMap(this);
		
		if (map != null) {
			String clear_screen = "";
			String pic = "";
			
			for (int i = 0; i < this.num_rows() + 1; ++i)
				clear_screen += '\n';
			
			
			for (int i = 0; i < this.num_cols() + 2; ++i) {
				pic += "00";
			}
			pic += '\n';
			
			for (int i = 0; i < this.num_rows(); ++i) {
				pic += "||";
				for (int j = 0; j < this.num_cols(); ++j) {
					switch (map[j][i]) {
					case Point.State.BLANK: pic += "  "; break;
					case Point.State.STATIC: pic += "[]"; break;
					case Point.State.DYNAMIC: pic += "()"; break;
					case Display.State.NEW_BLANK: pic += "  "; break;
					case Display.State.CROSS: pic += "XX"; break;
					}
				}
				pic += "||";
				pic += '\n';
			}
			
			
			for (int i = 0; i < this.num_cols() + 2; ++i) {
				pic += "00";
			}
			pic += '\n';
			
			System.out.print(clear_screen + pic);
		}
	}
	
	public 
	void setCoors(int[][] coors) {
		this._coors = coors;
	}
	
	public 
	void setLastCoors(int[][] last_coors) {
		this._last_coors = last_coors;
	}

	public
	int[][] coors() {
		return this._coors;
	}
	
	public
	int[][] lastCoors() {
		return this._last_coors;
	}
	
	public
	List<int[]> removeList() {
		return this._remove_list;
	}
	
	public 
	void crushBrick(BrickInMap brick) {
		while ( this.checkDynamic(brick) ) {
			brick.motion(BrickInMap.Motion.DOWN);
			brick.updateCoorsInMap();
		}
	}
	
	public
	boolean checkBoundary(BrickInMap brick) {
		int[][] coors = brick.coorsInMap();
		
		if (coors != null) {
			for (int i = 0; i < coors.length; ++i) {
				int x = coors[i][0];
				int y = coors[i][1];
				
				// 确认边界
				if (x >= 0
					&& x < this.num_cols()
					  && y >= 0
					    && y < this.num_rows()) {
					// 确认不碰见其他方块
					if (this.point(x, y).state == Point.State.BLANK 
					    || this.point(x, y).state == Point.State.DYNAMIC + brick.no() )
						continue;
					else
						return true;
				}
				else
					return true;
			}
			return false;
		}
		else
			return false;
	}
	
	public 
	boolean checkDynamic(BrickInMap brick) {
		int[][] coors = brick.coorsInMap();

		if (coors != null) {
			if (brick.lastMotion() == BrickInMap.Motion.DOWN) {
				for (int i = 0; i < coors.length; ++i) {
					int x = coors[i][0];
					int y = coors[i][1];
					
					if (y == this.num_rows() - 1
						|| this.point(x, y+1).state == Point.State.STATIC) 
						return false;
				}
				return true;
			}
			else 
				return true;
		}
		else 
			return true;
	}
	
	public
	boolean checkRemove(BrickInMap brick) {
		int[][] coordinates = brick.coorsInMap();
		this._remove_list = new List<int[]>();
		
		// 检查
		for (int i = 0; i < coordinates.length; ++i) {
			int y = coordinates[i][1];
			
			// 若刚刚没有被检查过才检查
			if ( !this.row(y).isChecked() ) {
				// 若一行满了，加入待移除列表
				if ( this.row(y).checkRemovable() )
					this._remove_list.append(new int[] {y});
			}
		}
		// 清空刚刚的已检查
		for (int i = 0; i < coordinates.length; ++i) {
			int y = coordinates[i][1];
			this.row(y).clearChecked();
		}
		
		this.score += this._remove_list.length();
		
		return this._remove_list.length() > 0
				? true
					: false;
 	}
	
	public 
	boolean checkGameOver(BrickInMap brick) {
		int[][] coors = brick.coorsInMap();
		for (int i = 0; i < coors.length; ++i) {
			if (coors[i][1] <= 4
				&& this.point(coors[i][0], coors[i][1]).state == Point.State.STATIC)
				return true;
		}
		return false;
	}
	
	public
	void removeFullRows() {
		if (this._remove_list != null) {
			while (this._remove_list.length() != 0) {
				//System.out.println(this._remove_list.elem(0)[0]);
				this.removeRow(this._remove_list.remove(0)[0]);
				this.prependRows(1);
			}
			
		}
		
		this._remove_list = null;
	}
	
	
	
	public 
	void updateState(BrickInMap brick, int state) {
		this._setCoorsState(Point.State.BLANK, brick.lastCoorsInMap());
		this._setCoorsState(state, brick.coorsInMap());
	}
																													
	private
	void _setCoorsState(int state, int[][] coors) {
		if (coors != null) {
			for (int i = 0; i < coors.length; ++i) {
				int x = coors[i][0];
				int y = coors[i][1];
				this.point(x, y).state = state;
			}
		}
	}
	
}

