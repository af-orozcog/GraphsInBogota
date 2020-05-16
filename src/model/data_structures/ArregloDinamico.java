package model.data_structures;

import java.util.Random;

public class ArregloDinamico <T> implements IArregloDinamico <T> {

	private int max;
	private int act;
	private T objetos [];
	
	public ArregloDinamico (int pMax) 
	
	{
	max = pMax;
	act = 0;
	objetos = (T[]) new Object[pMax];
	}
	public int darCapacidad() {
	
		return max;
	}

	@Override
	public int tamaño() {
		return act;
	}

	@Override
	public T get(int i) {
		// TODO Auto-generated method stub
		return objetos[i];
	}

	@Override
	public void add(T dato) {
		if(max == act)
		{
			max ++;
			T[] temp = objetos;
			objetos = (T[])new Object [max];
			for(int i = 0;  i< act; i++)
			{
				objetos[i] = temp[i];
			}
		}
		objetos[act] = dato;
		act ++;
		
	}

	@Override
	public void delate(int pos) 
	{
		if(pos <0 || pos > act)
		{
			throw new ArrayIndexOutOfBoundsException(pos);
		}
		if(pos == act)
		{
			objetos[pos] = null;
		}
		else
		{
			for(int i = pos; pos <act - 1; i++)
			{
				objetos[i] = objetos[i+1];
			}
			objetos[act -1] = null;
		}
		act --;
	}

	@Override
	public void intercambiar(int pos1, int pos2) {
		T a = objetos[pos1];
		T b = objetos[pos2];
		objetos[pos1] = a;
		objetos[pos2] = b;
		
	}

	@Override
	public void asignar(int posicion, T dato) {
		// TODO Auto-generated method stub
		objetos[posicion] = dato;
	}

	@Override
	public void shuffle() {
		// TODO Auto-generated method stub
		int n = act;
		Random r = new Random();
		for(int i = n-1; i > 0; i++)
		{
			int j = r.nextInt(i);
			intercambiar(i, j);
		}
	}

}
