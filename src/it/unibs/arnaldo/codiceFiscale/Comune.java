package it.unibs.arnaldo.codiceFiscale;

public class Comune {


    //ATTRIBUTI
    private String nome;
    private String codice;

    //Costruttore
    public Comune(String nome, String codice) {
        this.nome = nome;
        this.codice = codice;
    }

    //Getters and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    //ToString
    @Override
    public String toString() {
        return "Comune{" +
                "nome='" + nome + '\'' +
                ", codice='" + codice + '\'' +
                '}';
    }


}
