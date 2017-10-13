/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demoRest;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author edgardo
 */
@javax.ws.rs.ApplicationPath("webresources")//nombre del API (http://localhost:8084/demoSendPdfWS-1/webresources/savePdf)
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     * Clase donde se deben registrar los controladores para hacerlos visibles en el API
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(demoRest.GenericResource.class);
        resources.add(demoRest.getPdfB64.class);
        resources.add(demoRest.savePdf.class);
    }
    
}
