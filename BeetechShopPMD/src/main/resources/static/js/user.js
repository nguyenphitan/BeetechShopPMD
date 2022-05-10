/**
 * Quản lý tài khoản
 * Created by: NPTAN (08/05/2022)
 * Version: 1.0
 */
 
 $(document).ready(function() {
	/*
		Kiểm tra role:
		1. Nếu là SELLER hoặc ADMIN thì làm mờ nút thêm nhân viên
		2. Ngược lại, hiển thị nút thêm nhân viên
		
		Created by: NPTAN (08/05/2022)
		Version: 1.0
	*/
	let roles = $('.account_role');
	console.log(roles);
	for(let role of roles) {
		let roleValue = $(role).text();
		if(roleValue == 'SELLER' || roleValue == 'ADMIN') {
			$(role).prev().addClass('complete');
			$(role).prev().text("Đã thêm");
		}
	}
	
	
	// Click thêm nhân viên:
	$('.add_seller').click(function(e) {
		// 1. Lấy ra id của account:
		let id = Number($(e.target).prev().text());
		
		// 2. Gọi đến API thêm seller:
		$.ajax({
            type: "POST",
            url: "http://localhost:8081/admin/seller",
            // async: false,
            data: JSON.stringify(id),
            dataType: "json",
            contentType: "application/json",
            success: function (response) {
            	alert("Thêm thành công.");
            	window.location.reload();
            },
            error: function(reject) {
                alert("Không thành công.");
                console.log(reject);
            }
        });	
		
	})
	
	
	
})