import java.util.ArrayList;
import java.util.Iterator;

/**
 * The MyHashSet API is similar to the Java Set interface. This collection is
 * backed by a hash table.
 */
public class MyHashSet<E> implements Iterable<E> {

	/**
	 * Unless otherwise specified, the table will start as an array (ArrayList) of
	 * this size.
	 */
	private final static int DEFAULT_INITIAL_CAPACITY = 4;

	/**
	 * When the ratio of size/capacity exceeds this value, the table will be
	 * expanded.
	 */
	private final static double MAX_LOAD_FACTOR = 0.75;

	public ArrayList<Node<E>> hashTable; 

	private int size; // number of elements in the table

	/**
	 * 
	 * Node<T> class
	 *
	 * class of Node that is inside the hashTable
	 *
	 * @param <T>
	 */
	public static class Node<T> {
		private T data;
		public Node<T> next; 

		private Node(T data) {
			this.data = data;
			next = null;
		}

	}

	/**
	 * Initializes an empty table with the specified capacity.
	 *
	 * @param initialCapacity initial capacity (length) of the underlying table
	 */
	public MyHashSet(int initialCapacity) {
		hashTable = new ArrayList<>();
		// adds null values to each index in hashTable
		for (int node = 0; node < initialCapacity; node++) {
			hashTable.add(null);
		}
	}

	/**
	 * Initializes an empty table of length equal to DEFAULT_INITIAL_CAPACITY
	 */
	public MyHashSet() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	/**
	 * Returns the number of elements stored in the table.
	 * 
	 * @return number of elements in the table
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns the length of the table (the number of buckets).
	 * 
	 * @return length of the table (capacity)
	 */
	public int getCapacity() {
		return hashTable.size();
	}

	/**
	 * Looks for the specified element in the table.
	 * 
	 * @param element to be found
	 * @return true if the element is in the table, false otherwise
	 */
	public boolean contains(Object element) {
		if (element == null || size == 0) {
			return false;
		}
		// hashcode refers to the bucket that element will go in
		int hashcode = Math.abs(element.hashCode() % getCapacity());
		// elementNode gets the first node in the bucket
		Node<E> elementNode = hashTable.get(hashcode);
		// if elementNode isn't null then there is more in the linkedlist for that
		// bucket
		while (elementNode != null) {
			// when the data in elementNode is equal to
			// element that means elementNode has found the element in the list
			if (elementNode.data.equals(element)) {
				return true;
			}
			// otherwise goes to the next element
			elementNode = elementNode.next;
		}
		// return false if not found
		return false;
	}

	/**
	 * Adds the specified element to the collection, if it is not already present.
	 * If the element is already in the collection, then this method does nothing.
	 * 
	 * @param element the element to be added to the collection
	 */
	public void add(E eleme1nt) {
		// if the element is already in the hashTable then don't add it
		if (contains(eleme1nt)) {
			return;
		}
		// create a new node to use later throughout the add method
		Node<E> elementNode = new Node<E>(null);
		// increases size since by now it is guaranteed to add the element to the
		// HashTable
		size++;
		// casts size and capacity to doubles to get a double value compared to
		// MAX_LOAD_FACTOR if greater then rehash
		if ((double) size / (double) getCapacity() > MAX_LOAD_FACTOR) {
			// make a new tempHashTable (this is going to be the larger one that copies the
			// data from hashTable over)
			ArrayList<Node<E>> tempHashTable = new ArrayList<>();
			// tempHashCode is the index of buckets for the tempHashTable
			int tempHashCode = 0;
			// keep adding null values to tempHashTable until the size of tempHashTable is
			// 2*hashTable size
			for (int i = 0; i < hashTable.size() * 2; i++) {
				tempHashTable.add(null);
			}
			// retrieves all elements of hashTable and rehashes into tempHashTable
			for (int i = 0; i < hashTable.size(); i++) {
				// if the bucket is not empty or null
				if (hashTable.get(i) != null) {
					// elementNode (our node created before) is set to the first node in this
					// bucket's linkedList
					elementNode = hashTable.get(i);
					// while there are still elements in this linked list
					while (elementNode != null) {
						// create a new bucket in tempHashTable for the data value that elementNode
						// currently has
						tempHashCode = Math.abs(elementNode.data.hashCode() % tempHashTable.size());
						// if the bucket in tempHashTable is currently null, a head needs to be
						// established
						if (tempHashTable.get(tempHashCode) == null) {
							// create a new node with elementNode's current data and put it in tempHashTable
							tempHashTable.set(tempHashCode, new Node<E>(elementNode.data));
						}
						// if there is already a head, then push that head back and make the element
						// being added the
						// new head for efficiency
						else {
							// grabs current head
							Node<E> currHead = tempHashTable.get(tempHashCode);
							// new node being pushed into the list
							Node<E> pushedIn = new Node<E>(elementNode.data);
							// sets the new head of the list as pushedIn
							tempHashTable.set((tempHashCode), pushedIn);
							// pushedIn points to the old head, continuing the chain
							pushedIn.next = currHead;
						}
						// iterate to the next node
						elementNode = elementNode.next;
					}
				}
			}
			// set hashTable to tempHashTable to replace current hashTable
			hashTable = tempHashTable;
		}

		// add the new element to the hashTable
		// get bucket of element being added in hashTable
		int hashcode = Math.abs(eleme1nt.hashCode() % getCapacity());
		// elementNode is a new node with element data
		elementNode = new Node<E>(eleme1nt);
		// if the bucket in hashTable is currently null, a head needs to be established
		if (hashTable.get(hashcode) == null) {
			// set the new head of that bucket to elementNode
			hashTable.set((hashcode), elementNode);
		} else {
			// grabs current head
			Node<E> headNode = hashTable.get(hashcode);
			// sets elementNode to the new head
			hashTable.set((hashcode), elementNode);
			// points the new head to the old head
			elementNode.next = headNode;
		}
	}

	/**
	 * Removes the specified element from the collection. If the element is not
	 * present then this method should do nothing (and return false in this case).
	 *
	 * @param element the element to be removed
	 * @return true if an element was removed, false if no element removed
	 */
	public boolean remove(Object element) {
		// makes sure that the element is in the list and that the linkedList for the
		// bucket isn't empty
		if (!(contains(element)) || size == 0) {
			return false;
		}
		// gets the bucket in hashTable where the element would be
		int hashcode = Math.abs(element.hashCode() % getCapacity());
		// sets a prevNode and elementNode in case the element is at the end of the
		// linkedList
		Node<E> prevNode = hashTable.get(hashcode);
		Node<E> elementNode = hashTable.get(hashcode).next;
		// initially checks if the head is the element
		if (prevNode.data.equals(element)) {
			// sets the new head to elementNode which is the node after prevNode
			hashTable.set(hashcode, elementNode);
			size--;
			return true;
		}
		// loops through the linkedList
		for (; elementNode != null; elementNode = elementNode.next) {
			// if element is in the last node set prevNode to null to remove the tail
			if (elementNode.data.equals(element) && elementNode.next == null) {
				prevNode.next = null;
				size--;
				return true;
			}
			// else if the element is anywhere else in the list
			else if (elementNode.data.equals(element)) {
				// remove elementNode by setting prevNode to its next
				prevNode.next = elementNode.next;
				size--;
				return true;
			} else {
				// go to next node
				prevNode = prevNode.next;
			}

		}
		// default return
		return false;
	}

	/**
	 * Returns an Iterator that can be used to iterate over all of the elements in
	 * the collection.
	 * 
	 * The order of the elements is unspecified.
	 */
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			/**
			 * curr: node that is going through the whole hashTable bucketTrack: tracks the
			 * bucket currently on sizeTrack: tracks how many elements we have gone through
			 */
			public Node<E> curr = hashTable.get(0);
			public int bucketTrack, sizeTrack;

			/**
			 * @return true if there are elements left in the hashTable, false otherwise
			 */
			@Override
			public boolean hasNext() {
				return (sizeTrack != size);
			}

			/**
			 * @return the next element in the hashTable
			 */
			@Override
			public E next() {
				// if curr is null then either it has reached the end of a linkedList or a null
				// bucket so it needs to
				// go to the next bucket
				while (curr == null) {
					// increase bucket index
					bucketTrack++;
					// set curr to the head of the next bucket
					curr = hashTable.get(bucketTrack);
				}
				// increase sizeTrack by 1 since iterated through to a new element
				sizeTrack++;
				// set retVal to curr.data
				E retVal = curr.data;
				// set2 curr to the next node
				curr = curr.next;
				return retVal;
			}

		};
	}

}
