import java.util.ArrayList;

public class AmbiguousSentence{
    private ArrayList<AmbiguousSyntagm> syntagms;

    public AmbiguousSentence(){
        syntagms = new ArrayList<>();
    }

    public void add(Syntagm s){
        if(syntagms.isEmpty() || !s.forma.equals(syntagms.getLast().getAny().forma)){
            AmbiguousSyntagm as = new AmbiguousSyntagm();
            as.addMeaning(s);
            syntagms.add(as);
            return;
        }
        else {
            syntagms.getLast().addMeaning(s);
        }
    }

    public Sentence solve(){
        Sentence sentence = new Sentence();
        for (AmbiguousSyntagm as : syntagms) {
            Syntagm any = as.getAny();
            sentence.add(
                new Syntagm(any.forma, any.lemma, as.size() == 1 ? any.caratteristiche : "caratteristiche ambigue"));
            
        }
        return sentence;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AmbiguousSyntagm s : syntagms) {
            sb.append(s.toString()).append('\n');
        }
        return sb.toString();
    }
}