package misc;

public class Pair<T> {
	private T _first;
	private T _second;

	public Pair(T first, T second) {
		_first = first;
		_second = second;
	}

	public T getFirst() {
		return _first;
	}

	public T getSecond() {
		return _second;
	}
	
}
