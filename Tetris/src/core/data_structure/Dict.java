package core.data_structure;

// 基于桶散列的字典
public 
class Dict<KeyType, ValueType> {
	
	class KeyValue {
		KeyType key;
		ValueType value;
		KeyValue(KeyType key, ValueType value) {
			this.key = key;
			this.value = value;
		}
	}

	private Object[] _hash_list;
	private int _hash_length;
	private List<KeyType> _key_list;
	private ValueType _last_value;
	
	public
	Dict() {
		this._hash_length = 1024;
		this._hash_list = new Object[this._hash_length];
		this._key_list = new List<KeyType>();
	}
	
	public
	Dict<KeyType, ValueType> add(KeyType key, ValueType value) {
		int index = key.hashCode() % this._hash_length;
		
		if (this._hash_list[index] == null)
			this._hash_list[index] = new List<ValueType>();
		
		@SuppressWarnings("unchecked")
		List<KeyValue> bucket = (List<KeyValue>) this._hash_list[index];
		
		bucket.append(new KeyValue(key, value));
		this._key_list.append(key);
		return this;
	}
	
	public
	ValueType search(KeyType key) {
		int index = key.hashCode() % this._hash_length;
		
		this._last_value = null;
		
		if (this._hash_list[index] == null)
			return null;
		else {
			@SuppressWarnings("unchecked")
			List<KeyValue> bucket = (List<KeyValue>) this._hash_list[index];
			
			for (int i = 0; i < bucket.length(); ++i) {
				if (key == bucket.elem(i).key) {
					this._last_value = bucket.elem(i).value;
					return this._last_value;
				}
			}
		}
		
		return null;
	}
	
	public 
	List<ValueType> values() {
		List<ValueType> values = new List<ValueType>();
		for (int i = 0; i < this._key_list.length(); ++i) {
			int index = this._key_list.elem(i).hashCode() % this._hash_length;
			@SuppressWarnings("unchecked")
			List<KeyValue> bucket = (List<KeyValue>) this._hash_list[index];
			for (int j = 0; j < bucket.length(); ++j) {
				values.append(bucket.elem(j).value);
			}
		}
		return values;
	}
	
	public
	List<KeyType> keys() {
		return this._key_list;
	}
	
	public 
	ValueType lastValue() {
		return this._last_value;
	}
	
}
