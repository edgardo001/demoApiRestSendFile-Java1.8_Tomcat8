/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demoRest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.FileUtils;

/**
 * REST Web Service
 *
 * @author edgardo
 */
@Path("savePdf") //Nombre del controlador http://localhost:8084/demoSendPdfWS-1/webresources/savePdf
public class savePdf {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of savePdf
     */
    public savePdf() {
    }

    /**
     * Metodo rest encargado de recibir el nombre del documento, la extension y el documento en base 64.
     * Para aumentar el tamaño de recepcion por medio de post, se debe agregar los siguiente al server tomcat8:
     * max-http-post-size="31457280" (para aumentar a 30mb)
     * Quedando de la siguiente manera:
     * <Connector URIEncoding="utf-8" connectionTimeout="20000" bufferSize="65536" socketBuffer="65536" port="8080" protocol="HTTP/1.1" redirectPort="8443" max-http-post-size="31457280"/>
     * Para el consumo, se debe usar la siguiente URL
     * http://localhost:8084/demoSendPdfWS-1/webresources/savePdf
     * http://ip:puerto/nombreProyecto/application_path/path_de_la_clase_a_consumir
     * http://ip:puerto/nombreProyecto/recurso_api/controlador
     * @param name
     * @param extension
     * @param doc
     * @return 
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@FormParam("name") String name, @FormParam("extension") String extension, @FormParam("doc") String doc) {
        //TODO return proper representation object        
        try {
            byte[] pdfByte = Base64toByteArray(doc);//convierto el "doc" a byte array
            String ruta ="C:\\ruta de ejemplo";//Indico donde almacenar            
            FileUtils.writeByteArrayToFile(new File(ruta+"\\"+name+"."+extension), pdfByte);//Lo almaceno        
            
            byte[] b =  readBytesFromFile(ruta+"\\"+name+"."+extension);//Leo el archivo almacenado y lo convierto en byte array
            return "doc: " + ByteArrayToBase64(b) + ", name: " + name;//Retorno la informacion al cliente
            
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    /**
     * Metodo encargado de recibir un byte array y convertirlo en base64
     * @param b
     * @return 
     */
    private String ByteArrayToBase64(byte[] b){
        byte[] encoded = Base64.getEncoder().encode(b);
        return new String(encoded);
    }
    /**
     * Metodo encargado de convertir una cadena en base64 a un byte array
     * @param cadena
     * @return 
     */
    private byte[] Base64toByteArray(String cadena){
        byte[] decoded = Base64.getDecoder().decode(cadena);
        return decoded;
    }  
    /**
     * Metodo encargado de recibir una ruta de un archivo y convertirlo en byte array
     * @param filePath
     * @return 
     */
    private static byte[] readBytesFromFile(String filePath) {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {

            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return bytesArray;

    }
}

/**
 * 
Codificar en B64

Java 8

Encode or decode byte arrays:

byte[] encoded = Base64.getEncoder().encode("Hello".getBytes());
println(new String(encoded));   // Outputs "SGVsbG8="

byte[] decoded = Base64.getDecoder().decode(encoded);
println(new String(decoded))    // Outputs "Hello"

Or if you just want the strings:

String encoded = Base64.getEncoder().encodeToString("Hello".getBytes());
println(encoded);   // Outputs "SGVsbG8="

String decoded = new String(Base64.getDecoder().decode(encoded.getBytes()));
println(decoded)    // Outputs "Hello"

For more info, see Base64.
Java 7

Base64 is not bundled with Java 7. I recommend using Apache Commons Codec.

For direct byte arrays:

Base64 codec = new Base64();
byte[] encoded = codec.encode("Hello".getBytes());
println(new String(encoded));   // Outputs "SGVsbG8="

byte[] decoded = codec.decode(encoded);
println(new String(decoded))    // Outputs "Hello"

Or if you just want the strings:

Base64 codec = new Base64();
String encoded = codec.encodeBase64String("Hello".getBytes());
println(encoded);   // Outputs "SGVsbG8="

String decoded = new String(codec.decodeBase64(encoded));
println(decoded)    // Outputs "Hello"

Android/Java 7

If you are using the Android SDK with Java 7 then your best option is to use the bundled android.util.Base64.

For direct byte arrays:

byte[] encoded = Base64.encode("Hello".getBytes());
println(new String(encoded))    // Outputs "SGVsbG8="

byte [] decoded = Base64.decode(encoded);
println(new String(decoded))    // Outputs "Hello"

Or if you just want the strings:

String encoded = Base64.encodeToString("Hello".getBytes());
println(encoded);   // Outputs "SGVsbG8="

String decoded = new String(Base64.decode(encoded));
println(decoded)    // Outputs "Hello"


 */