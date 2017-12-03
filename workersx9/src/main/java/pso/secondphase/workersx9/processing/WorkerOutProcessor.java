/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.workersx9.processing;

import java.awt.Image;
import java.util.Date;
import java.util.List;
import pso.secondphase.iox9.business.processing.EntityProcessor;
import pso.secondphase.iox9.business.processing.EntityRecognizer;
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
public class WorkerOutProcessor extends EntityProcessor<Image>{

    public WorkerOutProcessor(IORecordType ioType, ModelAbstractFactory modelAbstractFactory, EntityRecognizer entityRecognizer, EntityDAO entityDAO, IORecordDAO ioDAO) {
        super(ioType, modelAbstractFactory, entityRecognizer, entityDAO, ioDAO);
    }

    @Override
    protected boolean validate(Entity entity) throws InvalidEntityException {
        ApplicationConfiguration.getInstance().decrementEntityCount();
        return true;
    }

    @Override
    protected void populateSpecificValues(Image idt, Entity entity) {
        if(entity.getIdentifier() != null){
            Entity newEntity = entityDAO.getByIdentifier(entity.getIdentifier());
            entity.setAttrs(newEntity.getAttrs());
        }
        if(idt != null){
            entity.getAttrs().put("image", new Attribute<>(idt, "image"));
        }
        List<Date> instants = ioDAO.getLastVisit(entity);
        
        entity.getAttrs().put("instants", new Attribute<>(instants, "instants"));
    }

    @Override
    protected void collect(Entity entity) {
        /*Empty*/
    }
    
}
