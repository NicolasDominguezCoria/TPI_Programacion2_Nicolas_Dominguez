
package integrado.prog2.services;

import integrado.prog2.entities.DetallePedido;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Usuario;
import integrado.prog2.entities.Producto;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.StockInvalidoException;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    //atributos
    private final List<Pedido> pedidos; //aqui se acumularán los pedidos instanciados
    private Long contadorId = 1L; //id autoincremental para cada padido instanciado
    
    //establecemos relacion con el servicio de gestion de usuario y el servicio de gestion de productos ya
    //que vamos a necesitar hacer uso de sus métodos.
    private final UsuarioService usuarioService; 
    private final ProductoService productoService;
    
    //constructor
    public PedidoService(UsuarioService usuarioService, ProductoService productoService) {
        this.pedidos = new ArrayList<>();
        this.usuarioService = usuarioService;
        this.productoService = productoService;
    }
    
    //metodo para listar pedidos
    public List<Pedido> listarPedidos() {

        List<Pedido> activos = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            if (!pedido.isEliminado()) {
                activos.add(pedido);
            }
        }

        return activos;
    }
    
    //metodo para listar pedidos por usuario
    public List<Pedido> listarPedidosPorUsuario(Long idUsuario)
        throws EntidadNoEncontradaException {

    Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);

    List<Pedido> pedidoUsuario = new ArrayList<>();

    for (Pedido pedido : pedidos) {

        if (!pedido.isEliminado() && pedido.getUsuario().getId().equals(usuario.getId())) {
            pedidoUsuario.add(pedido);
        }
    }

    return pedidoUsuario;
}
    //metodo para buscar un pedido por su id
    public Pedido buscarPedidoPorId(Long id) throws EntidadNoEncontradaException {

        for (Pedido pedido : pedidos) {

            if (pedido.getId().equals(id) && !pedido.isEliminado()) {
                return pedido;
            }
        }

        throw new EntidadNoEncontradaException(
                "Error! Pedido no encontrado."
        );
    }
    
    // metodo para crear un pedido
    public Pedido crearPedido(Long idUsuario, FormaPago formaPago) throws  EntidadNoEncontradaException {
        // 1. obtenemos el usuario
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        
        
        
        return new Pedido(contadorId++,Estado.PENDIENTE,formaPago, usuario);
    }
    
    public void guardarPedido(Pedido pedido) {
        pedidos.add(pedido); //añadir pedido a la lista
        pedido.getUsuario().agregarPedido(pedido); //sincronizamos el pedido con su usuario
    }
    
    public void agregarDetalleAPedido(Pedido pedido, Long idProducto, int cantidad) throws EntidadNoEncontradaException, StockInvalidoException {
        
        Producto producto = productoService.buscarProductoPorId(idProducto);
        pedido.addDetallePedido(cantidad, producto.getPrecio(), producto);
    }
    
    public void eliminarPedido(Long id) {

        try {
            Pedido pedido = buscarPedidoPorId(id);
            pedido.setEliminado(true);
            
            for (DetallePedido detalle : pedido.getDetalles()) {
                detalle.setEliminado(true);
            }

            System.out.println(
                    "   Éxito! Pedido eliminado correctamente.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void editarPedido(Long id, Estado estado, FormaPago formaPago) {
        try {
            Pedido pedido = buscarPedidoPorId(id);
            if (estado != null) {
                pedido.setEstado(estado);
            }

            if (formaPago != null) {
                pedido.setFormaPago(formaPago);
            }

            System.out.println(
                    "Éxito! Pedido actualizado correctamente.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }
}