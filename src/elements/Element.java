package elements;

import board.Board;
import board.ElementType;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import main.Move;
import prepare.PrepareBoard;

import static main.Config.PREPARE;
import static main.Config.TILE_SIZE;

public class Element extends StackPane {
    protected int x,y;

    ElementType type;

    public ElementType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Element(int x, int y,ElementType type){
        this.x = x;
        this.y = y;
        this.type =type;
        move(x, y);
        setOnDragDetected(e->{
            Dragboard db = startDragAndDrop(TransferMode.ANY);
            ClipboardContent cb = new ClipboardContent();
            cb.putString(this.x + "|" + this.y);
            db.setContent(cb);
            e.consume();
        });

        setOnDragDropped(e -> {
            var data = e.getDragboard().getString();
            var x1 = Integer.parseInt(data.substring(0, data.indexOf("|")));
            var y1 = Integer.parseInt(data.substring(data.indexOf("|") + 1));
            int x2 = (int) e.getSceneX() / TILE_SIZE;
            int y2 = (int) e.getSceneY() / TILE_SIZE;
            if(PREPARE)
                Move.set(PrepareBoard.board,x1,y1,x2,y2);
            else
                Move.set(Board.board,x1,y1,x2,y2);
        });

    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
        relocate(x * TILE_SIZE, y * TILE_SIZE);
    }

}
