package pages;

import elements.Loading;
import elements.LoadingType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.DataTransfer;
import main.Search;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static main.Config.PLAY_TYPE;

public class PlayType extends GridPane {
    public PlayType() {
        setPadding(new Insets(10, 10, 10, 10));
        setVgap(5);
        setHgap(5);

        Button local = new Button("Play on LOCAL");
        setConstraints(local, 0, 0);
        local.setPrefWidth(200);
        getChildren().add(local);


        Button network = new Button("Search for NETWORK");
        setConstraints(network, 0, 1);
        network.setPrefWidth(200);
        getChildren().add(network);

        ListView<String> list = new ListView<String>();
        setConstraints(list, 0, 2);
        list.setPrefSize(200, 200);
        getChildren().add(list);

        local.setOnAction(e -> {
            PrepareBoard pBoard = new PrepareBoard();
            PrepareBoard board = new PrepareBoard();
            Stage stage = new Stage();
            stage.setTitle("Game");
            stage.setScene(new Scene(board.build()));
            stage.show();

            ((Node) (e.getSource())).getScene().getWindow().hide();
        });
        network.setOnAction(e -> {
            PLAY_TYPE = 1;
            Search task = new Search();
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute(task);

            Alert alert = new Alert(Alert.AlertType.WARNING);

            task.setOnRunning(__ -> {

                alert.initStyle(StageStyle.UNDECORATED);
                alert.getDialogPane().setContent(new Loading(LoadingType.LOAD));
                alert.setHeaderText("Search servers");
                alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
                alert.show();
            });
            task.setOnSucceeded(__ -> {
                alert.close();
                executorService.shutdown();
                var servers = Search.servers;
                ObservableList<String> items = FXCollections.observableArrayList();

                for (var server : servers)
                    items.add(server.name + ":" + server.port + "   " + server.player + "/" + server.size);
                list.setItems(items);
                list.setCellFactory((ListView<String> l) -> new ColoredCell());
            });
        });

    }

    private static class ColoredCell extends ListCell<String> {

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                boolean type = item.substring(item.indexOf(" ") + 3, item.indexOf("/")).equals(item.substring(item.indexOf("/") + 1));
                setText(item);
                setTextFill(type ? Color.RED : Color.GREEN);
                setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY) && !type) {
                        if (e.getClickCount() == 2) {
                            System.out.println("wrong");
                            for (var server : Search.servers) {
                                if (item.substring(0, item.indexOf(" ")).equals(server.name + ":" + server.port)) {

                                    if (server.password.length() > 0) {
                                        TextInputDialog dialog = new TextInputDialog();
                                        Optional<String> result;
                                        dialog.setTitle("Password");
                                        dialog.setHeaderText(null);
                                        dialog.setGraphic(null);
                                        dialog.initStyle(StageStyle.UNDECORATED);
                                        dialog.setContentText("Pleas enter password:");

                                        result = dialog.showAndWait();
                                        result.ifPresent(p -> {
                                            if (p.equals(server.password)) {
                                                Thread t = new Thread(new DataTransfer(server));
                                                t.start();
                                            } else
                                                System.out.println("wrong");
                                        });
                                    } else {

                                        Thread t = new Thread(new DataTransfer(server));
                                        t.start();
                                    }


                                }
                            }

                        }
                    }
                });
            }
        }
    }
}
