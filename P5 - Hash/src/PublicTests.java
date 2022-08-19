import static org.junit.Assert.*;

import org.junit.Test;

public class PublicTests {
	
	@Test
	public void testSimpleAdd() {
		MyHashSet<String> s = new MyHashSet<String>();
		s.add("hello");
		s.add("apple");
		assertEquals(4, s.getCapacity());
		assertEquals(2, s.size());
	}
	
	@Test
	public void testReHash() {
		MyHashSet<String> s = new MyHashSet<String>();
		for (int i = 0; i < 10000; i++) {
			s.add("Entry " + i);
		}
		//s.remove("Entry " + 10);
		//assertEquals(262144, s.getCapacity());
		//assertEquals(100000, s.size());
		for (int i = 0; i < 10000; i++) {
			assertTrue(s.contains("Entry " + i));
		}
		for(String i : s) {
			System.out.println(i + " BUCKET " + Math.abs(i.hashCode() % s.hashTable.size()));
		}
	
		//System.out.println(s.getCapacity());
	}

	@Test
	public void testNoDuplicates() {
		MyHashSet<String> s = new MyHashSet<String>(100000);
		for (int i = 0; i < 1000; i++) {
			s.add("hello");
			s.add("apple");
			s.add("cat");
			s.add("last");
		}
		assertEquals(100000, s.getCapacity());
		assertEquals(4, s.size());
		//System.out.println(s.hashTable.get(0));
	}
	@Test
	public void forEachTest() {
		MyHashSet<Integer> s = new MyHashSet<Integer>();
		assertEquals(0,s.size());
	}
	public void testRemove() {
		MyHashSet<String> s = new MyHashSet<String>(10);
		s.add("hello");
		s.add("apple");
		s.add("cat");
		s.add("last");
		s.remove("Hello");
		assertFalse(s.contains("Hello"));
	}
	@Test
	public void containsTest() {
		MyHashSet<String> s = new MyHashSet<String>();
		String a = "";
		s.add(a);
		s.add("hello");
		s.add("coding");
		s.add("sucks");
		a = "B";
		s.add(a);
		for(String i : s) {
			System.out.println(i);
		}
		s.remove(a);
		s.remove("hello");
		s.remove("coding");
		s.remove("sucks");
		s.remove("fdsafdsa");
		System.out.println(s.contains("hello"));
		for(String i : s) {
			System.out.println(i);
		}
		MyHashSet<Integer> p = new MyHashSet<Integer>(10);
		p.add(1);
		p.add(11);
		assertTrue(p.contains(1));
		assertTrue(p.contains(11));
		p.remove(1);
		assertFalse(p.contains(1));
		}
}
