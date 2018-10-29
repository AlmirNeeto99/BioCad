
package Biocad.Model;
import java.util.Date;
public class Agendamento {

    private int numeroAgendamento;
    private Date data;
    private int ordem;
    
    private Eleitor titulo;
    private Guiche codigo;
    
    public Agendamento(int numeroAgendamento, Date data, int ordem, Eleitor titulo, Guiche codigo) {
        this.numeroAgendamento = numeroAgendamento;
        this.data = data;
        this.ordem = ordem;
        this.titulo = titulo;
        this.codigo = codigo;
    }

    public int getNumeroAgendamento() {
        return numeroAgendamento;
    }

    public Date getData() {
        return data;
    }

    public int getOrdem() {
        return ordem;
    }

    
    /*Métodos relacionados aos Dados do eleitor e dados do guichê*/
    public String getTitulo() {
        return titulo.getTitulo();
    }

    public void setTitulo(Eleitor titulo) {
        this.titulo = titulo;
    } 

    public int getCodigo() {
        return codigo.getCodigo();
    }

    public void setCodigo(Guiche codigo) {
        this.codigo = codigo;
    }
    
    @Override
    public boolean equals(Object compara){
        if(compara instanceof Agendamento){
            Agendamento compara2 = (Agendamento) compara;
            if(numeroAgendamento > compara2.getNumeroAgendamento()){
                return true;
            }
        }
        return false;
    }
    
}

