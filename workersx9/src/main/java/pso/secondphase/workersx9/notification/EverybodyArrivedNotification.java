/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.workersx9.notification;

import java.util.Objects;
import pso.secondphase.iox9.business.notification.NotificationAgent;
import pso.secondphase.iox9.business.processing.EntityProcessor;
import pso.secondphase.iox9.configuration.ApplicationConfiguration;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.Notification;
import pso.secondphase.iox9.model.SimpleNotificationType;

/**
 *
 * @author andre
 */
public class EverybodyArrivedNotification extends NotificationAgent{

    public EverybodyArrivedNotification(NotificationAgent successor) {
        super(successor);
    }

    @Override
    protected boolean test(IORecord ior, EntityProcessor ep) {
        Integer maxCapacity = (Integer) ApplicationConfiguration.getInstance().getParameters().get("capacity");
        Long current = ApplicationConfiguration.getInstance().getEntityCount();
        return Objects.equals(maxCapacity.longValue(), current);
    }

    @Override
    protected Notification createNotification() {
        return new Notification("Todos chegaram!", SimpleNotificationType.INFO);
    }
    
}
