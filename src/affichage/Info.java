package affichage;

import data.Element;
import logs.LoggerUtility;
import moteur.Traitement;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Mise en place des différentes informations à afficher
 * 
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * 
 * @version 10
 */

public class Info extends JPanel {
	private static final long serialVersionUID = 1L;

    /**
     * Journalisation
     */
    private static Logger logger = LoggerUtility.getLogger(AgricoleGUI.class);

    /**
     * Etiquette anomalie actuelle, anomalies actuelle, étiquette liste d'anomalies, étiquette nombre d'anomalies, nombre d'anomalies de chaque types, nombre total d'anomalies
     */
    private JLabel txt_anomalie, anomalie, txt_liste_anomalie, txt_nb_anomalie, nb_anomalie, total_anomalie;

    /**
     * Nombre d'otages, étiquette individu actuel, individu actuel, étiquete liste d'individus, nombre total d'individus, nombre d'assaillants
     */
    private JLabel nb_otage, txt_individu, individu, txt_liste_individu, total_individu, total_assaillant;

    /**
     * Liste d'anomalies ou d'individus
     */
    private JTextArea liste;

    /**
     * Curseur si la liste est longue
     */
    private JScrollPane scrollPane;

    /**
     * Panneau de boutons
     */
    private JPanel buttonPanel;

    /**
     * Boutons anomalie/assaillant suivant et précédent
     */
    private JButton next, prec;

    /**
     * Traitement
     */
    private Traitement traitement;

    /**
     * Carte
     */
    private Display display;

    /**
     * Constructeur, initialise le panneau
     * @param traitement Traitement
     * @param diffy Hauteur à soustraire
     * @param display Carte
     */
    public Info(Traitement traitement, int diffy, Display display) {
        this.traitement = traitement;
        this.display = display;
        if(traitement.isOtage()){

            /**
             * Mise en place de l'encadré de texte affichant le nombre d'otages rempli par l'utilisateur au préalable
             */
            nb_otage = new JLabel("  Nombre d'otages :");
            nb_otage.setHorizontalAlignment(SwingConstants.LEFT);
            nb_otage.setBorder(new MatteBorder(0, 0, 3, 0, (Color) Color.BLACK));
            nb_otage.setBounds(0, 0, 255, 30);
            this.add(nb_otage);

            /**
             * Mise en place de l'encadré de texte affichant les coordonnées de l'individu que l'on a sélectionner sur la carte
             */
            txt_individu = new JLabel("  Coordonnées de l'individu actuel");
            txt_individu.setBackground(SystemColor.activeCaption);
            txt_individu.setHorizontalAlignment(SwingConstants.LEFT);
            txt_individu.setBounds(0, 30, 255, 30);
            txt_individu.setBorder(new MatteBorder(0, 0, 3, 0, (Color) Color.BLACK));
            this.add(txt_individu);

            /**
             * Mise en place des informations affichées en fonction de la case sélectionnée
             */
            individu = new JLabel("    Aucun individu sélectionné");
            individu.setBounds(0, 60, 255, 50);
            this.add(individu);
            individu.setVerticalAlignment(SwingConstants.CENTER);
            individu.setHorizontalAlignment(SwingConstants.LEFT);
            individu.setBorder(null);

            /**
             * Mise en place du texte au dessus de la liste
             */
            txt_liste_individu = new JLabel("  Liste des individus repérés par le drone");
            txt_liste_individu.setHorizontalAlignment(SwingConstants.LEFT);
            txt_liste_individu.setBorder(new MatteBorder(3, 0, 0, 0, (Color) new Color(0, 0, 0)));
            txt_liste_individu.setBackground(SystemColor.activeCaption);
            txt_liste_individu.setBounds(0, 110, 255, 30);
            this.add(txt_liste_individu);

            /**
             * Mise en place d'un système pour parcourir la liste facilement si celle ci est trop longue
             */
            scrollPane = new JScrollPane();
            scrollPane.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)));
            scrollPane.setBounds(0, 140, 255, 280);
            this.add(scrollPane);

            /**
             * Mise en place de la liste contenant les informations sur les individus de la carte
             */
            liste = new JTextArea();
            liste.setEditable(false);
            scrollPane.setViewportView(liste);
            liste.setBorder(null);
            liste.setBackground(new Color(204, 190, 121));
            liste.setFont(liste.getFont().deriveFont(Font.BOLD, liste.getFont().getSize()));

            /**
             * Mise en place de l'encadré de texte affichant le nombre total d'individu présents sur la carte
             */
            total_individu = new JLabel("  Nombre total d'individus :");
            total_individu.setHorizontalAlignment(SwingConstants.LEFT);
            total_individu.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
            total_individu.setBounds(0, 420, 255, 30);
            this.add(total_individu);

            /**
             * Mise en place de l'encadré de texte affichant le nombre total d'assaillants présents sur la carte
             */
            total_assaillant = new JLabel("  Nombre total d'assaillants : ");
            total_assaillant.setHorizontalAlignment(SwingConstants.LEFT);
            total_assaillant.setBorder(new MatteBorder(0, 0, 3, 0, (Color) Color.BLACK));
            total_assaillant.setBounds(0, 450, 255, 30);
            this.add(total_assaillant);

            /**
             * Mise en place des boutons
             */
            prec = new JButton("Assaillant précédent");
            next = new JButton("Assaillant suivant");
            
        }
        else{

            /**
             * Mise en place de l'encadré de texte affichant l'anomalie sélectionnée sur la carte
             */
            txt_anomalie = new JLabel("  Anomalie sélectionnée");
            txt_anomalie.setHorizontalAlignment(SwingConstants.LEFT);
            txt_anomalie.setBounds(0, 0, 255, 30);
            txt_anomalie.setBorder(new MatteBorder(3, 0, 3, 0, (Color) new Color(0, 0, 0)));
            this.add(txt_anomalie);

            /**
             * Mise en place des informations de coordonnées affichées en fonction de la case sélectionnée
             */
            anomalie = new JLabel("    Aucune anomalie sélectionnée"); //lister les coordonnées
            anomalie.setVerticalAlignment(SwingConstants.CENTER);
            anomalie.setHorizontalAlignment(SwingConstants.LEFT);
            anomalie.setBounds(0, 30, 255, 50);
            anomalie.setBorder(null);
            this.add(anomalie);

            /**
             * Mise en place du texte au dessus de la liste
             */
            txt_liste_anomalie = new JLabel("  Liste des anomalies repérées par le drone");
            txt_liste_anomalie.setHorizontalAlignment(SwingConstants.LEFT);
            txt_liste_anomalie.setBorder(new MatteBorder(3, 0, 0, 0, (Color) new Color(0, 0, 0)));
            txt_liste_anomalie.setBounds(0, 80, 255, 30);
            this.add(txt_liste_anomalie);

            /**
             * Mise en place d'un système pour parcourir la liste facilement si celle ci est trop longue
             */
            scrollPane = new JScrollPane();
            scrollPane.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)));
            scrollPane.setBounds(0, 108, 255, 235);
            this.add(scrollPane);

            /**
             * Mise en place de la liste contenant les informations sur les anomalies de la carte
             */
            
            liste = new JTextArea();
            liste.setEditable(false);
            scrollPane.setViewportView(liste);
            liste.setBorder(null);
            liste.setBackground(new Color(204, 190, 121));
            liste.setFont(liste.getFont().deriveFont(Font.BOLD, liste.getFont().getSize()));

            /**
             * Mise en place de l'encadré de texte affichant le nombre d'anomalies de chaque catégorie présentes sur la carte
             */
            txt_nb_anomalie = new JLabel("  Nombre d'anomalies");
            txt_nb_anomalie.setHorizontalAlignment(SwingConstants.LEFT);
            txt_nb_anomalie.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
            txt_nb_anomalie.setBounds(0, 340, 255, 30);
            this.add(txt_nb_anomalie);

            /**
             * Mise en place des informations de types d'anomalie affichées en fonction de la case sélectionnée
             */
            nb_anomalie = new JLabel(); //
            nb_anomalie.setVerticalAlignment(SwingConstants.CENTER);
            nb_anomalie.setHorizontalAlignment(SwingConstants.LEFT);
            nb_anomalie.setBorder(null);
            nb_anomalie.setBounds(0, 370, 255, 80);
            this.add(nb_anomalie);

            /**
             * Mise en place de l'encadré de texte affichant le nombre total d'anomalies présentes sur la carte
             */
            total_anomalie = new JLabel("    Total : "); //
            total_anomalie.setHorizontalAlignment(SwingConstants.LEFT);
            total_anomalie.setBorder(new MatteBorder(3, 0, 3, 0, (Color) Color.BLACK));
            total_anomalie.setBounds(0, 450, 255, 30);
            this.add(total_anomalie);
            
            /**
             * Mise en place des boutons
             */
            prec = new JButton("Anomalie précédente");
            next = new JButton("Anomalie suivante");
        }

        /**
         * Mise en place du panneau de boutons
         */
        buttonPanel = new JPanel();
        buttonPanel.setBorder(new MatteBorder(0, 3, 3, 3, (Color) new Color(0, 0, 0)));
        buttonPanel.setBounds(0, 480, 255, 120-diffy);
        buttonPanel.setBackground(new Color(204, 190, 121));

        /**
         * Mise en place de la charte graphique du bouton "précédent"
         */
        ButtonsListener listener = new ButtonsListener();
        prec.setBorder(null);
        prec.setBackground(SystemColor.activeCaption);
        prec.addActionListener(listener);
        
        /**
         * Mise en place de la charte graphique du bouton "suivant"
         */
        next.setBorder(null);
        next.setBackground(SystemColor.activeCaption);
        next.addActionListener(listener);
        
        /**
         * Mise en place graphique
         */
        this.setBounds(10, 50, 255, 600-diffy);
        this.setBorder(new MatteBorder(3, 3, 3, 3, (Color) Color.BLACK));
        this.setLayout(null);
        this.setBackground(new Color(204, 190, 121));

        /**
         * Layout du panneau de boutons pour que la taille et la position des boutons se régulent
         */
        SpringLayout sl_panel = new SpringLayout();
        buttonPanel.setLayout(sl_panel);
        sl_panel.putConstraint(SpringLayout.SOUTH, next, -10, SpringLayout.SOUTH, buttonPanel);
        sl_panel.putConstraint(SpringLayout.NORTH, next, 5, SpringLayout.VERTICAL_CENTER, buttonPanel);
        sl_panel.putConstraint(SpringLayout.SOUTH, prec, -5, SpringLayout.VERTICAL_CENTER, buttonPanel);
        sl_panel.putConstraint(SpringLayout.NORTH, prec, 10, SpringLayout.NORTH, buttonPanel);
        sl_panel.putConstraint(SpringLayout.WEST, prec, 10, SpringLayout.WEST, buttonPanel);
        sl_panel.putConstraint(SpringLayout.EAST, prec, -10, SpringLayout.EAST, buttonPanel);
        sl_panel.putConstraint(SpringLayout.WEST, next, 10, SpringLayout.WEST, buttonPanel);
        sl_panel.putConstraint(SpringLayout.EAST, next, -10, SpringLayout.EAST, buttonPanel);

        /**
         * Ajout des boutons à l'application
         */
        buttonPanel.add(prec);
        buttonPanel.add(next);
        this.add(buttonPanel);
    }

    /**
     * Mise en place des changements de couleur de background des boutons pour les thèmes d'apparence
     * @param color Couleur
     */
    public void setButtonBackground(Color color) {
    	next.setBackground(color);
    	prec.setBackground(color);
    }
    
    /**
     * Mise en place des changements de couleur de texte des boutons pour les thèmes d'apparence
     * @param color Couleur
     */
    public void setButtonForeground(Color color) {
    	next.setForeground(color);
    	prec.setForeground(color);
    }
    
    /**
     * Mise en place des changements de couleur des différentes informations qui s'affichent dans la grille d'information
     * @param color Couleur
     */
    public void setInfoBackground (Color color){
    	liste.setBackground(color);
    	buttonPanel.setBackground(color);
    	this.setBackground(color);
    }

    /**
     * Met à jour la liste
     * @param reset Remise à zéro
     */
    public void majGUI(boolean reset){
    	
    	/**
    	 * Scénario Otage
    	 */
        if (traitement.isOtage()){
            try{

            	/**
            	 * Si la case sélectionnée ne contient rien
            	 */
                if(traitement.getSelected().getDesc().equals(".")){ 
                    individu.setText("    " + "Aucun Individu en : x = " + (traitement.getSelected().getCoordonnees().getX()+traitement.getDebut().getX()) + " , y =  " + (traitement.getSelected().getCoordonnees().getY()+traitement.getDebut().getY()));
                    logger.info("Aucun Individu en : " + (traitement.getSelected().getCoordonnees().getX()+traitement.getDebut().getX()) + ", " + (traitement.getSelected().getCoordonnees().getY()+traitement.getDebut().getY()));

                }

                /**
                 * Si la case sélectionnée contient une image supplémentaire de celle du fond
                 */
                else{ //individu
                    individu.setText("    " + traitement.getSelected().getDesc() + " en : x = " + (traitement.getSelected().getCoordonnees().getX()+traitement.getDebut().getX()) + " , y = " + (traitement.getSelected().getCoordonnees().getY()+traitement.getDebut().getY()));
                    logger.info(traitement.getSelected().getDesc() + " sélectionée en : x = " + (traitement.getSelected().getCoordonnees().getX()+traitement.getDebut().getX()) + " , y = " + (traitement.getSelected().getCoordonnees().getY()+traitement.getDebut().getY()));
                }
            }
            catch (NullPointerException nullPointerException){
                logger.warn("Pas d'élément sélectionnable/sélectionné");
            }
            
            /**
             * Affichage des nombres d'items recherchés
             */
            total_individu.setText("  Nombre total d'individus : " + traitement.getNbIndividu());
            nb_otage.setText("  Nombre d'otages : " + traitement.getNbOtage());
            total_assaillant.setText("  Nombre total d'assaillants : " + traitement.getNbAssaillant());
            String txt = liste.getText();
            
            /**
             * mise en place de la liste d'informations en fonction des coordonnées des individus repérés
             */
            if(reset){ //vide la liste
                txt = "";
            }
            
            /**
             * Liste des individus
             */
            for(Element e : traitement.getEntoure()){
                String line = e.getDesc() + " en : x = " + (e.getCoordonnees().getX()+traitement.getDebut().getX()) + " , y = " + (e.getCoordonnees().getY()+traitement.getDebut().getY());
                if(!txt.contains(line)){
                    txt = txt + "   " + line + "\n";
                }
                logger.info(e.getDesc() + " en : x = " + (e.getCoordonnees().getX()+traitement.getDebut().getX()) + " , y = " + (e.getCoordonnees().getY()+traitement.getDebut().getY()));
            }
            
            /**
             * Liste des assaillants
             */
            for(Element e : traitement.getIndividu()){
                String line = e.getDesc() + " en : x = " + (e.getCoordonnees().getX()+traitement.getDebut().getX()) + " , y = " + (e.getCoordonnees().getY()+traitement.getDebut().getY());
                if(!txt.contains(line)){
                    txt = txt + "   " + line + "\n";
                }
                logger.info(e.getDesc() + " en : x = " + (e.getCoordonnees().getX()+traitement.getDebut().getX()) + " , y = " + (e.getCoordonnees().getY()+traitement.getDebut().getY()));
            }
            
            /**
             * Ajout à la liste
             */
            liste.setText(txt);
            logger.info("Nombre total d'individus : " + traitement.getNbIndividu());
            logger.info("Nombre d'otages : " + traitement.getNbOtage());
            logger.info("Nombre total d'assaillants : " + traitement.getNbAssaillant());
        }

        /**
         * Scénario Agricole
         */
        else {
            try{
            	
            	/**
            	 * Si la case sélectionnée ne contient rien
            	 */
                if(traitement.getSelected().getDesc().equals(".")){
                    anomalie.setText("    " + "Aucune anomalie en : x = " + (traitement.getSelected().getCoordonnees().getX()+traitement.getDebut().getX()) + " , y = " + (traitement.getSelected().getCoordonnees().getY()+traitement.getDebut().getY()));
                    logger.info("    " + "Aucune anomalie en : x = " + (traitement.getSelected().getCoordonnees().getX()+traitement.getDebut().getX()) + " , y = " + (traitement.getSelected().getCoordonnees().getY()+traitement.getDebut().getY()));
                }
                
                /**
                 * Si la case sélectionnée contient une anomalie
                 */
                else{ //anomalies
                    anomalie.setText("    " + traitement.getSelected().getDesc() + " en : x = " + (traitement.getSelected().getCoordonnees().getX()+traitement.getDebut().getX()) + " , y = " + (traitement.getSelected().getCoordonnees().getY()+traitement.getDebut().getY()));
                    logger.info("    " + traitement.getSelected().getDesc() + " en : x = " + (traitement.getSelected().getCoordonnees().getX()+traitement.getDebut().getX()) + " , y = " + (traitement.getSelected().getCoordonnees().getY()+traitement.getDebut().getY()));
                }
            }
            catch (NullPointerException nullPointerException){
                logger.warn("Pas d'élément sélectionnable/séléctionné");
            }
            
            /**
             * Affichage des nombres d'items recherchés 
             */
            total_anomalie.setText("   Total : " + (traitement.getNbInconnue()+traitement.getNbFeu()+traitement.getNbMaladie()+traitement.getNbIntrusion()));
            nb_anomalie.setText("<html> &nbsp &#160 Nombre de feux : " + traitement.getNbFeu() + "<br/>" +
                    " &nbsp &#160 Nombre d'intrusions : " + traitement.getNbIntrusion() + "<br/>" +
                    " &nbsp &#160 Nombre de maladies : " + traitement.getNbMaladie() + "<br/>" +
                    " &nbsp &#160 Nombre d'anomalies inconnues : " + traitement.getNbInconnue() + "</html>");
            logger.info("Total : " + (traitement.getNbInconnue()+traitement.getNbFeu()+traitement.getNbMaladie()+traitement.getNbIntrusion()));
            String txt = liste.getText();
            if(reset){ //vide la liste
                txt = "";
            }
            for(Element e : traitement.getEntoure()){ //liste les anomalies
                String line = e.getDesc() + " en : x = " + (e.getCoordonnees().getX()+traitement.getDebut().getX()) + " , y = " + (e.getCoordonnees().getY()+traitement.getDebut().getX());
                if(!txt.contains(line)){
                    txt = txt + "   " + line + "\n";
                }
                logger.info(e.getDesc() + " en : x = " + (e.getCoordonnees().getX()+traitement.getDebut().getX()) + ", y = " + (e.getCoordonnees().getY()+traitement.getDebut().getX()));
            }
            
            /**
             * Ajout des éléments à la liste
             */
            liste.setText(txt);
            logger.info("Nombre de feux : " + traitement.getNbFeu());
            logger.info("Nombre d'intrusion : " + traitement.getNbIntrusion());
            logger.info("Nombre de maladie : " + traitement.getNbMaladie());
            logger.info("Nombre d'anomalies inconnues : " + traitement.getNbInconnue());
        }
    }

    /**
     * Actions des boutons
     */
	private class ButtonsListener implements ActionListener{
        @Override
        /**
         * Mise en place des action réalisées par les boutons
         */
        public void actionPerformed(ActionEvent e) {

            /**
             * Suivant
             */
            if(e.getSource()==next) {
                try{
                    traitement.next();
                    logger.info("Passage à l'anomalie suivante");
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException){
                    logger.error("Aucun élément entouré");
                }
            }

            /**
             * Précédent
             */
            if(e.getSource()==prec) {
                try{
                    traitement.previous();
                    logger.info("Passage à l'anomalie précédente");
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException){
                    logger.error("Aucun élément entouré");
                }
            }

            /**
             * Mise à jour de la fenêtre
             */
            majGUI(false);
            display.repaint();
        }
    }
}