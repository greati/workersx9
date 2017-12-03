/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.workersx9.view;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pso.secondphase.iox9.configuration.ApplicationConfiguration;
import pso.secondphase.iox9.configuration.StartableView;

/**
 *
 * @author andre
 */
public class RealTimePanel extends Application implements StartableView{

    private GridPane rootPane, workerInOut, content;
    
    WorkersPanelIn workerInPanel;
    WorkersPanelOut workerOutPanel;
    //ChartsPanel graphsPanel;
    //NotificationPanel notificationPanel;
    
    @Override
    public void start(Stage stage) throws Exception {
        workerInPanel = (WorkersPanelIn) ApplicationConfiguration.getInstance().getViews().get("entrance_panel");
        workerInPanel.init();
        
        //workerOutPanel = (PanelOut) ApplicationConfiguration.getInstance().getViews().get("exit_panel");
        
        //graphsPanel = new ChartsPanel();
        //notificationPanel = new NotificationPanel();
        
        //Load font
        Font.loadFont(getClass().getResource("/font/JosefinSans-Light.ttf").toExternalForm(), 20);
        
        //Init main grid
        initGrid();
        
        //Creating a scene object 
        Scene scene = new Scene(rootPane);
        
        //Defining stylesheet
        scene.getStylesheets().add(getClass().getResource("/styles/realtimepanellayout.css").toExternalForm());
      
        //Setting title to the Stage 
        stage.setTitle("Real Time Panel"); 

        //Adding scene to the stage 
        stage.setScene(scene);
        
        //Displaying the contents of the stage 
        stage.show(); 
        
        //control = new MainControl(vehicleInPanel, vehicleOutPanel, graphsPanel, notificationPanel);
    }

    @Override
    public void start() {
        Application.launch("");
    }
    
    public void initGrid(){
        //Creating a Grid Pane 
        rootPane = new GridPane();
        rootPane.setPrefSize(1010, 700);
        rootPane.getStyleClass().add("root-pane");
        
        //Creating Sub-Grid Panes
        workerInOut = new GridPane();
        workerInOut.setPrefSize(1010, 350);
        
        workerInOut.add(workerInPanel.getPanel(), 0, 0);
        //vehicleInOut.add(vehicleOutPanel.getPanel(), 1, 0);
        
        //Setting vehicleInOut postion
        rootPane.add(workerInOut, 0, 0);
        
        content = new GridPane();
        content.setPrefSize(1010, 350);
        
        GridPane notification = new GridPane();
        notification.setPrefSize(390, 350);
        notification.getStyleClass().add("gridpane-notification");
        
        //content.add(graphsPanel.getPanel(), 0, 0);
        //content.add(notificationPanel.getPanel(), 1, 0);
        
        //Setting content postion
        rootPane.add(content, 0, 1);
    }
    
    public static void main(String[] args) {   
        launch(args);
    }
}
