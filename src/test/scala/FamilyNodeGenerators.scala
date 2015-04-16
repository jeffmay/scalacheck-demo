import org.scalacheck.{Arbitrary, Gen}

import scala.language.implicitConversions

trait FamilyNodeGenerators extends PersonGenerators {

  def maxDepth: Int = 10

  def maxWidth: Int = 2

  def genFamilyNode(
    depth: Int = maxDepth,
    widthGen: Gen[Int] = Gen.choose(0, maxWidth),
    personGen: Gen[Person] = genPerson()
    ): Gen[FamilyNode] = {
    if (depth == 0) personGen.map(FamilyNode(_))
    else for {
      parent <- personGen.retryUntil(_.age > 14)
      // children have n = 0 - width children
      n <- widthGen
      children <- Gen.listOfN(n, genFamilyNode(depth - 1, widthGen, personGen.retryUntil(_.age < parent.age)))
    } yield FamilyNode(parent, children)
  }

  implicit def arbFamilyNode(implicit arbPerson: Arbitrary[Person]): Arbitrary[FamilyNode] =
    Arbitrary(genFamilyNode())

  // Alternatively,
//  def genConstraintFriendlyFamilyNode(
//    depth: Int = maxDepth,
//    widthGen: Gen[Int] = Gen.choose(0, maxWidth),
//    olderPersonGen: Gen[Person] = genPerson(),
//    minReproductiveAge: Int = 14
//    ): Gen[FamilyNode] = {
//    def genChildWithMaxAge(maxChildAge: Int): Gen[Person] = {
//      require(maxChildAge <= maxAge,
//        s"Too many generations of children ($depth) to have before exceeding the max age of $maxAge " +
//          s"with a minimum reproductive age of $minReproductiveAge")
//      for {
//        person <- olderPersonGen
//        age <- Gen.choose(1, maxChildAge)
//      } yield person.copy(age = age)
//    }
//    if (depth == 0) genChildWithMaxAge(maxAge).map(FamilyNode(_))
//    else for {
//      parent <- olderPersonGen
//      n <- widthGen
//      children <- Gen.listOfN(n,
//        genConstraintFriendlyFamilyNode(
//          depth - 1,
//          widthGen,
//          genChildWithMaxAge(parent.age - minReproductiveAge),
//          minReproductiveAge
//        )
//      )
//    } yield FamilyNode(parent, children)
//  }
//
//  implicit def arbFamilyNode(implicit arbPerson: Arbitrary[Person]): Arbitrary[FamilyNode] =
//    Arbitrary {
//      for {
//        depth <- Gen.choose(0, maxDepth)
//        node <- genConstraintFriendlyFamilyNode(depth, Gen.choose(0, maxWidth))
//      } yield node
//    }

}
