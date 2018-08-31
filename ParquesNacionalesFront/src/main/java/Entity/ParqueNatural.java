/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.json.JsonObject;
import javax.ws.rs.client.Entity;


/**
 *
 * @author Usuario
 */
public class ParqueNatural implements Serializable{
    private String id;
    private String name;
    private String state;
    private int capacity;
    private String status;

    public ParqueNatural(String id, String name, String state, int capacity, String status) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.capacity = capacity;
        this.status = status;
    }

    public ParqueNatural() {
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public static ParqueNatural fromJSon(JsonObject val){       
            ParqueNatural Pnew = new ParqueNatural();
            Pnew.setId(val.getString("id"));
            Pnew.setName(val.getString("name"));
            Pnew.setState(val.getString("state"));
            Pnew.setCapacity(val.getInt("capacity"));
            Pnew.setStatus(val.getString("status"));           
        return Pnew;
    }
    
    public Entity toJson(){
        
        Entity payload = Entity.json("{  'id': "+getId()+", 'name': "+getName()+",  'state': "+getState()+",  'capacity': "+getCapacity()+",  'status': "+getStatus()+"}");
        return payload;
    }
    
    public Entity toJsonCrear(){
        
        Entity payload = Entity.json("{ 'name': "+getName()+",  'state': "+getState()+",  'capacity': "+getCapacity()+"}");
        return payload;
    }
    
    public boolean getBoolState() {
        if(this.status.equals("Open")){
            return true;
        }else{
           return false; 
        }                
    }
}
