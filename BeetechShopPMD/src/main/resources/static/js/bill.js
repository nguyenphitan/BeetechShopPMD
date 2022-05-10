/**
 * Quản lý hóa đơn
 * Created NPTAN (06/05/2022)
 * Version: 1.0
 */
 
 $(document).ready(function() {
	
	const btnCompletes = document.querySelectorAll('.btn_complete')
	console.log(btnCompletes.length)
	
	btnCompletes.forEach((btnComplete) => {
		let text = btnComplete.innerText;
		if( text == "Đã hoàn thành") {
		    btnComplete.classList.add('complete');
		}
	})
	
	// Click hoàn thành hóa đơn:
	$('.btn_complete').click(billSuccess.bind(this));
	
})


/*
	Hoàn thành hóa đơn (Hóa đơn đã được thanh toán, giao hàng thành công)
	Created by: NPTAN (06/05/2022)
	Version: 1.0
*/
function billSuccess(e) {
	// Lấy ra id hóa đơn:
	let billId = Number( $(e.target).prev().text() );
	
	// Gọi API sửa hóa đơn ( sửa status = 1 )
	$.ajax({
        type: "PUT",
        url: `http://localhost:8081/public/bill/update/${billId}`,
        // async: false,
        data: JSON.stringify(billId),
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