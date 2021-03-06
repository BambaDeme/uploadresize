package com.example.uploadresize.service;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl implements ImageService{
    @Value("${image.folder}")
    private String imageFolder;

    @Value("${image.size}")
    private Integer imageSize;

    private Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Override
    public boolean resizeImage(File sourceFile) {
        try{
            BufferedImage bufferedImage = ImageIO.read(sourceFile); // read the image
            // resize image with default defined size imageSize
            BufferedImage outputImage = Scalr.resize(bufferedImage,Scalr.Method.QUALITY ,imageSize);

            // choose a file for the compressed image baseName+imageSize
            String newFileName = FilenameUtils.getBaseName(sourceFile.getName())
                    + "_" + imageSize.toString() + "."
                    + FilenameUtils.getExtension(sourceFile.getName());

            //
            Path path = Paths.get(imageFolder,newFileName);
            //
            File newImageFile = path.toFile();

            ImageIO.write(outputImage, "jpg", newImageFile);
            outputImage.flush();
            return true;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return false;
        }
    }
}
