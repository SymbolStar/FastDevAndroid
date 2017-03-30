package cn.com.igdj.library.alipay;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by zou on 8/6/15.
 */
public class OrderCommitted {
    
    private String orderID;
	private String orderName;
	private String finalPrice;
	private String nameList;
	
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(String finalPrice) {
		this.finalPrice = finalPrice;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String ordername) {
		this.orderName = ordername;
	}

    public static OrderCommitted parseResult(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<OrderCommitted>() {

        }.getType();
        OrderCommitted orderCommitted = gson.fromJson(jsonString, type);
        return orderCommitted;
    }
	public String getNameList() {
		return nameList;
	}
	public void setNameList(String nameList) {
		this.nameList = nameList;
	}

}
