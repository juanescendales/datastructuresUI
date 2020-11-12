package app;

import java.util.Hashtable;
import java.util.Scanner;

public class SistemaUNSBN {

    public static Scanner input = new Scanner(System.in);
    public static Hashtable<String, Autor> autores = new Hashtable<>();
    public static Hashtable<String, Libro> libros = new Hashtable<>();
    public static Hashtable<String, String> codigosSedes = new Hashtable<>();
    public static  int  semillaCodigoAutores =0;

    public static void main(String[] args) {
        codigosSedes.put("bogota","23");
        codigosSedes.put("medellin","19");
        codigosSedes.put("manizales","17");
        codigosSedes.put("orinoquia","13");
        codigosSedes.put("amazonia","11");
        codigosSedes.put("caribe","07");
        codigosSedes.put("palmira","05");
        codigosSedes.put("tumaco","02");
        String option;
        while (true) {
            System.out.println();
            System.out.println("-----------------------------");
            System.out.println("Buenas, bienvenido al sistema de tareas");
            System.out.println("Escoja una opcion:");
            System.out.println("1. Ingresar autor.");
            System.out.println("2. Ver autores ingresados");
            System.out.println("3. Ingresar libro.");
            System.out.println("4. Consultar libro.");
            System.out.println("5. Ver libros.");
            System.out.println("0. Salir.");
            System.out.println();
            option = input.next();
            if (option.equals("1")) {
                ingresarAutor();
            }else if (option.equals("2")) {
                verAutores();
            } else if (option.equals("3")) {
                ingresarLibro();
            } else if (option.equals("4")) {
                consultarLibro();
            } else if (option.equals("5")) {
                verLibros();
            } else if (option.equals("0")) {
                break;
            }
        }
    }

    public static void ingresarAutor(){
        System.out.println("Ingrese la cedula del nuevo autor");
        String cedula = input.next();
        if(autores.containsKey(cedula)){
            System.out.println("Este autor ya esta registrado!");
            return;
        }
        System.out.println("Ingrese el nombre del autor");
        input.nextLine();
        String nombre = input.nextLine();
        System.out.println("Ingrese la edad del autor");
        int edad = input.nextInt();
        String codigoUnico = generarCodigoAutor();
        autores.put(cedula,new Autor(cedula,nombre,edad,codigoUnico));

        System.out.println("Autor ingresado satisfactoriamente");
    }
    public static void verAutores(){
        if(autores.isEmpty()){
            System.out.println("No hay autores en el momento");
            return;
        }
        System.out.println("Estos son los autores ingresados hasta el momento");
        for (Autor autor : autores.values()){
            System.out.println("-----------------");
            System.out.println(autor);
            System.out.println();
        }
    }


    public static void ingresarLibro(){
        System.out.println("Ingrese el nombre de la ciudad donde se encuentra la sede");
        String ciudad = input.next().toLowerCase();
        if(!codigosSedes.containsKey(ciudad)){
            System.out.println("El nombre ingresado no corresponde a ninguna sede!");
            return;
        }
        String codigoSede = codigosSedes.get(ciudad);
        System.out.println("Ingrese la cedula del autor");
        String cedula = input.next();
        if(!autores.containsKey(cedula)){
            System.out.println("El autor no se encuentra registrado, debe registrarse primero");
            return;
        }

        Autor autor = autores.get(cedula);
        String codigoAutor = autor.codigoUnicoAutor;

        System.out.println("Ingrese el numero de articulo asignado por el Autor");
        int codigoArticuloInt = input.nextInt();
        if(codigoArticuloInt<0 && codigoArticuloInt > 999){
            System.out.println("Ingrese un codigo valido entre 0 y 999 inclusive");
            return;
        }
        String codigoArticulo =generarCodigoArticulo(codigoArticuloInt);
        String digitoVerificacion = generarDigitoDeVerificacion(codigoSede,codigoAutor,codigoArticulo);

        String UNSBN = codigoSede+codigoAutor+codigoArticulo+digitoVerificacion;
        if(libros.containsKey(UNSBN)){
            System.out.println("Ya existe un libro con el UNSBN generado :" + UNSBN);
            return;
        }

        input.nextLine();
        System.out.println("Ingrese el nombre del libro: ");
        String nombre = input.nextLine();
        System.out.println("Ingrese el genero del libro: ");
        String genero = input.nextLine();
        System.out.println("Ingrese el numero de paginas: ");
        int numeroPaginas = input.nextInt();
        if(numeroPaginas<1){
            System.out.println("Numero de paginas invalido, debe ser un numero mayor a 0");
            return;
        }

        Libro nuevoLibro = new Libro(UNSBN,nombre,genero,numeroPaginas,autor);
        libros.put(UNSBN,nuevoLibro);
        autor.asociarLibro(nuevoLibro);

        System.out.println("Nuevo libro de la UN agregado");
        System.out.println(nuevoLibro);
    }

    public static void consultarLibro(){
        if(libros.isEmpty()){
            System.out.println("No hay libros que consultar aun :( ");
            return;
        }
        System.out.println("Ingrese el UNSBN del libro a consultar: ");
        String UNSBN =  input.next();
        if(libros.containsKey(UNSBN)){
            System.out.println("El libro con UNSBN "+UNSBN+" es el siguiente: ");
            System.out.println(libros.get(UNSBN));
        }else{
            System.out.println("No se encontro este libro");
        }
    }

    public static void verLibros(){
        if(libros.isEmpty()){
            System.out.println("No hay libros que consultar aun :( ");
            return;
        }
        System.out.println("Los libros ingresados hasta este momento son: ");
        for(Libro libro : libros.values()){
            System.out.println("-------------------------");
            System.out.println();
            System.out.println(libro);
            System.out.println();
        }
    }

    public static String generarCodigoAutor(){
        String codigoBase = String.valueOf(semillaCodigoAutores);
        while(codigoBase.length() != 4){
            codigoBase = "0" + codigoBase;
        }
        semillaCodigoAutores++;
        return codigoBase;
    }

    public static String generarCodigoArticulo(int codigoArticulo){
        String codigoBase = String.valueOf(codigoArticulo);
        while(codigoBase.length() != 3){
            codigoBase = "0" + codigoBase;
        }
        return codigoBase;
    }

    public static String generarDigitoDeVerificacion(String codigoSede,String codigoAutor, String codigoArticulo){
        int A = Integer.parseInt(codigoSede);
        int B = Integer.parseInt(codigoAutor);
        int C = Integer.parseInt(codigoArticulo);

        int digitoVerificacion = ((A*B)/C)%9;
        return String.valueOf(digitoVerificacion);
    }
}
