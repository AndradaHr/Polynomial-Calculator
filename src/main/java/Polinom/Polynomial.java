package Polinom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
    // TreeMap este folosit pentru a stoca termenii polinomului, cu exponentul ca si cheie și coeficientul ca si valoare
    private Map<Integer, Double> terms;

    public Polynomial(Map<Integer, Double> terms) {
        this.terms = terms;
    }

    public Polynomial() {
        terms = new TreeMap<Integer, Double>();

    }
    public Map<Integer, Double> getTerms() {
        return terms;
    }

    // Metoda preiaRegex extrage coeficientii si exponentii dintr-un sir si ii adauga in TreeMap-ul terms
    public void preiaRegex(String s){
        // grup1=coeficient
        // grup2=^+puterea
        // grup3=puterea
        // grup4=doar coeficient
        // Inlocuim - cu +- pentru a facilita procesarea ulterioara
        String input = s.replace("-", "+-");
        String pattern = "([+-]?\\d*\\.?\\d*)[xX](\\^(\\d+))?|([+-]?\\d+\\.?\\d*)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        // Parcurgem toate potrivirile
        while (m.find()) {
            if (m.group(4) != null) {// am doar coeficient
                // Adaugam coeficientul in mapul terms asocindu-l cu puterea 0
                this.terms.put(0, Double.parseDouble(m.group(4)));
            } else {
                // Verificam daca avem coeficient pozitiv sau negativ
                if (m.group(1).equals("-") || m.group(1).equals("") || m.group(1).equals("+")) {
                    if (m.group(1).equals("-")) {
                        if (m.group(2) == null)// -x (coef negativ, fara putere)
                            this.terms.put(1, -1.0);
                        else
                            this.terms.put((Integer.parseInt(m.group(3))), -1.0);// -x la o putere
                    } else {// daca e pozitiv coeficientul
                        if (m.group(2) == null)// nu am putere specificata
                            this.terms.put(1, 1.0);// x
                        else
                            this.terms.put(Integer.parseInt(m.group(3)), 1.0);// x la putere
                    }
                } else {// daca am semn +coeficient
                    if (m.group(2) == null)// nu am putere
                        this.terms.put(1, Double.parseDouble(m.group(1)));// coeficient si x cu puterea 1
                    else// altfel adaugam coef cu x si puterea specificata
                        this.terms.put(Integer.parseInt(m.group(3)), Double.parseDouble(m.group(1)));
                }
            }
        }
    }

    public String toString() {
        // Creaza un StringBuilder pt a construi sirul de caractere al polinomului
        StringBuilder sb = new StringBuilder();
        // Cream o lista cu toti exponentii din terms si ii ordonam descrescator
        List<Integer> sortedExponents = new ArrayList(terms.keySet());
        Collections.sort(sortedExponents, Collections.reverseOrder());
       // Parcurgem exponentii
        for (int exponent : sortedExponents) {
            double coefficient = terms.get(exponent); //coef asociat cu exp curent
            if (coefficient == 0) {
                // Se ignora coef 0
                continue;
            }
            // Punem + daca avem coef pozitiv si nu e primul termen al polinomului
            if (coefficient > 0 && sb.length() > 0) {
                sb.append(" + ");
            } else if (coefficient < 0) {
                // Daca coef e negativ punem - si face coef pozitiv pt afisare
                sb.append(" - ");
                coefficient = -coefficient;
            }

            // Verificam ce fel de coef avem intreg sau real cu zecimale
            // Si il formatam ca strinf in functie de valoarea sa
            String coefficientString;
            if (coefficient == (int)coefficient) {
                coefficientString = String.format("%d", (int)coefficient);
            } else if (Math.round(coefficient * 10.0) / 10.0 == (int)(Math.round(coefficient * 10.0) / 10.0)) {
                coefficientString = String.format("%.1f", coefficient);
            } else {
                coefficientString = String.format("%.3f", coefficient);
            }
            // Adaugam coef si exp la sirul de caractere in functie de caz
            if (exponent == 0) {
                sb.append(coefficientString);
            } else if (coefficient == 1) {
                sb.append("x");
                if (exponent > 1) {
                    sb.append("^").append(exponent);
                }
            } else {
                sb.append(coefficientString).append("x");
                if (exponent > 1) {
                    sb.append("^").append(exponent);
                }
            }
        }
        // Daca nu exista termeni in sirul de caractere scriem 0
        if (sb.length() == 0) {
            sb.append("0");
        }
        return sb.toString();
    }


    public int getDegree() {
        int maxPower = -1;
        for (int power : terms.keySet()) {
            if (power > maxPower) {
                maxPower = power;
            }
        }
        return maxPower;
    }

    public double getLeadingCoefficient() {
        int degree = getDegree();
        return terms.get(degree);
    }


}



