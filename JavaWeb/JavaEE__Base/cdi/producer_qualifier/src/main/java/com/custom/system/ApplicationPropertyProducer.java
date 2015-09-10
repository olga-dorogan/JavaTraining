package com.custom.system;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * Created by olga on 04.08.15.
 */
@ApplicationScoped
public class ApplicationPropertyProducer {
    @EJB
    private PropertyFileResolver fileResolver;

    @Produces
    @ApplicationProperty(name = "")
    public String getPropertyAsString(InjectionPoint injectionPoint) {
        System.out.println("Get property by name, fileResolver== " + fileResolver);

        String propertyName = injectionPoint.getAnnotated().getAnnotation(ApplicationProperty.class).name();
        String value = fileResolver.getProperty(propertyName);

        if (value == null || propertyName.trim().length() == 0) {
            throw new IllegalArgumentException("No property found with name " + value);
        }
        return value;
    }

    @Produces
    @ApplicationProperty(name = "")
    public Integer getPropertyAsInteger(InjectionPoint injectionPoint) {

        String value = getPropertyAsString(injectionPoint);
        return value == null ? null : Integer.valueOf(value);
    }
}