/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.workersx9.view;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.Notification;
import pso.secondphase.workersx9.processing.WorkerInProcessor;
import pso.secondphase.workersx9.processing.WorkerOutProcessor;

/**
 *
 * @author andre
 */
public class WorkersPanelOut extends Observer{
    
    private GridPane workerOut;
    private GridPane imageOut;
    private GridPane imageOutAndData;
    private GridPane dataOut;
    
    private ImageView cameraOut;
    
    private Label titleOut;
    private Label nomeOut, nascimentoOut;
    
    
    public void init(){
        initGrids();
        
        initTitle();
        
        initImage();
        
        initData();
    }
    
    
    public void update(WorkerOutProcessor observable, Object o){
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                Entity e = ((IORecord)o).getEntity();
                System.out.println("SAIU");
                System.out.println("CPF: "+ e.getIdentifier());
                cameraOut.setImage(new Image(getClass().getResource("/img/"+e.getIdentifier()+".png").toExternalForm()));
                if(e.getAttrs().get("nome").value != null){
                    nomeOut.setText("Nome: " + e.getAttrs().get("nome").value);
                }
                else{
                    nomeOut.setText("Nome: ");
                }
                Date data = (Date)e.getAttrs().get("data_nascimento").value;
                LocalDate nascimento = data.toInstant().atZone(ZoneId.systemDefault().systemDefault()).toLocalDate();

                if(nascimento != null){
                    nascimentoOut.setText("Data nascimento: " + nascimento.getDayOfMonth() + "/" + nascimento.getMonthValue() + "/" + nascimento.getYear());
                }
                else{
                    nascimentoOut.setText("Data nascimento: ");
                }
            }
        });
        
        //System.out.println("Data Nascimento: "+ e.getAttrs().get("data").value);
    }
    
    public void update(NotifierChainSingleton processor, Object o) {
        Notification n = (Notification) o;
        System.out.println(n.getMessage());
    }

    private void initData(){
        //sinespIn
        nomeOut = new Label("Nome: ");
        nomeOut.setMinHeight(35);
        nascimentoOut = new Label("Data nascimento: ");
        nascimentoOut.setMinHeight(35);
        
        dataOut.add(nomeOut, 0, 0);
        dataOut.add(nascimentoOut, 0, 1);
        
        nomeOut.getStyleClass().add("sinesp");
        nascimentoOut.getStyleClass().add("sinesp");
        
        
    }
    
    private void initGrids() {
        workerOut = new GridPane();
        workerOut.setPrefSize(500, 350);
        workerOut.getStyleClass().add("gridpane-left");
        
        imageOutAndData = new GridPane();
        imageOutAndData.setPrefSize(500, 200);
        
        imageOut = new GridPane();
        imageOut.setPrefSize(275, 200);
        imageOut.getStyleClass().add("camera");
        dataOut = new GridPane();
        dataOut.setPrefSize(225, 200);
        
        imageOutAndData.add(imageOut, 0, 0);
        imageOutAndData.add(dataOut, 1, 0);
        
//        weekCounter = new GridPane();
//        weekCounter.setPrefSize(500, 100);
        
        workerOut.add(imageOutAndData, 0, 1);
        //vehicleIn.add(weekCounter, 0, 2);
    }

    private void initTitle() {
        Rectangle bar = new Rectangle(350, 4);  
        bar.setArcWidth(6);  
        bar.setArcHeight(6);  
        bar.setFill(Color.rgb(142,68,173));
        
        titleOut = new Label("Entrada");
        titleOut.getStyleClass().add("title");
        VBox vBox = new VBox(titleOut, bar);  
        vBox.setSpacing(2);  
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10,10,10,10));
        workerOut.add(vBox, 0, 0);
    }
    
    private void initImage(){        
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString();
        Image img = new Image(getClass().getResource("/img/default.png").toExternalForm());
        
        //imageIn
        cameraOut = new ImageView(img);
        cameraOut.setFitWidth(250);
        cameraOut.setFitHeight(200);
        imageOut.add(cameraOut, 0, 0);
    }
    
    public GridPane getPanel(){
        return this.workerOut;
    }
    
    
}
