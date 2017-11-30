/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.workersx9.processing;

import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;
import pso.secondphase.iox9.business.processing.EntityProcessor;
import pso.secondphase.iox9.business.processing.EntityRecognizer;
import pso.secondphase.iox9.business.processing.InformationCollectorThread;
import pso.secondphase.iox9.configuration.ApplicationConfiguration;
import pso.secondphase.iox9.dao.EntityDAO;
import pso.secondphase.iox9.dao.IORecordDAO;
import pso.secondphase.iox9.exception.InvalidEntityException;
import pso.secondphase.iox9.model.Attribute;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecordType;
import pso.secondphase.iox9.model.ModelAbstractFactory;

/**
 *
 * @author andre
 */
public class WorkerInProcessor extends EntityProcessor<Image>{

    public WorkerInProcessor(IORecordType ioType, ModelAbstractFactory modelAbstractFactory, EntityRecognizer entityRecognizer, EntityDAO entityDAO, IORecordDAO ioDAO) {
        super(ioType, modelAbstractFactory, entityRecognizer, entityDAO, ioDAO);
    }

    @Override
    protected boolean validate(Entity entity) throws InvalidEntityException {
        ApplicationConfiguration.getInstance().incrementEntityCount();
        return true;
    }

    @Override
    protected void collect(Entity entity) {
        try{
            InformationCollectorThread.getInstance().getEntitiesQueue().add(entity);
        }
        catch(Exception ex){
            Logger.getLogger(WorkerInProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    protected void populateSpecificValues(Image idt, Entity entity) {
        if(entity.getIdentifier() != null){
            Entity newEntity = entityDAO.getByIdentifier(entity.getIdentifier());
            entity.setAttrs(newEntity.getAttrs());
        }
        if(idt != null){
            entity.getAttrs().put("image", new Attribute<>(idt, "image", false));
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
