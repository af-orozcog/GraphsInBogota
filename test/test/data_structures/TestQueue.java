package test.data_structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.IQueue;
import model.data_structures.Node;
import model.data_structures.Queue;

public class TestQueue {
	
	private IQueue<Node<Integer>> cola;
	@Before
	public void setup()
	{
		cola = new Queue<Node<Integer>>();
	}
	@Test
	public void testQueue()
	{
		assertTrue(cola!=null);
	}
	public void testVacia()
	{
		assertTrue(cola.vacia());
	}
	@Test
	public void testPonerEnCola()
	{
		Node<Integer> n = new Node(54);
		((Queue<Node<Integer>>) cola).ponerEnCola(n);
		assertEquals(54, (int)(cola.darPrimero().getObject()));
	}
	@Test
	public void testDarTamanio()
	{
		assertEquals(0,cola.darTamanio());
		for (int i = 0; i < 7; i++)
			((Queue<Node<Integer>>) cola).ponerEnCola(new Node(i));
		assertEquals(7, (int)cola.darTamanio());
	}
	@Test
	public void testSacarDeCola() throws Exception
	{
		for (int i = 0; i < 4; i++)
			((Queue<Node<Integer>>) cola).ponerEnCola(new Node(i)); 
		//Agrega nodos con los numeros 0,1,2,3 en ese orden
		cola.sacarDeCola(); //Saca el 0
		assertEquals(3, cola.darTamanio());
		cola.sacarDeCola(); // Saca el uno
		assertEquals(2, cola.darTamanio());
	}
	@Test
	public void testDarPrimero() throws Exception
	{
		for (int i = 15; i < 19; i++)
			((Queue<Node<Integer>>) cola).ponerEnCola(new Node(i)); 
		//Agrega nodos con los numeros 0,1,2,3 en ese orden
		assertEquals(15, (int)cola.darPrimero().getObject()); //Verifica que el primero que queda en la cola es el 2
	}

}
