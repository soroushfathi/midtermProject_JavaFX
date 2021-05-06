package elements;

import javafx.scene.control.*;
import javafx.stage.StageStyle;
import pages.PrepareBoard;

import java.util.Optional;


public class DropdownMenu extends ContextMenu {
    public DropdownMenu(boolean removeAble, int x, int y) {

        Menu AddItem = new Menu(PrepareBoard.getBoard()[x][y].hasElement() ? "Replace" : "Add");
        MenuItem wall = new MenuItem("Wall");
        MenuItem slow = new MenuItem("Slow");
        MenuItem star = new MenuItem("Star");

        AddItem.getItems().addAll(wall, slow, star);


        MenuItem removeItem = new MenuItem("Remove");
        removeItem.setDisable(!removeAble);

        wall.setOnAction(e -> {
            if (PrepareBoard.getBoard()[x][y].hasElement()) {
                PrepareBoard.getBoard()[x][y].getElement().setVisible(false);
            }
            PrepareBoard.getWalls()[x][y].setVisible(true);
            PrepareBoard.getBoard()[x][y].setElement(PrepareBoard.getWalls()[x][y]);
        });

        slow.setOnAction(e -> {

            TextInputDialog dialog = new TextInputDialog();

            dialog.setTitle("Set value");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.setContentText("Pleas enter slow value:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(s ->{
                try {
                    int value = Integer.parseInt(s);
                    if (PrepareBoard.getBoard()[x][y].hasElement()) {
                        PrepareBoard.getBoard()[x][y].getElement().setVisible(false);
                    }
                    PrepareBoard.getSlows()[x][y].setVisible(true);
                    PrepareBoard.getSlows()[x][y].setValue(value);
                    PrepareBoard.getBoard()[x][y].setElement(PrepareBoard.getSlows()[x][y]);
                    Tooltip.install(
                            PrepareBoard.getSlows()[x][y],
                            new Tooltip("Value is: "+value)
                    );
                }catch(Exception ex){
                    Error error=new Error("Value is not valid");
                }
            } );

        });

        star.setOnAction(e -> {
            if (PrepareBoard.getBoard()[x][y].hasElement()) {
                PrepareBoard.getBoard()[x][y].getElement().setVisible(false);
            }
            PrepareBoard.getStars()[x][y].setVisible(true);
            PrepareBoard.getBoard()[x][y].setElement(PrepareBoard.getStars()[x][y]);
        });

        removeItem.setOnAction(e -> {
            PrepareBoard.getBoard()[x][y].getElement().setVisible(false);
            PrepareBoard.getBoard()[x][y].setElement(null);
        });
        // Add MenuItem to ContextMenu
        getItems().addAll(AddItem, removeItem);
    }

}
