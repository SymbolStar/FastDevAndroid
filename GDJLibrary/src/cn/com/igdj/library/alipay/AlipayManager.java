package cn.com.igdj.library.alipay;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alipay.sdk.app.PayTask;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class AlipayManager {

    private static final int RQF_PAY = 1;

/**
 * 根据订单信息构建支付宝需要的信息串
 * @param orderInfo
 * @param notifyUrl 回调url
 * @return
 */
    public static String getNewOrderInfo(OrderCommitted orderInfo , String notifyUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append("partner=\"");
        sb.append(Keys.DEFAULT_PARTNER);
        sb.append("\"&out_trade_no=\"");
        sb.append(orderInfo.getOrderID());
        sb.append("\"&subject=\"");
        sb.append(orderInfo.getOrderName());
        sb.append("\"&body=\"");
        sb.append(orderInfo.getNameList());
        sb.append("\"&total_fee=\"");
        sb.append(orderInfo.getFinalPrice());
        sb.append("\"&notify_url=\"");

        sb.append(notifyUrl);
        sb.append("\"&service=\"mobile.securitypay.pay");
        sb.append("\"&_input_charset=\"utf-8");
        sb.append("\"&return_url=\"");
        sb.append("http://m.alipay.com");
        sb.append("\"&payment_type=\"1");
        sb.append("\"&seller_id=\"");
        sb.append(Keys.DEFAULT_SELLER);

        // 如果show_url值为空，可不传
        // sb.append("\"&show_url=\"");
        sb.append("\"&it_b_pay=\"1d");
        sb.append("\"");

        return new String(sb);
    }

    private static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
        Date date = new Date();
        String key = format.format(date);

        java.util.Random r = new java.util.Random();
        key += r.nextInt();
        key = key.substring(0, 15);
        Log.d("", "outTradeNo: " + key);
        return key;
    }

    public static String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * 使用支付宝快捷支付进行付款
     * @param orderInfo
     * @param activity
     */
    public static void payOrder(final OrderCommitted orderInfo, final String notifyUrl , final Activity activity, final Handler handler) {
        String info = getNewOrderInfo(orderInfo,notifyUrl);
        String sign = Rsa.sign(info, Keys.PRIVATE);
        sign = URLEncoder.encode(sign);
        info += "&sign=\"" + sign + "\"&" + getSignType();
        Log.i("ExternalPartner", "start pay");
        // start the pay.
        Log.i("", "info = " + info);

        final String order = info;


        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(order);

                Message msg = new Message();
                msg.what = RQF_PAY;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();


//        new Thread() {
//            public void run() {
//                AliPay alipay = new AliPay(activity, mHandler);
//
//                //设置为沙箱模式，不设置默认为线上环境
////                alipay.setSandBox(true);
//                String result = alipay.pay(order);
//
//                Log.i("", "result = " + result);
//                Message msg = new Message();
//                msg.what = RQF_PAY;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        }.start();
    }

//    public static final String WAP_HOST = "http://10.10.2.8:8080/WS_WAP_PAYWAP-JAVA-UTF-8/alipayapi.jsp";
//
//    public static void payOrderByWAP(OrderInfo orderInfo, Context context) {
//        //卖家支付宝帐户
//        String WIDseller_email = Keys.DEFAULT_SELLER;
//        //必填
//
//        //商户订单号
//        String WIDout_trade_no = getOutTradeNo();
//        //商户网站订单系统中唯一订单号，必填
//
//        //订单名称
//        String WIDsubject = orderInfo.getCommodities().get(0).getNAME();
//        //必填
//
//        //付款金额
//        String WIDtotal_fee = String.format("%.2f",orderInfo.getAmount());
//        //必填
//
//        String url = WAP_HOST + "?WIDseller_email=" + WIDseller_email +
//                "&WIDout_trade_no=" + WIDout_trade_no +
//                "&WIDsubject=" + WIDsubject +
//                "&WIDtotal_fee=" + WIDtotal_fee;
//        try {
//            Uri uri = Uri.parse(url);
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            context.startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
