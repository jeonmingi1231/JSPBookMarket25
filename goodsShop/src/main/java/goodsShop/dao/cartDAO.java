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
	 * public int clearCartByMember(String mId) { int result = 0; String sql =
	 * "DELETE FROM cart WHERE m_id = ?"; try { pstmt = conn.prepareStatement(sql);
	 * pstmt.setString(1, mId); result = pstmt.executeUpdate(); } catch (Exception
	 * e) { e.printStackTrace(); } return result; }
	 */

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
