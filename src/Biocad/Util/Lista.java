
/*Classe referente à Lista Escadeada utilizada no problema*/

package Biocad.Util; //Pacote onde está inserido a Lista

import Biocad.Model.Agendamento;


public class Lista implements ILista{
    
    private Node head;  //Nó que aponta para o primeiro elemento da lista.
    private Node tail;  //Nó que aponta para o último elemento da lista.
    
    private class Node { //Classe interna, responsável por encapsular os objetos passados para ela.
    
        private Object dados; //Objeto que será guardado no nó.
        private Node next;  //Referência para o próximo objeto, se existir.

    public Node(Object dados) { //Construtor do Nó, onde é passado o objeto como parâmetro.
        this.dados = dados;
    }

    public Object getDados() { //Método que retorna o objeto do nó.
        return dados;
    }

    public Node getNext() { //Método que retorna o próximo nó, o qual o atual o referencia.
        return next;
    }

    public void setNext(Node next) { //Método que altera o próximo nó.
        this.next = next;
    } 
}
    @Override
    public boolean estaVazia(){ //Método que retorna verdadeiro quando a lista estiver vazia.
        return head == null;
    }
    
    @Override
    public int obterTamanho(){ //Método que retorna o tamanho da lista, baseado na contagem de nós.
        
        int tamanho = 0;
        for(Node cabeca = head; cabeca != null; cabeca = cabeca.getNext()){
            tamanho++;
        }
        return tamanho;
    }
    
    @Override
    public void inserirInicio(Object objeto) { //Método que insere um nó no inicio da lista.
        
        if(estaVazia()){
            /*Verificação feita, para garantir que a "cauda" da lista apontará para algum nó.*/
            head = new Node(objeto);
            tail = head;
        }
        else{
            Node novo = new Node(objeto);
            novo.setNext(head);
            head = novo;
        }
    }
    
    @Override
    public void inserirFinal(Object objeto) {
        Node novo = new Node(objeto);
        /*Verificação feita, para garantir que a "cauda" da lista apontará para algum nó.*/
        if(estaVazia()){
            tail.setNext(novo);    
            tail = novo;
            head = tail;
        }
        else{
            tail.setNext(novo);
            tail = novo;
        }
    }
    @Override
    public void removerInicio() { //Método que remove a cabeça da lista.
        head = head.getNext(); 
    }
    
    @Override
    public void removerFinal() { //Método que remove a cauda da lista.
        Node anterior = pegarNo(obterTamanho() - 1);
        anterior.setNext(tail.getNext());
        tail = anterior;
    }
    
    @Override
    public Object recuperarItem(int indice) { //Método que retorna o objeto contido em certo nó da lista.
        Node cabeca = pegarNo(indice);
            if(cabeca != null){
                return cabeca.getDados();
            } 
        return null;
    }
    
    public Node pegarNo(int indice){ //Método que retorna um determinado nó da lista.
        if(indice >= 0 && indice < obterTamanho()){
            Node percorre = head;
            for(int x = 0; x < indice; x++){
                percorre = percorre.getNext();
            }
            return percorre;
        }
        return null;
    }
    public void removeMeio(int indice){
        if(indice == 0){
            head = head.getNext();
            tail = head;
        }
        else if(indice > 0 && indice <= obterTamanho() - 1){
            Node anterior = pegarNo(indice - 1);
            Node removido = anterior.getNext();
            anterior.setNext(removido.getNext());
        }
    }
    
    public Object inserirOrdenado(Object objeto){
        
        Agendamento teste = (Agendamento) objeto;

        int contador = 0;
        Node compara = head;
        
        while(compara != null && objeto.equals(compara.getDados())){
            contador++;
            compara = compara.getNext();
        }
        if(compara == null){
            inserirFinal(objeto);
            return objeto;
        }
        else if(contador == 0){
            inserirInicio(objeto);
            return objeto;
        }
        else if (contador > 0){
            Node anterior = pegarNo(contador - 1);
            Node novo = new Node(objeto);
            
            novo.setNext(anterior.getNext());
            anterior.setNext(novo);
            return objeto;
        }     
        return null;
    }
    @Override
    public Iterador iterador() {
        return new novoIterador();
    }
    

    public class novoIterador implements Iterador{
        private int atual;
        
        @Override
        public boolean temProximo(){
            return pegarNo(atual) != null;
        }
        
        @Override
        public Object obterProximo(){
            Object prox = recuperarItem(atual);
            atual++;
            return prox;
        }
    }
    
}
