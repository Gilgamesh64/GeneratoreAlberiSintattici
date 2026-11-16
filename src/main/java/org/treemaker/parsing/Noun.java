package org.treemaker.parsing;

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
        return super.toString() + "gender: " + gender + ", person: " + person;
    }
}
