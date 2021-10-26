package data;


import config.Configuration;

/**
 * Classe carte
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 4
 */
public class Carte {

    /**
     * Taille de la carte
     */
    private Coordonnees taille;

    /**
     * Matrice de dimension 2 / carte
     */
    private Element[][] tab;

    /**
     * Booléen true=otage, false=agricole
     */
    private boolean otage = false;

    /**
     * Nombre d'anomalies
     */
    private int nb_anomalie;

    /**
     * Nombre d'otages
     */
    private int nb_otage;

    /**
     * Nombre d'asaillants
     */
    private int nb_assaillant;

    /**
     * Constructeur, initialise les variables
     * @param taille Taille de la carte
     * @param mode Otage ou agricole
     * @param nb_otage Nombre d'otages
     */
    public Carte(Coordonnees taille, boolean mode, int nb_otage){
        this.taille = taille;
        this.otage = mode;
        this.nb_otage = nb_otage;
        tab = new Element[taille.getX()][taille.getY()];
        init();
    }

    /**
     * Fonction initialisation
     */
    public void init(){
        generer();
        scan();
        if(otage){
            while(nb_assaillant <= nb_otage/4){  //au moins 1 assaillant pour 4 otages
                generer();
                scan();
            }
        }
    }

    /**
     * Fonction pour générer la carte aléatoirement
     */
    public void generer(){
        for (int i=0 ; i< taille.getY() ; i++){ //parcours x
            for (int j=0 ; j< taille.getX() ; j++){ //parcours y
                if(otage){
                    int random = (int) (Math.random()* Configuration.PROBA_INDIVIDU); //aléatoire
                    switch(random){
                        case 1:
                            tab[j][i] = new Personne(new Coordonnees(j, i)); //personne
                            break;
                        default:
                            tab[j][i] = new Element(new Coordonnees(j, i)); //rien
                            break;
                    }
                }
                else{ //agricole
                    int random = (int) (Math.random()*Configuration.PROBA_ANOMALIE); //aléatoire
                    switch (random) {
                        case 1:
                            tab[j][i] = new Maladie(new Coordonnees(j, i)); //maladie
                            break;
                        case 2:
                            tab[j][i] = new Intrusion(new Coordonnees(j, i)); //intrusion
                            break;
                        case 3:
                            tab[j][i] = new Feu(new Coordonnees(j, i)); //feu
                            break;
                        case 4:
                            tab[j][i] = new Anomalie(new Coordonnees(j, i)); //anomalie
                            break;
                        default:
                            tab[j][i] = new Element(new Coordonnees(j, i)); //rien
                            break;
                    }
                }
            }
        }
    }

    /**
     * Fonction pour afficher la carte dans le terminal (pour tester)
     */
    public void afficher(){
        for (int  i=0 ; i< taille.getX() ; i++){
            for (int j=0 ; j< taille.getY() ; j++){
                System.out.print(tab[i][j].getDesc() + "\t");
            }
            System.out.print("\n");
        }
    }

    /**
     * Fonction pour détecter le nombre de personnes/anomalies
     */
    public void scan(){
        int nb =0;
        for (int i=0 ; i< taille.getY() ; i++){ //parcours x
            for (int j=0 ; j< taille.getX() ; j++){ //parcours y
                if (!tab[j][i].getDesc().equals(".")){ //si pas rien
                    nb++;
                }
            }
        }
        if(otage){
            nb_assaillant = nb - nb_otage; //calcul nombre d'otages
        }
        else {
            setNb_anomalie(nb); //calcul nombre d'anomalies
        }
    }

    /**
     * Fonction qui retourne la matrice
     * @return Matrice
     */
    public Element[][] getTab(){
        return tab;
    }

    /**
     * Fonction qui retourne les dimensions de la carte
     * @return Dimensions
     */
    public Coordonnees getTaille() {
        return taille;
    }

    /**
     * Fonctione qui retourne si c'est une carte d'otage ou agricole
     * @return Otage ou agricole
     */
    public boolean isOtage() {
        return otage;
    }

    /**
     * Fonction qui retourne un élément à une position x y
     * @param x position x
     * @param y position y
     * @return Elément
     */
    public Element getElement(int x, int y) {
        return tab[x][y];
    }

	/**
	 * Setter du nombre d'anomalies
	 * @param nb_anomalie Nombre d'anomalies
	 */
	public void setNb_anomalie(int nb_anomalie) {
		this.nb_anomalie = nb_anomalie;
	}
}

