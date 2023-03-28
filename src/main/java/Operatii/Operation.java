package Operatii;
import Polinom.Polynomial;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Operation {
    public static Polynomial add(Polynomial p1, Polynomial p2) {
        // Creeaza un map pentru termenii polinomului rezultat, initializat cu termenii polinomului p1
        Map<Integer, Double> terms = new HashMap(p1.getTerms());
        // Parcurge termenii polinomului p2
        for (Map.Entry<Integer, Double> term : p2.getTerms().entrySet()) {
            // Extrage puterea și coeficientul termenului curent
            int power = term.getKey();
            double coefficient = term.getValue();
            // Daca puterea termenului exista deja in maparea termenilor, adauga coeficientul la valoarea existenta
            if (terms.containsKey(power)) {
                terms.put(power, terms.get(power) + coefficient);
            } else {
                // Altfel, adauga un termen nou in Map cu puterea si coeficientul curent
                terms.put(power, coefficient);
            }
        }
        return new Polynomial(terms);
    }

    public static Polynomial subtract(Polynomial p1, Polynomial p2) {
        // Creeaza un Map nou pentru termenii polinomului rezultat, initializata cu termenii polinomului p1
        Map<Integer, Double> terms = new HashMap(p1.getTerms());
        // Parcurge termenii polinomului p2
        for (Map.Entry<Integer, Double> term : p2.getTerms().entrySet()) {
            // Extrage puterea și coeficientul termenului curent
            int power = term.getKey();
            double coefficient = term.getValue();
            // Daca puterea termenului exista deja in maparea termenilor, adauga coeficientul la valoarea existenta
            if (terms.containsKey(power)) {
                terms.put(power, terms.get(power) - coefficient);
            } else {
                // Altfel, adauga un termen nou in Map cu puterea si coeficientul curent
                terms.put(power, -coefficient);
            }
        }
        return new Polynomial(terms);
    }

    public static Polynomial multiply(Polynomial p1, Polynomial p2) {
        // Creeaza un Map pentru termenii polinomului rezultat
        Map<Integer, Double> terms = new HashMap();
        // Parcurge termenii polinomului p1 si p2
        for (Map.Entry<Integer, Double> term1 : p1.getTerms().entrySet()) {
            for (Map.Entry<Integer, Double> term2 : p2.getTerms().entrySet()) {
                // Calculează puterea si coeficientul termenului rezultat din inmulțirea celor doi termeni
                int power = term1.getKey() + term2.getKey();
                double coefficient = term1.getValue() * term2.getValue();
                if (terms.containsKey(power)) {
                    // Daca puterea termenului exista deja in harta termenilor, adauga coeficientul la valoarea existenta
                    terms.put(power, terms.get(power) + coefficient);
                } else {
                    // Altfel, adauga un termen nou in Map cu puterea si coeficientul curent
                    terms.put(power, coefficient);
                }
            }
        }
        return new Polynomial(terms);
    }


    public static Pair<Polynomial, Polynomial> divide(Polynomial dividend, Polynomial divisor) {
        // Verificam daca divizorul are toti coef 0->daca da aruncam o exceptie
        if (divisor.getDegree() == -1) {
            throw new IllegalArgumentException("Cannot divide by zero polynomial.");
        }
        // Initializam doua mapuri pentru cat si rest, in ordinea exponentilor fiind ordonate

        Map<Integer, Double> quotientTerms = new TreeMap();
        Map<Integer, Double> remainderTerms = new TreeMap(dividend.getTerms());

        // Cat timp gradul deimpartitului e mai mare sau egal cu a impartitorului
        while (dividend.getDegree() >= divisor.getDegree()) {
            // Calculam noul exponent si noul coeficient al catului bazat pe coef si exp deimpartitului si impartitorului
            int newExponent = dividend.getDegree() - divisor.getDegree();
            double newCoefficient = dividend.getLeadingCoefficient() / divisor.getLeadingCoefficient();
        // Adaugam noul membru in cat
            quotientTerms.put(newExponent, newCoefficient);

            //Parcurgem termenii polinomului impartitor si actualizam coeficientii si exponentii corespunzatori in maparea restului
            for (Map.Entry<Integer, Double> term : divisor.getTerms().entrySet()) {
                int exponent = term.getKey() + newExponent;
                double coefficient = term.getValue() * newCoefficient;
                if (remainderTerms.containsKey(exponent)) {
                    double newRemainderCoefficient = remainderTerms.get(exponent) - coefficient;
                    // Daca diferenta coeficientilor este aproape de zero (mai mica decat 1e-9), eliminam termenul respectiv
                    if (Math.abs(newRemainderCoefficient) < 1e-9) {
                        remainderTerms.remove(exponent);
                    } else {
                        remainderTerms.put(exponent, newRemainderCoefficient);
                    }
                } else {
                    remainderTerms.put(exponent, -coefficient);
                }
            }
            //Actualizam polinomul dividend cu termenii ramasi in harta remainderTerms.
            // Acest lucru va fi folosit in urmatoarea iteratie a buclei while
            dividend = new Polynomial(remainderTerms);
        }

      //  Odata ce bucla while s-a incheiat, cream doua noi obiecte de tip Polynomial, quotient si remainder, utilizand termenii stocati in hartile quotientTerms si remainderTerms
        Polynomial quotient = new Polynomial(quotientTerms);
        Polynomial remainder = new Polynomial(remainderTerms);
        // Returnam un obiect Pair care contine cele doua polinoame rezultate, quotient si remainder
        return new Pair(quotient, remainder);
    }


    public static Polynomial derivative(Polynomial p) {
        // Initializam un (Map) numita terms pentru a stoca termenii polinomului derivat
        Map<Integer, Double> terms = new HashMap();
        // Parcurgem termenii polinomului initial p utilizand o bucla for pentru fiecare intrare (Entry) din p.getTerms()
        for (Map.Entry<Integer, Double> term : p.getTerms().entrySet()) {
            // Pentru fiecare termen, extragem exponentul (power) si coeficientul (coefficient)
            int power = term.getKey();
            double coefficient = term.getValue();
            // Verificam daca exponentul (power) este mai mare decat 0. Daca este, atunci calculam coeficientul si exponentul termenului derivat, conform regulilor de derivare
            // Coeficientul derivatului este produsul dintre coeficientul initial si exponentul initial, iar exponentul derivatului este cu o unitate mai mic decat exponentul initial
            if (power > 0) {
                terms.put(power - 1, coefficient * power);
            }
        }
        return new Polynomial(terms);
    }

    public static Polynomial integrate(Polynomial p) {
        //Initializam un (Map) numit terms pentru a stoca termenii polinomului integrat
        Map<Integer, Double> terms = new HashMap();
        //Parcurgem termenii polinomului initial p folosind o bucla for pentru fiecare intrare (Entry) din maapare p.getTerms()
        for (Map.Entry<Integer, Double> term : p.getTerms().entrySet()) {
            //Pentru fiecare termen, extragem exponentul (power) si coeficientul (coefficient)
            int power = term.getKey();
            double coefficient = term.getValue();
            //Adaugam noul termen in terms, cu exponentul marit cu 1 (power + 1) si coeficientul impartit la noul exponent (coefficient / (power + 1))
            terms.put(power + 1, coefficient / (power + 1));
        }
        // Constanta de derivare
        terms.put(0, 0.0);
        return new Polynomial(terms);
    }
}