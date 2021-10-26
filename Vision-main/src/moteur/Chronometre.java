package moteur;

/**
 * Chronomètre
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 1
 */
public class Chronometre {

    /**
     * Seconde
     */
    private int s;

    /**
     * Minute
     */
    private int m;

    /**
     * Heure
     */
    private int h;

    /**
     * Constructeur, initialise les variables
     */
    public Chronometre() {
        this.s = 0;
        this.m = 0;
        this.h = 0;
    }

    /**
     * Incrémenter
     */
    public void incrementer(){
        s++;
        if(s>59){
            s = 0;
            m++;
            if(m>59){
                m = 0;
                h++;
            }
        }
    }

    /**
     * Retourne le temps dans un format hh:mm:ss
     * @return Timer
     */
    public String getTimer(){
        String secondes = String.valueOf(s);
        String minutes = String.valueOf(m);
        String heures = String.valueOf(h);
        if(s<10){
            secondes = "0"+secondes;
        }
        if(m<10){
            minutes = "0"+minutes;
        }
        if(h<10){
            heures = "0"+heures;
        }
        return heures+":"+minutes+":"+secondes;
    }
}
