package it.unibs.arnaldo.codiceFiscale;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.*;
import java.io.*;
import java.util.ArrayList;

public class ServizioFileXml {

    //Costanti
    private static final String ERRORE_INIZIALIZZAZIONE_READER="Errore nell'inizializzazione del reader: ";
    private static final String ERRORE_LETTURA="Errore durante la lettura del file %s.Per ulteriori informazioni: ";
    private static final String TAG_PERSONA="persona";
    private static final String TAG_NOME="nome";
    private static final String TAG_CONOME="cognome";
    private static final String TAG_SESSO="sesso";
    private static final String TAG_COMUNE="comune_nascita";
    private static final String TAG_DATA="data_nascita";
    private static final String TAG_COMUNI="comune";
    private static final String TAG_CODICE_COMUNE="codice";
    private static final String TAG_CODICE_FISCALE = "codice";

    public static ArrayList<Persona> leggiPersone(String filename){
        XMLInputFactory xmlif = null;
        XMLStreamReader xmlreader = null;
        ArrayList<Persona> persone=new ArrayList<Persona>();
        String tag_attuale=new String("");
        Persona persona=null;
        Comune comune;

        //try catch per gestire eventuali eccezioni durante l'inizializzazione
        try{
            xmlif=XMLInputFactory.newInstance();
            xmlreader= xmlif.createXMLStreamReader(filename, new FileInputStream(filename));
        }catch(Exception e){
            System.out.println(ERRORE_INIZIALIZZAZIONE_READER);
            System.out.println(e.getMessage());
        }

        //try catch per gestire evemtuali eccezioni durante la lettura del file XML contenente dei dati relativi ad una persona
        try{
            while (xmlreader.hasNext()) { // continua a leggere finché ha eventi a disposizione
                switch (xmlreader.getEventType()) { // switch sul tipo di evento
                    case XMLStreamConstants.START_DOCUMENT: // inizio del documento: stampa che inizia il documento
                        System.out.println("Start Read Doc " + filename);
                    break;
                    case XMLStreamConstants.START_ELEMENT: // inizio di un elemento: stampa il nome del tag e i suoi attributi
                        System.out.println("Tag " + xmlreader.getLocalName());

                        switch(xmlreader.getLocalName()){
                            case TAG_PERSONA:
                                persona=new Persona();
                            break;
                            default: tag_attuale = xmlreader.getLocalName();
                            break;
                        }

                        for (int i = 0; i < xmlreader.getAttributeCount(); i++) {
                            System.out.printf(" => attributo %s->%s%n", xmlreader.getAttributeLocalName(i), xmlreader.getAttributeValue(i));
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT: // fine di un elemento: stampa il nome del tag chiuso
                        System.out.println("END-Tag " + xmlreader.getLocalName());
                        tag_attuale=xmlreader.getLocalName();
                        if(tag_attuale.equals(TAG_PERSONA)){
                            //aggiunta di una persona all'arraylist
                            persone.add(persona);
                        }
                        break;
                    case XMLStreamConstants.COMMENT:
                        System.out.println("// commento " + xmlreader.getText());
                    break; // commento: ne stampa il contenuto
                    case XMLStreamConstants.CHARACTERS: // content all’interno di un elemento: stampa il testo
                        if (xmlreader.getText().trim().length() > 0){
                            // controlla se il testo non contiene solo spazi
                            System.out.println("-> " + xmlreader.getText());

                            switch (tag_attuale){
                                case TAG_NOME:
                                    persona.setNome(xmlreader.getText());
                                break;
                                case TAG_CONOME:
                                    persona.setCognome(xmlreader.getText());
                                break;
                                case TAG_SESSO:
                                    char sex=xmlreader.getText().charAt(0);
                                    persona.setSesso(sex);
                                break;
                                case TAG_COMUNE:
                                    comune=new Comune();
                                    persona.setComune_di_nascita(comune);
                                    persona.getComune_di_nascita().setNome(xmlreader.getText());
                                break;
                                case TAG_DATA:
                                    persona.setData_di_nascita(xmlreader.getText());
                                break;
                            }

                            tag_attuale="";
                        }
                        break;
                }
                xmlreader.next();
            }
        }catch(XMLStreamException e){
            System.out.println(String.format(ERRORE_LETTURA,filename));
            System.out.println(e.getMessage());
        }

        //dato che la classe Persona aggrega Comune (solamente 1-->associazione semplice/aggregazione) e quest'ultima è caratterizzata da
        ArrayList<Comune> comuni=leggiComuni("../PgAr2021_ProgrammazioneDiFondamenti_CodiceFiscale/src/it/unibs/arnaldo/codiceFiscale/comuni.xml");

        for(Persona persona_da_valutare : persone){
            for(int i=0;i<comuni.size();i++){
                /*non posso utilizzare equals/overridare equals perché comune è costituito da nome e codice e nel file inputPersone quest'ultima informazione non esiste
                * allora cerco una corrispondenza tra il comune di nascita della persona e la lista di comuni a disposizione
                */
                if(persona_da_valutare.getComune_di_nascita().getNome().equals(comuni.get(i).getNome())){
                    persona_da_valutare.getComune_di_nascita().setCodice(comuni.get(i).getCodice());
                }
            }
        }

        return persone;
    }

    public static ArrayList<String> leggiCodiciFiscali(String filename){

        XMLInputFactory xmlif = null;
        XMLStreamReader xmlreader = null;
        ArrayList<String> codici_fiscali = new ArrayList<String>();
        String codice_fiscale = null;

        //inizializzazione
        try{
            xmlif=XMLInputFactory.newInstance();
            xmlreader= xmlif.createXMLStreamReader(filename, new FileInputStream(filename));
        }catch(Exception e){
            System.out.println(ERRORE_INIZIALIZZAZIONE_READER);
            System.out.println(e.getMessage());
        }

        try{
            while (xmlreader.hasNext()){

                switch (xmlreader.getEventType()) {

                    case XMLStreamConstants.START_DOCUMENT:
                        System.out.println("Inizio lettura file: " + filename);
                        break;

                    case XMLStreamConstants.END_DOCUMENT:
                        System.out.println("Chiusura file: " + filename);
                        break;

                    case XMLStreamConstants.START_ELEMENT:
                        if (xmlreader.getLocalName() == TAG_CODICE_FISCALE) {
                            System.out.print("codice: ");
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        if (xmlreader.getText().trim().length() > 0) {
                            codice_fiscale = xmlreader.getText();
                            codici_fiscali.add(codice_fiscale);
                            System.out.println(codice_fiscale);
                        }
                    break;

                    case XMLStreamConstants.END_ELEMENT:
                        System.out.println("chiusura elemento");
                        break;
                }
                xmlreader.next();
            }

        }catch(XMLStreamException e){
            System.out.println(String.format(ERRORE_LETTURA,filename));
            System.out.println(e.getMessage());
        }
        return codici_fiscali;
    }

    public static ArrayList<Comune> leggiComuni(String filename){
        XMLInputFactory xmlif = null;
        XMLStreamReader xmlreader = null;
        ArrayList<Comune> comuni=new ArrayList<Comune>();
        String tag_attuale=new String("");
        Comune comune=null;

        //try catch per gestire eventuali eccezioni durante l'inizializzazione
        try{
            xmlif=XMLInputFactory.newInstance();
            xmlreader= xmlif.createXMLStreamReader(filename, new FileInputStream(filename));
        }catch(Exception e){
            System.out.println(ERRORE_INIZIALIZZAZIONE_READER);
            System.out.println(e.getMessage());
        }

        //try catch per gestire evemtuali eccezioni durante la lettura del file XML contenente dei dati relativi ad una persona
        try{
            while (xmlreader.hasNext()) { // continua a leggere finché ha eventi a disposizione
                switch (xmlreader.getEventType()) { // switch sul tipo di evento
                    case XMLStreamConstants.START_DOCUMENT: // inizio del documento: stampa che inizia il documento
                        System.out.println("Start Read Doc " + filename);
                    break;
                    case XMLStreamConstants.START_ELEMENT: // inizio di un elemento: stampa il nome del tag e i suoi attributi
                        System.out.println("Tag " + xmlreader.getLocalName());

                        switch(xmlreader.getLocalName()){
                            case TAG_COMUNI:
                                comune=new Comune();
                            break;
                            default: tag_attuale=xmlreader.getLocalName();
                            break;//si potrebbe migliorare sempre per sicurezza con i case..
                        }

                        for (int i = 0; i < xmlreader.getAttributeCount(); i++) {
                            System.out.printf(" => attributo %s->%s%n", xmlreader.getAttributeLocalName(i), xmlreader.getAttributeValue(i));
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT: // fine di un elemento: stampa il nome del tag chiuso
                        System.out.println("END-Tag " + xmlreader.getLocalName());
                        tag_attuale=xmlreader.getLocalName();
                        if(tag_attuale.equals(TAG_COMUNI)){
                            comuni.add(comune);
                        }
                        break;
                    case XMLStreamConstants.COMMENT:
                        System.out.println("// commento " + xmlreader.getText());
                    break; // commento: ne stampa il contenuto
                    case XMLStreamConstants.CHARACTERS: // content all’interno di un elemento: stampa il testo
                        if (xmlreader.getText().trim().length() > 0){
                            // controlla se il testo non contiene solo spazi
                            System.out.println("-> " + xmlreader.getText());

                            switch (tag_attuale){
                                case TAG_NOME: comune.setNome(xmlreader.getText());
                            break;
                                case TAG_CODICE_COMUNE: comune.setCodice(xmlreader.getText());
                            break;
                            }

                            tag_attuale="";
                        }
                        break;
                }
                xmlreader.next();
            }
        }catch(XMLStreamException e){
            System.out.println(String.format(ERRORE_LETTURA,filename));
            System.out.println(e.getMessage());
        }
        return comuni;
    }



    public static void scritturaOutput(ArrayList<Persona> persone, ArrayList<String> codici_fiscali){

        //inizializzazione
        XMLOutputFactory xmlof = null;
        XMLStreamWriter xmlw = null;
        try {
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream("../PgAr2021_ProgrammazioneDiFondamenti_CodiceFiscale/src/it/unibs/arnaldo/codiceFiscale/codiciPersone.xml"), "utf-8");
            xmlw.writeStartDocument("utf-8", "1.0");
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del writer:");
            System.out.println(e.getMessage());
        }

        try {
            //creo array list per i codici non validi
            ArrayList<String> codici_non_validi = new ArrayList<String>();
            //creo un arraylist per i codici appaiati
            ArrayList<String> codici_appaiati = new ArrayList<String>();
            //creo un arraylist per i codici validi ma non appaiati
            ArrayList<String> codici_non_appaiati = new ArrayList<String>();//spaiati
            //ArrayList<String> codici_assenti=new ArrayList<String>();

            int cont=0;

            xmlw.writeStartElement("output"); // scrittura del tag radice <output>
                xmlw.writeStartElement("persone");
                xmlw.writeAttribute("numero", String.valueOf(persone.size()));
                    for (int i = 0; i < persone.size(); i++) {
                        xmlw.writeStartElement("persona"); //apre persona
                        xmlw.writeAttribute("id", Integer.toString(i)); // attributo persona
                            xmlw.writeStartElement("nome");
                            xmlw.writeCharacters(persone.get(i).getNome());
                            xmlw.writeEndElement();
                            xmlw.writeStartElement("cognome");
                            xmlw.writeCharacters(persone.get(i).getCognome());
                            xmlw.writeEndElement();
                            xmlw.writeStartElement("sesso");
                            xmlw.writeCharacters(String.valueOf(persone.get(i).getSesso()));
                            xmlw.writeEndElement();
                            xmlw.writeStartElement("comune_nascita");
                            xmlw.writeCharacters(persone.get(i).getComune_di_nascita().getNome());
                            xmlw.writeEndElement();
                            xmlw.writeStartElement("data_nascita");
                            xmlw.writeCharacters(persone.get(i).getData_di_nascita());
                            xmlw.writeEndElement();
                            xmlw.writeStartElement("codice_fiscale");
                            int trovato =0; //flag che serve per stabilire quando trovo corrispondenza tra il codice calcolato e quelllo del file

                            for(int j=0; j<codici_fiscali.size() && trovato==0 ; j++){
                                //controlla se il codice calcolato corrisponde ad un codice nel file
                                if(persone.get(i).confrontaCodiciFiscali(codici_fiscali.get(j))) {
                                    //scrive il codice fiscale
                                    xmlw.writeCharacters(persone.get(i).calcolaCodiceFiscale());//codici_fiscali.get(j)
                                    trovato=1;
                                    //aggiungo il codice fiscale all'array list dei codici appaiati
                                    codici_appaiati.add(codici_fiscali.get(j));
                                }
                            }
                            if(trovato == 0){
                                //se il flag per controllare se è stato trovato il codice nel file è uguale a zero, scrive assente
                                //codici_assenti.add(codici_fiscali.get());
                                xmlw.writeCharacters("ASSENTE");
                                cont+=1;//da cancellare
                            }
                            xmlw.writeEndElement();//chiude codice fiscale
                        xmlw.writeEndElement(); // chiude persona
                    }
                xmlw.writeEndElement(); //chiude persone

                //ciclo tutti i codici fiscali per cercare quelli INVALIDI
                 System.out.println("CODICI INVALIDI: ");
                 int j=0;
                for (int i=0; i<codici_fiscali.size(); i++) {
                    //se il codice non è valido lo salvo nell'array
                    //System.out.println(i);
                    if (!CodiceFiscale.validaCodiceFiscale(codici_fiscali.get(i))) {
                        codici_non_validi.add(codici_fiscali.get(i));
                        System.out.println(codici_non_validi.get(j));
                        j+=1;
                    }
                }
                //System.out.println("SIZE: "+codici_non_validi.size());

                //ciclo tutti i codici fiscali per carcare quelli validi ma SPAIATI
                for (int i=0; i<codici_fiscali.size(); i++){
                    //se il codice è valido ma non appartiene ai codici appaiati lo aggiungo all'arraylist di quelli  spaiati
                    if(!codici_non_validi.contains(codici_fiscali.get(i)) && !codici_appaiati.contains(codici_fiscali.get(i))){
                        codici_non_appaiati.add(codici_fiscali.get(i));
                    }
                }
                System.out.println("SIZE di appaiati,non validi,spaiati: "+codici_appaiati.size()+" "+codici_non_validi.size()+" "+codici_non_appaiati.size()+" "+cont);

                xmlw.writeStartElement("codici");//aperura codici
                    xmlw.writeStartElement("invalidi"); // apertura invalidi
                    xmlw.writeAttribute("numero", String.valueOf(codici_non_validi.size()));
                        for (int i=0; i<codici_non_validi.size(); i++){
                            xmlw.writeStartElement("codice"); //apertura codice
                                xmlw.writeCharacters(codici_non_validi.get(i));
                            xmlw.writeEndElement(); //chiusura codice
                        }
                    xmlw.writeEndElement(); //chiusura invalidi

                    xmlw.writeStartElement("spaiati");//apertura spaiati
                    xmlw.writeAttribute("numero", String.valueOf(codici_non_appaiati.size()));
                        for (int i=0; i<codici_non_appaiati.size(); i++){
                            xmlw.writeStartElement("codice"); //apertura codice
                                xmlw.writeCharacters(codici_non_appaiati.get(i));
                            xmlw.writeEndElement(); //chiusura codice
                        }
                    xmlw.writeEndElement();//chiusura spaiati
                xmlw.writeEndElement();//chiusura codici


            xmlw.writeEndElement(); // chiusura di </output>
            xmlw.writeEndDocument(); // scrittura della fine del documento
            xmlw.flush(); // svuota il buffer e procede alla scrittura
            xmlw.close(); // chiusura del documento e delle risorse impiegate
        } catch (XMLStreamException e) { // se c’è un errore viene eseguita questa parte
            System.out.println("Errore nella scrittura");
            System.out.println(e.getMessage());
        }
    }
}
