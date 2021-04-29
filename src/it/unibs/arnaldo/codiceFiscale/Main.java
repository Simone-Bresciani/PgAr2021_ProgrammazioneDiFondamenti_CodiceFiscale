package it.unibs.arnaldo.codiceFiscale;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        //Creo un array di persone mediante il metodo di lettura dell'Xml (al suo interno é giá presente il metodo di lettura dei comuni che andr)
        ArrayList<Persona> persone = ServizioFileXml.leggiPersone("../PgAr2021_ProgrammazioneDiFondamenti_CodiceFiscale/src/it/unibs/arnaldo/codiceFiscale/inputPersone.xml");


        //Creo un array di stringhe di codici fiscali mediante il metodo della lettura dell'Xml
        ArrayList<String> codici_fiscali = ServizioFileXml.leggiCodiciFiscali("../PgAr2021_ProgrammazioneDiFondamenti_CodiceFiscale/src/it/unibs/arnaldo/codiceFiscale/codiciFiscali.xml");


        ServizioFileXml.scritturaOutput(persone, codici_fiscali);

        //METODO CHE DOVREMO SCRIVERE NEL XML WRITER
        /*
        for(int i=0;i<persone.size();i++){
            for(int j=0; j<codici_fiscali.size(); j++){
                if(persone.get(i).confrontaCodiciFiscali(codici_fiscali.get(j)){
                    persone.get(i).set
                }
            }


        }
        */

        //STAMPA ARRAYLIST PERSONA
        /*
        System.out.println(persone.size());
        for(int i=0;i<persone.size();i++){
            System.out.println("Nome: "+persone.get(i));
        }*/

    }
}
