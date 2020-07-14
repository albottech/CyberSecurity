
package io.kryptoblocks.msa.common.cache.repository;

 
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.security.SecureRandom;

// TODO: Auto-generated Javadoc
/**
 * The Class CacheServerPortGenerator.
 */
public class CacheServerPortGenerator {
    
    /**
     * Generate port.
     *
     * @return the int
     */
    public int generatePort() {
        SecureRandom random = new SecureRandom();
        int port = random.nextInt(10000);
        port += 40000;
        // implement a check to make sure port is not used.
        // on bind exception try again
        System.err.println("Server Port:" + port);
        return port;
    }

    /**
     * Generate port.
     *
     * @param min the min
     * @param max the max
     * @return the int
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public int generatePort(int min, int max) throws IOException {
        ServerSocket socket = new ServerSocket();
        int port = bind(socket, min, max - min);
        if (port>0) {
            socket.close();
            return port;
        } else {
            throw new IOException("Unable to bind on to a port between "+min+" and "+max);
        }
        
    }

    /**
     * Bind.
     *
     * @param socket the socket
     * @param portstart the portstart
     * @param retries the retries
     * @return the int
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public int bind(ServerSocket socket, int portstart, int retries) throws IOException {
        InetSocketAddress addr = null;
        int port = portstart;
        while (retries > 0) {
            try {
                addr = new InetSocketAddress(port);
                socket.bind(addr);
                retries = 0;
                return port;
            } catch (IOException x) {
                retries--;
                if (retries <= 0) {
                    throw x;
                }
                port++;
            }
        }
        return -1;
    }

}
