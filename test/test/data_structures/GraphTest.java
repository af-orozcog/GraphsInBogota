package test.data_structures;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Iterator;

import model.data_structures.Edge;
import model.data_structures.Graph;
import model.data_structures.HashTableSC;
import model.data_structures.ORArray;
import model.data_structures.Pair;
import model.data_structures.RedBlackBST;
import model.vo.VertexInfo;



public class GraphTest {
	/**
	 * Grafo de prueba que va a conetener numeros como vertices y como informacion
	 */
	Graph<Integer,Integer,Integer> numeros;
	
	/**
	 * Grafo de prueba que va a contener cadenas de caracteres como vertices y
	 * enteros como informacion de vertice y cadenas de caracteres como informacion
	 * de arcos
	 */
	Graph<String,Integer,String> palabras;
	
	/**
	 * 
	 */
	RedBlackBST<Integer,Integer> numVertices;
	
	/**
	 * 
	 */
	RedBlackBST<Pair<Integer,Integer>, Integer> arcosVal;
	
	/**
	 * metodo que inicializa los grafos
	 */
	public void setUp0() {
		palabras = new Graph<String,Integer,String>();
		numeros = new Graph<Integer,Integer,Integer>();
	}
	
	public void setUp1() {
		setUp0();
		numVertices = new RedBlackBST<Integer,Integer>();
		arcosVal = new RedBlackBST<Pair<Integer,Integer>,Integer>();
		for(int  i = 0; i < 10000; i++) {
			double randomDouble = Math.random();
			randomDouble = randomDouble * 10000 + 1;
			int randomInt = (int) randomDouble;
			numeros.addVertex(i, randomInt);
			numVertices.put(i,randomInt);
		}
		for(int i = 0; i < 10000;i++) {
			double randomDouble1 = Math.random();
			randomDouble1 = randomDouble1 * 9999 + 1;
			int randomInt1 = (int) randomDouble1;
			double randomDouble2 = Math.random();
			randomDouble2 = randomDouble2 * 10000 + 1;
			int randomInt2 = (int) randomDouble2;
			if(randomInt1 != i) {
				numeros.addEdge(i, randomInt1, randomInt2);
				arcosVal.put(new Pair(i,randomInt1), randomInt2);
			}
		}
	}
	
	
	public void VTest0() {
		setUp0();
		assertEquals("el numero de vertices no es el correcto",palabras.V(),0);
		assertEquals("el numero de verices no es el correcto",numeros.V(),0);
	}
	
	@Test
	public void ETest0() {
		setUp0();
		assertEquals("el numero de vertices no es el correcto",palabras.V(),0);
		assertEquals("el numero de verices no es el correcto",numeros.V(),0);
	}
	
	@Test
	public void addVertexTest0() {
		setUp0();
		palabras.addVertex("a", 1);
		numeros.addVertex(1, 1);
		assertEquals("el numero de vertices no es el correcto",palabras.V(),1);
		assertEquals("el numero de verices no es el correcto",numeros.V(),1);
		palabras.addVertex("a", 1);
		numeros.addVertex(1,1);
		assertEquals("el numero de vertices no es el correcto",palabras.V(),1);
		assertEquals("el numero de verices no es el correcto",numeros.V(),1);
		palabras.addVertex("b", 2);
		numeros.addVertex(2,2);
		assertEquals("el numero de vertices no es el correcto",palabras.V(),2);
		assertEquals("el numero de verices no es el correcto",numeros.V(),2);
	}
	
	/**
	 * 
	 */
	@Test
	public void addEdgeTest0() {
		setUp0();
		palabras.addVertex("a", 1);
		palabras.addVertex("b", 2);
		palabras.addEdge("a", "b", "prueba1");
		assertEquals("la cantidad de arcos no es el correcto",palabras.E(),1);
		numeros.addVertex(1, 1);
		numeros.addVertex(2, 2);
		numeros.addEdge(1, 2, 12);
		assertEquals("la cantidad de arcos no es el correcto", numeros.E(),1);
		
		palabras.addEdge("b","a", "prueba1");	
		assertEquals("la cantidad de arcos no es el correcto",palabras.E(),1);
		numeros.addEdge(2, 1, 11);
		assertEquals("la cantidad de arcos no es el correcto", numeros.E(),1);
		Integer val = 12;
		assertEquals("el valor no fue modificado", numeros.getInfoArc(2, 1),val);
		numeros.setInfoArc(1, 2, 13);
		val = 13;
		assertEquals("el valor no fue modificado", numeros.getInfoArc(2, 1),val);
		
		numeros.addVertex(3, 3);
		numeros.addVertex(4, 4);
		numeros.addEdge(3, 4, 34);
		assertEquals("la cantidad de arcos no es el correcto", numeros.E(),2);
		palabras.addVertex("c", 3);
		palabras.addVertex("d", 4);
		palabras.addEdge("c","d", "prueba2");
		assertEquals("la cantidad de arcos no es el correcto", palabras.E(),2);
	}
	
	/**
	 * 
	 */
	@Test
	public void getInfoVertexTest0() {
		setUp0();
		numeros.addVertex(1, 2);
		Integer comparador = 2;
		assertEquals("el valor obtenido no es el correcto",numeros.getInfoVertex(1),comparador);
		numeros.addVertex(3, 4);
		comparador = 4;
		assertEquals("el valor obtenido no es el correcto", numeros.getInfoVertex(3),comparador);
		
		palabras.addVertex("a", 1);
		comparador = 1;
		assertEquals("el valor obtenido no es el correcto", palabras.getInfoVertex("a"),comparador);
		palabras.addVertex("b", 2);
		comparador = 2;
		assertEquals("el valor obtenido no es el correcto", palabras.getInfoVertex("b"),comparador);
		
		
		numeros.addVertex(1, 3);
		comparador = 3;
		assertNotEquals("el valor obtenido no es el correcto",numeros.getInfoVertex(1),comparador);
		palabras.addVertex("a", 2);
		comparador = 2;
		assertNotEquals("el valor obtenido no es el correcto", palabras.getInfoVertex("a"),comparador);
	}
	
	/**
	 * 
	 */
	@Test
	public void getInfoVertexTest1() {
		setUp1();
		Integer min = numVertices.min();
		Integer max = numVertices.max();
		Iterator<Integer> llaves = numVertices.keysInRange(min, max);
		while(llaves.hasNext()) {
			Integer valor = llaves.next();
			assertEquals("deberian ser iguales las informaciones",numVertices.get(valor),numeros.getInfoVertex(valor));
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void getInfoArc0() {
		setUp0();
		palabras.addVertex("a", 1);
		palabras.addVertex("b", 2);
		palabras.addEdge("a", "b", "prueba1");
		assertEquals("la informacion suminstrada no es la correcta",palabras.getInfoArc("a", "b"),"prueba1");
		
		palabras.addVertex("c", 3);
		palabras.addEdge("a", "c", "prueba2");
		assertEquals("la informacion suministrada no es la correcta",palabras.getInfoArc("a", "c"),"prueba2");
		
		numeros.addVertex(1, 2);
		numeros.addVertex(3, 4);
		numeros.addEdge(1, 3, 13);
		Integer comparador = 13;
		assertEquals("la informacion suministrada no es la correcta", numeros.getInfoArc(1, 3),comparador);
	
		numeros.addVertex(5, 6);
		numeros.addEdge(1, 5, 15);
		comparador = 15;
		assertEquals("la informacion suministrada no es la correcta", numeros.getInfoArc(1, 5),comparador);
	}
	
	@Test
	public void getInfoArc1() {
		setUp1();
		Pair<Integer,Integer> min = arcosVal.min();
		Pair<Integer,Integer> max = arcosVal.max();
		Iterator<Pair<Integer,Integer>> llaves = arcosVal.keysInRange(min, max);
		while(llaves.hasNext()) {
			Pair<Integer,Integer> valor = llaves.next();
			assertEquals("deberian ser iguales las informaciones",arcosVal.get(valor),numeros.getInfoArc(valor.first,valor.second));
			assertEquals("deberian ser iguales las informaciones",arcosVal.get(valor),numeros.getInfoArc(valor.second,valor.first));
		}
	}
	
	@Test
	public void adjTest0() {
		setUp0();
		numeros.addVertex(1, 2);
		numeros.addVertex(3, 4);
		numeros.addVertex(5, 6);
		numeros.addVertex(7, 8);
		numeros.addEdge(1, 3, 13);
		numeros.addEdge(1, 5, 15);
		numeros.addEdge(1, 7, 17);
		boolean a[] = {false,false,false,false,false,false,false,false};
		Iterator<Integer> ite = numeros.adj(1);
		while(ite.hasNext()) {
			a[ite.next()] = true;
		}
		assertTrue("los adyacentes no son los correctos",a[3]);
		assertTrue("los adyacentes no son los correctos",a[5]);
		assertTrue("los adyacentes no son los correctos",a[7]);
		int count = 0;
		for(int i = 0; i < 8;i++) {
			if(a[i])
				count++;
		}
		assertEquals("el numero de adyacentes no es el correcto", count,3);
	}
	
	@Test
	public void pruneTest() {
		Graph<Integer,VertexInfo,Double> numero = new Graph<Integer, VertexInfo, Double>();
		numero.addVertex(1, null);
		numero.addVertex(2, null);
		numero.addVertex(3, null);
		numero.addVertex(4, null);
		numero.addVertex(5, null);
		numero.addEdge(1, 2, 1.0);
		numero.addEdge(2, 3, 1.0);
		numero.addEdge(3, 4, 1.0);
		numero.addEdge(2, 5, 1.0);
		HashTableSC<Integer, Integer> needed = new HashTableSC<Integer, Integer>(200);
		needed.put(0, 1);
		needed.put(3,1);
		ORArray<Edge<Double>> wut = Graph.pruneMST(numero, needed);
		//for(Edge<Double> edg:wut) 
			//System.out.println(edg.either() + " " + edg.other(edg.either()));
		assertTrue("cual es la sapa joda",wut.getSize() == 3);
	}
}
