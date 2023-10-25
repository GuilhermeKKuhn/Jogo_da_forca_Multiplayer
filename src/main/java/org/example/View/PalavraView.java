package org.example.View;
import org.example.Difficulty;

import java.util.ArrayList;
import java.util.List;
public class PalavraView {



    private Difficulty difficulty;
    private String palavra;
    private List<String> dicas;


    public PalavraView(Difficulty difficulty, String palavra) {
        this.difficulty = difficulty;
        this.palavra = palavra;
        this.dicas = new ArrayList<>();
    }


    public PalavraView addDica(String value) {
        this.dicas.add(value);
        return this;
    }


    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public List<String> getDicas() {
        return dicas;
    }

}
