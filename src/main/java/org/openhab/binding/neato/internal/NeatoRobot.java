package org.openhab.binding.neato.internal;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.neato.handler.NeatoHandler;
import org.openhab.binding.neato.internal.classes.NeatoGeneralInfo;
import org.openhab.binding.neato.internal.classes.NeatoRobotInfo;
import org.openhab.binding.neato.internal.classes.NeatoState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * The {@link NeatoBinding} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Patrik Wimnell - Initial contribution
 * @author Florian Dietrich - Vendor added
 */

public class NeatoRobot {

    private static final Logger logger = LoggerFactory.getLogger(NeatoHandler.class);

    private static final String HTTP_ACCEPT = "application/vnd.neato.nucleo.v1";

    private String serialNumber;
    private String secret;
    private String name;
    private Vendor vendor;

    private NeatoState state;
    private NeatoRobotInfo info;
    private NeatoGeneralInfo generalInfo;

    public NeatoState getState() {
        return this.state;
    }

    public NeatoRobotInfo getInfo() {
        return this.info;
    }

    public NeatoGeneralInfo getGeneralInfo() {
        return this.generalInfo;
    }

    public String getName() {
        return this.name;
    }

    public NeatoRobot(String _serial, String _secret, String _name, Vendor _vendor) {
        this.serialNumber = _serial;
        this.secret = _secret;
        this.name = _name;
        this.vendor = _vendor;
        this.state = null;
        this.info = null;
        this.generalInfo = null;
    }

    public String callNeatoWS(String body) throws Exception {
        logger.debug("Request body: {}", body);
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

        // Time in GMT
        String dateString = dateFormatGmt.format(new Date());

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        String stringToSign = this.serialNumber.toLowerCase() + "\n" + dateString + "\n" + body;

        SecretKeySpec secret_key = new SecretKeySpec(this.secret.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        byte[] signature = sha256_HMAC.doFinal(stringToSign.getBytes("UTF-8"));
        String hexString = Hex.encodeHexString(signature);

        // Properties headers = new Properties
        Properties headers = new Properties();
        headers.setProperty("Date", dateString);
        headers.setProperty("Authorization", "NEATOAPP " + hexString);
        headers.setProperty("Accept", "application/vnd.neato.nucleo.v1");

        InputStream stream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));

        String url = String.format("%s/vendors/%s/robots/%s/messages", this.vendor.getNucleoUrl(),
                this.vendor.toString(), this.serialNumber);
        logger.debug("Request URL: {}", url);
        String result = "";
        try {
            result = vendor.executeRequest("POST", url, headers, stream, "text/html; charset=UTF-8", 20000);
            logger.debug("Response body: {}", result);
        } catch (Exception e) {
            logger.error("Error connection nucleo host: ", e);
            throw e;
        }
        return result;
    }

    public Boolean sendCommand(Command command) throws Exception {
        String body = "";

        if (command.toString().equalsIgnoreCase("clean")) {
            body = "{\"reqId\": \"1\", \"cmd\": \"startCleaning\", \"params\": { \"category\": 2, \"mode\": 2, \"modifier\": 2}}";
        } else if (command.toString().equalsIgnoreCase("pause")) {
            body = "{\"reqId\": \"1\", \"cmd\": \"pauseCleaning\"}";
        } else if (command.toString().equalsIgnoreCase("stop")) {
            body = "{\"reqId\": \"1\", \"cmd\": \"stopCleaning\"}";
        } else if (command.toString().equalsIgnoreCase("resume")) {
            body = "{\"reqId\": \"1\", \"cmd\": \"resumeCleaning\"}";
        } else if (command.toString().equalsIgnoreCase("dock")) {
            body = "{\"reqId\": \"1\", \"cmd\": \"sendToBase\"}";
        } else if (command.toString().equalsIgnoreCase("dismissAlert")) {
            body = "{\"reqId\": \"1\", \"cmd\": \"dismissCurrentAlert\"}";
        }

        if (body.isEmpty()) {
            return false;
        }

        try {
            this.callNeatoWS(body);
        } catch (Exception exc) {
            throw (exc);
        }
        return true;
    }

    public Boolean sendGetRobotInfo() throws Exception {

        logger.info("Will get INFO for Robot {}", this.name);

        String body = "{\"reqId\": \"abc\",\"cmd\": \"getRobotInfo\" }";

        try {

            String result = this.callNeatoWS(body);
            result = result.replaceAll("ModelName", "InternalModelName");
            logger.debug("Result from getRobotInfo: {}", result);

            Gson gson = new Gson();

            this.info = gson.fromJson(result, NeatoRobotInfo.class);

        } catch (Exception ex) {
            throw (ex);
        }

        return true;
    }

    public Boolean sendGetState() throws Exception {
        logger.info("Will get STATE for Robot {}", this.name);

        String body = "{\"reqId\": \"abc\",\"cmd\": \"getRobotState\" }";

        try {

            String result = this.callNeatoWS(body);
            logger.debug("Result from getRobotState: {}", result);

            Gson gson = new Gson();

            this.state = gson.fromJson(result, NeatoState.class);

            logger.info("Successfully got and parsed new state for {}", this.name);
        } catch (Exception ex) {
            throw (ex);
        }

        return true;

    }

    public Boolean sendGetGeneralInfo() throws Exception {

        if (state.getAvailableServices().getGeneralInfo() == "basic-1"
                || state.getAvailableServices().getGeneralInfo() == "advanced-1") {
            logger.info("Will get GENERAL INFO for Robot {}", this.name);

            String body = "{\"reqId\": \"abc\",\"cmd\": \"getGeneralInfo\" }";

            try {

                String result = this.callNeatoWS(body);
                logger.debug("Result from getRobotState: {}", result);

                Gson gson = new Gson();

                this.generalInfo = gson.fromJson(result, NeatoGeneralInfo.class);

            } catch (Exception ex) {
                throw (ex);
            }

            return true;

        } else {
            logger.debug("Your vacuum cleaner does not support General Info messages");
            this.generalInfo = null;
            return false;
        }

    }

}
