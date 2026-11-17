package org.treemaker.phrases;

public class Article extends Phrase{

    private String gender;
    private String person;
    private boolean isPre;
    
    Article(String forma, String lemma, String caratteristiche, boolean isPre) {
        super(forma, lemma, caratteristiche);

        String[] split = caratteristiche.split(":");
        gender = split[0];
        person = split[1];

        this.isPre = isPre;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append(super.toString())
            .append(isPre ? "ARTPRE, " : "ART, ")
            .append("gender: ").append(gender).append(", ")
            .append("person: ").append(person).append(", ")
            .toString();
    }
}
