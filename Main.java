package sample;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
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

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;

public class Main extends Application {
    Statement stmt;
    // sets up database connection
    public void initializeDB(){
        try{
            // load jdbc driver
            Class.forName("com.mysql.jdbc.Driver");
            // establish connection with a database
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/file_management", "root", "saadsubhani123");
            // create a statement
            stmt = con.createStatement();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


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


                Label cover=new Label("Do you want to upload a cover photo for "+file.getName()+" ?");
                cover.setFont(Font.font("Helvetica",15 ));

                AnchorPane cpane=new AnchorPane();
                AnchorPane.setTopAnchor(cover, 10.0);
                cpane.getChildren().add(cover);

                Scene coverscene=new Scene(cpane,475,200);
                Stage cstage=new Stage();
                cstage.setScene(coverscene);
                cstage.setTitle("Cover photo");
                cstage.show();

                Button yes=new Button("YES");
                Button no=new Button("NO");

                yes.setMinWidth(120);
                yes.setMinHeight(30);
                yes.setTranslateX(100);
                yes.setTranslateY(100);

                no.setMinWidth(120);
                no.setMinHeight(30);
                no.setTranslateX(250);
                no.setTranslateY(100);

                cpane.getChildren().add(yes);
                cpane.getChildren().add(no);

                no.setOnAction(n->{
                    cstage.close();
                    SecondaryStage.close();

                    initializeDB();

                    String pathstr=file.getAbsolutePath();
                    pathstr=pathstr.replaceAll("\\\\", "\\\\\\\\");
                    System.out.println(pathstr);


                    try {
                        String q = "Insert into files(filename,file_category,file_extension,date_created,file_path) " +
                                "VALUES(" +"'"+file.getName()+"'"+ ","+"'"+Category+ "'"+"," + "'"+Extension[Extension.length-1]+"'"+","+"'"+dateFormat.format(file.lastModified())+"'"+","
                                +"'"+pathstr+"'"+")";

                        stmt.execute(q);

                    }
                    catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                });

                yes.setOnAction(y->{
                    FileChooser pic=new FileChooser();
                    pic.selectedExtensionFilterProperty();
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files","*.jpg","*.png","*.jpeg","*.gif");
                    pic.setTitle("Upload your cover");
                    pic.getExtensionFilters().add(extFilter);
                   File coverfile= pic.showOpenDialog(null);

                    String covername = String.valueOf(coverfile);
                    System.out.println("The path of the cover picture is: "+covername);
                    cstage.close();
                    SecondaryStage.close();
                    String coverpath = String.valueOf(covername);


                    initializeDB();

                    String pathstr1=file.getAbsolutePath();
                    pathstr1=pathstr1.replaceAll("\\\\", "\\\\\\\\");
                    System.out.println(pathstr1);

                    String pathstr2=covername;
                    pathstr2=pathstr2.replaceAll("\\\\", "\\\\\\\\");
                    System.out.println(pathstr2);



                    try {
                        String q = "Insert into files(filename,file_category,file_extension,date_created,file_path,cover_page) " +
                                "VALUES(" +"'"+file.getName()+"'"+ ","+"'"+Category+ "'"+"," + "'"+Extension[Extension.length-1]+"'"+","+"'"+dateFormat.format(file.lastModified())+"'"+","
                                +"'"+pathstr1+"'"+","+"'"+pathstr2+"'"+")";



                        stmt.execute(q);

                    }
                    catch (SQLException ex) {
                        ex.printStackTrace();
                    }




                });



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

