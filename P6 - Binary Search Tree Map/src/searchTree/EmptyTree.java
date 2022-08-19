package searchTree;

import java.util.Collection;

/**
 * This class is used to represent the empty search tree: a search tree that
 * contains no entries.
 * 
 * This class is a singleton class: since all empty search trees are the same,
 * there is no need for multiple instances of this class. Instead, a single
 * instance of the class is created and made available through the static field
 * SINGLETON.
 * 
 * The constructor is private, preventing other code from mistakenly creating
 * additional instances of the class.
 *  
 */
 public class EmptyTree<K extends Comparable<K>,V> implements Tree<K,V> {
	/**
	 * This static field references the one and only instance of this class.
	 * We won't declare generic types for this one, so the same singleton
	 * can be used for any kind of EmptyTree.
	 */
	private static EmptyTree SINGLETON = new EmptyTree();

	public static  <K extends Comparable<K>, V> EmptyTree<K,V> getInstance() {
		return SINGLETON;
	}

	/**
	 * Constructor is private to enforce it being a singleton
	 *  
	 */
	private EmptyTree() {
		// Nothing to do
	}
	/**
	 * will only happen if the key being searched for doesn't exist so return null
	 */
	public V search(K key) {
		return null;
	}
	/**
	 * Adds a new leaf to the tree with the subtrees being the singleton 
	 */
	public NonEmptyTree<K, V> insert(K key, V value) {
		return new NonEmptyTree<K,V>(key,value, this, this);
	}
	/**
	 * Will only hit this if key doesn't exist or subtree doesn't exist
	 */
	public Tree<K, V> delete(K key) {
		return this;
	}
	/**
	 * Throws the exception if gone past the max, no more further to go
	 */
	public K max() throws TreeIsEmptyException {
		throw new TreeIsEmptyException();
			
	}
	/**
	 * Throws the exception if gone past the min, no more further to go
	 */
	public K min() throws TreeIsEmptyException {
		throw new TreeIsEmptyException();
	}
	/**
	 * Returns 0 if the recursive call hits the singleton since nothing is there
	 */
	public int size() {
		return 0;
	}
	/**
	 * no operation since void return & hits the end of the tree 
	 */
	public void addKeysToCollection(Collection<K> c) {
	}
	/**
	 * returns the singleton to signify the end of tree 
	 */
	public Tree<K,V> subTree(K fromKey, K toKey) {
		return getInstance();
	}
}