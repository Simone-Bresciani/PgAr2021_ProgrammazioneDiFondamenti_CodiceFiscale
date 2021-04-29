package it.unibs.arnaldo.codiceFiscale;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //Persona persona=new Persona("Ermenegildo");//funziona anhce caso particolare con aggiunta X
        //System.out.println(persona.calcolaLettereNome());
        //Persona persona= new Persona('M', "2002-01-01");
        //System.out.println(persona.calcolaCaratteriNascita());

        ArrayList<Persona> persone=ServizioFileXml.leggiPersone("C:\\Users\\Mattia\\Desktop\\UNIVERSITA'\\1°ANNO-2°SEMESTRE\\JAVA,FONDAMENTI PROGRAMMAZIONE\\ARNALDO\\PgAr2021_ProgrammazioneDiFondamenti_CodiceFiscale\\src\\it\\unibs\\arnaldo\\codiceFiscale\\inputPersone.xml");
        //ArrayList<Comune> comuni=ServizioFileXml.leggiComuni("C:\\Users\\Mattia\\Desktop\\UNIVERSITA'\\1°ANNO-2°SEMESTRE\\JAVA,FONDAMENTI PROGRAMMAZIONE\\ARNALDO\\PgAr2021_ProgrammazioneDiFondamenti_CodiceFiscale\\src\\it\\unibs\\arnaldo\\codiceFiscale\\comuni.xml");

        System.out.println(persone.size());
        for(int i=0;i<persone.size();i++){
            System.out.println("Nome: "+persone.get(i));
        }

        /*for(int j=0;j<comuni.size();j++){
            System.out.println("Nome: "+comuni.get(j));
        }*/
    }
}
