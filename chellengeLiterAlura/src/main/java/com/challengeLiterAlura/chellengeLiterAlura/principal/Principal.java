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
                    mostrarAutoresFecha();
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
         List<DatosLibro> datosLibros = getDatosLibro();

         if (datosLibros.isEmpty()) {
             System.out.println("NO SE ENCONTRO EL LIBRO ");
         }else{
             //obtener los datos del libro
             DatosLibro datos = datosLibros.get(0);

             //convertirlo a un libro:
             Libro libro1 = new Libro(datos);

             //verificar si ya se guardo el libro anteriormente:

             Optional<Libro> libroExistente = repositorio.findByTituloContainsIgnoreCase(libro1.getTitulo());

             if (libroExistente.get().getNumeroDescargas().equals(libro1.getNumeroDescargas()) && libroExistente.get().getTitulo().equals(libro1.getTitulo())){
                 //si el numero de descargas y el titulo es igual entonces no se guarda el libro;
                 System.out.println("NO SE PUEDE INGRESAR EL LIBRO DOS VECES");
             }else{
                 //guardar el libro buscado en la base de datos
                 repositorio.save(libro1);
                 System.out.println(libro1);
             }


         }

     }

    private void mostrarLibrosRegistrados(){
        //obtener todos los libros de la base de datos:
        librosGuardados = repositorio.findAll();

        if (librosGuardados.isEmpty()){
            System.out.println("AUN NO TIENES LIBROS GUARDADOS, AÑADE UNOS ;)");
        }else{
            System.out.println("TODOS LOS LIBROS REGISTRADOS \n");
            librosGuardados.forEach(l -> {
                System.out.println(l);
                System.out.println("-------");
            });
        }


    }

    private void buscarLibrosIdioma() {
        var menu = """
                    es - Español
                    en - Ingles
                    fr - Frances
                    pt - Portuges
                    """;
        System.out.println(menu);
        System.out.println("Escribe el idioma por el que quieres buscar:  ");
        var idiomaLibro = teclado.nextLine();

        if (idiomaLibro.equals("es") || idiomaLibro.equals("en") || idiomaLibro.equals("fr") || idiomaLibro.equals("pt")){
//si se ingreso una opcion  valida entonces se muestra la lista de los libros dependiendo del idioma:
            librosGuardados = repositorio.findByIdiomaContainsIgnoreCase(idiomaLibro);
            if (librosGuardados.isEmpty()){
                System.out.println("NO TENEMOS LIBROS EN ESE IDIOMA POR EL MOMENTO..");
            }else{
                librosGuardados.forEach(l -> {
                    System.out.println(l);
                    System.out.println("-------");
                });
            }

        }else {
            System.out.println("Por favor ingresa una opcion valida");
        }

    }

    private  void mostrarAutoresRegistrados(){
        autoresGuardados = repositorio.autoresRegistrados();
        autoresGuardados.forEach(l -> {
            System.out.println(l);
            System.out.println("-------");
        });

    }

    private  void  mostrarAutoresFecha(){
        System.out.println("Escribe el primer año :  ");
        var fecha = teclado.nextInt();

        autoresGuardados = repositorio.autoresVivosAnio(fecha);
        if (autoresGuardados.isEmpty()){
            System.out.println("NO HAY AUTORES VIVOS EN ESE AÑO");
        }else{
            autoresGuardados.forEach(l -> {
                System.out.println(l);
                System.out.println("-------");
            });
        }





    }


}
