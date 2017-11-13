package groovy

import static groovyx.net.http.ContentType.URLENC

/**
 * Created by mykola.ivkov on 8/20/2017.
 */
 class GetTweetInfoSpec extends BaseSpec{

    def "getTweetInfoSpecification"() {

        setup: "Posting test Tweet"

            def msg = "Test Tweet to verify Get method"
            def postResp = http.post(
                    path: 'update.json',
                    body: [ status: msg, source: 'httpbuilder' ],
                    requestContentType: URLENC )

        when: "Trying to retrieve information about the test Tweets"

            def resp = http.get( path : 'home_timeline.json', requestContentType : URLENC )

        then: "Successful response with Tweet info is received"

            resp.status == 200
            resp.data.retweet_count[0] == 0
            resp.data.text[0] == msg
            resp.data.created_at[0] == postResp.data.created_at

        cleanup: "Removing test data"

            http.post( path: "destroy/${postResp.data.id}.json" )
    }
}
