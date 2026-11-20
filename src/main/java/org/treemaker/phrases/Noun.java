package org.treemaker.phrases;

public class Noun extends Phrase{
    private String gender;
    private String person;

    public Noun(String form, String lemma, String features) {
        super(form, lemma, features);

        String[] split = features.split(":");
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
