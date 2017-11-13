package groovy

import groovyx.net.http.HttpResponseException

import static groovyx.net.http.ContentType.URLENC

/**
 * Created by mykola.ivkov on 8/20/2017.
 */
class DuplicateTweetSpec extends BaseSpec{

    def "duplicateTweetSpecification"() {

        setup: "Posting initial Tweet"

            def msg = "Test Tweet to verify restriction for duplication"
            def resp = http.post( path: "update.json",
                    body : [ status:msg, source:'httpbuilder' ],
                    requestContentType : URLENC)

        when: "Trying to duplicate existing Tweet"

            http.post( path: "update.json",
                body : [ status:msg, source:'httpbuilder' ],
                requestContentType : URLENC)

        then: "403 Exception is thrown"

            HttpResponseException e = thrown()
            e.response.status == 403
            e.message == "Forbidden"

        cleanup: "Removing test data"

            http.post( path: "destroy/${resp.data.id}.json" )

    }

}
