package cn.web.auction.services;

import cn.web.auction.pojo.Auctionuser;

public interface userService {
    public Auctionuser login(String username,String password);
}
