/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demoRest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * REST Web Service
 *
 * @author edgardo
 */
@Path("getPdfB64")
public class getPdfB64 {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of getPdfB64
     */
    public getPdfB64() {
    }

    /**
     * Retrieves representation of an instance of demoRest.getPdfB64
     *
     * @param term
     * @return an instance of java.lang.String
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@QueryParam("term") String term) {
        try {
            //TODO return proper representation object
            String ruta = "doc.pdf";
            InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream(ruta);
            byte[] bytes64bytes = IOUtils.toByteArray(file);
            String result = Base64.encodeBase64String(bytes64bytes);
            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }
}
