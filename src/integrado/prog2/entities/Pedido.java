
package integrado.prog2.entities;

import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.interfaces.Calculable;
import integrado.prog2.exception.StockInvalidoException; //excepción propia

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Base implements Calculable{ //hereda de base e implementa Calculable
    //atributos
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private List<DetallePedido> detalles; //relacion 1:n con la entidad DetallePedido
    private Usuario usuario;
    
    // Contador local para los IDs de los detallesPedido
    private Long contadorDetalles = 1L;
    
    //constructor
    public Pedido(Long id, Estado estado, FormaPago formaPago, Usuario usuario) {
        super(id);
        this.fecha = LocalDate.now();
        this.estado = estado;
        this.formaPago = formaPago;
        this.detalles = new ArrayList<>();
        this.usuario = usuario;
    }
    
    //getters
    public Usuario getUsuario() {
        return usuario;
    }

    public double getTotal() {
        return total;
    }
    public List<DetallePedido> getDetalles() { 
        return detalles;
    }
    
    
    //setters
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }
    
    
    //Metodo sobre escrito de la interfaz calculable
    @Override
    public void calcularTotal(){
        total = 0.0; //acumulará el valor total del pedido
        for (DetallePedido detalle : detalles) { //for each para recorrer toda la lista y obtener todos los subtotales de los productos
            total += detalle.getSubtotal(); //se suman los subtotales de cada detalle en la lista
        }
    }
    
    public void addDetallePedido(int cantidad, Double precioUnitario, Producto producto) throws StockInvalidoException { 
        // Regla de negocio 1: Cantidad mayor a cero
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }
        
        // Regla de negocio 2: Validar stock disponible
        if (producto.getStock() < cantidad) {
            throw new StockInvalidoException("Stock insuficiente para " + producto.getNombre() + ". Disponibles: " + producto.getStock());
        }

        // Restamos el stock del producto (regla lógica implícita en un pedido)
        producto.setStock(producto.getStock() - cantidad);

        // Creamos el detalle pasando el precioUnitario correcto y el ID correlativo
        DetallePedido detalle = new DetallePedido(contadorDetalles++, cantidad, precioUnitario, producto); 
        detalles.add(detalle); 
        
        calcularTotal();
    }
    
    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        if (producto == null) return null; // Si no pasan producto, no hay nada que buscar
        
        for (DetallePedido detalle : detalles) { //for each para recorrer todos los valores en la lista
            if(detalle.getProducto().getId().equals(producto.getId())) { //compara el id de los productos en la lista con el producto ingresado por parametro en cada iteracion
                return detalle; // devuelve el valor encontrado
            }
        }
        return null; //si no hay coincidencia devuelve null
    }
    
    public void deleteDetallePedidoByProducto(Producto producto) {
    DetallePedido detalle = findDetallePedidoByProducto(producto); //para no repetir codigo (DRY)
    
    if (detalle != null) {
        // consistencia: Devuelve las unidades al stock general del producto
        Producto p = detalle.getProducto();
        p.setStock(p.getStock() + detalle.getCantidad());

        // Remueve el detalle de la lista
        detalles.remove(detalle); 
        
        // Recalculam el total del pedido
        calcularTotal(); 
    }
}
    
    
    //toString sobre escrito
    @Override
    public String toString() {
        return String.format( "Pedido: id=%d, fecha=%s, estado=%s, formaPago=%s, total= %.2f, usuario= %s",
                            getId(), fecha, estado, formaPago, total, usuario.getNombre() + " "+ usuario.getApellido());
    }
}
