/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import Entity.ParqueNatural;
import java.util.Arrays;
import java.util.Iterator;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

 
@ManagedBean(name = "parqueService")
@ApplicationScoped
public class ParqueService {

     
    private final static String[] states;
     
    static {
        
         
        states = new String[3];
        states[0] = "Cauca";
        states[1] = "Magdalena";
        states[2] = "Tolima";        
    }
     
    public List<ParqueNatural> createParks() {
        Client client = ClientBuilder.newClient();
        JsonArray jsonArray = null;
        WebTarget rs = null;      
        List<ParqueNatural> list = new ArrayList<ParqueNatural>();
        rs = client.target("https://private-f57ba-parques5.apiary-mock.com/parks");
                    jsonArray = (JsonArray) rs.request(MediaType.APPLICATION_JSON).get(JsonArray.class);
        if (jsonArray != null) {
            Iterator iter = jsonArray.iterator();
            while (iter.hasNext()) {
                JsonObject val = (JsonObject) iter.next();
                list.add(ParqueNatural.fromJSon(val));
            }
        }                
         
        return list;
    }         
     
    public List<String> getstate() {
        return Arrays.asList(states);
    } 
    
    public int editar(Entity payload, String id){
        Client client = ClientBuilder.newClient();
        Response response = client.target("https://private-f57ba-parques5.apiary-mock.com/parks/{id}")
            .resolveTemplate("id", id)
            .request(MediaType.APPLICATION_JSON_TYPE)
            .put(payload);
        return response.getStatus();
    }  
    
    public int Eliminar(String id){
        Client client = ClientBuilder.newClient();
        Response response = client.target("https://private-anon-4f17d88ef6-parques5.apiary-mock.com/parks/{id}")
            .resolveTemplate("id", id)
            .request(MediaType.TEXT_PLAIN_TYPE)
            .delete();
        return response.getStatus();
        
    } 
}
