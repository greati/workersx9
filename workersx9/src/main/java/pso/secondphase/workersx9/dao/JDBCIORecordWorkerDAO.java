/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.workersx9.dao;

import java.util.List;
import pso.secondphase.iox9.dao.JDBCIORecordDAO;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.SimpleIORecordType;

/**
 *
 * @author andre
 */
public class JDBCIORecordWorkerDAO extends JDBCIORecordDAO{
    public IORecord getLastInRecord(Entity e){
        List<IORecord> lista = listByEntity(e);
        IORecord latest = null;
        for(IORecord ior : lista){
            if(ior.getType() == SimpleIORecordType.IN){
                if(latest != null && latest.getInstant().before(ior.getInstant())){
                    latest = ior;
                }
                if(latest == null){
                    latest = ior;
                }
            }
        }
        return latest;
    }
}
