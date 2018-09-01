/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Beans.ParqueBean;
import Entity.ParqueNatural;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Usuario
 */
@ManagedBean
@ViewScoped
public class ParqueInfo implements Serializable {

    private String selectedId;
    private ParqueNatural selectedParque = new ParqueNatural();
    private static ResourceBundle bundle;

    @ManagedProperty("#{beanParque}")
    private ParqueBean parque;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        selectedId = params.get("idpark");
        Client client = ClientBuilder.newClient();
        WebTarget rs = client.target("https://private-f57ba-parques5.apiary-mock.com/parks/{id}");
        JsonObject jsonObj = (JsonObject) rs.resolveTemplate("id", selectedId)
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);
        selectedParque = ParqueNatural.fromJSon(jsonObj);
    }

    public ParqueBean getParque() {
        return parque;
    }

    public void setParque(ParqueBean parque) {
        this.parque = parque;
    }

    public String getSelectedId() {

        return selectedId;
    }

    public void setSelectedId(String selectedId) {
        this.selectedId = selectedId;
    }

    public ParqueNatural getSelectedParque() {
        return selectedParque;
    }

    public void setSelectedParque(ParqueNatural selectedParque) {
        this.selectedParque = selectedParque;
    }

    public void editarPark(ActionEvent actionEvent) {
        getLocalidad();
        if (!selectedParque.getId().isEmpty()) {
            if (parque.editar(selectedParque) == 204) {
                parque.addMessage(bundle.getString("MSG_EDITADO"), FacesMessage.SEVERITY_INFO);
            } else {
                parque.addMessage(bundle.getString("MSG_ERROR_EDITANDO"), FacesMessage.SEVERITY_INFO);
            }
        } else {
            parque.addMessage("Error API", FacesMessage.SEVERITY_INFO);
        }

    }

    private void getLocalidad() {
        UIViewRoot vr = FacesContext.getCurrentInstance().getViewRoot();
        bundle = ResourceBundle.getBundle("co.com.iteria.parques.bundle.ParqueNacional", vr.getLocale());
    }

}
