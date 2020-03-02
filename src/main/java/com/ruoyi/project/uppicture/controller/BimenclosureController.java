package com.ruoyi.project.uppicture.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.project.tool.picture.PictureHelp;
import com.ruoyi.project.uppicture.domain.Bimenclosure;
import com.ruoyi.project.uppicture.service.BimenclosureServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/Bimenclosure")
public class BimenclosureController extends BaseController {
    @Resource
    private BimenclosureServiceImpl bimenclosureServiceImpl;


    /**
     * 插入上传照片的地理信息
     * @param h_coordinates
     * @param multipartFile
     * @return
     */
    @PostMapping(value = "/insertBimenclosure")
    @ResponseBody
    public int insertBimenclosure(@RequestParam(value ="h_coordinates", required = true) Double h_coordinates ,
                                  @RequestParam(value ="xy_coordinates", required = true) String xy_coordinates ,
                                  @RequestParam(value = "file", required = true) MultipartFile multipartFile){
        int n;
        try{
            String avatar = FileUploadUtils.upload(RuoYiConfig.getUploadPath(), multipartFile);
            File f = new File(multipartFile.getOriginalFilename());
            try (
                    InputStream in  = multipartFile.getInputStream();
                    OutputStream os = new FileOutputStream(f)){
                    byte[] buffer = new byte[4096];
                    while ((n = in.read(buffer,0,4096)) != -1){
                        os.write(buffer,0,n);
                    }
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
                    System.out.println(bufferedReader.readLine());
                    bufferedReader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            System.out.println(f.toURI().toURL().toString());// 输出file的URL
            System.out.println(f.getAbsolutePath());// 输出文件的绝对路径
            File file = new File(f.toURI());// 操作完上的文件 需要删除在根目录下生成的文件
            Bimenclosure bim = new PictureHelp().readPics(file);//File file = new File(RuoYiConfig.getProfile()+avatar);
            bim.setPic_url(f.getAbsolutePath().trim().toString());
            bim.setH_coordinates(h_coordinates);
            bim.setUser_id(1l);
            if("".equals(bim.getX_coordinates())||bim.getX_coordinates()==null){
                String []coordinates = xy_coordinates.split(",");
                bim.setX_coordinates(Double.valueOf(coordinates[1].toString()));
                bim.setY_coordinates(Double.valueOf(coordinates[0].toString()));
            }

            return bimenclosureServiceImpl.insertBim(bim);
        }catch (Exception e){
            e.printStackTrace();
        }
        /*File ff = new File(multipartFile.getOriginalFilename());
        new PictureHelp().readPics(ff);*/
        return  0;
    }

    /**
     * 提供地理信息，人员信息给BIM前台展示
     */
    @GetMapping(value = "/SelectList")
    @ResponseBody
    public JSONPObject QueryBimenclosure(String callback){
       /* List<Bimenclosure> bim = bimenclosureServiceImpl.SelectList();
        JSONObject jsono = JSONObject.fromObject(bim.get(3));
        String jstr = jsono.toString();
        JSONPObject json = new JSONPObject(jstr);
        return  json;*/
        List<Bimenclosure> bim = bimenclosureServiceImpl.SelectList();
        JSONPObject jsonobj = new JSONPObject(callback, bim.get(3));
        return jsonobj;

    }

}
