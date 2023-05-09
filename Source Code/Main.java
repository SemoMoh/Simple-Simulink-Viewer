import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
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


                // Create new Scene
                Scene scene = new Scene(pane, 1500, 800);
                // set the scene to the Stage in Full Screen
                primaryStage.setScene(scene);
                primaryStage.setFullScreen(true);
                primaryStage.setResizable(true);
                // set the title to Simulink: + file name
                primaryStage.setTitle("Simulink: " + "\"" + file.getName() + "\"");

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                System.out.println("Exception");
            }
        });

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
        primaryStage.setTitle("Simulink");                          // set the title
        primaryStage.show();                                        // show the Stage

    }
}