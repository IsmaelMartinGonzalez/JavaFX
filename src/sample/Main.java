package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    public static Circle cercle;
    public static Pane canvas;
    public static Rectangle rectangle;

    @Override
    public void start(final Stage primaryStage) {

        canvas = new Pane();
        final Scene escena = new Scene(canvas, 1000, 500,Color.BLACK);

        primaryStage.setTitle("Pong");
        primaryStage.setScene(escena);
        primaryStage.show();

        int radi=15;
        cercle = new Circle(radi, Color.WHITE);
        rectangle= new Rectangle(20,50,Color.WHITE);
        cercle.relocate(500-radi, 20-radi);
        rectangle.relocate(200,200);

        canvas.getChildren().addAll(cercle);
        canvas.getChildren().addAll(rectangle);

        final Timeline loop = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {


            // Formula en graus
            double angle_en_radians =Math.toRadians(30);
            int velocitat=2;
            double deltaX = velocitat*Math.cos(angle_en_radians);
            double deltaY = velocitat*Math.sin(angle_en_radians);

            // Simulació gravitatòria
            double temps=0;
            final Bounds limits = canvas.getBoundsInLocal();

            @Override
            public void handle(final ActionEvent t) {

                cercle.setLayoutX(cercle.getLayoutX() + deltaX);
                cercle.setLayoutY(cercle.getLayoutY() + deltaY);
                final boolean alLimitDret = cercle.getLayoutX() >= (limits.getMaxX() - cercle.getRadius());
                final boolean alLimitEsquerra = cercle.getLayoutX() <= (limits.getMinX() + cercle.getRadius());
                final boolean alLimitInferior = cercle.getLayoutY() >= (limits.getMaxY() - cercle.getRadius());
                final boolean alLimitSuperior = cercle.getLayoutY() <= (limits.getMinY() + cercle.getRadius());


                if (alLimitDret || alLimitEsquerra) {
                    // Delta aleatori
                    // Multiplicam pel signe de deltaX per mantenir la trajectoria
                    deltaX *= -1;
                }
                if (alLimitInferior || alLimitSuperior) {
                    // Delta aleatori
                    // Multiplicam pel signe de deltaX per mantenir la trajectoria

                    deltaY *= -1;
                }
            }
        }));

        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
