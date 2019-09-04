package cn.web.auction.services;

import cn.web.auction.pojo.Auctionuser;
import org.springframework.stereotype.Service;

@Service
public interface userService {
    public Auctionuser login(String username,String password);
}
