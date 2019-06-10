/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.ait.oop2.k17017;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String DB_HOST = "127.0.0.1";
    private static final int DB_PORT = 3306;
    private static final String DB_NAME = "NewsData";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";
    private static final String JDBC_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    /**
     * DBConnection 生成
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);  // SQLExceptionに変換して返す
        }
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
    }

    //contentIdでデータを選択し渡す
    public static News getNewsByContentId(long contentId) throws SQLException {
        News ret = null;

        String SQL = "SELECT * FROM Data WHERE contentId = ?";
        PreparedStatement stm = getConnection().prepareStatement(SQL);
        stm.setLong(1, contentId);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            ret = new News(rs.getDate("issueDate").toLocalDate(), rs.getLong("contentId"), rs.getInt("genreId"), rs.getString("title"), rs.getString("body"), rs.getString("linkUrl"), rs.getString("imageUrl"), rs.getString("imageHeight"), rs.getString("imageWidth"), rs.getDate("createdDate").toLocalDate(), rs.getString("sourceDomain"), rs.getString("sourceName"), rs.getInt("rank"));
        }

        return ret;
    }

    //genreIdでデータを選択しwidthの数だけ渡す
    public static List<News> getNewsByGenreId(int genreId, int width) throws SQLException {
        List<News> ret = new ArrayList();

        String SQL = "SELECT * FROM Data WHERE genreId = ? LIMIT ? ORDER BY contentId DESC";
        PreparedStatement stm = getConnection().prepareStatement(SQL);
        stm.setInt(1, genreId);
        stm.setInt(2, width);
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            ret.add(new News(rs.getDate("issueDate").toLocalDate(), rs.getLong("contentId"), rs.getInt("genreId"), rs.getString("title"), rs.getString("body"), rs.getString("linkUrl"), rs.getString("imageUrl"), rs.getString("imageHeight"), rs.getString("imageWidth"), rs.getDate("createdDate").toLocalDate(), rs.getString("sourceDomain"), rs.getString("sourceName"), rs.getInt("rank")));
        }

        return ret;
    }

    //Newsデータの新規登録
    public static boolean insertNewsData(News i_data) throws SQLException {
        News news;

        //データベースにすでに登録済みかどうか判断する登録済みならそのデータのrankを1あげる
        if ((news = getNewsByContentId(i_data.getContentId())) != null) {
            if (uprank(news) != false) {
                int rank1 = news.getrank();
                int rank2 = rank1 + 1;
                System.out.println("rank is changed\n" + news.getTitle() + "\n" + rank1 + "→" + rank2 + "\n");
                return true;
            } else {
                System.out.println("Error:rank up");
                return false;
            }
        }

        //データベースに存在しなければ登録
        String SQL = "INSERT INTO Data VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = getConnection().prepareStatement(SQL);
        stm.setDate(1, java.sql.Date.valueOf(i_data.getIssueDate()));
        stm.setLong(2, i_data.getContentId());
        stm.setInt(3, i_data.getGenreId());
        stm.setString(4, i_data.getTitle());
        stm.setString(5, i_data.getBody());
        stm.setString(6, i_data.getLinkUrl());
        stm.setString(7, i_data.getImageUrl());
        stm.setString(8, i_data.getImageHeight());
        stm.setString(9, i_data.getImageWidth());
        stm.setDate(10, java.sql.Date.valueOf(i_data.getCreatedDate()));
        stm.setString(11, i_data.getSourceDomain());
        stm.setString(12, i_data.getSourceName());
        stm.setInt(13, i_data.getrank());

        int result_count = stm.executeUpdate();

        if (result_count == 0) {
            return false;
        }
        stm.close();
        return true;
    }

    //rankを上昇させる
    private static boolean uprank(News i_data) throws SQLException {
        String SQL = "UPDATE Data SET rank = ? WHERE contentId = ?";
        PreparedStatement stm = getConnection().prepareStatement(SQL);
        stm.setInt(1, i_data.getrank() + 1);
        stm.setLong(2, i_data.getContentId());

        int result_count = stm.executeUpdate();

        if (result_count == 0) {
            return false;
        }
        stm.close();
        return true;
    }

    /*全ての記事のrankを1にリセットする。メンテナンス用なので普段はコメント化
    static boolean AllRankReset(boolean b) throws SQLException {
        if (b == true) {
            String SQL = "UPDATE Data SET rank = 1";
            PreparedStatement stm = getConnection().prepareStatement(SQL);
            int result_count = stm.executeUpdate();

            if (result_count == 0) {
                return false;
            }
            stm.close();
        } else {
            return false;
        }
        return true;
    }
     */
}
