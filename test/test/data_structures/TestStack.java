package test.data_structures;

import model.data_structures.Node;
import model.data_structures.Stack;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class TestStack{

	//Se usará la implementación con lista encadenada y nodos.
	private Stack<Integer> stack;
	@Before
	public void setUp1()
	{
		stack = new Stack<Integer>();
	}

	public void setUp2()
	{
		int i = 1;
		while(i <= 30)
		{
			Node<Integer> node = new Node<Integer>(i);
			stack.push(node);
			i++;
		}
	}
	@Test
	public void testStack()
	{
		assertTrue(stack != null);
		setUp2();
		assertTrue(stack.size() == 30);
	}


	@Test
	public void testPush() 
	{
		stack.push((Node<Integer>)new Node<Integer>(22));
		assertTrue(((Integer)stack.getFirst()) == 22);
		setUp2();
		assertTrue((Integer)stack.getFirst() == 30);
	}

	@Test
	public void testPop() {
		assertNull(stack.getFirst());
		setUp2();
		assertTrue(((Integer) stack.pop()) == 30);
		assertTrue(((Integer) stack.pop()) == 29);
		assertTrue(((Integer) stack.pop()) == 28);
	}
	@Test
	public void testSize() 
	{
		assertTrue(stack.size() == 0);
		setUp2();
		assertTrue(stack.size() == 30);
	}

	@Test
	public void testIsEmpty() 
	{	
		assertTrue(stack.isEmpty());
		setUp2();
		assertFalse(stack.isEmpty());
	}

	@Test
	public void testGetFirst() 
	{
		assertNull(stack.getFirst());
		setUp2();
		assertTrue(((Integer) stack.getFirst()) == 30);
	}

}
