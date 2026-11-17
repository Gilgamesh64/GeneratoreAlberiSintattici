package org.treemaker.parsing;

import java.util.ArrayList;

import org.treemaker.phrases.Article;
import org.treemaker.phrases.Noun;
import org.treemaker.phrases.Phrase;
import org.treemaker.phrases.Verb;

public class AmbiguousSentence{
    private ArrayList<AmbiguousPhrase> phrases;

    public AmbiguousSentence(){
        phrases = new ArrayList<>();
    }

    public void add(Phrase s){
        if(phrases.isEmpty() || !s.getForma().equals(phrases.getLast().getAny().getForma())){
            AmbiguousPhrase as = new AmbiguousPhrase();
            as.addMeaning(s);
            phrases.add(as);
            return;
        }
        else {
            phrases.getLast().addMeaning(s);
        }
    }

    public Sentence solve(){
        Sentence sentence = new Sentence();
        boolean articleFound = false;
        boolean nounFound = false;
        boolean verbFound = false;

        for (AmbiguousPhrase ap : phrases) {

            if(ap.countMeanings() == 1){
                sentence.add(ap.getOnly());
                if(ap.getOnly() instanceof Article) articleFound = true;
                if(ap.getOnly() instanceof Noun) nounFound = true;
                if(ap.getOnly() instanceof Verb) verbFound = true;
            }
            else{
                if(ap.containsMeaning(Article.class) && !articleFound){
                    articleFound = true;
                    sentence.add(ap.getPhrase(Article.class));
                    continue;
                }
                if(ap.containsMeaning(Noun.class) && !nounFound){
                    nounFound = true;
                    sentence.add(ap.getPhrase(Noun.class));
                    continue;
                }
                if(ap.containsMeaning(Verb.class) && !verbFound){
                    verbFound = true;
                    ap.getAllPhrases(Verb.class).forEach(System.out::println);
                    System.out.println();
                    sentence.add(ap.getAllPhrases(Verb.class).getFirst());
                    continue;
                }
                
            }
        }
        
        return sentence;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AmbiguousPhrase ap : phrases) {
            sb.append(ap).append("\n\n");
        }
        return sb.toString();
    }
}