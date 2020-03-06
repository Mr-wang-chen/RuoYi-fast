package com.ruoyi.project.uppicture.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.user.domain.User;
import com.ruoyi.project.tool.picture.PictureHelp;
import com.ruoyi.project.uppicture.domain.Bimenclosure;
import com.ruoyi.project.uppicture.service.BimenclosureServiceImpl;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
    public String insertBimenclosure(@RequestParam(value ="h_coordinates", required = true) Double h_coordinates ,
                                     @RequestParam(value ="xy_coordinates", required = true) String xy_coordinates ,
                                     @RequestParam(value ="problem_type", required = true) String problem_type ,
                                     @RequestParam(value = "file", required = true) MultipartFile multipartFile){
        User currentUser = getSysUser();
        int n;
        try{
            String avatar = FileUploadUtils.upload(RuoYiConfig.getUploadPath(), multipartFile);
            System.out.println(avatar);
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
            bim.setPic_url(avatar.trim().toString());
            bim.setH_coordinates(h_coordinates);
            bim.setUser_id(currentUser.getUserId());
            bim.setProblem_type(problem_type);
            if("".equals(bim.getX_coordinates())||bim.getX_coordinates()==null){
                String []coordinates = xy_coordinates.split(",");
                bim.setX_coordinates(Double.valueOf(coordinates[1].toString()));
                bim.setY_coordinates(Double.valueOf(coordinates[0].toString()));
            }
            int i = bimenclosureServiceImpl.insertBim(bim);
            if(i>0){
                return "main";
            }else{
                return  "bimenclosure/add";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        /*File ff = new File(multipartFile.getOriginalFilename());
        new PictureHelp().readPics(ff);*/
        return  "bimenclosure/add";
    }

    /**
     * 提供地理信息，人员信息给BIM前台展示
     */
    @GetMapping(value = "/SelectList")
    @ResponseBody
    public JSONPObject QueryBimenclosure(String callback,Bimenclosure bims){
       /* List<Bimenclosure> bim = bimenclosureServiceImpl.SelectList();
        JSONObject jsono = JSONObject.fromObject(bim.get(3));
        String jstr = jsono.toString();
        JSONPObject json = new JSONPObject(jstr);
        return  json;*/
        List<Bimenclosure> bim = bimenclosureServiceImpl.SelectList(bims);
        JSONPObject jsonobj = new JSONPObject(callback, bim.get(bim.size()-1));
        return jsonobj;

    }
    @GetMapping(value = "/BimList")
    @ResponseBody
    public JSONPObject  QueryBimList(String callback, HttpServletResponse response,Bimenclosure bims){
        List<Bimenclosure> bim = bimenclosureServiceImpl.SelectList(bims);
        JSONPObject jsonobj = new JSONPObject(callback, bim);
        return jsonobj;
    }
    @PostMapping(value = "/list")
    @ResponseBody
    public TableDataInfo getAllBim(Bimenclosure bim){
            List<Bimenclosure> list =bimenclosureServiceImpl.SelectList(bim);
            return getDataTable(list);
    }
    @GetMapping(value = "/add")
    public String Add(){
        return "bimenclosure/add";
    }

    @GetMapping(value = "/detail/{id}")
    public Object getDetail(@PathVariable(value ="id") Integer rowId){
        ModelAndView modelAndView = new ModelAndView();
        Bimenclosure bim = bimenclosureServiceImpl.getBimbyId(rowId);
        modelAndView.addObject("bimenclosure", bim);

        modelAndView.setViewName("bimenclosure/detail");
        return modelAndView;
    }

}
