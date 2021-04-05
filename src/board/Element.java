package board;

import board.elements.Piece;
import board.elements.Slow;
import board.elements.Star;
import board.elements.Wall;

public class Element {
    double x,y;
    ElementType type;
    public Piece piece;
    public Star star;
    public Slow slow;
    public Wall wall;

    public ElementType getType() {
        return type;
    }

    public  Element(Piece p){
       x=p.getOldX();
       y=p.getOldY();
       piece=p;
       type=ElementType.PIECE;
    }

    public Element(Wall w){
        x=w.getOldX();
        y=w.getOldY();
        wall=w;
        type=ElementType.WALL;

    }

    public  Element(Star s){
        x=s.getOldX();
        y=s.getOldY();
        star=s;
        type=ElementType.STAR;
    }
    public  Element(Slow sl){
        x=sl.getOldX();
        y=sl.getOldY();
        slow=sl;
        type=ElementType.SLOW;
    }

}
