package com.ruoyi.project.tool.picture;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.ruoyi.project.uppicture.domain.Bimenclosure;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;

public class PictureHelp {

    public static Bimenclosure readPics(File file){
        Bimenclosure bim = new Bimenclosure();
        String photo_time = "" ;
        try{
            Metadata metadata = JpegMetadataReader.readMetadata(file);
            Iterator<Directory> it = metadata.getDirectories().iterator();
            while (it.hasNext()){
                Directory exif = it.next();
                Iterator<Tag> tags = exif.getTags().iterator();
                while (tags.hasNext()) {
                    Tag tag = (Tag) tags.next();
                    System.out.println(tag);
                    if("GPS Latitude".equals(tag.getTagName())){
                        String x_coordinates = tag.getDescription();
                        bim.setX_coordinates(getDouble(x_coordinates));
                    }else if("GPS Longitude".equals(tag.getTagName())){
                        String y_coordinates = tag.getDescription();
                        bim.setY_coordinates(getDouble(y_coordinates));
                    }else if("GPS Date Stamp".equals(tag.getTagName())){
                         photo_time = tag.getDescription()+" "+photo_time;
                    }else if("GPS Time-Stamp".equals(tag.getTagName())){//设备型号
                         photo_time += tag.getDescription();
                    }else if("Make".equals(tag.getTagName())){//设备信息
                        String Make = tag.getDescription();
                    }

                }
                //Timestamp time=Timestamp.valueOf(photo_time);
                //bim.setPhoto_time(time);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bim;
    }
    public static  void readPic() {
        System.out.println("开始读取图片信息...");
        File jpegFile = new File("D://cb8a28fc99293fb288bb4e33d30c5e5.jpg");
        Metadata metadata;
        try {
            metadata =JpegMetadataReader.readMetadata(jpegFile);
            Iterator<Directory> it = metadata.getDirectories().iterator();
            while (it.hasNext()) {
                Directory exif = it.next();
                    Iterator<Tag> tags = exif.getTags().iterator();
                    while (tags.hasNext()) {
                        Tag tag = (Tag) tags.next();
                        System.out.println(tag);
                }
            }
            System.out.println("图片信息读取完成！");
        } catch (JpegProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }

    /**
     * 度分秒转度
     * @param
     */
    public static Double getDouble(String Coordinates){
        String[] first = Coordinates.split("°");
        String a = first[0];
        String [] second = first[1].split("'");
        String b = second[0];
        String [] third = second[1].split("\"");
        String c =third[0];
        Double iDouble  = Double.parseDouble(a)+Double.parseDouble(b)/60+Double.parseDouble(c)/3600;
        return  iDouble;
    }
    public static void main(String[] args) {
        readPic();
    }

}
