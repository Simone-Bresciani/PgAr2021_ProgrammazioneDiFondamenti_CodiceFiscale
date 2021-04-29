package it.unibs.arnaldo.codiceFiscale;


import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
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
                        System.out.println("Start Read Doc " + filename); break;
                    case XMLStreamConstants.START_ELEMENT: // inizio di un elemento: stampa il nome del tag e i suoi attributi
                        System.out.println("Tag " + xmlreader.getLocalName());

                        switch(xmlreader.getLocalName()){
                            case TAG_PERSONA: persona=new Persona(); break;
                            default: tag_attuale=xmlreader.getLocalName(); break;//si potrebbe migliorare sempre per sicurezza con i case..
                        }

                        for (int i = 0; i < xmlreader.getAttributeCount(); i++) {
                            System.out.printf(" => attributo %s->%s%n", xmlreader.getAttributeLocalName(i), xmlreader.getAttributeValue(i));
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT: // fine di un elemento: stampa il nome del tag chiuso
                        System.out.println("END-Tag " + xmlreader.getLocalName());
                        tag_attuale=xmlreader.getLocalName();
                        if(tag_attuale.equals(TAG_PERSONA)){
                            persone.add(persona);
                        }
                        break;
                    case XMLStreamConstants.COMMENT:
                        System.out.println("// commento " + xmlreader.getText()); break; // commento: ne stampa il contenuto
                    case XMLStreamConstants.CHARACTERS: // content all’interno di un elemento: stampa il testo
                        if (xmlreader.getText().trim().length() > 0){
                            // controlla se il testo non contiene solo spazi
                            System.out.println("-> " + xmlreader.getText());

                            switch (tag_attuale){
                                case TAG_NOME: persona.setNome(xmlreader.getText()); break;
                                case TAG_CONOME: persona.setCognome(xmlreader.getText()); break;
                                case TAG_SESSO: char sex=xmlreader.getText().charAt(0);
                                                persona.setSesso(sex);
                                                break;
                                case TAG_COMUNE: comune=new Comune();persona.setComune_di_nascita(comune); persona.getComune_di_nascita().setNome(xmlreader.getText()); break;
                                case TAG_DATA: persona.setData_di_nascita(xmlreader.getText()); break;
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
        ArrayList<Comune> comuni=leggiComuni("C:\\Users\\Mattia\\Desktop\\UNIVERSITA'\\1°ANNO-2°SEMESTRE\\JAVA,FONDAMENTI PROGRAMMAZIONE\\ARNALDO\\PgAr2021_ProgrammazioneDiFondamenti_CodiceFiscale\\src\\it\\unibs\\arnaldo\\codiceFiscale\\comuni.xml");

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

    private static ArrayList<Comune> leggiComuni(String filename){
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
                        System.out.println("Start Read Doc " + filename); break;
                    case XMLStreamConstants.START_ELEMENT: // inizio di un elemento: stampa il nome del tag e i suoi attributi
                        System.out.println("Tag " + xmlreader.getLocalName());

                        switch(xmlreader.getLocalName()){
                            case TAG_COMUNI: comune=new Comune(); break;
                            default: tag_attuale=xmlreader.getLocalName(); break;//si potrebbe migliorare sempre per sicurezza con i case..
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
                        System.out.println("// commento " + xmlreader.getText()); break; // commento: ne stampa il contenuto
                    case XMLStreamConstants.CHARACTERS: // content all’interno di un elemento: stampa il testo
                        if (xmlreader.getText().trim().length() > 0){
                            // controlla se il testo non contiene solo spazi
                            System.out.println("-> " + xmlreader.getText());

                            switch (tag_attuale){
                                case TAG_NOME: comune.setNome(xmlreader.getText()); break;
                                case TAG_CODICE_COMUNE: comune.setCodice(xmlreader.getText()); break;
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
}
