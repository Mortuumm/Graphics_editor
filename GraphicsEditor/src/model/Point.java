package model;


import javafx.scene.paint.Color;

public class Point extends java.awt.Point {
    private double wP;
    private double hP;
    private Color color;

    public Point(){
        this.color = Color.BLACK;
        this.wP = 1;
        this.hP = 1;
    }
    public Point(int x, int y){super(x,y);}
    public Point(int x, int y, Color color, double wP, double hP){
        super(x,y);
        this.color = color;
        this.wP = wP;
        this.hP = hP;
    }
    public Color getColor(){return color;}
    public void setColor(Color color){this.color = color;}

    public double getwP(){return wP;}

    public void setwP(double wP) {
        this.wP = wP;
    }

    public double gethP() {
        return hP;
    }

    public void sethP(double hP) {
        this.hP = hP;
    }

    public void setSizePoint(double wP, double hP){
        this.wP = wP;
        this.hP = hP;
    }

}
