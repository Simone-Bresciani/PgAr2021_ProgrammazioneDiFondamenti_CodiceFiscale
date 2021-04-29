package it.unibs.arnaldo.codiceFiscale;

import mylib.ControlloDati;

public class CodiceFiscale {


    private String cognome;
    private String nome;
    private String anno_di_nascita;
    private char mese_di_nascita;
    private String giorno_di_nascita;
    private String luogo_di_nascita;
    private char controllo;


//GETTERS AND SETTERS

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAnno_di_nascita() {
        return anno_di_nascita;
    }

    public void setAnno_di_nascita(String anno_di_nascita) {
        this.anno_di_nascita = anno_di_nascita;
    }

    public char getMese_di_nascita() {
        return mese_di_nascita;
    }

    public void setMese_di_nascita(char mese_di_nascita) {
        this.mese_di_nascita = mese_di_nascita;
    }

    public String getGiorno_di_nascita() {
        return giorno_di_nascita;
    }

    public void setGiorno_di_nascita(String giorno_di_nascita) {
        this.giorno_di_nascita = giorno_di_nascita;
    }

    public String getLuogo_di_nascita() {
        return luogo_di_nascita;
    }

    public void setLuogo_di_nascita(String luogo_di_nascita) {
        this.luogo_di_nascita = luogo_di_nascita;
    }

    public char getControllo() {
        return controllo;
    }

    public void setControllo(char controllo) {
        this.controllo = controllo;
    }

    //COSTRUTTORE
    public CodiceFiscale(String cognome, String nome, String anno_di_nascita, char mese_di_nascita, String giorno_di_nascita, String luogo_di_nascita, char controllo) {
        this.cognome = cognome;
        this.nome = nome;
        this.anno_di_nascita = anno_di_nascita;
        this.mese_di_nascita = mese_di_nascita;
        this.giorno_di_nascita = giorno_di_nascita;
        this.luogo_di_nascita = luogo_di_nascita;
        this.controllo = controllo;
    }

    public boolean validaCodiceFiscale(){
        return (controllaNomeCognome() && controllaAnnoDiNascita() && controllaMeseDiNascita() &&
                controllaGiornoDiNascita() && controllaLuogoDiNascita() && controllaControllo() );
    }







    private boolean controllaAnnoDiNascita(){

    }


    //TOSTRING
    @Override
    public String toString() {
        return "CodiceFiscale{" +
                "cognome='" + cognome + '\'' +
                ", nome='" + nome + '\'' +
                ", anno_di_nascita=" + anno_di_nascita +
                ", mese_di_nascita=" + mese_di_nascita +
                ", giorno_di_nascita=" + giorno_di_nascita +
                ", luogo_di_nascita='" + luogo_di_nascita + '\'' +
                ", controllo=" + controllo +
                '}';
    }


}
