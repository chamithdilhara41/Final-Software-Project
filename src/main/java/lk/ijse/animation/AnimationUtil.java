package lk.ijse.animation;

import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnimationUtil {
    public static void popUpAnimation(AnchorPane stage, Parent rootNode) {

        // Implement your right back animation logic here
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), rootNode);
        translateTransition.setFromX(+rootNode.getBoundsInLocal().getWidth());
        translateTransition.setToX(0);
        translateTransition.play();
    }

    public static void popUpAnimation1(Stage stage, Parent rootNode) {

        stage.setHeight(792);
        stage.setWidth(1164);

        // Implement your pop-up animation logic here
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), rootNode);
        translateTransition.setFromY(-rootNode.getBoundsInLocal().getHeight());
        translateTransition.setToY(0);
        translateTransition.play();

        // Show the stage after animation
        stage.show();
    }
}
