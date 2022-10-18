// 메뉴 상단 고정
window.onscroll = function () {
	scrollFunction();
};
window.onload = function () {
	scrollFunction();
};
function scrollFunction() {
	if (document.documentElement.scrollTop > 30) {
		// document.querySelector(".navbar").classList.add("fixed-top");
	} else {
		// document.querySelector(".navbar").classList.remove("fixed-top");
	}
}

// 메뉴 활성화
$(function(){
    var url = window.location.pathname;
    var urlRegExp = new RegExp(url.replace(/\/$/, '') + "$");
    
	$('nav .navbar-collapse>ul>li>a').each(function() {
		if (urlRegExp.test(this.href.replace(/\/$/, ''))) {
			$(this).addClass('active');
			return false;
		}
	});
	if($('nav .navbar-collapse>ul>li>a').hasClass("active")) return false;
	
	$('nav .navbar-collapse>ul>li a').each(function() {
		if (urlRegExp.test(this.href.replace(/\/$/, ''))) {
			if($(this).closest(".dropdown").parent().parent(".navbar-collapse")) {
				
				$(this).closest(".dropdown").children("a").eq(0).addClass('active');
			} else {
				$(this).closest(".dropdown").closest(".dropdown").children("a").eq(0).addClass('active');
			}
			return false;
		}
	});
	if($('nav .navbar-collapse>ul>li a').hasClass("active")) return false;
	
 	var parent = url.replace(/\/$/, '').substr(0, url.replace(/\/$/, '').lastIndexOf("/"));
 	if(! parent) parent = "/";
    var urlParentRegExp = new RegExp(parent);
	$('nav .navbar-collapse>ul>li a').each(function() {
		if($(this).attr("href")=="#") return true;
		
		var phref = this.href.replace(/\/$/, '').substr(0, this.href.replace(/\/$/, '').lastIndexOf("/"));
		if (urlParentRegExp.test(phref)) {
			if(! $(this).closest(".dropdown")) {
				$(this).addClass('active');
			} else if($(this).closest(".dropdown").parent().parent(".navbar-collapse")) {
				$(this).closest(".dropdown").children("a").eq(0).addClass('active');
			} else {
				$(this).closest(".dropdown").closest(".dropdown").children("a").eq(0).addClass('active');
			}
			return false;
		}
	});
});