/**
 * Esta classe contém todas as estruturas de dados da aplicação.
 * Foi usado HashMap para guardar os actores da aplicação (clientes e proprietários)
 * onde a chave de acesso a esta é o seu email.
 * 
 * 
 * 
 * 
 * @version 20190415
 */

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class UmCarroJa implements Serializable {
    
    /** Variáveis de Instância */
    
    /* Map cuja chave de acesso é o email do user e o valor é o próprio */
    private Map<String, Utilizador> utilizadores;
    
    /* Map cuja chave de acesso é a matrícula do veículo e o valor é o próprio veículo */
    private Map<String, Veiculo> veiculos;
    
    /* Map cuja chave de acesso é o nif do proprietário e o valor é outro map contendo como chave de acesso
       a matrícula / id da viatura, e como valor armazenado uma lista com os alugueres */
    private Map<String, Map<String, List<Aluguer>>> alugueres;
    
    /* Utilizador que efetuou o login */
    private Utilizador user;

    
    /**********************************************************************************
     *                                  CONSTRUTORES                                  *
     **********************************************************************************/

    /**
     * Construtor por omissão.
     * É criada uma instância da classe UmCarroJa.
     */
    public UmCarroJa(){
        this.utilizadores = new HashMap<>();
        this.veiculos = new HashMap<>();
        this.alugueres = new HashMap<>();
        this.user = new Utilizador();
    }
    
    /**********************************************************************************
     *                              END CONSTRUTORES                                  *
     **********************************************************************************/


    /**********************************************************************************
     *                              MÉTODOS DO UTILIZADOR                             *
     **********************************************************************************/
    
    /**
     * Método que devolve o utilizador referente ao email recebido como parâmetro.
     * @param email Email do utilizador que se pretende obter.
     * @return Utilizador Devolve o utilizador correspondente ao email recebido como parâmetro, caso este exista.
     */
    public Utilizador getUtilizador(String email) throws UtilizadorNaoExisteException {
        if (!this.utilizadores.containsKey(email)){
            throw new UtilizadorNaoExisteException(email);
        }
        return utilizadores.get(email).clone();
    }

    /**
     * Método que devolve o NIF do utilizador que está logado.
     * @return String Devolve NIF do utilizador que tem sessão iniciada.
     */
    public String getUserNIF(){
        return this.user.getNIF();
    }

    /**
     * Método que devolve o Email do utilizador que está logado.
     * @return String Devolve Email do utilizador que tem sessão iniciada.
     */
    public String getEmailUser(){
        return  this.user.getEmail();
    }

    /**
     * Método que devolve a posição do utilizador que está logado (Cliente).
     * @return Coordinate Devolve a posição do utilizador que tem sessão iniciada.
     */
    public Coordinate getPosicaoCliente(){
        Cliente c = (Cliente) this.user;
        return c.getPosicao();
    }

    /**
     * Método responsável por registar um utilizador no Map de utilizadores. Primeiro é feita uma
     * verificação se o mesmo já existe no Map de utilizadores, caso exista é enviada
     * uma exception. Caso contrário o mesmo é inserido no Map de utilizadores.
     *  
     * @param user Utilizador que se pretende registar.
     */
    public void registarUtilizador(Utilizador user) throws UtilizadorJaExisteException{
        if (this.utilizadores.containsKey(user.getEmail())) {
            throw new UtilizadorJaExisteException(user.getEmail());
        }
        this.utilizadores.put(user.getEmail(), user.clone());
    }
    
    /**
     * Método responsável por iniciar sessão do utilizador. É feita primeiro uma verificação para
     * comprovar que este existe no Map de Utilizadores, através do email recebido como parâmetro,
     * caso este existe é então comparada a sua password com a recebida como parâmetro.
     * 
     * @param email    Email do utilizador que pretende iniciar sessão.
     * @param password Password do utilizador que pretende iniciar sessão.
     */
    public void iniciarSessao(String email, String password) throws UtilizadorNaoExisteException, PasswordIncorretaException  {
        if (!this.utilizadores.containsKey(email)){
            throw new UtilizadorNaoExisteException(email);
        }
        if (!this.utilizadores.get(email).getPassword().equals(password)){
            throw new PasswordIncorretaException(password);
        }
        this.user = utilizadores.get(email).clone();
    }
    
    /**
     * Método responsável por fazer logout do utilizador, colocando o user a null.
     */
    public void logoutUtilizador() {
        this.user = null;
    }
    
    /**
     * @param email Email do utilizador a verificar se existe no Map.
     * @return Devolve true se existir um utilizador com o email recebido como parâmetro no Map de utilizadores 
     *         ou false caso contrário.
     */
    public boolean existeUtilizador(String email) {
        return this.utilizadores.containsKey(email);
    }

    /**********************************************************************************
     *                          FIM DOS MÉTODOS DO UTILIZADOR                         *
     **********************************************************************************/


    /**********************************************************************************
     *                            MÉTODOS DA ADMINISTRAÇÃO                            *
     **********************************************************************************/

    /**
     * Método responsável por determinar a lista de alugueres de uma viatura.
     * @throws VeiculoNaoESeuException Exceção caso não seja o proprietário do veículo.
     * @param matricula  Matricula.
     * @return List<Aluguer> Lista de alugueres de um veículos.
     */
    public List<Aluguer> getAlugueresVeiculo(String matricula)  throws VeiculoNaoExisteException{
        if(!this.veiculos.containsKey(matricula)) {
            throw new VeiculoNaoExisteException(matricula);
        }
        Veiculo v = this.veiculos.get(matricula);
        String prop = v.getNIF();
        List<Aluguer> aux = this.alugueres.get(prop).get(matricula);

        if(aux != null){
            return aux.stream().map(Aluguer:: clone).collect(Collectors.toList());
        }
        return aux;
    }

    /**
     * Método que devolve uma lista dos 10 clientes que têm mais KM percorridos da aplicação.
     * @throws NaoExistemClientesException Exceção caso não existam clientes.
     * @return List<Cliente> Lista dos 10 clientes com mais Km percorridos.
     */
    public List<Cliente> get10ClientesKm() throws NaoExistemClientesException{
        List<Cliente> cli = new ArrayList<>();
        for(Utilizador u: this.utilizadores.values()){
            if(u instanceof Cliente){
                Cliente c = (Cliente) u;
                cli.add(c.clone());
            }
        }
        if(cli.isEmpty()){
            throw new NaoExistemClientesException("Não existem clientes a apresentar.");
        }
        cli.sort(new ComparadorKm());
        return cli.stream().limit(10).collect(Collectors.toList());
    }

    /**
     * Método que devolve uma lista dos 10 clientes que têm mais alugueres efetuados na aplicação.
     * @throws NaoExistemClientesException Exceção caso não existam clientes.
     * @return List<Cliente> Lista dos 10 clientes com mais alugueres efetuados.
     */
    public List<Cliente> get10ClientesAlugueres() throws NaoExistemClientesException{
        List<Cliente> cli = new ArrayList<>();
        for(Utilizador u: this.utilizadores.values()){
            if(u instanceof Cliente){
                Cliente c = (Cliente) u;
                cli.add(c.clone());
            }
        }
        if(cli.isEmpty()){
            throw new NaoExistemClientesException("Não existem clientes a apresentar.");
        }
        cli.sort(new ComparadorNAluguer());
        return cli.stream().limit(10).collect(Collectors.toList());
    }

    /**********************************************************************************
     *                        FIM DOS MÉTODOS DA ADMINISTRAÇÃO                        *
     **********************************************************************************/


    /**********************************************************************************
     *                            MÉTODOS PROPRIET�?RIOS                               *
     **********************************************************************************/

    /**
     * Método que verifica se existe um determinado veículo existe na aplicação.
     * @param matricula Matrícula do veículo a verificar se existe no Map.
     * @return Devolve true se existir um veículo com a matrícula recebida como parâmetro no Map de veículos
     *         ou false caso contrário.
     */
    public boolean existeVeiculo(String matricula) {
        return this.veiculos.containsKey(matricula);
    }


    /* -------------------------------- CLASSIFICAÇÃO -------------------------------- */


    /**
     * Método responsável por devolver uma lista de alugueres que o proprietário tem para classificar o cliente.
     * É feito um varrimento completo e feita a verificação acerca do estado da classificação.
     *
     * @return List<Aluguer> Lista com os alugueres para o proprietário classificar o cliente.
     */
     public List<Aluguer> alugueresClassificarCliente() throws NaoExistemAlugueresException{
         List<Aluguer> alugsClassif = new ArrayList<>();

         if (!this.alugueres.containsKey(getUserNIF())){
             throw new NaoExistemAlugueresException(getUserNIF());
         }

         Map<String, List<Aluguer>> alugsProp = this.alugueres.get(getUserNIF());

         for (List<Aluguer> lAlugs : alugsProp.values()){
             for (Aluguer alug : lAlugs){
                 if (alug.getRealizado() && (alug.getEstadoClassificacao() == 0 || alug.getEstadoClassificacao() == 2)) {
				         alugsClassif.add(alug);
				 }
             }
         }
         return  alugsClassif;
     }

    /**
     * Método responsável por efetuar a classificação do cliente.
     * Vai-se buscar a classificação anterior e faz-se média com o número de alugueres que
     * o cliente tem e depois é substituido nos alugueres.
     * @param alug           Aluguer a classificar o cliente.
     * @param classif        Classificação a atribuir.
     */
     public void classificarCliente(Aluguer alug, int classif){
         Cliente cli = (Cliente) this.utilizadores.get(alug.getEmail());
         int nAlugs = cli.getNAlugueres();
         int classificAnterior = cli.getClassificacao();
         double novaClassificacao = (classificAnterior + classif) / nAlugs;
         Long c = Math.round(novaClassificacao);
         int classifiFinal = Integer.valueOf(c.intValue());
         cli.setClassificacao(classifiFinal);
         this.utilizadores.replace(cli.getEmail(), cli.clone());
         if (alug.getEstadoClassificacao() == 0){
             alug.setEstadoClassificacao(1);
         }
         if (alug.getEstadoClassificacao() == 2){
             alug.setEstadoClassificacao(3);
         }
     }


    /* ----------------------------------- VE�?CULOS ---------------------------------- */


    /**
     * Método responsável por registar um veículo. É feita primeiro 
     * uma verificação para comprovar que este existe no Map de veiculos, através da matrícula 
     * recebida como parâmetro, caso este exista não é adicionado caso contrário o veículo é adicionado ao map de veiculos.
     * 
     * @param v Veículo a registar.
     */
    public void registarVeiculo(Veiculo v) throws VeiculoJaExisteException{
        String matricula = v.getMatricula();
        if(this.veiculos.containsKey(matricula)){
            throw new VeiculoJaExisteException(matricula);
        }
        this.veiculos.put(matricula, v.clone());
    }
    
    /**
     * Método responsável por definir a disponibilidade de um certo veículo. É feita primeiro 
     * uma verificação para comprovar que este existe no Map de veiculos, através da matrícula 
     * recebida como parâmetro, caso este exista é alterada a disponibilidade recebendo esta também com parâmetro.
     * 
     * @param matricula        Matrícula do veículo.
     * @param disponibilidade  Disponibilidade desejada para o veículo.
     */
    public void sinalizarDisponibilidade(String matricula, boolean disponibilidade) throws VeiculoNaoExisteException, VeiculoNaoESeuException{
        if(!veiculos.containsKey(matricula)){
            throw new VeiculoNaoExisteException(matricula);
        }
        if(!this.user.getNIF().equals(veiculos.get(matricula).getNIF())) {
            throw new VeiculoNaoESeuException(matricula);
        }
        this.veiculos.get(matricula).setDisponibilidade(disponibilidade);
    }
    
    /**
     * Método responsável por abastecer um certo veículo. 
     * 
     * @param matricula    Matrícula do veículo a abastecer.
     * @param quantidade    Quantidade a abastecer.
     */
    public void abastecerVeiculo(String matricula, double quantidade) throws VeiculoNaoExisteException, VeiculoNaoESeuException{
        if(!this.veiculos.containsKey(matricula)){
            throw new VeiculoNaoExisteException(matricula);
        }
        if(!this.user.getNIF().equals(veiculos.get(matricula).getNIF())) {
            throw new VeiculoNaoESeuException(matricula);
        }
        this.veiculos.get(matricula).abastecerVeiculo(quantidade);
    }
    
    /**
     * Método responsável por alterar preço por Km de um veículo.
     * 
     * @param matricula Matrícula do veículo.
     * @param precoKM   Novo Preço por KM.
     */
    public void altPrecoKm(String matricula, double precoKM) throws VeiculoNaoExisteException, VeiculoNaoESeuException{
        if(!veiculos.containsKey(matricula)){
            throw new VeiculoNaoExisteException(matricula);
        }
        if(!this.user.getNIF().equals(veiculos.get(matricula).getNIF())) {
            throw new VeiculoNaoESeuException(matricula);
        }
        veiculos.get(matricula).setPreco(precoKM);
    }


    /* ---------------------------------- ALUGUERES ---------------------------------- */


    /**
     * Método responsável por determinar a lista de alugueres de um proprietário.
     * 
     * @param chave  Chave de acesso do proprietário.
     */
    public List<Aluguer> getAlugueresProp(String chave){
        List<Aluguer> res = new ArrayList<>();
        Map<String, List<Aluguer>> aux = this.alugueres.get(chave);
        
        if(aux != null){
            for (List<Aluguer> a : aux.values()) {
                for(Aluguer al : a) {
                    res.add(al.clone());
                }
            }
            }
        return res;
    }
    
    /**
     * Método responsável por determinar a lista de alugueres de um proprietário 
     * que estão à espera de uma resposta do mesmo.
     * 
     * @param chave  Chave de acesso do proprietario.
     */
    public List<Aluguer> determinarListaEspera(String chave){
        List<Aluguer> alugs = getAlugueresProp(chave);
        alugs.stream().filter(alug -> alug.getListaEspera()).collect(Collectors.toList());
        return alugs;
    }
    
    /**
     * Método responsável por definir no aluguer se este foi aceite pelo proprietário.
     * 
     * @param resp  Resposta do proprietário ao pedido de aluguer.
     * @param alug  Aluguer ao qual recebeu resposta do proprietário.
     */
    public void respostaProp(boolean resp, Aluguer alug){
        // buscar indice do aluguer na lista de alugueres do veiculo em questão
        int index = this.alugueres.get(user.getNIF()).get(alug.getMatricula()).indexOf(alug);
        // remover antigo aluguer que está desatualizado
        this.alugueres.get(user.getNIF()).get(alug.getMatricula()).remove(index);
        
        alug.setListaEspera(false);
        alug.setAceite(resp);
        
        if(resp) {
            GregorianCalendar inicio = alug.getDataInicio();
            GregorianCalendar fim = alug.getDataInicio();
            this.veiculos.get(alug.getMatricula()).addDatas(inicio,fim);
        }
        
        //adicionar alugueres na posição correta
        this.alugueres.get(user.getNIF()).get(alug.getMatricula()).add(index,alug);
    }
    
    /**
     * Método responsável por determinar a lista de alugueres de um proprietário feitos por determinado cliente e veiculo 
     * que estão à espera de uma resposta do mesmo.
     * 
     * @param matricula  Matricula Carro.
     * @param mail  Email do Cliente.
     */
    public List<Aluguer> determinarListaAlugCli(String matricula, String mail) throws VeiculoNaoESeuException, UtilizadorNaoExisteException{
        if(!this.utilizadores.containsKey(mail)) {
            throw new UtilizadorNaoExisteException(mail);
        }
        if(!this.user.getNIF().equals(veiculos.get(matricula).getNIF())) {
            throw new VeiculoNaoESeuException(matricula);
        }
        List<Aluguer> alugs = this.alugueres.get(user.getNIF()).get(matricula).stream().map(Aluguer :: clone).collect(Collectors.toList());
        alugs.stream().filter(alug -> !alug.getAlteraPreco() && alug.getEmail().equals(mail)).collect(Collectors.toList());
        return alugs;
    }
    
    /**
     * Método responsável por alterar o preço de um aluguer.
     * 
     * @param newPrice novo preço.
     * @param alug  Aluguer ao qual o seu preço vai ser alterado.
     */
    public void altPrecoAluguer(double newPrice, Aluguer alug){
        // buscar indice do aluguer na lista de alugueres do veiculo em questão
        int index = this.alugueres.get(user.getNIF()).get(alug.getMatricula()).indexOf(alug);
        // remover antigo aluguer que está desatualizado
        this.alugueres.get(user.getNIF()).get(alug.getMatricula()).remove(index);
        
        alug.setAlteraPreco(true);
        alug.setCustoViagem(newPrice);
         
        //adicionar alugueres na posição correta
        this.alugueres.get(user.getNIF()).get(alug.getMatricula()).add(index,alug);
    }
    
    /**
     * Método responsável por determinar o total faturado de um veículo entre duas datas, 
     * recebidas como parâmetro.
     * 
     * @param matricula  Matrícula do veículo.
     * @param inicio     Limite temporal inferior.
     * @param fim        Limite temporal superior.
     */
    public double totalFactBDates(String matricula, GregorianCalendar inicio, GregorianCalendar fim) throws VeiculoNaoESeuException{
        double total = 0;
        if(! this.alugueres.get(user.getNIF()).containsKey(matricula)){
            throw new VeiculoNaoESeuException(matricula);
        }
        for(Aluguer a: this.alugueres.get(user.getNIF()).get(matricula)){
            if(a.getDataInicio().after(inicio) && a.getDataFim().before(fim) && a.getAceite()){
                total += a.getCustoViagem();
            }
        }
        return total;
    }

    /**********************************************************************************
     *                           FIM MÉTODOS PROPRIET�?RIOS                            *
     **********************************************************************************/

     
    /**********************************************************************************
     *                                MÉTODOS CLIENTES                                *
     **********************************************************************************/


    /* -------------------------------- CLASSIFICAÇÃO -------------------------------- */


    /**
     * Método responsável por devolver uma lista de alugueres que o cliente tem para classificar o veículo.
     * É feito um varrimento completo e feita a verificação acerca do estado da classificação.
     *
     * @return List<Aluguer> Lista com os alugueres para o cliente classificar o veículo.
     */
    public List<Aluguer> alugueresClassificarVeiculo(){
        List<Aluguer> alugsClassif = new ArrayList<>();
        for (Map<String, List<Aluguer>> mAlugs : this.alugueres.values()){
            for (List<Aluguer> lAlugs : mAlugs.values()){
                for (Aluguer alug : lAlugs){
                    if (alug.getEmail().equals(getEmailUser()) && alug.getRealizado() && (alug.getEstadoClassificacao() == 0 || alug.getEstadoClassificacao() == 1)) {
					    alugsClassif.add(alug);
					}
                }
            }
        }
        return  alugsClassif;
    }

    /**
     * Método responsável por efetuar a classificação do veículo.
     * Vai-se buscar a classificação anterior e faz-se média com o número de alugueres que
     * o cliente tem e depois é substituido nos alugueres.
     * @param alug           Aluguer a classificar o cliente.
     * @param classif        Classificação a atribuir.
     */
    public void classificarVeiculo(Aluguer alug, int classif){
        Veiculo v = this.veiculos.get(alug.getMatricula());
        int nAlugs = this.veiculos.get(alug.getMatricula()).getDatasAlugueres().size();
        int classificAnterior = v.getClassificacao();
        double novaClassificacao = (classificAnterior + classif) / nAlugs;
        Long c = Math.round(novaClassificacao);
        int classifiFinal = Integer.valueOf(c.intValue());
        v.setClassificacao(classifiFinal);
        this.veiculos.replace(v.getMatricula(), v.clone());
        if (alug.getEstadoClassificacao() == 0){
            alug.setEstadoClassificacao(2);
        }
        if (alug.getEstadoClassificacao() == 1){
            alug.setEstadoClassificacao(3);
        }
    }


    /* ---------------------------------- ALUGUERES ---------------------------------- */


    /**
     * Método responsável por registar um aluguer na aplicação. É feita a inserção a partir
     * do NIF do proprietário e no segundo Map, a inserção é através da matrícula do veículo
     * do proprietário e este aluguer será adicionado a este lista. Caso o proprietário ainda
     * não tenha qualquer aluguer registado é feita então a criação das estruturas de dados
     * para armazenar o aluguer.
     *
     * @param alug Aluguer que se pretende registar.
     */
    public void registaAluguer(Aluguer alug){
        String matricula = alug.getMatricula();
        String NIFprop = this.veiculos.get(matricula).getNIF();

        Map<String, List<Aluguer>> alugs = this.alugueres.get(NIFprop);
        try{
            if (alugs.get(matricula) == null){
                List<Aluguer> alugsPropVeiculo = new ArrayList<Aluguer>();
                alugsPropVeiculo.add(alug.clone());
                alugs.put(matricula, alugsPropVeiculo);
            }else{
                List<Aluguer> alugsPropVeiculo = alugs.get(matricula);
                alugsPropVeiculo.add(alug.clone());
                alugs.put(matricula, alugsPropVeiculo);
            }
        }
        catch (NullPointerException exc){
            alugs = new HashMap<String, List<Aluguer>>();
            List<Aluguer> alugsPropVeiculo = new ArrayList<Aluguer>();
            alugsPropVeiculo.add(alug.clone());
            alugs.put(matricula, alugsPropVeiculo);
        }
        this.alugueres.put(NIFprop, alugs);
    }

    /**
     * Método que coloca os alugueres efetuados antes de uma determinada data, recebida como parâmetro a
     * realizados ou não realizados, dependendo se a data recebida como parâmetro é depois ou antes da data
     * de fim de aluguer.
     * @param date Data do dia de hoje.
     */
    public void alugueresEfetuados(GregorianCalendar date){
        for (Map<String, List<Aluguer>> mAlugs : this.alugueres.values()){
            for (List<Aluguer> lAlugs : mAlugs.values()){
                for (Aluguer alug : lAlugs){
                    if (alug.getDataFim().before(date) && !alug.getRealizado()){
                        alug.setRealizado(true);
                        String matricula = alug.getMatricula();
                        String mail = alug.getEmail();
                        Cliente cli = (Cliente) this.utilizadores.get(mail);
                        cli.setNAlugueres(cli.getNAlugueres() + 1);
                        cli.setNKM(alug.getDistancia() + cli.getNKm());
                        cli.setPosicao(alug.getDestino());
                        Veiculo v = this.veiculos.get(matricula);
                        v.setPosicao(alug.getDestino());
                        Long d = Math.round(alug.getDistancia());
                        int dist = Integer.valueOf(d.intValue());
                        v.setAutonomia(v.getAutonomia() - dist);
                    }
                }
            }
        }
    }

    /**
     * Método responsável por devolver uma lista com os veículos que estão disponíveis
     * para alugar, numa determinada data recebida como parâmetro. É feita
     * inicialmente uma verificação se este está disponível e depois são comparadas
     * as datas de aluguer deste com a data de aluguer recebida com o parâmetro.
     *
     * @param date  Intervalo de datas em que o cliente pretende o aluguer.
     */
    private List<Veiculo> disponiveisAlugar(Coordinate destino, ParDatas date){
        return this.veiculos.values().stream().filter(v -> v.getDisponibilidade() &&
                v.getDatasAlugueres().stream().filter(d -> !d.isAvailable(date)).count() == 0
                && autonomiaSuf(v,destino)).map(Veiculo :: clone).collect(Collectors.toList());
    }

    /**
     * Método responsável por determinar se um veículo tem autonomia suficiente
     * para atingir um certo destino.
     *
     * @param v        Veículo a efetuar a verificação.
     * @param destino  Destino do cliente.
     * @return boolean True caso tenha autonomia, False caso contrário.
     */
    private boolean autonomiaSuf(Veiculo v, Coordinate destino){
        if(v.getAutonomia() >= v.getPosicao().getDistancia(destino)){
            return true;
        }
        return false;
    }
    
    /**
     * Método responsável por devolver uma List ordenada com os veículos mais
     * próximos do cliente, que estejam disponíveis para aluguar mediante
     * uma data recebida como parâmetro e mediante o destino.
     *
     * @param destino  Destino do cliente.
     * @param date     Intervalo de datas em que o cliente pretende o aluguer.
     * @param quantos  Número a listar.
     */
    public List<Veiculo> maisProximo(Coordinate destino, ParDatas date, int quantos) throws NaoExistemVeiculosDisponiveisException{
        List<Veiculo> veiculosOrdenados = new ArrayList<>();


        for (Veiculo v : disponiveisAlugar(destino, date)){
            veiculosOrdenados.add(v.clone());
        }
        if(veiculosOrdenados.isEmpty()){
            throw new NaoExistemVeiculosDisponiveisException("Nao existem veiculos disponiveis para alugar. 1");
        }
        Coordinate posCli = getPosicaoCliente();
        veiculosOrdenados.sort(new Comparator<Veiculo>(){
                                   public int compare(Veiculo a1, Veiculo a2) {
                                       if (a1.getPosicao().getDistancia(posCli) < a2.getPosicao().getDistancia(posCli)) {
                                           return 1;
                                       }
                                       return -1;
                                   };
                                 });
        return veiculosOrdenados.stream().limit(quantos).collect(Collectors.toList());
    }
    
    /**
     * Método responsável por devolver uma Lista ordenada com os veículos mais
     * baratos, onde em cada veículo na lista de datas de aluguer do mesmo,
     * esteja disponíveis para alugar mediante uma data recebida como parâmetro
     * e caso tenha autonomia.
     *
     * @param destino Destino do cliente.
     * @param date    Intervalo de datas em que o cliente pretende o aluguer.
     * @param quantos Número de veículos que o cliente deseja ver.
     * @throws NaoExistemVeiculosDisponiveisException Excepção caso não existam veículos disponíveis.
     * @return List<Veiculo> Lista com os veículos mais baratos.
     */
    public List<Veiculo> maisBarato(Coordinate destino, ParDatas date, int quantos) throws NaoExistemVeiculosDisponiveisException{
        List<Veiculo> veic = new ArrayList<>();

        for (Veiculo v : disponiveisAlugar(destino, date)){
            veic.add(v.clone());
        }
        if(veic.isEmpty()){
            throw new NaoExistemVeiculosDisponiveisException("Nao existem veiculos disponiveis para alugar. 1");
        }
        veic.sort(new ComparadorPreco());
        return veic.stream().limit(quantos).collect(Collectors.toList());
    }
    
    /**
     * Método responsável por devolver uma Lista ordenada com os veículos mais baratos que estão disponíveis
     * para alugar, numa determinada data recebida como parâmetro e dentro de um perímetro. É feita
     * inicialmente uma verificação se este está disponível, depois são comparadas
     * as datas de aluguer deste com a data de aluguer recebida com o parâmetro e posteriormente
     * calculada a distancia entre o Cliente e o Veiculo e são apresentados os veiculos cuja
     * distância é menor ou igual ao perímetro.
     *
     * @param destino   Destino do cliente.
     * @param cordsCli  Coordenadas do cliente.
     * @param date      Intervalo de datas em que o cliente pretende o aluguer.
     * @param perimetro Perimetro a percorrer a pé pelo cliente.
     * @param quantos   Número de veículos que o cliente deseja ver.
     * @throws NaoExistemVeiculosDisponiveisException Excepção caso não existam veículos disponíveis.
     * @return List<Veiculo> Lista com os veículos mais baratos num perímetro.
     */
    public List<Veiculo> maisBaratoNoPerimetro (Coordinate destino, Coordinate cordsCli, ParDatas date, double perimetro, int quantos) throws NaoExistemVeiculosDisponiveisException{
        List<Veiculo> veiculosOrdenados = maisBarato(destino,date,quantos);
        List<Veiculo> veiculosBaratosNoP = new ArrayList<>();

        for(Veiculo v: veiculosOrdenados){
            if (cordsCli.getDistancia(v.getPosicao()) <= perimetro){
                veiculosBaratosNoP.add(v.clone());
            }
        }
        if(veiculosBaratosNoP.isEmpty()){
            throw new NaoExistemVeiculosDisponiveisException("Não Existem Veículos Disponíveis para Alugar.");
        }
        return veiculosBaratosNoP.stream().limit(quantos).collect(Collectors.toList());
    }

    /**
     * Método responsável por devolver o Veiculo em especifico requisitado pelo cliente.
     * Caso o veiculo não exista , devolve VeiculoNaoExisteException.
     * Visto que é relativo a um veiculo apenas, obtemos as datas de alugueres do veiculo e
     * verificamos a disponibilidade das datas e do veiculo.
     *
     * @param destino Destino do cliente.
     * @param date    Intervalo de datas em que o cliente pretende o aluguer.
     * @param id      Matrícula do veículo.
     * @throws VeiculoNaoExisteException Excepção caso o veículo não exista.
     * @throws VeiculoIndisponivelException Excepção caso o veículo esteja indisponivel para alugar.
     * @return Veiculo O veículo a alugar.
     */
    public Veiculo veiculoEspecifico(Coordinate destino, ParDatas date, String id) throws VeiculoNaoExisteException, VeiculoIndisponivelException{
        if( !this.veiculos.containsKey(id) ){
            throw new VeiculoNaoExisteException(id);
        }

        List<ParDatas> datasDeUmVeiculo = this.veiculos.get(id).getDatasAlugueres();

        if(this.veiculos.get(id).getDisponibilidade() && autonomiaSuf(this.veiculos.get(id), destino) &&
                datasDeUmVeiculo.stream().filter(d->!d.isAvailable(date)).count() == 0){
            return this.veiculos.get(id).clone();
        }else {
            throw new VeiculoIndisponivelException(id);
        }
    }
  
    /**
     * Método responsável por devolver uma Lista com os veículos com autonomia dentro de um intervalo,
     * que estão disponíveis para alugar, numa determinada data recebida como parâmetro.
     *
     * @param destino  Destino do cliente.
     * @param date     Intervalo de datas em que o cliente pretende o aluguer.
     * @param val1     Limite inferior do intervalo
     * @param val2     Limite superior do intervalo
     * @param quantos  Número de veículos a listar.
     * @throws NaoExistemVeiculosDisponiveisException Excepção caso não existam veículos disponíveis para alugar.
     * @return List<Veiculo> Lista ordenada com os veículos que têm a autonomia desejada.
     */
    public List<Veiculo> determinadaAutonomia (Coordinate destino, ParDatas date, int val1, int val2, int quantos) throws NaoExistemVeiculosDisponiveisException {
        List<Veiculo> veiculosAuto = new ArrayList<>();

        for(Veiculo v: disponiveisAlugar(destino,date) ){
            if(v.verificaAutonomia(val1,val2)){
                veiculosAuto.add(v.clone());
            }
        }
        if(veiculosAuto.isEmpty()){
            throw new NaoExistemVeiculosDisponiveisException("Não Existem Veículos Disponíveis para Alugar.");
        }
        veiculosAuto.sort(new ComparadorAutonomia());
        return veiculosAuto.stream().limit(quantos).collect(Collectors.toList());
    }
    
   /**
    * Método responsável por determinar a lista de alugueres de um cliente.
    * 
    * @param mail  Chave de acesso do cliente.
    */
   public List<Aluguer> getAlugueresCliente(String mail) throws NaoEfetuouNenhumAluguerException {
       List <Aluguer> lista = new ArrayList<>();
       if(this.alugueres != null){
           for(Map<String, List<Aluguer>> aux : this.alugueres.values()) {
               if(aux != null) {
                   for (List<Aluguer> a : aux.values()) {
                       for(Aluguer al : a) {
                           if(al.getEmail().equals(mail)){
                               lista.add(al.clone());
                           }
                       }
                   }
               }
           }
       }
       if(lista.isEmpty()){
           throw new NaoEfetuouNenhumAluguerException("Não efetuou nenhum aluguer.");
       }
       return lista;
   }

    
   /**********************************************************************************
    *                                FIM CLIENTES                                    *
    **********************************************************************************/


    /**********************************************************************************
     *                        MÉTODOS EXCLUSIVOS PARA OS LOGS                         *
     **********************************************************************************/

    /**
     * Método responsável por devolver o veículo mais próximo do cliente, q
     * ue esteja disponível para aluguar mediante uma data recebida como
     * parâmetro e mediante o destino.
     *
     * @param posCli   Posição do cliente.
     * @param destino  Destino do cliente.
     * @param date     Intervalo de datas em que o cliente pretende o aluguer.
     * @param combustivel Tipo de combustível.
     */
    public Veiculo maisPertoJa(Coordinate posCli, Coordinate destino, ParDatas date, String combustivel) throws NaoExistemVeiculosDisponiveisException{
        List<Veiculo> veiculosOrdenados = new ArrayList<>();

        for (Veiculo v : disponiveisAlugar(destino, date)){
            if (v.getClass().getSimpleName().equals(combustivel)) {
                veiculosOrdenados.add(v.clone());
            }
        }
        if(veiculosOrdenados.isEmpty()){
            throw new NaoExistemVeiculosDisponiveisException("Nao existem veículos disponíveis para alugar.");
        }
        /*veiculosOrdenados.sort(new Comparator<Veiculo>(){
            public int compare(Veiculo a1, Veiculo a2) {
                if (a1.getPosicao().getDistancia(posCli) < a2.getPosicao().getDistancia(posCli)) {
                    return 1;
                }
                if (a1.getPosicao().getDistancia(posCli) > a2.getPosicao().getDistancia(posCli)){
                    return -1;
                }
                return 0;
            };
        });*/
        return veiculosOrdenados.get(0).clone();
    }

    /**
     * Método responsável por o veículo mais barato, que esteja disponível para alugar
     * mediante uma data recebida como parâmetro e mediante uma coordenada de destino.
     *
     * @param dest  Coordenada do destino.
     * @param date  Intervalo de datas em que o cliente pretende o aluguer.
     * @param combustivel Tipo de combustível.
     * @return Veiculo Veículo mais barato.
     */
    public Veiculo maisBaratoJa(Coordinate dest, ParDatas date, String combustivel) throws NaoExistemVeiculosDisponiveisException{
        List<Veiculo> veiculosOrdenados = new ArrayList<>();

        for (Veiculo v : disponiveisAlugar(dest, date)){
            if (v.getClass().getSimpleName().equals(combustivel)){
                veiculosOrdenados.add(v);
            }
        }
        if(veiculosOrdenados.isEmpty()){
            throw new NaoExistemVeiculosDisponiveisException("Não Existem Veículos Disponíveis para Alugar.");
        }
        return veiculosOrdenados.get(0).clone();
    }

    public void alterarPosAutonomiaVeiculo(String matricula, Coordinate dest){
        Veiculo v = this.veiculos.get(matricula);
        Long d = Math.round(v.getPosicao().getDistancia(dest));
        int dist = Integer.valueOf(d.intValue());
        v.setAutonomia(v.getAutonomia() - dist);
        v.setPosicao(dest);
        GregorianCalendar dataInicio = new GregorianCalendar(2019,Calendar.MARCH,20);
        GregorianCalendar dataFim = new GregorianCalendar(2019,Calendar.MARCH,21);
        v.addDatas(dataInicio, dataFim);
    }

    public void alterarPosCliente(String mail, Coordinate dest){
        Cliente cli = (Cliente) this.utilizadores.get(mail);
        cli.setPosicao(dest);
        cli.setNAlugueres(cli.getNAlugueres() + 1);
    }

    public void classificarClienteJa(String mail, int classificacao){
        try{
            Utilizador u = getUtilizador(mail);
            if (u instanceof Cliente){
                Cliente cli = (Cliente) u;
                int nAlugs = cli.getNAlugueres();
                if (nAlugs == 0){
                    nAlugs = 1;
                }
                int classificAnterior = cli.getClassificacao();
                double novaClassificacao = (int) (classificAnterior + classificacao) / nAlugs;
                Long c = Math.round(novaClassificacao);
                int classifiFinal = Integer.valueOf(c.intValue());
                cli.setClassificacao(classifiFinal);
                this.utilizadores.replace(cli.getEmail(), cli.clone());
            }   
        }catch (UtilizadorNaoExisteException e){
        }
    }

    public void classificarVeiculoJa(String matricula, int classificacao){
        Veiculo v = this.veiculos.get(matricula);
        int nAlugs = v.getDatasAlugueres().size();
        if (nAlugs == 0){
            nAlugs = 1;
        }
        int classificAnterior = v.getClassificacao();
        double novaClassificacao = (classificAnterior + classificacao) / nAlugs;
        Long c = Math.round(novaClassificacao);
        int classifiFinal = Integer.valueOf(c.intValue());
        v.setClassificacao(classifiFinal);
        this.veiculos.replace(v.getMatricula(), v.clone());
    }

    public int getNUsers(){
        return this.utilizadores.size();
    }
    
    public int getNVeiculos(){
        return this.veiculos.size();
    }
    
    public int getNAlugs(){
        int conta = 0;
        for (Map<String, List<Aluguer>> a : this.alugueres.values()){
            for (List<Aluguer> l : a.values()){
                for (Aluguer alug : l){
                    conta++;
                }
            }
        }
        return conta;
    }
    
    public String toStringAlugs(){
        StringBuilder sb = new StringBuilder();
        for (Map<String, List<Aluguer>> a : this.alugueres.values()){
            for (List<Aluguer> l : a.values()){
                for (Aluguer alug : l){
                    sb.append(alug);
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }
    
    public String toStringUser(){
        StringBuilder sb = new StringBuilder();
        for (Utilizador a : this.utilizadores.values()){
            sb.append(a);
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public String toStringVeiculo(){
        StringBuilder sb = new StringBuilder();
        for (Veiculo v : this.veiculos.values()){
            sb.append(v);
            sb.append("\n");
        }
        return sb.toString();
    }

    /**********************************************************************************
     *                    FIM DOS MÉTODOS EXCLUSIVOS PARA OS LOGS                     *
     **********************************************************************************/

    /**
     * Grava os campos da instância UmCarroJa e a data da aplicação num ficheiro objeto.
     */
    public void guardarEstado(String file, GregorianCalendar date) throws FileNotFoundException, IOException{
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(date);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }
}
