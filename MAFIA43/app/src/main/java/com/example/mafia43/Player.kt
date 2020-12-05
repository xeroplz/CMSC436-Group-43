package com.example.mafia43

import java.io.Serializable



class Player : Serializable {
    private lateinit var privName : String
    private var privRole : Int = 0 // 1 = Mafia | 2 = Doctor | 3 = Detective | 4 = Civilian
    private var privAlive = true // true = alive, false = dead

    constructor(name : String, role : Int) {
        this.privName = name
        this.privRole = role
    }

    fun name(): String {
        return privName
    }

    fun role(): Int {
        return privRole
    }

    fun roleString(): String {
        when (privRole) {
            1 -> return "Mafia"
            2 -> return "Doctor"
            3 -> return "Detective"
            4 -> return "Civilian"
            else -> return "Undefined"
        }
    }

    fun alive(): Boolean {
        return privAlive
    }

    fun die() {
        this.privAlive = false
    }
}