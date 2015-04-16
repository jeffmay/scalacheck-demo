import scalaz.Tree

case class Person(first: String, last: String, age: Int) {
  require(age > 0, "Age must be greater than 0")
}

case class Organization(people: Map[String, Person], subOrgs: Seq[Organization])

case class FamilyNode(person: Person, children: Seq[FamilyNode] = Seq.empty) {

  children.find(_.person.age > person.age) foreach {
    older =>
      require(false, s"Child (${older.person.age}) must be younger than parent (${person.age})")
  }

  def descendants: Seq[Seq[Person]] = children.flatMap(_.descendants)

  def toTree: Tree[Person] = Tree.node(person, children.toStream.map(_.toTree))
}