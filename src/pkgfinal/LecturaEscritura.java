package pkgfinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class LecturaEscritura {

    //esta funcion permite registrar un nuevo dato en un archivo
    // @url es la ruta del archivo al cual vamos a agregar
    // @datosAgregar es un string el cual esta en formato csv el cual son los datos a agregar
    static void RegistrarDato(String url, String datosAgregar) {
        File rutaCompra = new File(url);
        //nos lee primeramente el archivo usuario.csv y lo guarda en la variable temp
        try {
            rutaCompra.createNewFile();
            FileReader lectorCompra = new FileReader(rutaCompra);

            BufferedReader bf = new BufferedReader(lectorCompra);
            String s = bf.readLine();

            while (s != null) {
                datosAgregar = datosAgregar + s + "\n";
                s = bf.readLine();
            }
            lectorCompra.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            FileWriter escritor;
            rutaCompra.createNewFile();
            escritor = new FileWriter(rutaCompra);
            escritor.write(datosAgregar);
            escritor.close();
        } catch (IOException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // esta funcion permite borra los datos de la tabla  y volver a cargarlo para tener los datos actualizado
    // @tabla es la tabla que estamos editando (el que colocamos en el diseño)
    // @url es de donde vamos a sacar la datos para actualizar
    // @datosTabla Es la tabla que contiene los datos
    static void actualizarTabla(String url, DefaultTableModel datosTabla, JTable tabla) {

        datosTabla.setRowCount(0);

        // Trae/actualiza una lista generica
        File ruta = new File(url);
        FileReader lector;
        try {
            ruta.createNewFile();
            lector = new FileReader(ruta);
            BufferedReader bf = new BufferedReader(lector);
            String linea = bf.readLine();
            String[] LISTA_DE_DATOS;
            //busca linea por linea
            while (linea != null) {
                LISTA_DE_DATOS = linea.split(";");
                String x[] = new String[LISTA_DE_DATOS.length];
                //ciclo para rellenar el vector
                for (int i = 0; i <= LISTA_DE_DATOS.length - 1; i++) {
                    x[i] = LISTA_DE_DATOS[i];
                }

                datosTabla.addRow(x);
                tabla.setModel(datosTabla);
                linea = bf.readLine();
            }
            lector.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginPage.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginPage.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    // esta funcion permite borra los datos de la tabla  y volver a cargarlo para tener los datos actualizado, y ademas
    // de eso permite solo mostrar los datos que coincida con la sesión activa del usuario
    // @tabla es la tabla que estamos editando (el que colocamos en el diseño)
    // @url es de donde vamos a sacar la datos para actualizar
    // @datosTabla Es la tabla que contiene los datos
    // @posABuscar es la posición la cual se va a comprar (dato del archivo pasado por routa)
    // @PosAComparar es la posición con la cual se va a comprara (datos usuario)
    static void actualizarTablaFiltradoCedula(String url, DefaultTableModel datosTabla, JTable tabla, int posABuscar, int PosAComparar) {

        datosTabla.setRowCount(0);

        // Trae/actualiza una lista generica
        File ruta = new File(url);
        FileReader lector;
        try {
            ruta.createNewFile();
            lector = new FileReader(ruta);
            BufferedReader bf = new BufferedReader(lector);
            String linea = bf.readLine();
            String[] LISTA_DE_DATOS;
            //busca linea por linea
            while (linea != null) {
                LISTA_DE_DATOS = linea.split(";");
                String x[] = new String[LISTA_DE_DATOS.length];
                //ciclo para rellenar el vector
                for (int i = 0; i <= LISTA_DE_DATOS.length - 1; i++) {
                    if (LISTA_DE_DATOS[posABuscar].equals(LoginPage.datoDeIngreso[PosAComparar])) {
                        x[i] = LISTA_DE_DATOS[i];
                    }
                }

                // esas solo agrega la fila cuando los datos son iguales
                if (LISTA_DE_DATOS[posABuscar].equals(LoginPage.datoDeIngreso[PosAComparar])) {
                    datosTabla.addRow(x);
                    tabla.setModel(datosTabla);
                }

                linea = bf.readLine();

            }
            lector.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginPage.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginPage.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    //esta funcion permite realizar un compra
    // @url es la ruta del archivo al cual vamos a agregar
    // @idArticulo es el id del producto el cual estamos comprando
    // @cantidad es la cantidad de articulo que vamos a comprar
    // @idCompra es el id al cual se le va a asociar la compra
    // @datosCompra son lo datos de la compra, normalmente se pasa como ""
    static String RealizarCompra(String url, String idArticulo, int cantidad, String idCompra, String datosCompra) {
        File ruta = new File(url);
        FileReader lectorArticulos;
        String tempArticulos = "";
        Date fecha = new Date();
        int year = fecha.getYear() + 1900;
        String fechaFormateada = fecha.getDate() + "/" + fecha.getMonth() + "/" + year;
        try {
            lectorArticulos = new FileReader(ruta);
            BufferedReader bf = new BufferedReader(lectorArticulos);
            String linea = bf.readLine();
            String[] datosProducto;

            while (linea != null) {
                boolean ventaTotal = false;
                datosProducto = linea.split(";");
                if (datosProducto[0].equals(idArticulo)) {

                    // para eliminar el articulo de listadoArticulo cuando se compra la totalidad 
                    if (Integer.parseInt(datosProducto[4]) == cantidad) {
                        linea = "";
                        ventaTotal = true;
                    }

                    //cuando se compra menos de la totalidad se le hace la resta 
                    if (Integer.parseInt(datosProducto[4]) > cantidad) {
                        int resta = Integer.parseInt(datosProducto[4]) - cantidad;
                        linea = datosProducto[0] + ";" + datosProducto[1] + ";" + datosProducto[2] + ";" + datosProducto[3] + ";" + resta + ";" + datosProducto[5] + ";" + datosProducto[6];
                    }

                    datosCompra = idCompra + ";" + fechaFormateada + ";" + datosProducto[1] + ";" + String.valueOf(cantidad) + ";" + datosProducto[3] + ";" + String.valueOf(Integer.parseInt(datosProducto[3]) * cantidad) + ";" + LoginPage.datoDeIngreso[0] + ";" + LoginPage.datoDeIngreso[4] + ";" + datosProducto[5] + ";" + datosProducto[6] + "\n";

                }
                if (!ventaTotal) {
                    tempArticulos = tempArticulos + linea + "\n";
                }
                linea = bf.readLine();
            }
            lectorArticulos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }

        // actualiza el archivo listadoArticulo para que quede con las cantidades correctas
        try {
            FileWriter escritor;
            ruta.createNewFile();
            escritor = new FileWriter(ruta);
            escritor.write(tempArticulos);
            escritor.close();
        } catch (IOException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datosCompra;
    }

    // esta es una funcion que busca cuales empeños se le ha acabado el plazo y lo remueve de listadoEmpeñado.csv y lo coloca en listadoArticulo.csv
    // @url  es la ruta del archivo al cual vamos a agregar
    static void ActualizarPawns(String url) {

        String paws_a_vender = "";

        // Trae/actualiza una lista generica
        File ruta = new File(url);
        FileReader lector;
        String PawnActualizado = "";
        try {
            ruta.createNewFile();
            lector = new FileReader(ruta);
            BufferedReader bf = new BufferedReader(lector);

            String linea = bf.readLine();
            String[] LISTA_DE_DATOS;

            //busca linea por linea
            while (linea != null) {

                LISTA_DE_DATOS = linea.split(";");
                String x[] = new String[LISTA_DE_DATOS.length];
                String y[] = new String[LISTA_DE_DATOS.length];

                //ciclo para rellenar el vector
                for (int i = 0; i <= LISTA_DE_DATOS.length - 1; i++) {
                    //solo guardamos los datos que no supere la fecha actual
                    if (FechaLimiteVigente(LISTA_DE_DATOS[7])) {
                        x[i] = LISTA_DE_DATOS[i];
                    } else {
                        y[i] = LISTA_DE_DATOS[i];
                    }

                }

                if (FechaLimiteVigente(LISTA_DE_DATOS[7].toString())) {
                    PawnActualizado = PawnActualizado + x[0] + ";" + x[1] + ";" + x[2] + ";" + x[3] + ";" + x[4] + ";" + x[5] + ";" + x[6] + ";" + x[7] + ";" + x[8] + ";" + x[9] + "\n";
                } else {
                    String Avender = y[0] + ";" + y[1] + ";" + y[2] + ";" + y[4] + ";" + y[3] + ";" + y[8] + ";" + y[9] + "\n";
                    LecturaEscritura.RegistrarDato("listadoArticulo.csv", Avender);
                    String HisotiralVencido = y[0] + ";" + y[1] + ";" + y[2] + ";" + y[3] + ";" + y[4] + ";" + y[5] + ";" + y[6] + ";" + y[7] + ";" + y[8] + ";" + y[9] + "\n";
                    LecturaEscritura.RegistrarDato("listadoEmpeñadoVencido.csv", HisotiralVencido);
                }

                linea = bf.readLine();
            }

            lector.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }   //3412;Cadena de oro;es una cadena de hace unos 100 años;1;35000000;35000000;23/10/2022;12/03/2003;Valeria;999

        System.out.println(paws_a_vender);

        try {
            FileWriter escritor;
            ruta.createNewFile();
            escritor = new FileWriter(ruta);
            escritor.write(PawnActualizado);
            escritor.close();
        } catch (IOException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //esta funcion me indica si la la fecha pasada por parametro es mayor a la fecha actual
    // @date es la fecha a saber si ya pasó la fecha actual
    public static boolean FechaLimiteVigente(String date) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            Date fechalimte = formatoFecha.parse(date);
            Date hoy = new Date();
            if (fechalimte.after(hoy)) {
                return true;
            }
        } catch (ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return false;
    }

    //
    static void actualizarTablaRange(String Route, DefaultTableModel ModeloDato, JTable Tabla, String CantSearch, String ValorMinSearch, String ValorMaxSearch) {
        ModeloDato.setRowCount(0);
        File ruta = new File(Route);
        FileReader lector;

        try {
            //On these two they autoadd catch
            ruta.createNewFile();
            lector = new FileReader(ruta);
            BufferedReader bf = new BufferedReader(lector);
            String linea = bf.readLine();
            String[] LISTA_DE_DATOS;
            Boolean Approved = false;
            while (linea != null) {
                Approved = false;
                LISTA_DE_DATOS = linea.split(";");
                String x[] = new String[LISTA_DE_DATOS.length];
                if (CantSearch.equals("")) {
                    for (int i = 0; i <= x.length - 1; i++) {
                        if (Integer.parseInt(ValorMinSearch) <= Integer.parseInt(LISTA_DE_DATOS[3])
                                && Integer.parseInt(LISTA_DE_DATOS[3]) <= Integer.parseInt(ValorMaxSearch)) {
                            x[i] = LISTA_DE_DATOS[i];
                            Approved = true;
                        }

                    }
                } else {
                    for (int i = 0; i <= x.length - 1; i++) {
                        if (Integer.parseInt(ValorMinSearch) <= Integer.parseInt(LISTA_DE_DATOS[3])
                                && Integer.parseInt(LISTA_DE_DATOS[3]) <= Integer.parseInt(ValorMaxSearch)
                                && Integer.parseInt(LISTA_DE_DATOS[4]) >= Integer.parseInt(CantSearch)) {
                            x[i] = LISTA_DE_DATOS[i];
                            Approved = true;
                        }

                    }
                }
                if (Approved) {
                    ModeloDato.addRow(x);
                    Tabla.setModel(ModeloDato);

                }
                linea = bf.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //esta funcion te edita los usuario pasandole nuevamente todos los campos
    // @url  es la ruta del archivo al cual vamos a agregar
    // @Abuscar es el dato de usuario(cedula) el cual se va a modificar
    // @dato son los nuevos datos
    static void EditarUsuario(String url, String Abuscar, String datos) {
        String temp = "";
        File ruta = new File(url);
        //nos lee primeramente el archivo listadoArticulo.csv y lo guarda en la variable temp
        try {
            ruta.createNewFile();
            FileReader lector = new FileReader(ruta);

            BufferedReader bf = new BufferedReader(lector);
            String s = bf.readLine();
            String LineData[];
            while (s != null) {
                LineData = s.split(";");
                if (LineData[4].equals(Abuscar)) {
                    temp = temp + datos + "\n";
                } else {
                    temp = temp + s + "\n";
                }
                s = bf.readLine();
            }
            lector.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            FileWriter escritor;
            ruta.createNewFile();
            escritor = new FileWriter(ruta);
            escritor.write(temp);
            escritor.close();
        } catch (IOException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // esta funcion permite solo mostrar los datos que se encuentre en un rango de fecha
    // @tabla es la tabla que estamos editando (el que colocamos en el diseño)
    // @url es de donde vamos a sacar la datos para actualizar
    // @datosTabla Es la tabla que contiene los datos
    // @posABuscar es la posición la cual se va a comprar (dato del archivo pasado por routa)
    // @PosAComparar es la posición con la cual se va a comprara (datos usuario)
    static void filtrarEntreFechas(String url, DefaultTableModel datosTabla, JTable tabla, String FechaInferior, String fechaSuperior, int IDfechaRegistrada) throws ParseException {
        datosTabla.setRowCount(0);

        // Trae/actualiza una lista generica
        File ruta = new File(url);
        FileReader lector;
        try {
            ruta.createNewFile();
            lector = new FileReader(ruta);
            BufferedReader bf = new BufferedReader(lector);
            String linea = bf.readLine();
            String[] LISTA_DE_DATOS;
            //busca linea por linea
            while (linea != null) {
                LISTA_DE_DATOS = linea.split(";");
                String x[] = new String[LISTA_DE_DATOS.length];
                //ciclo para rellenar el vector
                for (int i = 0; i <= LISTA_DE_DATOS.length - 1; i++) {
                    if (isBetween(FechaInferior, fechaSuperior, LISTA_DE_DATOS[IDfechaRegistrada])) {
                        x[i] = LISTA_DE_DATOS[i];
                    }
                }

                // esas solo agrega la fila cuando los datos son iguales
                if (isBetween(FechaInferior, fechaSuperior, LISTA_DE_DATOS[IDfechaRegistrada])) {
                    datosTabla.addRow(x);
                    tabla.setModel(datosTabla);
                }

                linea = bf.readLine();

            }
            lector.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginPage.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginPage.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean isBetween(String FechaInferior, String fechaSuperior, String fechaRegistrada) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date inf = sdf.parse(FechaInferior);
        Date sup = sdf.parse(fechaSuperior);
        Date reg = sdf.parse(fechaRegistrada);
        return reg.after(inf) && reg.before(sup);
    }

    static void EliminarUsuario(String url, String Abuscar) {
        String temp = "";
        File ruta = new File(url);
        //nos lee primeramente el archivo listadoArticulo.csv y lo guarda en la variable temp
        try {
            ruta.createNewFile();
            FileReader lector = new FileReader(ruta);

            BufferedReader bf = new BufferedReader(lector);
            String s = bf.readLine();
            String LineData[];
            while (s != null) {
                LineData = s.split(";");
                if (!LineData[4].equals(Abuscar)) {

                    temp = temp + s + "\n";
                }
                s = bf.readLine();
            }
            lector.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            FileWriter escritor;
            ruta.createNewFile();
            escritor = new FileWriter(ruta);
            escritor.write(temp);
            escritor.close();
        } catch (IOException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
