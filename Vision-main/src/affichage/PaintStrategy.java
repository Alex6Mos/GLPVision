package affichage;

import data.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Définit les éléments à dessiner dans Display
 *
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 *
 * @version 3
 */
public class PaintStrategy {

    /**
     * Hauteur et largeur des cases
     */
    private int width, height;

    /**
     * rayon du icônes
     */
    private int size;

    /**
     * Image de champ
     */
    private Image champ = ImageIO.read(ClassLoader.getSystemResource("champ.jpg"));

    /**
     * Image de bâtiments
     */
    private Image bat = ImageIO.read(ClassLoader.getSystemResource("fondotage.png"));

    /**
     * Image de personne
     */
    private Image personne = ImageIO.read(ClassLoader.getSystemResource("personne.png"));

    /**
     * Image d'inconnue
     */
    private Image inconnue = ImageIO.read(ClassLoader.getSystemResource("inconnue.png"));

    /**
     * Image de feu
     */
    private Image feu = ImageIO.read(ClassLoader.getSystemResource("feu.png"));

    /**
     * Image d'intrusion
     */
    private Image intrusion = ImageIO.read(ClassLoader.getSystemResource("intrusion.png"));

    /**
     * Image de virus
     */
    private Image maladie = ImageIO.read(ClassLoader.getSystemResource("virus.png"));

    /**
     * Constructeur vide
     * @throws IOException Erreur de lecture des images
     */
    public PaintStrategy() throws IOException {
    }

    /**
     * Dessine la fond de la carte
     * @param carte carte
     * @param graphics graphics
     */
    public void draw(Carte carte, Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        Coordonnees taille = carte.getTaille(); //dimension de la carte
        height = 600/taille.getY(); //hauteur case
        width = 950/taille.getX(); //largeur case
        if(height<width) //choisi le min entre les 2
            size = height/2;
        else
            size = width/2;
        Image fond = null;
        if(carte.isOtage())
            fond = bat.getScaledInstance(width, height, Image.SCALE_DEFAULT); //batiment
        else
            fond = champ.getScaledInstance(width, height, Image.SCALE_DEFAULT); //champs
        for(int i=0 ; i< taille.getY() ; i++){ //dessine fond dans chaque cases
            for(int j=0 ; j< taille.getX() ; j++){
                g.drawImage(fond, (j*width), (i*height), null, null); //dessine
            }
        }
    }

    /**
     * Dessine les anomalies inconnues
     * @param a liste des anomalies inconnues
     * @param graphics graphics
     */
    public void draw(Anomalie a, Graphics graphics){ //dessine liste
        inconnue = inconnue.getScaledInstance(size, size, Image.SCALE_DEFAULT); //redimensionne image
        Coordonnees pos = a.getCoordonnees();
        graphics.drawImage(inconnue, pos.getX()*width+(width/2-size/2), pos.getY()*height+(height/2-size/2), null, null); //dessine
    }

    /**
     * Dessine les feux
     * @param f liste des feux
     * @param graphics graphics
     */
    public void draw(Feu f, Graphics graphics){
        feu = feu.getScaledInstance(size, size, Image.SCALE_DEFAULT); //redimensionne
        Coordonnees pos = f.getCoordonnees();
        graphics.drawImage(feu, pos.getX()*width+(width/2-size/2), pos.getY()*height+(height/2-size/2), null, null); //dessine
    }

    /**
     * Dessine les intrusions
     * @param i liste des intrusions
     * @param graphics graphics
     */
    public void draw(Intrusion i, Graphics graphics){
        intrusion = intrusion.getScaledInstance(size, size, Image.SCALE_DEFAULT); //redimensionne
        Coordonnees pos = i.getCoordonnees();
        graphics.drawImage(intrusion, pos.getX()*width+(width/2-size/2), pos.getY()*height+(height/2-size/2), null, null); //dessine
    }

    /**
     * Dessine les maladies
     * @param m liste des maladies
     * @param graphics graphics
     */
    public void draw(Maladie m, Graphics graphics){
        maladie = maladie.getScaledInstance(size, size, Image.SCALE_DEFAULT); //redimensionne
        Coordonnees pos = m.getCoordonnees();
        graphics.drawImage(maladie, pos.getX()*width+(width/2-size/2), pos.getY()*height+(height/2-size/2), null, null); //dessine
    }

    /**
     * Dessine les individus
     * @param p liste des individus
     * @param graphics graphics
     */
    public void draw(Personne p, Graphics graphics){
        personne = personne.getScaledInstance(size, size, Image.SCALE_DEFAULT); //redimensionne
        Coordonnees pos = p.getCoordonnees();
        graphics.drawImage(personne, pos.getX()*width+(width/2-size/2), pos.getY()*height+(height/2-size/2), null, null); //dessine
    }

    /**
     * Entoure les éléments
     * @param e liste des éléments à entourer
     * @param graphics graphics
     */
    public void draw(Element e, Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        Coordonnees pos = e.getCoordonnees();
        g.setColor(new Color(255, 0, 0)); //rouge
        g.setStroke(new BasicStroke(3)); //épai
        g.drawOval(pos.getX()*width+(width/2-size)+1, pos.getY()*height+(height/2-size)+3, (size*2)-2, (size*2)-5); //dessine cercle
        g.setColor(new Color(0, 0, 0)); //noir
        g.setStroke(new BasicStroke(3)); //fin
    }

    /**
     * Encadre l'élément sélectionné
     * @param e élément sélectionné
     * @param graphics graphics
     */
    public void draw_selected(Element e, Graphics graphics){
        try{
            Coordonnees pos = e.getCoordonnees();
            graphics.setColor(Color.WHITE); //blanc
            graphics.drawRect(pos.getX()*width, pos.getY()*height, width, height); //rectangle
            graphics.setColor(Color.BLACK); //noir
        }
        catch (NullPointerException nullPointerException){ //aucun élément sélectionné
        }
    }
}
