
package integrado.prog2.services;

import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Categoria;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.PrecioInvalidoException;
import integrado.prog2.exception.StockInvalidoException;

import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    //atributos
    private final List<Producto> productos; //aqui se acumularán los productos instanciados
    private Long contadorId = 1L; //id autoincremental
    //al igual que Producto posee una relacion con Categoria, sus service también lo hará para poder disponer de sus métodos, por ejemplo buscarPorId()
    private final CategoriaService categoriaService; 
    
    //Constructor
    public ProductoService(CategoriaService categoriaService) {
        this.productos = new ArrayList<>();
        this.categoriaService = categoriaService;
    }
    
    // Método para crear un prdocuto validando las reglas de negocio
    public void crearProducto(String nombre, Double precio, String descripcion, int stock, String imagen, Long idCategoria) 
            throws StockInvalidoException, EntidadNoEncontradaException, PrecioInvalidoException {
        
        // Validar precio
        if (precio <= 0) {
            throw new PrecioInvalidoException("Error! El precio no puede ser igual ni menor a 0.");
        }
        // Validar stock
        if (stock < 0) {
            throw new StockInvalidoException("Error! El stock inicial no puede ser menor a 0.");
        }
        
        // Obtener la categoria
        Categoria categoria = categoriaService.buscarCategoriaPorId(idCategoria);
        
        // Si pasa las validaciones, se instancia con el ID correspondiente
        Producto nuevoProducto = new Producto(contadorId++, nombre, precio, descripcion, stock, imagen, categoria);
        
        // Se guarda en la lista general de productos
        productos.add(nuevoProducto);
        
        // se sincroniza con la lista interna de la categoria a la que pertenece
        categoria.agregarProducto(nuevoProducto); 
        System.out.println( "Éxito! Producto creado correctamente.");
        System.out.println(nuevoProducto);
    }

    // Metodo para listar productos Activos (stock = 0 inclusive)
    // se usa en la opcion de edicion del menu de gestion de productos
    public List<Producto> listarProductosActivos() {
        List<Producto> activos = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.isEliminado()) {
                activos.add(p);
            }
        }
        return activos;
    }
    
    // Metodo para listar productos Disponibles (stock != 0)
    //se usa en la opcion 1 del menu de gestion de productos y en la opcion de crear pedido del menu de pedidos.
    public List<Producto> listarProductosDisponibles() {
        List<Producto> disponibles = new ArrayList<>();
        for (Producto p : productos) {
            if(!p.isEliminado() && p.getDisponible() && p.getStock() > 0) {
                disponibles.add(p);
            }
        }
        return disponibles;
    }
    
    // Metodo para buscar un producto por su id
    public Producto buscarProductoPorId(Long id)
            throws EntidadNoEncontradaException{

        for(Producto producto : productos){
            if(producto.getId().equals(id)){
                return producto;
            }
        }
        throw new EntidadNoEncontradaException(
                "   Error! Producto no encontrado."
        );
    }
    
    // Metodo para editar un producto
    public void editarProducto(Long id, String nombre, Double precio, String descripcion, Integer stock, String imagen, Long idCategoria) {
        try {
            Producto producto = buscarProductoPorId(id);
            if (!nombre.isBlank()) {
                producto.setNombre(nombre);
            }
            if (precio >= 0 && precio != null) {
                producto.setPrecio(precio);
            }
            if (!descripcion.isBlank()) {
                producto.setDescripcion(descripcion);
            }
            if (stock >= 0 && stock != null) {
                producto.setStock(stock);
                producto.setDisponible();
            }
            if (!imagen.isBlank()) {
                producto.setImagen(imagen);
            }
            
            if(!idCategoria.equals("")) {
                Categoria categoria = categoriaService.buscarCategoriaPorId(idCategoria);
                producto.setCategoria(categoria);
            }
            System.out.println("   Éxito! producto actualizado correctamente.");
            
        } catch (EntidadNoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //metodo para eliminar una categoria
    public void eliminarProducto(Long id) {
        try {
            Producto producto = buscarProductoPorId(id);
            producto.setEliminado(true);
            System.out.println("   Éxito! Producto eliminado correctamente.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }
}
