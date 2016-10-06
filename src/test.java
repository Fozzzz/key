import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.UUID;

public class test extends JFrame {
    public test() {
        this.setTitle("_Fozzz密码锁");

        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setResizable(false);


        this.setLayout(new BorderLayout());

        JPasswordField password = new JPasswordField();

        JTextArea passwordInfo = new JTextArea();

        JTextArea context = new JTextArea();
        JButton decryption = new JButton("解密");
        JButton encryption = new JButton("加密");
        JButton random = new JButton("随机数");

        this.add(password, BorderLayout.NORTH);

        this.add(context, BorderLayout.CENTER);

        Panel panel = new Panel(new FlowLayout());

        panel.add(random);
        panel.add(encryption);
        panel.add(decryption);


        this.add(panel, BorderLayout.SOUTH);

        this.setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        decryption.setBackground(Color.white);
        encryption.setBackground(Color.white);
        random.setBackground(Color.white);

        //密码提示框
        passwordInfo.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                remove(passwordInfo);
                remove(password);
                add(password, BorderLayout.NORTH);
                password.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        password.addKeyListener(new keyL(password, context));


        //密码输入框
        password.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = password.getText();
                if (text.equals("")) {
                    remove(passwordInfo);
                    remove(password);

                    add(passwordInfo, BorderLayout.NORTH);
                    passwordInfo.setText("empty");
                    passwordInfo.setBackground(Color.red);
                } else if (text.length() != 10) {
                    remove(passwordInfo);
                    remove(password);
                    add(passwordInfo, BorderLayout.NORTH);
                    passwordInfo.setText("password error");
                    passwordInfo.setBackground(Color.red);
                }
            }
        });

        //内容框
        context.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = context.getText();
                if (text.equals("empty") || text.startsWith("当前密码长度")) {
                    context.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (context.getText().equals("")) {
                    context.setText("empty");
                }
            }
        });
        //随机数
        random.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.setText(UUID.randomUUID().toString().substring(0,8));
                context.setBackground(Color.white);
                encryption.setBackground(Color.white);
                decryption.setBackground(Color.white);
            }
        });

        //加密
        encryption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = context.getText();
                byte[] bytes = text.getBytes();
                byte[] newBytes = new byte[bytes.length];
                for (int i = 0; i < bytes.length; i++) {
                    newBytes[i] = (byte) (bytes[i] + i);
                }
                try {
                    context.setText(new String(newBytes, "utf-8"));
                    context.setBackground(Color.cyan);
                    encryption.setBackground(Color.cyan);
                    decryption.setBackground(Color.white);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
            }
        });
        //解密
        decryption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (password.getPassword().length == 10) {
                    String text = context.getText();
                    byte[] bytes = text.getBytes();
                    byte[] newBytes = new byte[bytes.length];
                    for (int i = 0; i < bytes.length; i++) {
                        newBytes[i] = (byte) (bytes[i] - i);
                    }
                    try {
                        context.setText(new String(newBytes, "utf-8"));
                        context.setBackground(Color.YELLOW);
                        decryption.setBackground(Color.YELLOW);
                        encryption.setBackground(Color.white);
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) throws IOException {

        new test();
    }

    private class keyL implements KeyListener {
        private final JTextArea context;
        private JPasswordField password;


        public keyL(JPasswordField password, JTextArea context) {
            this.password = password;
            this.context = context;
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            context.setText(String.valueOf("当前密码长度：" + (password.getPassword().length + 1)));
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
