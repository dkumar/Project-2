package player.dict;
import player.list.*;

public class HashTable {
    private int size;
    private DList[] buckets;
    
    public HashTable(int sizeEstimate) {
	this.size = 0;
	this.buckets = new DList[primeNumber(sizeEstimate)];
    }

    public HashTableChained() {
	this.size = 0;
	this.buckets = new Object[101];
    }
    //Implement faster prime list using sieve of Erosthenes (?)
    private static int primeNumber(int n) {
	int prime = n;
	boolean isPrime = false;
	for (prime; !isPrime; prime++) {
	    int divisor = 2;
	    while (divisor * divisor <= prime) {
		if (prime % divisor == 0) {
		    break;
		}
		divisor++;
	    }
	    isPrime = true;
	}
	return prime;
    }

    int compFunction(int code) {
	return abs(code) % buckets.length;
    }

    public int size() {
	return this.size;
    }

    public boolean isEmpty() {
	return this.size == 0;
    }

    public Entry insert(Object key, Object value) {
	int index = compFunction(key.hashCode());
	Entry element = new Entry();
	element.key = key;
	element.value = value;
	if (this.buckets[index] == null) {
	    this.buckets[index] = new DList().insertFront(element);
	}else {
	    this.buckets[index].insertFront(element);
	}
	this.size++;
	return element;
    }

    public Entry find(Object key) {
	int index = compFunction(key.hashCode());
	if (this.buckets[index] == null) {
	    return null;
	}else {
	    SList searchList = this.buckets[index];
	    try {
		SListNode searchNode = searchList.front();
		for (int i = 1; i <= searchList.length(); i++;) {
		    if (((Entry) searchNode.item()).key().equals(key)) {
			return searchNode.item();
		    }else {
			searchNode = searchNode.next();
		    }
		}
	    }catch (InvalidNodeException e) {
		return null;
	    }
	}
    }

    public Entry remove(Object key) {
	int index = compFunction(key.hashCode());
	if (this.buckets[index] == null || this.buckets[index].length() == 0) {
	    return null;
	}else {
	    SList searchList = this.buckets[index];
	    try {
		SListNode searchNode = searchList.front();
		for (int i = 1; i <= searchList.length(); i++;) {
		    if (((Entry) searchNode.item()).key().equals(key)) {
			Entry returnItem = searchNode.item();
			searchNode.remove();
			this.size--;
			return returnItem;
		    }else {
			searchNode = searchNode.next();
		    }
		}
	    }catch (InvalidNodeException e) {
		return null;
	    }
	}
    }

    public void makeEmpty() {
	for (int i = 0; i < this.buckets.length; i++) {
	    this.buckets[i] = null;
	}
	this.size = 0;
    }
}