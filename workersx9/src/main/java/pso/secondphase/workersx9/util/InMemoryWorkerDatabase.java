package pso.secondphase.workersx9.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vitorgreati
 */
public class InMemoryWorkerDatabase {

    private volatile List<String> outsideWorkers;
    private volatile List<String> insideWorkers;

    public InMemoryWorkerDatabase() {
        outsideWorkers = new ArrayList<>();
        insideWorkers = new ArrayList<>();
        
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString();
        
        //Populate outside cars
        for(int i = 0; i < 50; ++i)
            outsideWorkers.add(path + "/src/main/resources/img/"+i+".png");

    }

    /**
     * @return the outsideCars
     */
    public List<String> getOutsideWorkers() {
        return outsideWorkers;
    }

    /**
     * @param outsideCars the outsideCars to set
     */
    public void setOutsideWorkers(List<String> outsideWorkers) {
        this.outsideWorkers = outsideWorkers;
    }

    /**
     * @return the insideCars
     */
    public List<String> getInsideWorkers() {
        return insideWorkers;
    }

    /**
     * @param insideCars the insideWorkers to set
     */
    public void setInsideCars(List<String> insideWorkers) {
        this.insideWorkers = insideWorkers;
    }

}