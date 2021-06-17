package core.modules.interation;


import agent.Agent;
import core.brick.BrickInMap;
import core.data_structure.List;
import core.interaction.Interaction;
import core.map.Map;
import core.modules.MdlMsg;
import core.modules.Module;
import core.modules.disp.DispMessage;
import core.modules.map.MapMessage;
import core.modules.map.MapModule;
import core.timer.Timer;
import keyboard.KeyBrickEvent;
import core.message.*;

public 
class InterModule 
extends Module {
	
	// 定时器的回调函数
	public static
	class InterRegular
	extends Module.CheckEventRegularly {
		
		// 在线程里又创一个线程，来阻塞监视
		class InputChecker 
		extends Thread {
			public InterModule inter_module;
			
			InputChecker(InterModule inter_module) {
				this.inter_module = inter_module;
			}
			
			@Override
			public 
			void run() {
				this.inter_module._inter.checkInputInBlockingMode();
			}
		}
		
		private boolean is_first = true;
		
		public InterRegular(InterModule this_mod) {
			super(1, this_mod);
		}
		
		@Override
		public 
		void callback() {
			
			// 开启监视线程
			if (this.is_first) {
				// 应该在注册map的邮箱后才进行这一步
				InterModule inter_module = (InterModule) this.this_mod;
				
				List<Message> mails_to_send = inter_module.mailsToSend().values();
				
				// 找到message
				Message disp_msg = null;
				Message map_msg = null;
				for (int i = 0; i < mails_to_send.length(); ++i) {
					if (mails_to_send.elem(i) instanceof DispMessage) {
						disp_msg = mails_to_send.elem(i);
					} else if (mails_to_send.elem(i) instanceof MapMessage) {
						map_msg = mails_to_send.elem(i);
					}
				}
				
				// 键盘
				inter_module._inter = new KeyBrickEvent(
					inter_module._keys, 
					new MsgPoster(inter_module, map_msg, disp_msg)
				);
				
				 // 机器
				inter_module._ai = new Agent(
					inter_module._keys, 
					new MsgPoster(inter_module, map_msg, disp_msg)
				);
				
				
				new InputChecker((InterModule) this.this_mod).start();
				
				this.is_first = false;
			}
			
			/////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////
			// 下面写其他代码
			InterModule inter_module = (InterModule) this.this_mod;
			
			// 找到message
			List<Message> mails_to_send = inter_module.mailsToSend().values();
			Message map_msg = null;
			for (int i = 0; i < mails_to_send.length(); ++i) {
				if (mails_to_send.elem(i) instanceof MapMessage) {
					map_msg = mails_to_send.elem(i);
				}
			}
			
			Agent agent = (Agent) inter_module._ai;
			Message own_mail = this.this_mod.ownMail();
			Timer timer = new Timer();
			
			// 清空队列
			while ( !own_mail.isEmpty() ) {
				// 判断消息是发给自己的
				if( own_mail.topMsg() instanceof InterMessage.InterMessageContent ) {
					Module mod = ( (MdlMsg.MdlMsgCtnt<?>) own_mail.topMsg() ).mod;
					// 判断消息的源头module
					if (mod instanceof MapModule
						|| mod instanceof InterModule) {
						// 接收消息
						InterMessage.InterMessageContent<?> msg_cntnt = (InterMessage.InterMessageContent<?>) own_mail.receive();
						Map.MapBrick mb = (Map.MapBrick) msg_cntnt.data;
						int[] strategy = agent.getBestStrategy(mb.map, mb.brick);
						
						// 托管模式下才开启
						if (inter_module._is_hosted_mode) {
							timer.sleep(500);
							
							// 旋转策略
							while (mb.brick.posture() != strategy[0]) {
								mb.brick.motion(BrickInMap.Motion.CLOCKWISE);
								map_msg.post(
										new MapMessage.MapMessageContent<Byte>
										(
											this.this_mod, 
											MapMessage.MapMessageContent.Msgs.Map_BrickMoved,
											BrickInMap.Motion.CLOCKWISE
										)
								);
								
								timer.sleep(200);
							}
							
							// 平移策略
							byte motion = strategy[1] - mb.brick.x() > 0 ? BrickInMap.Motion.RIGHT : BrickInMap.Motion.LEFT;
							int nb_moves = Math.abs(strategy[1] - mb.brick.x());
							
							for (int i = 0; i < nb_moves; ++i) {
								map_msg.post(
										new MapMessage.MapMessageContent<Byte>
										(
											this.this_mod, 
											MapMessage.MapMessageContent.Msgs.Map_BrickMoved,
											motion
										)
								);
								
								timer.sleep(200);
							}
							
							// 坠机
							map_msg.post(
									new MapMessage.MapMessageContent<Byte>
									(
										this.this_mod, 
										MapMessage.MapMessageContent.Msgs.Map_BrickMoved,
										BrickInMap.Motion.CRUSH
									)
							);
						}
					}
				} else {
					// 不是发给自己的不管这则消息
					own_mail.receive();
				}
			}
		}
	}
	
	// 向地图发布消息的回调函数
	static
	class MsgPoster
	implements Interaction.Callback {
		
		public Message map_msg;
		public Module this_mod;
		public Message disp_msg;
		
		public
		MsgPoster(Module this_mod, Message map_msg, Message disp_msg) {
			this.map_msg = map_msg;
			this.this_mod = this_mod;
			this.disp_msg = disp_msg;
		}
		
		@Override
		public
		void callback(byte motion) {
			if (motion == Interaction.HOSTED ) {
				InterModule inter = (InterModule) this_mod;
				inter._is_hosted_mode = !inter._is_hosted_mode;
				// 进入托管模式后立即发一则消息坠机
				if (inter._is_hosted_mode) {
					map_msg.post(
							new MapMessage.MapMessageContent<Byte>
							(
								this.this_mod, 
								MapMessage.MapMessageContent.Msgs.Map_BrickMoved,
								BrickInMap.Motion.CRUSH
							)
					);
				}
				// 给显示也发一则
				disp_msg.post(
						new DispMessage.DispMessageContent<Boolean>
						(
							this.this_mod, 
							DispMessage.DispMessageContent.Msgs.Disp_HostedModeChanged,
							inter._is_hosted_mode
						)
				);
				
			} else {
				map_msg.post(
						new MapMessage.MapMessageContent<Byte>
						(
							this.this_mod, 
							MapMessage.MapMessageContent.Msgs.Map_BrickMoved,
							motion
						)
				);
			}
		}
	}
	
	// 键盘或者agent
	private Interaction _inter;
	private Interaction _ai;
	private boolean _is_hosted_mode = false;
	private char[] _keys;
	
	public
	InterModule(long check_period_in_millis, boolean is_keyboard, char[] keys) {

		this._keys = keys;
		
		this.setupTimer(check_period_in_millis, 
				new InterRegular(this));
		
	}
}
