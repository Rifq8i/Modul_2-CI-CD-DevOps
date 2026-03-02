package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController{

    @Autowired
    private CarService carService;

    @GetMapping("/createCar")
    public String createCarPage(Model model) {
        Car car = new Car();
        model.addAttribute("car", car);
        return "createCar";
    }

    @PostMapping("/createCar")
    public String createCarPost(@ModelAttribute Car car, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || car.getCarQuantity() == null) {
            if (car.getCarQuantity() == null) {
                model.addAttribute("error", "Quantity harus diisi angka!");
            }
            return "createCar";
        }
        carService.create(car);
        return "redirect:/car/list";
    }

    @GetMapping("/list")
    public String carListPage(Model model) {
        List<Car> allCars = carService.findAll();
        model.addAttribute("cars", allCars);
        return "carList";
    }

    @GetMapping("/editCar/{carId}")
    public String editCarPage(@PathVariable String carId, Model model) {
        Car car = carService.findById(carId);
        model.addAttribute("car", car);
        return "editCar";
    }

    @PostMapping("/editCar")
    public String editCarPost(@ModelAttribute Car car, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || car.getCarQuantity() == null) {
            if (car.getCarQuantity() == null) {
                model.addAttribute("error", "Quantity tidak boleh kosong saat edit!");
            }
            model.addAttribute("car", car);
            return "editCar";
        }

        carService.update(car.getCarId(), car);
        return "redirect:/car/list";
    }

    @PostMapping("/deleteCar")
    public String deleteCar(@RequestParam("carId") String carId) {
        carService.deleteCarById(carId);
        return "redirect:/car/list";
    }
}