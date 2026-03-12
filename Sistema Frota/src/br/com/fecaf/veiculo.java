package br.com.fecaf;

public class veiculo {
    private final int id;
    private final String modelo;
    private final String marca;
    private final String placa;
    private final int ano;
    private final int quilometragem;
    private final String cor;
    private final String tipo;
    private final int preco;
    private final String status;

    public veiculo(int id, String modelo, String marca, String placa, int ano, int quilometragem, String cor, String tipo, int preco, String status) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.placa = placa;
        this.ano = ano;
        this.quilometragem = quilometragem;
        this.cor = cor;
        this.tipo = tipo;
        this.preco = preco;
        this.status = status;

    }

    // Getters
    public int getId() { return id; }
    public String getModelo() { return modelo; }
    public String getMarca() { return marca; }
    public String getPlaca() { return placa; }
    public int getAno() { return ano; }
    public int getQuilometragem() { return quilometragem; }
    public String getCor() { return cor; }
    public String getTipo() { return tipo; }
    public int getPreco() { return preco; }
    public String getStatus() { return status; }
}

