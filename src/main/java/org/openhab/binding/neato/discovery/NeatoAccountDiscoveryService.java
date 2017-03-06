package org.openhab.binding.neato.discovery;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.io.net.http.HttpUtil;
import org.openhab.binding.neato.NeatoBindingConstants;
import org.openhab.binding.neato.handler.NeatoHandler;
import org.openhab.binding.neato.internal.NeatoHandlerFactory;
import org.openhab.binding.neato.internal.Vendor;
import org.openhab.binding.neato.internal.VendorFactory;
import org.openhab.binding.neato.internal.classes.BeehiveAuthentication;
import org.openhab.binding.neato.internal.classes.NeatoAccountInformation;
import org.openhab.binding.neato.internal.classes.Robot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 *
 * @author Florian Dietrich - Vendor added
 *
 */
public class NeatoAccountDiscoveryService extends AbstractDiscoveryService {

    private static final Logger logger = LoggerFactory.getLogger(NeatoHandler.class);

    private static final int TIMEOUT = 15;
    private SecureRandom random = new SecureRandom();

    private String accessToken;

    public NeatoAccountDiscoveryService() throws IllegalArgumentException {
        super(NeatoHandlerFactory.SUPPORTED_THING_TYPES_UIDS, TIMEOUT);
        this.accessToken = null;
    }

    private String sendAuthRequestToNeato(String data, Vendor vendor) {
        // Properties headers = new Properties
        Properties headers = new Properties();
        headers.setProperty("Accept", "application/vnd.neato.nucleo.v1");

        if (this.accessToken != null) {
            headers.setProperty("Token token", this.accessToken);
        }

        String resultString = "";

        try {

            InputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));

            resultString = HttpUtil.executeUrl("POST", vendor.getBeehiveUrl() + "/sessions", headers, stream,
                    "application/json", 20000);

            logger.info(resultString);

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultString;
    }

    private Boolean authenticate(String username, String password, Vendor vendor) {

        String authenticationString;
        try {
            authenticationString = "{\"email\": \"" + username + "\", \"password\": \"" + password
                    + "\", \"os\": \"ios\", \"token\": \"" + new BigInteger(130, random).toString(64).getBytes("UTF-8")
                    + "\"}";
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

        String authenticationResponse = sendAuthRequestToNeato(authenticationString, vendor);

        logger.info("Authentication Response: {}", authenticationResponse);

        Gson gson = new Gson();

        BeehiveAuthentication authenticationObject = gson.fromJson(authenticationResponse, BeehiveAuthentication.class);
        this.accessToken = authenticationObject.getAccessToken();

        return true;
    }

    public Boolean sendGetRobots(Vendor vendor) {
        // resp = requests.get(urljoin(self.ENDPOINT, 'dashboard'), headers=self._headers)
        // Properties headers = new Properties
        Properties headers = new Properties();
        headers.setProperty("Accept", "application/vnd.neato.nucleo.v1");

        if (this.accessToken != null) {
            headers.setProperty("Authorization", "Token token=" + this.accessToken);
        }

        String resultString = "";

        try {

            resultString = HttpUtil.executeUrl("GET", vendor.getBeehiveUrl() + "/dashboard", headers, null,
                    "application/json", 20000);

            Gson gson = new Gson();
            NeatoAccountInformation accountInformation = gson.fromJson(resultString, NeatoAccountInformation.class);

            logger.info(resultString);

            List<Robot> mrRobots = accountInformation.getRobots();

            for (int i = 0; i < mrRobots.size(); i++) {
                Robot mrRobot = accountInformation.getRobots().get(i);
                addThing(mrRobot, vendor);
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    public void getRobotsFromNeato() {
        if (NeatoHandlerFactory.email != null) {
            authenticate(NeatoHandlerFactory.email, NeatoHandlerFactory.password,
                    VendorFactory.createVendor(NeatoHandlerFactory.vendor));
        }

        if (this.accessToken != null) {
            sendGetRobots(VendorFactory.createVendor(NeatoHandlerFactory.vendor));
        }

    }

    @Override
    protected void startBackgroundDiscovery() {
        logger.debug("First - let's authenticate!");
        getRobotsFromNeato();
    }

    @Override
    protected void startScan() {
        getRobotsFromNeato();
    }

    private void addThing(Robot mrRobot, Vendor vendor) {
        logger.trace("addThing(): Adding new Neato unit {} to the smarthome inbox", mrRobot.getName());

        ThingUID thingUID = null;

        Map<String, Object> properties = null;

        // thingID = "Neato Vacuum " + String.valueOf(zoneNumber);
        // thingLabel = "Zone " + String.valueOf(zoneNumber);
        // ;
        properties = new HashMap<>(0);
        thingUID = new ThingUID(NeatoBindingConstants.THING_TYPE_VACUUMCLEANER, mrRobot.getSerial());
        properties.put(NeatoBindingConstants.CONFIG_NAME, mrRobot.getName());
        properties.put(NeatoBindingConstants.CONFIG_SECRET, mrRobot.getSecretKey());
        properties.put(NeatoBindingConstants.CONFIG_SERIAL, mrRobot.getSerial());
        properties.put(NeatoBindingConstants.CONFIG_VENDOR, vendor.toString());

        DiscoveryResult discoveryResult;

        if (properties != null) {
            discoveryResult = DiscoveryResultBuilder.create(thingUID).withProperties(properties).build();

            thingDiscovered(discoveryResult);
        }
    }

    @Override
    protected void thingDiscovered(DiscoveryResult discoveryResult) {
        // TODO Auto-generated method stub
        super.thingDiscovered(discoveryResult);
    }

}
