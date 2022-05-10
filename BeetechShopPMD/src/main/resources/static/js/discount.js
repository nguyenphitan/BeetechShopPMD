/**
 * Quản lý discount
 * Created by: NPTAN
 * Version: 1.0
 */
 
 $(document).ready(function() {
	// Click mở hộp chức năng:
	$('.t-div-function').click(openOrCloseBox.bind(this));
	
	// Click xóa discount:
	$('.t-delete').click(deleteProduct.bind(this));
	
	// Click sửa discount:
	$('.t-update').click(openUpdate.bind(this));
	
	// Click lưu thông tin cập nhật:
	$('.t-save-update').click(updateProduct.bind(this));
	
	// Click hủy cập nhật:
	$('.t-cancel-update').click(cancelUpdate.bind(this));
	
})


/*
	Đóng/Mở hộp chức năng: (Thêm, sửa, xóa)
	Created by: NPTAN
	Version: 1.0
*/
function openOrCloseBox(e) {
	e.stopPropagation();
	let className = $(e.target).attr('class');
	if( className == 't-div-function' ) {
		$(e.target).addClass('t-active');
		$(e.target).find('.t-function-box').show();
	}
	else {
		$(e.target).removeClass('t-active');
		$(e.target).find('.t-function-box').hide();
	}
	
}

/*
	Đóng hộp chức năng khi nhấn: thêm, sửa, xóa
	Created by: NPTAN
	Version: 1.0
*/
function closeBox(e) {
	e.preventDefault();
	e.stopPropagation();
	let className = $(e.target).parents('.t-div-function').attr('class');
	if(className != 't-div-function') {
		$(e.target).parents('.t-div-function').removeClass('t-active');
		$(e.target).parents('.t-function-box').hide();
	}
}


/*
	Xóa discount khỏi danh sách
	Created by: NPTAN
	Version: 1.0
*/
function deleteProduct(e) {
	// Lấy ra id của sản phẩm:
	let productId = Number($(e.target).prev().text());

	$.ajax({
        type: "DELETE",
        url: `http://localhost:8081/admin/api/v1/discounts/${productId}`,
        success: function (response) {
            alert("Xóa thành công.");
            window.location.reload();
        }
    });
}


/*
	Hiển thị ô cập nhật thông tin discount
	Created by: NPTAN (30/04/2022)
	Version: 1.0
*/
function openUpdate(e) {
	let inputs = $(e.target).parents('.t-func-col').siblings('.t-info-col').find('input[hide]');
	for(let input of inputs) {
		$(input).prev().addClass('t-hide');
		$(input).removeClass('t-hide');
	}
	
	// Hiển thị nút: Lưu, hủy -> Ẩn nút sửa, xóa
	$(e.target).parent().siblings('.t-hide').removeClass('t-hide');
	$(e.target).parent().addClass('t-hide');
}


/*
	Lưu thông tin cập nhật discount
	Created by: NPTAN (30/04/2022)
	Version: 1.0
*/
function updateProduct(e) {
	// Lấy ra id của discount:
	let discountId = Number($(e.target).next().text());
	console.log(discountId);
	
	// Lấy các giá trị update build thành product:
	let inputUpdates = $(e.target).parents('.t-func-col').siblings('.t-info-col').find('input[hide]');
	let discount = {};
	discount['id'] = discountId;
	discount['value'] = $(inputUpdates[0]).val();
	discount['discount'] = Number($(inputUpdates[1]).val());
	
	// Gọi API sửa product:
	$.ajax({
        type: "PUT",
        url: `http://localhost:8081/admin/api/v1/discounts/${discountId}`,
        // async: false,
        data: JSON.stringify(discount),
        dataType: "json",
        contentType: "application/json",
        success: function (response) {
            alert('Cập nhật thành công.');
            window.location.reload();
        },
        error: function(reject) {
            alert('Không thành công.');
            console.log(reject);
        }
    });
}


/*
	Hủy thông tin cập nhật discount
	Created by: NPTAN (30/04/2022)
	Version: 1.0
*/
function cancelUpdate() {
	window.location.reload();
}
