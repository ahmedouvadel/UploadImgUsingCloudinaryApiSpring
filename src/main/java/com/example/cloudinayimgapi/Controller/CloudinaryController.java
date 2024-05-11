package com.example.cloudinayimgapi.Controller;

import com.example.cloudinayimgapi.Model.Entity.Image;
import com.example.cloudinayimgapi.Service.ServiceI.ImageService;
import com.example.cloudinayimgapi.Service.ServiceImpl.CloudinaryServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cloudinary")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class CloudinaryController {

    public final CloudinaryServiceImpl cloudinaryService;
    public final ImageService imageService;

    @GetMapping("/list")
    public ResponseEntity<List<Image>> list(){
        List<Image> list = imageService.list();
        return ResponseEntity.ok(list);
    }
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> upload(@RequestParam MultipartFile multipartFile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
        if(bufferedImage == null){
            return new ResponseEntity<>("Image non valide m3nah no mawuploade", HttpStatus.BAD_REQUEST);
        }
        Map result = cloudinaryService.upload(multipartFile);
        Image image = new Image((String) result.get("original_filename"),
                (String) result.get("url"),
                (String) result.get("public_id"));
        imageService.save(image);
        return new ResponseEntity<>("image ajoute avec succes", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        Optional<Image> optionalImage = imageService.getOne(id);
        if(optionalImage.isEmpty()){
            return  new ResponseEntity<>("n'existe pas", HttpStatus.NOT_FOUND);
        }
        Image image = optionalImage.get();
        String cloudinaryImageId = image.getImageId();
        try{
            cloudinaryService.delete(cloudinaryImageId);
        } catch (IOException e) {
                return new ResponseEntity<>("Failed to delete image from cloudinary", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        imageService.delete(id);
        return new ResponseEntity<>("image supprime", HttpStatus.NO_CONTENT);
    }

}
