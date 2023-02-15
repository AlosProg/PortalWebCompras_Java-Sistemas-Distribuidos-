/*
AEC 3 SSDD: Uso de Servlets y HTML en servicios Web

Antonio Luis Ojeda Soto
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/Variables"})
public class Variables extends HttpServlet {
    // creamos Variables del Servlet
    public int contador = 0;
    String nombre;
    
    // creamos la estructura de datos: colección de objetos componente-cantidad
    Map<String, Integer> pares = new HashMap<>();
   
   // elegimos el método doPost
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter salida = response.getWriter();
        
        // redireccionamos la petición al método hacerPeticiones para darles una respuesta
        hacerPeticiones(salida,request);
    } //end doPost
   
    private synchronized void hacerPeticiones(PrintWriter salida,HttpServletRequest request){
        // recogemos nuestras peticiones y las pasamos a nuestras variables de Servlet
        contador=Integer.parseInt(request.getParameter("cantidad"));
        nombre=request.getParameter("nombre");
        
        // recorremos la colección de objetos componente-cantidad
        for(Map.Entry<String, Integer> entrada:pares.entrySet()){
            if (nombre.equals(entrada.getKey())){ // si vemos que ya había sido introducido el componenete...
                contador = Integer.parseInt(request.getParameter("cantidad")) + entrada.getValue();//... le sumamos la nueva cantidad a la ya existente para ese componente
            } 
        }
        pares.put(nombre, contador); // añadimos el componente y su cantidad ya actualizada
        
        // recorremos la colección para imprimir la cesta de lo que lleva el cliente en ese momento
        for(Map.Entry<String, Integer> entrada:pares.entrySet()){
            salida.println("Ha pedido el componenete: " + entrada.getKey()+" cantidad: " + entrada.getValue());
        }
    } //end hacerPeticiones
}
