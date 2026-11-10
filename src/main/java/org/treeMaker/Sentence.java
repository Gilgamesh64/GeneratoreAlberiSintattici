import java.util.ArrayList;

public class Sentence{
    private ArrayList<Syntagm> syntagms;

    public Sentence(){
        syntagms = new ArrayList<>();
    }

    public void add(Syntagm syntagm){
        syntagms.add(syntagm);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Syntagm s : syntagms) {
            sb.append(s.toString()).append("\n\n");
        }
        return sb.toString();
    }
}