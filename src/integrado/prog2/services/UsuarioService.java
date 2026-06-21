
package integrado.prog2.services;

import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Rol;
import integrado.prog2.exception.EmailDuplicadoException;
import integrado.prog2.exception.EntidadNoEncontradaException;

import java.util.ArrayList;
import java.util.List;


public class UsuarioService {
    //atributos
    private final List<Usuario> usuarios; //aqui se acumularán los usuarios instanciados
    private Long contadorId = 1L; //id autoincremental
    
    //constructor
    public UsuarioService() {
        usuarios = new ArrayList<>();
    }
    
    
    //metodo para crear usuarios
    public void crearUsuario(String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) throws EmailDuplicadoException {
        for (Usuario usuario : usuarios) {
            if (!usuario.isEliminado() && usuario.getMail().equalsIgnoreCase(mail)) {
                throw new EmailDuplicadoException(
                        "   Error! El mail ingresado ya se encuentra registrado.");
            }
        }
        Usuario nuevoUsuario = new Usuario(contadorId++, nombre, apellido, mail,celular, contrasenia, rol);
        usuarios.add(nuevoUsuario);
        System.out.println("   Éxito! Usuario creado correctamente.");
        System.out.println(nuevoUsuario);
    }
    
    // metodo para listar usuarios activos
    public List<Usuario> listarUsuarios() {
        List<Usuario> activos = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (!usuario.isEliminado()) {
                activos.add(usuario);
            }
        }
        return activos;
    }

    
    // metodo para buscar un usuario por id 
    public Usuario buscarUsuarioPorId(Long id) throws EntidadNoEncontradaException {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id) && !usuario.isEliminado()) {
                return usuario;
            }
        }
        throw new EntidadNoEncontradaException("   Error! Usuario no encontrado.");
    }
    
    //metodo para editar un usuario
     public void editarUsuario(Long id, String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) throws EmailDuplicadoException, EntidadNoEncontradaException {
         
             Usuario usuario = buscarUsuarioPorId(id);
             if(!nombre.isBlank()) {
                usuario.setNombre(nombre);
             }
             if(!apellido.isBlank()) {
                usuario.setApellido(apellido);
             }
             if(!mail.isBlank() && !mail.equalsIgnoreCase(usuario.getMail())) {
                 for (Usuario u : usuarios) {
                     if (!u.isEliminado() && !u.getId().equals(usuario.getId()) && u.getMail().equalsIgnoreCase(mail)) {
                         throw new EmailDuplicadoException(
                         "Error! eL mail ya perteece a otro usuario");
                     }
                 }
                usuario.setMail(mail);
             }
             if(!celular.isBlank()) {
                 usuario.setCelular(celular);
             }
             if(!contrasenia.isBlank()) {
                 usuario.setContrasenia(contrasenia);
             }
             if(rol != usuario.getRol()) {
                usuario.setRol(rol);
             }
             System.out.println("    Éxito! Usuario actualizado correctamente.");
         }

    // Metodo para eliminar usuario (Baja lógica)
    public void eliminarUsuario(Long id) {
        try {
            Usuario usuario = buscarUsuarioPorId(id);
            
            usuario.setEliminado(true);
            System.out.println("   Éxito! Usuario eliminado correctamente.");
            
            
        } catch (EntidadNoEncontradaException e) {
            System.out.println(e.getMessage());

        }
    }
}
