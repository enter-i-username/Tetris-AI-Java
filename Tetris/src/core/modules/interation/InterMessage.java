package core.modules.interation;

import core.message.Message;
import core.modules.MdlMsg.MdlMsgCtnt;
import core.modules.Module;

public 
class InterMessage 
extends Message {
	
	public static
	class InterMessageContent<DataType>
	extends MdlMsgCtnt<DataType> {
		public InterMessageContent(Module mod, Msgs m, DataType data) {
			super(mod, data);
			this.m = m;
		}
		
		public
		enum Msgs {
			Inter_InputReceived,
		}
		
		public Msgs m;
	}
}
