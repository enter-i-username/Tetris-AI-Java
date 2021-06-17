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
	
	// ��ʱ���Ļص�����
	public static
	class InterRegular
	extends Module.CheckEventRegularly {
		
		// ���߳����ִ�һ���̣߳�����������
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
			
			// ���������߳�
			if (this.is_first) {
				// Ӧ����ע��map�������Ž�����һ��
				InterModule inter_module = (InterModule) this.this_mod;
				
				List<Message> mails_to_send = inter_module.mailsToSend().values();
				
				// �ҵ�message
				Message disp_msg = null;
				Message map_msg = null;
				for (int i = 0; i < mails_to_send.length(); ++i) {
					if (mails_to_send.elem(i) instanceof DispMessage) {
						disp_msg = mails_to_send.elem(i);
					} else if (mails_to_send.elem(i) instanceof MapMessage) {
						map_msg = mails_to_send.elem(i);
					}
				}
				
				// ����
				inter_module._inter = new KeyBrickEvent(
					inter_module._keys, 
					new MsgPoster(inter_module, map_msg, disp_msg)
				);
				
				 // ����
				inter_module._ai = new Agent(
					inter_module._keys, 
					new MsgPoster(inter_module, map_msg, disp_msg)
				);
				
				
				new InputChecker((InterModule) this.this_mod).start();
				
				this.is_first = false;
			}
			
			/////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////
			// ����д��������
			InterModule inter_module = (InterModule) this.this_mod;
			
			// �ҵ�message
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
			
			// ��ն���
			while ( !own_mail.isEmpty() ) {
				// �ж���Ϣ�Ƿ����Լ���
				if( own_mail.topMsg() instanceof InterMessage.InterMessageContent ) {
					Module mod = ( (MdlMsg.MdlMsgCtnt<?>) own_mail.topMsg() ).mod;
					// �ж���Ϣ��Դͷmodule
					if (mod instanceof MapModule
						|| mod instanceof InterModule) {
						// ������Ϣ
						InterMessage.InterMessageContent<?> msg_cntnt = (InterMessage.InterMessageContent<?>) own_mail.receive();
						Map.MapBrick mb = (Map.MapBrick) msg_cntnt.data;
						int[] strategy = agent.getBestStrategy(mb.map, mb.brick);
						
						// �й�ģʽ�²ſ���
						if (inter_module._is_hosted_mode) {
							timer.sleep(500);
							
							// ��ת����
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
							
							// ƽ�Ʋ���
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
							
							// ׹��
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
					// ���Ƿ����Լ��Ĳ���������Ϣ
					own_mail.receive();
				}
			}
		}
	}
	
	// ���ͼ������Ϣ�Ļص�����
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
				// �����й�ģʽ��������һ����Ϣ׹��
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
				// ����ʾҲ��һ��
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
	
	// ���̻���agent
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
