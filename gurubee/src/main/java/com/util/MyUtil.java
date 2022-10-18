package com.util;

import java.util.regex.Pattern;

public abstract class MyUtil {
	/**
	 * 전체 페이지 수 구하기
	 * @param dataCount	총 데이터 개수
	 * @param size		한화면에 출력할 목록 수
	 * @return			전체 페이지 수
	 */
	public int pageCount(int dataCount, int size) {
		if(dataCount <= 0) {
			return 0;
		}
	
		return dataCount / size + (dataCount % size > 0 ? 1 : 0);
	}

    /**
     * 특수문자를 HTML 문자로 변경 및 엔터를 <br>로 변경
     * @param str	변경할 문자열
     * @return		HTML 문자로 변경된 문자열
     */
     public String htmlSymbols(String str) {
		if(str == null || str.length() == 0)
			return "";

    	 str=str.replaceAll("&", "&amp;");
    	 str=str.replaceAll("\"", "&quot;");
    	 str=str.replaceAll(">", "&gt;");
    	 str=str.replaceAll("<", "&lt;");
    	 
    	 str=str.replaceAll("\n", "<br>");
    	 str=str.replaceAll("\\s", "&nbsp;");  // \\s가 엔터도 변경하므로 \n보다 뒤에
    	 
    	 return str;
     }

     /**
      * NULL을 ""로 변경하기
      * @param str	변경할 문자열
      * @return		NULL을 ""로 변경된 문자열
      */
     public String checkNull(String str) {
         return str == null ? "" : str;
     }

     /**
      * E-Mail 검사
      * @param email  검사 할 E-Mail 
      * @return E-Mail 검사 결과
      */
     public boolean isValidEmail(String email) {
         if (email == null) return false;
         
         return Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", email.trim());
     }
	
	public abstract String paging(int current_page, int total_page, String list_url);
	public abstract String pagingMethod(int current_page, int total_page, String methodName);
	
	public abstract String pagingUrl(int current_page, int total_page, String list_url);
	public abstract String pagingFunc(int current_page, int total_page, String methodName);
}
