package test.data_structures;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import model.data_structures.QuickSort;

public class TestQuickSort {
	
	private QuickSort sorter;
	@Before
	public void setup()
	{
		sorter = new QuickSort();
	}
	@Test
	public void testDecendentInputShellSort()
	{
		Comparable a[] = {999, 998, 678,544,433,300,264,250,100,99,56,31,4,0};
		sorter.sort(a);
		assertEquals(0, a[0]);
		assertEquals(a[a.length-1], 999);
	}
	@Test
	public void testShuffleInputShellSort()
	{
		Comparable a[] = {34,535,666,634,3,66,223,5,25,20,10,1,999};
		sorter.sort(a);
		assertEquals(1, a[0]);
		assertEquals(a[a.length-1], 999);
	}
	public void testAscendingInputShellSort()
	{
		Comparable a[] = {0,4,5,6,7,52,67,88,363,364,677,888,999};
		sorter.sort(a);
		assertEquals(999, a[0]);
		assertEquals(0, a[a.length-1]);
	}

}
