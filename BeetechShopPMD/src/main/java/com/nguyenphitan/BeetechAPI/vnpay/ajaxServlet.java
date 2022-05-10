/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nguyenphitan.BeetechAPI.vnpay;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 *
 * @author xonv
 */
@RestController
@RequestMapping("/vnpay_jsp")
public class AjaxServlet extends HttpServlet {
	
    @PostMapping("/vnpayajax")
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String vnpVersion = "2.1.0";
        String vnpCommand = "pay";
        String vnpOrderInfo = req.getParameter("vnp_OrderInfo");
        String orderType = req.getParameter("ordertype");
        String vnpTxnRef = Config.getRandomNumber(8);
        String vnpIpAddr = Config.getIpAddress(req);
        String vnpTmnCode = Config.vnpTmnCode;
        
        int amount = Integer.parseInt(req.getParameter("amount")) * 100;
        
        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", vnpVersion);
        vnpParams.put("vnp_Command", vnpCommand);
        vnpParams.put("vnp_TmnCode", vnpTmnCode);
        vnpParams.put("vnp_Amount", String.valueOf(amount));
        vnpParams.put("vnp_CurrCode", "VND");
        String bankCode = req.getParameter("bankcode");
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParams.put("vnp_BankCode", bankCode);
        }
        vnpParams.put("vnp_TxnRef", vnpTxnRef);
        vnpParams.put("vnp_OrderInfo", vnpOrderInfo);
        vnpParams.put("vnp_OrderType", orderType);

        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnpParams.put("vnp_Locale", locate);
        } else {
            vnpParams.put("vnp_Locale", "vn");
        }
        vnpParams.put("vnp_ReturnUrl", Config.vnpReturnurl);
        vnpParams.put("vnp_IpAddr", vnpIpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(cld.getTime());

        vnpParams.put("vnp_CreateDate", vnpCreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnpExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.0.1 Version
        vnpParams.put("vnp_ExpireDate", vnpExpireDate);
        //Billing
        vnpParams.put("vnp_Bill_Mobile", req.getParameter("txt_billing_mobile"));
        vnpParams.put("vnp_Bill_Email", req.getParameter("txt_billing_email"));
        String fullName = (req.getParameter("txt_billing_fullname")).trim();
        if (fullName != null && !fullName.isEmpty()) {
            int idx = fullName.indexOf(' ');
            String firstName = fullName.substring(0, idx);
            String lastName = fullName.substring(fullName.lastIndexOf(' ') + 1);
            vnpParams.put("vnp_Bill_FirstName", firstName);
            vnpParams.put("vnp_Bill_LastName", lastName);

        }
        vnpParams.put("vnp_Bill_Address", req.getParameter("txt_inv_addr1"));
        vnpParams.put("vnp_Bill_City", req.getParameter("txt_bill_city"));
        vnpParams.put("vnp_Bill_Country", req.getParameter("txt_bill_country"));
        if (req.getParameter("txt_bill_state") != null && !req.getParameter("txt_bill_state").isEmpty()) {
            vnpParams.put("vnp_Bill_State", req.getParameter("txt_bill_state"));
        }
        // Invoice
        vnpParams.put("vnp_Inv_Phone", req.getParameter("txt_inv_mobile"));
        vnpParams.put("vnp_Inv_Email", req.getParameter("txt_inv_email"));
        vnpParams.put("vnp_Inv_Customer", req.getParameter("txt_inv_customer"));
        vnpParams.put("vnp_Inv_Address", req.getParameter("txt_inv_addr1"));
        vnpParams.put("vnp_Inv_Company", req.getParameter("txt_inv_company"));
        vnpParams.put("vnp_Inv_Taxcode", req.getParameter("txt_inv_taxcode"));
        vnpParams.put("vnp_Inv_Type", req.getParameter("cbo_inv_type"));
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnpParams.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnpParams.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnpSecureHash = Config.hmacSHA512(Config.vnpHashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = Config.vnpPayUrl + "?" + queryUrl;
        JsonObject job = new JsonObject();
        job.addProperty("code", "00");
        job.addProperty("message", "success");
        job.addProperty("data", paymentUrl);
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(job));
    }

}
