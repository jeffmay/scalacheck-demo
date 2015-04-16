import org.scalatest.WordSpec

import scala.util.Random

trait PersonTestExamples {

  def newAge: Int = Random.nextInt(79) + 1
  
  def newPerson(
    first: String = Random.nextString(10),
    last: String = Random.nextString(10),
    age: Int = newAge) =
    Person(first, last, age)

  def newOrganization(
    people: Seq[Person] = List.fill(Random.nextInt(5))(newPerson()),
    subOrgs: Seq[Organization] = List.fill(Random.nextInt(5))(newOrganization()) // explodes!
    ) =
    Organization(people.groupBy(p => p.first + " " + p.last).mapValues(_.head), subOrgs)

  def samplePeople(
    first: Iterator[String] = Iterator.continually(Random.nextString(10)),
    last: Iterator[String] = Iterator.continually(Random.nextString(10)),
    age: Iterator[Int] = Iterator.continually(Random.nextInt(79) + 1)
    ): Iterator[Person] = Iterator.continually(newPerson())
}

class PersonTest extends WordSpec with PersonTestExamples {

  "Things" should {
    "work" in {
      val person = new Person("personFirst", "personLast", 23)
      testSomething(person)
    }
  }

  "Things" should {
    "work for multiple people" in {
      val people = (samplePeople() take 10).toStream
      people foreach testSomething
      val families = people groupBy (_.last)
      // ...
    }
  }

  def testSomething(person: Person) = ???
}