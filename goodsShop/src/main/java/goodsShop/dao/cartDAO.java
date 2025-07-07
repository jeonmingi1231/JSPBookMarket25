package goodsShop.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import goodsShop.dto.cartDTO;

public class cartDAO {

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public cartDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://192.168.111.101:3306/goodsDB", "goods", "1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================================
    // [DELETE] 장바구니 항목 삭제 (by cartId)
    // 이 메서드는 특정 장바구니 항목(cart_id)을 삭제합니다.
    // cart 테이블에서 해당 cart_id 값을 가진 행을 제거합니다.
    // 사용 예: 사용자가 장바구니에서 개별 상품을 삭제할 때
    // ================================
    public void deleteCartById(int cartId) {
        String sql = "DELETE FROM cart WHERE cart_id = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cartId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================================
    // [READ] 장바구니 목록 조회 (by 사용자 ID)
    // 이 메서드는 사용자의 m_id(회원 ID)를 기준으로
    // 해당 사용자의 장바구니에 담긴 모든 상품 목록을 조회합니다.
    // SQL SELECT 문을 통해 cart 테이블에서 정보를 읽어옵니다.
    // ================================
    public List<cartDTO> getCartList(String mId) {
        List<cartDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM cart WHERE m_id = ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                cartDTO dto = new cartDTO();
                dto.setCartId(rs.getInt("cart_id"));
                dto.setMId(rs.getString("m_id"));
                dto.setMName(rs.getString("m_name"));
                dto.setIId(rs.getString("i_id"));
                dto.setIName(rs.getString("i_name"));
                dto.setIUnitPrice(rs.getInt("i_unitPrice"));
                dto.setICategory(rs.getString("i_category"));
                dto.setISize(rs.getString("i_size"));
                dto.setIQuantity(rs.getInt("i_quantity"));
                dto.setCTotal(rs.getInt("c_total"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================================
    // [CREATE] 장바구니에 항목 추가
    // 이 메서드는 사용자가 장바구니에 상품을 추가할 때 호출됩니다.
    // cartDTO에 담긴 정보를 바탕으로 cart 테이블에 새 행을 INSERT합니다.
    // SQL INSERT 문을 사용합니다.
    // ================================
    public int insertCart(cartDTO dto) {
        int result = 0;
        String sql = "INSERT INTO cart (m_id, m_name, i_id, i_name, i_unitPrice, i_category, i_size, i_quantity, c_total) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getMId());
            pstmt.setString(2, dto.getMName());
            pstmt.setString(3, dto.getIId());
            pstmt.setString(4, dto.getIName());
            pstmt.setInt(5, dto.getIUnitPrice());
            pstmt.setString(6, dto.getICategory());
            pstmt.setString(7, dto.getISize());
            pstmt.setInt(8, dto.getIQuantity());
            pstmt.setInt(9, dto.getCTotal());
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // ================================
    // [DELETE] 장바구니 항목 삭제 (by cartId)
    // 위의 deleteCartById()와 유사하며, 삭제 결과를 int로 반환합니다.
    // 삭제 성공 시 1, 실패 시 0을 반환합니다.
    // ================================
    public int deleteCartItem(int cartId) {
        int result = 0;
        String sql = "DELETE FROM cart WHERE cart_id = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cartId);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

	/*
	// ================================
	// [DELETE] 장바구니 전체 비우기 (by 사용자 ID)
	// 사용자의 m_id를 기준으로 해당 사용자의 장바구니 전체 항목을 삭제합니다.
	// 전체 비우기 기능으로, 결제 후 초기화 등에 사용될 수 있습니다.
	// ================================
	public int clearCartByMember(String mId) {
	    int result = 0;
	    String sql = "DELETE FROM cart WHERE m_id = ?";
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, mId);
	        result = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	*/

    // ================================
    // [UTILITY] 데이터베이스 연결 종료
    // 사용이 끝난 Connection, PreparedStatement, ResultSet을 안전하게 종료합니다.
    // 메모리 누수 및 연결 유지 방지를 위해 꼭 필요합니다.
    // ================================
    public void close() {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
