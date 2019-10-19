package cn.web.auction.services.impl;

import cn.web.auction.mapper.AuctionCustomMapper;
import cn.web.auction.mapper.AuctionMapper;
import cn.web.auction.mapper.AuctionrecordMapper;
import cn.web.auction.pojo.*;
import cn.web.auction.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private AuctionMapper auctionMapper;

    @Autowired
    private AuctionCustomMapper auctionCustomMapper;

    @Autowired
    private AuctionrecordMapper auctionrecordMapper;

    @Override
    public List<Auction> findAuctions(Auction query) {
        AuctionExample example = new AuctionExample();
        AuctionExample.Criteria criteria=example.createCriteria();
        //拼接查询条件
        if(query!=null){
            //1.商品名称查询
            if(query.getAuctionname()!=null&&!"".equals(query.getAuctionname())){
                criteria.andAuctionnameLike("%"+query.getAuctionname()+"%");
            }
            //2.商品描述
            if(query.getAuctiondesc()!=null&&!"".equals(query.getAuctiondesc())){
                criteria.andAuctiondescLike("%"+query.getAuctiondesc()+"%");
            }
            //3.大于开始时间
            if(query.getAuctionstarttime()!=null){
                criteria.andAuctionstarttimeGreaterThanOrEqualTo(query.getAuctionstarttime());
            }
            //4.小于结束时间
            if(query.getAuctionendtime()!=null){
                criteria.andAuctionendtimeLessThanOrEqualTo(query.getAuctionendtime());
            }
            //5.大于起拍价
            if(query.getAuctionstartprice()!=null){
                criteria.andAuctionstartpriceGreaterThan(query.getAuctionstartprice());
            }
        }
        example.setOrderByClause("auctionstartprice desc");
        List<Auction> list=auctionMapper.selectByExample(example);//若没有封装查询条件，查找全部
        return list;
    }

    @Override
    public int deleteAuction(int auctionid) {
        AuctionrecordExample auctionrecordExample=new AuctionrecordExample();
        AuctionrecordExample.Criteria criteria=auctionrecordExample.createCriteria();
        criteria.andAuctionidEqualTo(auctionid);
        //删除对应商品id的竞拍记录
        auctionrecordMapper.deleteByExample(auctionrecordExample);
        //删除商品
        return auctionMapper.deleteByPrimaryKey(auctionid);
    }

    @Override
    public int updateAuction(Auction auction) {
        AuctionExample auctionExample=new AuctionExample();
        AuctionExample.Criteria criteria=auctionExample.createCriteria();
        criteria.andAuctionidEqualTo(auction.getAuctionid());
        return auctionMapper.updateByExampleSelective(auction,auctionExample);
    }

    @Override
    public int addAuction(Auction auction) {
            return auctionMapper.insert(auction);
    }

    @Override
    public Auction findAuctionAndRecordList(int auctionid) {
        return auctionCustomMapper.findAuctionAndRecordList(auctionid);
    }

    @Override
    public void addAuctionRecord(Auctionrecord record) throws Exception {
        Auction auction=auctionCustomMapper.findAuctionAndRecordList(record.getAuctionid());
            //判定时间有没有过期
        if(auction.getAuctionendtime().after(new Date())==false){
            throw new Exception("该商品已经结束竞拍！");
        }else {
            //判定是否是第一次竞拍
            if(auction.getAuctionrecordList()!=null&&auction.getAuctionrecordList().size()>0){
                Auctionrecord maxRecord=auction.getAuctionrecordList().get(0);
                if(record.getAuctionprice().doubleValue()<=maxRecord.getAuctionprice().doubleValue()){
                        throw new Exception("价格必须高于最高价！");
                }
            }else{//第一次竞价
                if(record.getAuctionprice().doubleValue()<auction.getAuctionstartprice().doubleValue()){
                    throw new Exception("价格必须高于或等于起拍价！");
                }
            }
        }
        //保存数据库
        auctionrecordMapper.insert(record);
    }

    @Override
    public List<AuctionCustom> findAuctionResult() {
        return auctionCustomMapper.findAuctionResult();
    }

    @Override
    public List<Auction> findAuctionNotEnd() {
        return auctionCustomMapper.findAuctionNotEnd();
    }
}
