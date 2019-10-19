package cn.web.auction.services;

import cn.web.auction.pojo.Auction;
import cn.web.auction.pojo.AuctionCustom;
import cn.web.auction.pojo.Auctionrecord;

import java.util.List;

public interface AuctionService {
    public List<Auction> findAuctions(Auction query);

    public int  deleteAuction(int auctionid);

    public int updateAuction(Auction auction);

    public int  addAuction(Auction auction);

    public Auction findAuctionAndRecordList(int auctionid);

    public void addAuctionRecord(Auctionrecord record) throws Exception;

    public List<AuctionCustom> findAuctionResult();

    public List<Auction> findAuctionNotEnd();
}
