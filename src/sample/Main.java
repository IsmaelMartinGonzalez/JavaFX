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
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    public static Circle bola;
    public static Pane canvas;
    public static Barra player;
    public static Barra player2;
    public static Barra malla;
    public static Text ganador;
    public static Text playerText;
    public static Text player2Text;
    public static Text playerContador;
    public static int contador1=0;
    public static int contador2=0;
    public static Text player2Contador;
    public static Font font=new Font("Action ManAction Man",40);
    public static Font fontContador=new Font("Action ManAction Man",60);

    @Override
    public void start(final Stage primaryStage) {
        canvas = new Pane();
        final Scene escena = new Scene(canvas, 1000, 500, Color.BLACK);

        primaryStage.setTitle("Pong");
        primaryStage.setScene(escena);
        primaryStage.show();

        playerText=new Text("Player 1");
        playerText.setFont(font);
        playerText.setFill(Color.WHITE);
        playerText.setLayoutX(45);
        playerText.setLayoutY(35);

        player2Text=new Text("Player 2");
        player2Text.setFont(font);
        player2Text.setFill(Color.WHITE);
        player2Text.setLayoutX(820);
        player2Text.setLayoutY(35);

        playerContador=new Text("0");
        playerContador.setFont(fontContador);
        playerContador.setFill(Color.WHITE);
        playerContador.setLayoutX(400);
        playerContador.setLayoutY(50);

        player2Contador=new Text("0");
        player2Contador.setFont(fontContador);
        player2Contador.setFill(Color.WHITE);
        player2Contador.setLayoutX(600);
        player2Contador.setLayoutY(50);

        int radio=15;
        bola = new Circle(radio, Color.WHITE);
        bola.setLayoutX(500);
        bola.setLayoutY(250);

        malla=new Barra(10,500,0,-1,canvas);
        player= new Barra(10,80,30,0,canvas);
        player2=new Barra(10,80,30,1,canvas);

        canvas.getChildren().addAll(bola);
        canvas.getChildren().addAll(malla.getBarra());
        canvas.getChildren().addAll(player.getBarra());
        canvas.getChildren().addAll(player2.getBarra());
        canvas.getChildren().addAll(playerText);
        canvas.getChildren().addAll(player2Text);
        canvas.getChildren().addAll(playerContador);
        canvas.getChildren().addAll(player2Contador);

        canvas.requestFocus();
        canvas.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W: player.arriba(); break;
                case S: player.abajo(); break;
                case UP:player2.arriba();break;
                case DOWN:player2.abajo();break;
                case ENTER:
                    final Timeline loop = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {

                    // Formula en graus
                    double angle_en_radians =Math.toRadians(30);
                    int velocitat=3;
                    double deltaX = velocitat*Math.cos(angle_en_radians);
                    double deltaY = velocitat*Math.sin(angle_en_radians);

                    // Simulació gravitatòria
                    double temps=0;
                    final Bounds limits = canvas.getBoundsInLocal();

                    @Override
                    public void handle(final ActionEvent t) {
                        bola.setLayoutX(bola.getLayoutX() + deltaX);
                        bola.setLayoutY(bola.getLayoutY() + deltaY);

                        final boolean alLimitDerecho = bola.getLayoutX() >= (limits.getMaxX() - bola.getRadius());
                        final boolean alLimitIzquierdo = bola.getLayoutX() <= (limits.getMinX() + bola.getRadius());
                        final boolean alLimitInferior = bola.getLayoutY() >= (limits.getMaxY() - bola.getRadius());
                        final boolean alLimitSuperior = bola.getLayoutY() <= (limits.getMinY() + bola.getRadius());

                        if(bola.getBoundsInParent().intersects(player.getBarra().getBoundsInParent())) {
                            deltaX *= -1;
                        }
                        if(bola.getBoundsInParent().intersects(player2.getBarra().getBoundsInParent())) {
                            deltaX *= -1;
                        }

                        if (alLimitDerecho || alLimitIzquierdo) {
                            if (alLimitDerecho){
                                contador1++;
                                playerContador.setText(contador1+"");
                            }else if (alLimitIzquierdo){
                                contador2++;
                                player2Contador.setText(contador2+"");
                            }else {
                                try {
                                    throw new Exception("Error. El contador a fallado");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            bola.setLayoutX(500);
                            bola.setLayoutY(250);
                        }
                        if (alLimitInferior || alLimitSuperior) {
                            // Multiplicam pel signe de deltaX per mantenir la trajectoria
                            deltaY *= -1;
                        }
                    }
                }));
                    loop.setCycleCount(Timeline.INDEFINITE);
                    loop.play();
                    break;
            }
        });


    }

    public static void endGame(Timeline loop){
        loop.stop();
        if (contador1==10){
            ganador=new Text("Gana el player 1");
            ganador.setFont(font);
            ganador.setFill(Color.WHITE);
            ganador.setLayoutX(500);
            ganador.setLayoutY(250);
            canvas.getChildren().addAll(ganador);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}