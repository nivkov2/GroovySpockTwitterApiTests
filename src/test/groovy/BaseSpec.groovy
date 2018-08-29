package groovy

import groovyx.net.http.RESTClient
import org.apache.logging.log4j.LogManager
import ru.yandex.qatools.allure.annotations.Step
import spock.lang.Shared
import spock.lang.Specification


/**
 * Created by mykola.ivkov on 8/17/2017.
 */
class BaseSpec extends Specification {

    @Shared
    def logger = LogManager.getLogger(this.class.getName());

    //please, use your Twitter credentials
    @Shared
    def twitter = [ user: '',
                    consumerKey: '',
                    consumerSecret: '',
                    accessToken: '',
                    secretToken: '' ]
    @Shared
    def http


    @Step
    def setupSpec() {
        logger.info(" Initializing connection for the "+ this.getClass().getSimpleName() + "specification.")
        http = new RESTClient('https://api.twitter.com/1.1/statuses/')
        http.auth.oauth twitter.consumerKey, twitter.consumerSecret,
                twitter.accessToken, twitter.secretToken
        logger.info(" Connection was successfully initialized. ")
        logger.info(" Executing specification...")
    }

    @Step
    def cleanupSpec() {
        logger.info(" " + this.getClass().getSimpleName() + "specification has been executed.")
        logger.info("=====================================================================")
    }
}

