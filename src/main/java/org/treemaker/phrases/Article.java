package org.treemaker.phrases;

public class Article extends Phrase{

    private String gender;
    private String person;
    private boolean isPre;
    
    Article(String form, String lemma, String features, boolean isPre) {
        super(form, lemma, features);

        String[] split = features.split(":");
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
