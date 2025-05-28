package org.example.otpproject.model

import jakarta.persistence.*

@Entity(name = "vehicle")
class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    var capacity :  Int = 0

    var range: Float = 0.0F

    var fuel : FuelType = FuelType.GASOLINE

    var assigned: Boolean = false

    constructor() {}

    constructor(capacity: Int, range: Float, fuel: FuelType,assigned: Boolean) {
        this.capacity = capacity
        this.range = range
        this.fuel = fuel
        this.assigned = assigned
    }
}