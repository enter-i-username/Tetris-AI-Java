package agent;

import agent.NN.Layer;
import core.brick.BrickInMap;
import core.interaction.Interaction;
import core.map.Map;
import core.map.Point;
import core.data_structure.*;

public 
class Agent
extends Interaction {
	
	private NN _neural_network;
	
	public 
	Agent(char[] keys, Interaction.Callback cbk) {
		super(keys, cbk);
		
		this._neural_network = new NN(
				new Layer(Weights.W1, Weights.b1, new NN.Layer.ReLU()),
				new Layer(Weights.W2, Weights.b2, new NN.Layer.ReLU()),
				new Layer(Weights.W3, Weights.b3, new NN.Layer.Linear())
		);
		
	}
	
	public
	int[] getBestStrategy(Map map, BrickInMap brick) {
		double max_posi = 0.;
		int[] strategy = new int[2];
		
		int type_ = brick.type();
		int pos_  = brick.posture();
		int num_bricks = BrickInMap.brick_indices_by_type[type_][pos_].length;
		int[][] last_coors = new int[num_bricks][2];
		BrickInMap.int_array_copy(last_coors, brick.coorsInMap());

		int type = brick.type();
		for (int pos = 0; pos < BrickInMap.brick_indices_by_type[type].length; ++pos) {
			
			byte[] max_min = this.max_min(BrickInMap.brick_indices_by_type[type][pos]);
			for (int x_in_map = -max_min[1]; x_in_map < map.num_cols() - max_min[0]; ++x_in_map) {
				// 创建此种情况的方块，并更新在地图上的坐标
				BrickInMap brick_new = new BrickInMap(type, pos, brick.no(), x_in_map, 0);
				Map map_new = new Map(map);
				brick_new.updateCoorsInMap();
				
				// 直到静止，一直让方块下落
				map_new.crushBrick(brick_new);
				brick_new.setLastCoorsInMap(last_coors);
				map_new.updateState(brick_new, Point.State.STATIC + brick_new.no());
				map_new.checkRemove(brick_new);
				
				// 构造4维特征
				Mat feature = new Mat(new double[][] {
					{
						(double) this.clear_lines(map_new),			// 得到消除行数
						(double) this.number_of_holes(map_new),		// 得到消除后空缺的方块数目
						(double) this.bumpiness(map_new),			// 得到所有相邻两列的上空格数差的绝对值和
						(double) this.height(map_new),				// 得到所有列的最高高度和
					}
				});
				
				// 神经网络预测概率
				double posibility = this._neural_network.predict(feature)._mat[0][0];
				posibility = Math.exp(posibility);
				
				// 取出预测可能性最大的策略作为最优策略
				if (posibility > max_posi) {
					max_posi = posibility;
					strategy[0] = pos;
					strategy[1] = x_in_map;
				}
			}
		}
		
		return strategy;
	}
	
	
	private
	byte[] max_min(byte[][] brick_indices) {
		byte[] maxmin = new byte[2];
		maxmin[1] = (byte)100;
		
		for (int i = 0; i < brick_indices.length; ++i) {
			if (brick_indices[i][0] > maxmin[0]) {
				maxmin[0] = brick_indices[i][0];
			} else if (brick_indices[i][0] < maxmin[1]) {
				maxmin[1] = brick_indices[i][0];
			}
		}
		
		return maxmin;
	}
	
	
	private
	int clear_lines(Map map) {
		return map.removeList().length();
	}
	
	private
	int number_of_holes(Map map) {
		int num_cols = map.num_cols();
		int num_rows = map.num_rows();
		int num_holes = 0;
		
		for (int col = 0; col < num_cols; ++col) {
			int i = 0;
            while (i < num_rows
            	   && map.point(col, i).state != Point.State.STATIC)
                i += 1;
            for (int x = i+1; x < num_rows; ++x)
            	if (map.point(col, x).state == Point.State.BLANK)
            		num_holes += 1;
		}
		
		return num_holes;
	}
	
	private
	int bumpiness(Map map) {
		int num_cols = map.num_cols();
		int num_rows = map.num_rows();
		int num_bumpiness = 0;
		List<Integer> min_ys = new List<Integer>();
		
		for (int col = 0; col < num_cols; ++col) {
			int i = 0;
            while (i < num_rows
            	   && map.point(col, i).state != Point.State.STATIC)
                i += 1;
            min_ys.append(i);
		}
		
		int length = min_ys.length() - 1;
		for (int i = 0; i < length; ++i) {
			num_bumpiness += Math.abs(min_ys.elem(i) - min_ys.elem(i+1));
		}
		
		return num_bumpiness;
	}
	
	private
	int height(Map map) {
		int num_cols = map.num_cols();
		int num_rows = map.num_rows();
		int num_height = 0;
		
		for (int col = 0; col < num_cols; ++col) {
			int i = 0;
            while (i < num_rows
            	   && map.point(col, i).state == Point.State.BLANK)
                i += 1;
            num_height += num_rows - i;
		}
		
		return num_height;
	}
	
	
	@Override
	public void checkInputInBlockingMode() {
		// 啥都不做
	}
}
