package utils

class MyStack<T>( var items: MutableList<T> = arrayListOf()) {

    private fun isEmpty():Boolean = this.items.isEmpty()

    fun count():Int = this.items.count()

    fun push(element:T) {
        val position = this.count()
        this.items.add(position, element)
    }

    override fun toString() = this.items.toString()

    fun pop():T? {
        if (!this.isEmpty()) {
            val item = this.items.count() - 1
            return this.items.removeAt(item)
        }
        return null
    }

    fun peek():T? {
        if (!this.isEmpty()) {
            return this.items[this.items.count() - 1]
        }
        return null
    }

}