package org.example.otpproject.Services

import org.example.otpproject.Repositories.VehicleRepository
import org.example.otpproject.model.FuelType
import org.example.otpproject.model.Vehicle
import org.example.otpproject.model.VehicleProfitDTO
import org.springframework.stereotype.Service
import kotlin.math.ceil
import kotlin.math.min

@Service
class VehicleService(
    private val vehicleRepository: VehicleRepository
) {

    fun assignVehicle(passCapacity: Int?,distance: Float?): List<VehicleProfitDTO> {
        if (passCapacity == null || distance == null || passCapacity <= 0 || distance <= 0) {
            throw IllegalArgumentException("Capacity and distance must be positive numbers")
        }
        return vehicleRepository.findAll()
            .filter { it.capacity >= passCapacity && !it.assigned && canReach(it,distance) }
            .toList()
            .map { vehicle ->
                VehicleProfitDTO(
                    vehicle.id ?: 0,
                    vehicle.capacity,
                    vehicle.range,
                    calculateProfit(distance,passCapacity,vehicle),
                    vehicle.fuel
                )
            }
            .sortedByDescending { it.profit }
    }

    fun addVehicle(capacity: Int, range: Float, fuel: FuelType, assigned: Boolean): Vehicle {
        if(capacity <= 0 || range <= 0){
            throw IllegalArgumentException("Capacity and range must be positive numbers")
        }
        val vehicle = Vehicle(capacity, range, fuel, assigned)
        return vehicleRepository.save(vehicle)
    }

    fun calculateProfit(distance: Float,passCapacity: Int,vehicle: Vehicle): Float {
        if(distance <= 0){
            throw IllegalArgumentException("Distance cannot be a negative number or zero")
        }
        val cityTravel = min(50.0f,distance)
        val highwayTravel = maxOf(0.0f, distance - cityTravel)
        val consumption = when (vehicle.fuel) {
            FuelType.HYBRID -> cityTravel /2 + highwayTravel
            else -> cityTravel + highwayTravel
        }
        val travelTime = cityTravel * 2 + highwayTravel * 1
        val profit =passCapacity * (distance * 2 + ceil(travelTime/30) * 2)

        val cost = when (vehicle.fuel) {
            FuelType.GASOLINE -> consumption*2
            FuelType.ELECTRIC -> consumption*1
            FuelType.HYBRID -> consumption*2
        }
        return profit-cost
    }

    fun canReach(vehicle: Vehicle,distance: Float): Boolean{
        if(distance <= 0){
            throw IllegalArgumentException("Distance cannot be a negative number or zero")
        }
        return when(vehicle.fuel){
            FuelType.HYBRID -> {
                val cityTravel= min(50.0f,distance)
                when(distance>50) {
                    true -> distance - cityTravel + 25 <= vehicle.range
                    false -> cityTravel <= vehicle.range * 2
                }
            }else ->{
                distance <= vehicle.range
            }
        }

    }
}