import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Some test cases distributed as part of the project.
 */
public class StudentTests {

	
	/**
     * Utility function Given a String, return a list consisting of one
     * character Strings
     */
    public static List<String> makeListOfCharacters(String s) {
        List<String> lst = new ArrayList<String>();
        for (int i = 0; i < s.length(); i++)
            lst.add(s.substring(i, i + 1));
        return lst;
    }

    /**
     * Test adding to a Bag
     * 
     */
    @Test
    public void testBagAddSizeUniqueElements() {
        List<String> lst = makeListOfCharacters("aaabbc");
        HeavyBag<String> b = new HeavyBag<String>();
        b.addAll(lst);
        assertEquals(6, b.size());
        assertEquals(3, b.uniqueElements().size());
    }

    /**
     * Test checking if a Bag contains a key, and the count for each element
     * 
     */
    @Test
    public void testBagContainsAndCount() {
        List<String> lst = makeListOfCharacters("aaabbc");
        HeavyBag<String> b = new HeavyBag<String>();
        b.addAll(lst);
        assertEquals(6, b.size());
        assertEquals(3, b.uniqueElements().size());
        assertTrue(b.contains("a"));
        assertTrue(b.contains("b"));
        assertTrue(b.contains("c"));
        assertFalse(b.contains("d"));
        assertEquals(3, b.getCount("a"));
        assertEquals(2, b.getCount("b"));
        assertEquals(1, b.getCount("c"));
        assertEquals(0, b.getCount("d"));
    }
    
    @Test
    public void testIterator() {
    	List<String> lst = makeListOfCharacters("aaabbc");
    	HeavyBag<String> b = new HeavyBag<String>();
        b.addAll(lst);
        Iterator<?> it = b.iterator();
     
        while(it.hasNext()) {
        	it.next();
        	it.remove();
        }
        System.out.println(b.toString());
        System.out.println(1);
    }

 
}