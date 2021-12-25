package data;

/**
 * Classe Personne
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 2
 */
public class Personne extends Element {

    /**
     * Description
     */
    private String personne;

    /**
     * Constructeur, initialise les variables
     * @param c Coordonnées
     */
    public Personne(Coordonnees c) {
        super(c);
        this.personne = "Individu";
    }

    /**
     * Fonction qui retourne la description
     * @return Description
     */
    public String getDesc(){
        return personne;
    }

    /**
     * Fonction qui définit la description
     * @param personne Description
     */
    public void setDesc(String personne) {
        this.personne = personne;
    }
}
