public class Syntagm{
    protected String forma;
    protected String lemma;
    protected String caratteristiche;

    public Syntagm(String forma, String lemma, String caratteristiche){
        this.forma = forma;
        this.lemma = lemma;
        this.caratteristiche = caratteristiche;
    }

    @Override
    public String toString() {
        return "Forma: " + forma + "\nLemma: " + lemma + "\nCaratteristiche: " + caratteristiche;
    }
}