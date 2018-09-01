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
import java.util.Locale;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.ws.rs.client.Entity;

/**
 *
 * @author Usuario
 */
@ManagedBean(name = "beanParque")
@ApplicationScoped
public class ParqueBean implements Serializable {

    private List<ParqueNatural> parques;
    private List<ParqueNatural> filteredParque;
    private String idPark;
    private ParqueNatural parque;
    private static ResourceBundle bundle;

    @ManagedProperty("#{parqueService}")
    private ParqueService service;

    @PostConstruct
    public void init() {
        parques = service.createParks();
        parque = new ParqueNatural();

    }

    public String getIdPark() {
        return idPark;
    }

    public void setIdPark(String idPark) {
        this.idPark = idPark;
    }

    public ParqueNatural getParque() {
        return parque;
    }

    public void setParque(ParqueNatural parque) {
        this.parque = parque;
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

    public int editar(ParqueNatural parque) {
        Entity payload = parque.toJson();
        return service.editar(payload, parque.getId());
    }

    public void addMessage(String summary, Severity mes) {
        FacesMessage message = new FacesMessage(mes, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void delete(ParqueNatural parque) {
        getLocalidad();
        if (parque != null) {
            int estado = service.Eliminar(parque.getId());
            if (estado == 204) {
                parques.remove(parque);
                addMessage(bundle.getString("MSG_ELIMINAR") +" : " + parque.getName(), FacesMessage.SEVERITY_INFO);
            } else {
                addMessage(bundle.getString("MSG_ERROR_ELIMINAR") +" : " + parque.getName(), FacesMessage.SEVERITY_ERROR);
            }
        } else {
            addMessage("Error API", FacesMessage.SEVERITY_ERROR);
        }

    }

    public void crear() {
        getLocalidad();
        Entity payload = parque.toJsonCrear();
        int estado = service.crear(payload);
        if (estado == 201) {
            parque.setStatus("Open");
            parques.add(parque);
            addMessage(bundle.getString("MSG_CREADO") +" : " + parque.getName(), FacesMessage.SEVERITY_INFO);
        } else {
            addMessage(bundle.getString("MSG_ERROR_CREANDO") +" : " + parque.getName() + " " + estado, FacesMessage.SEVERITY_ERROR);
        }

    }

    private void getLocalidad() {
        UIViewRoot vr = FacesContext.getCurrentInstance().getViewRoot();
        bundle = ResourceBundle.getBundle("co.com.iteria.parques.bundle.ParqueNacional", vr.getLocale());
    }
}
