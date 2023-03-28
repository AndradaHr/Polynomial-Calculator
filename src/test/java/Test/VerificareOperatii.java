package Test;

import Operatii.Operation;
import Operatii.Pair;
import Polinom.Polynomial;
import org.junit.Before;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class VerificareOperatii {
    private Polynomial p1;
    private Polynomial p2;

    @Before
    public void setUp(){
        p1 = new Polynomial(new HashMap<Integer, Double>() {{
            put(3, 4.0);
            put(2, 3.0);
            put(1, 1.0);
            //4x^3 + 3x^2 + x
        }});
        p2 = new Polynomial(new HashMap<Integer, Double>() {{
            put(2, 2.0);
            put(1, 1.0);
            put(0, 2.0);
            //2x^2+ x + 2
        }});
    }
    @org.junit.Test
    public void adunareTest(){
        Polynomial rezultat;
        rezultat= Operation.add(p1,p2);
        assertEquals("4x^3 + 5x^2 + 2x + 2",rezultat.toString());
     }

    @org.junit.Test
    public void scadereTest(){
        Polynomial rezultat;
        rezultat=Operation.subtract(p1,p2);
        assertEquals("4x^3 + x^2 - 2",rezultat.toString());
    }
    @org.junit.Test
    public void inmultireTest(){
        Polynomial rezultat;
        rezultat=Operation.multiply(p1,p2);
        assertEquals("8x^5 + 10x^4 + 13x^3 + 7x^2 + 2x",rezultat.toString());
    }
    @org.junit.Test
    public void impartireTest(){
        Pair rezultat;
        rezultat=Operation.divide(p1,p2);
        assertEquals("Quotient 2x + 0.500   Remainder  - 3.500x - 1",rezultat.toString());
    }
    @org.junit.Test
    public void derivareTest(){
        Polynomial rezultat;
        rezultat=Operation.derivative(p1);
        assertEquals("12x^2 + 6x + 1",rezultat.toString());
    }
    @org.junit.Test
    public void integrareTest(){
        Polynomial rezultat;
        rezultat=Operation.integrate(p1);
        assertEquals("x^4 + x^3 + 0.500x^2",rezultat.toString());
    }

}
