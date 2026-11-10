import java.util.ArrayList;

public class AmbiguousSyntagm{
    private ArrayList<Syntagm> meanings;

    public AmbiguousSyntagm(){
        meanings = new ArrayList<>();
    }

    public void addMeaning(Syntagm meaning){
        meanings.add(meaning);
    }

    public Syntagm getAny(){
        return meanings.getFirst();
    }

    public int size(){
        return meanings.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Syntagm s : meanings){
            sb.append(s + "\n");
        }
        return sb.toString();
    }
}