package cn.web.auction.controller;

import cn.web.auction.pojo.Auction;
import cn.web.auction.pojo.AuctionCustom;
import cn.web.auction.pojo.Auctionrecord;
import cn.web.auction.pojo.Auctionuser;
import cn.web.auction.services.AuctionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/auction")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    public static final int PAG_SIZE=5;

    @RequestMapping("/findAuction")
    public ModelAndView findAuctions(
            @ModelAttribute("condition") Auction query,
            @RequestParam(value = "pageNum",required=false,defaultValue = "1")int pageNum
    ){
        ModelAndView mv=new ModelAndView();

        PageHelper.startPage(pageNum,PAG_SIZE);
        List<Auction> list=auctionService.findAuctions(query);
        PageInfo pageInfo=new PageInfo<>(list);

        mv.addObject("pageInfo",pageInfo);
        mv.addObject("auctionList",list);

        mv.setViewName("auctionList");
        return mv;
    }

    @RequestMapping("/findAuction_update")
    public ModelAndView findAuction_update(@RequestParam(value = "auctionid")int auctionid){
        ModelAndView mv=new ModelAndView();
        Auction auction=new Auction();
        auction.setAuctionid(auctionid);
        List<Auction> auctions=auctionService.findAuctions(auction);
        if(auctions.size()>0){
            auction=auctions.get(0);
            mv.addObject("auction",auction);
            mv.setViewName("updateAuction");
        }
        return mv;
    }

    @RequestMapping("/auctionDetail/{auctionId}")
    public ModelAndView auctionDetail(@PathVariable(value = "auctionId") int auctionId){
        ModelAndView mv=new ModelAndView();

        Auction auctionDetail=auctionService.findAuctionAndRecordList(auctionId);
        mv.addObject("auctionDetail",auctionDetail);
        mv.setViewName("auctionDetail");
        return mv;
    }

    @RequestMapping("/saveAuctionRecord/{auctionId}")
    public String saveAuctionRecord(
            @RequestParam(value = "auctionPrice") BigDecimal auctionPrice,
            @PathVariable(value = "auctionId")int auctionId, HttpSession session, Model model){
        if(session.getAttribute("admin")==null){
            model.addAttribute("errorMsg","请先登录!");
            return "error";
        }
        try{
            Auctionuser user=(Auctionuser)session.getAttribute("admin");
            Auctionrecord auctionrecord=new Auctionrecord();
            //设置竞拍用户ID
            auctionrecord.setUserid(user.getUserid());
            //设置物品id
            auctionrecord.setAuctionid(auctionId);
            //设置竞拍时间
            auctionrecord.setAuctiontime(new Date());
            //设置价格
            auctionrecord.setAuctionprice(auctionPrice);
            auctionService.addAuctionRecord(auctionrecord);
        }catch (Exception e){
            model.addAttribute("errorMsg",e.getMessage());
            return "error";
        }
        return "redirect:/auction/auctionDetail/"+auctionId;
    }

    @RequestMapping("/addAuction")
    public String addAuction(HttpSession session, Model model, Auction auction, MultipartFile pic){
        if(session.getAttribute("admin")==null){
            model.addAttribute("errorMsg","请先登录!");
            return "error";
        }else {
            Auctionuser auctionuser=(Auctionuser)session.getAttribute("admin");
            if(auctionuser.getUserisadmin()==1){
                try {
                    if(pic.getSize()>0) {
                        //获取图片目录的绝对路径
                        String path = session.getServletContext().getRealPath("static/images");
                        //获取文件后缀名
                        String suffix=pic.getOriginalFilename()
                                .substring(pic.getOriginalFilename()
                                        .lastIndexOf("."),pic.getOriginalFilename().length());
                        //新的文件名
                        String filename=auction.getAuctionname()+suffix;
                        File targetFile = new File(path,filename);
                        //存放图片
                        pic.transferTo(targetFile);
                        //修改auction属性
                        auction.setAuctionpic(filename);
                        auction.setAuctionpictype(pic.getContentType());
                        //添加拍卖品到数据库
                        auctionService.addAuction(auction);
                    }else{
                        model.addAttribute("errorMsg","文件大小应大于0B!");
                        return "error";
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return "redirect:/auction/findAuction";
            }else{
                model.addAttribute("errorMsg","您没有权限发布!");
                return "error";
            }
        }
    }

    @RequestMapping("/deleteAuction")
    public String deleteAuction(HttpSession session,@RequestParam(value = "auctionid") int auctionid,Model model){
        if(session.getAttribute("admin")==null){
            model.addAttribute("errorMsg","请先登录!");
            return "error";
        }else{
            Auctionuser auctionuser=(Auctionuser)session.getAttribute("admin");
            if(auctionuser.getUserisadmin()==1){
                try{
                    auctionService.deleteAuction(auctionid);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return "redirect:/auction/findAuction";
            }else{
                model.addAttribute("errorMsg","您没有权限删除!");
                return "error";
            }
        }
    }

    @RequestMapping("/updateAuction")
    public String updateAuction(HttpSession session,@ModelAttribute(value = "auction") Auction auction,Model model,MultipartFile pic){
        if(session.getAttribute("admin")==null){
            model.addAttribute("errorMsg","请先登录!");
            return "error";
        }else {
            Auctionuser auctionuser=(Auctionuser)session.getAttribute("admin");
            if(auctionuser.getUserisadmin()==1){
                try{
                    //若要替换图片
                    if(pic.getSize()>0){
                        //获取图片目录的绝对路径
                        String path = session.getServletContext().getRealPath("static/images");
                        //获取文件后缀名
                        String suffix=pic.getOriginalFilename()
                                .substring(pic.getOriginalFilename()
                                        .lastIndexOf("."),pic.getOriginalFilename().length());
                        //新的文件名
                        String filename=auction.getAuctionname()+suffix;
                        File targetFile = new File(path,filename);
                        //若存在旧图片，删除
                        if(targetFile.exists()){
                            targetFile.delete();
                        }
                        //存放图片
                        pic.transferTo(targetFile);
                        //修改auction属性
                        auction.setAuctionpic(filename);
                        auction.setAuctionpictype(pic.getContentType());
                    }
                    //修改
                    auctionService.updateAuction(auction);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return "redirect:/auction/findAuction";
            }else{
                model.addAttribute("errorMsg","您没有权限修改!");
                return "error";
            }
        }
    }

    @RequestMapping("/findAuctionResult")
    public ModelAndView findAuctionResult(){
        ModelAndView mv = new ModelAndView();

        List<AuctionCustom> auctionCustomList=auctionService.findAuctionResult();
        List<Auction> auctionList=auctionService.findAuctionNotEnd();

        mv.addObject("auctionCustomList",auctionCustomList);
        mv.addObject("auctionList",auctionList);

        mv.setViewName("auctionResult");

        return mv;
    }
}
