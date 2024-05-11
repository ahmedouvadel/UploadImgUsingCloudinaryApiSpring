package com.example.cloudinayimgapi.Service.ServiceI;

import com.example.cloudinayimgapi.Model.Entity.Image;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    List<Image> list();
    Optional<Image> getOne(Long id);
    void save(Image image);
    void delete(Long id);
    boolean exists(Long id);
}
