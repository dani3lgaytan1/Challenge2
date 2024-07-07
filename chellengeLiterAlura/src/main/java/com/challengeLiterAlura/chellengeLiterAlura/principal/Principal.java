package com.challengeLiterAlura.chellengeLiterAlura.principal;

import com.challengeLiterAlura.chellengeLiterAlura.model.Autor;
import com.challengeLiterAlura.chellengeLiterAlura.model.DatosLibro;
import com.challengeLiterAlura.chellengeLiterAlura.model.DatosRespuestaLibros;
import com.challengeLiterAlura.chellengeLiterAlura.model.Libro;
import com.challengeLiterAlura.chellengeLiterAlura.repository.LibroRepository;
import com.challengeLiterAlura.chellengeLiterAlura.service.ApiLibros;
import com.challengeLiterAlura.chellengeLiterAlura.service.ConvierteDatos;
import org.w3c.dom.ls.LSException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ApiLibros consumoApi = new ApiLibros();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/?";
    private LibroRepository repositorio;
    private List<Libro> librosGuardados ; // lista donde se obtienen los libros guardados en la base de datos
    private List<Autor>  autoresGuardados; //lista de los autores guardados
    public Principal(LibroRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    
                    1 - Buscar Libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar libros por idioma
                    5 - Listar autores vivos en un determinado año
             
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3 :
                    mostrarAutoresRegistrados();
                    break;

                case 4 :
                    buscarLibrosIdioma();

                    break;

                case 5 :

                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }
    public List<DatosLibro> getDatosLibro(){
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        String urlf= URL_BASE +"search="+nombreLibro.replace(" ", "%20");
        var json = consumoApi.obtenerDatos(urlf);
        DatosRespuestaLibros datos = conversor.obtenerDatos(json, DatosRespuestaLibros.class);
      //  System.out.println(datos);
        return datos.datosLibros();
     }

     public void buscarLibro(){

         DatosLibro datos = getDatosLibro().get(0);
         Libro libro1 = new Libro(datos); //obtener los datos de la serie

         //guardar el libro buscado en la base de datos
         repositorio.save(libro1);

         System.out.println(libro1);
     }

    private void mostrarLibrosRegistrados(){
        librosGuardados = repositorio.findAll();

        librosGuardados
                .forEach(System.out::println);

    }

    private void buscarLibrosIdioma() {
        System.out.println("Escribe el idioma por el que quieres buscar:  ");
        var idiomaLibro = teclado.nextLine();

        //crear la lista donde se va almacenar las series:

        List<Libro> libroBuscados;
        libroBuscados = repositorio.findByIdiomaContainsIgnoreCase(idiomaLibro);
        libroBuscados.forEach(l -> System.out.println( l.toString() + "\n") );
    }

    private  void mostrarAutoresRegistrados(){
        autoresGuardados = repositorio.autoresRegistrados();
        autoresGuardados.forEach(a-> System.out.println(a.toString() + "\n"));
    }

    private  void  mostrarAutoresFecha(){
        System.out.println("Escribe el primer :  ");
        var idiomaLibro = teclado.nextLine();
    }


}
