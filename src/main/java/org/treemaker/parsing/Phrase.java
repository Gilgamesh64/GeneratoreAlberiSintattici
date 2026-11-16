package org.treemaker.parsing;

public class Phrase {
    protected String forma;
    protected String lemma;
    protected String caratteristiche;

    Phrase(String forma, String lemma, String caratteristiche){
        this.forma = forma;
        this.lemma = lemma;
        this.caratteristiche = caratteristiche;
    }

    public static Phrase from(String forma, String lemma, String caratteristiche){
        String category = "";
        if(caratteristiche.matches("[A-Z]+")){
            category = caratteristiche;
        }
        else{
            String withColon = caratteristiche.split(":", -1)[0];
            String withDash = caratteristiche.split("-", -1)[0];
            if(withColon.matches("[A-Z]+")){
                category = withColon;
            }
            else if(withDash.matches("[A-Z]+")){
                category = withDash;
            }
        }

        String restOf = caratteristiche.replace(category, "").substring(1);
        return switch (category){
            case "VER" -> new Verb(forma, lemma, restOf);
            case "NOUN" -> new Noun(forma, lemma, restOf);
            case "ART" -> new Article(forma, lemma, restOf);
            case "UNKNOWN" -> new Phrase(forma, lemma, restOf);
            default -> new Phrase(forma, lemma, restOf);
        };
    }

    @Override
    public String toString() {
        return "Forma: " + forma + ", Lemma: " + lemma + ", ";
    }
}