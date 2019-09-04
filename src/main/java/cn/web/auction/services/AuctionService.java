package cn.web.auction.services;

import cn.web.auction.pojo.Auction;
import cn.web.auction.pojo.Auctionrecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuctionService {
    public List<Auction> findAuctions(Auction query);

    public void deleteAuction(int auctionid);

    public void addAuction(Auction auction);

    public Auction findAuctionAndRecordList(int auctionid);

    void addAuctionRecord(Auctionrecord record) throws Exception;
}
