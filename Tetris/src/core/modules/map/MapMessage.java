package core.modules.map;

import core.message.Message;
import core.modules.MdlMsg;
import core.modules.Module;

public 
class MapMessage
extends Message {
	
	public static
	class MapMessageContent<DataType>
	extends MdlMsg.MdlMsgCtnt<DataType> {
		public MapMessageContent(Module mod, Msgs msg, DataType data) {
			super(mod, data);
			this.msg = msg;
		}
		
		public
		enum Msgs {
			Map_BrickMoved,
		}
		
		public Msgs msg;
	}
}


