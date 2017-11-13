package groovy

import static groovyx.net.http.ContentType.URLENC

/**
 * Created by mykola.ivkov on 8/20/2017.
 */
class PostTweetSpec extends BaseSpec {

    def "postTweetSpecification"(){

        when: "Posting test Tweet"

            def msg = "Test Tweet to verify Post method"
            def resp = http.post(
                    path: 'update.json',
                    body: [ status: msg, source: 'httpbuilder' ],
                    requestContentType: URLENC )

        then: "Tweet was successfully posted to the timeline"

            resp.status == 200
            resp.headers.Status
            resp.data.text == msg

        cleanup: "Removing test data"

            http.post( path: "destroy/${resp.data.id}.json" )
    }

}
