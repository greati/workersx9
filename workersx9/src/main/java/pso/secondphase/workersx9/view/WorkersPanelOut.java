/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.workersx9.view;

import java.util.Date;
import pso.secondphase.iox9.business.processing.Observer;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.workersx9.processing.WorkerInProcessor;
import pso.secondphase.workersx9.processing.WorkerOutProcessor;

/**
 *
 * @author andre
 */
public class WorkersPanelOut extends Observer{
    public void update(WorkerOutProcessor observable, Object o){
        Entity e = ((IORecord)o).getEntity();
        System.out.println("SAIU");
        //System.out.println("Nome: " + e.getAttrs().get("nome").value);
        System.out.println("CPF: "+ e.getIdentifier());
        //System.out.println("Data Nascimento: "+ e.getAttrs().get("data").value);
    }
}
