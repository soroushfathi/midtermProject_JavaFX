package main;

import elements.Element;
import elements.Piece;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final int id;

    public Element getPiece() {
        return piece;
    }

    private final Element piece;
    private int score;
    private List<Integer> limits=new ArrayList<>();


    public Player(int id, Piece p){
        this.id=id;
        piece=p;
    }


    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Integer> getLimits() {
        return limits;
    }

    public void setLimits(List<Integer> limits) {
        this.limits = limits;
    }
}
