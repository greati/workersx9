/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.workersx9.statistics;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import pso.secondphase.iox9.business.statistics.StatisticsProcessor;
import pso.secondphase.iox9.configuration.ApplicationConfiguration;
import pso.secondphase.iox9.dao.JDBCEntityDAO;
import pso.secondphase.iox9.dao.JDBCIORecordDAO;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.workersx9.dao.JDBCIORecordWorkerDAO;

/**
 *
 * @author andre
 */
public class LateWorkersStatistics extends StatisticsProcessor{

    public LateWorkersStatistics(StatisticsProcessor sucessor) {
        super(sucessor);
    }

    @Override
    public Object generateStatistics(IORecord ior) {
        System.out.println("askfjakfhsdjf");
        int horaChegada = (int) ApplicationConfiguration.getInstance().getParameters().get("hora_chegada");
        
        JDBCIORecordWorkerDAO iodao = new JDBCIORecordWorkerDAO();
        JDBCEntityDAO edao = new JDBCEntityDAO();
        
        List<Entity> lista = edao.list();
        int freqAtrasada = 0;
        int freqNaoChegou = 0;
        int freqNaoAtrasada = 0;
        LocalDateTime now = LocalDateTime.now();
        for(Entity e : lista){
            IORecord last = iodao.getLastInRecord(e);
            if(last != null){
                LocalDateTime ldt = LocalDateTime.ofInstant(last.getInstant().toInstant(), ZoneId.systemDefault());
                if(ldt.getDayOfYear() == now.getDayOfYear() && ldt.getYear() == now.getYear()){
                    if(ldt.getHour() > horaChegada){
                        freqAtrasada++;
                    }
                    else if(ldt.getHour() == horaChegada && ldt.getMinute() > 10){
                        freqAtrasada++;
                    }
                    else{
                        freqNaoAtrasada++;
                    }
                }
                else{
                    freqNaoChegou++;
                }
            }
            else{
                freqNaoChegou++;
            }
        }
        int total = freqAtrasada+freqNaoAtrasada+freqNaoChegou;
        List<Float> dados = new ArrayList<>();
        dados.add((float)freqAtrasada/total);
        dados.add((float)freqNaoAtrasada/total);
        dados.add((float)freqNaoChegou/total);
        
        return dados;
    }
    
}
