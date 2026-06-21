
package integrado.prog2.services;

import integrado.prog2.entities.Categoria;
import integrado.prog2.exception.EntidadNoEncontradaException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaService {
    // Atributos
    private final List<Categoria> categorias; //aqui se acumularán las categorías instanciadas
    private Long contadorId = 1L; //el id se crea aquí y se pasa como parametro cada vez que se intancia una nueva categoria
    
    // Constructor de la lista de categorias
    public CategoriaService() {
        categorias = new ArrayList<>();
    }
    
    // metodo para guardar la nueva categoria
    public void crearCategoria(String nombre, String descripcion){ //los parametros nombre y descripcion se pasan desde el menu correspondiente
        Categoria nuevaCategoria = new Categoria(contadorId++, nombre, descripcion);
        
        for(Categoria c : categorias){
            if(!c.isEliminado() && c.getNombre().equalsIgnoreCase(nuevaCategoria.getNombre())) {
                System.out.println("  Error! La categoría " + nuevaCategoria.getNombre() + " ya existe.");
                return;
            }
        }
        categorias.add(nuevaCategoria);
        System.out.println("    Exito! La categoría " + nuevaCategoria.getNombre() + " ha sido creada correctamente:");
        System.out.println(nuevaCategoria);
    }
    
    // metodo para listar categorias activas
    public List<Categoria> listarCategorias(){
        List<Categoria> categoriasActivas = new ArrayList<>();
        for(Categoria categoria : categorias){
            if(!categoria.isEliminado()){
                categoriasActivas.add(categoria);
            }
        }
        return categoriasActivas;
    }
    
    //metodo para buscar una categoria por id
    public Categoria buscarCategoriaPorId(Long id)
            throws EntidadNoEncontradaException{

        for(Categoria categoria : categorias){
            if(categoria.getId().equals(id) && !categoria.isEliminado()){
                return categoria;
            }
        }
        throw new EntidadNoEncontradaException(
                "   Error! Categoría no encontrada."
        );
    }
    
    //metodo para editar una categoria
    public void editarCategoria(Long id, String nombre, String descripcion) {
        try {
            Categoria categoria = buscarCategoriaPorId(id);
            if (!nombre.isBlank()) {
                categoria.setNombre(nombre);
            }
            if (!descripcion.isBlank()) {
                categoria.setDescripcion(descripcion);
            }
            System.out.println("    Éxito! Categoría actualizada correctamente.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println(e.getMessage());

        }
    }
    
    //metodo para eliminar una categoria
    public void eliminarCategoria(Long id) {
        try {
            Categoria categoria = buscarCategoriaPorId(id);
            categoria.setEliminado(true);
            System.out.println("   Éxito! Categoría eliminada correctamente.");
            
        } catch (EntidadNoEncontradaException e) {
            System.out.println(e.getMessage());

        }
        
    }
    
}
