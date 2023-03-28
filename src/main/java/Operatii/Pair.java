package Operatii;

// Folosim o clasa generica pentru a retine o pereche
// O folosim in cadrul impartirii care returneaza o pereche care stocheaza doua polinoame
// Mai exact stocheaza catul si retul impartirii
public class Pair<A, B> {
    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }
    public String toString() {
        return "Quotient " + first.toString() + "   Remainder " + second.toString() ;
    }
}
