package pokemongame.pokemon

@JvmInline
value class Accuracy(val value: Int) {
    init {
        require(value in 30..100)
    }
}

@JvmInline
value class Priority(val value: Int) {
    init {
        require(value in 0..4)
    }

    operator fun compareTo(other: Priority) = value.compareTo(other.value)
}

@JvmInline
value class PowerPoints(val value: Int) {
    init {
        require(value in 5..40)
    }
}

@JvmInline
value class BasePower(val value: Int) {
    init {
        require(value in 10..250)
    }
}
