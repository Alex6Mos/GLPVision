package affichage;

import config.Configuration;
import data.Coordonnees;
import data.Element;
import logs.LoggerUtility;
import moteur.Chronometre;
import moteur.Traitement;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * Mise en place de l'interface graphique du scénario Otage
 * 
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * 
 * @version 18
 */

public class OtageGUI extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Journalisation
	 */
	private static Logger logger = LoggerUtility.getLogger(OtageGUI.class);

	/**
	 * Panneau d'informations
	 */
	private Info infoPanel;

	/**
	 * Carte
	 */
	private Display carte;

	/**
	 * Fenêtre du scénario
	 */
	private JPanel contentPane;

	/**
	 * Barre de menu
	 */
	private JMenuBar menu;

	/**
	 * Eléments de la barre de menu
	 */
	private JMenu Fichier, Apparence;

	/**
	 * Sous-éléments de la barre de menu
	 */
	private JMenuItem recherche, quitter, sombre, clair, Aide;

	/**
	 * Etiquettes et horloge
	 */
	private JTextField nomcarte, info, date;

	/**
	 * Traitement
	 */
	private Traitement traitement;

	/**
	 * Taille de la carte et coordonnées du début
	 */
	private Coordonnees taille, debut;

	/**
	 * Curseur de vitesse de simulation
	 */
	private JSlider vitesse;

	/**
	 * Indicateur de la vitesse de simulation
	 */
	private JLabel numero;
	
	/**
	 * Largeur à soustraire, hauteur à soustraire, largeur de case, hauteur de case
	 */
	private int nbOtage, diffy, diffx, casex, casey;
	/**
	 * Etat du Thread
	 */
	private boolean running = true;

	/**
	 * Horloge
	 */
	private Chronometre chronometre;

	/**
	 * Vitesse de simulation
	 */
	private int speed = Configuration.BASE_SPEED;

	/**
	 * Constructeur, initialise les variables
	 * @param debut Coordonnées du début
	 * @param taille Taille de la carte
	 * @param nbOtage Nombre d'otages
	 */
	public OtageGUI(Coordonnees debut, Coordonnees taille, int nbOtage) {
		/**
		 * Définition du nom de la fenêtre
		 */
		super("Vision Détection : Prise d'otages");
		this.debut = debut;
		this.taille = taille;
		this.nbOtage = nbOtage;
		logger.info("Affichage de la fenêtre d'Otage");
	}

	/**
	 * Initialisation de la fenêtre
	 * @param debut Coordonnées du début
	 * @param taille Taille de la carte
	 * @throws IOException Exception lié aux images
	 */
	public void init(Coordonnees debut, Coordonnees taille) throws IOException {
		/**
		 * Définition des listeners
		 */
		ActionBar actionListener = new ActionBar();
		Click click = new Click();
		Slider slider = new Slider();

		/**
		 * Définition de la fenêtre		
		 */
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(null);
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setContentPane(contentPane);
		
		/**
		 * Définition de la barre de menu		
		 */
		menu = new JMenuBar();
		setJMenuBar(menu);
		menu.setBackground(Color.lightGray);
		
		/**
		 * Mise en place de l'onglet "Fichier"		
		 */
		Fichier = new JMenu("Fichier");
		menu.add(Fichier);

		/**
		 * Mise en place de l'onglet "Apparence"
		 */
		Apparence = new JMenu("Apparence");
		menu.add(Apparence);
		
		/**
		 * Mise en place de l'onglet "Aide"		
		 */
		Aide = new JMenuItem("Aide ?") {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getMaximumSize() {
				Dimension d1 = super.getPreferredSize();
                Dimension d2 = super.getMaximumSize();
                d2.width = d1.width;
                return d2;
			}
		};
		Aide.setPreferredSize(new Dimension(45,10));
		Aide.setHorizontalAlignment(SwingConstants.CENTER);
		Aide.setBackground(Color.LIGHT_GRAY);
		menu.add(Aide);		
		Aide.addActionListener(actionListener);
		
		/**
		 * Mise en place de la fonctionnalité de recherche
		 */
		recherche = new JMenuItem("Nouvelle Recherche");
		Fichier.add(recherche);
		recherche.addActionListener(actionListener);
		
		/**
		 * Mise en place de la fonctionnalité pour quitter l'application
		 */
		quitter = new JMenuItem("Quitter/Fermer Vision Détection");
		Fichier.add(quitter);
		quitter.addActionListener(actionListener);
		
		/**
		 * Mise en place de la fonctionnalité pour passer au thème sombre
		 */
		sombre = new JMenuItem("Thème Sombre");
		Apparence.add(sombre);
		sombre.addActionListener(actionListener);
		
		/**
		 * Mise en place de la fonctionnalité pour passer au thème clair
		 */
		clair = new JMenuItem("Thème Clair");
		Apparence.add(clair);
		clair.addActionListener(actionListener);
		contentPane.setLayout(null);
		
		/**
		 * Mise en place du nom au dessus de la carte
		 */
		nomcarte = new JTextField();
		nomcarte.setHorizontalAlignment(SwingConstants.CENTER);
		nomcarte.setBounds(274, 25, 180, 25);
		contentPane.add(nomcarte);
		nomcarte.setColumns(10);
		nomcarte.setText("Cartographie : Vision du drone ");
		nomcarte.setEditable(false);
		nomcarte.setBackground(SystemColor.activeCaption);
		nomcarte.setBorder(new MatteBorder(3, 3, 0, 3, (Color) Color.BLACK));

		/**
		 * Mise en place de l'indicateur de vitesse de simulation
		 */
		numero = new JLabel();
		numero.setHorizontalAlignment(SwingConstants.LEFT);
		numero.setBounds(640, 28, 150, 15);
		contentPane.add(numero);
		numero.setText("Vitesse de simulation : 1");
		numero.setForeground(Color.white);

		/**
		 * Mise en place du curseur de la vitesse de simulation
		 */
		vitesse = new JSlider();
		vitesse.setBounds(800, 25, 100, 25);
		contentPane.add(vitesse);
		vitesse.setBackground(Color.DARK_GRAY);
		vitesse.setMinimum(1);
		vitesse.setMaximum(10);
		vitesse.setValue(1);
		vitesse.addChangeListener(slider);
		
		/**
		 * Mise en place du nom au dessus du cadre d'informations
		 */
		info = new JTextField();
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setBounds(10, 25, 90, 25);
		contentPane.add(info);
		info.setColumns(10);
		info.setText("Informations : ");
		info.setEditable(false);
		info.setBackground(SystemColor.activeCaption);
		info.setBorder(new MatteBorder(3, 3, 0, 3, (Color) Color.BLACK));

		/**
		 * Initialisation du traitement
		 */
		traitement = new Traitement(taille, debut, nbOtage);

		/**
		 * Calcul de la taille des différents composants de la fenêtre
		 */
		casex = 950/traitement.getTaille().getX();
		casey = 600/traitement.getTaille().getY();
		int taillex = casex*traitement.getTaille().getX();
		int tailley = casey*traitement.getTaille().getY();
		diffy = 600-tailley;
		diffx = 950-taillex;

		/**
		 * Mise en place de l'affichage de l'horloge
		 */
		date = new JTextField();
		date.setHorizontalAlignment(SwingConstants.LEFT);
		date.setBounds(1104-diffx, 25, 120, 25);
		contentPane.add(date);
		date.setColumns(10);
		date.setText(" Temps : 00:00:00");
		date.setEditable(false);
		date.setBackground(SystemColor.activeCaption);
		date.setBorder(new MatteBorder(3, 3, 0, 3, (Color) Color.BLACK));

		/**
		 * Mise en place du cadre contenant la carte
		 */
		PaintStrategy paintStrategy = new PaintStrategy();
		carte = new Display(traitement, paintStrategy);
		carte.addMouseListener(click);
		carte.setLayout(new BorderLayout());
		carte.setBackground(new Color(0, 128, 128));
		carte.setBounds(274, 50, taillex, tailley);
		carte.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		contentPane.add(carte);

		/**
		 * Mise en place des informations
		 */
		infoPanel = new Info(traitement, diffy, carte);
		contentPane.add(infoPanel);

		/**
		 * Propriétés de la fenêtre
		 */
		this.getContentPane().setBackground(Color.DARK_GRAY);
		ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("drone.png"));
		this.setIconImage(icon.getImage());
		this.setResizable(false);
		this.setSize(1250-diffx, 720-diffy);
		this.setBounds(300, 200, 1250-diffx, 720-diffy);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setVisible(true);
	}

	/**
	 * Thread
	 */
	@Override
	public void run(){
		/**
		 * Création de la fenêtre
		 */
		try {
			init(debut, taille);
		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * Initialisation de l'horloge
		 */
		chronometre = new Chronometre();
		
		/**
		 * Mise en place de l'état du scan
		 */
		int state = 0;
		int timer = 0;
		int s = 0;
		while (running) {

			/**
			 * Si le scan n'est pas terminé
			 * Mise à jour des informations sans remettre la liste à zéro
			 * Mise à jour de la carte
			 * Avancement du temps
			 */
			if(state == 0){
				try {
					Thread.sleep(Configuration.SCAN_SPEED);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				state = traitement.scan();
				infoPanel.majGUI(false);
				carte.repaint();
				timer = timer + Configuration.SCAN_SPEED;
			}

			/**
			 * Si le scan a rencontré un changement
			 * Mise à jour des informations sans remettre la liste à zéro
			 * Mise à jour de la carte
			 * Avancement du temps
			 */
			else{
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(traitement.animate() == 1){
					infoPanel.majGUI(true);
					carte.repaint();
				}
				timer = timer + Configuration.BASE_SPEED*(Configuration.BASE_SPEED/speed);
			}

			/**
			 * Mise à jour de l'horloge
			 */
			if(timer/1000 > s){
				for(int i=0 ; i<timer/1000-s ; i++){
					incrementer();
				}
				s = timer/1000;
			}
		}
		
		/**
		 * Libération de la mémoire
		 */
		traitement.supp();
	}

	/**
	 * Arrêter le Thread
	 */
	public void stop(){
		running = false;
	}

	/**
	 * Incrémente le chronomètre
	 */
	public void incrementer(){
		chronometre.incrementer();
		date.setText(" Temps : " + chronometre.getTimer());
	}

	/**
	 * Actions de la barre de menu
	 */
	private class ActionBar implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			/**
			 * Acition pour quitter
			 */
			if(e.getSource()==quitter) {
				System.exit(0);
				logger.info("Fermeture de l'application");
			}

			/**
			 * Action pour passer en thème sombre
			 */
			if(e.getSource()==sombre) {
				contentPane.setBackground(Color.DARK_GRAY);
				logger.info("Passage au thème sombre");
				menu.setBackground(Color.LIGHT_GRAY);//Couleur de l'arrière plan de la barre de menu
				Aide.setBackground(Color.LIGHT_GRAY);
				Aide.setForeground(null);
				Fichier.setForeground(null);
				Apparence.setForeground(null);
				numero.setForeground(Color.white);
				vitesse.setBackground(Color.DARK_GRAY);
				info.setBackground(SystemColor.activeCaption);
				info.setForeground(null);
				date.setBackground(SystemColor.activeCaption);
				date.setForeground(null);
				nomcarte.setBackground(SystemColor.activeCaption);
				nomcarte.setForeground(null);
				infoPanel.setButtonBackground(SystemColor.activeCaption);
				infoPanel.setButtonForeground(null);
				infoPanel.setButtonBackground(SystemColor.activeCaption);
				infoPanel.setButtonForeground(null);
				infoPanel.setInfoBackground(new Color(204, 190, 121));
			}

			/**
			 * Action pour passer en thème clair
			 */
			if(e.getSource()==clair) {
				contentPane.setBackground(new Color(145,203,222));
				logger.info("Passage au thème clair");
				menu.setBackground(new Color(84,96,143));//Couleur de l'arrière plan de la barre de menu
				Aide.setBackground(new Color(84,96,143));
				Aide.setForeground(Color.white);
				Fichier.setForeground(Color.white);
				Apparence.setForeground(Color.white);
				numero.setForeground(null);
				vitesse.setBackground(new Color(145,203,222));
				info.setBackground(new Color(84,96,143));
				info.setForeground(Color.white);
				date.setBackground(new Color(84,96,143));
				date.setForeground(Color.white);
				nomcarte.setBackground(new Color(84,96,143));
				nomcarte.setForeground(Color.white);
				infoPanel.setButtonBackground(new Color(84,96,143));
				infoPanel.setButtonForeground(Color.white);
				infoPanel.setButtonBackground(new Color(84,96,143));
				infoPanel.setButtonForeground(Color.white);
				infoPanel.setInfoBackground(Color.white);
			}

			/**
			 * Action affichant l'aide
			 */
			if(e.getSource()==Aide) {
				JOptionPane.showMessageDialog(OtageGUI.this, "Bienvenue sur Vision Détection ! \n\nNotre application vous permet de repérer le nombre d'aissaillants lors d'une prise d'otage.\nVous devrez entrer des coordonnées de départ et des coordonnées d'arrivée pour que le drone puisse s'envoler et survoler l'endroit désiré. \n\nA gauche, de nombreuse informations sont disponibles pour que vous puissiez localiser l'endroit précis de ces malfaiteurs.\nIl est aussi possible de déterminer leur nombre précis grâce aux images renvoyées en temps réel.\n\nAidez-nous à sauvez les otages en collaborant avec les forces de l'ordre !\n", "Aide", JOptionPane.INFORMATION_MESSAGE);
				logger.info("Affichage de l'aide");
			}

			/**
			 * Action fermant la fenêtre actuelle et renvoyant vers la fenêtre d'accueil
			 */
			if(e.getSource()==recherche) {
				VisionGUI fen = new VisionGUI();
				OtageGUI.this.setVisible(false);
				OtageGUI.this.stop();
				logger.info("Retour à la fenêtre principale");
			}
		}
	}

	/**
	 * Actions de la souris sur la carte
	 */
	private class Click implements MouseListener {
		/**
		 * Mise en place des évènement (actions réalisés lors d'une interraction avec la souris
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX()/casex;
			int y = e.getY()/casey;
			Element selected = traitement.getCarte().getElement(x, y);
			traitement.setSelected(selected);
			infoPanel.majGUI(false);
			carte.repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}
	}

	/**
	 * Actions du curseur de vitesse de simulation
	 */
	private class Slider implements ChangeListener {
		/**
		 * Changement de valeur
		 */
		@Override
		public void stateChanged(ChangeEvent e) {
			numero.setText("Vitesse de simulation : " + vitesse.getValue());
			switch (vitesse.getValue()){
				case 1:
					speed = Configuration.BASE_SPEED;
					break;
				case 2:
					speed = Configuration.BASE_SPEED/2;
					break;
				case 3:
					speed = Configuration.BASE_SPEED/3;
					break;
				case 4:
					speed = Configuration.BASE_SPEED/4;
					break;
				case 5:
					speed = Configuration.BASE_SPEED/5;
					break;
				case 6:
					speed = Configuration.BASE_SPEED/6;
					break;
				case 7:
					speed = Configuration.BASE_SPEED/7;
					break;
				case 8:
					speed = Configuration.BASE_SPEED/8;
					break;
				case 9:
					speed = Configuration.BASE_SPEED/9;
					break;
				case 10:
					speed = Configuration.BASE_SPEED/10;
					break;
			}
		}
	}
}
