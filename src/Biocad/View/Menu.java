
package Biocad.View;

import Biocad.Controller.BiocadController;
import Biocad.Model.Agendamento;
import Biocad.Model.Eleitor;
import Biocad.Model.Guiche;
import Biocad.Util.Console;
import Biocad.Util.Iterador;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class Menu {
    
    private static BiocadController controla = new BiocadController(); 
    static LocalDateTime agora = LocalDateTime.now();
    
    public static void menu() throws IOException, ParseException{
        
        int escolha;
        
        System.out.printf("\t> Recadastramento Biométrico:\n");
        System.out.println("(01)_____Adicionar Guichê");
        System.out.println("(02)_____Listar Guichês");
        System.out.println("(03)_____Buscar Guichês");
        System.out.println("(04)_____Adicionar Eleitor");
        System.out.println("(05)_____Verificar disponibilidade de Guichê por dia");
        System.out.println("(06)_____Agendar horário para eleitor");
        System.out.println("(07)_____Listar Eleitores agendados por dia");
        System.out.println("(08)_____Cancelar agendamento");
        System.out.println("(09)_____Sair");
        System.out.printf("> Selecione uma opção: ");
        escolha = Console.readInt();
        
        switch(escolha){
            case 1:{
                cadastrar_guiche();
                break;
            }
            case 2:{
                listar_guiches();
                break;
            }
            case 3:{
                buscarGuiches();
                break;
            }
            case 4:{
                adicionarEleitor();
                break;
            }
            case 5:{
                verificar_guiche();
                break;
            }
            case 6:{
                agendarHorario();
                break;
            }
            case 7:{
                listarEleitores();
                break;
            }
            case 8:{
               cancelarAgendamento();
                break;
            }
            case 9:{
                clear();
                despedida();
                System.exit(0);
                break;  
            }
            default:{
                System.out.println("> Opção inválida!");
                pause();
                menu();
                break;
            } 
        }           
    }

    private static void cadastrar_guiche() throws IOException, ParseException{
        System.out.println("\t> Cadastro de Guichê");
        System.out.println("> Informe o número da sala:");
        int numeroSala = Console.readInt();
        System.out.println("> Informe o número do corredor:");
        int numeroCorredor = Console.readInt();
        System.out.println("> Informe o nome do atendente:");
        String nomeAtendente = Console.readString();
        
        int confirma = 0;
        do{
            System.out.println("\t> Você confirma os dados inseridos?");
            System.out.println("Não será possível alterá-los posteriormente!");
            System.out.println("\t1 - Sim || 2 - Não");
            confirma = Console.readInt();

            if(confirma == 1){
                clear();
            }
            else if(confirma == 2){
                cadastrar_guiche();
            }
        }while(confirma != 1 && confirma != 2);
        
        Guiche confere = controla.cadastrarGuiche(numeroSala, numeroCorredor, nomeAtendente);
        if(confere == null){
            System.out.println("\t> Não foi possível criar um novo guichê.<");
            pause();
            clear();
            menu();
        }
        else{
            System.out.printf("\t> Guichê cadastrado com sucesso.<\n\n");
            System.out.println("\t> Dados do Guichê:<");
            System.out.println("> Código do Guiche: "+confere.getCodigo());
            System.out.println("> Nome do atendente: "+confere.getAtendente().toUpperCase());
            System.out.println("> Corredor do Guichê: "+confere.getCorredor());
            System.out.println("> Sala do Guichê: "+confere.getNumeroSala());
            pause();
            clear();
            menu();
        }
    }
private static void buscarGuiches() throws IOException, ParseException{
    
        System.out.println("> Digite o código do Guichê a ser procurado: ");
        int codigo = Console.readInt();

        Guiche obtido = controla.obterGuiche(codigo);
        if(obtido == null){
            System.out.println("\t> O Guichê não existe.<");
            pause();
            menu();
        }
        else{
            System.out.println("> Código do Guiche: "+obtido.getCodigo());
            System.out.println("> Nome do atendente: "+obtido.getAtendente().toUpperCase());
            System.out.println("> Corredor do Guichê: "+obtido.getCorredor());
            System.out.println("> Sala do Guichê: "+obtido.getNumeroSala());
            clear();
        }
        pause();
        clear();
        menu();
    }

    private static void listar_guiches() throws IOException, ParseException{
        Iterador percorre = controla.listarGuiche();
        if(!percorre.temProximo()){
            System.out.println("\t> Não existem Guichês cadastrados.<");
            pause();
            clear();
            menu();
        }
        System.out.printf("\t> Listando os Guichês Cadastrados:\n\n");
        while(percorre.temProximo()){
            Guiche mostra = (Guiche) percorre.obterProximo();
            System.out.println("> Código do Guichê: "+mostra.getCodigo());
            System.out.println("> Corredor do Guichê: "+mostra.getCorredor());
            System.out.println("> Sala do Guichê: "+mostra.getNumeroSala());
            System.out.println("> Nome do atendente: "+mostra.getAtendente().toUpperCase());
            clear();
        }
        pause();
        clear();
        menu();
    }

    

    private static void adicionarEleitor() throws IOException, ParseException{
        
        System.out.println("\t> Cadastro de Eleitor:");
        
        System.out.println("> Digite o Nome do Eleitor:");
            String nome = Console.readString();
        System.out.println("> Digite o Número do Título de Eleitor:");
            String titulo = Console.readString();
        System.out.println("> Digite o Nome da mãe:");
            String nomeMae = Console.readString();
        System.out.println("> Digite o Nome do pai:");
            String nomePai = Console.readString();
        System.out.println("> Digite a Data de Nascimento:");
            String data = Console.readString();
        System.out.println("> Digite o número de telefone:");
            int telefone = Console.readInt();
            
        int confirma = 0;
        do{
            System.out.println("\t> Você confirma os dados inseridos?");
            System.out.println("Não será possível alterá-los posteriormente!");
            System.out.println("\t1 - Sim || 2 - Não");
            confirma = Console.readInt();

            if(confirma == 1){
                clear();
            }
            else if(confirma == 2){
                adicionarEleitor();
            }
        }while(confirma != 1 && confirma != 2);
        
        Eleitor verifica = controla.cadastrarEleitor(nome, titulo, nomeMae, nomePai, data, telefone);
        
        if(verifica == null){
            System.out.println("\t> Já existe um eleitor cadastrado com esse Título.<");
            pause();
            clear();
            menu();
        }
        System.out.printf("\t> Eleitor cadastrado com sucesso.<\n\n");
        System.out.println("\t>Dados do Eleitor cadastrado:<");
        System.out.println("> Nome do Eleitor: "+verifica.getNome().toUpperCase());
        System.out.println("> Título de Eleitor: "+verifica.getTitulo());
        pause();
        clear();
        menu();
    }

    private static void verificar_guiche() throws IOException, ParseException {
        
        System.out.println("> Digite o código do guichê que deseja verificar: ");
        int codigo = Console.readInt();
        
        System.out.println(">Digite a data que deseja verificar:");
        String data = Console.readString();
        
        System.out.println(">Digite a ordem que deseja verificar:");
        int ordem = Console.readInt();
        
        SimpleDateFormat formato = new SimpleDateFormat("dd");
        Date date = formato.parse(data);
        
        Guiche verifica = controla.obterGuiche(codigo);
        if(verifica == null){
            System.out.println("\t> O Guichê informado não existe.");
            System.out.println("> Código informado: "+codigo);
            pause();
            clear();
            menu();
        }
        
        Boolean teste = controla.verificarGuiche(codigo, date, ordem);
        if(teste == true){
            System.out.println("\t> Há um agendamento marcado nesse guichê, nesse dia, nessa ordem.");
            pause();
            clear();
            menu();
        }
        else{
            System.out.println("\t> O Guichê está disponível<");
            pause();
            clear();
            menu();
        }
        
        
    }
    
    private static void listarEleitores() throws IOException, ParseException {
        System.out.println("\t> Listar Eleitores por Dia e por Guichê <");
        System.out.print("> Digite o código do Guichê desejado:");
        int num_guiche = Console.readInt();
        System.out.print("> Digite o dia que deseja verificar:");
        int dia = Console.readInt();
        
        String day = ""+dia;
        SimpleDateFormat formato = new SimpleDateFormat("dd");
        Date date = formato.parse(day);
        
        Iterador lista_eleitores = controla.listarEleitoresAgendados(num_guiche, date);
        int existe = 0;
        if(lista_eleitores == null){
            System.out.println("\t> Não existem Eleitores cadastrados no Sistema.<");
            pause();
            clear();
            menu();
        }
        else{
            while(lista_eleitores.temProximo()){
                Eleitor imprime = (Eleitor) lista_eleitores.obterProximo();
                if(imprime.isAgended()){
                    if(imprime.getCodigo() == num_guiche && imprime.getDia().getDay() == date.getDay()){
                        existe++;
                        System.out.println("-------------------------------------------------------------");
                        System.out.println("> Nome: "+imprime.getNome().toUpperCase());
                        System.out.println("> Título:"+imprime.getTitulo().toUpperCase());
                        System.out.println("> Número de Atendimento:"+imprime.getNumeroAtendimento());
                    }
                }
            }
            
            if(existe == 0){
                System.out.println("\t> Não existem Eleitores cadastrados nesse dia e nesse Guichê.<");
                pause();
                clear();
                menu();
            }
        }
        pause();
        clear();
        menu();
        
    }
    private static void agendarHorario() throws IOException, ParseException{
        
        if(agora.getDayOfMonth() != 20){
            System.out.println("\t> Os agendamentos só podem sem realizados dia 20.");
            System.in.read();
            clear();
            menu();
            
        }
        
        System.out.println("\t> Agendamento de Atendimento:");
        
        System.out.println("> Digite o código do Guichê desejado:");
            int codigo = Console.readInt();
        System.out.println("> Digite a data desejada para o atendimento:");
        System.out.println("\tOBS.: Digite apenas o Dia do agendamento.");
        System.out.println("\tLembre-se o agendamento será efetuado para o próximo mês.");
            String data = Console.readString();
            int verifica_data = Integer.parseInt(data);
            
            if(verifica_data < 1 || verifica_data > 31){
                System.out.println("\tDigite uma data entre 01 e 31.");
                clear();
                pause();
                agendarHorario();
            }
                SimpleDateFormat formato = new SimpleDateFormat("dd");
                Date date = formato.parse(data);
        
        System.out.println("> Digite a ordem de atendimento desejado:");
        System.out.println("Dica: Digite '0' para ver os horários relativos a cada ordem.");
            int ordem = Console.readInt();
            if(ordem == 0){
                horarios();
                pause();
                clear();
                System.out.println("> Digite a ordem de atendimento desejado:");
                ordem = Console.readInt();
            }
            if(ordem < 1 || ordem > 32){
                System.out.println("\t> Escolha uma ordem entre 1 e 32");
                pause();
                clear();
                agendarHorario();
            }
        System.out.println("> Digite o Número do Título de Eleitor:");
            String titulo = Console.readString();
            
        int confirma = 0;
        do{
            clear();
            System.out.println("\tVocê confirma os dados inseridos?");
            System.out.println("Não será possível alterá-los posteriormente!");
            System.out.println("\t1 - Sim || 2 - Não");
            confirma = Console.readInt();

            if(confirma == 1){
                clear();
            }
            else if(confirma == 2){
                agendarHorario();
            }
        }while(confirma != 1 && confirma != 2);
        
        Agendamento verifica = controla.agendarHorario(codigo, date, ordem, titulo);
        if(verifica == null){

           Iterador imprime = controla.verificarGuicheLivres();
           if(imprime == null){
               System.out.println("\t> Não existem Guichês cadastrados no Sistema.");
               pause();
               clear();
               menu();
           }
           System.out.println("\t* Horários OCUPADOS no Guichê: " +codigo);
           System.out.println("\t* E no dia: "+date.getDate());
           while(imprime.temProximo()){
               Agendamento mostra = (Agendamento) imprime.obterProximo();
               if(mostra.getCodigo() == codigo && mostra.getData().equals(date)){
                   System.out.println("> Ordem -> "+mostra.getOrdem());
               }
            }
           pause();
           int escolha;
           do{
                System.out.println("\n\t> O que deseja fazer agora?");
                System.out.println("1 - Agendamento || 2 - Ir para o menu");
                escolha = Console.readInt();
                if(escolha == 1){
                    clear();
                    agendarHorario();
                }
                else if(escolha == 2){
                    clear();
                    menu();
                }
           }while(escolha != 1 && escolha != 2);
           
        }
        else{
            System.out.printf("\t>Agendamento realizado com sucesso.<\n\n");
            System.out.println("\t> Dados do Agendamento:<\n");
            System.out.println("> Título do Eleitor: "+verifica.getTitulo());
            System.out.println("> Número do Atendimento: "+verifica.getNumeroAgendamento());
            System.out.println("> Data do atendimento: "+verifica.getData().getDate());
            System.out.println("> Ordem do Atendimento: "+verifica.getOrdem());
            pause();
            clear();
            menu();
        }
    }
    
    
    /*Métodos adicionais para melhorar a legibilidade do código, e aumentar a modularização
    do mesmo.*/
    
    public static void clear(){ //Método Simples, apenas para melhorara a visualização no console.
        System.out.println();
        System.out.println("_____________________________________________________________________");
        System.out.println();
    }
    
    public static void pause() throws IOException{
        /*Método auxiliar apenas para "esperar" o usuário ler a informação passada para ele(a).
        Caso o usuário tente digitar algo além de pressionar o ENTER, uma exceçõa será lançada.*/
        System.out.println("Pressione ENTER para continuar...");
        System.in.read();
    }
    
    public static void despedida(){
        System.out.printf("\t> Obrigado por utilizar nosso sistema de Recadastramento! :)\n"); 
    }
    
    private static void cancelarAgendamento() throws IOException, ParseException {
        System.out.println("\t> Cancelamento de Agendamento:");
        
        System.out.println("> Digite o número do seu Título de Eleitor:");
            String titulo = Console.readString();
        int confirma = 0;
        do{
            clear();
            System.out.println("\t> Você confirma os dados inseridos?");
            System.out.println("Não será possível alterá-los posteriormente!");
            System.out.println("\t1 - Sim || 2 - Não");
            confirma = Console.readInt();

            if(confirma == 1){
                clear();
            }
            else if(confirma == 2){
                cancelarAgendamento();
            }
        }while(confirma != 1 && confirma != 2);
        /*Busca um eleitor com o título fornecido que esteja agendado, caso esteja agendado,
        o agendamento é desfeito, caso não exista um eleitor ou agendamento para tal eleitor,
        a mensagem a seguir é mostrada.*/
        Eleitor teste = controla.cancelarAgendamento(titulo);
        
        if(teste == null){
            System.out.println("\t> Não foi possível efetuar essa operação.<");
            System.out.println("Possíveis acontecimentos:\n> O eleitor informado não existe.");
            System.out.println("> O eleitor informado não possui um agendamento marcado.");
            pause();
            clear();
            menu();
        }
        
        System.out.println("\t> Cancelamento efetuado com sucesso.<");
        pause();
        clear();
        menu();
            
    }
    public static void horarios(){
        LocalTime horario = LocalTime.now();  
        horario = horario.withHour(8);
        horario = horario.withMinute(0);
        horario = horario.withSecond(0);
        horario = horario.withNano(0);
        System.out.println("\t> Esses são os horários disponíveis para atendimento:");
        for(int x = 1; x < 33; x++){
            
            System.out.println("Ordem -> "+x+" - "+"Horário: "+horario);
            if(horario.getHour() == 11 && horario.getMinute() == 45){
                System.out.println("> Horário de Almoço das 12:00 as 13:00");
                horario = horario.withHour(13);
                horario = horario.withMinute(0);
                x++;
                System.out.println("Ordem -> "+x+" - "+"Horário: "+horario);
            }
            horario = horario.plusMinutes(15);
        }
    }

    public static void main(String[] args) throws IOException, ParseException{
        Menu.menu();
    }

    
}
