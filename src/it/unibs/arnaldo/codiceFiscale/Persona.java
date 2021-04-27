package it.unibs.arnaldo.codiceFiscale;

import java.util.ArrayList;
import java.util.Date;

public class Persona {



    //COSTANTI


     //ATTRIBUTI
    private String nome;
    private String cognome;
    private char sesso;
    private Date data_di_nascita;
    private Comune comune_di_nascita;
    private char carattere_controllo;
    private String codice_fiscale;

    //costruttore
    public Persona(String nome, String cognome, char sesso, Date data_di_nascita, Comune comune_di_nascita, char carattere_controllo, String codice_fiscale) {
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.data_di_nascita = data_di_nascita;
        this.comune_di_nascita = comune_di_nascita;
        this.carattere_controllo = carattere_controllo;
        this.codice_fiscale = codice_fiscale;
    }

    public Persona(String _nome){
        this.nome=_nome;
    }

    //Getters and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public char getSesso() {
        return sesso;
    }

    public void setSesso(char sesso) {
        this.sesso = sesso;
    }

    public Date getData_di_nascita() {
        return data_di_nascita;
    }

    public void setData_di_nascita(Date data_di_nascita) {
        this.data_di_nascita = data_di_nascita;
    }

    public Comune getComune_di_nascita() {
        return comune_di_nascita;
    }

    public void setComune_di_nascita(Comune comune_di_nascita) {
        this.comune_di_nascita = comune_di_nascita;
    }

    public char getCarattere_controllo() {
        return carattere_controllo;
    }

    public void setCarattere_controllo(char carattere_controllo) {
        this.carattere_controllo = carattere_controllo;
    }

    public String getCodice_fiscale() {
        return codice_fiscale;
    }

    public void setCodice_fiscale(String codice_fiscale) {
        this.codice_fiscale = codice_fiscale;
    }

    //ToString
    @Override
    public String toString() {
        return "Persona{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", sesso=" + sesso +
                ", data_di_nascita=" + data_di_nascita +
                ", comune_di_nascita=" + comune_di_nascita +
                ", carattere_controllo=" + carattere_controllo +
                ", codice_fiscale='" + codice_fiscale + '\'' +
                '}';
    }

    public String calcolaCodiceFiscale(){
        String codice = "";
        codice += calcolaLettereCognome();
        codice += calcolaLettereNome();
        codice += calcolaCaratteriNascita();
        codice += calcolaCaratteriLuogo();
        codice += calcolaCarattereControllo();
        return codice;
    }

    private String calcolaLettereCognome(){
        String cognome_maiuscolo = this.cognome.toUpperCase();
        int lunghezza_cognome = cognome.length();
        char [] cognome = cognome_maiuscolo.toCharArray();
        String lettere_cognome="XXX";
        int numero_lettere = 0;

        //in questo primo ciclo cerco di riempire le 3 lettere con delle consonanti
        for (int i=0; i<lunghezza_cognome && numero_lettere != 3; i++){
            if(seConsonante(cognome[i]) && !seNumero(cognome[i])){
                lettere_cognome += cognome[i];
                numero_lettere++;
            }
        }

        if(numero_lettere != 3){

        }
        return "";
    }

    public String calcolaLettereNome(){
        int num_consonanti=0;
        String lettere_nome="";
        String nome_maiuscolo=this.nome.toUpperCase();

        //variabile stringa di supporto
        String consonanti_trovate="";

        //conto quante consonanti sono presenti nel nome
        for(int i=0;i<nome_maiuscolo.length();i++){
            if(this.seConsonante(nome_maiuscolo.charAt(i))){//se è una consonante
                consonanti_trovate=consonanti_trovate.concat(""+nome_maiuscolo.charAt(i));
                num_consonanti+=1;//num_consonanti++;
            }
        }

       // return consonanti_trovate;
        //se il numero di consonanti è maggiore o uguale a 4 allora si prende la prima ,la terza e la quarta consonante
        if(num_consonanti>=4){
           lettere_nome=lettere_nome.concat(""+ consonanti_trovate.charAt(0)+ consonanti_trovate.charAt(2)+ consonanti_trovate.charAt(3));
        }

        //se il numero di consonanti è esattamente 3 si prendono le prime tre consonanti in ordine
        if(num_consonanti==3){
            //lettere_nome=lettere_nome.concat(""+this.nome.charAt(0)+this.nome.charAt(1)+this.nome.charAt(2));
            lettere_nome= consonanti_trovate;
        }


        if(num_consonanti<3){
           int num_vocali_necessarie=3-num_consonanti;

           lettere_nome= consonanti_trovate;

           for(int volte=0,j=0;volte<num_vocali_necessarie && j<nome_maiuscolo.length();j++) {//j+=1
                if(this.seVocale(nome_maiuscolo.charAt(j))){
                    lettere_nome=lettere_nome.concat(""+nome_maiuscolo.charAt(j));
                    volte+=1;//volte++
                }
           }
        }

        if(nome_maiuscolo.length()<3){
            int numero_x_necessarie=3-nome_maiuscolo.length();

            lettere_nome=nome_maiuscolo;

            for(int t=0;t<numero_x_necessarie;t++){
                lettere_nome=lettere_nome.concat("X");
            }
        }

        return lettere_nome;
    }

    /**
     *
     * @return la stringa che corrisponde alla data di nascita tenendo in considerazione il sesso dell'individuo
     */
    private String calcolaCaratteriNascita(){
        String data_di_nascita = "";
        int anno_di_nascita = this.data_di_nascita.getYear();   //getYear prende la data e sottrae 1900
        int mese_di_nascita = this.data_di_nascita.getMonth(); //getMonth ritorna un numero tra 0 e 11 rappresentante il mese
        int giorno_di_nascita = this.data_di_nascita.getDate(); //getDate ritorna un numero tra 1 e 31 rappresentante il giorno
        char sesso = this.sesso;

        if (anno_di_nascita >= 100) anno_di_nascita -= 100;  //se va oltre il secolo allora sottrae 100
        if (anno_di_nascita <= 9 ) data_di_nascita += "0" + anno_di_nascita; //se rimane una cifra aggiunge uno zero
        else data_di_nascita += anno_di_nascita; //altrimenti stampa le due cifre significative rimaste

        /*
        Aggiunta della lettera corretta corrispondente al mese di nascita
         */
        if (mese_di_nascita == 0) data_di_nascita += "A";
        else if (mese_di_nascita == 1) data_di_nascita += "B";
        else if (mese_di_nascita == 2) data_di_nascita += "C";
        else if (mese_di_nascita == 3) data_di_nascita += "D";
        else if (mese_di_nascita == 4) data_di_nascita += "E";
        else if (mese_di_nascita == 5) data_di_nascita += "H";
        else if (mese_di_nascita == 6) data_di_nascita += "L";
        else if (mese_di_nascita == 7) data_di_nascita += "M";
        else if (mese_di_nascita == 8) data_di_nascita += "P";
        else if (mese_di_nascita == 9) data_di_nascita += "R";
        else if (mese_di_nascita == 10) data_di_nascita += "S";
        else if (mese_di_nascita == 11) data_di_nascita += "T";


        if (sesso == 'M') {
            if (giorno_di_nascita <= 9) data_di_nascita += "0";   //se minore di 10 aggiunge uno zero
            data_di_nascita += giorno_di_nascita;
        }
        else if (sesso == 'F'){
            giorno_di_nascita += Costanti.NUMERO_DONNA;    //se si tratta di una donna allora si aggiunge 40 al giorno
            data_di_nascita += giorno_di_nascita;
        }

        return data_di_nascita;
    }

    private String calcolaCaratteriLuogo(){
        return "";
    }

    private String calcolaCarattereControllo(){
        return "";
    }

    public boolean confrontaCodiciFiscali(String codice_da_comparare){
        return false;
    }

    private boolean seVocale(char lettera) {
        switch (lettera) {
            case 'A':
            case 'E':
            case 'I':
            case 'O':
            case 'U':
                return true;
            default:return false;
        }
    }

    private boolean seConsonante(char lettera){
        switch(lettera){
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'G':
            case 'H':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
                return true;
            default: return false;//se non è una consonante
        }
    }

    private boolean seNumero(char lettera){
        if(Character.isDigit(lettera))
            return true;
        return false;
    }
}
