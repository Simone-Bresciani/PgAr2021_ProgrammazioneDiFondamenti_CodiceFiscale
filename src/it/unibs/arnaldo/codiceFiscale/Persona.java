package it.unibs.arnaldo.codiceFiscale;

import mylib.ControlloDati;

import java.util.ArrayList;
import java.util.Date;

public class Persona {

    //COSTANTI
    public static final int DIM_COGNOME_NOME = 3;
    public static final int DIM_CODICE_FISCALE = 16;

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
        codice += calcolaCarattereControllo(codice);
        return codice;
    }

    private String calcolaLettereCognome(){

        //rendo maiuscole le lettere del cognome
        String cognome_maiuscolo = this.cognome.toUpperCase();

        //creo un array di caratteri contenente le lettere del cognome
        char [] cognome = cognome_maiuscolo.toCharArray();

        //creo un array di caratteri, con le lettere che rappresenteranno il cognome
        char [] lettere_cognome = new char[DIM_COGNOME_NOME];

        //inizializzo l'array di caratteri con le lettere di default
        lettere_cognome[0] = 'X';
        lettere_cognome[1] = 'X';
        lettere_cognome[2] = 'X';

        //creo un contatore che conta le lettere adatte per rappresentare il cognome
        int numero_lettere = 0;

        //in questo primo ciclo cerco di riempire le 3 lettere con delle consonanti
        for (int i=0; i<cognome.length && numero_lettere != 3; i++){
            if(ControlloDati.seConsonante(cognome[i])){
                lettere_cognome[numero_lettere] = cognome[i];
                numero_lettere++;
            }
        }
        //se le consonanti sono meno di 3, passo alle vocali
        for(int i=0; i<cognome.length && numero_lettere != 3; i++){
            if(ControlloDati.seVocale(cognome[i])){
                lettere_cognome[numero_lettere] = cognome[i];
                numero_lettere++;
            }
        }
        //infine creo la stringa finale da passare
        String tre_lettere = "";
        //itero il ciclo 3 volte
        for (int i=0; i<DIM_COGNOME_NOME; i++)
            //accodo alla stringa le lettere
            tre_lettere += lettere_cognome[i];
        return tre_lettere;
    }

    public String calcolaLettereNome(){
        int num_consonanti=0;
        String lettere_nome="";
        String nome_maiuscolo=this.nome.toUpperCase();

        //variabile stringa di supporto
        String consonanti_trovate="";

        //conto quante consonanti sono presenti nel nome
        for(int i=0;i<nome_maiuscolo.length();i++){
            if(ControlloDati.seConsonante(nome_maiuscolo.charAt(i))){//se è una consonante
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
                if(ControlloDati.seVocale(nome_maiuscolo.charAt(j))){
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

    private String calcolaCarattereControllo(String codice_fiscale_senza_controllo){
        char [] codice_15_caratteri = codice_fiscale_senza_controllo.toCharArray();
        int resto, somma=0;
        for (int i=0; i<DIM_CODICE_FISCALE-1; i++){
            if(i%2 == 0){
                somma += valoreControlloPari(codice_15_caratteri[i]);
            }else if(i%2 != 0){
                somma += valoreControlloDispari(codice_15_caratteri[i]);
            }
        }
        resto = somma%26;
        char carattere_controllo = valoreCarattereControllo(resto);
        String carattere_finale = "";
        carattere_finale += carattere_controllo;
        return carattere_finale;
    }

    public boolean confrontaCodiciFiscali(String codice_da_comparare){
        return false;
    }

    private int valoreControlloPari(char carattere){

        if(Character.isDigit(carattere)){
            return (int)carattere;
        }

        switch (carattere){

            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case 'G':
                return 6;
            case 'H':
                return 7;
            case 'I':
                return 8;
            case 'J':
                return 9;
            case 'K':
                return 10;
            case 'L':
                return 11;
            case 'M':
                return 12;
            case 'N':
                return 13;
            case 'O':
                return 14;
            case 'P':
                return 15;
            case 'Q':
                return 16;
            case 'R':
                return 17;
            case 'S':
                return 18;
            case 'T':
                return 19;
            case 'U':
                return 20;
            case 'V':
                return 21;
            case 'W':
                return 22;
            case 'X':
                return 23;
            case 'Y':
                return 24;
            case 'Z':
                return 25;
            default: return -1;
        }
    }
    private int valoreControlloDispari(char carattere){
        switch (carattere){
            case 'A':
            case '0':
                return 1;
            case 'B':
            case '1':
                return 0;
            case 'C':
            case '2':
                return 5;
            case 'D':
            case '3':
                return 7;
            case 'E':
            case '4':
                return 9;
            case 'F':
            case '5':
                return 13;
            case 'G':
            case '6':
                return 15;
            case 'H':
            case '7':
                return 17;
            case 'I':
            case'8':
                return 19;
            case 'J':
            case '9':
                return 21;
            case 'K':
                return 2;
            case 'L':
                return 4;
            case 'M':
                return 18;
            case 'N':
                return 20;
            case 'O':
                return 11;
            case 'P':
                return 3;
            case 'Q':
                return 6;
            case 'R':
                return 8;
            case 'S':
                return 12;
            case 'T':
                return 14;
            case 'U':
                return 16;
            case 'V':
                return 10;
            case 'W':
                return 22;
            case 'X':
                return 25;
            case 'Y':
                return 24;
            case 'Z':
                return 23;
            default: return -1;
        }
    }
    private char valoreCarattereControllo(int resto){
        switch (resto){
            case 0:
                return 'A';
            case 1:
                return 'B';
            case 2:
                return 'C';
            case 3:
                return 'D';
            case 4:
                return 'E';
            case 5:
                return 'F';
            case 6:
                return 'G';
            case 7:
                return 'H';
            case 8:
                return 'I';
            case 9:
                return 'J';
            case 10:
                return 'K';
            case 11:
                return 'L';
            case 12:
                return 'M';
            case 13:
                return 'N';
            case 14:
                return 'O';
            case 15:
                return 'P';
            case 16:
                return 'Q';
            case 17:
                return 'R';
            case 18:
                return 'S';
            case 19:
                return 'T';
            case 20:
                return 'U';
            case 21:
                return 'V';
            case 22:
                return 'W';
            case 23:
                return 'X';
            case 24:
                return 'Y';
            case 25:
                return 'Z';
            default: return '0';
        }
    }
}
