package com.example.salonmanagement.service.impl;

import com.example.salonmanagement.entity.ServiceEntity;
import com.example.salonmanagement.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository repository;

    public List<ServiceEntity> getAllServices() {
        return repository.findAll();
    }

    public byte[] getImageById(Integer id) {
        Optional<ServiceEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Không tìm thấy dịch vụ có ID = " + id);
        }

        ServiceEntity service = optional.get();
        if (service.getImage() == null) {
            throw new RuntimeException("Dịch vụ này chưa có ảnh!");
        }

        return service.getImage();
    }

    public ServiceEntity saveService(ServiceEntity service) {
        return repository.save(service);
    }

    public ServiceEntity updateService(Integer id,
                                       String serviceName,
                                       ServiceEntity.Category category,
                                       Integer price,
                                       Integer duration,
                                       ServiceEntity.Status status,
                                       MultipartFile image) throws IOException {

        Optional<ServiceEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Không tìm thấy dịch vụ có ID = " + id);
        }

        ServiceEntity existing = optional.get();

        if (serviceName != null && !serviceName.isEmpty()) {
            existing.setServiceName(serviceName);
        }
        if (category != null) {
            existing.setCategory(category);
        }
        if (price != null) {
            existing.setPrice(price);
        }
        if (duration != null) {
            existing.setDuration(duration);
        }
        if (status != null) {
            existing.setStatus(status);
        }
        if (image != null && !image.isEmpty()) {
            existing.setImage(image.getBytes());
        }

        return repository.save(existing);
    }

    public Optional<ServiceEntity> getServiceById(Integer id) {
        return repository.findById(id);
    }

    public void deleteService(Integer id) {
        repository.deleteById(id);
    }
}


