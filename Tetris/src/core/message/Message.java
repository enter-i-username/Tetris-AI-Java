package core.message;

import core.data_structure.Queue;

// �����ֻ࣬����������������
class BasicMsgContent {}


public
class Message
extends Queue<BasicMsgContent> {
	
	public static
	class MsgContent<DataType>
	extends BasicMsgContent {
		
		public
		MsgContent(DataType data) {
			this.data = data;
		}		
		public DataType data;
	}

	public <DataType>
	void post(MsgContent<DataType> msg_content) {
		this.enqueue(msg_content);
	}
	
	@SuppressWarnings("unchecked")
	public <DataType>
	MsgContent<DataType> receive() {
		return (MsgContent<DataType>) this.dequeue();
	}
	
	
	@SuppressWarnings("unchecked")
	public <DataType>
	MsgContent<DataType> topMsg() {
		return (MsgContent<DataType>) this.top();
	}

}
