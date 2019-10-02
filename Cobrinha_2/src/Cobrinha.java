import java.awt.EventQueue;
import javax.swing.JFrame;

public class Cobrinha extends JFrame {

    public Cobrinha() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Tela());
        
        setResizable(false);
        pack();
        
        setTitle("Cobrinha");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {JFrame Frame = new Cobrinha();Frame.setVisible(true);});
        
    }
}
