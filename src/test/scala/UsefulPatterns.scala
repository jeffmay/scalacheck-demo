import java.util.UUID

import org.scalacheck.Gen

object UsefulPatterns {

  def genUUID: Gen[UUID] = for {
    newUUID <- Gen.const(() => UUID.randomUUID())
  } yield newUUID()
}
