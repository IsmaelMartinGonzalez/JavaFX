package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Project name: DAM20/PACKAGE_NAME
 * Filename:
 * Created:  14/11/2020 / 13:47
 * Description:
 * Revision:
 *
 * @Author: Ismael - fmartin@nigul.cide.es
 * @Version:
 */
public class BolaStart extends Application {
    Pane panel=new Pane();
    double ancho=1000;
    double alto=500;
    Bola bola;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primarystage) throws Exception {
        primarystage.setScene(new Scene(panel, ancho, alto, Color.BLACK));
        primarystage.setTitle("BolaStart");
        primarystage.show();
        panel.requestFocus();
        bola=new Bola(panel,500,100,10);
//        panel.setOnKeyPressed(e->{
//            switch (e.getCode()){
//                case ENTER:bola.start();break;
//            }
//        });
        final Timeline loop = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {

            // Formula en graus
            double angle_en_radians =Math.toRadians(30);
            int velocitat=2;
            double deltaX = velocitat*Math.cos(angle_en_radians);
            double deltaY = velocitat*Math.sin(angle_en_radians);

            // Simulació gravitatòria
            double temps=0;
            final Bounds limits = panel.getBoundsInLocal();

            @Override
            public void handle(final ActionEvent t) {
                bola.setLayoutX(bola.getLayoutX() + deltaX);
                bola.setLayoutY(bola.getLayoutY() + deltaY);

                final boolean alLimitDret = bola.getLayoutX() >= (limits.getMaxX() - bola.getRadius());
                final boolean alLimitEsquerra = bola.getLayoutX() <= (limits.getMinX() + bola.getRadius());
                final boolean alLimitInferior = bola.getLayoutY() >= (limits.getMaxY() - bola.getRadius());
                final boolean alLimitSuperior = bola.getLayoutY() <= (limits.getMinY() + bola.getRadius());


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

}
