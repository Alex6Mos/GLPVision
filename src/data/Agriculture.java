package data;

/**
 * Classe scénario agriculture
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 3
 */
public class Agriculture extends Scenario {
    /**
     * Constructeur, initialise les variables
     * @param taille Taille de la carte
     */
    public Agriculture(Coordonnees taille){
        carte = new Carte(taille, false, 0); //nouvelle carte
    }
}
