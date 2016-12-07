import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Hao on 12/6/2016.
 */
public class AlertBox
{
    public static void display(String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Alert!");
        window.setMinWidth(250);

        Label lb = new Label();
        lb.setText(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e ->window.close());

        VBox layout = new VBox(30);
        layout.setSpacing(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(lb,closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
}
