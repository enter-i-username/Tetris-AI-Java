package core.display;

import core.brick.BrickInMap;
import core.data_structure.List;
import core.map.Map;
import core.map.Point;

public abstract
class Display {

	public 
	enum DispEvent {
		DispMap, 			// ��������ͼ
		DispCollision,		// ��������Ч��
		DispRemoveRows,		// ����������Ч��
		DispUpdateBrick,	// ���Ʒ����ƶ�
		DispStaticBrick,	// ���Ʒ��龲ֹ
		DispGenBrick,		// �������ɷ���
	}
	
	public static
	class State {
		public static final int NEW_BLANK   = 3;
		public static final int CROSS    	= 4;
	}
	
	class Step {
		private int[][] int_map;
		private int ticks;
		Step(int[][] int_map, int ticks) {
			this.int_map = int_map;
			this.ticks = ticks;
		}
	}
	
	protected List<List<Step>> disp_chain;
	protected Map map;
	
	public
	Display() {
		this.disp_chain = new List<List<Step>>();
	}
	
	public static 
	int[][] copyMap(Map map) {
		int[][] dst = new int[map.num_cols()][map.num_rows()];
		
		for (int i = 0; i < map.num_cols(); ++i) {
			for (int j = 0; j < map.num_rows(); ++j) {
				dst[i][j] = map.point(i, j).state;
			}
		}
		return dst;
	}
	
	// ���һ�����ƽڵ㣬�ڵ���һЩ���Ʋ������
	public
	void addElem(Map map, DispEvent event) {
		this.map = map;
		List<Step> map_chain = new List<Step>();
		int[][] coors = map.coors();
		//int[][] last_coors = map.lastCoors();
		List<int[]> remove_list = map.removeList();
				
		switch (event) {
		case DispRemoveRows:	// ��������
			List<int[]> remove_coors = new List<int[]>();
			if (remove_list != null) {
				// ���ϵ��£������Ұ�ɾ��������ӽ�remove_coors
				for (int i = 0; i < remove_list.length(); ++i) {
					int y = remove_list.elem(i)[0];
					for (int x = 0; x < map.num_cols(); ++x) {
						remove_coors.append(new int[] {x, y});
						map.point(x, y).state = State.NEW_BLANK;
					}
				}
				int length = remove_coors.length();
				for (int i = 0; i < length; ++i) {
					remove_coors.remove(0);
					int[][] int_map = Display.copyMap(map);
					for (int j = 0; j < remove_coors.length(); ++j) {
						int[] coor = remove_coors.elem(j);
						int_map[coor[0]][coor[1]] = State.CROSS;
					}
					map_chain.append(new Step(int_map, 2));
				}
				
				this.disp_chain.append(map_chain);
			}
			break;
		case DispStaticBrick:	// ���龲ֹ
			// ���齥��Ч��
			if (coors != null) {
				
				List<int[]> coor_list = new List<int[]>();
				for (int i = 0; i < coors.length; ++i) { 
					coor_list.append(coors[i]);
				}
				for (int i = 0; i < coors.length; ++i) {
					int[][] int_map = Display.copyMap(map);
					coor_list.remove(0);
					for (int j = 0; j < coor_list.length(); ++j) {
						int[] coor = coor_list.elem(j);
						int_map[coor[0]][coor[1]] = Point.State.DYNAMIC;
					}
					map_chain.append(new Step(int_map, 4));
				}
				
				this.disp_chain.append(map_chain);
			}
			
			break;
		case DispCollision:		// ��������
			if (coors != null) {
				int[][] int_map = Display.copyMap(map);
				
				for (int i = 0; i < coors.length; ++i) { 
					if (coors[i][0] == 0
						|| coors[i][0] == map.num_cols() - 1
						  || coors[i][1] == 0
							|| coors[i][1] == map.num_rows() - 1) {
						int_map[coors[i][0]][coors[i][1]] = State.CROSS;
					}
					map_chain.append(new Step(int_map, 1));
					
				}
				
				this.disp_chain.append(map_chain);
			}
			break;
		case DispUpdateBrick:
			// ��һ��map���������Ϊ����
			int[][] int_map = Display.copyMap(map);
			map_chain.append(new Step(int_map, 1));
			
			if (this.disp_chain.length() > 0) {
				this.disp_chain.remove(0);
			}
			this.disp_chain.prepend(map_chain);
			
			break;
		case DispGenBrick:
			// ��һ��map���������Ϊ����
			int[][] int_map_ = Display.copyMap(map);
			map_chain.append(new Step(int_map_, 1));
						
			if (this.disp_chain.length() > 0) {
				this.disp_chain.remove(0);
			}
			this.disp_chain.prepend(map_chain);
						
			break;
		default:
			break;
		}
		
	}
	
	
	// ������������нڵ�ĵ�һ�����裬���Ѿ���ղ���Ľڵ�ɾ��
	// �����еĵ�һ����������ں�
	// �ںϷ�ʽΪ��ȡ���ֵ��Ϊ�����ص��ں�ֵ
	protected 
	int[][] fusedPixels(int num_rows, int num_cols) {
		int[][] pixels;
		
		
		if (this.disp_chain.length() > 0) {
			//System.out.println(this.disp_chain.length());
			
			pixels = new int[num_cols][num_rows];
			// ����������
			for (int i = 1; i < this.disp_chain.length(); ++i) {
				// ����һ�������ticks==0���߳�
				Step step0 = this.disp_chain.elem(i).elem(0);
				int[][] map_pixels = step0.int_map;
				if (--step0.ticks <= 0)
					this.disp_chain.elem(i).remove(0);
				// ����һ�������ÿ����������ȡ���ֵ
				for (int j = 0; j < map_pixels.length; ++j) {
					for (int k = 0; k < map_pixels[0].length; ++k) {
						pixels[j][k] = this.max(map_pixels[j][k], pixels[j][k]);
					}
				}
				// ���ýڵ�Ĳ���գ����߳�
				if (this.disp_chain.elem(i).length() == 0) {
					this.disp_chain.remove(i);
					--i;
				}
			}
			
			// ���ϱ���
			Step step0 = this.disp_chain.elem(0).elem(0);
			int[][] map_pixels = step0.int_map;
			for (int j = 0; j < map_pixels.length; ++j) {
				for (int k = 0; k < map_pixels[0].length; ++k) {
					pixels[j][k] = this.max(map_pixels[j][k], pixels[j][k]);
				}
			}
			
		} else {
			pixels = null;
		}
		
		return pixels;
	}

	private
	int max(int x, int y) {
		return x > y ? x : y;
	}
	
	/**
	  * �ӿں���
	  * 
	  * @param num_rows -��ͼ������
	  * @param num_cols -��ͼ������
	  * @param score -Ŀǰ��÷���
	  * @param is_hosted_mode -�Ƿ����й�ģʽ
	  * @param next_brick -��һ����
	  * @param is_game_over -��Ϸ�Ƿ����
	  */
	public abstract
	void display(int num_rows, int num_cols, int score, boolean is_hosted_mode, BrickInMap next_brick, boolean is_game_over);

}
