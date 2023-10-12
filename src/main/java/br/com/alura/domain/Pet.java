package br.com.alura.domain;

public class Pet {

    public Pet() {

    }

    public Pet(String tipo, String nome, String raca, int idade, String cor, Float peso) {
        this.tipo = tipo;
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.cor = cor;
        this.peso = peso;
    }

    private Long id;
    private String tipo;
    private String nome;
    private String raca;
    private int idade;
    private String cor;
    private Float peso;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getRaca() {
        return raca;
    }

    public String getTipo() {
        return tipo;
    }

    public int getIdade() {
        return idade;
    }

    public Float getPeso() {
        return peso;
    }

    public String getCor() {
        return cor;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return """
                     "id":%s,"nome":"%s","raca":"%s","tipo":"%s","idade":%s,"cor":"%s","peso":%s
                     """.formatted(this.id, this.nome, this.raca, this.tipo, this.idade, this.cor, this.peso);
    }
}