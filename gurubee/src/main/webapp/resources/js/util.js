// 내용의 값의 빈공백을 trim(앞/뒤)
if(typeof String.prototype.trim !== 'function') {
    String.prototype.trim = function() {
        let TRIM_PATTERN = /(^\s*)|(\s*$)/g;
        return this.replace(TRIM_PATTERN, "");
    };
}

// 이미지 파일인지 검사
function isImageFile(filename){
    let format = /(\.gif|\.jpg|\.jpeg|\.png)$/i;
    return format.test(filename);
}

// 파일 용량 구하기
function fileSize(file) {
	let size = 0;
	
	for(let i = 0; i < file.files.length; i++) {
		size += file.files[i].size;
	}
	
	return size;
}

// 이메일 형식 검사
function isValidEmail(data){
    let format = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
    return format.test(data); // true : 올바른 포맷 형식
}

// SQL 문 존재 여부 검사
function isValidSQL(data){
    let format = /(SELECT|INSERT|UPDATE|DELETE|CREATE|ALTER|DROP|EXEC|UNION|FETCH|DECLARE|TRUNCATE)/gi;
    return format.test(data);
}

// class 속성 값중 특정값을 가진 태그 찾기
function getElementsByClass(searchClass, node, tag) {
    let classElements = [];
    if ( node === null )
        node = document;
    if ( tag === null )
        tag = '*';
        
    let els = node.getElementsByTagName(tag);
    let elsLen = els.length;
    let pattern = new RegExp('(^|\\s)'+searchClass+'(\\s|$)');
    
    for (let i = 0, j = 0; i < elsLen; i++) {
        if ( pattern.test(els[i].className) ) {
            classElements[j] = els[i];
            j++;
        }
    }
    
    return classElements;
}

// 모든 태그에서 해당 속성과 속성의 값을 검색하여 해당 태그를 배열로 반환
function getElementsByAttr(attrName, attrValue) {
	let elements = document.getElementsByTagName("*");
	let foundElements = [];
	
	for(let i = 0; i<elements.length; i++) {
		if(elements[i].hasAttribute(attrName) && 
				elements[i].getAttribute(attrName).toLowerCase() === attrValue.toLowerCase()) {
			foundElements.push(elements[i]);
		}
	}
	
	return foundElements;	
}

// -------------------------------------------------
// 이벤트 등록
/* 
    // 사용 예
    let func= function() { alert('예제'); }
    addEvent(window, load, func);
*/
function addEvent(elm, evType, fn) {
    if (elm.addEventListener) {
        elm.addEventListener(evType, fn, false);
        return true;
    } else if (elm.attachEvent) {
        let r = elm.attachEvent('on' + evType, fn);
        return r;
    } else {
        elm['on' + evType] = fn;
    }
}

// -------------------------------------------------
// 팝업 윈도우즈
function winOpen(url, windowName, windowFeatures) {
	if(! theURL) return;
	if(! windowName) windowName="";
	
	let flag = windowFeatures;
    if(flag === undefined) {
		flag = "left=10, ";
		flag += "top=10, ";
		flag += "width=372, ";
		flag += "height=466, ";
		flag += "toolbar=no, ";
		flag += "menubar=no, ";
		flag += "status=no, ";
		flag += "scrollbars=no, ";
		flag += "resizable=no";
		// flag = "scrollbars=no,resizable=no,width=220,height=230";
	}
	
    window.open(url, windowName, flag);
}

// -------------------------------------------------
// 기타 형식 검사
// 영문, 숫자 인지 확인
 function isValidEngNum(str) {
    for(let i = 0; i < str.length; i++) {
        achar = str.charCodeAt(i);                 
        if( achar > 255 ) {
            return false;
        }
    }
    return true; 
}

// 전화번호 형식(숫자-숫자-숫자)인지 체크
function isValidPhone(data) {
    // let format = /^(\d+)-(\d+)-(\d+)$/;
    let format = /^(01[016789])-[0-9]{3,4}-[0-9]{4}$/g;
    return format.test(data);
}

// 문자열에 특수문자(",  ',  <,  > ) 검사
function isValidSpecialChar(data) {
    let format = /[\",\',<,>]/g;
    return format.test(data);
}
