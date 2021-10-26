package data;

/**
 * Classe Intrusion
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 2
 */
public class Intrusion extends Anomalie{

    /**
     * Description
     */
    private String intrusion;

    /**
     * Constructeur, initialise les variables
     * @param c Coordonnées
     */
    public Intrusion(Coordonnees c) {
        super(c);
        intrusion = "Intrusion";
    }

    /**
     * Fonction qui retourne la description
     * @return Description
     */
    public String getDesc(){
        return intrusion;
    }
}
