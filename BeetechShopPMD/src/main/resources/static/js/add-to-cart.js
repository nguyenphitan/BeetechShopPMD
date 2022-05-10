 $(document).ready(function() {
	// Thêm vào giỏ hàng:
	$('.t-add-to-cart').click(function (e) {
		e.preventDefault();
		e.stopPropagation();
		
		// Kiểm tra số lượng đã chọn > số lượng còn lại hay không?
		let quantitySelected = 1;
		console.log($(e.target).parents('.product-item').siblings('.product-info'));
		let quantity = Number( $(e.target).parents('.product-item').siblings('.product-info').find('.product-quantity').text() );
		if( quantity < quantitySelected ) {
			alert("Số lượng sản phẩm không đủ để thêm.");
			return;
		}
		
		let productId = Number( $(e.target).parents('.product-item').siblings(".product-info").find('.product-id').text() );
		console.log(productId);
		
		let productRequest = {};
		productRequest['idProduct'] = productId;
		productRequest['quantitySelected'] = quantitySelected;
		// Kiểm tra chuỗi token:
		// Nếu chưa đăng nhập (chưa có token) -> thêm vào list sản phẩm -> đẩy lên session.
		// Nếu đã đăng nhập -> thêm danh sách sản phẩm vào bảng cart database.
		let token = $(e.target).parents('.product-item').siblings(".product-info").find('.token').text();
		
		
		if( token === "null" ) {
			// Gọi đến API clone, post dữ liệu vào session:
			$.ajax({
                type: "POST",
                url: "http://localhost:8081/clone/cart",
                // async: false,
                data: JSON.stringify(productRequest),
                dataType: "json",
                contentType: "application/json",
                success: function (response) {
                	alert("Thêm thành công.");
                	window.location.reload();
                },
                error: function(reject) {
                    alert("Không thành công.");
                }
            });	
			
		}
		else {
			// Gọi đến API, post dữ liệu:
			$.ajax({
                type: "POST",
                url: "http://localhost:8081/cart",
                // async: false,
                data: JSON.stringify(productRequest),
                dataType: "json",
                contentType: "application/json",
                success: function (response) {
                	alert("Thêm thành công.");
                	window.location.reload();
                },
                error: function(reject) {
                    alert("Không thành công.");
                }
            });	
		}	
	});
	
	
	
})