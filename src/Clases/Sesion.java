package Clases;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Sesion {

    Properties p = new Properties();
    private String host, port, user, pass;
    Session session;
    MimeMessage message;
    Transport t;
    BodyPart text, adjuntar;
    MimeMultipart multiParte;
    public Sesion(String host, String port, String user, String pass) throws NoSuchProviderException {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        p.setProperty("mail.smtp.host", host);
        p.setProperty("mail.smtp.starttls.enable", "true");
        p.setProperty("mail.smtp.port", port);
        p.setProperty("mail.smtp.user", user);
        p.setProperty("mail.smtp.auth", "false");
        session = Session.getDefaultInstance(p);
        message = new MimeMessage(session);
        t = session.getTransport("smtp");
        text = new MimeBodyPart();
        adjuntar = new MimeBodyPart();
        multiParte = new MimeMultipart();
    }
    public Sesion() {
    }
    
    public Properties getP() {
        return p;
    }

    public void setP(Properties p) {
        this.p = p;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean enviarSinAdjunto(String correoDe, String correoPara, String asunto, String texto) {
        boolean enviado = false;
        try {
            session.setDebug(true);
            message.setFrom(new InternetAddress(correoDe));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoPara));
            message.setSubject(asunto);
            message.setText(texto);
            t.connect(this.user, this.pass);
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

    public boolean enviarConAdjunto(String correoDe, String correoPara, String asunto, String texto, String adjunto, String nombreArchivo) {
        boolean enviado = false;
        try {
            session.setDebug(true);          
            message.setFrom(new InternetAddress(correoDe));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoPara));
            message.setSubject(asunto);
            text.setText(texto);        
            adjuntar.setDataHandler(new DataHandler(new FileDataSource(adjunto)));
            adjuntar.setFileName(nombreArchivo);
            multiParte.addBodyPart(text);
            multiParte.addBodyPart(adjuntar);
            message.setContent(multiParte);
            t.connect(this.user, this.pass);
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
    
    public boolean validarInfo(){
        boolean valido;
        try {
            session.setDebug(true);
            t.connect(this.user, this.pass);
            valido = true;
        } catch (MessagingException ex) {
            valido = false;
        }
        return valido;
    }
}
