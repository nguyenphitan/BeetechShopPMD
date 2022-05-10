
$(document).ready(function() {
	//admin bill button complete
	const btnCompletes = document.querySelectorAll('.btn_complete')
	console.log(btnCompletes.length)
	
	btnCompletes.forEach((btnComplete) => {
		let text = btnComplete.innerText;
		if( text == "Đã hoàn thành") {
		    btnComplete.classList.add('complete');
		}
	})
	
})