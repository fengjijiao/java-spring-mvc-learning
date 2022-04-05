package cc.goq.chat01;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 内容
 * 文件上传开发步骤
 * 单文件上传
 * 多文件上传
 * 通过MultipartHttpServletRequest处理文件上传
 * 通过自定义对象接收上传的文件
 *
 * springmvc文件上传步骤
 * 1.添加maven配置(commons-fileupload、commons-io)
 * 2.springmvc容器中定制MultipartResolver这个bean(springmvc.xml中)
 * [
 * 1.http上传的请求类型为multipart/form-data类型，springmvc内部需要为这种请求指定解析器，解析器的类型为
 * {@link org.springframework.web.multipart.MultipartResolver}
 * 2.MultipartResolver有2个实现类，这里我们使用CommonsMultipartResolver作为解析器来解析文件上传的http请求
 * 3.注意bean名称必须为multipartResolver
 * 4.两个比较有用的属性
 * maxUploadSizePerFile: 单个文件大小限制（byte）
 * maxUploadSize: 整个请求大小限制（byte）
 * ]
 * 3.controller中使用MultipartFile接收上传的文件
 * @RequestMapping("/upload1.do")
 * public ModelAndView upload1(@RequestParam("file1") MultipartFile f1) {
 * }
 * 4.调用MultipartFile#transferTo方法保存文件
 * @RequestMapping("/upload1.do")
 * public ModelAndView upload1(@RequestParam("file1") MultipartFile f1) {
 *      //destFile为目标文件，即将上传的文件写道destFile中
 *      f1.transferTo(destFile);
 * }
 * 5.指定请求类型为：multipart/form-data
 * 上传文件，需要设置form表单的enctype属性值为multipart/form-data
 *
 * MultipartFile: 上传的文件对象
 * springmvc中使用MultipartFile这个类来表示上传的文件，提供了一系列方法获取上传的文件信息。
 * 方法                       描述
 * String getName()             用来获取<input name=""/>中name的名称
 * String getOriginalFilename() 获取文件的原始名称
 * String getContentType()        获取文件类型
 * long getSize()                   获取文件大小（byte）
 * bytes[] getBytes()               获取文件内容
 * InputStream getInputStream()     获取文件流
 * void transferTo(File dest)       将上传的文件写到dest中
 */
@Controller
public class UploadController {
    /**
     * 下面来介绍4种常用的上传文件的方式。
     */
    /**
     * 单文件上传
     * 控制器中使用一个MultipartFile来接收上传的文件。
     * 每个MultipartFile对应表单中的一个元素
     * @RequestParam用来自动接收表单中的哪个元素？value用来指定表单元素的名称
     */
    @PostMapping("/upload/upload1.do")
    public ModelAndView upload1(@RequestParam("file1")MultipartFile f1) throws IOException {
        String originalFilename = f1.getOriginalFilename();
        String destFilePath = String.format("C:\\Users\\jijiao\\Downloads\\%s", originalFilename);
        File destFile = new File(destFilePath);
        //调用transferTo将上传的文件保存到指定的地址
        f1.transferTo(destFile);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/result.jsp");
        modelAndView.addObject("msg", destFile.getAbsolutePath());
        return modelAndView;
    }

    @GetMapping("/upload/upload1.do")
    public ModelAndView upload1() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/upload/upload1.jsp");
        return modelAndView;
    }

    /**
     * 多文件上传
     * 当上传多个文件的时候，可以使用多个MuiltipartFile参数来接收上传的文件。
     *
     */
    @PostMapping("/upload/upload2.do")
    public ModelAndView upload2(@RequestParam("file1") MultipartFile f1,
                                @RequestParam("file2") MultipartFile f2) {
        System.out.println("f1: "+f1);
        System.out.println("f2: "+f2);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/result.jsp");
        modelAndView.addObject("msg", null);
        return modelAndView;
    }

    @GetMapping("/upload/upload2.do")
    public ModelAndView upload2() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/upload/upload2.jsp");
        return modelAndView;
    }

    /**
     * 通过MultipartHttpServletRequest处理文件上传
     * MultipartHttpServletRequest接口
     * 1.SpringMVC接受到上传文件的请求后，会将请求转换为MultipartHttpServletRequest类型的对象
     * 2.MultipartHttpServletRequest中提供了一系列方法来获取请求中的所有参数信息
     * 3.其中getParameterMap()用来获取非文件类型的参数列表
     * 4.getMultiFileMap()方法用来获取上传的文件列表
     * 继承关系
     * ServletRequest(I)
     *        |
     *        |
     * HttpServletRequest(I)           MultipartRequest(I)
     *       |                                  |
     *       |                                  |
     *       ---MultipartHttpServletRequest(I)---
     */
    /**
     * 控制器中使用MultipartHttpServletRequest来获取所有参数信息，分了2部分获取
     * 1.先使用request.getParameterMap()获取非文件类型的参数，即可以获取表单中的name和age这2个参数的信息
     * 2.通过request.getMultiFileMap()获取文件类型的参数，既可以获取表单中file1和file2这2个文件的信息
     */
    @RequestMapping("/upload/upload3.do")
    public ModelAndView upload3(MultipartHttpServletRequest request) {
        //1.获取表单中非文件数据
        System.out.println("获取表单中非文件数据");
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((name, values) -> {
            System.out.println(String.format("%s:%s", name, Arrays.asList(values)));
        });
        //2.获取表单中文件数据
        System.out.println("获取表单中文件数据");
        MultiValueMap<String, MultipartFile> multiFileMap = request.getMultiFileMap();
        //3.遍历表单中元素信息
        multiFileMap.forEach((name, files) -> {
            System.out.println(String.format("%s:%s", name, files));
        });
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/result.jsp");
        modelAndView.addObject("msg", "上传成功！");
        return modelAndView;
    }
    @GetMapping("/upload/upload3.do")
    public ModelAndView upload3() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/upload/upload3.jsp");
        return modelAndView;
    }


    /**
     * 自定义对象接收多文件上传
     *
     */
    @Data
    @ToString
    static class UserDto {
        //姓名
        private String name;
        //年龄
        private Integer age;
        //头像
        private MultipartFile headImg;
        //身份证（多张图片）
        private List<MultipartFile> idCardImg;
    }

    @PostMapping("/upload/upload4.do")
    public ModelAndView upload4(UserDto userDto) {
        System.out.println("姓名："+userDto.getName());
        System.out.println("年龄："+userDto.getAge());
        System.out.println("头像图片："+userDto.getHeadImg());
        System.out.println("身份证（多张图片）："+Arrays.asList(userDto.getIdCardImg()));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/result.jsp");
        modelAndView.addObject("msg", "上传成功！");
        return modelAndView;
    }

    @GetMapping("/upload/upload4.do")
    public ModelAndView upload4() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/upload/upload4.jsp");
        return modelAndView;
    }
}
