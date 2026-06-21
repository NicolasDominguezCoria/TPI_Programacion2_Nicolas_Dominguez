
package integrado.prog2.entities;

import integrado.prog2.enums.Rol;
import java.util.ArrayList;
import java.util.List;

public class Usuario extends Base{ //hereda de base
    //atributos
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasenia;
    private Rol rol;
    private List<Pedido> pedidos; //relacion 1:n con pedidos
    
    //constructor
    public Usuario( Long id, String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) {
        super(id);
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.pedidos = new ArrayList<>(); // lista de pedidos al crear la entidad
    }
    
    // getters
    public String getMail() {
        return mail;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public String getCelular() { 
        return celular; 
    }
    
    public Rol getRol() {
        return rol;
    }
    
    public List<Pedido> getPedidos() {
        return pedidos;
    }
    
    //setters
    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public void setCelular(String celular) { 
        this.celular = celular; 
    }
    
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    //sincronizacion de relación 
    public void agregarPedido(Pedido pedido) {
        if (pedido != null && !this.pedidos.contains(pedido)) {
            this.pedidos.add(pedido);
        }
    }
    
    //toString sobre escrito
    @Override
    public String toString() {
        return String.format("Usuario: id=%d, nombre='%s', apellido='%s', mail='%s', rol=%s",
                            getId(), nombre, apellido, mail, rol);
    }
}
