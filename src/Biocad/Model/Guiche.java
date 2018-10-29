
package Biocad.Model;

public class Guiche {
    private int codigo;
    private int numeroSala;
    private int corredor;
    private String atendente;
    
    public Guiche(int codigo, int numeroSala, int corredor, String atendente) {  
        this.codigo = codigo;
        this.numeroSala = numeroSala;
        this.corredor = corredor;
        this.atendente = atendente;
    }
    
    public int getCodigo() {
        return codigo;
    }

    public int getNumeroSala() {
        return numeroSala;
    }

    public int getCorredor() {
        return corredor;
    }

    public String getAtendente() {
        return atendente;
    }
    
}
