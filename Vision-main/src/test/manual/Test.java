package test.manual;

import data.Agriculture;
import data.Coordonnees;
import data.Otage;

/**
 * Classe test
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 2
 */
public class Test {
    /**
     * Fonction main
     * @param args Non utilisé
     */
    public static void main(String args []){
        new Otage(new Coordonnees(10, 10), 5).getCarte().afficher();
        System.out.println("\n");
        new Agriculture(new Coordonnees(10, 10)).getCarte().afficher();
    }
}
