package endpoints.algebra.server
import java.util.UUID

import com.softwaremill.sttp._
import endpoints.algebra.EndpointsTestApi

trait EndpointsTestSuite[T <: EndpointsTestApi] extends ServerTestBase[T] {

  def serverTestSuite() = {

    "Server interpreter" should {

      "return server response for UUID" in {

        val uuid = UUID.randomUUID()
        val mockedResponse = "interpretedServerResponse"

        serveEndpoint(serverApi.UUIDEndpoint, mockedResponse) { port =>
          implicit val backend: SttpBackend[Id, Nothing] = HttpURLConnectionBackend()
          val response  = sttp.get(uri"http://localhost:$port/user/$uuid/description?name=name1&age=18").send()
          assert(response.body.isRight)
          assert(response.body.right.get == mockedResponse)
          assert(response.code == 200)
          ()
        }

        serveEndpoint(serverApi.putUUIDEndpoint, ()) { port =>
          implicit val backend: SttpBackend[Id, Nothing] = HttpURLConnectionBackend()
          val response  = sttp.put(uri"http://localhost:$port/user/$uuid").send()
          assert(response.body.isRight)
          assert(response.body.right.get == "")
          assert(response.code == 200)
          ()
        }

        serveEndpoint(serverApi.deleteUUIDEndpoint, ()) { port =>
          implicit val backend: SttpBackend[Id, Nothing] = HttpURLConnectionBackend()
          val response  = sttp.delete(uri"http://localhost:$port/user/$uuid").send()
          assert(response.body.isRight)
          assert(response.body.right.get == "")
          assert(response.code == 200)
          ()
        }
      }

      "return server response" in {

        val mockedResponse = "interpretedServerResponse"

        serveEndpoint(serverApi.smokeEndpoint, mockedResponse) { port =>
          implicit val backend: SttpBackend[Id, Nothing] = HttpURLConnectionBackend()
          val response  = sttp.get(uri"http://localhost:$port/user/userId/description?name=name1&age=18").send()
          assert(response.body.isRight)
          assert(response.body.right.get == mockedResponse)
          assert(response.code == 200)
          ()
        }

        serveEndpoint(serverApi.putEndpoint, ()) { port =>
          implicit val backend: SttpBackend[Id, Nothing] = HttpURLConnectionBackend()
          val response  = sttp.put(uri"http://localhost:$port/user/foo123").send()
          assert(response.body.isRight)
          assert(response.body.right.get == "")
          assert(response.code == 200)
          ()
        }

        serveEndpoint(serverApi.deleteEndpoint, ()) { port =>
          implicit val backend: SttpBackend[Id, Nothing] = HttpURLConnectionBackend()
          val response  = sttp.delete(uri"http://localhost:$port/user/foo123").send()
          assert(response.body.isRight)
          assert(response.body.right.get == "")
          assert(response.code == 200)
          ()
        }
      }
    }
  }


}
