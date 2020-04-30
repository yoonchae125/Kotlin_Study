package _04

class CountingSet<T>(
		val innerSet: MutableCollection<T> = HashSet<T>()
):MutableCollection<T> by innerSet {
	var objectsAdded = 0
	override fun add(element:T): Boolean{
		objectsAdded++
		return innerSet.add(element)
	}
	override fun addAll(c:Collection<T>):Boolean{
		objectsAdded++
		return innerSet.addAll(c)
	}
}