package model;

import java.util.ArrayList;

public class Model {
    public int pointCount;
    public int rectangeCount;
    public ArrayList<Point> p;

    public Model() {
        this.pointCount = pointCount;
        this.rectangeCount = rectangeCount;
        this.p = new ArrayList<Point>();
    }

    public int getPointCount() {
        return p.size();//возвращает размер листа с точками
    }

    public void addPoint(Point point) {
        p.add(point); // добавляет точку в лист}
    }

    public void removePoint(int x, int y) {
        for (int tempX = x - 3; tempX < x + 4; tempX++) {
            for (int tempY = y - 3; tempY < y + 4; tempY++) {
                int finalTempX = tempX;
                int finalTempY = tempY;
                p.removeIf(point -> point.getX() == finalTempX && point.getY() == finalTempY ) ;
            }
        }
    }

    public Point getPoint(int i){
        return this.p.get(i);//вернет элемент по индексу
    }
    public  void  deleteArray()
    {
        p.clear();///полностью очишает массив с точками
    }
    public int searchPoint(int x, int y){
        int index = -1;//ищем точку по координатм
        for (int i = 0; i < this.p.size(); i++){
            if (this.p.get(i).x == x && this.p.get(i).y == y) index = i;
        }
        return index;
    }


}
