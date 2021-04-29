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

    private boolean controllaNomeCognome(){
        char[] lettere_nome = nome.toCharArray();
        if( (ControlloDati.seVocale(lettere_nome[0]) || ControlloDati.seConsonante(lettere_nome[0])) &&
                (ControlloDati.seVocale(lettere_nome[1]) || ControlloDati.seConsonante(lettere_nome[1])) &&
                (ControlloDati.seVocale(lettere_nome[2]) || ControlloDati.seConsonante(lettere_nome[2])) &&
                (ControlloDati.seVocale(lettere_nome[3]) || ControlloDati.seConsonante(lettere_nome[3]))){
            return true;
        }else
            return false;
    }

    private boolean controllaMeseDiNascita(){
        return mese_di_nascita == 'A' || mese_di_nascita == 'B' || mese_di_nascita == 'C' ||
               mese_di_nascita == 'D' || mese_di_nascita == 'E' || mese_di_nascita == 'H' ||
               mese_di_nascita == 'L' || mese_di_nascita == 'P' || mese_di_nascita == 'M' ||
               mese_di_nascita == 'R' || mese_di_nascita == 'S' || mese_di_nascita == 'T';
    }

    private boolean controllaAnnoDiNascita(){
        char [] carattere_anno = this.anno_di_nascita.toCharArray();
        return (ControlloDati.seNumero(carattere_anno[0]) && ControlloDati.seNumero(carattere_anno[1]));
    }

    private boolean controllaGiornoDiNascita(){
        char [] carattere_giorno = this.giorno_di_nascita.toCharArray();
        //boolean procedere = false;
        if (!ControlloDati.seNumero(carattere_giorno[0]) || !ControlloDati.seNumero(carattere_giorno[1])){
            return false;
        }
        else {
            int numero = Integer.parseInt(giorno_di_nascita);
            if( numero<1 || (numero > 31 && numero <41) || numero>71){
                return false;
            }
            else {
                //30 giorni a Novembre, con April, Giugno e Settembre...
                if((mese_di_nascita == 'D' || mese_di_nascita == 'H' || mese_di_nascita == 'P' || mese_di_nascita == 'S') && numero > 30) return false;
                //...di 28 ce n'è uno...
                else if(mese_di_nascita == 'B' && numero > 28) return false;
            }
        }
        return true;
    }


    /**
     * Controlla se il primo carattere della stringa è una lettera (un non numero) e se i 3 caratteri successivi sono numeri
     * @return
     */
    private boolean controllaLuogoDiNascita(){
        char [] caratteri_luogo = this.luogo_di_nascita.toCharArray(); //questo è un vettore lungo 4 che contiene 1 char e un 3 char che corrispondono a interi
        return (!ControlloDati.seNumero(caratteri_luogo[0]) && ControlloDati.seNumero(caratteri_luogo[1]) &&
                ControlloDati.seNumero(caratteri_luogo[2]) && ControlloDati.seNumero(caratteri_luogo[3]));

    }

    private boolean controllaControllo(){
        //creo una stringa senza l'ultimo carattere del controllo
        String stringa_senza_controllo = cognome + nome + anno_di_nascita + mese_di_nascita + giorno_di_nascita + luogo_di_nascita;
        //ora calcolo il carattere di controllo, poi lo comparerò con quello corrente per vedere se combaciano, in caso sarà valido
        char carattere_controllo = calcolaCarattereControllo(stringa_senza_controllo);
        if(carattere_controllo == this.controllo){
            return true;
        }else return false;
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

    /*
     * Metodo privato utilizzato in concomitanza con calcolaCodiceFiscale().
     * Il suo scopo è quello di determinare il carattere di controllo presente all'interno di ogni codice
     * fiscale di un'istanza di classe Persona(di un individuo) come ultimo carattere/lettera.
     * Ritorna una stringa di un carattere./un char
     */
    public static char calcolaCarattereControllo(String codice_fiscale_senza_controllo){
        char [] codice_15_caratteri = codice_fiscale_senza_controllo.toCharArray();
        int resto, somma=0;
        for (int i=0; i<Costanti.DIM_CODICE_FISCALE-1; i++){
            if(i%2 == 0){
                somma += valoreControlloPari(codice_15_caratteri[i]);
            }else if(i%2 != 0){
                somma += valoreControlloDispari(codice_15_caratteri[i]);
            }
        }
        resto = somma%26;
        char carattere_controllo = valoreCarattereControllo(resto);
        return carattere_controllo;
    }

    /*
     * Metodo privato utilizzato in concomitanza con calcolaCarattereControllo().
     * Il suo scopo è quello di determinare l'intero corrispondente ad un determinato char in posizione
     * pari all'interno del "futuro" codice fiscale secondo una tabulazione fissata dal Ministero delle
     * Finanze Italiano.
     * Ritorna un intero necessario per la determinazione del carattere di controllo.
     */
    private static int valoreControlloPari(char carattere){

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

    /*
     * Metodo privato utilizzato in concomitanza con calcolaCarattereControllo().
     * Il suo scopo è quello di determinare l'intero corrispondente ad un determinato char in posizione
     * dispari all'interno del "futuro" codice fiscale secondo una tabulazione fissata dal Ministero delle
     * Finanze Italiano.
     * Ritorna un intero necessario per la determinazione del carattere di controllo.
     */
    private static int valoreControlloDispari(char carattere){
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

    /*
     * Metodo privato utilizzato in concomitanza con calcolaCarattereControllo().
     * Il suo scopo è quello di determinare il carattere corrispondente ad un determinato resto
     * secondo una tabulazione fissata dal Ministero delle Finanze Italiano.
     * Il suo utilizzo è logicamente corretto se posticipato all'uso di valoreControlloPari e valoreControlloDispari:
     * il resto della divisione ottenuto in calcolaCarattereControllo,fornirà il codice identificativo, ottenuto da
     * una tabella di conversione stabilita.
     * Ritorna un char necessario che determina il carattere di controllo.
     */
    private static char valoreCarattereControllo(int resto){
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
