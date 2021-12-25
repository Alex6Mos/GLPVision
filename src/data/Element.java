package data;

/**
 * Classe Element / case de la matrice
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 2
 */
public class Element {

    /**
     * Coordonnées
     */
    private Coordonnees coordonnees;

    /**
     * Description
     */
    private String desc;

    /**
     * Constructeur, initialise les variables
     * @param c Coordonnées
     */
    public Element(Coordonnees c){
        coordonnees = c;
        this.desc = ".";
    }

    /**
     * Fonction qui retourne la description
     * @return Description
     */
    public String getDesc(){
        return desc;
    }

    /**
     * Fonction qui retourne les coordonnées
     * @return Coordonnées
     */
    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    /**
     * Fonction qui définit les coordonnées
     * @param coordonnees Coordonnées
     */
    public void setCoordonnees(Coordonnees coordonnees){
        this.coordonnees = coordonnees;
    }
}
