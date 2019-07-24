
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DIO
 */
public class Repository implements IRepositiry {

    Connection conn;
    PreparedStatement st;

    //вставка категории
    public void insert(Category cat) {
        String sql = "INSERT INTO DOC_CATEGORY (CATEGORY_NAME) VALUES(?)";
        try {
            conn= MyConnection.getConnection();
            st=conn.prepareStatement(sql);
            st.setString(1,cat.getName());
            st.execute();
            System.out.println("add category");
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
           try {
               st.close();
               conn.close();
           } catch (SQLException e){
               e.printStackTrace();
           }
        }
    }

    //вставка статуса
    public void insert(Status status) {
        String sql = "INSERT INTO DOC_STATUS (STATUS_NAME) VALUES(?)";
        try {
            conn= MyConnection.getConnection();
            st=conn.prepareStatement(sql);
            st.setString(1,status.getName());
            st.execute();
            System.out.println("add status");
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    //вставка пользователя
    public void insert(User user) {
        String sql = "INSERT INTO DOC_USER (USER_NAME) VALUES(?)";
        try {
            conn= MyConnection.getConnection();
            st=conn.prepareStatement(sql);
            st.setString(1,user.getName());
            st.execute();
            System.out.println("add user");
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    //вставка документа
    public void insert(Document doc) {
        Integer cat_id= null;
        if (!(doc.getCategory()==null)){
            cat_id=doc.getCategory().getId();
        }
        String sql = "INSERT INTO DOC (USER_ID,DOC_DATE,STATUS_ID,\n" +
                "TSHORT) VALUES(?,?,?,?)";
       // String sql = "INSERT INTO DOC (USER_ID,DOC_DATE,STATUS_ID,CATEGORY_ID,\n" +
       //              "TSHORT, LINK_DOC_ID VALUES(?,?,?,?,?,?)";
        try {
            conn= MyConnection.getConnection();
            st=conn.prepareStatement(sql);
            st.setInt(1,doc.getUser().getId());
            st.setString(2,doc.getDate());
            st.setInt(3,doc.getStatus().getId());
//            st.setInt(4,cat_id);
            st.setString(4,doc.getTshort());
            st.execute();

        //   System.out.println("add doc");
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

    //получение пользователя по id
    public User getUser(int id) {
        User user = new User();
        String sql = "SELECT DOC.USER_ID, USER_NAME FROM DOC LEFT OUTER JOIN DOC_USER \n" +
                     "ON DOC.USER_ID=DOC_USER.USER_ID WHERE DOC_ID=?";
        try {
            conn= MyConnection.getConnection();
            st=conn.prepareStatement(sql);
            st.setInt(1,id);
            ResultSet rs= st.executeQuery();
            user.setId(rs.getInt("USER_ID"));
            user.setName(rs.getString("USER_NAME"));
            System.out.println(user);
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return user;
    }




    //получение информации о документе по пользователю
    public List<ResultDoc> select(User user){
        List<ResultDoc> resultList =new  ArrayList(50);
        String sql = "SELECT  DOC_ID, TSHORT, DOC_DATE, STATUS_NAME, CATEGORY_NAME, \n" +
                     "LINK_DOC_ID FROM DOC LEFT OUTER JOIN DOC_STATUS ON DOC.STATUS_ID = DOC_STATUS.STATUS_ID \n" +
                     "LEFT OUTER JOIN DOC_CATEGORY ON DOC.CATEGORY_ID = DOC_CATEGORY.CATEGORY_ID \n" +
                     "WHERE USER_ID=?;";
        try {
            conn= MyConnection.getConnection();
            st=conn.prepareStatement(sql);
            st.setInt(1,user.getId());
            ResultSet rs =st.executeQuery();
            while (rs.next()){
                ResultDoc rd= new ResultDoc();
                rd.setDoc_id(rs.getInt("DOC_ID"));
                rd.setTshort(rs.getString("TSHORT"));
                rd.setDoc_date(rs.getString("DOC_DATE"));
                rd.setDoc_status(rs.getString("STATUS_NAME"));
                rd.setDoc_category(rs.getString("CATEGORY_NAME"));
                rd.setLink_doc_id(rs.getInt("LINK_DOC_ID"));
                resultList.add(rd);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return resultList;

    }

    //вспомогательный класс для хранения информации о документе
class ResultDoc{
    private   int doc_id;
    private  String tshort;
    private  String doc_date;
    private  String doc_status;
    private  String doc_category;
    private   int link_doc_id;

    public ResultDoc() {
        this.doc_category=null;
        this.link_doc_id=0;
    }

    public ResultDoc(int doc_id, String tshort, String doc_date, String doc_status) {
        this.doc_id = doc_id;
        this.tshort = tshort;
        this.doc_date = doc_date;
        this.doc_status = doc_status;
        this.doc_category=null;
        this.link_doc_id=0;
    }

    public ResultDoc(int doc_id, String tshort, String doc_date, String doc_status, String doc_category) {
        this.doc_id = doc_id;
        this.tshort = tshort;
        this.doc_date = doc_date;
        this.doc_status = doc_status;
        this.doc_category = doc_category;
        this.link_doc_id = 0;
    }

    public void setDoc_id(int doc_id) {
        this.doc_id = doc_id;
    }

    public void setTshort(String tshort) {
        this.tshort = tshort;
    }

    public void setDoc_date(String doc_date) {
        this.doc_date = doc_date;
    }

    public void setDoc_status(String doc_status) {
        this.doc_status = doc_status;
    }

    public void setDoc_category(String doc_category) {
        this.doc_category = doc_category;
    }

    public void setLink_doc_id(int link_doc_id) {
        this.link_doc_id = link_doc_id;
    }

    @Override
    public String toString() {
        return "ResultDoc{" +
                "doc_id=" + doc_id +
                ", tshort='" + tshort + '\'' +
                ", doc_date='" + doc_date + '\'' +
                ", doc_status='" + doc_status + '\'' +
                ", doc_category='" + doc_category + '\'' +
                ", link_doc_id=" + link_doc_id +
                "}\n";
    }
}


}
