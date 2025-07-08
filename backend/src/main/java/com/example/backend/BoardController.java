package com.example.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/board")
public class BoardController {

    @PostMapping("add")
    @ResponseBody
    public String add(@RequestBody BoardDto dto) {
        System.out.println(dto);
        
        return null;
    }
}
