package com.util;

public class MyUtilGeneral extends MyUtil{
	
	/**
	 * 페이징(paging) 처리(GET 방식)
	 * @param current_page	현재 표시되는 페이지 번호	
	 * @param total_page	전체 페이지 수
	 * @param list_url		링크를 설정할 주소
	 * @return				페이징 처리 결과
	 */
	@Override
	public String paging(int current_page, int total_page, String list_url) {
		StringBuilder sb = new StringBuilder();
		
		int numPerBlock = 10;
		int currentPageSetup;
		int n, page;
		
		if(current_page < 1 || total_page < current_page) {
			return "";
		}
		
		if(list_url.indexOf("?") != -1) {
			list_url += "&";
		} else {
			list_url += "?";
		}
		
		// currentPageSetup : 표시할첫페이지-1
		currentPageSetup = (current_page / numPerBlock) * numPerBlock;
		if(current_page % numPerBlock == 0) {
			currentPageSetup = currentPageSetup - numPerBlock;
		}

		sb.append("<div class='paginate'>");
		// 처음페이지, 이전(10페이지 전)
		n = current_page - numPerBlock;
		if(total_page > numPerBlock && currentPageSetup > 0) {
			sb.append("<a href='" + list_url + "page=1'>처음</a>");
			sb.append("<a href='" + list_url + "page=" + n + "'>이전</a>");
		}
		
		// 페이징
		page = currentPageSetup + 1;
		while(page <= total_page && page <= (currentPageSetup + numPerBlock)) {
			if(page == current_page) {
				sb.append("<span>" + page + "</span>");
			} else {
				sb.append("<a href='" + list_url + "page=" + page + "'>" + page + "</a>");
			}
			page++;
		}
		
		// 다음(10페이지 후), 마지막페이지
		n = current_page + numPerBlock;
		if(n > total_page) n = total_page;
		if(total_page - currentPageSetup > numPerBlock) {
			sb.append("<a href='" + list_url + "page=" + n + "'>다음</a>");
			sb.append("<a href='" + list_url + "page=" + total_page + "'>끝</a>");
		}
		sb.append("</div>");
	
		return sb.toString();
	}

	/**
	 * javascript로 페이징(paging) 처리 : javascript 지정 함수 호출
	 * @param current_page	현재 표시되는 페이지 번호	
	 * @param total_page	전체 페이지 수
	 * @param methodName	호출할 자바 스크립트 함수명
	 * @return				페이징 처리 결과
	 */
	@Override
    public String pagingMethod(int current_page, int total_page, String methodName) {
    	StringBuilder sb = new StringBuilder();

        int numPerBlock = 10;   // 리스트에 나타낼 페이지 수
        int currentPageSetUp;
        int n, page;
        
        if(current_page < 1 || total_page < current_page) {
        	return "";
        }
        
     // currentPageSetup : 표시할첫페이지-1
        currentPageSetUp = (current_page / numPerBlock) * numPerBlock;
        if (current_page % numPerBlock == 0) {
            currentPageSetUp = currentPageSetUp - numPerBlock;
        }

		sb.append("<div class='paginate'>");
        
        // 처음페이지, 이전(10페이지 전)
        n = current_page - numPerBlock;
        if ((total_page > numPerBlock) && (currentPageSetUp > 0)) {
			sb.append("<a onclick='" + methodName + "(1);'>처음</a>");
			sb.append("<a onclick='" + methodName + "(" + n + ");'>이전</a>");
        }

        // 페이지징
        page = currentPageSetUp + 1;
        while((page <= total_page) && (page <= currentPageSetUp + numPerBlock)) {
           if(page == current_page) {
        	   sb.append("<span>" + page + "</span>");
           } else {
			   sb.append("<a onclick='" + methodName + "(" + page + ");'>" + page + "</a>");
           }
           page++;
        }

        // 다음(10페이지 후), 마지막 페이지
        n = current_page + numPerBlock;
		if(n > total_page) n = total_page;
        if (total_page - currentPageSetUp > numPerBlock) {
			sb.append("<a onclick='" + methodName + "(" + n + ");'>다음</a>");
			sb.append("<a onclick='" + methodName + "(" + total_page + ");'>끝</a>");
        }
		sb.append("</div>");

        return sb.toString();
    }

	// 화면에 표시할 페이지를 중앙에 출력
	@Override
	public String pagingUrl(int current_page, int total_page, String list_url) {
		StringBuilder sb = new StringBuilder();
		
		int numPerBlock = 10;
		int n, page;
		
		if(current_page < 1 || total_page < current_page) {
			return "";
		}
		
		if(list_url.indexOf("?") != -1) {
			list_url += "&";
		} else {
			list_url += "?";
		}
		
		page = 1; // 출력할 시작 페이지
		if(current_page > (numPerBlock / 2) + 1) {
			page = current_page - (numPerBlock / 2) ;
			
			n = total_page - page;
			if( n < numPerBlock ) {
				page = total_page - numPerBlock + 1;
			}
			
			if(page < 1) page = 1;
		}
		
		sb.append("<div class='paginate'>");
		
		// 처음페이지
		if(page > 1) {
			sb.append("<a href='"+list_url+"page=1' title='처음'>&#x226A</a>");
		}

		// 이전(한페이지 전)
		n = current_page - 1;
		if(current_page > 1) {
			sb.append("<a href='"+list_url+"page="+n+"' title='이전'>&#x003C</a>");
		}

		n = page;
		while(page <= total_page && page < n + numPerBlock) {
			if(page == current_page) {
				sb.append("<span>"+page+"</span>");
			} else {
				sb.append("<a href='"+list_url+"page="+page+"'>"+page+"</a>");
			}
			page++;
		}

		// 다음(한페이지 다음)
		n = current_page + 1;
		if(current_page < total_page) {
			sb.append("<a href='"+list_url+"page="+n+"' title='다음'>&#x003E</a>");
		}

		// 마지막페이지
		if(page <= total_page) {
			sb.append("<a href='"+list_url+"page="+total_page+"' title='마지막'>&#x226B</a>");
		}
		
		sb.append("</div>");
		
		return sb.toString();
	}

	// 화면에 표시할 페이지를 중앙에 출력 : javascript 함수 호출
	@Override
	public String pagingFunc(int current_page, int total_page, String methodName) {
		StringBuilder sb = new StringBuilder();
		
		int numPerBlock = 10;
		int n, page;
		
		if(current_page < 1 || total_page < current_page) {
			return "";
		}
		
		page = 1; // 출력할 시작 페이지
		if(current_page > (numPerBlock / 2) + 1) {
			page = current_page - (numPerBlock / 2) ;
			
			n = total_page - page;
			if( n < numPerBlock ) {
				page = total_page - numPerBlock + 1;
			}
			
			if(page < 1) page = 1;
		}
		
		sb.append("<div class='paginate'>");
		
		// 처음페이지
		if(page > 1) {
			sb.append("<a onclick='" + methodName + "(1);' title='처음'>&#x226A</a>");
		}

		// 이전(한페이지 전)
		n = current_page - 1;
		if(current_page > 1) {
			sb.append("<a onclick='" + methodName + "(" + n + ");' title='이전'>&#x003C</a>");
		}

		n = page;
		while(page <= total_page && page < n + numPerBlock) {
			if(page == current_page) {
				sb.append("<span>"+page+"</span>");
			} else {
				sb.append("<a onclick='" + methodName + "(" + page + ");' >"+page+"</a>");
			}
			page++;
		}

		// 다음(한페이지 다음)
		n = current_page + 1;
		if(current_page < total_page) {
			sb.append("<a onclick='" + methodName + "(" + n + ");' title='다음'>&#x003E</a>");
		}

		// 마지막페이지
		if(page <= total_page) {
			sb.append("<a onclick='" + methodName + "(" + total_page + ");' title='마지막'>&#x226B</a>");
		}
		
		sb.append("</div>");
		
		return sb.toString();
	}

}
