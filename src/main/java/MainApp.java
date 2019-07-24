import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by DIO
 */
public class MainApp {

    static Connection conn;
    static Statement st;
    static Repository rep = new Repository();
    static Random ran = new Random();

    public static void main(String[] args) {
        dropTable();
        createDatabase();
        putData();
        List<Repository.ResultDoc> list= rep.select(rep.getUser(3));
        System.out.println(list.toString());

}

         static void createDatabase(){

            try {
                conn= DriverManager.getConnection("jdbc:sqlite:doc_db");
                String sql = "CREATE TABLE IF NOT EXISTS DOC_STATUS(\n" +
                             "STATUS_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                             "STATUS_NAME TEXT NOT NULL)";
                st=conn.createStatement();
                st.execute(sql);

                sql = "CREATE TABLE IF NOT EXISTS DOC_CATEGORY(\n" +
                        "   CATEGORY_ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "   CATEGORY_NAME TEXT )";
                st=conn.createStatement();
                st.execute(sql);

                sql = "CREATE TABLE IF NOT EXISTS DOC_USER(\n" +
                        "   USER_ID INTEGER PRIMARY KEY  AUTOINCREMENT,\n" +
                        "   USER_NAME TEXT NOT NULL)";
                st=conn.createStatement();
                st.execute(sql);

                sql = "CREATE TABLE IF NOT EXISTS DOC(\n" +
                        "   DOC_ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "   USER_ID INTEGER NOT NULL,\n" +
                        "   TSHORT TEXT NOT NULL,\n" +
                        "   DOC_DATE TEXT NOT NULL,\n" +
                        "   STATUS_ID INTEGER NOT NULL,\n" +
                        "   CATEGORY_ID INTEGER DEFAULT NULL,\n" +
                        "   LINK_DOC_ID INTEGER DEFAULT 0,\n" +
                        "   FOREIGN KEY (USER_ID) REFERENCES\n" +
                        "   DOC_USER(USER_ID)\n" +

                        "   FOREIGN KEY (STATUS_ID) REFERENCES\n" +
                        "   DOC_STATUS(STATUS_ID)\n" +

                        "   FOREIGN KEY (CATEGORY_ID) REFERENCES\n" +
                        "   DOC_CATEGORY(CATEGORY_ID) \n" +

                        "   FOREIGN KEY (LINK_DOC_ID) REFERENCES\n" +
                        "   DOC(DOC_ID))";
                st=conn.createStatement();
                st.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                     conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
         static void dropTable(){

            try {
                conn=DriverManager.getConnection("jdbc:sqlite:doc_db");
                String sql = "DROP TABLE DOC";
                st=conn.createStatement();
                st.execute(sql);
                sql = "DROP TABLE DOC_CATEGORY";
                st=conn.createStatement();
                st.execute(sql);
                sql = "DROP TABLE DOC_STATUS";
                st=conn.createStatement();
                st.execute(sql);
                sql = "DROP TABLE DOC_USER";
                st=conn.createStatement();
                st.execute(sql);
                System.out.println("DB was deleted");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

         static void putData(){
            Status status1 = new Status(1,"project");
            Status status2 = new Status(2,"published");
            ArrayList<User> users = new ArrayList<>();
            for (int i = 1; i <=5 ; i++) {
                users.add( new User(i,"user_name "+i));
            }
            ArrayList<Category> categorys = new ArrayList<>(10);
            for (int i = 0; i <10 ; i++) {
                categorys.add( new Category(i,"cat_name "+i));
            }
            ArrayList<Document> docs = new ArrayList<>(50);
            for (int i = 0; i <50 ; i++) {
                docs.add(new Document(i,users.get(ran.nextInt(4)+1),"str","1"+(i+100)+"-11-11",status1));
            }

            rep.insert(status1);
            rep.insert(status2);
            for (User user:users
                 ) {
                rep.insert(user);
            }
            for (Category cat:categorys
            ) {
                rep.insert(cat);
            }
            List<Document> list = new ArrayList<>(50);
            for (Document doc:docs
                 ) {
                rep.insert(doc);
                //System.out.println(doc);
                list.add(doc);
            }
        }



}
