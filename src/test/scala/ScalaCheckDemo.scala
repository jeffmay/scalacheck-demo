import org.scalacheck.Prop._
import org.scalacheck.Properties
import org.scalatest.WordSpec
import org.scalatest.prop.GeneratorDrivenPropertyChecks

import scalaz.Show

object PersonProperties extends Properties("Person") with PersonGenerators {

//  import shapeless.contrib.scalacheck._

  property("is older than 0") = forAll { (person: Person) =>
    person.age > 0
  }

  property("has non empty first name") = forAll { (person: Person) =>
    person.first.nonEmpty
  }

  property("has non empty last name") = forAll { (person: Person) =>
    person.last.nonEmpty
  }
}

class PersonSpec extends WordSpec with GeneratorDrivenPropertyChecks with PersonGenerators {

//  import shapeless.contrib.scalacheck._

  "Person" should {

    "always be older than 0" in {
      forAll() { (person: Person) =>
        assert(person.age > 0)
      }
    }

    "have a funny name" in {
      forAll() { (person: Person) =>
        println(person)
      }
    }
  }
}

class FamilyNodeSpec extends WordSpec with GeneratorDrivenPropertyChecks with FamilyNodeGenerators {

  implicit val showPerson: Show[Person] = Show.shows(_.toString)

  "FamilyNode" should {

    "print strange family trees" in {
      forAll() { (node: FamilyNode) =>
        println(node.toTree.drawTree)
      }
    }
  }
}
