/**
 * Esta classe Ã© responsÃ¡vel pela apresentaÃ§Ã£o do menu ao utilizador e
 * pelo tratamento de todo o input / output.
 * As variÃ¡veis de instÃ¢ncia sÃ£o a classe UmCarroJa que contÃ©m as
 * estruturas de dados utilizadas, bem como manuseamento das mesmas, para
 * alÃ©m de trÃªs menus principais, gerados a partir da classe Menu.
 * 
 * 
 * 
 * 
 * @version 20190415
 */

import java.io.*;
import static java.lang.System.out;


import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;

import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.Comparator;


import java.io.IOException;
import java.util.InputMismatchException;

import java.lang.String;

public class UmCarroJaApp{
    
    /** VariÃ¡veis de Classe */
    
    /* InstÃ¢ncia da classe UmCarroJa */
    private static UmCarroJa ucj;
    
    /* Menu inicial da aplicaÃ§Ã£o */
    private static Menu menuInicial;
    /* Menu dos utilizadores da aplicaÃ§Ã£o */
    private static Menu menuUtilizador;
    /* Menu do administrador da aplicaÃ§Ã£o */
    private static Menu menuAdmin;
    /* Menu do proprietÃ¡rio */
    private static Menu menuProprietario;
    /* Menu do cliente */
    private static Menu menuCliente;
    
    /* Nome do ficheiro de dados a gravar / ler */
    private static String ficheiroDados = "UCJ.dat";
    
    /* Data de inÃ­cio da aplicaÃ§Ã£o */
    private static GregorianCalendar dataInicioApp;

    
    /** O construtor Ã© privado, pois nÃ£o queremos instÃ¢ncias da mesma. */
    private UmCarroJaApp() {}

    /** MÃ©todos de Classe */

    /**
     * @brief Inicia a aplicaÃ§Ã£o lendo o ficheiro objeto que contÃ©m
     * a data de inÃ­cio da aplicacaÃ§Ã£o e os dados.
     */
    private static void initApp() {
        try {
            FileInputStream fis = new FileInputStream(ficheiroDados);
            ObjectInputStream ois = new ObjectInputStream(fis);
            dataInicioApp = (GregorianCalendar) ois.readObject();
            ucj = (UmCarroJa) ois.readObject();
        }catch (FileNotFoundException e){
            dataInicioApp = new GregorianCalendar();
            ucj = new UmCarroJa();
            out.println("O ficheiro de dados nÃ£o foi encontrado!\n.");
        }
        catch (IOException e) {
            dataInicioApp = new GregorianCalendar();
            ucj = new UmCarroJa();
            System.out.println("Erro ao ler os dados!\nErro de leitura\n.");
        }
        catch (ClassNotFoundException e){
            dataInicioApp = new GregorianCalendar();
            ucj = new UmCarroJa();
            out.println("Erro ao ler os dados! Ficheiro com formato desconhecido!\n.");
        }
    }

    /**
     * Inicia os diferentes Menus da aplicaÃ§Ã£o, respetivamente o Menu Inicial,
     * o Menu do ProprietÃ¡rio e o Menu do Cliente.
     */
    private static void initMenus(){
        String[] inicial =      {"Menu Inicial", 
                                 "Menu Utilizador",
                                 "Menu AdministraÃ§Ã£o",
                                 "Guardar Dados da AplicaÃ§Ã£o"};
                                 
        String[] utilizador =   {"Menu Utilizador",
                                 "Iniciar SessÃ£o", 
                                 "Registar Utilizador"};
                                 
        String[] admin =        {"Menu AdministraÃ§Ã£o", 
                                 "Extrato de Aluguer de uma Viatura num Determinado PerÃ­odo",
                                 "10 Clientes com Mais Alugueres",
                                 "10 Clientes com Mais Km Percorridos"};
        
        String[] proprietario = {"Menu ProprietÃ¡rio",
                                 "Registar Viatura",
                                 "Sinalizar Viatura DisponÃ­vel", 
                                 "Abastecer VeÃ­culo", 
                                 "Alterar o PreÃ§o por Km", 
                                 "Aceitar/Rejeitar Alugueres", 
                                 "Registar Suplemento no PreÃ§o da Viagem",
                                 "Meus Alugueres",
                                 "Valor Faturado entre Datas"};
        
        String[] cliente =      {"Menu Cliente", 
                                 "Alugar VeÃ­culo Mais PrÃ³ximo", 
                                 "Alugar VeÃ­culo Mais Barato", 
                                 "Alugar VeÃ­culo Mais Barato num PerÃ­metro", 
                                 "Alugar Viatura EspecÃ­fica", 
                                 "Alugar Viatura com Determinada Autonomia",
                                 "Meus Alugueres"};
        
        menuInicial = new Menu(inicial);
        menuUtilizador = new Menu(utilizador);
        menuAdmin = new Menu(admin);
        menuProprietario = new Menu(proprietario);
        menuCliente = new Menu(cliente);
    }

    /**
     * @brief MÃ©todo responsÃ¡vel por ler a data de hoje do Input e fazer a comparaÃ§Ã£o com a data
     * guardada no ficheiro de dados.
     */
    private static void lerData(){
        GregorianCalendar dataAtual;
        out.print("Digite a Data do Dia de Hoje (dd-mm-aaaa): ");
        do {
            dataAtual  = Input.lerData("Data de Hoje InvÃ¡lida! Digite Novamente a Data (dd-mm-aaaa): ", "Digite a Data do Dia de Hoje (dd-mm-aaaa): ");
        }while (!dataInicioApp.equals(dataAtual) || dataAtual.before(dataInicioApp));
        dataInicioApp = dataAtual;
        UmCarroJaApp.guardarDados();
    }

    /**
     * MÃ©todo responsÃ¡vel por limpar o terminal do IDE BlueJ.
     * Retirado da pÃ¡gina de tips da blueJ.org.
     *
     * Este mÃ©todo nÃ£o deverÃ¡ funcionar noutros IDE's excepto BlueJ.
     */
    private static void clearScreen(){
        System.out.print('\u000C');
    }
    
    public static void main(String[] args){
        //initApp();
        initMenus();
        ucj = new UmCarroJa();
        lerDadosTXT("logsPOO_carregamentoInicial.bak");
        out.println("NÃšMERO UTILIZADORES: " + ucj.getNUsers());
        out.println("NÃšMERO VEÃ?CULOS: " + ucj.getNVeiculos());
        out.println("NÃšMERO ALUGUERES: " + ucj.getNAlugs());
        //printUsers();
        //printVeiculos();
        //printAlugs();
        lerData();
        
        ucj.alugueresEfetuados(dataInicioApp);
        do{
            UmCarroJaApp.clearScreen();
            menuInicial.executa();
            switch(menuInicial.getOpcao()){
                case 1: menuUtilizador();
                        break;
                case 2: menuAdmin();
                        break;
                case 3: guardarDados();
                        break;
            }
        }while(menuInicial.getOpcao() != 0);
        guardarDados();
    }
    
    /******************************************************************************
     *                            MENU DOS UTILIZADORES                           *
     ******************************************************************************/
     
    private static void menuUtilizador(){
        UmCarroJaApp.clearScreen();
        do{
            menuUtilizador.executa();
            switch(menuUtilizador.getOpcao()){
                case 1:
                    try{
                        iniciarSessao();
                    }catch (UtilizadorNaoExisteException e){
                        out.println("O utilizador introduzido nÃ£o se encontra registado!");
                    }catch (PasswordIncorretaException p){
                        out.println("A password que introduziu estÃ¡ incorreta!");
                    }
                    break;
                case 2: registarUtilizador();
                        break;                        
            }
        }while(menuUtilizador.getOpcao() != 0);
    }
    
    /**
     * @brief MÃ©todo responsÃ¡vel pela exibiÃ§Ã£o das informaÃ§Ãµes que permitem a um utilizador autenticar-se na aplicaÃ§Ã£o.
     * 
     * Primeiro pede-se e verifica-se o email e a password do utilizador. Caso o login esteja correto, verifica-se se
     * se trata de um cliente ou de um proprietÃ¡rio e chama-se o mÃ©todo respetivo para iniciar sessÃ£o. SÃ£o ainda lanÃ§adas
     * as exceÃ§Ãµes UtilizadorNaoExisteException caso o utilizador nÃ£o esteja registado e a exceÃ§Ã£o
     * PasswordIncorretaException, caso a password esteja incorreta.
     */
    private static void iniciarSessao() throws UtilizadorNaoExisteException, PasswordIncorretaException{
        String email;
        String password;
        
        UmCarroJaApp.clearScreen();
        
        out.print("Digite o seu Email: ");
        email = Input.lerString("Email InvÃ¡lido!", "Digite novamente o seu Email: ");
        
        out.print("Digite a sua Password: ");
        password = Input.lerString("Password InvÃ¡lida!", "Digite novamente a sua Password: ");
        
        try{
            ucj.iniciarSessao(email, password);
            try{
                Utilizador user = ucj.getUtilizador(email);
                
                if (ucj.getUtilizador(email) instanceof Proprietario){
                    UmCarroJaApp.sessaoProprietario();
                }
                else{
                    UmCarroJaApp.sessaoCliente();
                }
            }catch (UtilizadorNaoExisteException e){
                out.println("O utilizador com o email: " + e.getMessage() + " nÃ£o existe!");
            }
        }
        catch (UtilizadorNaoExisteException u){
            out.println("O utilizador com o email: " + u.getMessage() + " nÃ£o existe!");
        }
        catch (PasswordIncorretaException p){
            out.println("A password introduzida: " + p.getMessage() + " estÃ¡ incorreta!");
        }
    }
    
    /**
     * @brief MÃ©todo responsÃ¡vel por proceder ao registo de um utilizador na aplicaÃ§Ã£o.
     *
     * SÃ£o pedidas todas as informaÃ§Ãµes necessÃ¡rias para o registo na aplicaÃ§Ã£o do utilizador
     * e no final Ã© perguntado qual o tipo de conta que pretende. Posteriormente este Ã©
     * inserido na base de dados da aplicaÃ§Ã£o.
     */
    private static void registarUtilizador(){
        String email = "";
        String password;
        String nome;
        String nif;
        String morada;
        GregorianCalendar dataNascimento;
        
        boolean existe = true;
        int tipoUser;
        
        UmCarroJaApp.clearScreen();
        
        while(existe){
            out.print("Digite o seu Email: ");
            email = Input.lerString("Email InvÃ¡lido!", "Digite novamente o seu Email: ");
            existe = ucj.existeUtilizador(email);
            if (existe){
                out.println("JÃ¡ existe um utilizador com o email introduzido!");
            }
        }
        
        out.print("Digite a sua Password: ");
        password = Input.lerString("Password InvÃ¡lida!", "Digite novamente a sua Password: ");
        
        out.print("Digite o seu Nome Completo: ");
        nome = Input.lerString("Nome InvÃ¡lido!", "Digite novamente o seu Nome Completo: ");
        
        out.print("Digite o seu NIF: ");
        nif = Input.lerString("NIF InvÃ¡lido!", "Digite novamente o seu NIF: ");
        
        out.print("Digite a sua Morada: ");
        morada = Input.lerString("Morada InvÃ¡lida!", "Digite novamente a sua Morada: ");
        
        out.print("Digite a sua Data de Nascimento (dd-mm-aaaa): ");
        dataNascimento = Input.lerData("Data de Nascimento InvÃ¡lida!", "Digite novamente a sua Data de Nascimento (dd-mm-aaaa): ");
            
        tipoUser = Menu.MenuLerInt(1, 2, "\n******************\n1 - ProprietÃ¡rio\n2 - Cliente.\nEscolha o Tipo de Utilizador: ", 
                                 "Tipo de Utilizador InvÃ¡lido!, Tente novamente!");
                                 
        if (tipoUser == 1){
            UmCarroJaApp.registarProprietario(nome, nif, email, password, morada, dataNascimento);
        }else{
            UmCarroJaApp.registarCliente(nome, nif, email, password, morada, dataNascimento);
        }
    }
    
    private static void registarProprietario(String nome, String nif, String email, String password, 
                                            String morada, GregorianCalendar data){
                                                
        Proprietario prop = new Proprietario(nome, nif, email, password, morada, data);
                                            
        try {
            ucj.registarUtilizador(prop);
            guardarDados();
        }
        catch (UtilizadorJaExisteException e) {
            out.println("O proprietÃ¡rio " + e.getMessage() +  " jÃ¡ estÃ¡ registado!\n");
        }
    }
    
    private static void registarCliente(String nome, String nif, String email, String password, 
                                       String morada, GregorianCalendar data){
        double latitude, longitude; 
        
        out.print("Digite a sua Latitude: ");
        latitude = Input.lerDouble("Latitude InvÃ¡lida!", "Digite novamente a sua Latitude: ");
        
        out.print("Digite a sua Longitude: ");
        longitude = Input.lerDouble("Longitude InvÃ¡lida!", "Digite novamente a sua Longitude: ");
        
        Coordinate cords = new Coordinate(latitude, longitude);
        
        Cliente cli = new Cliente(nome, nif, email, password, morada, data, cords, 0, 0, 0.0);
                                            
        try {
            ucj.registarUtilizador(cli);
            guardarDados();
        }
        catch (UtilizadorJaExisteException e) {
            out.println("O cliente " + e.getMessage() +  " jÃ¡ estÃ¡ registado!\n");
        }                       
    }
    
    /******************************************************************************
     *                         FIM DO MENU DOS UTILIZADORES                       *
     ******************************************************************************/


    /******************************************************************************
     *                            MENU DA ADMINISTRAÃ‡ÃƒO                           *
     ******************************************************************************/
    
    private static void menuAdmin(){
        UmCarroJaApp.clearScreen();
        do{
            menuAdmin.executa();
            switch(menuAdmin.getOpcao()){
                case 1: extratoAluguerViatura();
                    break;
                case 2: clientesMaisAlugueres();
                    break;
                case 3: clientesComMaisKm();
            }
        }while(menuAdmin.getOpcao() != 0);
        menuInicial.executa();
    }

    private static void extratoAluguerViatura(){
        String matricula;
        GregorianCalendar dataInicio;
        GregorianCalendar dataFim;
        UmCarroJaApp.clearScreen();

        out.print("Digite a MatrÃ­cula do VeÃ­culo: ");
        matricula = Input.lerString("MatrÃ­cula InvÃ¡lida!", "Digite Novamente a MatrÃ­cula do VeÃ­culo: ");

        do {
            out.print("Digite a Data de InÃ­cio (dd-mm-aaaa): ");
            dataInicio = Input.lerData("Data de InÃ­cio InvÃ¡lida!", "Digite Novamente a Data de InÃ­cio (dd-mm-aaaa): ");

            out.print("Digite a Data de Fim de Aluguer (dd-mm-aaaa): ");
            dataFim = Input.lerData("Data de Fim invÃ¡lida!", "Digite novamente a data de fim (dd-mm-aaaa): ");
        }while (dataFim.before(dataInicio));

        List<Aluguer> aux;
        List<Aluguer> alugs = new ArrayList<>();
        try{
            aux = ucj.getAlugueresVeiculo(matricula);
            alugs = filterAlugueresBD(aux, dataInicio, dataFim);
        } catch(VeiculoNaoExisteException e) {
            out.println("Veiculo " + e.getMessage() + " nÃ£o existe.");
        }
        if(alugs.isEmpty()) {
            out.print("NÃ£o existem alugures entre essas datas.");
        } else {
            for(Aluguer a : alugs){
                out.println("------------------------------------------------\n");
                out.println(a.toString());
                out.println("------------------------------------------------\n");
            }
        }
    }

    private static void clientesComMaisKm(){
        List <Cliente> lista = new ArrayList<>();

        try{
            lista = ucj.get10ClientesKm();
        }
        catch(NaoExistemClientesException e){
            out.println(e.getMessage());
        }

        for(Cliente c : lista){
            out.println("------------------------------------------------\n");
            out.println(c.toString());
            out.println("------------------------------------------------\n");
        }
    }

    private static void clientesMaisAlugueres(){
        List <Cliente> lista = new ArrayList<>();

        try{
            lista = ucj.get10ClientesAlugueres();
        }
        catch(NaoExistemClientesException e){
            out.println(e.getMessage());
        }

        for(Cliente c : lista){
            out.println("------------------------------------------------\n");
            out.println(c.toString());
            out.println("------------------------------------------------\n");
        }
    }
     
    /******************************************************************************
     *                         FIM DO MENU DA ADMINISTRAÃ‡ÃƒO                       *
     ******************************************************************************/

    
    /******************************************************************************
     *                              MENU PROPRIETÃ?RIOS                            *
     *****************************************************************************/
     
    private static void sessaoProprietario(){
        do{
            classificarClientes();
            clearScreen();
            menuProprietario.executa();
            switch(menuProprietario.getOpcao()){
                case 1: registarVeiculoProp();
                        break;
                case 2: setVeiculoDisponivel();
                        break;     
                case 3: abastecerVeiculoProp();
                        break;
                case 4: altPKMVeiculo();
                        break;
                case 5: analisarAlugueres();
                        break;
                case 6: suplementoPrecoAlug();
                        break;
                case 7: getListaDeAlugueres();
                        break;
                case 8: calcFactBDates();
                        break;
            }
        }while(menuProprietario.getOpcao() != 0);
        UmCarroJaApp.guardarDados();
        ucj.logoutUtilizador();
        menuUtilizador.executa();
    }

    private static void classificarClientes(){
        List<Aluguer> classif = new ArrayList<>();
        try{
            classif = ucj.alugueresClassificarCliente();
        }catch (NaoExistemAlugueresException e){
        }

        boolean correto = false;
        int classificacao = 0;

        for (Aluguer alug : classif){
            out.println("--------------------- Aluguer ---------------------");
            out.println("MatrÃ­cula: " + alug.getMatricula());
            out.println("Email do Cliente: " + alug.getEmail());
            out.println("Data de InÃ­cio do Aluguer: " + alug.getDataFim());
            out.println("Data de Fim do Aluguer: " + alug.getDataFim());
            out.println("---------------------------------------------------");
            do {
                out.print("Digite uma classificaÃ§Ã£o para o cliente com o email: " + alug.getEmail() + " (0-100): ");
                classificacao = Input.lerInt("ClassificaÃ§Ã£o InvÃ¡lida!", "Digite novamente a classificaÃ§Ã£o (0-100): ");
                if (classificacao >= 0 && classificacao <= 100){
                    correto = true;
                }
            }while(!correto);
            ucj.classificarCliente(alug, classificacao);
        }
    }
     
    private static void registarVeiculoProp(){
        String marca; 
        String matricula = "";
        String nif = ucj.getUserNIF();
        int velocidade;
        double preco;
        double consumo;
        int autonomia;
        Coordinate posicao;
        double latitude;
        double longitude;
        boolean disponivel;
        int classificacao = 0;
        List<ParDatas> datasAlugueres = new ArrayList<>();
        
        boolean existe = true;
        int tipoVeiculo;
        
        UmCarroJaApp.clearScreen();
       
       
       while(existe){
            out.print("Digite a matricula do VeÃ­culo: ");
            matricula = Input.lerString("Matricula InvÃ¡lida!", "Digite novamente a matricula do veÃ­culo: ");
            existe = ucj.existeVeiculo(matricula);
            if (existe){
                out.println("JÃ¡ existe um veÃ­culo com a matricula introduzida!");
            }
       }
        
        out.print("Digite a marca do VeÃ­culo: ");
        marca = Input.lerString("Marca InvÃ¡lida!", "Digite novamente a marca do veÃ­culo: ");
        
        out.print("Digite o velocidade mÃ©dia do veÃ­culo: ");
        velocidade = Input.lerInt("Velocidade mÃ©dia do veÃ­culo InvÃ¡lida!", "Digite novamente a velocidade mÃ©dia do veÃ­culo: ");
        
        out.print("Digite o preÃ§o base por Km do veÃ­culo: ");
        preco = Input.lerDouble("PreÃ§o base por Km do veÃ­culo InvÃ¡lida!", "Digite novamente o preÃ§o base por Km do veÃ­culo: ");
        
        out.print("Digite o consumo por Km percorrido do veÃ­culo: ");
        consumo = Input.lerDouble("Consumo por Km percorrido do veÃ­culo InvÃ¡lida!", "Digite novamente o consumo por Km percorrido do veÃ­culo: ");
        
        out.print("Digite a autonomia do veÃ­culo: ");
        autonomia = Input.lerInt("Autonomia do veÃ­culo InvÃ¡lida!", "Digite novamente a autonomia do veÃ­culo: ");
        
        out.print("Digite a disponibilidade do veÃ­culo [true - disponÃ­vel, false - indisponÃ­vel]: ");
        disponivel = Input.lerBoolean("Disponobilidade do veÃ­culo InvÃ¡lida!", "Digite novamente a disponibilidade do veÃ­culo: ");
        
        out.print("Digite o latitude de localizaÃ§Ã£o do veÃ­culo: ");
        latitude = Input.lerDouble("Latitude de localizaÃ§Ã£o fo veÃ­culo InvÃ¡lida!", "Digite novamente a latitude de localizaÃ§Ã£o do veÃ­culo: ");
        
        out.print("Digite o longitude de localizaÃ§Ã£o do veÃ­culo: ");
        longitude = Input.lerDouble("Longitude de localizaÃ§Ã£o fo veÃ­culo InvÃ¡lida!", "Digite novamente a longitude de localizaÃ§Ã£o do veÃ­culo: ");
        
        posicao = new Coordinate(latitude, longitude);
        
        tipoVeiculo = Menu.MenuLerInt(1, 3, "\n******************\n1 - CombustÃ­vel.\n2 - ElÃ©trico.\n3 - Hibrido.\nEscolha o Tipo de VeÃ­culo: ", 
                                 "Tipo de VeÃ­culo InvÃ¡lido!, Tente novamente!");
        Veiculo vr;                     
        if (tipoVeiculo == 1){
            vr = new CarroGasolina(marca,matricula,nif,velocidade,preco,consumo, autonomia, posicao,disponivel,classificacao,datasAlugueres);
        }else{
            if (tipoVeiculo == 2){
                vr =  new CarroEletrico(marca,matricula,nif,velocidade,preco,consumo, autonomia, posicao,disponivel,classificacao,datasAlugueres);
            }else{
                vr = new CarroHibrido(marca,matricula,nif,velocidade,preco,consumo, autonomia, posicao,disponivel,classificacao,datasAlugueres);
            }
        }

        try {
            ucj.registarVeiculo(vr);
            UmCarroJaApp.guardarDados();
        }
        catch (VeiculoJaExisteException e) {
            out.println("O Veiculo " + e.getMessage() +  " jÃ¡ estÃ¡ registado!\n");
        }
    }   
     
    private static void setVeiculoDisponivel(){
        String matricula;
        boolean disp;
        boolean existe;
        
        UmCarroJaApp.clearScreen();
       
        out.print("Digite a matricula do VeÃ­culo: ");
        matricula = Input.lerString("Matricula InvÃ¡lida!", "Digite novamente a matricula do veÃ­culo: ");
        
        
        out.print("Digite a disponibilidade do veÃ­culo: ");
        disp = Input.lerBoolean("Disponobilidade do veÃ­culo InvÃ¡lida!", "Digite novamente a disponibilidade do veÃ­culo: ");
        
        try {
            ucj.sinalizarDisponibilidade(matricula, disp);
            UmCarroJaApp.guardarDados();
        }
        catch (VeiculoNaoExisteException e) {
            out.println("O Veiculo " + e.getMessage() +  " nÃ£o existe!\n");
        }
        catch (VeiculoNaoESeuException e2) {
            out.println("O Veiculo " + e2.getMessage() +  " nÃ£o lhe pertence!\n");
        }
    }
    
    private static void abastecerVeiculoProp(){
        String matricula;
        double abast;
        
        UmCarroJaApp.clearScreen();
        
        out.print("Digite a matricula do VeÃ­culo: ");
         matricula = Input.lerString("Matricula InvÃ¡lida!", "Digite novamente a matricula do veÃ­culo: ");
        
        
        out.print("Digite quanto desejada a abastecer: ");
        abast = Input.lerDouble("Quantidade InvÃ¡lida!", "Digite novamente a quantidade a abastecer: ");
        
        try {
            ucj.abastecerVeiculo(matricula, abast);
            UmCarroJaApp.guardarDados();
        }
        catch (VeiculoNaoExisteException e) {
            out.println("O Veiculo " + e.getMessage() +  " nÃ£o existe!\n");
        }
        catch (VeiculoNaoESeuException e2) {
            out.println("O Veiculo " + e2.getMessage() +  " nÃ£o lhe pertence!\n");
        }
    }
    
    private static void altPKMVeiculo(){
        String matricula;
        double pkm;
        
        UmCarroJaApp.clearScreen();
        
        out.print("Digite a matricula do VeÃ­culo: ");
        matricula = Input.lerString("Matricula InvÃ¡lida!", "Digite novamente a matricula do veÃ­culo: ");
        
        
        out.print("Digite novo preÃ§o por km: ");
        pkm = Input.lerDouble("PreÃ§o por km InvÃ¡lida!", "Digite novamente um novo preÃ§o por km: ");
        
        try {
            ucj.altPrecoKm(matricula, pkm);
            //UmCarroJaApp.guardarDados();
        }
        catch (VeiculoNaoExisteException e) {
            out.println("O Veiculo " + e.getMessage() +  " nÃ£o existe!\n");
        }
        catch (VeiculoNaoESeuException e2) {
            out.println("O Veiculo " + e2.getMessage() +  " nÃ£o lhe pertence!\n");
        }
    }

    private static void getListaDeAlugueres(){
        List<Aluguer> aux = ucj.getAlugueresProp(ucj.getUserNIF());

        GregorianCalendar dataInicio;
        GregorianCalendar dataFim;

        do {
            out.print("Digite a data de inicio (dd-mm-aaaa): ");
            dataInicio = Input.lerData("Data de inicio invÃ¡lida!", "Digite novamente a data de inicio (dd-mm-aaaa): ");

            out.print("Digite a data de fim de aluguer (dd-mm-aaaa): ");
            dataFim = Input.lerData("Data de fim invÃ¡lida!", "Digite novamente a data de fim (dd-mm-aaaa): ");
        }while (dataFim.before(dataInicio));

        List<Aluguer> alugs;

        alugs = filterAlugueresBD(aux, dataInicio, dataFim);

        UmCarroJaApp.clearScreen();

        if(alugs.isEmpty()) {
            out.print("NÃ£o existe alugures entre essas datas.");
        } else {
            for(Aluguer a : alugs){
                out.println("------------------------------------------------\n");
                out.println(a.toString());
                out.println("------------------------------------------------\n");
            }
        }
    }
    
    private static void analisarAlugueres(){
        List<Aluguer> alugs = ucj.determinarListaEspera(ucj.getUserNIF());
        boolean rep;
        UmCarroJaApp.clearScreen();
        
        for(Aluguer a : alugs) {
            a.toString();
            out.print("Digite se pretende aceitar aluguer (Sim - true, NÃ£o - false): ");
            rep = Input.lerBoolean("Resposta InvÃ¡lida!", "Digite novamente uma nova resposta: ");
            
            ucj.respostaProp(rep,a);
        }
    }
    
    public static void suplementoPrecoAlug(){
        String matricula;
        String nifCliente;
        double newPrice;
        List<Aluguer> alugs = new ArrayList();
        UmCarroJaApp.clearScreen();
        
        out.print("Digite a matricula do VeÃ­culo: ");
        matricula = Input.lerString("Matricula InvÃ¡lida!", "Digite novamente a matricula do veÃ­culo: ");
        
        
        out.print("Digite o Email do cliente: ");
        nifCliente = Input.lerString("Email InvÃ¡lido!", "Digite novamente o Email do cliente: ");
        
        
        try {
            alugs = ucj.determinarListaAlugCli(matricula, nifCliente);
        }
        catch (VeiculoNaoESeuException e) {
            out.println("O Veiculo " + e.getMessage() +  " nÃ£o lhe pertence!\n");
        }
        catch (UtilizadorNaoExisteException e2) {
            out.println("O cliente " + e2.getMessage() +  " nÃ£o existe!\n");
        }
        
        boolean rep, flag;
        
        if(alugs.isEmpty()){
            out.println("NÃ£o existem alugueres por por parte do cliente " + nifCliente + " no veÃ­culo com matrÃ­cula " + matricula + ".\n");
        } else {
            for(Aluguer a : alugs) {
                a.toString();
                out.print("Digite se pretende alterar preÃ§o de aluguer (Sim - true, NÃ£o - false): ");
                rep = Input.lerBoolean("Resposta InvÃ¡lida!", "Digite novamente uma nova resposta: ");
                if(rep) {
                    do{
                        out.print("Digite novo preÃ§o para aluguer: ");
                        newPrice = Input.lerDouble("PreÃ§o InvÃ¡lido!", "Digite novamente um novo preÃ§o: ");
                        if (flag = newPrice < a.getCustoViagem()){
                            out.println("Novo preÃ§o menor do que o anterior digite novo preÃ§o novamente!");
                        }
                    } while(flag);
                    ucj.altPrecoAluguer(newPrice, a);
                }
            }
        }
        UmCarroJaApp.guardarDados();
     }
    
    public static void calcFactBDates(){
        String matricula;
        GregorianCalendar inicio;
        GregorianCalendar fim;
        boolean existe;
        double total = 0;
        UmCarroJaApp.clearScreen();
        
        out.print("Digite a matricula do VeÃ­culo: ");
        matricula = Input.lerString("Matricula InvÃ¡lida!", "Digite novamente a matricula do veÃ­culo: ");
         
        do {
            out.print("Digite a Data limite inferior (dd-mm-aaaa): ");
            inicio = Input.lerData("Data limite inferior InvÃ¡lida!", "Digite novamente a Data limite inferior (dd-mm-aaaa): ");

            out.print("Digite a Data limite superior (dd-mm-aaaa): ");
            fim = Input.lerData("Data limite superior InvÃ¡lida!", "Digite novamente a Data limite superior (dd-mm-aaaa): ");
        }while (fim.before(inicio));

        try {
            total = ucj.totalFactBDates(matricula, inicio, fim);
        }
        catch (VeiculoNaoESeuException e) {
            out.println("O VeÃ­culo " + e.getMessage() +  " nÃ£o estÃ¡ registado no seu historial de alugueres!\n");
        }
       
        StringBuilder str = new StringBuilder();
        str.append("O total facturado pelo veÃ­culo de matrÃ­cula "); str.append(matricula);
        str.append(" faturou no total: "); str.append(total);
        str.append(" euros, entre "); str.append(inicio);
        str.append(" e "); str.append(fim);
        str.append(".\n");
        out.println(str);
    }

    private static List<Aluguer> filterAlugueresBD (List<Aluguer> alugs, GregorianCalendar dataInicio, GregorianCalendar dataFim) {
        List<Aluguer> res = alugs.stream().filter(a -> a.getDataInicio().after(dataInicio) && a.getDataFim().before(dataFim) && a.getRealizado()).collect(Collectors.toList());

        TreeSet<Aluguer> resOrd = new TreeSet<>(new Comparator<Aluguer>() {
            public int compare (Aluguer a1, Aluguer a2) {
                int i = a1.getDataInicio().compareTo(a2.getDataInicio());
                if(i == 0) {return -1; }
                return i;
            }
        });


        for(Aluguer a : res) {
            resOrd.add(a.clone());
        }

        return resOrd.stream().collect(Collectors.toList());
    }

    private static void meusAlugueresEntreDatasProp(){
        List<Aluguer> alugs = ucj.getAlugueresProp(ucj.getUserNIF());

        GregorianCalendar dataInicio;
        GregorianCalendar dataFim;

        do {
            out.print("Digite a Data de InÃ­cio do Aluguer (dd-mm-aaaa): ");
            dataInicio = Input.lerData("Data de InÃ­cio InvÃ¡lida!", "Digite Novamente a Data de InÃ­cio (dd-mm-aaaa): ");

            out.print("Digite a Data de Fim do Aluguer (dd-mm-aaaa): ");
            dataFim = Input.lerData("Data de fim invÃ¡lida!", "Digite Novamente a Data de Fim (dd-mm-aaaa): ");
        }while(dataFim.before(dataInicio));

        List<Aluguer> res = filterAlugueresBD(alugs, dataInicio, dataFim);

        UmCarroJaApp.clearScreen();
        if(alugs.isEmpty()) {
            out.print("NÃ£o existem alugures entre essas datas.");
        } else {
            for(Aluguer a : res){
                out.println("------------------------------------------------\n");
                out.println(a.toString());
                out.println("------------------------------------------------\n");
            }
        }
    }
    
    /******************************************************************************
     *                         FIM DO MENU DOS PROPRIETÃ?RIOS                      *
     ******************************************************************************/
     
    /******************************************************************************
     *                               MENU DOS CLIENTES                            *
     ******************************************************************************/
    
    private static void sessaoCliente(){
        do{
            classificarVeiculos();
            UmCarroJaApp.clearScreen();
            menuCliente.executa();
            switch(menuCliente.getOpcao()){
                case 1: aluguerMaisProximo();
                        break;
                case 2: aluguerMaisBarato();
                        break;     
                case 3: aluguerMaisBaratoPerimetro();
                        break;
                case 4: aluguerVeiculoEspecifico();
                        break;
                case 5: aluguerDeterminadaAutonomia();
                        break;
                case 6: meusAlugueresEntreDatasCli();
                        break;
            }
        }while(menuCliente.getOpcao() != 0);
        ucj.logoutUtilizador();
        menuUtilizador.executa();
    }

    private static boolean verificaData (GregorianCalendar inicio, GregorianCalendar fim){
        if ((inicio.equals(dataInicioApp) || inicio.after(dataInicioApp)) && (fim.equals(inicio) || fim.after(inicio))){
            return true;
        }
        return false;
    }

    private static void classificarVeiculos(){
        List<Aluguer> classif = ucj.alugueresClassificarVeiculo();

        boolean correto = false;
        int classificacao = 0;

        for (Aluguer alug : classif){
            out.println("--------------------- Aluguer ---------------------");
            out.println("MatrÃ­cula: " + alug.getMatricula());
            out.println("Data de InÃ­cio do Aluguer: " + alug.getDataFim());
            out.println("Data de Fim do Aluguer: " + alug.getDataFim());
            out.println("---------------------------------------------------");
            do {
                out.print("Digite uma classificaÃ§Ã£o para o veÃ­culo com a matrÃ­cula: " + alug.getMatricula() + " (0-100): ");
                classificacao = Input.lerInt("ClassificaÃ§Ã£o InvÃ¡lida!", "Digite novamente a classificaÃ§Ã£o (0-100): ");
                if (classificacao >= 0 && classificacao <= 100){
                    correto = true;
                }
            }while(!correto);
            ucj.classificarVeiculo(alug, classificacao);
        }
    }

    private static void aluguerMaisProximo(){

        GregorianCalendar dataInicio;
        GregorianCalendar dataFim;
        int quantos;
        String matricula;
        double latitude, longitude;
        boolean correto = false;

        UmCarroJaApp.clearScreen();

        do{
            out.print("Digite a Data de InÃ­cio do Aluguer (dd-mm-aaaa): ");
            dataInicio = Input.lerData("Data de InÃ­cio de Aluguer InvÃ¡lida!", "Digite Novamente a Data de InÃ­cio de Aluguer (dd-mm-aaaa): ");

            out.print("Digite a Data de Fim de Aluguer (dd-mm-aaaa): ");
            dataFim = Input.lerData("Data de Fim de Aluguer InvÃ¡lida!", "Digite novamente a Data de Fim de Aluguer (dd-mm-aaaa): ");
        }while (!verificaData(dataInicio, dataFim));

        out.print("Digite a Latitude do Destino: ");
        latitude = Input.lerDouble("Latitude InvÃ¡lida!", "Digite Novamente a Latitude: ");

        out.print("Digite  a Longitude do Destino: ");
        longitude = Input.lerDouble("Longitude InvÃ¡lida!", "Digite Novamente a Longitude: ");

        out.print("Digite o NÃºmero de VeÃ­culos Mais PrÃ³ximos que Deseja Listar: ");
        quantos = Input.lerInt("Valor InvÃ¡lido", "Digite Novamente o NÃºmero de VeÃ­culos que Deseja Listar: ");

        ParDatas newPair = new ParDatas(dataInicio, dataFim);
        Coordinate posDestino = new Coordinate(latitude,longitude);

        List<Veiculo> veiculosMaisProximo = new ArrayList<>();

        try{
            veiculosMaisProximo = ucj.maisProximo(posDestino, newPair, quantos);
        }
        catch(NaoExistemVeiculosDisponiveisException e){
            out.println("NÃ£o Existem VeÃ­culos DisponÃ­veis para Alugar, Com os CritÃ©rios Inseridos!");
        }

        for(Veiculo v: veiculosMaisProximo){
            out.print("------------------------------------------------\n");
            out.print(v.toString());
            out.print("------------------------------------------------\n");
        }

        do{
            out.print("Introduza a MatrÃ­cula do VeÃ­culo que Pretende Alugar: ");
            matricula = Input.lerString("Matricula InvÃ¡lida!", "Digite Novamente a MatrÃ­cula do VeÃ­culo: ");
            final String matr = matricula;
            if (veiculosMaisProximo.stream().filter(v -> v.getMatricula().equals(matr)).count() > 0){
                correto = true;
            }
        }while(!correto);

        Aluguer alug = new Aluguer(ucj.getEmailUser(), matricula, dataInicio, dataFim, 0.0, 0, 0, posDestino, 0, false, true, false, false, 0);

        ucj.registaAluguer(alug);
    }

    private static void aluguerMaisBarato(){
        GregorianCalendar dataInicio;
        GregorianCalendar dataFim;
        int quantos;
        String matricula;
        double latitude, longitude;
        boolean correto = false;

        do {
            out.print("Digite a Data de InÃ­cio de Aluguer (dd-mm-aaaa): ");
            dataInicio = Input.lerData("Data de InÃ­cio de Aluguer InvÃ¡lida!", "Digite Novamente a Data de InÃ­cio de Aluguer (dd-mm-aaaa): ");

            out.print("Digite a data de fim de aluguer (dd-mm-aaaa): ");
            dataFim = Input.lerData("Data de InÃ­cio de Aluguer InvÃ¡lida!", "Digite Novamente a Data de Fim de Aluguer (dd-mm-aaaa): ");
        }while (!verificaData(dataInicio, dataFim));

        out.print("Digite a latitude de destino: ");
        latitude = Input.lerDouble("Latitude InvÃ¡lida!", "Digite novamente a latitude: ");

        out.print("Digite  a longitude de destino: ");
        longitude = Input.lerDouble("Longitude InvÃ¡lida!", "Digite novamente a longitude: ");

        out.print("Quanto veiculos deseja ver: ");
        quantos = Input.lerInt("Valor invÃ¡lido", "Digite novamente o numero de veiculos que deseja ver: ");

        ParDatas newPair = new ParDatas(dataInicio,dataFim);
        Coordinate posDestino = new Coordinate(latitude,longitude);

        List<Veiculo> veiculosMaisBaratos = new ArrayList<>();

        try{
            veiculosMaisBaratos = ucj.maisBarato(posDestino, newPair, quantos);
        }
        catch(NaoExistemVeiculosDisponiveisException e){
            out.println(e.getMessage());
        }

        for(Veiculo v: veiculosMaisBaratos){
            out.println("------------------------------------------------\n");
            out.println(v.toString());
            out.println("------------------------------------------------\n");
        }

        do{
            out.print("Introduza a MatrÃ­cula do VeÃ­culo que Pretende Alugar: ");
            matricula = Input.lerString("Matricula InvÃ¡lida!", "Digite Novamente a MatrÃ­cula do VeÃ­culo: ");
            final String matr = matricula;
            if (veiculosMaisBaratos.stream().filter(v -> v.getMatricula().equals(matr)).count() > 0){
                correto = true;
            }
        }while(!correto);

        Aluguer alug = new Aluguer(ucj.getEmailUser(), matricula, dataInicio, dataFim, 0.0, 0, 0, posDestino, 0, false, true, false, false, 0);

        ucj.registaAluguer(alug);
    }


    private static void aluguerMaisBaratoPerimetro(){
        GregorianCalendar dataInicio;
        GregorianCalendar dataFim;
        int quantos;
        String matricula;
        double latitude, longitude;
        double perimetro;
        boolean correto = false;

        do {
            out.print("Digite a Data de InÃ­cio de Aluguer (dd-mm-aaaa): ");
            dataInicio = Input.lerData("Data de InÃ­cio de Aluguer InvÃ¡lida!", "Digite Novamente a Data de InÃ­cio de Aluguer (dd-mm-aaaa): ");

            out.print("Digite a data de fim de aluguer (dd-mm-aaaa): ");
            dataFim = Input.lerData("Data de InÃ­cio de Aluguer InvÃ¡lida!", "Digite Novamente a Data de Fim de Aluguer (dd-mm-aaaa): ");
        }while (!verificaData(dataInicio, dataFim));

        out.print("Digite a latitude de destino: ");
        latitude = Input.lerDouble("Latitude InvÃ¡lida!", "Digite novamente a latitude: ");

        out.print("Digite  a longitude de destino: ");
        longitude = Input.lerDouble("Longitude InvÃ¡lida!", "Digite novamente a longitude: ");

        out.print("Indique o perimetro de procura: ");
        perimetro = Input.lerDouble("Perimetro InvÃ¡lido!", "Digite novamente o perimetro: ");

        out.print("Quanto veiculos deseja ver: ");
        quantos = Input.lerInt("Valor invÃ¡lido", "Digite novamente o numero de veiculos que deseja ver: ");

        ParDatas newPair = new ParDatas(dataInicio,dataFim);
        Coordinate posDestino = new Coordinate(latitude,longitude);

        List<Veiculo> veiculosMaisBaratosPeri = new ArrayList<>();

        try{
            veiculosMaisBaratosPeri = ucj.maisBaratoNoPerimetro(posDestino, ucj.getPosicaoCliente(), newPair, perimetro, quantos);
        }
        catch(NaoExistemVeiculosDisponiveisException e){
            out.println(e.getMessage());
        }

        for(Veiculo v: veiculosMaisBaratosPeri){
            out.println("------------------------------------------------\n");
            out.println(v.toString());
            out.println("------------------------------------------------\n");
        }

        do{
            out.print("Introduza a MatrÃ­cula do VeÃ­culo que Pretende Alugar: ");
            matricula = Input.lerString("Matricula InvÃ¡lida!", "Digite Novamente a MatrÃ­cula do VeÃ­culo: ");
            final String matr = matricula;
            if (veiculosMaisBaratosPeri.stream().filter(v -> v.getMatricula().equals(matr)).count() > 0){
                correto = true;
            }
        }while(!correto);

        Aluguer alug = new Aluguer(ucj.getEmailUser(), matricula, dataInicio, dataFim, 0.0, 0, 0, posDestino, 0, false, true, false, false, 0);

        ucj.registaAluguer(alug);
    }

    private static void aluguerVeiculoEspecifico(){
        GregorianCalendar dataInicio;
        GregorianCalendar dataFim;
        String matricula;
        double latitude, longitude;

        do {
            out.print("Digite a Data de InÃ­cio de Aluguer (dd-mm-aaaa): ");
            dataInicio = Input.lerData("Data de InÃ­cio de Aluguer InvÃ¡lida!", "Digite Novamente a Data de InÃ­cio de Aluguer (dd-mm-aaaa): ");

            out.print("Digite a data de fim de aluguer (dd-mm-aaaa): ");
            dataFim = Input.lerData("Data de InÃ­cio de Aluguer InvÃ¡lida!", "Digite Novamente a Data de Fim de Aluguer (dd-mm-aaaa): ");
        }while (!verificaData(dataInicio, dataFim));

        out.print("Introduza a MatrÃ­cula do veÃ­culo pretendido");
        matricula = Input.lerString("Matricula InvÃ¡lida!", "Digite novamente a matricula do veÃ­culo: ");

        out.print("Digite a latitude de destino: ");
        latitude = Input.lerDouble("Latitude InvÃ¡lida!", "Digite novamente a latitude: ");

        out.print("Digite  a longitude de destino: ");
        longitude = Input.lerDouble("Longitude InvÃ¡lida!", "Digite novamente a longitude: ");

        ParDatas newPair = new ParDatas(dataInicio, dataFim);
        Coordinate posDestino = new Coordinate(latitude, longitude);

        Veiculo v = new Veiculo();
        try{
            v = ucj.veiculoEspecifico(posDestino, newPair, matricula);
        }
        catch(VeiculoNaoExisteException e){
            out.println("O VeÃ­culo " + e.getMessage() +  " com a matrÃ­cula introduzida nÃ£o existe. \n");
        }
        catch(VeiculoIndisponivelException e){
            out.println("O VeÃ­culo " + e.getMessage() +  " nÃ£o tem disponibilidade para ser alugado. \n");
        }

        Aluguer alug = new Aluguer(ucj.getEmailUser(), matricula, dataInicio, dataFim, 0.0, 0, 0, posDestino, 0, false, true, false, false, 0);

        ucj.registaAluguer(alug);
    }

    private static void aluguerDeterminadaAutonomia(){

        GregorianCalendar dataInicio;
        GregorianCalendar dataFim;
        int quantos;
        String matricula;
        double latitude, longitude;
        int auto1, auto2;
        boolean correto = false;

        do {
            out.print("Digite a Data de InÃ­cio de Aluguer (dd-mm-aaaa): ");
            dataInicio = Input.lerData("Data de InÃ­cio de Aluguer InvÃ¡lida!", "Digite Novamente a Data de InÃ­cio de Aluguer (dd-mm-aaaa): ");

            out.print("Digite a data de fim de aluguer (dd-mm-aaaa): ");
            dataFim = Input.lerData("Data de InÃ­cio de Aluguer InvÃ¡lida!", "Digite Novamente a Data de Fim de Aluguer (dd-mm-aaaa): ");
        }while(!verificaData(dataInicio, dataFim));

        out.print("Digite a latitude de destino: ");
        latitude = Input.lerDouble("Latitude InvÃ¡lida!", "Digite novamente a latitude: ");

        out.print("Digite  a longitude de destino: ");
        longitude = Input.lerDouble("Longitude InvÃ¡lida!", "Digite novamente a longitude: ");

        do {
            out.print("Indique o limite inferior de autonomia: ");
            auto1 = Input.lerInt("Limite InvÃ¡lido!", "Digite novamente o limite inferior: ");

            out.print("Indique o limite superior de autonomia: ");
            auto2 = Input.lerInt("Limite InvÃ¡lido!", "Digite novamente o limite superior: ");
            if (auto1 < auto2 && auto1 >= 0 && auto2 > 0){
                correto = true;
            }
        }while (!correto);

        correto = false;

        out.print("Indique quantos veiculos deseja ver: ");
        quantos = Input.lerInt("Valor invÃ¡lido", "Digite novamente o numero de veiculos que deseja ver: ");

        ParDatas newPair = new ParDatas(dataInicio,dataFim);
        Coordinate posDestino = new Coordinate(latitude,longitude);

        List<Veiculo> veiculosDetAutonomia = new ArrayList<>();

        try{
            veiculosDetAutonomia = ucj.determinadaAutonomia(posDestino, newPair, auto1, auto2, quantos);
        }
        catch(NaoExistemVeiculosDisponiveisException e){
            out.println(e.getMessage());
        }

        for(Veiculo v: veiculosDetAutonomia){
            out.println("------------------------------------------------\n");
            out.println(v.toString());
            out.println("------------------------------------------------\n");
        }

        do{
            out.print("Introduza a MatrÃ­cula do VeÃ­culo que Pretende Alugar: ");
            matricula = Input.lerString("Matricula InvÃ¡lida!", "Digite Novamente a MatrÃ­cula do VeÃ­culo: ");
            final String matr = matricula;
            if (veiculosDetAutonomia.stream().filter(v -> v.getMatricula().equals(matr)).count() > 0){
                correto = true;
            }
        }while(!correto);

        Aluguer alug = new Aluguer(ucj.getEmailUser(), matricula, dataInicio, dataFim, 0.0, 0, 0, posDestino, 0, false, true, false, false, 0);

        ucj.registaAluguer(alug);
    }

    private static void meusAlugueresEntreDatasCli() {
        List<Aluguer> alugs = new ArrayList<>();

        try {
            alugs = ucj.getAlugueresCliente(ucj.getEmailUser());
        } catch (NaoEfetuouNenhumAluguerException e) {
            out.println(e.getMessage());
        }

        GregorianCalendar dataInicio;
        GregorianCalendar dataFim;

        do {
            out.print("Digite a Data de InÃ­cio do Aluguer (dd-mm-aaaa): ");
            dataInicio = Input.lerData("Data de InÃ­cio InvÃ¡lida!", "Digite Novamente a Data de InÃ­cio (dd-mm-aaaa): ");

            out.print("Digite a Data de Fim do Aluguer (dd-mm-aaaa): ");
            dataFim = Input.lerData("Data de fim invÃ¡lida!", "Digite Novamente a Data de Fim (dd-mm-aaaa): ");
        } while (dataFim.before(dataInicio));

        List<Aluguer> res = filterAlugueresBD(alugs, dataInicio, dataFim);

        UmCarroJaApp.clearScreen();
        if (res.isEmpty()) {
            out.print("NÃ£o existem alugures entre essas datas.");
        } else {
            for (Aluguer a : res) {
                out.println("------------------------------------------------\n");
                out.println(a.toString());
                out.println("------------------------------------------------\n");
            }
        }
    }

                                 
    /******************************************************************************
     *                            FIM DO MENU DOS CLIENTES                        *
     *****************************************************************************
    /**
     * Guarda o estado da aplicaÃ§Ã£o no ficheiro de objetos e guarda a data do dia
     * de hoje.
     */
    private static void guardarDados(){
        try {
            ucj.guardarEstado(ficheiroDados, dataInicioApp);
        } catch (IOException e) {
            out.println("Erro ao gravar os dados no ficheiro " + ficheiroDados + "!");
        }
    }


    /********** PARSING *************/
    
    private static void lerDadosTXT(String fichtxt){
        BufferedReader inFile = null;
        String linha = null;
        String [] linhas = null;
       
        UmCarroJaApp.clearScreen();
        try{
            inFile = new BufferedReader(new FileReader(fichtxt));
            while(!inFile.readLine().equals("Logs")){
            }
            while((linha = inFile.readLine()) != null){
                linhas = linha.split(":", 2);
                if (linhas[0].equals("NovoProp")){
                    try{
                        Proprietario prop = ParseDados.parseProprietario(linhas[1]);
                        ucj.registarUtilizador(prop);
                    }catch(UtilizadorJaExisteException e){
                        out.println("O proprietÃ¡rio com o email: " + e.getMessage() + " jÃ¡ se encontra registado!\n");
                    }
                }
                if (linhas[0].equals("NovoCliente")){
                    try{
                        Cliente cli = ParseDados.parseCliente(linhas[1]);
                        ucj.registarUtilizador(cli);
                    }catch(UtilizadorJaExisteException e){
                        out.println("O cliente com o email: " + e.getMessage() + " jÃ¡ se encontra registado!\n");
                    }
                }
                if (linhas[0].equals("NovoCarro")){
                    try {
                        Veiculo v = ParseDados.parseVeiculo(linhas[1]);
                        ucj.registarVeiculo(v);
                    } catch (VeiculoJaExisteException e) {
                        out.println("O veÃ­culo com a matrÃ­cula: " + e.getMessage() + " jÃ¡ foi inserido!");
                    }
                }
                if (linhas[0].equals("Aluguer")){
                    parseAluguer(linhas[1]);
                }
                if (linhas[0].equals("Classificar")){
                    parseClassificar(linhas[1]);
                }
            }
            UmCarroJaApp.guardarDados();
        }
        catch (IOException exc){
            out.println("Erro ao ler ficheiro de texto!");
        }
    }


    
   private static void parseAluguer(String linha){
        String mail;
        double x,y;
        GregorianCalendar dataInicio = new GregorianCalendar(2019,4,20);
        GregorianCalendar dataFim = new GregorianCalendar(2019,4,21);
        ParDatas datas = new ParDatas(dataInicio, dataFim);
        String [] dados = linha.split(",");
        
        mail = dados[0] + "@gmail.com";

        try{
            x = Double.parseDouble(dados[1]);
            y = Double.parseDouble(dados[2]);
        }
        catch(InputMismatchException exc){return;}
        
        Coordinate cords = new Coordinate(x, y);

       Cliente cli = new Cliente();
       try {
           cli = (Cliente) ucj.getUtilizador(mail);
       }catch (UtilizadorNaoExisteException e) {
        out.println("Cliente nÃ£o encontrado!\n");
        }

       Veiculo v = new Veiculo();

        if (dados[4].equals("MaisBarato")){
            if (dados[3].equals("Electrico")){
                try {
                    v = ucj.maisBaratoJa(cords, datas, "CarroEletrico");
                }catch(NaoExistemVeiculosDisponiveisException e){
                    out.println("NÃ£o existem veÃ­culos disponÃ­veis para alugar!\n");
                }
            }
            if (dados[3].equals("Hibrido")){
                try{
                    v = ucj.maisBaratoJa(cords, datas, "CarroHibrido");
                }catch(NaoExistemVeiculosDisponiveisException e){
                    out.println("NÃ£o existem veÃ­culos disponÃ­veis para alugar!\n");
                }
            }
            if (dados[3].equals("Gasolina")){
                try {
                    v = ucj.maisBaratoJa(cords, datas, "CarroGasolina");
                }catch(NaoExistemVeiculosDisponiveisException e){
                    out.println("NÃ£o existem veÃ­culos disponÃ­veis para alugar!\n");
                }
            }
        }else{
            if (dados[3].equals("Electrico")){
                try{
                    v = ucj.maisPertoJa(cli.getPosicao().clone(), cords.clone(), datas, "CarroEletrico");
                }catch(NaoExistemVeiculosDisponiveisException e){
                    out.println("NÃ£o existem veÃ­culos disponÃ­veis para alugar!\n");
                }
            }
            if (dados[3].equals("Hibrido")){
                try{
                    v = ucj.maisPertoJa(cli.getPosicao().clone(), cords.clone(), datas, "CarroHibrido");
                }catch(NaoExistemVeiculosDisponiveisException e){
                    out.println("NÃ£o existem veÃ­culos disponÃ­veis para alugar!\n");
                }
            }
            if (dados[3].equals("Gasolina")){
                try{
                    v = ucj.maisPertoJa(cli.getPosicao().clone(), cords.clone(), datas, "CarroGasolina");
                }catch(NaoExistemVeiculosDisponiveisException e){
                    out.println("NÃ£o existem veÃ­culos disponÃ­veis para alugar!\n");
                }
            }
        }
       double dist = v.getPosicao().getDistancia(cords);
       Coordinate posCli = cli.getPosicao().clone();
       Aluguer alug = new Aluguer(mail, v.getMatricula(), dataInicio, dataFim, v.custoViagem(dist), v.tempoAteVeiculoPÃ©Ja(posCli), v.tempoViagemCarroJa(cords), cords, dist, true, false, true, false, 3);
       ucj.registaAluguer(alug);
       ucj.alterarPosAutonomiaVeiculo(v.getMatricula(), cords.clone());
       ucj.alterarPosCliente(mail, cords.clone());
    }
    
    public static void parseClassificar(String linha){
        String [] dados = linha.split(",");
        int classificacao = 0;
        
        try {
            classificacao = Integer.parseInt(dados[1]);
        }
        catch(InputMismatchException exc){return;}
        
        if (dados[0].contains("-")){
            ucj.classificarVeiculoJa(dados[0], classificacao);
        }else{
            ucj.classificarClienteJa(dados[0] + "@gmail.com", classificacao);
        }
    }

    private static void printAlugs(){
        out.print(ucj.toStringAlugs());
    }
    
    private static void printUsers(){
        out.print(ucj.toStringUser());
    }
    
    private static void printVeiculos(){
        out.print(ucj.toStringVeiculo());
    }
}
