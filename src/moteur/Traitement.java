package moteur;

import config.Configuration;
import data.*;

import java.util.ArrayList;

/**
 * Classe Traitement
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 15
 */
public class Traitement{
    /**
     * Nombre d'otages, nombre d'assaillants, nombre total de personnes, nombre d'intrusions, nombre de feux, nombre de maladies, nombre d'anomalies inconnues
     */
    private int nbOtage, nbAssaillant, nbIndividu, nbIntrusion, nbFeu, nbMaladie, nbInconnue;
    /**
     * Liste d'individus
     */
    private ArrayList <Personne> individu = new ArrayList<>();
    /**
     * Liste d'intrusions
     */
    private ArrayList <Intrusion> intrusion = new ArrayList<>();
    /**
     * Liste de feux
     */
    private ArrayList <Feu> feu = new ArrayList<>();
    /**
     * Liste de maladies
     */
    private ArrayList <Maladie> maladie = new ArrayList<>();
    /**
     * Liste d'anomalies inconnues
     */
    private ArrayList <Anomalie> inconnue = new ArrayList<>();
    /**
     * Liste des éléments entourés
     */
    private ArrayList <Element> entoure = new ArrayList<>();
    /**
     * Index de l'élément sélectionné
     */
    private int selectedIndex;
    /**
     * Elément sélectionné
     */
    private Element selected;
    /**
     * Carte
     */
    private Carte carte;
    /**
     * Scénario
     */
    private Scenario scenario;
    /**
     * Otage ou Agricole
     */
    private boolean otage;
    /**
     * Taille
     */
    private Coordonnees taille, debut;
    /**
     * Position x du parcours
     */
    private int posX = 0;
    /**
     * Position y du parcours
     */
    private int posY = 0;
    /**
     * S'il faut entourer
     */
    private boolean circle = false;
    /**
     * Nombre d'individus qui bougent
     */
    private int nbMoving = 0;

    /**
     * Constructeur, initialise les variables (agricole)
     * @param taille Taille de la carte
     * @param debut Coordonnées de départ
     */
    public Traitement(Coordonnees taille, Coordonnees debut){ //agricole
        this.scenario = new Agriculture(taille);
        this.carte = scenario.getCarte();
        this.otage = false;
        this.taille = taille;
        this.debut = debut;
        init();
    }

    /**
     * Constructeur, initialise les variables (otage)
     * @param taille Taille de la carte
     * @param debut Coordonnées de départ
     * @param nbOtage Nombre d'otages
     */
    public Traitement(Coordonnees taille, Coordonnees debut, int nbOtage){ //otage
        this.scenario = new Otage(taille, nbOtage);
        this.carte = scenario.getCarte();
        this.otage = true;
        this.taille = taille;
        this.debut = debut;
        this.nbOtage = nbOtage;
        init();
    }

    /**
     * Fonction qui initialise les variables
     */
    public void init(){
        entoure = new ArrayList<>();
        individu = new ArrayList<>();
        intrusion = new ArrayList<>();
        feu = new ArrayList<>();
        maladie = new ArrayList<>();
        inconnue = new ArrayList<>();
        nbIndividu = 0;
        nbIntrusion = 0;
        nbFeu = 0;
        nbMaladie = 0;
        nbInconnue = 0;
    }

    /**
     * Fonction qui scanne le nombre de personne/anomalies
     * @return Etat du scan
     */
    public int scan(){
        Element[][] tab = carte.getTab(); //recup la carte
        Element element = tab[posX][posY]; //élément à la position x y
        Coordonnees c = element.getCoordonnees();
        if(!circle){ //ne pas entourer
            switch (element.getDesc()) {
                case "Individu":  //si personne
                    individu.add(new Personne(c));
                    break;
                case "Intrusion":  //si intrusion
                    intrusion.add(new Intrusion(c));
                    break;
                case "Feu":  //si feu
                    feu.add(new Feu(c));
                    break;
                case "Maladie":  //si maladie
                    maladie.add(new Maladie(c));
                    break;
                case "Inconnue":  //si inconnue
                    inconnue.add(new Anomalie(c));
                    break;
            }
            posX++;
            if(posX>=taille.getX()){ //si au bout de la ligne
                posX = 0;
                posY++;
                if(posY >= taille.getY()){ //si au bout de la carte
                    posY = 0;
                    circle = true; //passage à l'entourage
                }
            }
        }
        else{ //entourer
            if(!otage && !element.getDesc().equals(".")){ //entoure les anomalies
                entoure.add(element);
            }
            //comptage
            switch (element.getDesc()) {
                case "Individu":  //si personne
                    nbIndividu++;
                    break;
                case "Intrusion":  //si intrusion
                    nbIntrusion++;
                    break;
                case "Feu":  //si feu
                    nbFeu++;
                    break;
                case "Maladie":  //si maladie
                    nbMaladie++;
                    break;
                case "Inconnue":  //si inconnue
                    nbInconnue++;
                    break;
            }
            posX++;
            if(posX>=taille.getX()){
                posX = 0;
                posY++;
                if(posY >= taille.getY()){
                    if(otage) {
                        nbAssaillant = nbIndividu - nbOtage; //calcule nombre d'assaillants
                    }
                    posY = 0;
                    return 1; //scan terminé
                }
            }
        }
        return 0; //scan non terminé
    }

    /**
     * Fonction qui déplace les différents éléments
     * @return S'il y a du déplacement
     */
    public int animate(){
        if((int) (Math.random()* Configuration.PROBA_MOVE) == 0){ //aleatoire
            if(otage){ //otage
                Personne p;
                p = individu.get((int) (Math.random() * individu.size()));
                int x = p.getCoordonnees().getX();
                int y = p.getCoordonnees().getY();
                if(nbMoving<nbAssaillant && !entoure.contains(p)){
                    p.setDesc("Assaillant");
                    entoure.add(p);
                    carte.getTab()[x][y] = p;
                    nbMoving++;
                    return move(x, y);
                }
                else if(p.getDesc().equals("Assaillant")){ //assaillant
                    return move(x, y);
                }
            }
            else{ //agricole
                Element e;
                e = entoure.get((int) (Math.random() * entoure.size()));
                int x = e.getCoordonnees().getX();
                int y = e.getCoordonnees().getY();
                switch (e.getDesc()) {
                    case "Intrusion":  //intrusion
                        return move(x, y);
                    case "Feu":  //étend le feu
                        return burn(x, y);
                    case "Inconnue":  //rien pour le moment
                        return 0; //n'a pas bougé

                    case "Maladie":  //infecte les autres plantes
                        return infect(x, y);
                }
            }
        }
        return 0; //n'a pas bougé
    }

    /**
     * Bouge gauche droite haut ou bas
     * @param x Position x
     * @param y Position Y
     * @return etat
     */
    public int move(int x, int y){
        switch ((int) (Math.random()*4)){ //choisi au hasard la direction
            case 0:
                return up(x, y);
            case 1:
                return down(x, y);
            case 2:
                return left(x, y);
            case 3:
                return right(x, y);
            default :
                return 0;
        }
    }

    /**
     * Monter
     * @param x Position x
     * @param y Position y
     * @return etat
     */
    public int up(int x, int y){ // i=x, j=y
        Element[][] tab = carte.getTab(); //recup la carte
        if(y-1 >= 0 && tab[x][y-1].getDesc().equals(".")){ //si y a la place pour bouger
            change(tab[x][y], x, y, 0, -1);
            return 1;
        }
        return 0;
    }

    /**
     * Descendre
     * @param x Position x
     * @param y Position y
     * @return etat
     */
    public int down(int x, int y){
        Element[][] tab = carte.getTab(); //recup la carte
        if(y+1 < taille.getY() && tab[x][y+1].getDesc().equals(".")){ //si y a la place pour bouger
            change(tab[x][y], x, y, 0, 1);
            return 1;
        }
        return 0;
    }

    /**
     * A gauche
     * @param x Position x
     * @param y Position y
     * @return etat
     */
    public int left(int x, int y){
        Element[][] tab = carte.getTab(); //recup la carte
        if(x-1 >= 0 && tab[x-1][y].getDesc().equals(".")){ //si y a la place pour bouger
            change(tab[x][y], x, y, -1, 0);
            return 1;
        }
        return 0;
    }

    /**
     * A droite
     * @param x Position x
     * @param y Position y
     * @return etat
     */
    public int right(int x, int y){
        Element[][] tab = carte.getTab(); //recup la carte
        if(x+1 < taille.getX() && tab[x+1][y].getDesc().equals(".")){ //si y a la place pour bouger
            change(tab[x][y], x, y, 1, 0);
            return 1;
        }
        return 0;
    }

    /**
     * Change de place un élément
     * @param element Elément
     * @param x Position x
     * @param y Position y
     * @param dx Décalage x
     * @param dy Décalage y
     */
    public void change(Element element, int x, int y, int dx, int dy){
        for(int i=0 ; i<entoure.size() ; i++){ //déplace le cercle
            if (entoure.get(i).getCoordonnees().getX() == x && entoure.get(i).getCoordonnees().getY() == y) {
                entoure.get(i).setCoordonnees(new Coordonnees(x+dx, y+dy));
                break;
            }
        }
        switch(element.getDesc()){
            case "Personne": //déplace personne
                for(int i=0 ; i<individu.size() ; i++){
                    if (individu.get(i).getCoordonnees().getX() == x && individu.get(i).getCoordonnees().getY() == y) {
                        individu.get(i).setCoordonnees(new Coordonnees(x+dx, y+dy));
                        break;
                    }
                }
                break;
            case "Intrusion": //déplace intrusion
                for(int i=0 ; i<intrusion.size() ; i++){
                    if (intrusion.get(i).getCoordonnees().getX() == x && intrusion.get(i).getCoordonnees().getY() == y) {
                        intrusion.get(i).setCoordonnees(new Coordonnees(x+dx, y+dy));
                        break;
                    }
                }
                break;
            default:
                break;
        }
        Element[][] tab = carte.getTab(); //recup la carte
        Element cache = tab[x][y];
        element.setCoordonnees(new Coordonnees(x+dx, y+dy)); //déplace dans la carte
        tab[x][y] = new Element(new Coordonnees(x, y));
        tab[x+dx][y+dy] = cache;
    }

    /**
     * Brule
     * @param x Position x
     * @param y Position y
     * @return etat
     */
    public int burn(int x, int y){
        Element[][] tab = carte.getTab(); //recup la carte
        switch((int) (Math.random() *Configuration.PROBA_FEU)){ //aleatoire
            case 0: //haut
                if(y-1 >= 0 && !tab[x][y-1].getDesc().equals("Feu")){
                    remove(x, y-1);
                    feu.add(new Feu(new Coordonnees(x, y-1)));
                    nbFeu++;
                    entoure.add(new Feu(new Coordonnees(x, y-1)));
                    tab[x][y-1] = new Feu(new Coordonnees(x, y-1));
                    return 1;
                }
                return 0;
            case 1: //bas
                if(y+1 < taille.getY() && !tab[x][y+1].getDesc().equals("Feu")){
                    remove(x, y+1);
                    feu.add(new Feu(new Coordonnees(x, y+1)));
                    nbFeu++;
                    entoure.add(new Feu(new Coordonnees(x, y+1)));
                    tab[x][y+1] = new Feu(new Coordonnees(x, y+1));
                    return 1;
                }
                return 0;
            case 2: //droite
                if(x+1 < taille.getX() && !tab[x+1][y].getDesc().equals("Feu")){
                    remove(x+1, y);
                    feu.add(new Feu(new Coordonnees(x+1, y)));
                    nbFeu++;
                    entoure.add(new Feu(new Coordonnees(x+1, y)));
                    tab[x+1][y] = new Feu(new Coordonnees(x+1, y));
                    return 1;
                }
                return 0;
            case 3: //gauche
                if(x-1 >= 0 && !tab[x-1][y].getDesc().equals("Feu")){
                    remove(x-1, y);
                    feu.add(new Feu(new Coordonnees(x-1, y)));
                    nbFeu++;
                    entoure.add(new Feu(new Coordonnees(x-1, y)));
                    tab[x-1][y] = new Feu(new Coordonnees(x-1, y));
                    return 1;
                }
                return 0;
        }
        return 0;
    }

    /**
     * Infecte les plantes aux alentours
     * @param x Position x
     * @param y Position y
     * @return etat
     */
    public int infect(int x, int y){
        Element[][] tab = carte.getTab(); //recup la carte
        switch((int) (Math.random() *Configuration.PROBA_INFECT)){ //aleatoire
            case 0: //haut
                if(y-1 >= 0 && tab[x][y-1].getDesc().equals(".") && !tab[x][y-1].getDesc().equals("Maladie")){
                    remove(x, y-1);
                    maladie.add(new Maladie(new Coordonnees(x, y-1)));
                    nbMaladie++;
                    entoure.add(new Maladie(new Coordonnees(x, y-1)));
                    tab[x][y-1] = new Maladie(new Coordonnees(x, y-1));
                    return 1;
                }
                return 0;
            case 1: //bas
                if(y+1 < taille.getY() && tab[x][y+1].getDesc().equals(".") && !tab[x][y+1].getDesc().equals("Maladie")){
                    remove(x, y+1);
                    maladie.add(new Maladie(new Coordonnees(x, y+1)));
                    nbMaladie++;
                    entoure.add(new Maladie(new Coordonnees(x, y+1)));
                    tab[x][y+1] = new Maladie(new Coordonnees(x, y+1));
                    return 1;
                }
                return 0;
            case 2: //droite
                if(x+1 < taille.getX() && tab[x+1][y].getDesc().equals(".") && !tab[x+1][y].getDesc().equals("Maladie")){
                    remove(x+1, y);
                    maladie.add(new Maladie(new Coordonnees(x+1, y)));
                    nbMaladie++;
                    entoure.add(new Maladie(new Coordonnees(x+1, y)));
                    tab[x+1][y] = new Maladie(new Coordonnees(x+1, y));
                    return 1;
                }
                return 0;
            case 3: //gauche
                if(x-1 >= 0 && tab[x-1][y].getDesc().equals(".") && !tab[x-1][y].getDesc().equals("Maladie")){
                    remove(x-1, y);
                    maladie.add(new Maladie(new Coordonnees(x-1, y)));
                    nbMaladie++;
                    entoure.add(new Maladie(new Coordonnees(x-1, y)));
                    tab[x-1][y] = new Maladie(new Coordonnees(x-1, y));
                    return 1;
                }
                return 0;
        }
        return 0;
    }
    /**
     * Retire un élément
     * @param x Position x
     * @param y Position y
     */
    public void remove(int x, int y){
        for(int i=0 ; i<intrusion.size() ; i++){ //intrusion
            if(intrusion.get(i).getCoordonnees().getX()==x && intrusion.get(i).getCoordonnees().getY()==y){
                intrusion.remove(i);
                nbIntrusion--;
                break;
            }
        }
        for(int i=0 ; i<feu.size() ; i++){ //fey
            if(feu.get(i).getCoordonnees().getX()==x && feu.get(i).getCoordonnees().getY()==y){
                feu.remove(i);
                nbFeu--;
                break;
            }
        }
        for(int i=0 ; i<maladie.size() ; i++){ //maladie
            if(maladie.get(i).getCoordonnees().getX()==x && maladie.get(i).getCoordonnees().getY()==y){
                maladie.remove(i);
                nbMaladie--;
                break;
            }
        }
        for(int i=0 ; i<inconnue.size() ; i++){ //inconnue
            if(inconnue.get(i).getCoordonnees().getX()==x && inconnue.get(i).getCoordonnees().getY()==y){
                inconnue.remove(i);
                nbInconnue--;
                break;
            }
        }
        for(int i=0 ; i<individu.size() ; i++){ //individu
            if(individu.get(i).getCoordonnees().getX()==x && individu.get(i).getCoordonnees().getY()==y){
                individu.remove(i);
                nbIndividu--;
                break;
            }
        }
        for(int i=0 ; i<entoure.size() ; i++){ //cercle
            if(entoure.get(i).getCoordonnees().getX()==x && entoure.get(i).getCoordonnees().getY()==y){
                entoure.remove(i);
                break;
            }
        }
    }

    /**
     * Bouton suivant
     */
    public void next(){
        if(selected == null){
            selectedIndex = 0;
        }
        else{
            selectedIndex++;
            if(selectedIndex>entoure.size()-1){
                selectedIndex = 0;
            }
        }
        trieEntoure();
        selected = entoure.get(selectedIndex);
    }

    /**
     * Bouton précédent
     */
    public void previous(){
        if(selected == null){
            selectedIndex = entoure.size()-1;
        }
        else{
            selectedIndex--;
            if(selectedIndex<0){
                selectedIndex = entoure.size()-1;
            }
        }
        trieEntoure();
        selected = entoure.get(selectedIndex);
    }

    /**
     * Fonction qui remet tout à zéro
     */
    public void supp() {
        nbOtage = 0;
        nbAssaillant = 0;
        nbIndividu = 0;
        nbIntrusion = 0;
        nbFeu = 0;
        nbMaladie = 0;
        nbInconnue = 0;
        inconnue = null;
        feu = null;
        maladie = null;
        intrusion = null;
        individu = null;
        carte = null;
        scenario = null;
    }

    /**
     * Trie la liste entoure pour avoir un parcours dans l'ordre avec les bouton suivant/précédent
     */
    public void trieEntoure(){
        ArrayList <Element> list = new ArrayList<>();
        for(int i=0 ; i<entoure.size() ; i++){
            if(list.isEmpty()){
                list.add(entoure.get(0));
            }
            else{
                int index = 0;
                while(index<list.size() && entoure.get(i).getCoordonnees().getY()*taille.getX() + entoure.get(i).getCoordonnees().getX() > list.get(index).getCoordonnees().getY()*taille.getX() + list.get(index).getCoordonnees().getX()){
                    index++;
                }
                list.add(index, entoure.get(i));
            }
        }
        entoure = list;
    }


    /**
     * Fonction qui retourne le Scénario
     * @return Scénario
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * Fonction qui retourne la carte/matrice
     * @return Matrice
     */
    public Carte getCarte() {
        return carte;
    }

    /**
     * Fonction qui retourne la taille de la carte/matrice
     * @return Taille
     */
    public Coordonnees getTaille() {
        return taille;
    }

    /**
     * Fonction qui retourne les coordonnées de départ
     * @return Début
     */
    public Coordonnees getDebut() {
        return debut;
    }

    /**
     * Fonction qui retourne si Otage ou Agricole
     * @return Otage ou Agricole
     */
    public boolean isOtage() {
        return otage;
    }

    /**
     * Fonction qui retourne le nombre d'otages
     * @return Nombre d'otages
     */
    public int getNbOtage() {
        return nbOtage;
    }
    /**
     * Fonction qui retourne le nombre d'assaillants
     * @return Nombre d'assaillants
     */
    public int getNbAssaillant() {
        return nbAssaillant;
    }
    /**
     * Fonction qui retourne le nombre d'individus
     * @return Nombre d'individus
     */
    public int getNbIndividu() {
        return nbIndividu;
    }
    /**
     * Fonction qui retourne le nombre d'intrusions
     * @return Nombre d'intrusions
     */
    public int getNbIntrusion() {
        return nbIntrusion;
    }
    /**
     * Fonction qui retourne le nombre de feux
     * @return Nombre de feux
     */
    public int getNbFeu() {
        return nbFeu;
    }
    /**
     * Fonction qui retourne le nombre de maladies
     * @return Nombre de maladies
     */
    public int getNbMaladie() {
        return nbMaladie;
    }
    /**
     * Fonction qui retourne le nombre d'inconnues
     * @return Nombre d'inconnues
     */
    public int getNbInconnue() {
        return nbInconnue;
    }
    /**
     * Fonction qui retourne la liste des individus
     * @return Liste des individus
     */
    public ArrayList <Personne> getIndividu() {
        return individu;
    }
    /**
     * Fonction qui retourne la liste des intrusions
     * @return Liste des intrusions
     */
    public ArrayList <Intrusion> getIntrusion() {
        return intrusion;
    }
    /**
     * Fonction qui retourne la liste des feux
     * @return Liste des feux
     */
    public ArrayList <Feu> getFeu() {
        return feu;
    }
    /**
     * Fonction qui retourne la liste des maladies
     * @return Liste des maladies
     */
    public ArrayList <Maladie> getMaladie() {
        return maladie;
    }
    /**
     * Fonction qui retourne  la liste des inconnues
     * @return Liste des inconnues
     */
    public ArrayList <Anomalie> getInconnue() {
        return inconnue;
    }
    /**
     * Fonction qui retourne  la liste des éléments entourés
     * @return Liste des éléments entourés
     */
    public ArrayList <Element> getEntoure() {
        return entoure;
    }
    /**
     * Fonction qui retourne l'élément sélectionné
     * @return Elément sélectionné
     */
    public Element getSelected() {
        return selected;
    }

    /**
     * Fonction qui définit l'élément sélectionné
     * @param selected Nouvel élément sélectionné
     */
    public void setSelected(Element selected) {
        this.selected = selected;
    }
}
