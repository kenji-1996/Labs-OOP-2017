package sample;

import java.sql.*;
import java.util.*;

public class MusicAlbumDSC extends MusicCatalogDS {
    static final String COLUMN_ID = "id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_GENRE = "genre";
    static final String COLUMN_COMPILATION = "compilation";
    static final String COLUMN_TRACKC = "track_count";

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public void connect() throws SQLException {
        // use this line instead of the next one if you have your own instance of MySQL
        String url = "jdbc:mysql://localhost:3306/java?autoReconnect=true&useSSL=false"; // replace some-database-name by your database name
        // replace your-username by your latcs7 username
        String user = "root";
        // replace your-password by the password provided to you; see lab sheet to find where password is
        String password = "";

        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
    }

    public void disconnect() throws SQLException {
        if (preparedStatement != null) preparedStatement.close();
        if (statement != null) statement.close();
        if (connection != null) connection.close();
    }

    /**
     * Retrieve a MusicAlbum by id
     * @param id
     * @return the MusicAlbum stored in the database, or null if the MusicAlbum does not exist
     * @throws SQLException
     */
    @Override
    public MusicAlbum get(String id) {
        MusicAlbum musicAlbum = null;
        try {
            connect();

            String queryString = "select * from music_album where id = ?";
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();



            if (rs.next()) {    // MusicAlbum exists in database
                musicAlbum = new MusicAlbum(
                        rs.getString(COLUMN_ID),
                        rs.getString(COLUMN_NAME),
                        rs.getString(COLUMN_GENRE),
                        rs.getBoolean(COLUMN_COMPILATION),
                        rs.getInt(COLUMN_TRACKC));
            }

            disconnect();


        } catch (Exception ex) { ex.printStackTrace(); }
        return musicAlbum;
    }

    /**
     * @return a list of all MusicAlbum records in database
     * @throws SQLException
     */
    @Override
    public List<MusicAlbum> getAll() {
        List<MusicAlbum> musicAlbumList = new ArrayList<MusicAlbum>();
        try {
            connect();

            String queryString = "select * from music_album";
            preparedStatement = connection.prepareStatement(queryString);
            ResultSet rs = preparedStatement.executeQuery();


            MusicAlbum tmp;

            while (rs.next()) {
                tmp = new MusicAlbum(
                        rs.getString(COLUMN_ID),
                        rs.getString(COLUMN_NAME),
                        rs.getString(COLUMN_GENRE),
                        rs.getBoolean(COLUMN_COMPILATION),
                        rs.getInt(COLUMN_TRACKC));

                musicAlbumList.add(tmp);
            }

            disconnect();
        } catch (Exception ex) { ex.printStackTrace(); }

        return musicAlbumList;
    }

    // todo in lab

    /**
     * @return a record count of all existing MusicAlbum records in database
     * @throws SQLException
     */
    public int count() throws SQLException {
        int count = 0;
        try {
            connect();
            String queryString = "SELECT COUNT(*) AS rowcount FROM music_album";
            preparedStatement = connection.prepareStatement(queryString);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            count = rs.getInt("rowcount");
            rs.close();
            disconnect();
        } catch (Exception ex) { ex.printStackTrace(); }
        return count;
    }

    @Override
    public void add(MusicAlbum musicAlbum) {
        try {
            // pre-condition:
            MusicAlbum tmp = get(musicAlbum.getId());
            // id should NOT EXIST in database in order to add musicAlbum to database
            boolean pre = (tmp == null);
            // if musicAlbum exists in database, throw exception
            if (!pre) {
                String msg = "MusicAlbum id " + musicAlbum.getId() + " is not new!";
                System.out.println("\nERROR: " + msg);
                throw new Exception(msg);
                // note: throwing exception terminates this method here, returning to the calling method.
            }

            // post-condition; given all pre-conditions are satisfied
            connect();

            String insertString = "insert into music_album values(?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.setString(1, musicAlbum.getId());
            preparedStatement.setString(2, musicAlbum.getName());
            preparedStatement.setString(3, musicAlbum.getGenre());
            preparedStatement.setBoolean(4, musicAlbum.isCompilation());
            preparedStatement.setInt(5, musicAlbum.getTrackCount());
            preparedStatement.executeUpdate();
            System.out.println("Added new album " + musicAlbum);

            disconnect();
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    // todo in lab
    @Override
    public void update(MusicAlbum musicAlbum) throws Exception {
        try {
            // pre-condition:
            MusicAlbum tmp = get(musicAlbum.getId());
            // id should NOT EXIST in database in order to add musicAlbum to database
            boolean pre = remove(tmp.getId()) == null;
            // if musicAlbum exists in database, throw exception
            if (pre) {
                String msg = "Album ID doesn't exist";
                throw new Exception(msg);
                // note: throwing exception terminates this method here, returning to the calling method.
            }

            // post-condition; given all pre-conditions are satisfied
            connect();
            String insertString = "insert into music_album values(?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.setString(1, musicAlbum.getId());
            preparedStatement.setString(2, musicAlbum.getName());
            preparedStatement.setString(3, musicAlbum.getGenre());
            preparedStatement.setBoolean(4, musicAlbum.isCompilation());
            preparedStatement.setInt(5, musicAlbum.getTrackCount());
            preparedStatement.executeUpdate();

            disconnect();
        } catch (Exception ex) { ex.printStackTrace(); }

    }

    @Override
    public MusicAlbum remove(String existingId) {
        MusicAlbum existing = null;
        try {
            // pre-condition:
            existing = get(existingId);
            // id should EXIST in database in order to delete musicAlbum from database
            boolean pre = (existing != null);
            // if musicAlbum DOES NOT EXIST in database, throw exception
            if (!pre) {
                String msg = "MusicAlbum id " + existingId + " does not exist!";
                System.out.println("\nERROR: " + msg);
                throw new Exception(msg);
                // note: throwing exception terminates this method here, returning to the calling method.
            }

            // post-condition; given all pre-conditions are satisfied
            connect();

            String deleteString = "delete from music_album where id = ? ";

            preparedStatement = connection.prepareStatement(deleteString);
            preparedStatement.setString(1, existingId);
            preparedStatement.executeUpdate();
            System.out.println("Album " + existingId + " removed");

            disconnect();
        } catch (Exception ex) { ex.printStackTrace(); }
        return existing;
    }

    public List<MusicAlbum> findByCompilation(boolean isCompilation)  {
        List<MusicAlbum> compilationList = new ArrayList<MusicAlbum>();
        try {
            connect();
            String queryString = "SELECT * FROM music_album WHERE compilation = ?";
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setBoolean(1,isCompilation);
            ResultSet rs = preparedStatement.executeQuery();
            MusicAlbum tmp;

            while (rs.next()) {
                tmp = new MusicAlbum(
                        rs.getString(COLUMN_ID),
                        rs.getString(COLUMN_NAME),
                        rs.getString(COLUMN_GENRE),
                        rs.getBoolean(COLUMN_COMPILATION),
                        rs.getInt(COLUMN_TRACKC));

                compilationList.add(tmp);
            }
            disconnect();
        } catch (Exception ex) { ex.printStackTrace(); }

        return compilationList;
    }


    // todo in lab
    public List<MusicAlbum> findByGenre(String genre) {
        List<MusicAlbum> genreList = new ArrayList<MusicAlbum>();
        try {
            connect();
            String queryString = "SELECT * FROM music_album WHERE genre = ?";
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setString(1,genre);
            ResultSet rs = preparedStatement.executeQuery();
            MusicAlbum tmp;

            while (rs.next()) {
                tmp = new MusicAlbum(
                        rs.getString(COLUMN_ID),
                        rs.getString(COLUMN_NAME),
                        rs.getString(COLUMN_GENRE),
                        rs.getBoolean(COLUMN_COMPILATION),
                        rs.getInt(COLUMN_TRACKC));

                genreList.add(tmp);
            }
            disconnect();
        } catch (Exception ex) { ex.printStackTrace(); }

        return genreList;
    }
}
