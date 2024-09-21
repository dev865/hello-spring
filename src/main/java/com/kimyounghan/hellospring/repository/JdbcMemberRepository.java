package com.kimyounghan.hellospring.repository;

import com.kimyounghan.hellospring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository{

    // DataSource는 인터페이스로 DBCP, hicariCP도 해당 인터페이스를 구현화 해서 사용했다.
    private final DataSource datasource;
    private String sql;
    public JdbcMemberRepository(DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public Member save(Member member) {
        sql = "insert into member (name) values (?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);    // << 생성키 반환 옵션 지정

            pstmt.setString(1, member.getName());
            pstmt.executeUpdate();

            // 생성된 pk 리턴
            // conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) 옵션을 지정해줘야 사용할 수 있다.
            rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        sql = "select * from member where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();

            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);   //   Optional.of() << Optional로 감싸서 반환한다.
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        sql = "SELECT * FROM MEMBER WHERE NAME = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);

            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
        } catch(Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

        return Optional.empty();       //  Optional.empty() << 빈값을 가진 Optional 인스턴스를 생성.
    }

    @Override
    public List<Member> findAll() {

        sql = "SELECT * from MEMBER";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<Member> memberList = new ArrayList<>();

            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                memberList.add(member);
            }

            return memberList;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(datasource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs ){
        try{
            if(rs != null){
                rs.close();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, datasource);
    }
}
