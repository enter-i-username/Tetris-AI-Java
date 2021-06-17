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
			
			// ��ʼ��
			if (this.is_first) {
				this.is_first = false;
				
				// �ҵ�display message��interaction message
				Message disp_msg = null;
				Message inter_msg = null;
				for (int i = 0; i < mails_to_send.length(); ++i) {
					if (mails_to_send.elem(i) instanceof DispMessage) {
						disp_msg = mails_to_send.elem(i);
					} else if (mails_to_send.elem(i) instanceof InterMessage) {
						inter_msg = mails_to_send.elem(i);
					}
				}
				
				// �����µķ���
				this._brick_module.genBrick();
				map_module._map.updateState(this._brick_module.brick(), Point.State.DYNAMIC);
				map_module.next_brick = this._brick_module.nextBrick();
				
				// �������ɷ�����Ϣ
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
			
			// ��ն���
			if ( !own_mail.isEmpty() ) {
				// �ж���Ϣ�Ƿ����Լ���
				if( own_mail.topMsg() instanceof MapMessage.MapMessageContent ) {
					Module mod = ( (MdlMsg.MdlMsgCtnt<?>) own_mail.topMsg() ).mod;
					// �ж���Ϣ��Դͷmodule
					if (mod instanceof InterModule 
						|| mod instanceof TimerModule) {
						// ������Ϣ
						MapMessage.MapMessageContent<?> msg_cntnt = (MapMessage.MapMessageContent<?>) own_mail.receive();
						byte motion = (byte) msg_cntnt.data;
						
						// �ҵ�display message��interaction message
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
							// ����crush
							if (motion == BrickInMap.Motion.CRUSH) {
								// ���������ͼ������
								int type = brick.type();
								int pos  = brick.posture();
								int num_bricks = BrickInMap.brick_indices_by_type[type][pos].length;
								int[][] last_coors = new int[num_bricks][2];
								BrickInMap.int_array_copy(last_coors, brick.coorsInMap());
								// ֱ����ֹ��һֱ�÷�������
								map_module._map.crushBrick(brick);
								
								brick.setLastCoorsInMap(last_coors);
							} else {
								brick.motion(motion);
								brick.updateCoorsInMap();
							}
							
							// ��ͼ�ϸ���
							if (map_module.updateBrickMotionInMap(brick, disp_msg)) {
								// �����µķ���
								this._brick_module.genBrick();
								map_module._map.updateState(this._brick_module.brick(), Point.State.DYNAMIC);
								map_module.next_brick = this._brick_module.nextBrick();
								
								// �������ɷ�����Ϣ
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
					// ���Ƿ����Լ��Ĳ���������Ϣ
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
		
		// ��鵽�������ߣ��Զ��������鶯����
		if ( this._map.checkBoundary(brick) ) {
			// �����ò���
			brick.undo();
			// ����������Ϣ
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
			// ���״̬Ϊ�ɶ�
			if ( this._map.checkDynamic(brick) ) {
				// ���·���Ϊ�ɶ�״̬
				this._map.updateState(brick, Point.State.DYNAMIC + brick.no());
				// ��������λ�ø�����Ϣ
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
				// ����ֵ��Ϊ�ײ�Map��߲�MapModule��ͨ��
				isRegen = true;
				
				// ���·���Ϊ��ֹ״̬
				this._map.updateState(brick, Point.State.STATIC);
				
				// ��Ϸ����
				if (this._map.checkGameOver(brick)) {
					disp_msg.post(
							new DispMessage.DispMessageContent<Map>
							(
								this, 
								DispMessage.DispMessageContent.Msgs.Disp_GameOver,
								new Map(this._map)
							)
					);
					// ��������
					while (true);
				} else {
					// ����ʾ��Ϣ���䷢�����龲ֹ��Ϣ
					this._map.setCoors(brick.coorsInMap());

					disp_msg.post(
							new DispMessage.DispMessageContent<Map>
							(
								this, 
								DispMessage.DispMessageContent.Msgs.Disp_BrickGetsStatic,
								new Map(this._map)
							)
					);
					
					// ��鷽�����������
					if ( this._map.checkRemove(brick) ) {
						// �������������Ϣ
						disp_msg.post(
								new DispMessage.DispMessageContent<Map>
								(
									this, 
									DispMessage.DispMessageContent.Msgs.Disp_RemoveFullRows,
									new Map(this._map)
								)
						);
						// �������
						this._map.removeFullRows();
					}
				}
			}
		}
		
		return isRegen;
	}
}
