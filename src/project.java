import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.scene.transform.Scale;
import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.scene.PerspectiveCamera;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.collections.*;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.event.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class project extends Application {
    static boolean flag = true;

    boolean isFree(int i, int j) {
        return (i >= 0 && j >= 0 && i < 5 && j < 5);
    }

    void setValue(Button[][] arr, int i, int j) {
        int z;
        if (isFree(i, j + 1)) {
            changeColor(arr[i][j + 1], arr[i][j]);
            z = Integer.parseInt(arr[i][j + 1].getText()) + 1;
            arr[i][j + 1].setText(String.valueOf(z));
        }
        if (isFree(i + 1, j)) {
            changeColor(arr[i + 1][j], arr[i][j]);
            z = Integer.parseInt(arr[i + 1][j].getText()) + 1;
            arr[i + 1][j].setText(String.valueOf(z));
        }
        if (isFree(i - 1, j)) {
            changeColor(arr[i - 1][j], arr[i][j]);
            z = Integer.parseInt(arr[i - 1][j].getText()) + 1;
            arr[i - 1][j].setText(String.valueOf(z));
        }
        if (isFree(i, j - 1)) {
            changeColor(arr[i][j - 1], arr[i][j]);
            z = Integer.parseInt(arr[i][j - 1].getText()) + 1;
            arr[i][j - 1].setText(String.valueOf(z));
        }
        arr[i][j].setStyle("");
    }

    void switchColor(Button[][] arr, int i, int j) {
        if (flag) {
            arr[i][j].setStyle("-fx-background-color: #FC4037; ");
            flag = false;
        } else {
            arr[i][j].setStyle("-fx-background-color: #2B62F9; ");
            flag = true;
        }
    }

    void changeColor(Button b1, Button b2) {
        b1.setStyle(b2.getStyle());
    }

    void check(Button[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (Integer.parseInt(arr[i][j].getText()) > 3) {
                    setValue(arr, i, j);
                    arr[i][j].setText("0");
                    arr[i][j].setStyle("");
                    check(arr);
                    return;
                }
            }
        }
        return;
    }

    void Win(Button[][] arr) {
        boolean tempA = true,tempB=true;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j].getStyle().equals("-fx-background-color: #FC4037; ")) {
                    tempA = false;
                }
             }
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
            if (arr[i][j].getStyle().equals("-fx-background-color: #2B62F9; ")) {
                    tempB = false;
                }
            }
        }
        if (tempA||tempB) {
            VBox root2 = new VBox();
            Label l = new Label("RED WINS");
            Label l2 = new Label("BLUE WINS");
            Label l3 = new Label("Close this window to Play Again");
            if (!tempA) {
                root2.getChildren().add(l);
            } else if (!tempB) {
                root2.getChildren().add(l2);
            }
            root2.getChildren().add(l3);
            root2.setAlignment(Pos.CENTER);
            Stage stage2 = new Stage();
            Scene s2 = new Scene(root2, 200, 100);
            stage2.setTitle("Window2");
            stage2.initModality(Modality.WINDOW_MODAL);
            stage2.setScene(s2);
            stage2.show();
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                    arr[i][j].setStyle("");
                    arr[i][j].setText("0");
                }
            }
        }

    }

    public void start(Stage stage1) throws Exception {
        GridPane root = new GridPane();
        Button[][] arr = new Button[5][5];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = new Button();
                arr[i][j].setText("0");
                arr[i][j].setMinSize(50, 50);
                arr[i][j].setShape(new Circle(10));
                root.add(arr[i][j], j, i, 1, 1);
            }
        }
        TextField tf = new TextField("RED'S TURN");
        Label la= new Label("Created By- Milind Nair and Satvik Jain");
        EventHandler<ActionEvent> actionevent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {
                for (int i = 0; i < arr.length; i++) {
                    for (int j = 0; j < arr[i].length; j++) {

                        if (ae.getSource() == arr[i][j]) {

                            if (Integer.parseInt(arr[i][j].getText()) < 3) {
                                arr[i][j].setText(String.valueOf(Integer.parseInt(arr[i][j].getText()) + 1));
                                switchColor(arr, i, j);
                                if (flag)
                                    tf.setText("RED'S TURN");
                                else
                                    tf.setText("BLUE'S TURN");
                            } else {
                                arr[i][j].setText("0");
                                setValue(arr, i, j);
                                flag = !flag;
                                if (flag)
                                    tf.setText("RED'S TURN");
                                else
                                    tf.setText("BLUE'S TURN");
                                check(arr);
                                Win(arr);
                            }
                        }
                    }
                }
            }
        };
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j].setOnAction(actionevent);
            }
        }
        tf.setAlignment(Pos.CENTER);
        root.setVgap(20);
        root.setHgap(20);
        root.setAlignment(Pos.CENTER);
        VBox groot = new VBox(30);
        groot.getChildren().addAll(tf, root, la);
        Scene s = new Scene(groot, 800, 500);
        stage1.setScene(s);
        stage1.setTitle("CHAIN REACTION 0.5");
        stage1.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
