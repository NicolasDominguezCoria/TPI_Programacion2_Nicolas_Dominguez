
package integrado.prog2.entities;

public class DetallePedido extends Base{
    //atributos
    private int cantidad;
    private Double precioUnitario;
    private Double subtotal;
    private Producto producto; //referencia a la relacion m:1 con la entidad Producto
    
    //constructor
    public DetallePedido(Long id, int cantidad, Double precioUnidtario, Producto producto) {
        super(id);
        this.cantidad = cantidad;
        this.precioUnitario = precioUnidtario;
        this.producto = producto;
        calcularSubtotal();
    }
    public int getCantidad() {
        return cantidad;
    }
    
    public double getSubtotal() {
        return subtotal;
    }

    public Producto getProducto() {
        return producto;
    }
    
    //metodo
    public double calcularSubtotal() {
        if(producto != null) { //condicional defensiva para evitar posibles errores null
            subtotal = producto.getPrecio() * cantidad;
        }
        return subtotal;
    }
    
    
    
    //toString sobre escrito
    @Override
    public String toString() {
        return String.format("DetallePedido: id=%d, producto= '%s'. cantidad = %d, subtotal= %.2f" , getId(), producto.getNombre(), cantidad, subtotal);
    }
}
