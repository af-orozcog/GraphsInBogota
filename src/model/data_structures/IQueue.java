package model.data_structures;

public interface IQueue<N extends Node> 
{
	public N sacarDeCola() throws Exception;
	public int darTamanio();
	public boolean vacia();
	public N darPrimero();
}
