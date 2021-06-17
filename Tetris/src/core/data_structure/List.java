package core.data_structure;

// 双向链表
public class List<DataType> {

	private static
	class _Node<DataType> {
		public DataType 		data;
		public _Node<DataType>  prev;
		public _Node<DataType>  next;
		
		_Node(DataType data, _Node<DataType> prev, _Node<DataType> next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}
	
	private static
	class Iterator<DataType> {
		
		private int 			_iter_index;
		private _Node<DataType> _iter;
		private int 			_dis;
		private BestIndex 		_bi;

		public 
		void setIndexIter(int index, _Node<DataType> node) {
			this._iter = node;
			this._iter_index = index;
		}
		public
		void setIter(_Node<DataType> node) {
			this._iter = node;
		}
		
		public
		_Node<DataType> iter() {
			return this._iter;
		}
		public 
		int dis() {
			return this._dis;
		}
		public 
		BestIndex bestIndex() {
			return this._bi;
		}
		public 
		void indexAdd(int add) {
			this._iter_index += add;
		}

		static
		enum BestIndex {
			HEAD, ITER, TAIL,
		};
		public
		void selectBest(int index, int length) {
			int _dis_h = index;
			int _dis_i = index - this._iter_index;
			int _dis_t = index - length + 1;
			int dis_h = this._abs(_dis_h);
			int dis_i = this._abs(_dis_i);
			int dis_t = this._abs(_dis_t);
			
			if (dis_i <= dis_h) {
				if (dis_i <= dis_t) {
					this._bi = BestIndex.ITER;
					this._dis = _dis_i;
				} else {
					this._bi = BestIndex.TAIL;
					this._dis = _dis_t;
				}
			} 
			else {
				if (dis_h <= dis_t) {
					this._bi = BestIndex.HEAD;
					this._dis = _dis_h;
				} else {
					this._bi = BestIndex.TAIL;
					this._dis = _dis_t;
				}
			}
		}
		
		private 
		int _abs(int x) {
			return x < 0 ? -x : x;
		}
	}
	
	private _Node<DataType> 	_head;
	private _Node<DataType> 	_tail;
	private Iterator<DataType> 	_iter;
	private int   	 			_length;
	
	public
	List() {
		this._length = 0;
		this._head = null;
		this._tail = null;
		this._iter = new Iterator<DataType>();
	}
	
	public
	void print() {
		for (int i = 0; i < this._length; ++i) {
			System.out.print(this.elem(i));
			System.out.print(" ");
		}
		System.out.println();
	}
	
	
	public
	int search(DataType data) {
		_Node<DataType> last_node = this._head;
		int index = 0;
		
		while (last_node != null) {
			if (last_node.data == data)
				return index;
			last_node = last_node.next;
			++index;
		}
		
		return -1;
	}
	
	public
	int length() {
		return this._length;
	}
	
	public 
	DataType elem(int index) {
		_Node<DataType> temp = this._toTargetNode(index);
		this._iter.setIndexIter(index, temp);
		if (temp == null)
			return null;
		return temp.data;
	}
	
	public 
	List<DataType> append(DataType data) {
		
		if (this._head == null) {
			this._head = new _Node<DataType>(data, null, null);
			this._tail = this._head;
			this._iter.setIndexIter(0, _head);
		} else {
			_Node<DataType> temp = new _Node<DataType>(data, this._tail, null);
			this._tail.next = temp;
			this._tail = temp;
		}
		
		++this._length;
		return this;
	}
	
	public 
	List<DataType> prepend(DataType data) {
		
		if (this._head == null) {
			this._head = new _Node<DataType>(data, null, null);
			this._tail = this._head;
			this._iter.setIndexIter(0, _head);
		} else {
			_Node<DataType> temp = new _Node<DataType>(data, null, this._head);
			this._head.prev = temp;
			this._head = temp;
			this._iter.indexAdd(1);
		}
		
		++this._length;
		return this;
	}
	
	public 
	DataType remove(int index) {
		
		if (this._length == 0) return null;
		
		_Node<DataType> target_node = this._toTargetNode(index);
		
		if (target_node.next == null) {
			if (target_node.prev == null) {		// 前无节点、后无节点
				this._head = null;
				this._tail = null;
				
				this._iter.setIndexIter(0, null);
			} else {							// 前有节点、后无节点
				target_node.prev.next = null;
				this._tail = target_node.prev;
				
				this._iter.setIter(target_node.prev);
				this._iter.indexAdd(-1);
				
				target_node.prev = null;
			}
		} else {								
			if (target_node.prev == null) {		// 前无节点、后有节点
				target_node.next.prev = null;
				this._head = target_node.next;
				
				this._iter.setIter(target_node.next);
				
				target_node.next = null;
			} else {							// 前有节点、后有节点
				target_node.next.prev = target_node.prev;
				target_node.prev.next = target_node.next;
				
				this._iter.setIter(target_node.prev);
				
				this._iter.indexAdd(-1);
				
				target_node.prev = null;
				target_node.next = null;
			}
		}
		
		
		--this._length;
		return target_node.data;
	} 
	
	private
	_Node<DataType> _toTargetNode(int index) {
		Iterator.BestIndex 	best_index;
		_Node<DataType> 	best_node;
		int 				best_dis;
		
		this._iter.selectBest(index, this._length);
		best_index = this._iter.bestIndex();
		best_dis   = this._iter.dis();
		
		switch (best_index) {
		case HEAD: best_node = this._head; break;
		case ITER: best_node = this._iter.iter(); break;
		case TAIL: best_node = this._tail; break;
		default:   best_node = this._head; break;
		}
		
		return this._moveAlongChain(best_dis, best_node);
	}
	
	private 
	_Node<DataType> _moveAlongChain(int times, _Node<DataType> org_node) {
		
		_Node<DataType> last_node = org_node;
		
		this._iter.indexAdd(times);
		
		if (times < 0) {
			times = -times;
			while (--times >= 0)
				last_node = last_node.prev;
		} else {
			while (--times >= 0)
				last_node = last_node.next;
		}
		
		return last_node;
	}
	
}
