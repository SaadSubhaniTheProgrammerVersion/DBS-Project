package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;

import javafx.scene.text.Text;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage PrimaryStage) {
        StackPane pane = new StackPane();
        Button temp = new Button();
        temp.setGraphic(new ImageView(new Image("sample/upload_button_progress_animation.gif")));
        pane.getChildren().add(temp);
        temp.setStyle("-fx-focus-color: transparent;");

        MenuBar menuBar = new MenuBar();
        VBox vBox = new VBox(menuBar);
        pane.getChildren().add(vBox);

        Menu menu1=new Menu("File");
        Menu menu2=new Menu("Edit");
        Menu menu3=new Menu("Options");
        menuBar.getMenus().add(menu1);
        menuBar.getMenus().add(menu2);
        menuBar.getMenus().add(menu3);


        Button btn = new Button("Upload Single File");
        btn.setMinWidth(150);
        btn.setMinHeight(40);
        btn.setTranslateX(-100);
        btn.setTranslateY(180);
        pane.getChildren().add(btn);

        Button btn2 =new Button("Upload Multiple Files ");
        btn2.setMinWidth(150);
        btn2.setMinHeight(40);
        btn2.setTranslateX(100);
        btn2.setTranslateY(180);
        pane.getChildren().add(btn2);

        Button submit=new Button("Submit");
        submit.setMinWidth(125);
        submit.setMinHeight(25);



        PrimaryStage.setTitle("Uploader Stage");
        Scene scene = new Scene(pane, 800, 600);
        PrimaryStage.setScene(scene);
        PrimaryStage.show();

        Stage SecondaryStage=new Stage();
        SecondaryStage.setTitle("Uploading");
        GridPane grid=new GridPane();
        Label label1 = new Label("File Name    ");
        Label label2 = new Label( "    Category    ");
        label1.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        label2.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));

        grid.getChildren().add(label1);
        grid.getChildren().add(label2);

        GridPane.setColumnIndex(label1,0);
        GridPane.setRowIndex(label1,0);
        GridPane.setColumnIndex(label2,3);
        GridPane.setRowIndex(label2,0);





        btn.setOnAction(e-> {
        FileChooser fc=new FileChooser();
        fc.selectedExtensionFilterProperty();
        fc.setTitle("Upload your files");

        File file=fc.showOpenDialog(PrimaryStage);
        String filename = String.valueOf(file);
        System.out.println(filename);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println(dateFormat.format(file.lastModified()));
            System.out.println(file.getName());
            String temp1=file.getName().toString();
            String[] Extension= temp1.split("\\.");
            System.out.println(Extension[Extension.length-1]);

            Scene scene2=new Scene(grid,425,200);
            SecondaryStage.setTitle("Enter Category");
            SecondaryStage.setScene(scene2);


          Label l1=new Label(file.getName());
          grid.getChildren().add(l1);
            GridPane.setColumnIndex(l1,0);
            GridPane.setRowIndex(l1,1);

    TextField field=new TextField();


            grid.getChildren().add(field);
            GridPane.setColumnIndex(field,3);
            GridPane.setRowIndex(field,1);

            grid.getChildren().add(submit);
            GridPane.setColumnIndex(submit,2);
            GridPane.setRowIndex(submit,2);
            submit.setTranslateX(50);
            submit.setTranslateY(100);
            SecondaryStage.show();
            submit.setOnAction(l->{
                String Category=field.getText();
                System.out.println("Category of the file is: ");
                System.out.println(Category);
            });
        } );

        btn2.setOnAction(e->{
            FileChooser fileChooser2 = new FileChooser();
            fileChooser2.setTitle("Upload Multiple Files");
            fileChooser2.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
            List<File> f=fileChooser2.showOpenMultipleDialog(null);
           for(File file:f) {
               System.out.println(file.getAbsolutePath());
               DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
               System.out.println(dateFormat.format(file.lastModified()));
               String temporary=file.getName();
               System.out.println(file.getName());

               String temp1=file.getName().toString();
               String[] Extension= temp1.split("\\.");
               System.out.println(Extension[Extension.length-1]);

           }

        });



    }


}

