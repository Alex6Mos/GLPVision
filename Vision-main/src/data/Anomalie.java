package data;

/**
 * Classe anomalie
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 2
 */
public class Anomalie extends Element{

    /**
     * Description
     */
    private String anomalie;

    /**
     * Constructeur, initialise les variables
     * @param c Coordonnées
     */
    public Anomalie(Coordonnees c) {
        super(c);
        anomalie = "Inconnue";
    }

    /**
     * Fonction qui retourne la description
     * @return Description
     */
    public String getDesc(){
        return anomalie;
    }
}
