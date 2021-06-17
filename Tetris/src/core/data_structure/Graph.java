package core.data_structure;

public 
class Graph<VertexType, EdgeType> {
	
	protected Dict<VertexType, Dict<VertexType, EdgeType>> _graph;
	
	public
	Graph() {
		this._graph = new Dict<VertexType, Dict<VertexType, EdgeType>>();
	}

	public 
	Graph<VertexType, EdgeType> addVertex(VertexType v) {
		this._graph.add(v, new Dict<VertexType, EdgeType>());
		return this;
	}
	
	public
	Graph<VertexType, EdgeType> addEdge(EdgeType e, VertexType v_out, VertexType v_in) {
		Dict<VertexType, EdgeType> d = this._graph.search(v_out);
		if (d == null) {
			this.addVertex(v_out);
			d = _graph.lastValue();
		}
		d.add(v_in, e);
		return this;
	}
	
	public 
	EdgeType getEdge(VertexType v_out, VertexType v_in) {
		Dict<VertexType, EdgeType> d = this._graph.search(v_out);
		return d == null
				? null
					: d.search(v_in);
	}
	
	public 
	Dict<VertexType, EdgeType> outEdges(VertexType v) {
		Dict<VertexType, EdgeType> d = this._graph.search(v);
		return d == null
				? null
					: d;
	}
	
	public
	List<VertexType> verteces() {
		return this._graph.keys();
	}
}
