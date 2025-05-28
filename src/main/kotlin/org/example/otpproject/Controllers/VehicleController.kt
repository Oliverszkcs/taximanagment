package org.example.otpproject.Controllers

import org.example.otpproject.Services.VehicleService
import org.example.otpproject.model.FuelType
import org.example.otpproject.model.Vehicle
import org.example.otpproject.model.VehicleProfitDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/vehicles")
class VehicleController(
    private val vehicleService: VehicleService
) {

    @GetMapping("/assignVehicle")
    fun assignVehicle(@RequestParam passCapacity: Int,@RequestParam distance: Float): List<VehicleProfitDTO> {
        return vehicleService.assignVehicle(passCapacity,distance)
    }

    @PostMapping("/addVehicle")
    fun addVehicle(@RequestParam capacity: Int,@RequestParam range: Float,@RequestParam fueltype: String,@RequestParam assigned: Boolean): Vehicle {
        val fuelType = FuelType.valueOf(fueltype.uppercase())
        return vehicleService.addVehicle(capacity, range, fuelType, assigned)
    }


}