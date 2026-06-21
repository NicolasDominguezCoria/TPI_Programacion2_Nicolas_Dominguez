#TRABAJO PRACTICO INTEGRADOR - PROGRAMACION 2 - TUPAD - UTN
# Sistema de Gestión de Pedidos - Consola Java
# Alumno: Daniel Nicolas Dominguez Coria, DNI 35085946, comision 4

¡Bienvenido al Sistema de Gestión de Pedidos! Esta es una aplicación de consola desarrollada en **Java 21**
que implementa un flujo completo de administración para un negocio gastronómico o de ventas, abarcando la
gestión de **Usuarios**, **Productos**, **Categorías** y un sistema interactivo de **Pedidos** con control de stock.

---

##  Características Clave y Arquitectura

El proyecto fue diseñado siguiendo buenas prácticas de la Programación Orientada a Objetos (POO) y principios **SOLID**,
estructurado en una **Arquitectura en Capas** completamente desacoplada:

* **Capa de Presentación (Main/Consola):** Controla la interacción con el usuario, menús interactivos y lectura de datos blindada contra errores de tipeo.
* **Capa de Servicios (Lógica de Negocio):** Centraliza las validaciones de negocio (unicidad de emails, consistencia atómica del stock al crear pedidos).
                                             No depende de la interfaz de consola, quedando **lista y preparada para la futura migración a JDBC / Base de Datos**.
* **Capa de Modelo (Entidades):** Objetos puros de datos que heredan de una clase `Base` común para gestionar IDs de forma limpia.

---

## ️ Requisitos Previos

Antes de ejecutar la aplicación, asegúrate de tener instalado:
* **Java Development Kit (JDK):** Versión 11 o superior (Recomendado JDK 21).
* Una terminal de comandos (Consola, PowerShell, Git Bash) o un IDE compatible (Recomendado NetBeans).

---

## Ejecutar el Proyecto

### Opción 1: Desde un IDE (NetBeans)
1. Importae el proyecto en tu IDE como un proyecto Java estándar.
2. Buscar la clase principal `Main.java` (ubicada en el paquete raíz de la aplicación).
3. Hacer clic derecho sobre la clase y seleccionae **Run 'Main.main()'**.

### Opción 2: Desde la Terminal de Comandos
Para ejecutar el proyecto manualmente desde la raíz del mismo, ejecutar los siguientes comandos:

```bash
# 1. Crear la carpeta para los archivos compilados
mkdir bin

# 2. Compilar todos los archivos .java del proyecto
javac -d bin src/**/*.java

# 3. Ejecutar la aplicación
java -cp bin Main

## Modo de creación de pedidos
1. crear al menos una categoria desde el menú de gestion de categorias, opcion 2.
2. crear al menos un producto y asociarlo a su categoria desde el menú de gestión de productos, opcion 2. (asignar un stock mayor a 0)
3. crear al menos un usuario desde el menú de gestión de usuarios, opcion 2. 
4. crear pedido desde el menú de gestión de pedidos, opcion 2. 
(Estos pasos son obligatorios ya que el sistema no permite registrar pedidos vacios o sin asociarlos a un usuario).

## Estructura de empaquetado
src/
└── integrado/
    └── prog2/
        ├── entities/     # Modelos de datos (Base, Categoria, Usuario, Producto, Pedido, DetallePedido,.)
        ├── enums/        # Enumeradores del sistema (Rol, Estado, FormaPago)
        ├── exception/    # Excepciones personalizadas (CategoriaDuplicadaException, EmailDuplicadoException, EntidadNoEncontradaException, PrecioInvalidoException, StockInvalidoException)
        ├── interfaces/   # Interfaces de comportamiento (Calculable)
        └── services/     # Lógica operativa y de control de persistencia (CategoriaService, ProductoService, UsuarioService, PedidoService)
        Main.java         # Punto de entrada de la aplicación y Menús de Consola

(existe tambien la carpeta config. La misma se encuentra en este momento sin elementos ya que el programa guarda datos en memoria, está pensada por si, en el futuro, se implementa al proyecto JDBC)

Link a repositorio de Github: https://github.com/NicolasDominguezCoria/TPI_Programacion2_Nicolas_Dominguez/tree/main/src
Link al video explicativo:
