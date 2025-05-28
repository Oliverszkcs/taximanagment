package org.example.otpproject.model

data class VehicleProfitDTO(
    val vehicleId: Long,
    val capacity: Int,
    val range: Float,
    val profit: Float,
    val fuelType: FuelType,
)
