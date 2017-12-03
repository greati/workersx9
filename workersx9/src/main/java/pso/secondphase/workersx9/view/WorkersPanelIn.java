/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.workersx9.view;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pso.secondphase.iox9.business.notification.NotifierChainSingleton;
import pso.secondphase.iox9.business.processing.Observer;
import pso.secondphase.iox9.business.statistics.StatisticsChainSingleton;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.Notification;
import pso.secondphase.workersx9.processing.WorkerInProcessor;
import pso.secondphase.workersx9.statistics.LateWorkersStatistics;

/**
 *
 * @author andre
 */
public class WorkersPanelIn extends Observer{
    
    private GridPane workerIn;
    private GridPane imageIn;
    private GridPane imageInAndData;
    private GridPane dataIn;
    
    private ImageView cameraIn;
    
    private Label titleIn;
    private Label nomeIn, nascimentoIn;
    
    
    public void init(){
        initGrids();
        
        initTitle();
        
        initImage();
        
        initData();
    }
    
    
    public void update(WorkerInProcessor observable, Object o){
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                Entity e = ((IORecord)o).getEntity();
                System.out.println("ENTROU");
                System.out.println("CPF: "+ e.getIdentifier());
                cameraIn.setImage(new Image(getClass().getResource("/img/"+e.getIdentifier()+".png").toExternalForm()));
                nomeIn.setText("Nome: " + e.getAttrs().get("nome").value);
                LocalDate nascimento = LocalDate.ofEpochDay(((Date)e.getAttrs().get("data").value).getTime());
                nascimentoIn.setText("Data nascimento: " + nascimento.getDayOfMonth() + "/" + nascimento.getMonthValue() + "/" + nascimento.getYear());
            }
        });
        
        //System.out.println("Data Nascimento: "+ e.getAttrs().get("data").value);
    }
    
    public void update(NotifierChainSingleton processor, Object o) {
        Notification n = (Notification) o;
        System.out.println(n.getMessage());
    }
    
    public void update(LateWorkersStatistics observable, Object o){
        List<Float> list = (ArrayList<Float>)o;
        System.out.println("Atrasados/Não-atrasados/Não chegaram: " + list.get(0)*100+"%/"+list.get(1)*100+"%/"+list.get(2)*100+"%");
    }

    private void initData(){
        //sinespIn
        nomeIn = new Label("Nome: ");
        nomeIn.setMinHeight(35);
        nascimentoIn = new Label("Data nascimento: ");
        nascimentoIn.setMinHeight(35);
        
        dataIn.add(nomeIn, 0, 0);
        dataIn.add(nascimentoIn, 0, 1);
        
        nomeIn.getStyleClass().add("sinesp");
        nascimentoIn.getStyleClass().add("sinesp");
        
        
    }
    
    private void initGrids() {
        workerIn = new GridPane();
        workerIn.setPrefSize(500, 350);
        workerIn.getStyleClass().add("gridpane-left");
        
        imageInAndData = new GridPane();
        imageInAndData.setPrefSize(500, 200);
        
        imageIn = new GridPane();
        imageIn.setPrefSize(275, 200);
        imageIn.getStyleClass().add("camera");
        dataIn = new GridPane();
        dataIn.setPrefSize(225, 200);
        
        imageInAndData.add(imageIn, 0, 0);
        imageInAndData.add(dataIn, 1, 0);
        
//        weekCounter = new GridPane();
//        weekCounter.setPrefSize(500, 100);
        
        workerIn.add(imageInAndData, 0, 1);
        //vehicleIn.add(weekCounter, 0, 2);
    }

    private void initTitle() {
        Rectangle bar = new Rectangle(350, 4);  
        bar.setArcWidth(6);  
        bar.setArcHeight(6);  
        bar.setFill(Color.rgb(142,68,173));
        
        titleIn = new Label("Entrada");
        titleIn.getStyleClass().add("title");
        VBox vBox = new VBox(titleIn, bar);  
        vBox.setSpacing(2);  
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10,10,10,10));
        workerIn.add(vBox, 0, 0);
    }
    
    private void initImage(){        
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString();
        Image img = new Image(getClass().getResource("/img/default.png").toExternalForm());
        
        //imageIn
        cameraIn = new ImageView(img);
        cameraIn.setFitWidth(250);
        cameraIn.setFitHeight(200);
        imageIn.add(cameraIn, 0, 0);
    }
    
    public GridPane getPanel(){
        return this.workerIn;
    }
}
