package data;

/**
 * Classe Cordonnées
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 2
 */
public class Coordonnees {

    /**
     * Coordonnées x
     */
	private int x;

    /**
     * Coordonnées y
     */
	private int y;

    /**
     * Constructeur, initialise les variables
     * @param x Coordonnées x
     * @param y Coordonnées y
     */
    public Coordonnees(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Fonction qui retourne Coordonnée X
     * @return Coordonnée X
     */
    public int getX() {
        return x;
    }

    /**
     * Fonction qui retourne Coordonnée Y
     * @return Coordonnée Y
     */
    public int getY() {
        return y;
    }
}
