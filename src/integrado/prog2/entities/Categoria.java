
package integrado.prog2.entities;

import java.util.ArrayList;
import java.util.List;

public class Categoria extends Base{ //hereda de base
    //atributos
    private String nombre;
    private String descripcion;
    private List<Producto> productos; //relacion 1:n con productos
    
    //constructor
    public Categoria(Long id, String nombre, String descripcion) {
        super(id);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = new ArrayList<>(); //inicializamos la lista de productos al instanciar la entidad
    }
    
    //getters
    public String getNombre() {
        return nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public List<Producto> getProductos() {
    return productos;
    }
    
    //metodo para sincronizar un producto con su categoria
    public void agregarProducto(Producto producto) {
        if(producto != null && !this.productos.contains(producto)) {
            this.productos.add(producto);
        }
    }
    
    //setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    //toString sobre escrito
    @Override
    public String toString() {
        return String.format("Categoria: id= %d, nombre= '%s', descripcion= '%s'", getId(), nombre, descripcion);
    }
}
