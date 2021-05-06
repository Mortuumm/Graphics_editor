package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import model.Model;
import model.Point;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    public Canvas CanvasD;
    public ColorPicker colorP;
    public Button lineS;
    public String flag;
    public Slider sliders;
    public Button lastick;
    public Button loadM;
    public Button obrezatM;

    private Window primaryStage;
     Model model;
    Point point;
    private GraphicsContext gr;
    Image bgImage;
    double bgX, bgY, bgW = 100.0, bgH = 100.0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model=new Model();
        gr = CanvasD.getGraphicsContext2D();
        SliderTol();


    }

    private void SliderTol() {//толщина линии
        sliders.setMin(1);
        sliders.setMax(10);
        sliders.setValue(1);
        colorP.setValue(Color.BLACK);
        flag =lastick.getId();

    }
    public void update(Model model) {
        gr.clearRect(0, 0, gr.getCanvas().getWidth(), gr.getCanvas().getHeight());//очищаем канвас

        for (int i = 0; i < model.getPointCount(); i++) {
            gr.setFill(model.getPoint(i).getColor());
            gr.fillRect(model.getPoint(i).getX(),model.getPoint(i).getY(),model.getPoint(i).getwP() ,model.getPoint(i).gethP());
        }
    }

    public void canvaClicked(MouseEvent mouseEvent) {

    }

    public void lineSClicked(ActionEvent actionEvent) {
    flag =  lastick.getId();
    }

    public void m_lastick(ActionEvent actionEvent) {
        flag = lineS.getId();
    }

    private void initDraw(GraphicsContext gc, File file){
        String str=file.getPath();
        //переменная для пути к картинке
        //get Canvas height and weidth
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        bgImage = new Image(file.toURI().toString());
        bgX = canvasWidth/2;
        bgY = canvasHeight/2 ;
        gc.drawImage(bgImage, bgX, bgY, bgW, bgH);//set image on Canvas

        PixelReader pixelReader = bgImage.getPixelReader();
        double yr=CanvasD.getHeight()/bgImage.getHeight();
        double xr=CanvasD.getWidth()/bgImage.getWidth();
        //попиксельная отрисовка изображения на канвас
        for (int y = 0; y < bgImage.getHeight(); y++) {
            for (int x = 0; x < bgImage.getWidth(); x++) {
                Color color = pixelReader.getColor(x, y);
                Point point =new Point(x,y);
                point.setColor(color);
                point.setSizePoint(xr,yr);
                model.addPoint(point);
            }
        }

    }
    public void print(MouseEvent mouseEvent) {//для непрерывной линии
        //update();

        Point point = new Point((int) mouseEvent.getX(), (int) mouseEvent.getY());
        if (flag == lastick.getId()) {
            point.setColor(colorP.getValue());
            point.setSizePoint(sliders.getValue(), sliders.getValue());
            model.addPoint(point);

        } else if(flag == lineS.getId()) {
            /*
            point.setColor(Color.web("#f2f2f2"));
            point.setSizePoint(sliders.getValue(), sliders.getValue());
            model.addPoint(point);

             */
                model.removePoint((int) mouseEvent.getX(), (int) mouseEvent.getY());

        }
        else if (flag == obrezatM.getId()){
           // model.removePoint(100,100);
            /*int x = 100000;
            int y = 200000;
            int count = 1;
            count ++;
            if(count == 1) {

                x = model.searchPoint((int) mouseEvent.getX(), (int) mouseEvent.getY());
            }
            else if (count == 2){
                y = model.searchPoint((int) mouseEvent.getX(), (int) mouseEvent.getY());
            }

             */
            point.setColor(Color.WHITE);
            point.setSizePoint(300,100);
            model.addPoint(point);

        }
        update(model);
    }
    public void obrezanie(MouseEvent event){

        if (flag == obrezatM.getId()){
            int x = 100000;
            int y = 200000;
            int count = 1;
            count ++;
            if(count == 1) {

                x = model.searchPoint((int) event.getX(), (int) event.getY());
            }
            else if (count == 2){
                y = model.searchPoint((int) event.getX(), (int) event.getY());
            }
        }
        update(model);
    }

    public void clear(ActionEvent actionEvent) {
        gr.clearRect(0, 0, gr.getCanvas().getWidth(), gr.getCanvas().getHeight());
    }

    public void Mupdate(ActionEvent actionEvent) {

        for (int i = 0; i < model.getPointCount(); i++) {

            gr.setFill(model.getPoint(i).getColor());
            gr.fillOval(model.getPoint(i).getX(),model.getPoint(i).getY(),model.getPoint(i).getwP() ,model.getPoint(i).gethP());

            model.getPoint(i).setColor(colorP.getValue());
        }
        update(model);

    }

    public Image loadImage() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите изображение...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Изображение", "*.png"));
        File loadedImage = fileChooser.showOpenDialog(primaryStage);
        return new Image(loadedImage.toURI().toString());
    }

    public void writeImage(Image image) {
        PixelReader pixelReader = image.getPixelReader();
        model.deleteArray();
        for(int x = 0; x < image.getWidth(); x++) {
            for(int y = 0; y < image.getHeight(); y++) {
                model.addPoint(new Point(x, y, pixelReader.getColor(x, y),bgW,bgH));
            }
        }
        update(model);
    }

    public void loadM(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();//класс работы с диалоговым окном
        fileChooser.setTitle("Выберите изображениe...");//заголовок диалога
//задает фильтр для указанного расшиерения
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Изображение", "*.png"),
                new FileChooser.ExtensionFilter("Изображение", "*.bmp"));

        File loadImageFile = fileChooser.showOpenDialog(CanvasD.getScene().getWindow());

        if (loadImageFile != null) {
            //Open
            System.out.println("Процесс открытия файла");
            initDraw(gr,loadImageFile);
            update(model);
        }
    //writeImage(loadImage());
    }

    public void saveM(ActionEvent actionEvent) throws IOException {
        WritableImage writableImage = CanvasD.snapshot(new SnapshotParameters(), null);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите директорию...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Изображение", "*.png"));
        File file = fileChooser.showSaveDialog(primaryStage);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void obrezat(ActionEvent actionEvent) {
        flag = obrezatM.getId();
    }
}

