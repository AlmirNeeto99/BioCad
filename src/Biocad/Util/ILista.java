package Biocad.Util;


public interface ILista {

    public boolean estaVazia();

    public int obterTamanho();

    public void inserirInicio(Object o);

    public void inserirFinal(Object o);

    public void removerInicio();

    public void removerFinal();

    public Object recuperarItem(int indice);

    public Iterador iterador();
}
