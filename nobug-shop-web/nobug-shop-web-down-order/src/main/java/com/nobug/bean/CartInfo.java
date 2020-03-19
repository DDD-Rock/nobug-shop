package com.nobug.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class CartInfo implements Serializable {
    static final java.text.DecimalFormat df =new   java.text.DecimalFormat("#.00");

    private List<TProduct> productList;// 购物车中商品列表
    //	private String productTotal;//商品总金额
    private String amount;// 合计总金额，也就是用户最终需要支付的金额
    private int totalExchangeScore;//总计所需积分

    private List<Address> addressList;//用户配送地址信息
    @Deprecated
    private Address address;//客户配送信息
    private String defaultAddessID;//用户的默认地址ID

    public List<TProduct> getProductList() {
        if(productList==null){
            productList = new LinkedList<TProduct>();
        }
        return productList;
    }

    public void setProductList(List<TProduct> productList) {
        this.productList = productList;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Address getAddress() {
        if(address==null){
            address = new Address();
        }
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * 购物车汇总计算总金额
     * @return
     */
    public void totalCacl(){
        double _amount = 0;
        int _totalExchangeScore = 0;
        for(int i=0;i<getProductList().size();i++){
            TProduct p = getProductList().get(i);

            //TODO 单价*数量 求和
        }

        this.totalExchangeScore = _totalExchangeScore;
        if(_amount!=0){
            this.amount = df.format(_amount);
        }else{
            this.amount = "0.00";
        }
    }



    public String getDefaultAddessID() {
        return defaultAddessID;
    }

    public void setDefaultAddessID(String defaultAddessID) {
        this.defaultAddessID = defaultAddessID;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public int getTotalExchangeScore() {
        return totalExchangeScore;
    }

    public void setTotalExchangeScore(int totalExchangeScore) {
        this.totalExchangeScore = totalExchangeScore;
    }

    @Override
    public String toString() {
        return "CartInfo [productList=" + productList + ", amount=" + amount
                + ", addressList=" + addressList + ", address=" + address
                + ", defaultAddessID=" + defaultAddessID + "]";
    }

}

