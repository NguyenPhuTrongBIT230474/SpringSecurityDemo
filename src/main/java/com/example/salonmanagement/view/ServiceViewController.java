package com.example.salonmanagement.view;

import com.example.salonmanagement.entity.ServiceEntity;
import com.example.salonmanagement.service.impl.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/services")
public class ServiceViewController {
    @Autowired
    private ServiceService serviceService;

    @GetMapping
    public String home(Model model) {
        List<ServiceEntity> services = serviceService.getAllServices();
        model.addAttribute("services", services);
        model.addAttribute("totalPages", 1);  // sau này có thể dùng pagination
        model.addAttribute("currentPage", 1);
        return "service/list";  // trỏ tới templates/service/detail.html
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Integer id) {
        byte[] image = serviceService.getImageById(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping("/new")
    public String addServiceForm(Model model) {
        model.addAttribute("service", new ServiceEntity());
        model.addAttribute("pageTitle", "Thêm Dịch vụ");
        return "service/form";
    }

    @GetMapping("/edit/{id}")
    public String editService(@PathVariable("id") Integer id, Model model) {
        ServiceEntity service = serviceService.getServiceById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service not found ID: " + id));
        model.addAttribute("service", service);
        model.addAttribute("pageTitle", "Sửa Dịch vụ");
        return "service/form";
    }

    @PostMapping("/save")
    public String saveOrUpdateService(
            @RequestParam(required = false) Integer serviceId,
            @RequestParam String serviceName,
            @RequestParam ServiceEntity.Category category,
            @RequestParam Integer price,
            @RequestParam Integer duration,
            @RequestParam ServiceEntity.Status status,
            @RequestParam(required = false) MultipartFile image
    ) throws IOException {
        if (serviceId == null) {
            // Thêm mới
            ServiceEntity service = new ServiceEntity();
            service.setServiceName(serviceName);
            service.setCategory(category);
            service.setPrice(price);
            service.setDuration(duration);
            service.setStatus(status);
            if (image != null && !image.isEmpty()) {
                service.setImage(image.getBytes());
            }
            serviceService.saveService(service);
        } else {
            serviceService.updateService(serviceId, serviceName, category, price, duration, status, image);
        }
        return "redirect:/services";
    }

    @GetMapping("/delete/{id}")
    public String deleteService(@PathVariable("id") Integer id) {
        serviceService.deleteService(id);
        return "redirect:/services";
    }

}
