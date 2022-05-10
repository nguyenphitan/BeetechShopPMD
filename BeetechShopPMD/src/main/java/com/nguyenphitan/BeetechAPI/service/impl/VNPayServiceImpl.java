package com.nguyenphitan.BeetechAPI.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.service.VNPayService;

@Service
public class VNPayServiceImpl implements VNPayService {

	@Override
	public ModelAndView vnpayReturnPage(String amount, String bankCode, String bankTranNo, String cardType,
			String orderInfo, String payDate, String responseCode, String tmnCode, String transactionNo,
			String transactionStatus, String txnRef, String secureHash) {
		ModelAndView modelAndView = new ModelAndView("vnpay_return");

		Map<String, String> data = new HashMap<String, String>();
		data.put("amount", amount);
		data.put("bankCode", bankCode);
		data.put("bankTranNo", bankTranNo);
		data.put("cardType", cardType);
		data.put("orderInfo", orderInfo);
		data.put("payDate", payDate);
		data.put("responseCode", responseCode);
		data.put("tmnCode", tmnCode);
		data.put("transactionNo", transactionNo);
		data.put("transactionStatus", transactionStatus);
		data.put("txnRef", txnRef);
		data.put("secureHash", secureHash);

		modelAndView.addObject("data", data);
		return modelAndView;
	}

}
