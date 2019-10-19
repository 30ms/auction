package cn.web.auction.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class AuctionCustom {
    private String auctionname;
    private Date auctionstarttime;
    private Date auctionendtime;
    private BigDecimal auctionstartprice;
    private BigDecimal auctionprice;
    private String username;

    public String getAuctionname() {
        return auctionname;
    }

    public void setAuctionname(String auctionname) {
        this.auctionname = auctionname;
    }

    public Date getAuctionstarttime() {
        return auctionstarttime;
    }

    public void setAuctionstarttime(Date auctionstarttime) {
        this.auctionstarttime = auctionstarttime;
    }

    public Date getAuctionendtime() {
        return auctionendtime;
    }

    public void setAuctionendtime(Date auctionendtime) {
        this.auctionendtime = auctionendtime;
    }

    public BigDecimal getAuctionstartprice() {
        return auctionstartprice;
    }

    public void setAuctionstartprice(BigDecimal auctionstartprice) {
        this.auctionstartprice = auctionstartprice;
    }

    public BigDecimal getAuctionprice() {
        return auctionprice;
    }

    public void setAuctionprice(BigDecimal auctionprice) {
        this.auctionprice = auctionprice;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
