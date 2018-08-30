/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entity.ParqueNatural;
import Services.ParqueService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.ws.rs.client.Entity;


/**
 *
 * @author Usuario
 */
@ManagedBean(name="beanParque")
@ApplicationScoped
public class ParqueBean implements Serializable{
    
    private List<ParqueNatural> parques;
    private List<ParqueNatural> filteredParque;
    private String idPark;
    
    @ManagedProperty("#{parqueService}")
    private ParqueService service;
    
    @PostConstruct
    public void init() {
        parques = service.createParks();
    }

    public String getIdPark() {
        return idPark;
    }

    public void setIdPark(String idPark) {
        this.idPark = idPark;
    }
    
    
    
    public List<String> getStates() {
        return service.getstate();
    }
    
    public List<ParqueNatural> getParks() {
        return parques;
    }
    
    public List<ParqueNatural> getFilteredParks() {
        return filteredParque;
    }
    
    public void setFilteredParks(List<ParqueNatural> filteredParks) {
        this.filteredParque = filteredParks;
    }
    
    public void setService(ParqueService service) {
        this.service = service;
    }
    
    public void editar(ParqueNatural parque){        
        Entity payload = parque.toJson();        
        service.editar(payload, parque.getId());                
    }
    public void addMessage(String summary, Severity mes) {
        FacesMessage message = new FacesMessage(mes, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }       
    
    public void delete(ParqueNatural parque) {            
        int estado = service.Eliminar(parque.getId());
        if (estado == 204) {
            parques.remove(parque);
            addMessage("Eliminado : "+parque.getName(),FacesMessage.SEVERITY_INFO);
        }else{
            addMessage("Errror eliminando : "+parque.getName(),FacesMessage.SEVERITY_ERROR);
        }
        
    }
    
    
    
}
