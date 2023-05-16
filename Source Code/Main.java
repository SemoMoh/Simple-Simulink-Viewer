import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {
    double magn = 11;

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) {
        // Scene intro nodes
        // leaf image
        Image leaf = new Image("images/leaf.png");
        ImageView leafView = new ImageView(leaf);
        leafView.setScaleX(0.1);
        leafView.setScaleY(0.1);
        leafView.setX(0);
        leafView.setY(0);

        // frame image
        Image frame = new Image("images/frame.png");
        ImageView frameView = new ImageView(frame);
        frameView.setLayoutX(0);
        frameView.setLayoutY(0);

        // Welcome Text
        Text welcome = new Text("Welcome ..\nChoose new .mdl file");
        welcome.setTextAlignment(TextAlignment.CENTER);
        welcome.setFont(new Font(24));
        welcome.setLayoutX(150);
        welcome.setLayoutY(200);
        welcome.setStyle("-fx-font-weight: bold");

        // new file button
        ImageView newFileIcon = new ImageView(new Image("images/New File.png"));
        newFileIcon.setScaleX(0.2);
        newFileIcon.setScaleY(0.2);

        // logo
        Image logoImage = new Image("images/s.png");
        ImageView logo = new ImageView(logoImage);
        logo.setScaleX(0.1);
        logo.setScaleY(0.1);
        logo.setLayoutX(200);
        logo.setLayoutY(200);

        // Create the button and
        Button newFileBtn = new Button("New File", newFileIcon);
        newFileBtn.setLayoutX(225);
        newFileBtn.setLayoutY(250);
        newFileBtn.setMinHeight(40);
        newFileBtn.setMaxHeight(50);
        newFileBtn.setMinWidth(40);
        newFileBtn.setMaxWidth(50);
        newFileBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);


        // Create Pane of scene intro
        Pane pane1 = new Pane();
        pane1.getChildren().addAll(
                newFileBtn,
                welcome,
                leafView,
                frameView,
                logo
        );


        // Create Scene of intro
        Scene firstScene = new Scene(pane1, 500, 500);

        primaryStage.getIcons().add(new Image("images/s.png")); // Add Icon to the App
        primaryStage.setResizable(false);                           // make not resizable
        primaryStage.setScene(firstScene);                          // set the scene to the Stage
        primaryStage.setTitle("Simulink Viewer");                   // set the title
        primaryStage.show();                                        // show the Stage


        // translate to scene 2 on getting the file and get <System>..</System>
        newFileBtn.setOnMouseClicked(event -> {
            try {
                // Open file dialog and choose .mdl file
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open .mdl file");
                FileChooser.ExtensionFilter extensionFilter
                        = new FileChooser.ExtensionFilter("MDL Files (*.mdl)", "*.mdl");
                fileChooser.getExtensionFilters().add(extensionFilter);
                File file = fileChooser.showOpenDialog(primaryStage);

                // Open file input stream and load data into stringBuilder
                FileInputStream fileInputStream = new FileInputStream(file);
                int d;
                StringBuilder stringBuilder = new StringBuilder();
                while ((d = fileInputStream.read()) != -1) {
                    stringBuilder.append((char) d);
                }
                String data = stringBuilder.toString();

                // Use the Scanner to scan the data and get the System tag.
                Scanner scanner = new Scanner(data);
                StringBuilder system = new StringBuilder("<System>\n");
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.contains("<System>")) {
                        do {
                            line = scanner.nextLine();
                            system.append(line).append("\n");
                        } while (!line.contains("</System>"));

                    }
                }


                // Create new Pane and Scene to display the blocks in
                Pane pane = new Pane();

                Blocks blocks = new Blocks();
                blocks.addBlocks(system.toString());    // pass the system tag string to addBlocks to parse it to ArrayList of Blocks
                blocks.drawBlock(pane);                 // draw the blocks

                //draw the lines
                DrawAllLines.drawAllLines(system.toString(), pane, blocks.getBlocks());

                Image returnImg = new Image("images/return.png");
                ImageView returnImage = new ImageView(returnImg);
                returnImage.setScaleX(0.08);
                returnImage.setScaleY(0.08);
                returnImage.setLayoutX(5);
                returnImage.setLayoutY(5);


                Button returnButton = new Button("Return", returnImage);
                returnButton.setLayoutX(5);
                returnButton.setLayoutY(5);
                returnButton.setMinHeight(40);
                returnButton.setMaxHeight(50);
                returnButton.setMinWidth(40);
                returnButton.setMaxWidth(50);
                returnButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                ImageView codeView = new ImageView(new Image("images/code.png"));
                codeView.setScaleX(0.1);
                codeView.setScaleY(0.1);
                codeView.setLayoutX(75);
                codeView.setLayoutY(5);

                Button codeButton = new Button("Code", codeView);
                codeButton.setLayoutX(75);
                codeButton.setLayoutY(5);
                codeButton.setMinHeight(40);
                codeButton.setMaxHeight(50);
                codeButton.setMinWidth(40);
                codeButton.setMaxWidth(50);
                codeButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                ImageView returnIcon = new ImageView(returnImg);
                returnIcon.setScaleX(0.08);
                returnIcon.setScaleY(0.08);
                returnIcon.setLayoutX(5);
                returnIcon.setLayoutY(5);

                Button returnBtn = new Button("Return", returnIcon);
                returnBtn.setLayoutX(5);
                returnBtn.setLayoutY(5);
                returnBtn.setMinHeight(40);
                returnBtn.setMaxHeight(50);
                returnBtn.setMinWidth(40);
                returnBtn.setMaxWidth(50);
                returnBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                returnBtn.setOnMouseClicked(event1 -> {
                    primaryStage.setScene(firstScene);
                    primaryStage.setResizable(false);
                });



                pane.getChildren().addAll(returnButton, codeButton);

                Text codeText = new Text(system.toString());
                codeText.setLayoutY(70);
                codeText.setFont(new Font(11));

                ScrollBar scroll = new ScrollBar();
                scroll.setLayoutX(250);
                scroll.setLayoutY(25);
                scroll.setMinWidth(300);
                scroll.setMin(0);
                scroll.setMax(5000);
                scroll.setValue(0);
                scroll.setOrientation(Orientation.HORIZONTAL);

                scroll.valueProperty().addListener(event2 ->{
                    codeText.setLayoutY(codeText.getY()-scroll.getValue()+100);
                });

                ImageView graphic = new ImageView(new Image("images/graphic.png"));
                graphic.setScaleX(0.1);
                graphic.setScaleY(0.1);
                graphic.setLayoutX(75);
                graphic.setLayoutY(5);

                Button graphicButton = new Button("Graphic", graphic);
                graphicButton.setLayoutX(75);
                graphicButton.setLayoutY(5);
                graphicButton.setMinHeight(40);
                graphicButton.setMaxHeight(50);
                graphicButton.setMinWidth(40);
                graphicButton.setMaxWidth(50);
                graphicButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                ImageView mag = new ImageView(new Image("images/mag.png"));
                mag.setScaleX(0.1);
                mag.setScaleY(0.1);
                mag.setLayoutX(145);
                mag.setLayoutY(5);


                Button magnifier = new Button("Magnifier", mag);
                magnifier.setLayoutX(145);
                magnifier.setLayoutY(5);
                magnifier.setMinHeight(40);
                magnifier.setMaxHeight(50);
                magnifier.setMinWidth(40);
                magnifier.setMaxWidth(50);
                magnifier.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                magnifier.setOnMouseClicked(event1 -> {
                    magn += 4;
                    codeText.setFont(new Font(magn));
                });


                Pane codePane = new Pane();
                codePane.getChildren().addAll( codeText, scroll,returnBtn, graphicButton, magnifier);

                Scene codeScene = new Scene(codePane,600,800);
                codeButton.setOnMouseClicked(e -> {
                    primaryStage.setScene(codeScene);
                    primaryStage.setFullScreen(true);
                });

                returnButton.setOnMouseClicked(event1 -> {
                    primaryStage.setScene(firstScene);
                    primaryStage.setResizable(false);
                });



                // Create new Scene
                Scene graphicScene = new Scene(pane, 1500, 800);
                // set the graphicScene to the Stage in Full Screen

                primaryStage.setScene(graphicScene);
                primaryStage.setFullScreen(true);
                primaryStage.setResizable(true);
                // set the title to Simulink: + file name
                primaryStage.setTitle("Simulink: " + "\"" + file.getName() + "\"");

                graphicButton.setOnMouseClicked(event1 -> {
                    magn = 14;
                    primaryStage.setScene(graphicScene);
                    primaryStage.setFullScreen(true);
                });



            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                System.out.println("Exception");
            }
        });




    }
}