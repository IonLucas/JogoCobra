import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Tela extends JPanel implements ActionListener {
    
    //Dimenções da tela
    private final int Width = 300;
    private final int Heigth = 300;
    
    //Maça
    private Image Maca;
    //private final int PosicaoRandom = 29;
     int Maca_x;
     int Maca_y;
    
    //Cobra(Cabeça)
    private Image Cabeca;
    
    //Cobra(Corpo)
    private Image Corpo;
    private final int TamanhoCorpo = 10;
    final int CorpoTotal = 900;
     final int ParteCorpox[] = new int[CorpoTotal];
     final int ParteCorpoy[] = new int[CorpoTotal];
    private int QntdPartes;
    
    
    //Direçoes   
    private boolean Esquerda = false;
    private boolean Direita = true;
    private boolean Cima = false;
    private boolean Baixo = false;
    private boolean EmJogo = true;
    
    //Outros
    private Timer Tempo;
    private int Delay = 150;
    
    
   

    public Tela() { 
        IniciarTela();
    }
    
    private void IniciarTela() {
        addKeyListener(new TAdapter());
        setBackground(Color.GRAY);
        setFocusable(true);
        setDoubleBuffered(true);
            setPreferredSize(new Dimension(Width, Heigth));
        CarregarImagens();
        IniciarJogo();
        
    }

    private void CarregarImagens() {

        ImageIcon iicorpo = new ImageIcon("src/resources/corpo.png");
        Corpo = iicorpo.getImage();

        ImageIcon iimaca = new ImageIcon("src/resources/maca.png");
        Maca = iimaca.getImage();

        ImageIcon iicbc = new ImageIcon("src/resources/cabeca_direita.png");
        Cabeca = iicbc.getImage();
    }

    private void IniciarJogo() {
        
        QntdPartes = 3;
        
        //Definindo As posiçoes de cada Segmento
        for (int z = 0; z < QntdPartes; z++) {
            ParteCorpox[z] = 50 - z * 10;
            ParteCorpoy[z] = 50;
        }
        
        //Spawnando a Maça
        CoodMaca();
        
        //Rodando Com o Delay
        Tempo = new Timer(Delay, this);
        Tempo.start();
    }

 
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
      
    private void doDrawing(Graphics g) {
        if (EmJogo) {
            g.drawImage(Maca, Maca_x, Maca_y,this);
            
            //Desenhando Cada Parte do Corpo
            for (int z = 0; z < QntdPartes; z++) {
                if (z == 0) {
                    g.drawImage(Cabeca, ParteCorpox[z],ParteCorpoy[z], this);
                } else {
                    g.drawImage(Corpo, ParteCorpox[z], ParteCorpoy[z], this);
                }
            }

        } else {
            gameOver(g);
        }        
    }

    private void gameOver(Graphics g) {
        
        String msg = "Game Over";
        Font fonte = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(fonte);
        EmJogo=false;
        g.setColor(Color.red);
        g.setFont(fonte);
        g.drawString(msg, (Width - metr.stringWidth(msg)) / 2, Heigth / 2);
        
        
    }

    private void Ponto() {

        if ((ParteCorpox[0] == Maca_x) && (ParteCorpoy[0] == Maca_y)) {

            QntdPartes++;
            CoodMaca();
        }
    }
    private void CoodMaca() {
        int r = (int) (Math.random() * 30);
        Maca_x = ((r * TamanhoCorpo));

        r = (int) (Math.random() * 30);
        Maca_y = ((r * TamanhoCorpo));
        
        //Para spawnar em um lugar livre
          
        for(int p = 0 ; p<=QntdPartes ;p++){
        if((Maca_x == ParteCorpox[p])&& (Maca_y == ParteCorpoy[p])){
        CoodMaca();
        }
        
        
        }
    }

    private void move() {

        for (int z = QntdPartes; z > 0; z--) {
            ParteCorpox[z] = ParteCorpox[(z - 1)];
            ParteCorpoy[z] = ParteCorpoy[(z - 1)];
        }

        if (Esquerda) {
            ImageIcon iicbc = new ImageIcon("src/resources/cabeca_esquerda.png");
            Cabeca = iicbc.getImage();
            ParteCorpox[0] -= TamanhoCorpo;
        }

        if (Direita) {
            ImageIcon iicbc = new ImageIcon("src/resources/cabeca_direita.png");
            Cabeca = iicbc.getImage();
            ParteCorpox[0] += TamanhoCorpo;
        }

        if (Cima) {
            ImageIcon iicbc = new ImageIcon("src/resources/cabeca_cima.png");
            Cabeca = iicbc.getImage();
            ParteCorpoy[0] -= TamanhoCorpo;
        }

        if (Baixo) {
            ImageIcon iicbc = new ImageIcon("src/resources/cabeca_baixo.png");
            Cabeca = iicbc.getImage();
            ParteCorpoy[0] += TamanhoCorpo;
        }
    }

    private void ColisaoParede() {
        


        for (int z = QntdPartes; z > 0; z--) {

            if ((z > 0) && (ParteCorpox[0] == ParteCorpox[z]) &&
                           (ParteCorpoy[0] == ParteCorpoy[z])) {
                EmJogo = false;
            }
        }

        if (ParteCorpoy[0] >= Heigth) {
            EmJogo = false;
        }

        if (ParteCorpoy[0] < 0) {
            EmJogo = false;
        }

        if (ParteCorpox[0] >= Width) {
            EmJogo = false;
        }

        if (ParteCorpox[0] < 0) {
            EmJogo = false;
        }
        
        if (!EmJogo) {
            Tempo.stop();
        }
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {

        if (EmJogo) {

            Ponto();
            ColisaoParede();
            move();
        }

        repaint();
    }
  


    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!Direita)) {
                Esquerda = true;
                Cima= false;
                Baixo = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!Esquerda)) {
                Direita = true;
                Cima = false;
                Baixo = false;
            }

            if ((key == KeyEvent.VK_UP) && (!Baixo)) {
                Cima = true;
                Direita = false;
                Esquerda = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!Cima)) {
                Baixo = true;
                Direita = false;
                Esquerda = false;
            }
            


        }
    }
}
