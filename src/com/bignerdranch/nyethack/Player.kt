package com.bignerdranch.nyethack

import java.io.File

class Player(
    _name: String,
    override var healthPoints: Int = 100,
    val _isBlessed: Boolean,
    private val _isImmortal: Boolean
) : Fightable {
    var name = _name
        get() = "${field.capitalize()} of $hometown"
        private set(value) {
            field = value.trim()

        }
    val hometown: String by lazy { selectHometown() }
    var currentPosition = Navigation.Coordinate(0, 0)


    init {
        require(healthPoints > 0, { "healthPoints must be greater than zero." })
        require(name.isNotBlank(), { "Player must have a name." })
    }

    constructor(name: String) : this(
        name,
        _isBlessed = true,
        _isImmortal = false
    ) {
        if (name.toLowerCase() == "kar") healthPoints = 40
    }

    fun castFireball(numFireballs: Int = 2) =
        println("A glass of Fireball springs into existence. (x$numFireballs)")

    fun formatHealthStatus() = when (healthPoints) {
        100 -> "is in excellent condition!"
        in 90..99 -> "has afew scratches."
        in 75..89 -> if (_isBlessed) {
            "has some minor wounds but is healing quite quickly!"
        } else {
            "has some minor wounds."
        }
        in 15..74 -> "looks pretty hurt."
        else -> "is in awful condition!"
    }

    fun auraColor(): String {
        val auraVisible = _isBlessed && healthPoints > 50 || _isImmortal
        val auraColor = if (auraVisible) "Green" else "NONE"
        return auraColor
    }

    private fun selectHometown() = File("data/towns.txt")
        .readText()
        .split("\n")
        .shuffled()
        .first()

    override val diceCount: Int = 3

    override val diceSides: Int = 6


    override fun attack(opponent: Fightable): Int {
        val damageDealt = if (_isBlessed) {
            damageRoll * 2
        } else {
            damageRoll
        }
        opponent.healthPoints -= damageDealt
        return damageDealt
    }
}