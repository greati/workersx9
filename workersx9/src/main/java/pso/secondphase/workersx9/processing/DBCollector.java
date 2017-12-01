/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.workersx9.processing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pso.secondphase.iox9.business.processing.InformationCollector;
import pso.secondphase.iox9.model.Attribute;
import pso.secondphase.iox9.model.Entity;

/**
 *
 * @author andre
 */
public class DBCollector implements InformationCollector{

    private BufferedReader reader;
    
    public DBCollector() {
        try {
            Path currentRelativePath = Paths.get("");
            String path = currentRelativePath.toAbsolutePath().toString();
            reader = new BufferedReader(new FileReader(path + "/src/main/resources/database.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void collect(Entity entity) {
        String[] pessoa = null;
        try {
            pessoa = getByCPF(entity.getIdentifier());
        } catch (IOException ex) {
            Logger.getLogger(DBCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map<String, Attribute<?>> attrs = entity.getAttrs();
        attrs.put("nome", new Attribute<String>(pessoa[1], "nome"));
        try {
            attrs.put("data", new Attribute<Date>((new SimpleDateFormat("dd-mm-YYYY")).parse(pessoa[2]+"-"+pessoa[3]+"-"+pessoa[4]), "data"));
        } catch (ParseException ex) {
            Logger.getLogger(DBCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String[] getByCPF(String cpf) throws IOException{
        
        String line = reader.readLine();
        while(line != null){
            
            String[] splitLine = line.split(",", -1);
            if(splitLine[0].equals(cpf)){
                return splitLine;
            }
            line = reader.readLine();
        }
        return null;
    }
    
}
