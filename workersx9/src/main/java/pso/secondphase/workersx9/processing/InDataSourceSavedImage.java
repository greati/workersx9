/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.workersx9.processing;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import pso.secondphase.iox9.business.capture.IdentityDataSource;
import pso.secondphase.iox9.configuration.ApplicationConfiguration;
import pso.secondphase.iox9.exception.FailedOpeningSourceException;
import pso.secondphase.iox9.exception.InvalidDataReceivedException;
import pso.secondphase.workersx9.util.InMemoryWorkerDatabase;

/**
 *
 * @author andre
 */
public class InDataSourceSavedImage extends IdentityDataSource<Image> {

    private final InMemoryWorkerDatabase database;    
    
    public InDataSourceSavedImage(String id){
        super(id);
        this.database = InMemoryWorkerDatabase.getInstance();
    }
    
    @Override
    protected Image _getData() throws InvalidDataReceivedException {
        BufferedImage img = null;

        if(database.getOutsideWorkers().isEmpty()) return null;

        int pos = (int)(Math.random() * database.getOutsideWorkers().size());
        String imgString = database.getOutsideWorkers().get(pos);
        try {
           img = ImageIO.read(new File(imgString));
           database.getOutsideWorkers().remove(pos);
           database.getInsideWorkers().add(imgString);
        } catch (IOException ex) {
           Logger.getLogger(InDataSourceSavedImage.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        return img;    
    }

    @Override
    protected void _setup() throws FailedOpeningSourceException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
