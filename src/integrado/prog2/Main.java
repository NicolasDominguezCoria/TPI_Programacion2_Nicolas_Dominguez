
package integrado.prog2;

import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.services.*;
import integrado.prog2.enums.*;
import integrado.prog2.exception.*;

import java.util.Scanner;

public class Main {
    //creamos el scanner
    private static final Scanner sc = new Scanner(System.in);
    
    //instanciamos los services
    private static CategoriaService categoriaService = new CategoriaService();
    private static ProductoService productoService = new ProductoService(categoriaService);
    private static UsuarioService usuarioService = new UsuarioService();
    private static PedidoService pedidoService = new PedidoService(usuarioService, productoService);
    
    public static void main(String[] args) throws StockInvalidoException, EntidadNoEncontradaException, PrecioInvalidoException, EmailDuplicadoException {
        //mensaje de bienvenida
        System.out.println("BIENVENIDO!");
        //creacion del menu
        int opcion;
        do {
            System.out.println("\n===== SISTEMA DE PEDIDOS (FOOD STORE) =====");
            System.out.println("1 - Categorias");
            System.out.println("2 - Productos");
            System.out.println("3 - Usuarios");
            System.out.println("4 - Pedidos");
            System.out.println("0 - Salir");

            System.out.print("   Seleccione una opción para continuar: ");
            opcion = leerEntero();
            
            switch(opcion){
                case 1:
                    menuCategorias();
                    break;
                case 2:
                    menuProductos();
                    break;
                case 3:
                    menuUsuarios();
                    break;
                case 4:
                    menuPedidos();
                    break;
                case 0:
                    System.out.println("\n    Gracias por usar el sistema de gestion de Pedidos FOOD STORE!\n    HASTA LA PRÓXIMA.");
                    break;
                default:
                    System.out.println("  Error! Opción invalida, intente nuevamente");
            }

        } while(opcion != 0);
    }
    // Metodos adicionales que nos permiten gestionar el ingreso de informacion
    // Método que captura errores si el usuario ingresa texto en vez de un número entero
    private static int leerEntero() {
        while(true) {
            try{
               return Integer.parseInt(sc.nextLine()); 
            } catch (NumberFormatException e) {
                System.out.print(  "Error! Ingrese un número entero válido: ");
            }
        }
    }
    // Metodo que captura error si el usuario ingresa texto en vez de un double
    private static Double leerDouble() {
        while(true) {
            try{
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Error! Ingrese un número válido: ");
            }
        }
    }
    //metodo que permite dejar vacio un imput Integer cuando se trabaja con una variable int
    //se usa para la edicion de productos, cuando se quiere conservar el valor de un int tal como está,
    private static Integer leerIntegerOpcional() {
        String entrada = sc.nextLine();

        if (entrada.isBlank()) {
            return null;
        }

        return Integer.parseInt(entrada);
    }
    //metodo que permite dejar vacio un imput Integer cuando se trabaja con una variable int
    //se usa para la edicion de productos, cuando se quiere conservar el valor de un int tal como está,
    private static Double leerDoubleOpcional() {
        String entrada = sc.nextLine();
        if (entrada.isBlank()) {
            return null;
        }
        return Double.parseDouble(entrada);
    }

    
    //menu de gestion de categorias
    public static void menuCategorias() {
        int opcion;
        do {
            System.out.println("\n===== GESTION DE CATEGORIAS =====");
            System.out.println("1 - Listar categorias");
            System.out.println("2 - Crear categoria");
            System.out.println("3 - Editar categoria");
            System.out.println("4 - Eliminar categoria");
            System.out.println("0 - Volver");

            System.out.print("   Seleccione una opción: ");
            opcion = leerEntero();

            switch(opcion){

                case 1:
                    System.out.println(" - LISTADO DE CATEOGIAS:");
                    if(categoriaService.listarCategorias().isEmpty()) {
                        System.out.println("    No hay categorías en existencia");
                    } else {
                        for(Categoria categoria : categoriaService.listarCategorias()) {
                            System.out.println(categoria);
                        }
                    }
                    break;
                case 2:
                    System.out.println(" - CREAR CATEGORIA: \n Complete los campos para crear una nueva categoría:");
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    
                    System.out.print("Descripción: ");
                    String descripcion = sc.nextLine();
                    
                    categoriaService.crearCategoria(nombre, descripcion);
                    break;

                case 3:
                    System.out.println(" - EDITAR CATEGORIA: \n Seleccione una categoria de la lista por su id");
                    for(Categoria categoria : categoriaService.listarCategorias()) {
                        System.out.println(categoria);
                    }
                    
                    System.out.print("Ingrese el ID de la categoria a editar: ");
                    Long id = (long) leerEntero();
                    
                    System.out.print("Nuevo nombre (dejar en blanco para saltear): ");
                    String nuevoNombre = sc.nextLine();

                    System.out.print("Nueva descripcion (dejar en blanco para saltear): ");
                    String nuevaDescripcion = sc.nextLine();
                    
                    categoriaService.editarCategoria(id,nuevoNombre,nuevaDescripcion);
                    break;

                case 4:
                    for(Categoria categoria : categoriaService.listarCategorias()) {
                        System.out.println(categoria);
                    }
                    
                    System.out.print("Ingrese ID de la categoria a eliminar: ");
                    Long idEliminar = (long) leerEntero();

                    System.out.print("  ¿Confirmar eliminación? (S/N): ");
                    String respuesta = sc.nextLine();

                    if(respuesta.equalsIgnoreCase("S")){
                        categoriaService.eliminarCategoria(idEliminar);
                    }
                    break;

                case 0:
                    break;

                default:
                    System.out.println("  Error! Opcion invalida.");
            }

        } while(opcion != 0);

    }
    
    //menu de gestion de productos
    public static void menuProductos() throws StockInvalidoException, EntidadNoEncontradaException, PrecioInvalidoException {

        int opcion;

        do {
            System.out.println("\n===== GESTION DE PRODUCTOS =====");
            System.out.println("1 - Listar productos");
            System.out.println("2 - Crear producto");
            System.out.println("3 - Editar producto");
            System.out.println("4 - Eliminar producto");
            System.out.println("0 - Volver");

            System.out.print("   Seleccione una opcion: ");
            opcion = leerEntero();

            switch(opcion){

                case 1:
                    System.out.println(" - LISTADO DE PRODUCTOS:");
                    if(productoService.listarProductosDisponibles().isEmpty()) {
                        System.out.println("    No hay productos en existencia.");
                    } else {
                        for(Producto producto : productoService.listarProductosDisponibles()) {
                            System.out.println(producto);
                        }
                    }
                    break;

                case 2:
                    System.out.println(" - CREAR PRODUCTO: \n Complete los campos para crear un nuevo producto:");
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    
                    System.out.print("Precio: ");
                    Double precio = leerDouble();
                    
                    System.out.print("Descripción: ");
                    String descripcion = sc.nextLine();
                    
                    System.out.print("Stock: ");
                    int stock = leerEntero();
                    
                    System.out.print("Imagen: ");
                    String imagen = sc.nextLine();
                    
                    System.out.println("Seleccione una categoria de la lista: ");
                    if(categoriaService.listarCategorias().isEmpty()) {
                        System.out.println("    No hay categorías cargadas");
                    } else {
                        for(Categoria categoria : categoriaService.listarCategorias()) {
                            System.out.println(categoria);
                        }
                    }
                    
                    System.out.print("Ingrese ID de la categoria: ");
                    Long idCategoria = (long) leerEntero();
                    
                    productoService.crearProducto(nombre, precio, descripcion, stock, imagen, idCategoria);
                    break;

                case 3:
                    System.out.println(" - EDITAR PRODUCTO: \n Seleccione una producto de la lista por su id");
                    for(Producto producto : productoService.listarProductosActivos()) {
                        System.out.println(producto);
                    }
                    
                    System.out.print("Ingrese el ID del producto a editar: ");
                    Long idProducto = (long) leerEntero();
                    
                    System.out.print("Nuevo nombre (dejar en blanco para saltear): ");
                    String nuevoNombre = sc.nextLine();
                    
                    System.out.print("Nuevo precio (dejar en blanco para saltear): ");
                    Double nuevoPrecio = leerDoubleOpcional();

                    System.out.print("Nueva descripcion (dejar en blanco para saltear): ");
                    String nuevaDescripcion = sc.nextLine();
                    
                    System.out.print("Nuevo stock (dejar en blanco para saltear): ");
                    Integer nuevoStock = leerIntegerOpcional();
                    
                    System.out.print("Nueva imagen (dejar en blanco para saltear): ");
                    String nuevaImagen = sc.nextLine();
                    
                    System.out.println("Seleccione una categoria de la lista: ");
                    if(categoriaService.listarCategorias().isEmpty()) {
                        System.out.println("    No hay categorías en existencia.");
                    } else {
                        for(Categoria categoria : categoriaService.listarCategorias()) {
                            System.out.println(categoria);
                        }
                    }
                    System.out.print("Nueva categoria (dejar en blanco para saltear): ");
                    Long nuevoIdCategoria = (long) leerEntero();
                    
                    productoService.editarProducto(idProducto, nuevoNombre, nuevoPrecio, nuevaDescripcion, nuevoStock, nuevaImagen, nuevoIdCategoria);
                    break;

                case 4:
                    for(Producto producto : productoService.listarProductosActivos()) {
                        System.out.println(producto);
                    }
                    
                    System.out.print("Ingrese ID del producto: ");
                    Long idEliminar = (long) leerEntero();

                    System.out.print("¿Confirmar eliminacion? (S/N): ");
                    String respuesta = sc.nextLine();

                    if(respuesta.equalsIgnoreCase("S")){
                        productoService.eliminarProducto(idEliminar);
                    }
                    break;

                case 0:
                    break;

                default:
                    System.out.println("   Opcion invalida.");
            }

        } while(opcion != 0);

    }
    
    //menu para gestionar usuarios
    public static void menuUsuarios() throws EmailDuplicadoException {

        int opcion;

        do {

            System.out.println("\n===== GESTION DE USUARIOS =====");
            System.out.println("1 - Listar usuarios");
            System.out.println("2 - Crear usuario");
            System.out.println("3 - Editar usuario");
            System.out.println("4 - Eliminar usuario");
            System.out.println("0 - Volver");

            System.out.print("   Seleccione una opcion: ");
            opcion = leerEntero();

            switch(opcion){

                case 1:
                    System.out.println(" - Listado de Usuarios:");
                    if(usuarioService.listarUsuarios().isEmpty()) {
                        System.out.println("    No hay usuarios cargados");
                    } else {
                        for(Usuario usuario : usuarioService.listarUsuarios()) {
                            System.out.println(usuario);
                        }
                    }
                    break;

                case 2:
                    System.out.println(" - CREAR USUARIO: \n Complete los campos para crear un nuevo usuario:");
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    
                    System.out.print("Apellido: ");
                    String apellido = sc.nextLine();
                    
                    System.out.print("mail: ");
                    String mail = sc.nextLine();
                    
                    System.out.print("celular: ");
                    String celular = sc.nextLine();
                    
                    System.out.print("constraseña: ");
                    String contrasenia = sc.nextLine();
                    
                    System.out.println("Seleccione el Rol:\n  1.USUARIO\n  2.ADMIN");
                    System.out.print("Su opción (numero): ");
                    int opcionRol = leerEntero();
                    Rol rol = (opcionRol == 2) ? Rol.ADMIN : Rol.USUARIO;
                    
                    try{
                    usuarioService.crearUsuario(nombre, apellido, mail, celular, contrasenia, rol);
                    } catch (EmailDuplicadoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println(" - Editar usuario: Seleccione un usuario de la lista por su id");
                    if(usuarioService.listarUsuarios().isEmpty()) {
                        System.out.println("    No hay usuarios cargados");
                        break;
                    } else {
                        for(Usuario usuario : usuarioService.listarUsuarios()) {
                            System.out.println(usuario);
                        }
                    }
                    System.out.print("Ingrese iD de usuario a editar: ");
                    Long id = (long) leerEntero();
                    
                    System.out.print("Nuevo nombre (dejar en blanco para saltear): ");
                    String nuevoNombre = sc.nextLine();

                    System.out.print("Nuevo apellido (dejar en blanco para saltear): ");
                    String nuevoApellido = sc.nextLine();
                    
                    System.out.print("Nuevo mail (dejar en blanco para saltear): ");
                    String nuevoMail = sc.nextLine();
                    
                    System.out.print("Nuevo celular (dejar en blanco para saltear): ");
                    String nuevoCelular = sc.nextLine();
                    
                    System.out.print("Nueva contraseña (dejar en blanco para saltear): ");
                    String nuevaContrasenia = sc.nextLine();
                    
                    System.out.println("Seleccione el nuevo Rol:\n  1.USUARIO\n  2.ADMIN");
                    System.out.print("Su opcion (numero): ");
                    int opcionNuevoRol = leerEntero();
                    Rol nuevoRol = (opcionNuevoRol == 2) ? Rol.ADMIN : Rol.USUARIO;
                    
                    try{
                    usuarioService.editarUsuario(id, nuevoNombre, nuevoApellido, nuevoMail, nuevoCelular, nuevaContrasenia, nuevoRol);
                    } catch (EntidadNoEncontradaException | EmailDuplicadoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    for(Usuario usuario : usuarioService.listarUsuarios()) {
                        System.out.println(usuario);
                    }
                    
                    System.out.print("Ingrese ID de la categoria: ");
                    Long idEliminar = (long) leerEntero();

                    System.out.print("¿Confirmar eliminacion? (S/N): ");
                    String respuesta = sc.nextLine();

                    if(respuesta.equalsIgnoreCase("S")){
                        usuarioService.eliminarUsuario(idEliminar);
                    }
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while(opcion != 0);

    }
    
    //menu para gestionar pedidos
    public static void menuPedidos() {

        int opcion;

        do {
            System.out.println("\n===== GESTION DE PEDIDOS =====");
            System.out.println("1 - Listar pedidos");
            System.out.println("2 - Crear pedido");
            System.out.println("3 - Editar pedido");
            System.out.println("4 - Eliminar pedido");
            System.out.println("5 - Listar pedidos por usuario");
            System.out.println("0 - Volver");

            System.out.print("Seleccione una opcion: ");
            opcion = leerEntero();

            switch(opcion){

                case 1:
                    System.out.println(" - LISTADO DE PEDIDOS:");
                    if(pedidoService.listarPedidos().isEmpty()) {
                        System.out.println("    No hay pedidos en existencia");
                    } else {
                        for(Pedido pedido : pedidoService.listarPedidos()) {
                            System.out.println(pedido);
                        }
                    }
                    break;

                case 2:
                    System.out.println(" - CREAR UN PEDIDO:");
                    try{
                        //mostrar usuarios
                        System.out.println("Usuarios disponibles: ");
                        if(usuarioService.listarUsuarios().isEmpty()) {
                            System.out.println("    No hay usuarios en existencia. Creación de pedido cancelada");
                            break; //cancela el pedido si no encuentra usuarios
                        } else {
                            for (Usuario usuario : usuarioService.listarUsuarios()) {
                                System.out.println(usuario);
                            }
                        }
                        System.out.print("Selecciona el Id del usuario: ");
                        Long idUsuario = (long) leerEntero();
                        
                        //Forma de pago
                        System.out.print(""" 
                                    Forma de pago:
                                      1. TARJETA
                                      2. TRANSFERENCIA
                                      3. EFECTIVO
                                           Tu opción: 
                                           """);
                        int opcionFormaPago = leerEntero();
                        FormaPago formaPago;
                        
                        switch(opcionFormaPago){
                            case 1:
                                formaPago = FormaPago.TARJETA;
                                break;
                            case 2:
                                formaPago = FormaPago.TRANSFERENCIA;
                                break;
                            default:
                                formaPago = FormaPago.EFECTIVO;
                        }
                        
                        //Crear pedido
                        Pedido pedido = pedidoService.crearPedido(idUsuario, formaPago);
                        String continuar;
                        do{
                            System.out.println("\n Productos disponibles:");
                            if(productoService.listarProductosDisponibles().isEmpty()) {
                                System.out.println("No hay productos en existencia.");
                                break;
                            } else{
                                for (Producto producto : productoService.listarProductosDisponibles()) {
                                    System.out.println(producto);
                                }
                            }
                            System.out.print("Id producto: ");
                            Long idProducto = (long) leerEntero();
                            
                            System.out.print("Cantidad: ");
                            int cantidad = leerEntero();
                            
                            pedidoService.agregarDetalleAPedido(pedido, idProducto, cantidad);
                            
                            System.out.print("¿Agregar otro producto? (S/N): ");
                            continuar = sc.nextLine();
                        } while (continuar.equalsIgnoreCase("S"));
                        
                        pedidoService.guardarPedido(pedido);
                        System.out.println("  Exito! Pedido creado correctamente.");
                        
                    } catch(EntidadNoEncontradaException | StockInvalidoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("- EDITAR UN PEDIDO:");
                    try{
                        for (Pedido pedido : pedidoService.listarPedidos()) {
                            System.out.println(pedido);
                        }
                        System.out.print("Ingrese el Id del pedido a editar: ");
                        Long idPedido = (long) leerEntero();
                        
                        System.out.print("""
                                Estado:
                                1. PENDIENTE
                                2. CONFIRMADO
                                3. TERMINADO
                                4. CANCELADO
                                      Tu opción: 
                                """);
                        int opcionEstado = leerEntero();
                        
                        Estado estado;
                        switch(opcionEstado){

                            case 2:
                                estado = Estado.CONFIRMADO;
                                break;

                            case 3:
                                estado = Estado.TERMINADO;
                                break;

                            case 4:
                                estado = Estado.CANCELADO;
                                break;

                            default:
                                estado = Estado.PENDIENTE;
                        }
                        
                        System.out.print("""
                                Forma pago:
                                1-TARJETA
                                2-TRANSFERENCIA
                                3-EFECTIVO
                                     Tu opción: 
                                """);

                        int opcionPago = leerEntero();

                        FormaPago formaPago;

                        switch(opcionPago){

                            case 1:
                                formaPago = FormaPago.TARJETA;
                                break;

                            case 2:
                                formaPago = FormaPago.TRANSFERENCIA;
                                break;

                            default:
                                formaPago = FormaPago.EFECTIVO;
                        }

                        pedidoService.editarPedido(idPedido, estado, formaPago);
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println(" - ELIMINAR PEDIDO:");
                    for(Pedido pedido : pedidoService.listarPedidos()) {
                        System.out.println(pedido);
                    }
                    
                    System.out.print("Ingrese ID del pedido: ");
                    Long idEliminar = (long) leerEntero();;

                    System.out.print("¿Confirmar eliminacion? (S/N): ");
                    String respuesta = sc.nextLine();

                    if(respuesta.equalsIgnoreCase("S")){
                        pedidoService.eliminarPedido(idEliminar);
                    }
                    break;
                    
                case 5:
                    try{
                        for (Usuario usuario : usuarioService.listarUsuarios()) {
                            System.out.println(usuario);
                        }
                        System.out.println("ID del usuario: ");
                        Long idUsuario = (long) leerEntero();
                        
                        for(Pedido pedido : pedidoService.listarPedidosPorUsuario(idUsuario)) {
                            System.out.println(pedido);
                            for(var detalle : pedido.getDetalles()){
                                System.out.println("  " + detalle);
                            }
                        }
                    } catch(EntidadNoEncontradaException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while(opcion != 0);

    }
}
