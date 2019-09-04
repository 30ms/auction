package cn.web.auction.services.impl;

import cn.web.auction.dao.AuctionuserMapper;
import cn.web.auction.pojo.Auctionuser;
import cn.web.auction.pojo.AuctionuserExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class userServiceImpl implements cn.web.auction.services.userService{

    @Autowired
    private AuctionuserMapper auctionuserMapper;

    @Override
    public Auctionuser login(String username, String password) {
        //查询条件封装类
        AuctionuserExample example = new AuctionuserExample();
        //调用封装类的内部类
        AuctionuserExample.Criteria criteria = example.createCriteria();
        //拼接实际需要的条件(精确查询)
        criteria.andUsernameEqualTo(username);
        criteria.andUserpasswordEqualTo(password);
        //返回用户
        List<Auctionuser> userList =auctionuserMapper.selectByExample(example);
        //验证登录
        if(userList!=null&&userList.size()>0){
            return userList.get(0);
        }
        return null;
    }
}
