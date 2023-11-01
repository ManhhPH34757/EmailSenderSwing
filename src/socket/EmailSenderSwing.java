package socket;

import javax.swing.*;

import java.awt.GridLayout;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

@SuppressWarnings("serial")
public class EmailSenderSwing extends JFrame {
    private JTextField hostField;
    private JTextField portField;
    private JTextField senderField;
    private JTextField recipientField;
    private JTextField subjectField;
    private JTextArea bodyArea;
    private JButton sendButton;

    public EmailSenderSwing() {
        setTitle("Email Sender");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        add(new JLabel("Host:"));
        hostField = new JTextField();
        add(hostField);

        add(new JLabel("Port:"));
        portField = new JTextField();
        add(portField);

        add(new JLabel("Sender:"));
        senderField = new JTextField();
        add(senderField);

        add(new JLabel("Recipient:"));
        recipientField = new JTextField();
        add(recipientField);

        add(new JLabel("Subject:"));
        subjectField = new JTextField();
        add(subjectField);

        add(new JLabel("Body:"));
        bodyArea = new JTextArea();
        add(new JScrollPane(bodyArea));

        sendButton = new JButton("Send");
        sendButton.addActionListener(e -> sendEmail());
        add(sendButton);

        pack();
        setVisible(true);
    }

    private void sendEmail() {
        String host = hostField.getText();
        int port = Integer.parseInt(portField.getText());
        String sender = senderField.getText();
        String recipient = recipientField.getText();
        String subject = subjectField.getText();
        String body = bodyArea.getText();

        // Thiết lập thông tin tài khoản email
        String username = "your-email@example.com";
        String password = "your-password";

        // Thiết lập thông tin cấu hình gửi email
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Tạo một phiên gửi email
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Tạo đối tượng MimeMessage để biểu diễn email
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(body);

            // Gửi email
            Transport.send(message);

            JOptionPane.showMessageDialog(this, "Email sent successfully.");
        } catch (MessagingException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error sending email: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmailSenderSwing::new);
    }
}