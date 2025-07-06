<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*, goodsShop.dao.cartDAO, goodsShop.dto.cartDTO" %>
<%
    request.setCharacterEncoding("UTF-8");

    // [1] 사용자 ID 받아오기 (없으면 기본값 user01로 처리)
    String mId = request.getParameter("mId");
    if (mId == null || mId.trim().isEmpty()) {
        mId = "user01"; // 테스트용 기본 ID
    }

    // [2] 장바구니 목록 불러오기
    cartDAO dao = new cartDAO();
    List<cartDTO> cartList = dao.getCartList(mId);
    int total = 0; // 총 합계 금액 초기화
    String cp = request.getContextPath(); // contextPath (경로) 사용
%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>장바구니</title>
    <!-- 부트스트랩 및 스타일시트 연결 -->
    <link href="<%=cp%>/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=cp%>/assets/css/styles.css" rel="stylesheet">
    <script src="<%=cp%>/assets/js/bootstrap.bundle.min.js"></script>
    <script src="<%=cp%>/assets/js/scripts.js"></script>
    <link rel="icon" href="<%=cp%>/assets/favicon.ico" type="image/x-icon">
</head>

<body>
<!-- [상단 네비게이션 바] -->
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
    <div class="container px-4 px-lg-5">
        <a class="navbar-brand fw-bold text-primary" href="#">🛍 Goods Shop</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent">
            메뉴 <span class="navbar-toggler-icon"></span>
        </button>
    </div>
</nav>

<!-- [장바구니 콘텐츠 영역] -->
<div class="container mt-5">
    <h2 class="mb-4 text-center fw-bold">🛒 장바구니</h2>

    <!-- [장바구니 테이블] -->
    <div class="table-responsive">
        <table class="table table-bordered text-center align-middle">
            <thead class="table-dark">
                <tr>
                    <th>상품명</th>
                    <th>카테고리</th>
                    <th>사이즈</th>
                    <th>단가</th>
                    <th>수량</th>
                    <th>총액</th>
                    <th>삭제</th>
                </tr>
            </thead>
            <tbody>
            <%
                // [3] 장바구니에 상품이 있는 경우 출력
                if (cartList != null && !cartList.isEmpty()) {
                    for (cartDTO dto : cartList) {
                        total += dto.getCTotal(); // 총합 계산
            %>
                <tr>
                    <td><%= dto.getIName() %></td>
                    <td><%= dto.getICategory() %></td>
                    <td><%= dto.getISize() %></td>
                    <td><%= dto.getIUnitPrice() %> 원</td>
                    <td><%= dto.getIQuantity() %></td>
                    <td><%= dto.getCTotal() %> 원</td>
                    <td>
                        <!-- [삭제 버튼: 해당 cartId를 post로 전달] -->
                        <form action="deleteCartItem.jsp" method="post">
                            <input type="hidden" name="cartId" value="<%= dto.getCartId() %>">
                            <button type="submit" class="btn btn-sm btn-danger">삭제</button>
                        </form>
                    </td>
                </tr>
            <%
                    }
                } else {
            %>
                <!-- [장바구니가 비어 있을 때 출력] -->
                <tr>
                    <td colspan="7">장바구니에 담긴 상품이 없습니다.</td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <!-- [하단: 총합계 표시 및 주문하기 버튼] -->
    <div class="d-flex justify-content-between align-items-center mt-4">
        <h4 class="text-primary">총 합계: <%= total %> 원</h4>
        <form action="order.jsp" method="get">
            <input type="hidden" name="mId" value="<%= mId %>">
            <button type="submit" class="btn btn-success btn-lg">🛍 주문하기</button>
        </form>
    </div>
</div>

<!-- [푸터] -->
<footer class="py-5 bg-dark mt-5">
    <div class="container text-center">
        <p class="m-0 text-white">&copy; 2025 수원삼성 블루윙즈 굿즈샵</p>
    </div>
</footer>

</body>
</html>
