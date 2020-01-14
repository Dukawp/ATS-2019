/**
 * Classe que representa um veículo.
 * 
 * 
 * 
 * 
 * @version 20190415
 */

import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;   

import java.util.GregorianCalendar;

public class Veiculo implements Serializable, Classificação{
    /** Variáveis de Instância. */
    
    /* Marca do veículo */
    private String marca;
    /** Matrícula do veículo. */
    private String matricula;
    /** NIF do proprietário do veículo. */
    private String nif;
    /** Velocidade média por Km. */
    private int velocidade;
    /** Preço base por Km. */
    private double preco;
    /** Consumo por Km percorrido. */
    private double consumo;
    /** Autonomia total do veículo. */
    private int autonomia;
    /** Posição (Latitude / Longitude) do veículo. */
    private Coordinate posicao;
    /** Veículo disponível para alugar. */
    private boolean disponivel;
    /** Classificação do veículo. */
    private int classificacao;
    /** Instância da classe ParDatas que representa um par de datas, data de início e fim de um aluguer, efetuado. */
    private List<ParDatas> datasAlugueres;
    
    /** Construtor por omissão. É criada uma instância da classe Veiculo. */
    public Veiculo(){
        this.marca = "";
        this.matricula = "";
        this.nif = "";
        this.velocidade = 0;
        this.preco = 0.0;
        this.consumo = 0.0;
        this.autonomia = 0;
        this.posicao = new Coordinate(0,0);
        this.disponivel = false;
        this.classificacao = 0;
        this.datasAlugueres = new ArrayList<>();
    }
    
    /**
     * Construtor por parâmetro.
     * @param marca       Marca do veículo.
     * @param matricula   Matrícula do veículo.
     * @param nif         NIF do veículo.
     * @param velocidade  Velocidade média por Km.
     * @param preco       Preço base por cada Km percorrido.
     * @param consumo     Consumo por Km percorrido. 
     * @param autonomia   Autonomia em Km do veículo.
     * @param cords       Coordenadas em que o veículo se encontra.
     * @param disponivel  True se o veículo estiver disponível, false caso contrário.
     * @param classif     Classificação do veículo.
     * @param datasAlugs  Datas dos alugueres do veículo.
     */
    public Veiculo(String marca, String matricula, String nif, int velocidade, double preco, double consumo, 
                   int autonomia, Coordinate cords, boolean disponivel, int classif, List<ParDatas> datasAlugs){
        this.marca = marca;
        this.matricula = matricula;
        this.nif = nif;
        this.velocidade = velocidade;
        this.preco = preco;
        this.consumo = consumo;
        this.autonomia = autonomia;
        this.posicao = cords;
        this.disponivel = disponivel;
        this.classificacao = classif;
        this.datasAlugueres = datasAlugs.stream().map(ParDatas :: clone).collect(Collectors.toCollection(ArrayList::new));
    }
    
    /**
     * Construtor por cópia.
     * @param outroVeiculo Um outro veículo.
     */
    public Veiculo(Veiculo outroVeiculo){
        this.marca = outroVeiculo.getMarca();
        this.matricula = outroVeiculo.getMatricula();
        this.nif = outroVeiculo.getNIF();
        this.velocidade = outroVeiculo.getVelocidade();
        this.preco = outroVeiculo.getPreco();
        this.consumo = outroVeiculo.getConsumo();
        this.autonomia = outroVeiculo.getAutonomia();
        this.posicao = outroVeiculo.getPosicao();
        this.disponivel = outroVeiculo.getDisponibilidade();
        this.classificacao = outroVeiculo.getClassificacao();
        this.datasAlugueres = outroVeiculo.getDatasAlugueres();
    }
    
    /** BEGIN GETTERS. */
    
    /**
     * Devolve a marca do veículo.
     * @return String Representa a marca do veículo.
     */
    public String getMarca(){return this.marca;}
    
    /**
     * Devolve a matrícula do veículo.
     * @return String Representa a matrícula do veículo.
     */
    public String getMatricula(){return this.matricula;}
    
    /**
     * Devolve a matrícula do veículo.
     * @return String Representa a matrícula do veículo.
     */
    public String getNIF(){return this.nif;}
    
    /**
     * Devolve a velocidade média por Km.
     * @return int Representa a velocidade média por Km.
     */
    public int getVelocidade(){return this.velocidade;}
    
    /**
     * Devolve o preço base por Km.
     * @return double Representa o preço base por Km.
     */
    public double getPreco(){return this.preco;}
    
    /**
     * Devolve o consumo por Km percorrido.
     * @return Representa o consumo por Km percorrido.
     */
    public double getConsumo(){return this.consumo;}
    
    /**
     * Devolve a autonomia total do veículo.
     * @return Representa a autonomia total do veículo.
     */
    public int getAutonomia(){return this.autonomia;}
    
    /**
     * Devolve a posição (latidude e longitude) do veículo.
     * @return Coordinate Representa a posição onde se encontra o veículo. 
     */
    public Coordinate getPosicao(){return this.posicao;}
    
    /**
     * Devolve true caso o veículo esteja disponível ou false caso contrário.
     * @return boolean True se estiver disponível ou false caso não esteja.. 
     */
    public boolean getDisponibilidade(){return this.disponivel;}
    
    /**
     * Devolve a classificação do veículo.
     * @return int Representa a classificação do veículo.
     */
    public int getClassificacao(){return this.classificacao;}
    
    /**
     * Devolve a lista com as datas dos alugueres, confirmados, do veículo.
     * @return List<ParDatas> Representa a lista de pares de datas de alugueres.
     */
    public List<ParDatas> getDatasAlugueres(){return this.datasAlugueres.stream().map(ParDatas :: clone).collect(Collectors.toList());}
    
    /** END GETTERS. */
     
    /** BEGIN SETTERS. */
    
    /** Altera o valor do campo marca. */
    public void setMarca(String marca){this.marca = marca;}
    
    /** Altera o valor do campo matrícula. */
    public void setMatricula(String matricula){this.matricula = matricula;}
    
    /** Altera o valor do campo NIF. */
    public void setNIF(String nif){this.nif = nif;}
    
    /** Altera o valor do campo velocidade. */
    public void setVelocidade(int velocidade){this.velocidade = velocidade;}
    
    /** Altera o valor do campo preço. */
    public void setPreco(double preco){this.preco = preco;}
    
    /** Altera o valor do campo consumo. */
    public void setConsumo(double consumo){this.consumo = consumo;}
    
    /** Altera o valor do campo autonomia. */
    public void setAutonomia(int autonomia){this.autonomia = autonomia;}
    
    /** Altera o valor do campo posição. */
    public void setPosicao(Coordinate cords){this.posicao = cords;}
    
    /** Altera o valor do campo disponível. */
    public void setDisponibilidade(boolean disponivel){this.disponivel = disponivel;}
    
    /** Altera o valor do campo classificação. */
    public void setClassificacao(int classificacao){this.classificacao = classificacao;}
    
    /**
     * Altera o valor da lista com as datas dos alugueres, confirmados, do veículo.
     */
    public void setDatasAlugueres(List<ParDatas> datas){this.datasAlugueres = datas.stream().map(ParDatas :: clone).collect(Collectors.toList());}
    
    /** END SETTERS. */

        /** Método que devolve o tempo a pé até ao veículo, mediante uma posição recebida como parâmetro.
     * @param posicaoCli Posição inicial.
     * @return int Tempo em minutos a pé até ao veículo.
     */
    public int tempoAteVeiculoPé(Coordinate posicaoCli, Weather weath){
        double dist = posicaoCli.getDistancia(this.posicao);
        Long tempo = Math.round((dist * 60) / 4 * weath.getPercentage(this.posicao.getLatitude(), this.posicao.getLongitude()));
        return Integer.valueOf(tempo.intValue());
    }

    /** Método que devolve o preço de uma viagem mediante uma distância recebida como parâmetro.
     * @param destino Destino.
     * @return int Representa o tempo em minutos da viagem.
     */
    public int tempoViagemCarro(Coordinate destino, Weather weath){
        double dist = this.posicao.getDistancia(destino);
        Long tempoMin = Math.round((dist * 60) / 70 * weath.getPercentage(this.posicao.getLatitude(), this.posicao.getLongitude()));
        return tempoMin.intValue();
    }
    
    /** Método que devolve o tempo a pé até ao veículo, mediante uma posição recebida como parâmetro.
     * @param posicaoCli Posição inicial.
     * @return int Tempo em minutos a pé até ao veículo.
     */
    public int tempoAteVeiculoPéJa(Coordinate posicaoCli){
        double dist = posicaoCli.getDistancia(this.posicao);
        Long tempo = Math.round((dist * 60) / 4);
        return Integer.valueOf(tempo.intValue());
    }

    /** Método que devolve o preço de uma viagem mediante uma distância recebida como parâmetro.
     * @param destino Destino.
     * @return int Representa o tempo em minutos da viagem.
     */
    public int tempoViagemCarroJa(Coordinate destino){
        double dist = this.posicao.getDistancia(destino);
        Long tempoMin = Math.round(dist * 60);
        return tempoMin.intValue();
    }

    /** Método que devolve o preço de uma viagem mediante uma distância recebida como parâmetro.
     * @param dist Distância a percorrer.
     * @return double Custo da viagem.
     */
    public double custoViagem(double dist){
        return this.preco * dist;
    }

    /**
     * Aumenta a autonomia do veículo após uma quantidade de combustível abastecida recebida como parâmetro.
     * @param quantidade Quantidade de combustível a abastecer.
     */
    public void abastecerVeiculo(double quantidade){
        BigDecimal qtd = BigDecimal.valueOf(quantidade);
        BigDecimal autonomiaInicial = BigDecimal.valueOf(getAutonomia());
        BigDecimal consumo = BigDecimal.valueOf(getConsumo());
        BigDecimal autonomiaAbastecimento = qtd.divide(consumo);
        BigDecimal autonomiaFinal = autonomiaAbastecimento.add(autonomiaInicial);
        
        int autonomiaTotal = autonomiaFinal.intValue();
        setAutonomia(autonomiaTotal);
    }
    
    /**
     * Método responsável por verificar se a autonomia de um
     * veiculo está dentro do intervalo recebido como parâmetro.
     * 
     * @param val1  Intervalo inferior de autonomia.
     * @param val2  Intervalo superior de autonomia.
     * @return boolean true Se estiver dentro do intervalo, false caso contrário.
     */

    public boolean verificaAutonomia(int val1, int val2){
        return getAutonomia() >= val1 && getAutonomia() <= val2;
    }
    
    /**
     * Adiciona datas à lista de datas.
     * @param inicio Data incio.
     * @param fim Data fim.
     */
    public void addDatas(GregorianCalendar inicio, GregorianCalendar fim){
        ParDatas newPair = new ParDatas(inicio,fim);
        this.datasAlugueres.add(newPair);
    }
        
    /**
     * Compara a igualdade com outro objeto.
     * @param obj O objeto a comparar.
     * @return true se forem iguais, false caso contrário.
     */
    public boolean equals(Object obj) {
        if(obj==this) {
			return true;
		}
        if(obj==null || obj.getClass()!=getClass()) {
			return false;
		}
        Veiculo veiculo = (Veiculo) obj;
        return this.marca.equals(veiculo.getMarca()) && this.matricula.equals(veiculo.getMatricula()) &&
               this.nif.equals(veiculo.getNIF()) && this.velocidade == veiculo.getVelocidade() && 
               this.preco == veiculo.getPreco() && this.consumo == veiculo.getConsumo() && 
               this.autonomia == veiculo.getAutonomia() && this.posicao.equals(veiculo.getPosicao()) && 
               this.disponivel == veiculo.getDisponibilidade() && this.classificacao == veiculo.getClassificacao();
    }
    
    /** 
     * Devolve uma representação do objeto em formato textual.
     * @return String Representa o objeto em formato textual.
     */
    public String toString(){
       StringBuilder str = new StringBuilder();
       
       str.append("*****    VE�?CULO    *****\n"); 
       str.append("Marca: "); str.append(this.marca); str.append("\n");
       str.append("Matrícula: "); str.append(this.matricula); str.append("\n");
       str.append("NIF do Proprietário: "); str.append(this.nif); str.append("\n");
       str.append("Velocidade Média: "); str.append(this.velocidade); str.append("\n");
       str.append("Preço Base por Km: "); str.append(this.preco); str.append("\n");
       str.append("Consumo: "); str.append(this.consumo); str.append("\n");
       str.append("Autonomia: "); str.append(this.autonomia); str.append("\n");
       str.append("Posição (latitude e longitude): "); str.append(this.posicao); str.append("\n");
       str.append("Disponível: "); str.append(this.disponivel); str.append("\n");
       str.append("Classificação: "); str.append(this.classificacao); str.append("\n");
        
       return str.toString();
    }
    
    /**
     * Retorna uma cópia da instância.
     * @return Veiculo Um novo veículo, que é cópia do this.
     */
    public Veiculo clone(){
        return new Veiculo(this);
    }
}