package com.custom.model.jms;

import com.custom.model.dao.DepartmentDAO;
import com.custom.model.entity.Department;
import com.custom.model.exception.DAOBusinessException;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Created by olga on 30.03.15.
 */
@MessageDriven(name = "SavingUniversityEntitiesMDBQueue", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/SavingUniversityEntitiesMDBQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class SavingEntitiesMDB implements MessageListener {
    @EJB
    private DepartmentDAO departmentDAO;

    @Override
    public void onMessage(Message message) {
        if (!(message instanceof ObjectMessage)) {
            return;
        }
        try {
            Object obj = ((ObjectMessage) message).getObject();
            if (obj instanceof Department) {
                departmentDAO.add((Department) obj);
            }
        } catch (JMSException e) {
            System.out.println("JMS: error with message parsing");
        } catch (DAOBusinessException e) {
            System.out.println("DAOBusinessException: " + e.getMessage());
        }
    }
}
