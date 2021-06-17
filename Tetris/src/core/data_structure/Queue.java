package core.data_structure;

// ╤сап
public 
class Queue<DataType> {
	
	private List<DataType> _list;
	
	public
	Queue() {
		this._list = new List<DataType>();
	}

	public
	void enqueue(DataType data) {
		this._list.append(data);
	}
	
	public 
	DataType dequeue() {
		if ( this.isEmpty() ) 
			return null;
		else {
			return this._list.remove(0);
		}
	}
	
	public 
	DataType top() {
		return this._list.elem(0);
	}
	
	public 
	boolean isEmpty() {
		return this._list.length() > 0 
				?  false
						: true;
	}
}
