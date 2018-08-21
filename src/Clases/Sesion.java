package Clases;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Sesion {

    public boolean enviarSinAdjunto(String user, String pass, String correoDe, String correoPara, String asunto, String texto) {
        boolean enviado = false;
        try {
            Properties p = new Properties();
            p.setProperty("mail.smtp.host", "smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable", "true");
            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user", user);
            p.setProperty("mail.smtp.auth", "false");
            Session session = Session.getDefaultInstance(p);
            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoDe));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoPara));
            message.setSubject(asunto);
            message.setText(texto);
            Transport t = session.getTransport("smtp");
            t.connect(user, pass);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            enviado = true;
        } catch (AddressException ex) {
            Logger.getLogger(Sesion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Sesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return enviado;
    }

    public boolean enviarConAdjunto(String user, String pass, String correoDe, String correoPara, String asunto, String texto, String adjunto, String nombreArchivo) {
        boolean enviado = false;
        try {
            Properties p = new Properties();
            p.setProperty("mail.smtp.host", "smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable", "true");
            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user", user);
            p.setProperty("mail.smtp.auth", "false");
            Session session = Session.getDefaultInstance(p);
            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoDe));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoPara));
            message.setSubject(asunto);
            BodyPart text = new MimeBodyPart();
            text.setText(texto);
            BodyPart adjuntar = new MimeBodyPart();
            adjuntar.setDataHandler(new DataHandler(new FileDataSource(adjunto)));
            adjuntar.setFileName(nombreArchivo);
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(text);
            multiParte.addBodyPart(adjuntar);
            message.setContent(multiParte);
            Transport t = session.getTransport("smtp");
            t.connect(user, pass);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            enviado = true;
        } catch (AddressException ex) {
            Logger.getLogger(Sesion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Sesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return enviado;
    }
}
