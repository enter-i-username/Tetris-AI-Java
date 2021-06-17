package core.modules.map;

import core.modules.MdlMsg;
import core.modules.Module;
import core.brick.BrickInMap;
import core.data_structure.List;
import core.modules.brick.*;
import core.modules.disp.DispMessage;
import core.modules.interation.InterMessage;
import core.modules.interation.InterModule;
import core.modules.timer.TimerModule;
import core.map.*;
import core.message.Message;


public 
class MapModule 
extends Module {
	public static
	class MapRegular
	extends Module.CheckEventRegularly {
		
		private BrickModule _brick_module;
		private boolean is_first = true;

		public 
		MapRegular(MapModule this_mod, BrickModule brick_module) {
			super(1, this_mod);
			MapModule map_module = (MapModule) this.this_mod;
			map_module._map = new Map(this_mod._num_rows, this_mod._num_cols);
			this._brick_module = brick_module;
		}

		@Override
		public 
		void callback() {
			
			MapModule map_module = (MapModule) this.this_mod;
			Message own_mail = this.this_mod.ownMail();
			List<Message> mails_to_send = this.this_mod.mailsToSend().values();
			
			// 初始化
			if (this.is_first) {
				this.is_first = false;
				
				// 找到display message和interaction message
				Message disp_msg = null;
				Message inter_msg = null;
				for (int i = 0; i < mails_to_send.length(); ++i) {
					if (mails_to_send.elem(i) instanceof DispMessage) {
						disp_msg = mails_to_send.elem(i);
					} else if (mails_to_send.elem(i) instanceof InterMessage) {
						inter_msg = mails_to_send.elem(i);
					}
				}
				
				// 生成新的方块
				this._brick_module.genBrick();
				map_module._map.updateState(this._brick_module.brick(), Point.State.DYNAMIC);
				map_module.next_brick = this._brick_module.nextBrick();
				
				// 发布生成方块消息
				disp_msg.post(
						new DispMessage.DispMessageContent<Map>
						(
							map_module, 
							DispMessage.DispMessageContent.Msgs.Disp_RegenBrick,
							new Map(map_module._map)
						)
				);
				inter_msg.post(
						new InterMessage.InterMessageContent<Map.MapBrick>
						(
							map_module, 
							InterMessage.InterMessageContent.Msgs.Inter_InputReceived,
							new Map.MapBrick(new Map(map_module._map), 
							new BrickInMap(this._brick_module.brick()))
						)
				);
			}
			
			// 清空队列
			if ( !own_mail.isEmpty() ) {
				// 判断消息是发给自己的
				if( own_mail.topMsg() instanceof MapMessage.MapMessageContent ) {
					Module mod = ( (MdlMsg.MdlMsgCtnt<?>) own_mail.topMsg() ).mod;
					// 判断消息的源头module
					if (mod instanceof InterModule 
						|| mod instanceof TimerModule) {
						// 接收消息
						MapMessage.MapMessageContent<?> msg_cntnt = (MapMessage.MapMessageContent<?>) own_mail.receive();
						byte motion = (byte) msg_cntnt.data;
						
						// 找到display message和interaction message
						Message disp_msg = null;
						Message inter_msg = null;
						for (int i = 0; i < mails_to_send.length(); ++i) {
							if (mails_to_send.elem(i) instanceof DispMessage) {
								disp_msg = mails_to_send.elem(i);
							} else if (mails_to_send.elem(i) instanceof InterMessage) {
								inter_msg = mails_to_send.elem(i);
							}
						}
						
						BrickInMap brick = this._brick_module.brick();
						
						if (brick != null) {
							// 若是crush
							if (motion == BrickInMap.Motion.CRUSH) {
								// 拷贝方块的图上坐标
								int type = brick.type();
								int pos  = brick.posture();
								int num_bricks = BrickInMap.brick_indices_by_type[type][pos].length;
								int[][] last_coors = new int[num_bricks][2];
								BrickInMap.int_array_copy(last_coors, brick.coorsInMap());
								// 直到静止，一直让方块下落
								map_module._map.crushBrick(brick);
								
								brick.setLastCoorsInMap(last_coors);
							} else {
								brick.motion(motion);
								brick.updateCoorsInMap();
							}
							
							// 地图上更新
							if (map_module.updateBrickMotionInMap(brick, disp_msg)) {
								// 生成新的方块
								this._brick_module.genBrick();
								map_module._map.updateState(this._brick_module.brick(), Point.State.DYNAMIC);
								map_module.next_brick = this._brick_module.nextBrick();
								
								// 发布生成方块消息
								disp_msg.post(
										new DispMessage.DispMessageContent<Map>
										(
											map_module, 
											DispMessage.DispMessageContent.Msgs.Disp_RegenBrick,
											new Map(map_module._map)
										)
								);
								inter_msg.post(
										new InterMessage.InterMessageContent<Map.MapBrick>
										(
											map_module, 
											InterMessage.InterMessageContent.Msgs.Inter_InputReceived,
											new Map.MapBrick(new Map(map_module._map), new BrickInMap(this._brick_module.brick()))
										)
								);
							}
						}
						
					}
					
				} else {
					// 不是发给自己的不管这则消息
					own_mail.receive();
				}
			}
			
		}
	}
	
	
	private int _num_rows;
	private int _num_cols;
	private Map _map;
	public  BrickInMap next_brick;
	@SuppressWarnings("unused")
	private BrickModule[] _brick_modules;
	
	public
	MapModule(long check_period_in_millis, int num_rows, int num_cols, BrickModule...brick_modules) {
		this._num_rows = num_rows;
		this._num_cols = num_cols;
		this._brick_modules = brick_modules;
		this.setupTimer(check_period_in_millis, 
				new MapRegular(this, brick_modules[0]));
	}
	
	public
	boolean updateBrickMotionInMap(BrickInMap brick, Message disp_msg)
	{	
		//brick.updateCoorsInMap();

		boolean isRegen = false;
		
		// 检查到方块碰边（自动撤销方块动作）
		if ( this._map.checkBoundary(brick) ) {
			// 撤销该操作
			brick.undo();
			// 发布碰边消息
			this._map.setCoors(brick.coorsInMap());
			this._map.setLastCoors(brick.lastCoorsInMap());
						
			disp_msg.post(
				new DispMessage.DispMessageContent<Map>
				(
					this, 
					DispMessage.DispMessageContent.Msgs.Disp_BrickOutOfBoundary,
					new Map(this._map)
				)
			);
		} else {
			// 检查状态为可动
			if ( this._map.checkDynamic(brick) ) {
				// 更新方块为可动状态
				this._map.updateState(brick, Point.State.DYNAMIC + brick.no());
				// 发布方块位置更新消息
				this._map.setCoors(brick.coorsInMap());
				this._map.setLastCoors(brick.lastCoorsInMap());

				disp_msg.post(
						new DispMessage.DispMessageContent<Map>
						(
							this, 
							DispMessage.DispMessageContent.Msgs.Disp_UpdateBrick,
							new Map(this._map)
						)
				);
				
			} else {
				// 返回值作为底层Map向高层MapModule的通信
				isRegen = true;
				
				// 更新方块为静止状态
				this._map.updateState(brick, Point.State.STATIC);
				
				// 游戏结束
				if (this._map.checkGameOver(brick)) {
					disp_msg.post(
							new DispMessage.DispMessageContent<Map>
							(
								this, 
								DispMessage.DispMessageContent.Msgs.Disp_GameOver,
								new Map(this._map)
							)
					);
					// 不进行了
					while (true);
				} else {
					// 给显示消息邮箱发布方块静止消息
					this._map.setCoors(brick.coorsInMap());

					disp_msg.post(
							new DispMessage.DispMessageContent<Map>
							(
								this, 
								DispMessage.DispMessageContent.Msgs.Disp_BrickGetsStatic,
								new Map(this._map)
							)
					);
					
					// 检查方块造成行满行
					if ( this._map.checkRemove(brick) ) {
						// 发布清除满行消息
						disp_msg.post(
								new DispMessage.DispMessageContent<Map>
								(
									this, 
									DispMessage.DispMessageContent.Msgs.Disp_RemoveFullRows,
									new Map(this._map)
								)
						);
						// 清除满行
						this._map.removeFullRows();
					}
				}
			}
		}
		
		return isRegen;
	}
}
