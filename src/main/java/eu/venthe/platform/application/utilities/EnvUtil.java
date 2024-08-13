package eu.venthe.platform.application.utilities;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.URL;

/**
 * Environment util.
 */
@Component
@RequiredArgsConstructor
public class EnvUtil {
    private final Environment environment;

    private String port;
    private String hostname;

    /**
     * Get port.
     *
     * @return
     */
    public String getPort() {
        if (port == null) port = environment.getProperty("local.server.port");
        return port;
    }

    /**
     * Get port, as Integer.
     *
     * @return
     */
    public Integer getPortAsInt() {
        return Integer.valueOf(getPort());
    }

    /**
     * Get hostname.
     *
     * @return
     */
    @SneakyThrows
    public String getHostname() {
        // TODO ... would this cache cause issue, when network env change ???
        if (hostname == null) hostname = InetAddress.getLocalHost().getHostAddress();
        return hostname;
    }

    @SneakyThrows
    public URL getServerUrl() {
        return new URL("http://" + getHostname() + ":" + getPort());
    }
}
