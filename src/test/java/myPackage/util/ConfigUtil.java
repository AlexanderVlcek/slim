package myPackage.util;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.config.HttpClientConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import lombok.extern.slf4j.Slf4j;
import myPackage.constants.ConfigConstant;
import myPackage.singeltons.ConfigSource;
import net.serenitybdd.rest.RestDefaults;
import net.serenitybdd.rest.SerenityRest;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT;
import static org.apache.http.params.CoreConnectionPNames.SO_TIMEOUT;

/**
 * Created by patrik.kempec on 2/14/2017.
 */
@Slf4j
public class ConfigUtil {
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ConfigUtil.class);

    private static final String WEBDRIVER_FIREFOX_BIN = "webdriver.firefox.bin";
    private static final String WEBDRIVER_GECKO_BIN = "webdriver.gecko.driver";
    private static final String SOCKET_TIMEOUT = "socketTimeout";
    private static final String CONN_TIMEOUT = "connectionTimeout";
    private static final Logger LOG = LoggerFactory.getLogger(ConfigUtil.class);


    public static String getString(String s) {
        return ConfigSource.INSTANCE.config.getString(s);
    }

    public static List<String> getStringList(String path) {
        return ConfigSource.INSTANCE.config.getStringList(path);
    }

    public static int getInt(String s) {
        return ConfigSource.INSTANCE.config.getInt(s);
    }

    public static boolean getBoolean(String s) {
        return ConfigSource.INSTANCE.config.getBoolean(s);
    }

    public static boolean hasPath(String s) {
        return ConfigSource.INSTANCE.config.hasPath(s);
    }

    public static void setDefaultsInit() {
        setSerenityRestInit();
        setRestInit();
        setRestAssuredInit();
    }

    public static void initWebDriver() {
        log.info("Initialize web driver config");
        String firefox = System.getProperty(WEBDRIVER_FIREFOX_BIN);
        String gecko = System.getProperty(WEBDRIVER_GECKO_BIN);
        if (firefox == null) {
            System.setProperty(WEBDRIVER_FIREFOX_BIN, ConfigUtil.getString(ConfigConstant.WEBDRIVER_PATH_FIREFOX));
        }
        if (gecko == null) {
            System.setProperty(WEBDRIVER_GECKO_BIN, ConfigUtil.getString(ConfigConstant.WEBDRIVER_PATH_CHROME));
        }
    }

    private static void setSerenityRestInit() {
        SerenityRest.setDefaultPort(ConfigUtil.getInt(ConfigConstant.APP_PORT));
        AuthenticationScheme auth = SerenityRest.preemptive().basic(ConfigUtil.getString(ConfigConstant.APP_BASIC_USERNAME), ConfigUtil.getString(ConfigConstant.APP_BASIC_PASSWORD));
        SerenityRest.setDefaultAuthentication(auth);
        SerenityRest.useRelaxedHTTPSValidation();
    }

    private static void setRestInit() {
//      RestDefaults.filters(new CustomFilter(CustomFilter.REQUEST_SEPARATOR), new RequestLoggingFilter(LogDetail.METHOD), new RequestLoggingFilter(LogDetail.URI),new RequestLoggingFilter(LogDetail.BODY));//STATUS is not a valid LogDetail for a request.
//      RestDefaults.filters(new CustomFilter(CustomFilter.RESPONSE_SEPARATOR), new ResponseLoggingFilter(LogDetail.STATUS), new ResponseLoggingFilter(LogDetail.BODY));//URI,METHOD is not a valid LogDetail for a response
        RestDefaults.filters(new CustomFilter(CustomFilter.REQUEST_SEPARATOR), new RequestLoggingFilter(LogDetail.ALL));//STATUS is not a valid LogDetail for a request.
        RestDefaults.filters(new CustomFilter(CustomFilter.RESPONSE_SEPARATOR), new ResponseLoggingFilter(LogDetail.ALL));//URI,METHOD is not a valid LogDetail for a response
    }

    private static void setRestAssuredInit() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = getString(ConfigConstant.APP_ENDPOINT);

        HttpClientConfig httpClientConfig = HttpClientConfig.httpClientConfig();
        httpClientConfig.setParam(SO_TIMEOUT, getInt(SOCKET_TIMEOUT));
        httpClientConfig.setParam(CONNECTION_TIMEOUT, getInt(CONN_TIMEOUT));

        RestAssured.config = RestAssured.config().httpClient(httpClientConfig);
    }
}
