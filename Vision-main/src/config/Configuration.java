package config;

/**
 * Configuration du programme
 *
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 *
 * @version 2
 */
public class Configuration {

    /**
     * Nombre max d'otages
     */
    public static final int MAX_OTAGES = 10;

    /**
     * Nombre min d'otages
     */
    public static final int MIN_OTAGES = 1;

    /**
     * Vitesse du scan initial
     */
    public static final int SCAN_SPEED = 10;

    /**
     * Vitesse de simulation de base
     */
    public static final int BASE_SPEED = 500;

    /**
     * Probabilité d'apparition d'anomalies, plus c'est élevé, moins il y a de chance
     */
    public static final int PROBA_ANOMALIE = 50;

    /**
     * Probabilité d'apparition d'individus
     */
    public static final int PROBA_INDIVIDU = 50;

    /**
     * Probabilité qu'un élément se déplace
     */
    public static final int PROBA_MOVE = 4; //1/4

    /**
     * Probabilité qu'un feu de propage
     */
    public static final int PROBA_FEU = 4;

    /**
     * probabilité qu'une maladie se propage
     */
    public static final int PROBA_INFECT = 8; //1/2
}
