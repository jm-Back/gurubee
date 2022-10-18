// 엔터 처리
$(function(){
	$("input").not($(":button")).keypress(function (evt) {
		if (evt.keyCode === 13) {
			let fields = $(this).parents('form,body').find('button,input,textarea,select');
			let index = fields.index(this);
			if ( index > -1 && ( index + 1 ) < fields.length ) {
				fields.eq( index + 1 ).focus();
			}
			return false;
		}
	});
});

// 스크롤바 존재 여부를 확인하는 함수
function checkScrollBar() {
	let hContent = $("body").height();
	let hWindow = $(window).height();
	if(hContent > hWindow)
		return true;
	
	return false;
}

// jQuery 함수 구현 : 개체를 화면 중앙에 위치하는 함수
jQuery.fn.center = function () {
    this.css("position","absolute");
    this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 2) + 
                                                $(window).scrollTop()) + "px");
    this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) + 
                                                $(window).scrollLeft()) + "px");
    return this;
}

// jQuery 함수 구현 : jquery 1.9에서 사라진 toggle() 함수
jQuery.fn.extend({
    cycle: function (fn) {
        let args = arguments,
		guid = fn.guid || jQuery.guid++,
		i = 0,
		toggler = function (event) {
		    // Figure out which function to execute
		    let lastToggle = (jQuery._data(this, "lastToggle" + fn.guid) || 0) % i;
		    jQuery._data(this, "lastToggle" + fn.guid, lastToggle + 1);

		    // Make sure that clicks stop
		    event.preventDefault();

		    // and execute the function
		    return args[lastToggle].apply(this, arguments) || false;
		};

        // link all the functions, so any of them can unbind this click handler
        toggler.guid = guid;
        while (i < args.length) {
            args[i++].guid = guid;
        }

        return this.click(toggler);
    }
});