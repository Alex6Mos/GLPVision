package affichage;

import data.*;
import moteur.Traitement;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Carte dans la JFrame
 *
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 *
 * @version 4
 */
public class Display extends JPanel{
	private static final long serialVersionUID = 1L;

    /**
     * Traitement/données
     */
    private Traitement traitement;

    /**
     * Méthodes des dessins
     */
    private PaintStrategy paintStrategy;

    /**
     * Constructeur, initialise les variables
     * @param traitement Données
     * @param paintStrategy Dessins
     */
    Display(Traitement traitement, PaintStrategy paintStrategy){
        this.paintStrategy = paintStrategy;
        this.traitement = traitement;
    }

    /**
     * Méthode repaint
     * @param g graphics
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        /**
         * Déssine le fond
         */
        paintStrategy.draw(traitement.getCarte(), g); //dessine fond
        if(traitement.isOtage()){ //otage
            ArrayList <Personne> personne = traitement.getIndividu(); //recup liste

            /**
             * Déssine les individus
             */
            for(int i=0 ; i<personne.size() ; i++){ //dessine tous les individus
                paintStrategy.draw(personne.get(i), g);
            }
        }
        else{ //agricole
            //recup listes
            ArrayList <Anomalie> anomalie = traitement.getInconnue();
            ArrayList <Feu> feu = traitement.getFeu();
            ArrayList <Intrusion> intrusion = traitement.getIntrusion();
            ArrayList <Maladie> maladie = traitement.getMaladie();

            /**
             * Déssine les anomalies inconnues
             */
            for(int i=0 ; i<anomalie.size() ; i++){ //dessine toutes les anomalies inconnues
                paintStrategy.draw(anomalie.get(i), g);
            }

            /**
             * Déssine les feux
             */
            for(int i=0 ; i<feu.size() ; i++){ //dessine tous les feux
                paintStrategy.draw(feu.get(i), g);
            }

            /**
             * Déssine les intrusion
             */
            for(int i=0 ; i<intrusion.size() ; i++){ //dessine toutes les intrusions
                paintStrategy.draw(intrusion.get(i), g);
            }

            /**
             * Déssine les maladies
             */
            for(int i=0 ; i<maladie.size() ; i++){ //dessine toutes les maladies
                paintStrategy.draw(maladie.get(i), g);
            }
        }
        ArrayList <Element> entoure = traitement.getEntoure(); //recup liste

        /**
         * Entoure les éléments
         */
        for(int i=0 ; i<entoure.size() ; i++){ //entoure tous les éléments
            paintStrategy.draw(entoure.get(i), g);
        }
        Element selected = traitement.getSelected();

        /**
         * Encadre l'élément sélectionné
         */
        paintStrategy.draw_selected(selected, g); //encadre l'élément sélectionné
    }
}
