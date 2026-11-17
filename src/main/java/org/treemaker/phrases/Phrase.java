package org.treemaker.phrases;

public class Phrase {
    protected String forma;
    protected String lemma;

    Phrase(String forma, String lemma, String caratteristiche){
        this.forma = forma;
        this.lemma = lemma;
    }

    public static Phrase from(String forma, String lemma, String caratteristiche) throws IllegalStateException{
        String category = "";
        String regex = "[A-Z]+";
        if(caratteristiche.matches(regex)){
            category = caratteristiche;
        }
        else{
            String withColon = caratteristiche.split(":", -1)[0];
            String withDash = caratteristiche.split("-", -1)[0];
            if(withColon.matches(regex)){
                category = withColon;
            }
            else if(withDash.matches(regex)){
                category = withDash;
            }
        }

        String restOf = caratteristiche.replace(category, "").substring(1);
        return switch (category){
            case "VER" -> new Verb(forma, lemma, restOf);
            case "NOUN" -> new Noun(forma, lemma, restOf);
            case "ART" -> new Article(forma, lemma, restOf, false);
            case "ARTPRE" -> new Article(forma, lemma, restOf, true);
            case "ADJ" -> new Adjective(forma, lemma, restOf);
            default -> throw new IllegalStateException(
                "Illegal category for phrase with forma: " + forma + ", lemma: " + ", caratteristiche: " + caratteristiche);
        };
    }

    public String getForma() { return forma; }
    public String getLemma() { return lemma; }

    @Override
    public String toString() {
        return "Forma: " + forma + ", Lemma: " + lemma + ", ";
    }
}