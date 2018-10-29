
package Biocad.Model;
import java.util.Date;

public class Eleitor {
    
    private String nome;
    private String titulo;
    private String nomeMae;
    private String nomePai;
    private String dataNasc;
    private int telefone;
    
    private boolean isAgended;
    private Guiche codigo;
    private Date dia;
    private Agendamento ordem;
    private Agendamento numeroAtendimento;

    public Eleitor(String nome, String titulo, String nomeMae, String nomePai, String dataNasc, int telefone) {
        this.nome = nome;
        this.titulo = titulo;
        this.nomeMae = nomeMae;
        this.nomePai = nomePai;
        this.dataNasc = dataNasc;
        this.telefone = telefone;
    } 
    public String getNome() {
        return nome;
    }
    
    public String getTitulo() {
        return titulo;
    }
   
    /*Métodos relacionados ao agendamento do eleitor*/
    public int getCodigo() {
        return codigo.getCodigo();
    }

    public void setCodigo(Guiche codigo) {
        this.codigo = codigo;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public int getOrdem() {
        return ordem.getOrdem();
    }

    public void setOrdem(Agendamento ordem) {
        this.ordem = ordem;
    }

    public int getNumeroAtendimento() {
        return numeroAtendimento.getNumeroAgendamento();
    }

    public void setNumeroAtendimento(Agendamento numeroAtendimento) {
        this.numeroAtendimento = numeroAtendimento;
    }

    public boolean isAgended() {
        return isAgended;
    }

    public void setAgended(boolean isAgended) {
        this.isAgended = isAgended;
    }
    
}
