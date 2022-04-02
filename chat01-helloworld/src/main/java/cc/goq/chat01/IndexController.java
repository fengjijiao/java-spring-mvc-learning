package cc.goq.chat01;

import lombok.Data;
import lombok.ToString;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IndexController {
    @GetMapping("/get")
    public String get() {
        return "get";
    }
    //post请求，模拟表单提交
    @PostMapping(value = "/post", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, String[]> post(HttpServletRequest request) {
        return request.getParameterMap();
    }
    //post请求，json数据
    @PostMapping(value = "/body", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Integer> body(@RequestBody List<Integer> list) {
        return list;
    }
    //put请求
    @PutMapping("/put")
    public String put() {
        return "put";
    }
    @Data
    @ToString
    static class User {
        private String userName;
        private int age;
    }
    //模拟多文件上传，顺便带上表单数据
    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestParam("file1") MultipartFile file1,
                                      @RequestParam("filw2") MultipartFile file2,
                                      User user,
                                      HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("file1.size", file1.getSize());
        result.put("file1.name", file1.getName());
        result.put("file1.originalFilename", file1.getOriginalFilename());
        result.put("file2.size", file2.getSize());
        result.put("file2.name", file2.getName());
        result.put("file2.originalFilename", file2.getOriginalFilename());
        result.put("params", request.getParameterMap());
        result.put("user", user);
        return result;
    }
}
