package org.treemaker.phrases;

public class Verb extends Phrase{
    private String mode;
    private String tense;
    private Integer number;
    private String person;
    private String gender;

    public Verb(String forma, String lemma, String caratteristiche) {
        super(forma, lemma, caratteristiche);

        String[] split = caratteristiche.split("\\+");
        if(split.length == 2){
            mode = split[0];
            tense = split[1];
        }
        else if(split.length == 4){
            if(split[0].equals("part")){
                mode = split[0];
                tense = split[1];
                person = split[2];
                gender = split[3];
            }
            else{
                mode = split[0];
                tense = split[1];
                number = Integer.parseInt(split[2]);
                person = split[3];
            }
            
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append(super.toString())
            .append("VER, ")
            .append("mode: ").append(mode).append(", ")
            .append("tense: ").append(tense).append(", ")
            .append("number: ").append(number).append(", ")
            .append("person: ").append(person).append(", ")
            .append("gender: ").append(gender)
            .toString();
    }
}
