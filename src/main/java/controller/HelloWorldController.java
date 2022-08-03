package controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class HelloWorldController {

    @GetMapping({"/hello/{name}","/hello/"})
    public String helloWorld(@PathVariable(required = false) String name){
        if (name == null){
            return "Hello world";
        }else{
            return "Hello "+name;
        }
    }

    @PostMapping(
            path = "/" ,
            consumes = {MediaType.IMAGE_PNG_VALUE , MediaType.IMAGE_JPEG_VALUE},
            produces = {MediaType.IMAGE_JPEG_VALUE , MediaType.IMAGE_PNG_VALUE} )
    public @ResponseBody byte[] postImage(@RequestBody byte[] image)throws IOException {
        ByteArrayInputStream img = new ByteArrayInputStream(image);
        return blackAndWhite(ImageIO.read(img));
    }

    public byte[] blackAndWhite(BufferedImage img) throws IOException {
        byte[] bytes = null;

        //get image width and height
        int width = img.getWidth();
        int height = img.getHeight();

        //convert to grayscale
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int p = img.getRGB(x,y);

                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                //calculate average
                int avg = (r+g+b)/3;

                //replace RGB value with avg
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;

                img.setRGB(x, y, p);
            }
        }

        //write image
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", baos);
            bytes = baos.toByteArray();
        }catch(IOException e){
            System.out.println(e);
        }
        return bytes;
    }

}