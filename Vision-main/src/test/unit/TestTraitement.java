package test.unit;

import data.Coordonnees;
import moteur.Traitement;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestTraitement {

    /**
     * Traitement prise d'otage
     */
    private Traitement otage;

    /**
     * Traitement agricole
     */
    private Traitement agricole;

    /**
     * prepartaion
     */
    @Before
    public void prepareTraitement(){
        otage = new Traitement(new Coordonnees(10, 10), new Coordonnees(0, 0), 5);
        agricole = new Traitement(new Coordonnees(20, 20), new Coordonnees(10, 10));
    }

    /**
     * test traitement
     */
    @Test
    public void testTraitement(){
        assertNotNull(otage.getScenario());
        assertNotNull(otage.getCarte());
        assertTrue(otage.isOtage());
        assertNotNull(otage.getTaille());
        assertEquals(otage.getTaille().getX(), 10);
        assertEquals(otage.getTaille().getY(), 10);
        assertNotNull(otage.getDebut());
        assertEquals(otage.getDebut().getX(), 0);
        assertEquals(otage.getDebut().getY(), 0);

        assertNotNull(otage.getEntoure());
        assertNotNull(otage.getIndividu());

        assertEquals(otage.getNbAssaillant(), 0);
        assertEquals(otage.getNbIndividu(), 0);
        assertEquals(otage.getNbOtage(), 5);


        assertNotNull(agricole.getScenario());
        assertNotNull(agricole.getCarte());
        assertFalse(agricole.isOtage());
        assertNotNull(agricole.getTaille());
        assertEquals(agricole.getTaille().getX(), 20);
        assertEquals(agricole.getTaille().getY(), 20);
        assertNotNull(agricole.getDebut());
        assertEquals(agricole.getDebut().getX(), 10);
        assertEquals(agricole.getDebut().getY(), 10);

        assertNotNull(agricole.getEntoure());
        assertNotNull(agricole.getIntrusion());
        assertNotNull(agricole.getFeu());
        assertNotNull(agricole.getMaladie());
        assertNotNull(agricole.getInconnue());

        assertEquals(agricole.getNbIntrusion(), 0);
        assertEquals(agricole.getNbFeu(), 0);
        assertEquals(agricole.getNbMaladie(), 0);
        assertEquals(agricole.getNbInconnue(), 0);

    }

    /**
     * test supp
     */
    @Test
    public void testSupp(){
        otage.supp();
        assertEquals(otage.getNbIndividu(), 0);
        assertEquals(otage.getNbAssaillant(), 0);
        assertEquals(otage.getNbOtage(), 0);

        assertNull(otage.getIndividu());

        assertNull(otage.getCarte());
        assertNull(otage.getScenario());


        agricole.supp();
        assertEquals(agricole.getNbIntrusion(), 0);
        assertEquals(agricole.getNbFeu(), 0);
        assertEquals(agricole.getNbMaladie(), 0);
        assertEquals(agricole.getNbInconnue(), 0);

        assertNull(agricole.getIntrusion());
        assertNull(agricole.getFeu());
        assertNull(agricole.getMaladie());
        assertNull(agricole.getInconnue());

        assertNull(agricole.getCarte());
        assertNull(agricole.getScenario());

    }

}
