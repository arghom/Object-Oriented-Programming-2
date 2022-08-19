package searchTree;

import java.util.Collection;
import java.util.Collections;

/**
 * This class represents a non-empty search tree. An instance of this class
 * should contain:
 * <ul>
 * <li>A key
 * <li>A value (that the key maps to)
 * <li>A reference to a left Tree that contains key:value pairs such that the
 * keys in the left Tree are less than the key stored in this tree node.
 * <li>A reference to a right Tree that contains key:value pairs such that the
 * keys in the right Tree are greater than the key stored in this tree node.
 * </ul>
 *  
 */
 public class NonEmptyTree<K extends Comparable<K>, V> implements Tree<K, V> {
	
	private Tree<K,V> left, right;
	private K key;
	private V value;
	/**
	 * This is the non empty tree object which are sorted throughout the tree contains key,value pairs
	 * along with the references to the left and right subtrees
	 * 
	 * @param key: the keys that are sorted in the tree
	 * @param value: the values that go along with each key
	 * @param left: the left subtree to the current tree
	 * @param right: the right subtree to the current tree
	 */
	public NonEmptyTree(K key, V value, Tree<K,V> left, Tree<K,V> right) { 
		this.key = key;
		this.value = value;
		this.left = left;
		this.right = right;
	}
	/**
	 * given a key, search the tree for the key and return its value.
	 * @return value 
	 */
	public V search(K key) {
		//if the current key is equal to the target key 
		if(this.key.compareTo(key) == 0) {
			//return the value of current key
			return this.value;
		//if the current key is less than the target key 
		} else if(this.key.compareTo(key) < 0) {
			//search the right subtree since those keys are greater 
			return right.search(key);
		//if the current key is greater than the target key
		} else {
			//search the left subtree since those values are smaller
			return left.search(key);
		}
	}
	/**
	 * given a key & value, insert the pair into the tree
	 * @return the NonEmptyTree added
	 */
	public NonEmptyTree<K, V> insert(K key, V value) {
		//if the target key is less than the current key
		if(key.compareTo(this.key) < 0) {
			//recursively calls and sets this.left subtree until it hits the EmptyTree class and inserts there
			 this.left = this.left.insert(key, value);
			 return this;
		//if the target key is greater than the current key
		} else if(key.compareTo(this.key) > 0) {
			//recursively calls and sets this.right subtree until it hits the EmptyTree and inserts there
			this.right = this.right.insert(key, value);
			return this;
		//if the target key is equal to the current key
		} else {
			//if the key is already in the tree the only thing left is to update the value
			this.value = value;
			return this;
			}
	}
	/**
	 * given a key, find it in the tree and delete it. Then return current tree
	 * @return the current tree
	 */
	public Tree<K, V> delete(K key) {
		//if the target key is the current key
		if(this.key.compareTo(key) == 0) {
			try {
				//set the current key to the max key in the left subtree
				this.key = this.left.max();
				//search for the value of that max key and set it also 
				this.value = this.left.search(this.key);
				//then delete the leaf node 
				this.left = this.left.delete(this.key);
				return this;
			//if the left subtree doens't exist then this exception will be thrown
			} catch(TreeIsEmptyException e) {
				//checking if there is a right subtree 
				return this.right;
			}
		//if the target key is greater than the current key 
		} else if(this.key.compareTo(key) < 0) {
			//recursively calls this.right subtree and adjusts the tree after deletion
			this.right = this.right.delete(key);
			return this;
		//if the target key is less than the current key
		} else {
			//recursively calls this.left subtree and adjusts the tree after deletion
			this.left = this.left.delete(key);
			return this;
		}
	}
	/**
	 * return the largest key in the tree
	 * @return the largest key
	 */
	public K max(){
		try{
			//keep checking the right subtree
			return this.right.max();
		//if the call ends up hitting the empty tree then return the key from before
		}catch(TreeIsEmptyException e) {
			return this.key;
		}
	}
	/**
	 * return the smallest key in the tree
	 * @return the smallest key 
	 */
	public K min() {
		try{
			//keep checking the left subtree
			return this.left.min();
		//if the call ends up hitting the empty tree then return the key from before
		}catch(TreeIsEmptyException e) {
			return this.key;
		}
	}
	/**
	 * @return the number of key,value pairs in the tree
	 */
	public int size() {
		//add 1 for each call to this method then call the left and right subtree. When the end is hit then the
		//EmptyTree class would be called
		return 1 + this.left.size() + this.right.size();
	}
	/**
	 * given any collection, add all the keys from the tree into it in sorted order by keys
	 * the traversal is in order
	 */
	public void addKeysToCollection(Collection<K> c) {
		//calls method on the left subtree
		this.left.addKeysToCollection(c);
		//adds current key to collection
		c.add(this.key);
		//calls the method on right subtree
		this.right.addKeysToCollection(c);
	}
	/**
	 * given bounds of [fromKey, toKey] return a new tree with key values of the range, inclusive
	 */
	public Tree<K, V> subTree(K fromKey, K toKey) {
		//if the current key is less than the fromKey
		if (this.key.compareTo(fromKey) < 0) {
			//return the right subtree call to subtree
			return this.right.subTree(fromKey, toKey);
		//if the current key is greater than the toKey 
		} else if (this.key.compareTo(toKey) > 0) {
			//return the left subtree call to subtree 
			return this.left.subTree(fromKey, toKey);
		//in the case that the key is in bounds and should be added to the tree
		} else {
			//return a new NonEmptyTree to add to the new subtree that is being returned (calls the left and
			//right subtree to check if those should be included in the new subtree)
			return new NonEmptyTree<K, V>(this.key, this.value, this.left.subTree(fromKey, toKey),
			this.right.subTree(fromKey, toKey));
		}
	}

}