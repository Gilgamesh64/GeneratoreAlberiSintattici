package org.treemaker.phrases;

public class Noun extends Phrase{
    private String gender;
    private String person;

    public Noun(String forma, String lemma, String caratteristiche) {
        super(forma, lemma, caratteristiche);

        String[] split = caratteristiche.split(":");
        gender = split[0];
        person = split[1];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append(super.toString())
            .append("NOUN, ")
            .append("gender: ").append(gender).append(", ")
            .append("person: ").append(person).append(", ")
            .toString();
    }
}
