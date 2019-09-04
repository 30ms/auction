package cn.web.auction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auction")
public class AuctionController {

    @RequestMapping("/hello")
    @ResponseBody
    public String helloworld(){
        return "Hello world!";
    }
}
