/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Usuario1
 */
@ManagedBean
@SessionScoped
public class LanguajeTemplate {

    public static final String LANG_ES = "es";
    public static final String LANG_EN = "en";

    private String languaje = LANG_EN;

    @PostConstruct
    public void init() {
        UIViewRoot vr = FacesContext.getCurrentInstance().getViewRoot();
        languaje = vr.getLocale().getLanguage();
    }

    public String getLanguaje() {
        return languaje;
    }

    public void setLanguaje(String languaje) {
        this.languaje = languaje;
    }

    public void onLangChange(ValueChangeEvent ev) {
        UIViewRoot vr = FacesContext.getCurrentInstance().getViewRoot();

        if (ev.getNewValue().equals(LANG_ES)) {
            vr.setLocale(new Locale(LANG_ES));
        } else {
            vr.setLocale(Locale.US);
        }

    }

}
