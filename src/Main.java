import com.patikadev.helper.MyHelper;
import com.patikadev.model.User;
import com.patikadev.view.LoginGUI;

public class Main {
    public static void main(String[] args) {

        MyHelper.setLayout();
        if(User.fetchByUsername("admin") == null){
            User.add("Administrator", "root","root","operator");
        }
        LoginGUI loginGUI = new LoginGUI();
    }
}