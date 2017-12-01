/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.workersx9.processing;

import java.awt.Image;
import pso.secondphase.iox9.business.capture.IdentityDataSource;
import pso.secondphase.iox9.exception.FailedOpeningSourceException;
import pso.secondphase.iox9.exception.InvalidDataReceivedException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import pso.secondphase.iox9.business.capture.IdentityDataSource;
import pso.secondphase.iox9.exception.FailedOpeningSourceException;
import pso.secondphase.iox9.exception.InvalidDataReceivedException;
import pso.secondphase.workersx9.util.InMemoryWorkerDatabase;


/**
 *
 * @author vitorgreati
 */
public class OutDataSourceSavedImage extends IdentityDataSource<Image> {

    private final InMemoryWorkerDatabase database;
    
    public OutDataSourceSavedImage(String id){
        super(id);
        this.database = InMemoryWorkerDatabase.getInstance();
    }

    
    @Override
    protected Image _getData() throws InvalidDataReceivedException {
        BufferedImage img = null;

        if(database.getInsideWorkers().isEmpty()) return null;

        int pos = (int)(Math.random() * database.getInsideWorkers().size());
        String imgString = database.getInsideWorkers().get(pos);
        try {
            img = ImageIO.read(new File(imgString));
            database.getInsideWorkers().remove(pos);
            database.getOutsideWorkers().add(imgString);
        } catch (IOException ex) {
            Logger.getLogger(OutDataSourceSavedImage.class.getName()).log(Level.SEVERE, null, ex);
        }

        return img;    
    }

    @Override
    protected void _setup() throws FailedOpeningSourceException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
