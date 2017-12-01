/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.workersx9.notification;

import java.time.LocalDate;
import java.util.Date;
import pso.secondphase.iox9.business.notification.NotificationAgent;
import pso.secondphase.iox9.business.processing.EntityProcessor;
import pso.secondphase.iox9.model.Attribute;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.Notification;
import pso.secondphase.iox9.model.NotificationType;
import pso.secondphase.iox9.model.SimpleNotificationType;

/**
 *
 * @author andre
 */
public class BirthdayNotificationAgent extends NotificationAgent{

    public BirthdayNotificationAgent(NotificationAgent successor) {
        super(successor);
    }

    @Override
    protected boolean test(IORecord ior, EntityProcessor ep) {
        Attribute<?> attrData = ior.getEntity().getAttrs().get("data");
        if (attrData != null) {
            Date data = (Date)attrData.value;

            LocalDate ld = LocalDate.ofEpochDay(data.getTime());
            LocalDate now = LocalDate.now();
            if(ld.getDayOfMonth() == now.getDayOfMonth() && ld.getDayOfWeek() == now.getDayOfWeek()){
                return true;
            }
        }
        return false;
    }

    @Override
    protected Notification createNotification() {
        return new Notification("Aniversariante chegou!", SimpleNotificationType.INFO);
    }
    
}
