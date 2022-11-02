<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class='reply-info'>
	<span class='reply-count'>댓글 ${replyCount}개</span>
	<span>[${pageNo}/${total_page} 페이지]</span>
</div>

<table class='table table-borderless reply-list'>
	<c:forEach var="vo" items="${listReply}">
		<tr class='list-header'>
			<td width='50%'>
				<span class='bold'>익명 ${vo.replyNum}</span>
			</td>
			<td width='50%' align='right'>
				<span>${vo.rep_regdate}</span>
				<c:choose>
					<c:when test="${sessionScope.member.id=='admin' || sessionScope.member.id==vo.reply_id}">
						<span class="deleteReply" data-replyNum='${vo.replyNum}' data-pageNo='${pageNo}'>| 삭제</span>
					</c:when>
					<c:otherwise>
						<span class="notifyReply">신고</span>
					</c:otherwise>					
				</c:choose>
			</td>
		</tr>
			<td colspan="2" valign='top'>${vo.rep_contents}</td>
		<tr>
			<td>
				<button type='button' class='btn btn-light btnReplyAnswerLayout' data-replyNum='${vo.replyNum}'>답글 <span id="answerCount${vo.replyNum}">${vo.answerCount}</span></button>
			</td>
			<td align='right'>
				<button type='button' class='btn btn-light btnSendReplyLike' data-replyNum='${vo.replyNum}' data-replyLike='1' title="좋아요"><i class="bi bi-hand-thumbs-up"></i> <span>${vo.likeCount}</span></button>
				<button type='button' class='btn btn-light btnSendReplyLike' data-replyNum='${vo.replyNum}' data-replyLike='0' title="싫어요"><i class="bi bi-hand-thumbs-down"></i> <span>${vo.disLikeCount}</span></button>	        
			</td>
		</tr>
	
	    <tr class='reply-answer'>
	        <td colspan='2'>
	            <div id='listReplyAnswer${vo.replyNum}' class='answer-list'></div>
	            <div class="answer-form">
	                <div class='answer-left'>└</div>
	                <div class='answer-right'><textarea class='form-control'></textarea></div>
	            </div>
	             <div class='answer-footer'>
	                <button type='button' class='btn btn-light btnSendReplyAnswer' data-replyNum='${vo.replyNum}'>답글 등록</button>
	            </div>
			</td>
	    </tr>
	</c:forEach>
</table>

<div class="page-navigation">
	${paging}
</div>							
