package cn.web.auction.mapper;

import cn.web.auction.pojo.Auction;
import cn.web.auction.pojo.AuctionCustom;

import java.util.List;

public interface AuctionCustomMapper {
    public Auction findAuctionAndRecordList(int auctionId);

    public List<AuctionCustom> findAuctionResult();

    public List<Auction> findAuctionNotEnd();
}
