package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
public class Bola {
//Attributes
    Posición posicion;
    int radio;
    int velocidad=1;
    Pane panel;
    Circle bola;
    double angle_en_radians =Math.toRadians(30);
    double deltaX = velocidad*Math.cos(angle_en_radians);
    double deltaY = velocidad*Math.sin(angle_en_radians);
//Builder
    public Bola(Pane panel, int posX, int posY, int radio){
        posicion=new Posición(posX,posY);
        this.radio=radio;
        this.panel=panel;
        this.bola=new Circle(posicion.posX-radio,posY-radio,radio,Color.WHITE);
        posicion.posX=0;
        posicion.posY=0;
        this.bola.setLayoutX(posicion.posX);
        this.bola.setLayoutY(posicion.posY);
        this.panel.getChildren().addAll(this.bola);
    }
//Getters/Setters



    //Others Methods
    public void start(){

        System.out.println("Start");
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

    public double getLayoutX() {
       return bola.getLayoutX();
    }

    public double getRadius() {
        return bola.getRadius();
    }

    public double getLayoutY() {
        return bola.getLayoutY();
    }

    public void setLayoutX(double v) {
        bola.setLayoutX(v);
    }

    public void setLayoutY(double v) {
        bola.setLayoutY(v);
    }
}
