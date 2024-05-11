package com.example.cloudinayimgapi.Service.ServiceImpl;

import com.example.cloudinayimgapi.Model.Entity.Image;
import com.example.cloudinayimgapi.Model.Repository.ImageRepository;
import com.example.cloudinayimgapi.Service.ServiceI.ImageService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    public List<Image> list(){
        return imageRepository.findByOrderById();
    }
    public Optional<Image> getOne(Long id){
        return imageRepository.findById(id);
    }

    public void save(Image image){
        imageRepository.save(image);
    }

    public void delete(Long id){
        imageRepository.deleteById(id);
    }

    public boolean exists(Long id){
        return imageRepository.existsById(id);
    }
}
