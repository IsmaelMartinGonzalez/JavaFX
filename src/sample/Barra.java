package sample;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Project name: JavaFX/sample
 * Filename:
 * Created:  03/02/2021 / 17:48
 * Description:
 * Revision:
 *
 * @Author: Ismael - fmartin@nigul.cide.es
 * @Version:
 */
public class Barra {
    //Attriubutes
    private Posicion posicion;
    private int vel;
    private Pane panel;
    private Node barra;
    //Builder

    public Barra(int anchura, int altura, int vel,int pos, Pane panel) {
        this.posicion=new Posicion(anchura,altura);
        this.vel = vel;
        this.panel = panel;
        this.barra=new Rectangle(anchura,altura, Color.WHITE);
        try {
            colocar(pos,anchura,altura);
        }catch (Exception e){
            System.out.println(e);
        }
        barra.setLayoutX(posicion.posX);
        barra.setLayoutY(posicion.posY);
    }
    //Getters/Setters

    public int getVel() {
        return vel;
    }

    public void setVel(int vel) {
        this.vel = vel;
    }

    public Pane getPanel() {
        return panel;
    }

    public void setPanel(Pane panel) {
        this.panel = panel;
    }

    public Node getBarra() {
        return barra;
    }

    public void setBarra(Node barra) {
        this.barra = barra;
    }

    //Other Methods
    private void reposiciona() {
        this.barra.setLayoutX(posicion.posX);
        this.barra.setLayoutY(posicion.posY);
    }

    public void arriba() {
        posicion.posY=posicion.posY-this.vel;
        this.reposiciona();
    }
    public void abajo() {
        posicion.posY=posicion.posY+this.vel;
        this.reposiciona();
    }

    private void colocar(int numero,int anchura, int altura) throws Exception {
        final Bounds limites = panel.getBoundsInLocal();
        if (numero==0){
            posicion.posX = (int) limites.getMinX() + anchura;
            posicion.posY = (int) limites.getMaxY() /2 - altura/2;
        }else if(numero==1){
            posicion.posX = (int) limites.getMaxX() - (anchura*2);
            posicion.posY = (int) limites.getMaxY() /2 - (altura/2);
        }else if(numero==-1) {
            posicion.posX=500;
            posicion.posY=0;
        }else {
            throw new Exception("Error. 0 es izquierda, 1 es derecha");
        }
    }
}
