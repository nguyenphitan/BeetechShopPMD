$(document).ready(function() {
	//Toggle delivery
	const deliverys = document.querySelectorAll('.header-delivery_item')
	const searchs = document.querySelectorAll('.header-search')
	const role = $('#role').text();
	
	if(role == 'ADMIN') {
		$('.t-search-area').css('display', 'none');
		let listIcons = $('.header-delivery_item-logo');
		for(let icon of listIcons) {
			$(icon).css('display', 'none');
		}
	}
	
	deliverys.forEach((delivery,index) => {
	    const search = searchs[index]
	    
	    $(delivery).click(function(e) {
			console.log(e.target);
			$('.header-delivery_item.active').removeClass('active');
			$('.header-search.block').removeClass('block');
			$(e.target).parent().addClass('active');
			if(role != 'ADMIN') {
				$(search).addClass('block');
			}
		})
	    
	    /*delivery.onclick = (e) => {
	        document.querySelector('.header-delivery_item.active').classList.remove('active')
	        document.querySelector('.header-search.block').classList.remove('block')
	
	        delivery.classList.add('active')
	        search.classList.add('block')
	    }*/
	})
	
	
	//Slider
	const slider=document.querySelector('.slider')
	const sliderMain = document.querySelector('.slider-main')
	const sliderItems = document.querySelectorAll('.slider-item')
	const nextBtn = document.querySelector('.slider-next')
	const prevBtn = document.querySelector('.slider-prev')
	const sliderItemWidth = sliderItems[0].offsetWidth
	const numberOfSliderItems = sliderItems.length 
	
	var slideNumber = 0
	
	
	//Next Slider
	nextBtn.addEventListener('click',() => {
	    
	    // sliderItems.forEach((slide,index) => {
	    //     slide.classList.remove('active')
	    // })
	    slideNumber++;
	    
	    if(slideNumber > (numberOfSliderItems-1) ){
	        slideNumber=0;
	    }
	
	    // sliderItems[slideNumber].classList.add('active')
	
	    sliderMain.style.left = "-"+sliderItemWidth*slideNumber+"px"
	})
	
	//Prev Slider
	prevBtn.addEventListener('click',() => {
	    // sliderItems.forEach((slide,index) => {
	    //     slide.classList.remove('active')
	    // })
	    slideNumber--;
	    
	    if(slideNumber < 0){
	        slideNumber = numberOfSliderItems-1;
	    }
	
	    // sliderItems[slideNumber].classList.add('active')
	
	    sliderMain.style.left = "-"+sliderItemWidth*slideNumber+"px"
	})
	
	//Auto Play
	
	var playSlider;
	var auto = () => {
	    playSlider = setInterval(() =>{
	        slideNumber++;
	        
	        if(slideNumber > (numberOfSliderItems-1) ){
	            slideNumber=0;
	        }
	    
	        sliderMain.style.left = "-"+sliderItemWidth*slideNumber+"px"
	    }, 5000)
	}
	auto()
	
	
	//Scroll type pizza
	const menu = document.querySelector('.container_type')
	
	const sectionOneOptions = {}
	
	const sectionOneObServer = new IntersectionObserver(function(entries, callback) {
	    entries.forEach(entry => {
	        if(!entry.isIntersecting) {
	            menu.classList.add('scroll')
	        } else {
	            menu.classList.remove('scroll')
	        }
	    })
	},sectionOneOptions)
	
	sectionOneObServer.observe(slider)
	
	
	//Change active when scroll

})