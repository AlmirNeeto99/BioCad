
package Biocad.Controller;
import Biocad.Model.*;
import Biocad.Util.*;
import static java.time.DayOfWeek.SATURDAY; //Biblioteca importada para verificar se o dia é um sábado
import static java.time.DayOfWeek.SUNDAY; //Biblioteca importada para verificar se o dia é um domingo
import java.time.Instant; //Permite "pegar" o horário atual do PC, para verificar se é dia 20.
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId; //"Seta" a zona de horário do programa para o padrão BR
import java.util.Date;

public class BiocadController {

    private static int CODIGO_GUICHE = 1000; //Váriavel que permite gerar "automaticamento" o código do guichê
    private Lista guiche_lista; //Lista para armazenar os guichês
    private Lista agendamentos_lista; //Lista para armazenar os agendamentos
    private Lista eleitores_lista; //Lista para armazenar os eleitores.
    
    public BiocadController(){
        this.guiche_lista = new Lista(); //Cria a lista de guichês
        this.agendamentos_lista = new Lista(); //Cria a lista de agendamentos
        this.eleitores_lista = new Lista(); //Cria a lista de Eleitores
    }
    
    public Guiche cadastrarGuiche(int numeroSala, int corredor, String atendente){
        Guiche guiche = new Guiche(CODIGO_GUICHE, numeroSala, corredor, atendente);
        if(guiche_lista.estaVazia()){
            /*Verifica se a lista está vazia e insere no início em caso positivo,
            Senão, insere no final*/
            guiche_lista.inserirInicio(guiche);
            CODIGO_GUICHE = CODIGO_GUICHE + 10; //Incrementa a váriavel para os próximos guichês
        }
        else{
            guiche_lista.inserirFinal(guiche);
            CODIGO_GUICHE = CODIGO_GUICHE + 10;
        }
        return guiche; //Retorna o guichê após criado
    }

    public Guiche obterGuiche(int codigo) {
        /*Percorre a lista procurando o guichê pelo código informado,
        caso exista, retorna o guichê, senão retorna 'null'*/
        if(guiche_lista.estaVazia()){ //Se a lista estiver vazia, retorna null
            return null;
        }
        Iterador percorre = guiche_lista.iterador();
        while(percorre.temProximo()){
            Guiche obtido = (Guiche) percorre.obterProximo();
            if(obtido.getCodigo() == codigo){
                return obtido;
            }   
        }
        return null;
    }
    
    public Iterador listarGuiche() {
            /*Retorna um iterador, que possibilita percorrer a lista de guichês*/
            Iterador percorre = guiche_lista.iterador(); 
        return percorre;
    }

    public Eleitor cadastrarEleitor(String nome, String titulo, String nomeMae, String nomePai, String dataNasc, int telefone) {
        
        /*Método que que cadastra um novo eleitor no sistema BIOCAD*/
        
        Eleitor atual = new Eleitor(nome, titulo, nomeMae, nomePai, dataNasc, telefone);
        //Verifica se a lista está vazia e insere no início
        if(eleitores_lista.estaVazia()){
            eleitores_lista.inserirInicio(atual);
            return atual;
        }
        /*Caso a lista não esteja vazia, percorre a lista verificando se já existe
        um eleitor com o mesmo título cadastrado. Em caso positivo, retorna 'null',
        em caso negativo insere o novo eleitor no final.*/
        else if(!eleitores_lista.estaVazia()){
            Iterador verifica = eleitores_lista.iterador();
            while(verifica.temProximo()){
                Eleitor compara = (Eleitor) verifica.obterProximo();
                if(compara.getTitulo().equals(atual.getTitulo())){
                    return null;
                }
            }
            eleitores_lista.inserirFinal(atual);
        }
        return atual;
    }
  

    public Agendamento agendarHorario(int numeroGuiche, Date data, int ordem, String tituloEleitor ) {
        
        Guiche guiche_verifica = obterGuiche(numeroGuiche); //Verifica a existência do guichê
        Eleitor eleitor_verifica = verificaEleitorAgendado(tituloEleitor); //Verifica a existência do eleitor
        
        /*Caso o guichê ou o eleitor informados não existam, retorna 'null'*/
        if(guiche_verifica == null || eleitor_verifica == null){
            return null;
        }
        /*Verifica se já existe um agendamento no guichê, daquele dia, em determinada ordem.*/
        int verifica_agendamento = gerarNumAtendimento(numeroGuiche, data, ordem); 
        
         //Caso o método acima retorne '0', significa que já existe um agendamento
         //E então, o método atual, retorna 'null'
        if(verifica_agendamento == 0){ 
            return null;
        }
        
        /*Caso passe nas verificações acima, o agendamento é cria e inserido na lista.*/
        Agendamento novo_agendamento = new Agendamento(verifica_agendamento, data, ordem, eleitor_verifica, guiche_verifica);
        if(agendamentos_lista.estaVazia()){
            agendamentos_lista.inserirInicio(novo_agendamento);
        }
        else{
            agendamentos_lista.inserirOrdenado(novo_agendamento);
        }
        /*Modifica alguns atributos do eleitor que está efetuando um agendamento
        para ser mais fácil detectar um eleitor agendado*/
            eleitor_verifica.setAgended(true);
            eleitor_verifica.setOrdem(novo_agendamento);
            eleitor_verifica.setDia(data);
            eleitor_verifica.setNumeroAtendimento(novo_agendamento);
            eleitor_verifica.setCodigo(guiche_verifica);
        return novo_agendamento; //Retorna um agendamento, para informar que o agendamento foi criado.                
        }
    


    public Iterador listarEleitoresAgendados(int numeroGuiche, Date data) {
        
        /*Verifica se a lista de eleitores existe, em caso positivo, retorna um iterador.
        Senão, retorna 'null'*/
        if(eleitores_lista.estaVazia()){
            return null;
        }
            Iterador percorre = eleitores_lista.iterador();
        return percorre;
    }
    
    
    
    public Boolean verificarGuiche(int codigoGuiche, Date data, int ordem) {
        /*Caso já exista um atendimento marcado com os dados informados,
        retornará 'true'.*/
        if(gerarNumAtendimento(codigoGuiche, data, ordem) == 0){
            return true;
        }
        else{
            return false;
        }
    } 
    
    
    
    /*Métodos adicionados por mim, para melhorar a legibilidade do código e aumentar a modularização
    do mesmo.*/
    
    public Eleitor cancelarAgendamento(String titulo){
        //Método que cancela um agendamento(título bem sugestivo) ;)
        int numAtendimento = 0;
        Eleitor compara = null;
        Iterador percorre = eleitores_lista.iterador();
        /*Percorre a lista verificando se o eleitor com o título passado possui um agendamento,
        em caso negativo, retorna 'null'.
        Em caso positivo, modifica todos os atributos relacionados ao agendamento,
        e faz uma cópia do número de atendimento, para ser mais fácil procurar o agendamento
        na sua respectiva lista.*/
        if(!eleitores_lista.estaVazia()){
            while(percorre.temProximo()){
                compara = (Eleitor) percorre.obterProximo();
                if(compara.getTitulo().equals(titulo)){
                    if(!compara.isAgended()){
                        //Caso o eleitor, exista mas não esteja agendado, retorna 'null'.
                        return null;
                    }
                    //Caso exista, altera os atributos.
                    else if(compara.isAgended()){
                        compara.setAgended(false);
                        compara.setCodigo(null);
                        compara.setOrdem(null);
                        numAtendimento = compara.getNumeroAtendimento();
                        compara.setNumeroAtendimento(null);
                        compara.setCodigo(null);
                        compara.setDia(null);
                    }   
                }
            }
        }
        /*Percorre a lista de agendamentos procurando o agendamento referente ao do eleitor passado
        como parâmetro. 
        Vale lembrar, que essa parte do método só será executada caso o referido
        eleitor tenha um agendamento.
        Senão o método encerra na linha '167'.*/
        Iterador percorre2 = agendamentos_lista.iterador();
        int posicao = 0;
        while(percorre2.temProximo()){
            Agendamento compara_agenda = (Agendamento)percorre2.obterProximo();
            if(compara_agenda.getNumeroAgendamento() == numAtendimento){
                agendamentos_lista.removeMeio(posicao);
                return compara;
            }
            posicao++;
        }
        return null; //Caso não "entre" em nenhum caso, retorna 'null'.
    }
    
    public Agendamento verificaAgendamento(int numAgendamento){
        //Se a lista de agendamentos estiver vazia, retorna 'null'.
        if(agendamentos_lista.estaVazia()){
            return null;
        }
        /*Percorre a lista de agendamentos verificando se existe um agendamento existente com
        o mesmo número de agendamento.*/
        Iterador percorre = agendamentos_lista.iterador();
        Agendamento verifica = null;
        while(percorre.temProximo()){
            verifica = (Agendamento) percorre.obterProximo();
            if(verifica.getNumeroAgendamento() == numAgendamento){
                return verifica; //Caso já exista um agendamento com aquele número, retorna o objeto.
            }
        }
        return null;
    }
    
    public Eleitor verificaEleitorAgendado(String titulo){
        Eleitor verifica = null;
        /*Retorna 'null' caso a lista de eleitores estaja vazia*/
        if(eleitores_lista.estaVazia()){
            return null;
        }
        /*Percorre a lista verificando se existe um eleitor com o título informado
        e se o mesmo possui um agendamento marcado.
        Caso exista um agendamento, retorna 'null', senão, retorna o eleitor.*/
        Iterador percorre = eleitores_lista.iterador();
        while(percorre.temProximo()){
            verifica = (Eleitor) percorre.obterProximo();
            if(verifica.getTitulo().equals(titulo)){
                if(verifica.isAgended()){
                    return null;
                }
                break;
            }
        }
        return verifica;
    }
    public int gerarNumAtendimento(int numeroGuiche, Date data, int ordem){
        /*Método que gera um número de atendimento ao agendamento, que possibilita
        diferenciar um agendamento do outro, e facilita busca e alterações.*/
        ZoneId zona_PC = ZoneId.systemDefault(); //"Seta" a zona do horário para 'BR'
        Instant converter_tipo = data.toInstant(); //Necessário para converter, 'Date' para 'LocalDateTime'
        LocalDate dia_agendamento = converter_tipo.atZone(zona_PC).toLocalDate(); //Efetua a conversão.
        
        LocalDateTime dados_atuais = LocalDateTime.now(); //Pega o horário atual do computador.
        
        /*Transforma o horário do computador, em horário de agendamento.
        Exemplo: se hoje são 15/03/2017, e o usuário deseja realizar um agendamento para o dia 20.
        Então, agenda será: 20/04/2017, ou seja, essa execução, altera o dia, e incrementa o mês,
        admitindo que os agendamentos são realizados para o mês corrente.
        Essa função foi utilizada, pois ela verifica casos específicos, exemplo, estamos em
        janeiro e o usuário deseja fazer um agendamento para o dia 30, sendo assim, o dia
        de atendimento seria 30/02/2017, porém, fevereiro não possui 30 dias, e o dia seria
        alterado automaticamente para 28/02/2017*/
        LocalDateTime agenda = LocalDateTime.of(dados_atuais.getYear(), dados_atuais.getMonthValue(), dia_agendamento.getDayOfMonth(), 0, 0);
        agenda = agenda.plusMonths(1); //Acrescenta um mês ao agendamento.
        //Verifica se o dia escolhido pelo usuário é um sábado ou domingo.
        if(agenda.getDayOfWeek() == SATURDAY || agenda.getDayOfWeek() == SUNDAY){
            return 0;
        }
        /*A partir daqui, o dia e a ordem são transformados em Strings para possibilitar
        uma conversão à um inteiro.*/
        int dia = agenda.getDayOfMonth();
        String day = null;
        if(dia < 10){
            //Acrescenta um zero à esquerda do número, caso este seja menor que 10.
            //Ex.: 9 = 09.
            day = "0"+dia;
        }
        else{
            day = ""+dia;
        }
        String ordem_string = null;
        //Idem ao dia.
        if(ordem < 10){
            ordem_string = "0"+ordem;
        }
        else{
            ordem_string = ""+ordem;
        }
        /*Concatena os número em uma só string e o transforma em um único inteiro para todo
        o programa, que permite a diferenciação de agendamentos.*/
        String codigo = ""+numeroGuiche;
        String num = codigo+day+ordem_string; //Concatenação.
        int numAtendimento = Integer.parseInt(num); //Transforma.
        /*Verifica se o número de atendimento gerado já existe na lista de agendamentos,
        em caso positivo, retorna '0', em caso negativo, retorna o novo número para o agendamento.*/
        Agendamento verifica_agendamento = verificaAgendamento(numAtendimento);
        if(verifica_agendamento == null){
            return numAtendimento;
        }
        else{
            return 0;
        }
    }
    public Iterador verificarGuicheLivres(){
        if(agendamentos_lista.estaVazia()){
            return null;
        }
            Iterador imprime = agendamentos_lista.iterador();
        return imprime;
    }
}

