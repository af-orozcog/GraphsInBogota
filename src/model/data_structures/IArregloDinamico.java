package model.data_structures;

public interface IArregloDinamico <T>
{
public  int darCapacidad();
public int tamaño();
public T get(int i);
public void add(T dato);
public void delate(int posicion);
public void intercambiar(int posicion1, int pos2);
public void asignar(int posicion, T dato);
public void shuffle();

}
