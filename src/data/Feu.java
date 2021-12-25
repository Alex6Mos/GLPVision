package data;

/**
 * Classe Feu
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 2
 */
public class Feu extends Anomalie{

    /**
     * Description
     */
    private String feu;

    /**
     * Constructeur, initialse les variables
     * @param c Coordonnées
     */
    public Feu(Coordonnees c) {
        super(c);
        feu = "Feu";
    }

    /**
     * Fonction qui retourne la description
     * @return Description
     */
    public String getDesc(){
        return feu;
    }

}
