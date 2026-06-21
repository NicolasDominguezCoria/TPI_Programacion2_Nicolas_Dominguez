
package integrado.prog2.entities;

import java.time.LocalDateTime;

public abstract class Base {
    //atributos
    private Long id; 
    private boolean eliminado;
    private LocalDateTime createdAt;
    
    //constructor
    public Base(Long id) {    
        this.id = id; //se asigna automaticamente al instanciar una entidad a través de una id autoincremental asignado desde dicha entidad.
        this.eliminado = false; //se otorga por defecto el estado false.
        this.createdAt = LocalDateTime.now(); //se crea automaticamente la fecha y hora al instanciar una entidad 
    }
    
    //getters
    public Long getId() {
        return id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    //toString abstracto
    @Override
    public abstract String toString();
}


