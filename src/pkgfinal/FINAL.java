
package pkgfinal;

//Realizar una aplicación con interfaces gráficas y archivos para una compra – venta

//La aplicación debe contar con 2 módulos uno par el administrador y el otro para usuarios de atención al cliente

//El administrador es el encargado de gestionar los usuarios y ver los reportes

//Los usuarios de atención al cliente pueden:
    // articulos ✅
    // Comprar artículo ✅
    // Empeñar artículo
    // Vender artículo( solo los disponible para la venta) ✅
    // Historial de venta ✅
    // Hisotiial de compra ✅
//Los reportes son:
    //Inventario en cantidad y precios
    //Artículos empeñados vencidos
    //Reporte de ventas por fechas
    //Reporte de empeños por fechas


// usuarios.csv [primerNombre, segundoNombre, PrimerApellido, SegundoApellido, Cedula, isAdmin ] ✅
// listadoArticulo [codigoArticulo, nombre, descripción, precio, cantidad, usuarioDueño, cedulaDueño] ✅
// listadoDeCompra [ codigoCompra, fechaCompra, nombreArticulo, cantidad, precioUnitario, PrecioTotal, usuarioComprador, cedulaUsuarioComprador, nombreVendedor, CedulaVendedor, ] ✅
// listadoArticuloEmpeñado [ codigoPawn, nombreArticulo, descripción, cantidad, precioTotal, fechaInicial, fechaLimite, nombrePawnbroker, cedulaPawnbroker ]

// boton actualizar busca en el el listado de empeño y las fechas vencia lo mueve a listado de articulo

public class FINAL {

    public static void main(String[] args) {
        //Login
        LoginPage lp = new LoginPage();
        lp.setVisible(true);
    }
    
}
