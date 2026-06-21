
package integrado.prog2.entities;

public class Producto extends Base { //hereda de base
    //atributos
    private String nombre;
    private Double precio;
    private String descripcion;
    private Integer stock;
    private String imagen;
    private Boolean disponible;
    private Categoria categoria; //referencia a la relacion m:1 con la entidad Categoria

    
    //constructor
    public Producto(Long id, String nombre, Double precio, String descripcion, int stock, String imagen, Categoria categoria) {
        super(id);
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.imagen = imagen;
        this.disponible = true;
        this.categoria = categoria;
    }
    
    //getters
    public Double getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }
    
    public int getStock() { 
        return stock; 
    }

    public Categoria getCategoria() { 
        return categoria; 
    }
    
    public Boolean getDisponible() { 
        return disponible; 
    }
    
    //setters
    public void setNombre(String nombre) { 
        this.nombre = nombre;
    }
    
    public void setPrecio(Double precio) { 
        this.precio = precio; 
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public void setStock(Integer stock) { 
        this.stock = stock; 
    }
    
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    public void setDisponible() {
        disponible = stock > 0;
    }

    //toString sobre escrito
    @Override
    public String toString() {
        return String.format("Producto: id= %d, nombre= '%s', precio= %.2f, stock= %d, disponible= %s, categoria= '%s'}",
                            getId(),nombre, precio, stock, disponible, categoria.getNombre());
    }
}
