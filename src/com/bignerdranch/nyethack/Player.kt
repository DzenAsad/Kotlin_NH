package com.bignerdranch.nyethack

import java.io.File

class Player(_name: String,
             var _healthPoints: Int=100,
             val _isBlessed: Boolean,
             private val _isImmortal: Boolean) {
    val hometown: String by lazy {selectHometown()}
    var name = _name

        get() = "${field.capitalize()} of $hometown"
        private set(value) {
            field = value.trim()
        }



    init {
        require(_healthPoints >0, { "healthPoints must be greater than zero." })
        require(name.isNotBlank(), { "Player must have a name." })
    }

    constructor(name: String) : this(name,
        _isBlessed = true,
        _isImmortal = false) {
        if (name.toLowerCase() == "kar") _healthPoints = 40
    }
    fun castFireball(numFireballs: Int = 2) =
        println("A glass of Fireball springs into existence. (x$numFireballs)")

    fun formatHealthStatus() = when (_healthPoints) {
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
        val auraVisible = _isBlessed && _healthPoints > 50 || _isImmortal
        val auraColor = if (auraVisible) "Green" else "NONE"
        return auraColor
    }

    private fun selectHometown() = File("data/towns.txt")
        .readText()
        .split("\n")
        .shuffled()
        .first()
}