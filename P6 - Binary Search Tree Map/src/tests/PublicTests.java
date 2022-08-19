package tests;

import java.util.NoSuchElementException;
import static org.junit.Assert.*;

import org.junit.Test;

import searchTree.EmptyTree;
import searchTree.SearchTreeMap;
import searchTree.Tree;

public class PublicTests{
	
	@Test
	public void testEasyBSTSearch() {
		Tree<Integer,String> t = EmptyTree.getInstance();
		assertTrue(t.search(0) == null);
		t = t.insert(5, "THIS IS 5");
		assertEquals("THIS IS 5", t.search(5));
		t = t.insert(8, "THIS IS 8");
		assertEquals("THIS IS 8", t.search(8));
		
	}
	
	@Test
	public void testEmptySearchTreeMap() {
		SearchTreeMap<String, Integer> s = new SearchTreeMap<String, Integer>();
		assertEquals(0, s.size());
		try {
			assertEquals(null, s.getMin());
			fail("Should have thrown NoSuchElementException");
		} catch (NoSuchElementException e) {
			assert true; // as intended
		}
		try {
			assertEquals(null, s.getMax());
			fail("Should have thrown NoSuchElementException");
		} catch (NoSuchElementException e) {
			assert true; // as intended
		}
		assertEquals(null, s.get("a"));
		
	}
	
	@Test
	public void testBasicSearchTreeMapStuff() {
		SearchTreeMap<Integer,String> s = new SearchTreeMap<Integer,String>();
		assertEquals(0, s.size());
		s.put(6, "Two");
		s.put(1, "One");
		s.put(3, "Three");
		s.put(1, "OneSecondTime");
		assertEquals(3, s.size());
		assertEquals("OneSecondTime", s.get(1));
		//assertEquals("Two", s.get(2));
		assertEquals("Three", s.get(3));
		assertEquals(null, s.get(8));
		//s.put(6, "Two");
		s.put(5, "One");
		s.put(7, "Three");
		s.remove(6);
		SearchTreeMap<Integer, String> retMap = s.subMap(1, 3);
		System.out.println(retMap.keySet());
		System.out.println(s.keyList());
		
	}
	
}