package groovy

import groovyx.net.http.HttpResponseException

import static groovyx.net.http.ContentType.URLENC

/**
 * Created by mykola.ivkov on 8/20/2017.
 */
class DeleteTweetSpec extends BaseSpec {

    def "deleteTweetSpecification"() {

        setup: "Posting test Tweet to be delete"

            def msg = "Test Tweet to verify Delete method"
            def postResp = http.post(
                    path: 'update.json',
                    body: [ status: msg, source: 'httpbuilder' ],
                    requestContentType: URLENC )

        when: "Request to delete the test Tweet is sent to the API"

            def resp = http.post( path: "destroy/${postResp.data.id}.json" )

        then: "Successful response is received"

            resp.status == 200
            resp.data.id == postResp.data.id

        when: "Trying to get information about previously removed Tweet"

            http.get( uri: "https://api.twitter.com/1.1/statuses/show.json?id=${postResp.data.id}")

        then: "Not Found exception is recieved"

            HttpResponseException e = thrown()
            e.cause == null
            e.message == "Not Found"
            e.statusCode == 404
    }

}
