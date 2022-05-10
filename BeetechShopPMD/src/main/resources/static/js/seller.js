/**
 * Quản lý nhân viên
 * Created by: NPTAN (08/05/2022)
 * Version: 1.0
 */
 
 
$(document).ready(function() {
	// Click xóa nhân viên (chỉ xóa role, không xóa tài khoản):
	$('.remove_seller').click(function(e) {
		// 1. Lấy ra id của account:
		let id = Number($(e.target).prev().text());
		
		// 2. Gọi đến API xóa seller:
		$.ajax({
            type: "DELETE",
            url: `http://localhost:8081/admin/seller/${id}`,
            success: function (response) {
                alert("Xóa thành công.");
                window.location.reload();
            }
        });
        
	})
	
	
	
})