package org.treemaker.phrases;

public class Adjective extends Phrase{

    private String gender;
    private String person;
    private boolean isPos;
    
    Adjective(String forma, String lemma, String caratteristiche) {
        super(forma, lemma, caratteristiche);

        String[] split = caratteristiche.split("\\+");
        isPos = split[0].equals("pos");
        gender = split[1];
        person = split[2];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append(super.toString())
            .append("ADJ, ")
            .append("gender: ").append(gender).append(", ")
            .append("person: ").append(person).append(", ")
            .append("type: ").append(isPos ? "pos" : "sup")
            .toString();
    }
}
