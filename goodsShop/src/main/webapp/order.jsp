<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page
	import="java.util.*, goodsShop.dao.cartDAO, goodsShop.dao.orderDAO, goodsShop.dao.orderItemDAO, goodsShop.dto.cartDTO, goodsShop.dto.orderDTO"%>
<%
request.setCharacterEncoding("UTF-8");

// [1] 회원 ID 확인 (파라미터 없으면 오류 처리)
String memberId = request.getParameter("mId");
if (memberId == null || memberId.trim().equals("")) {
	out.println("<script>alert('회원 정보가 없습니다.'); history.back();</script>");
	return;
}

// [2] 장바구니 정보 불러오기
cartDAO cartDAO = new cartDAO();
List<cartDTO> cartList = cartDAO.getCartList(memberId);

// [3] 장바구니 비었을 경우 예외 처리
if (cartList == null || cartList.isEmpty()) {
	cartDAO.close();
	out.println("<script>alert('장바구니가 비어 있습니다.'); location.href='cart.jsp?mId=" + memberId + "';</script>");
	return;
}

// [4] 총 주문 금액 계산 + 사용자 이름 추출
int total = 0;
String memberName = cartList.get(0).getMName();
for (cartDTO item : cartList) {
	total += item.getCTotal();
}

// [5] POST 요청이면 실제 주문 DB에 저장 처리
if ("POST".equalsIgnoreCase(request.getMethod())) {
	// [5-1] 사용자 입력값 수신
	String phone = request.getParameter("phone");
	String address = request.getParameter("address");
	String memo = request.getParameter("memo");
	String paymentMethod = request.getParameter("paymentMethod");

	// [5-2] 주문 DTO에 값 설정
	orderDTO order = new orderDTO();
	order.setMemberId(memberId);
	order.setMemberName(memberName);
	order.setOrderTotal(total);
	order.setPhone(phone);
	order.setAddress(address);
	order.setMemo(memo);
	order.setPaymentMethod(paymentMethod);
	
	// [5-3] 주문 DB 저장 (insertOrder) → orderId 반환
	orderDAO orderDAO = new orderDAO();
	int orderId = orderDAO.insertOrder(order);
	orderDAO.close();

	if (orderId == 0) {
		out.println("<script>alert('주문 등록 실패'); history.back();</script>");
		return;
	}

	// [5-4] 주문 상세 항목들 저장 (상품별 insert)
	orderItemDAO itemDao = new orderItemDAO();
	int successCount = 0;
	for (cartDTO item : cartList) {
		successCount += itemDao.insertOrderItem(orderId, item);
	}
	itemDao.close();

	// [5-5] 장바구니 비우기 (주석 처리됨: 원할 경우 주석 해제)
	// cartDAO.clearCartByMember(memberId);

	cartDAO.close();

	// [5-6] 주문 완료 화면으로 리다이렉트
	response.sendRedirect("orderItem.jsp?orderId=" + orderId);
	return;
}

cartDAO.close(); // GET 요청 시 장바구니 DAO 자원 해제
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>결제 정보 입력</title>
<link href="assets/css/bootstrap.min.css" rel="stylesheet">
<link href="assets/css/styles.css" rel="stylesheet">
</head>
<body>
	<div class="container mt-5">
		<h2 class="text-center mb-4">💳 주문 정보 입력</h2>

		<!-- [UI] 주문 정보 입력 폼 -->
		<form method="post" action="order.jsp?mId=<%=memberId%>">

			<!-- 이름 (readonly) -->
			<div class="mb-3">
				<label class="form-label">이름</label>
				<input type="text" class="form-control" value="<%=memberName%>" readonly>
			</div>

			<!-- 연락처 입력 -->
			<div class="mb-3">
				<label class="form-label">연락처</label>
				<input type="text" name="phone" class="form-control" required placeholder="010-0000-0000">
			</div>

			<!-- 주소 입력 -->
			<div class="mb-3">
				<label class="form-label">주소</label>
				<input type="text" name="address" class="form-control" required placeholder="예: 수원시 월드컵로 310">
			</div>

			<!-- 배송 요청사항 -->
			<div class="mb-3">
				<label class="form-label">배송 요청사항</label>
				<textarea name="memo" class="form-control" rows="3" placeholder="문 앞에 놔주세요."></textarea>
			</div>

			<!-- 결제 수단 선택 -->
			<div class="mb-3">
				<label class="form-label">결제 방법</label>
				<select name="paymentMethod" class="form-select" required>
					<option value="">-- 결제 수단 선택 --</option>
					<option value="무통장입금">무통장입금</option>
					<option value="카드결제">카드결제</option>
					<option value="간편결제">간편결제</option>
				</select>
			</div>

			<!-- 총 결제 금액 표시 -->
			<div class="mb-4 text-end">
				<h5 class="text-primary">총 결제 금액: <%=total%> 원</h5>
			</div>

			<!-- 버튼 영역 -->
			<div class="text-center">
				<button type="submit" class="btn btn-success btn-lg">✅ 결제하기</button>
				<a href="cart.jsp?mId=<%=memberId%>" class="btn btn-secondary">← 장바구니로</a>
			</div>

		</form>
	</div>
</body>
</html>
