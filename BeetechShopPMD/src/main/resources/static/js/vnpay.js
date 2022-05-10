/**
 * Quản lý tài khoản
 * Created by: NPTAN (25/04/2022)
 * Version: 1.0
 */

$(document).ready(function() {
	// Format lại số tiền:
	let amount = Math.floor( Number($('#t-amount-clone').text()) / 100 );
 	$('#t-amount').text(amount);  	 
	 
	// Nếu responseCode = '00' -> giao dịch thành công -> tạo hóa đơn -> xóa sản phẩm khỏi giỏ hàng:
	let responseCode = $('.responseCode').text();
	if( responseCode === '00' ) {
		// 1. Lấy ra user id:
		let userId = Number($('.t-user-id').text());
		// 2. Lấy ra danh sách id sản phẩm, số lượng đã chọn:
		let productIds = $('.t-product-id')		// ID sản phẩm
		let quantitySelecteds = $('.t-quantitySelected');	// Số lượng đã chọn
		
		// Buil object:
		// 1. Build List ProductRequest:
		let productRequests = [];
		for(let i = 0 ; i < productIds.length ; i++) {
			let proRequest = {};
			proRequest.idProduct = Number( $(productIds[i]).text() );
			proRequest.quantitySelected = Number( $(quantitySelecteds[i]).text() );
			productRequests.push(proRequest);
		}
		
		// 2. Build BillRequest:
		let billRequest = {};
		billRequest.userId = userId;
		billRequest.productRequests = productRequests;
		
		// Gọi API tạo hóa đơn:
		createBill(billRequest);
	}
	
	// Click quay về trang chủ:
   	$('.t-return-home').click(function () {
   		window.location.href = "http://localhost:8081";
	});
	
})


/*
	Reset giỏ hàng khi thanh toán thành công
	Created by: NPTAN (06/05/2022)
	Version: 1.0
*/
function createBill(billRequest) {
	// Gọi API tạo hóa đơn:
	$.ajax({
        type: "POST",
        url: "http://localhost:8081/public/bill",
        // async: false,
        data: JSON.stringify(billRequest),
        dataType: "json",
        contentType: "application/json",
        success: function (response) {
        	alert("Thanh toán thành công.");
			// Reset sản phẩm trong giỏ hàng
			deleteProductInCart();
        	
        },
        error: function(reject) {
            alert("Không thành công.");
            console.log(reject);
        }
    });	
	
}


/*
	Reset giỏ hàng khi thanh toán thành công
	Created by: NPTAN (06/05/2022)
	Version: 1.0
*/  
function deleteProductInCart() {
	// Xóa danh sách sản phẩm đã thanh toán trong giỏ hàng
	// Update số lượng sản phẩm còn lại trong database
	$.ajax({
	     type: "DELETE",
	     url: `http://localhost:8081/pay`,
	     success: function (response) {
	     	alert("Đặt hàng thành công, đơn hàng sẽ được giao trong vài ngày tới.");
	     }
	});
}
     