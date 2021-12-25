package data;

/**
 * Classe abstraite scénario
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 2
 */
public abstract class Scenario {

    /**
     * Carte
     */
    protected Carte carte;

    /**
     * Fonction qui retourne la carte
     * @return Carte
     */
    public Carte getCarte() {
        return carte;
    }
}
