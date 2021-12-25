package data;

/**
 * Classe Maladie
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 2
 */
public class Maladie extends Anomalie {

    /**
     * Maladie
     */
    private String maladie;

    /**
     * Constructeur, initialise les variables
     * @param c Coodonnées
     */
    public Maladie(Coordonnees c) {
        super(c);
        maladie = "Maladie";
    }

    /**
     * Fonction qui retourne la description
     * @return Description
     */
    public String getDesc(){
        return maladie;
    }
}
