import org.scalacheck.{Gen, Arbitrary}

trait PersonGenerators {
  def maxAge: Int = 80
  def genAge: Gen[Int] = Gen.choose(1, maxAge)
  def genFirstName: Gen[String] = Gen.oneOf("Willie", "Jeff", "Rob", "Chris", "Eric")
  def genLastName: Gen[String] = Gen.oneOf("Miller", "May", "Whiteston", "Salij", "Chen")
  def genPerson(
    firstNameGen: Gen[String] = genFirstName,
    lastNameGen: Gen[String] = genLastName,
    ageGen: Gen[Int] = genAge
    ): Gen[Person] = for {
    first <- firstNameGen
    last <- lastNameGen
    age <- ageGen
  } yield Person(first, last, age)
  implicit val arbPerson: Arbitrary[Person] = Arbitrary(genPerson())
}
