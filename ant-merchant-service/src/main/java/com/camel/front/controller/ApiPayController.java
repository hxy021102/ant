package com.camel.front.controller;


import com.aliyun.mns.model.TopicMessage;
import com.mobian.absx.F;
import com.mobian.controller.BaseController;
import com.mobian.listener.Application;
import com.mobian.pageModel.*;
import com.mobian.service.MbBalanceLogServiceI;
import com.mobian.service.MbBalanceServiceI;
import com.mobian.service.MbPaymentServiceI;
import com.mobian.service.MbUserServiceI;
import com.mobian.service.impl.RedisUserServiceImpl;
import com.mobian.thirdpart.mns.MNSTemplate;
import com.mobian.thirdpart.mns.MNSUtil;
import com.mobian.thirdpart.wx.HttpUtil;
import com.mobian.thirdpart.wx.PayCommonUtil;
import com.mobian.thirdpart.wx.WeixinUtil;
import com.mobian.thirdpart.wx.XMLUtil;
import com.mobian.util.IpUtil;
import com.mobian.util.Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * 基础数据
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/api/pay")
public class ApiPayController extends BaseController {

	private static final Logger log = Logger.getLogger(ApiPayController.class);

	@Autowired
	private MbPaymentServiceI mbPaymentService;

	@Autowired
	private MbBalanceLogServiceI mbBalanceLogService;

	@Autowired
	private MbUserServiceI mbUserService;

	@Autowired
	private MbBalanceServiceI mbBalanceService;

	@Autowired
	private RedisUserServiceImpl redisUserService;

	/**
	 * 微信支付
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/wxPay")
	@ResponseBody
	public Json wxPay(MbPayment payment, MbBalanceLog balanceLog, String trade_type, Integer type, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);

			String orderNo, body;
			Integer amount;
			if(!F.empty(payment.getOrderId())) {
				MbPayment exist = mbPaymentService.getByOrderId(payment.getOrderId());
				if(exist != null) {
					if(exist.getStatus()) {
						j.setMsg("已支付或审核中请耐心等待！");
						j.fail();
						return j;
					}
				} else {
					payment.setStatus(false);
					mbPaymentService.add(payment);
				}
				orderNo = "MP" + payment.getOrderId();
				body = Application.getString(WeixinUtil.BODY) + " - 订单支付";
				amount = payment.getAmount();
			} else {
				if(F.empty(type)) type = 1; // 默认余额
				MbBalance balance = null;
				if(type == 1) {
					balance = mbBalanceService.addOrGetMbBalance(s.getShopId());
					body = Application.getString(WeixinUtil.BODY) + " - 钱包充值";
				} else {
					balance = mbBalanceService.addOrGetMbBalanceCash(s.getShopId());
					body = Application.getString(WeixinUtil.BODY) + " - 桶账余额充值";
				}

				balanceLog.setIsdeleted(true);
				balanceLog.setBalanceId(balance.getId());
				mbBalanceLogService.add(balanceLog);

				orderNo = "MB" + balanceLog.getId();
				amount = balanceLog.getAmount();
			}

			MbUser user = mbUserService.get(Integer.valueOf(s.getId()));
			String requestXml = requestXML(orderNo,body,amount,user.getRefId(), trade_type, request);
			System.out.println("~~~~~~~~~~~~微信支付接口请求参数requestXml:" + requestXml);
			String result = HttpUtil.httpsRequest(WeixinUtil.UNIFIED_ORDER_URL, "POST", requestXml);
			System.out.println("~~~~~~~~~~~~微信支付接口返回结果result:" + result);
			Map<String, String> resultMap = XMLUtil.doXMLParse(result);

			j.setObj(returnJsonStr(resultMap, request));
			j.success();
			j.setMsg("微信支付成功！");
		}catch(Exception e){
			j.setMsg(Application.getString(EX_0001));
			logger.error("微信支付接口异常", e);
		}
		return j;
	}

	/**
	 * 余额支付
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/balancePay")
	@ResponseBody
	public Json balancePay(MbPayment payment, String vcode, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			String oldCode = redisUserService.getBalancePayValidateCode(s.getId());
			if(F.empty(oldCode)) {
				j.setMsg("验证码已过期！");
				return j;
			}
			if(!oldCode.equals(vcode)) {
				j.setMsg("验证码错误！");
				return j;
			}
			MbPayment exist = mbPaymentService.getByOrderId(payment.getOrderId());
			if(exist != null && exist.getStatus()) {
				j.setMsg("已支付或审核中请耐心等待！");
				j.fail();
				return j;
			}

			payment.setStatus(true);
//			payment.setShopId(s.getShopId());
			mbPaymentService.addOrUpdate(payment);

			j.success();
			j.setMsg("余额支付成功！");
		}catch(Exception e){
			j.setMsg(Application.getString(EX_0001));
			logger.error("余额支付接口异常", e);
		}
		return j;
	}

	/**
	 * 转账汇款
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/remitPay")
	@ResponseBody
	public Json remitPay(MbPayment payment, HttpServletRequest request) {
		Json j = new Json();
		try{
			MbPayment exist = mbPaymentService.getByOrderId(payment.getOrderId());
			if(exist != null && exist.getStatus()) {
				j.setMsg("已支付或审核中请耐心等待！");
				j.fail();
				return j;
			}
			payment.setStatus(true);
			mbPaymentService.addOrUpdate(payment);

			j.success();
			j.setMsg("余额支付成功！");
		}catch(Exception e){
			j.setMsg(Application.getString(EX_0001));
			logger.error("余额支付接口异常", e);
		}
		return j;
	}

	@ResponseBody
	@RequestMapping("/getVCode")
	public Json getVCode(HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			MbUser user = mbUserService.get(Integer.valueOf(s.getId()));
			String phone = user.getPhone();
			if(!F.empty(phone)) {
				String oldCode = redisUserService.getBalancePayValidateCode(s.getId());
				if(!F.empty(oldCode)) {
					j.setMsg("访问过于频繁，请秒后重试！");
					return j;
				}

				String code = Util.CreateNonceNumstr(6); //生成短信验证码
				MNSTemplate template = new MNSTemplate();
				template.setTemplateCode("SMS_63345368");
				Map<String, String> params = new HashMap<String, String>();
				params.put("code", code);
				params.put("product", "骆驼送");
				template.setParams(params);
				TopicMessage topicMessage = MNSUtil.sendMns(phone, template);
				if(topicMessage != null) {
					redisUserService.setBalancePayValidateCode(s.getId(), code);
					j.setSuccess(true);
					j.setMsg("获取短信验证码成功！");
					j.setObj(params);
					return j;
				}
				j.setMsg("获取短信验证码失败！");
			}
		} catch (Exception e) {
			j.setMsg(Application.getString(EX_0001));
			logger.error("获取短信验证码接口异常", e);
		}
		return j;
	}

	/**
	 * 退款
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/refund")
	@ResponseBody
	public Json refund(MbPayment payment, HttpServletRequest request) {
		Json j = new Json();
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("amount", payment.getAmount());
			params.put("orderNo", payment.getOrderId());
			String requestXml = PayCommonUtil.requestRefundXML(params);
			System.out.println("~~~~~~~~~~~~微信支付接口请求参数requestXml:" + requestXml);
			String result = HttpUtil.httpsRequest(WeixinUtil.REFUND_URL, "POST", requestXml);
			System.out.println("~~~~~~~~~~~~微信支付接口返回结果result:" + result);
			Map<String, String> resultMap = XMLUtil.doXMLParse(result);

			j.setObj(resultMap);
			j.success();
		}catch(Exception e){
			j.setMsg("支付异常！");
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping("/paySuccess")
	public synchronized void paySuccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		InputStream inStream = request.getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		System.out.println("~~~~~~~~~~~~~~~~付款成功~~~~~~~~~");
		outSteam.close();
		inStream.close();
		String result  = new String(outSteam.toByteArray(),"utf-8");//获取微信调用我们notify_url的返回信息
		Map<Object, Object> map = XMLUtil.doXMLParse(result);
		for(Object keyValue : map.keySet()){
			System.out.println(keyValue+"="+map.get(keyValue));
		}

		// 对数据库的操作
		String orderNo = map.get("out_trade_no").toString();
		String transaction_id = map.get("transaction_id").toString(); // 微信支付订单号
		if(orderNo.startsWith("MP")) {
			MbPayment payment = new MbPayment();
			payment.setOrderId(Integer.valueOf(orderNo.substring(2)));
			payment.setRefId(transaction_id);
			if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
				payment.setStatus(true);
			} else {
				payment.setReason(map.get("return_msg").toString());
				payment.setStatus(false);
			}
			mbPaymentService.addOrUpdate(payment);
		} else if(orderNo.startsWith("MB")) {
			MbBalanceLog balanceLog = new MbBalanceLog();
			balanceLog.setId(Integer.valueOf(orderNo.substring(2)));
			balanceLog.setRefId(transaction_id);
			if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
				balanceLog.setIsdeleted(false);
			} else {
				balanceLog.setReason(map.get("return_msg").toString());
				balanceLog.setIsdeleted(true);
			}
			mbBalanceLogService.updateLogAndBalance(balanceLog);
		}


		response.getWriter().write(PayCommonUtil.setXML("SUCCESS", ""));   //告诉微信服务器，我收到信息了，不要在调用回调action了
		System.out.println("-------------"+PayCommonUtil.setXML("SUCCESS", ""));
	}

	/**
	 * 微信支付请求参数
	 * @return
	 */
	private String requestXML(String orderNo, String body, Integer amount, String openid, String trade_type, HttpServletRequest request) {
		try {
			SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
			// 公众账号ID 必填
			parameters.put("appid", Application.getString(WeixinUtil.APPID));
			// 附加数据 不是必填
			parameters.put("attach", orderNo);
			// 商品描述  必填
			parameters.put("body", body);
			// 商户号 必填
			parameters.put("mch_id", Application.getString(WeixinUtil.MCH_ID));
			// 随机字符串  必填 不长于32位
			parameters.put("nonce_str", WeixinUtil.CreateNoncestr());
			// 通知地址  必填
			parameters.put("notify_url", Application.getString(WeixinUtil.NOTIFY_URL));
			// 用户标识  trade_type=JSAPI，此参数必传
			parameters.put("openid", openid);
			// 商户订单号  必填
			parameters.put("out_trade_no", orderNo);
			// 终端IP  必填
			parameters.put("spbill_create_ip", IpUtil.getIp(request));
			// 总金额  必填（单位为分必须为整数）
			parameters.put("total_fee", amount + "");
			// 交易类型  必填
			parameters.put("trade_type", F.empty(trade_type) ? "JSAPI" : trade_type);

			// 签名 必填
			String sign = PayCommonUtil.createSign("UTF-8", parameters);
			parameters.put("sign", sign);

			return PayCommonUtil.getRequestXml(parameters);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private SortedMap<Object,Object> returnJsonStr(Map<String, String> resultMap, HttpServletRequest request) {
		Date nowDate = new Date();
		SortedMap<Object,Object> params = new TreeMap<Object,Object>();
		// 公众账号ID
		params.put("appId", resultMap.get("appid"));
		// 时间戳
		params.put("timeStamp", Long.toString(nowDate.getTime()));
		// 随机串
		params.put("nonceStr", WeixinUtil.CreateNoncestr());
		// 商品包信息
		params.put("package", "prepay_id=" + resultMap.get("prepay_id"));
		// 微信签名方式
		params.put("signType", WeixinUtil.SIGN_TYPE);
		// 微信签名
		String paySign = PayCommonUtil.createSign("UTF-8", params);
		// paySign的生成规则和Sign的生成规则一致
		params.put("paySign", paySign);
		// 这里用packageValue是预防package是关键字在js获取值出错
		params.put("packageValue", "prepay_id=" + resultMap.get("prepay_id"));

		// 微信版本号，用于前面提到的判断用户手机微信的版本是否是5.0以上版本。
		String userAgent = request.getHeader("user-agent");
		char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
		params.put("agent", new String(new char[]{agent}));

		return params;
	}

}
