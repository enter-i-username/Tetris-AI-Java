package core.brick;

import java.util.Random;


public class BrickInMap 
extends BasicBrick {
	
	public static 
	class Motion {
		public static final byte UP    			= 1;
		public static final byte DOWN  			= -1;
		public static final byte LEFT  			= 2;
		public static final byte RIGHT 			= -2;
		public static final byte CLOCKWISE 		= 3;
		public static final byte ANTICLOCKWISE 	= -3;
		public static final byte NONE  			= 0;
		public static final byte CRUSH			= 4;
	}
	
	private int _x_org;
	private int _y_org;
	private int _x;
	private int _y;
	private int _no;
	private int _last_motion;
	private int[][] _last_coors_in_map;
	private int[][] _coors_in_map;

	// 随机方块发生器
	private static Random _rand = new Random();
	public static
	BrickInMap generateRandomType(int no, int x_in_map, int y_in_map) {
		
		// 随机产生一个方块
		int random_type = Math.abs(BrickInMap._rand.nextInt() % BasicBrick.brick_indices_by_type.length);
		
		return new BrickInMap(random_type, 
							  0,
							  no,
							  x_in_map, 
							  y_in_map);
		/*// 随机产生7种方块的顺序
		if (_rand_orders.length() == 0) {
			List<Integer> list = new List<Integer>();
			for (int i = 0; i < BasicBrick.brick_indices_by_type.length; i++)
				list.append(i);
				
			// 将有序序列打乱
			while (list.length() > 0) {
				
				// 产生list中的随机索引 
				int randIndex =  Math.abs(BrickInMap._rand.nextInt() % list.length());
				
				// 按随机索引取出来 
				int popElem = list.remove(randIndex);
		
				// 加入乱序序列 
				_rand_orders.append(popElem);
			} 
		}
		
		return new BrickInMap(_rand_orders.remove(0), 
							  0,
							  no,
							  x_in_map, 
							  y_in_map);
		*/
	}
	
	public
	BrickInMap(int brick_type, int pos, int no, int x_in_map, int y_in_map) {
		super(brick_type);
		this._x = x_in_map;
		this._y = y_in_map;
		this._x_org = x_in_map;
		this._y_org = y_in_map;
		this._no = no;
		
		int type = this.type();
		this._brick_posture = pos;
		int num_bricks = BrickInMap.brick_indices_by_type[type][pos].length;
		this._coors_in_map = new int[num_bricks][2];
		this._last_coors_in_map = new int[num_bricks][2];
		
		this.updateCoorsInMap();
		int_array_copy(this._last_coors_in_map, this._coors_in_map);
	}
	// 深拷贝构造函数
	public
	BrickInMap(BrickInMap copy) {
		super(copy.type());
		this._x_org = copy._x_org;
		this._y_org = copy._y_org;
		this._x = copy._x;
		this._y = copy._y;
		this._no = copy._no;
		this._last_motion = copy._last_motion;
		
		int type = this.type();
		this._brick_posture = copy.posture();
		int num_bricks = BrickInMap.brick_indices_by_type[type][this._brick_posture].length;
		this._coors_in_map = new int[num_bricks][2];
		this._last_coors_in_map = new int[num_bricks][2];
		
		
		int_array_copy(_coors_in_map, copy.coorsInMap());
		int_array_copy(_coors_in_map, copy.lastCoorsInMap());
	}
	
	public 
	int x_org() {
		return this._x_org;
	}
	public
	int y_org() {
		return this._y_org;
	}
	public
	int x() {
		return this._x;
	}
	
	public 
	int y() {
		return this._y;
	}
	
	public
	int no() {
		return this._no;
	}
	
	public
	int lastMotion() {
		return this._last_motion;
	}
	
	public
	int[][] coorsInMap() {
		return this._coors_in_map;
	}
	public
	int[][] lastCoorsInMap() {
		return this._last_coors_in_map;
	}
	
	public
	void motion(int motion) {
		switch (motion) {
			case Motion.UP: {
				--this._y; 
				break;
			}
			case Motion.LEFT: {
				--this._x;
				break;
			}
			case Motion.RIGHT: {
				++this._x;
				break;
			}
			case Motion.DOWN: {
				++this._y;
				break;
			}
			case Motion.CLOCKWISE: {
				if (++this._brick_posture >= 
						BasicBrick.brick_indices_by_type[this.type()].length) {
					this._brick_posture = 0;
				}
				break;
			}
			case Motion.ANTICLOCKWISE: {
				if (--this._brick_posture < 0) {
					this._brick_posture = BasicBrick.brick_indices_by_type[this.type()].length - 1;
				}
				break;
			}
			default: break;
		}
		
		this._last_motion = motion;
	}
	
	public
	void updateCoorsInMap() {
		
		int_array_copy(this._last_coors_in_map, this._coors_in_map);
		
		int type = this.type();
		int pos  = this.posture();
		int num_bricks = BrickInMap.brick_indices_by_type[type][pos].length;
		for (int i = 0; i < num_bricks; ++i) {
			int x = BrickInMap.brick_indices_by_type[type][pos][i][0];
			int y = BrickInMap.brick_indices_by_type[type][pos][i][1];
			
			x += this.x();
			y += this.y();
			
			this._coors_in_map[i][0] = x;
			this._coors_in_map[i][1] = y;
		}
	}
	
	public 
	void setLastCoorsInMap(int[][] last_coors) {
		int_array_copy(this._last_coors_in_map, last_coors);
	}
	
	public
	void undo() {
		this.motion(-this._last_motion);
		this._last_motion = Motion.NONE;
		
		int_array_copy(this._coors_in_map, this._last_coors_in_map);
	}
	
	public static 
	void int_array_copy(int[][] dst, int[][] src) {
		for (int i = 0; i < src.length; ++i)
			for (int j = 0; j < src[i].length; ++j)
				dst[i][j] = src[i][j];
	}
}